package org.ssdlv.userservice.users;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.ssdlv.userservice.utils.Constants;
import org.ssdlv.userservice.utils.forms.UpdatePasswordRequest;
import org.ssdlv.userservice.utils.responses.ErrorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.auth0.jwt.JWT;

@Transactional
@CrossOrigin("*")
@RestController
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public UserController(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        UserService userService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PatchMapping("/users/{id}")
    @PreAuthorize("hasAnyAuthority('USER:UPDATE')")
    public ResponseEntity<?> updateUser(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody User user,
        HttpServletRequest request
    ) {
        try {
            User _user = userService.update(user, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(_user);
        }
        catch (NotFound found) {
            logger.debug("User: {} is not found.", id);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                            "User {"+id+"} is not found !",
                            request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAnyAuthority('USER:DELETE')")
    public ResponseEntity<?> deleteUser(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request
    ) {
        try {
            User user = userService.delete(id);
            if (user != null) {
                return ResponseEntity.status(HttpStatus.OK).body("User Deleted");
            }
            logger.debug("User: {} is not found.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (NotFound found) {
            logger.debug("User: {} is not found.", id);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                            "User {"+id+"} is not found !",
                            request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAnyAuthority('USER:READ')")
    public ResponseEntity<?> getUser(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request
    ) {
        try {
            User user = userService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        catch (NotFound found) {
            logger.debug("User: {} is not found.", id);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                        "User {"+id+"} is not found !",
                        request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('USER:READ')")
    public ResponseEntity<Page<User>> all(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "12") int size,
        @RequestParam(name = "column", defaultValue = "createdAt") String column
    ) {
        try {
            Sort sort = Sort.by(column).descending();
            Pageable pageable = PageRequest.of(page, size, sort);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userRepository.findAll(pageable));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @PostMapping("/users/{id}/updatePassword")
    public ResponseEntity<?> updatePassword(
        @PathVariable(name = "id") Long id,
        @RequestBody UpdatePasswordRequest passwordRequest,
        HttpServletRequest request
    ) {
        try {
            if (!passwordRequest.getNewPassword().equals(passwordRequest.getConfirmNewPassword())) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body("Password not matching");
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userService.updatePassword(id, passwordRequest.getNewPassword()));
        }
        catch (NotFound found) {
            logger.debug("User: {} is not found.", id);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.not_found_error(
                        "User {"+id+"} is not found !",
                        request.getRequestURI()
                    ));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String authorization = request.getHeader(Constants.AUTHORIZATION);

        if (authorization != null && authorization.startsWith(Constants.BEARER)) {
            String refreshTokenEntry = authorization.substring(7);
            Algorithm algorithm = Algorithm.HMAC256(Constants.SECRET_KEY);

            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(refreshTokenEntry);

            String email = decodedJWT.getSubject();

            User user = userRepository.findByEmail(email);
            List<String> authorities = new ArrayList<>();
            user.getPermissions().forEach(userPermission -> authorities.add(userPermission.getPermission().getName()));

            String accessToken = JWT.create()
                    .withSubject(user.getEmail())
                    .withExpiresAt(Constants.ACCESS_TOKEN_EXPIRATION)
                    .withIssuer(request.getRequestURL().toString())
                    .withClaim(Constants.CLAIM_AUTHORITIES, authorities)
                    .sign(algorithm);

            String refreshToken = JWT.create()
                    .withSubject(user.getEmail())
                    .withExpiresAt(Constants.REFRESH_TOKEN_EXPIRATION)
                    .withIssuer(request.getRequestURL().toString())
                    .sign(algorithm);

            UserUtil.sendTokenResponse(response, accessToken, refreshToken, null);
        }
        else {
            logger.error(Constants.MESSAGE_REFRESH_TOKEN_REQUIRED);
            throw new RuntimeException(Constants.MESSAGE_REFRESH_TOKEN_REQUIRED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        try {
            String password = passwordEncoder.encode(user.getPassword());
            user.setPassword(password);
            user = userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/userLogout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            String authorization = request.getHeader(Constants.AUTHORIZATION);

            String accessToken = authorization.substring(7);
            System.err.println(accessToken);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userService.logout(accessToken));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
