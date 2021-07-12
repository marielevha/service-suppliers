package org.ssdlv.userservice.users;

import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.ssdlv.userservice.blacktoken.BlackToken;
import org.ssdlv.userservice.blacktoken.BlackTokenRepository;
import org.ssdlv.userservice.utils.Constants;
import org.ssdlv.userservice.utils.SlugifyUtil;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BlackTokenRepository blackTokenRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, BlackTokenRepository blackTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.blackTokenRepository = blackTokenRepository;
    }

    public User save(User user) {
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        user.setSlug(SlugifyUtil.getInstance().slugify((user.getFirstName() + " " + user.getLastName())));
        return userRepository.save(user);
    }

    public User update(User _user, Long id) throws NotFound {
        User user = userRepository.findById(id).orElseThrow(NotFound::new);
        user.setFirstName(_user.getFirstName());
        user.setLastName(_user.getLastName());
        user.setUpdatedAt(new Date());
        user.setSlug(SlugifyUtil.getInstance().slugify((user.getFirstName() + " " + user.getLastName())));
        return userRepository.save(user);
    }

    public User delete(Long id) throws NotFound {
        User user = userRepository.findById(id).orElseThrow(NotFound::new);
        user.setDeletedAt(new Date());
        return userRepository.save(user);
    }

    public User findById(Long id) throws NotFound {
        return userRepository.findById(id).orElseThrow(NotFound::new);
    }

    public User updatePassword(Long id, String password) throws NotFound {
        User user = userRepository.findById(id).orElseThrow(NotFound::new);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public Boolean logout(String token) {
        BlackToken blackToken = blackTokenRepository.save(new BlackToken(token));
        return blackToken.getId() != null;
    }

    public int count_user(String profile, String state) {
        List<User> users = userRepository.findAll();

        if (profile.equals(Constants.PROFILE_CLIENT)) {
            users = users
                    .stream()
                    .filter(user -> user.getProfile().equals(Constants.PROFILE_CLIENT))
                    .collect(Collectors.toList());
        }
        if (profile.equals(Constants.PROFILE_PROVIDER)) {
            users = users
                    .stream()
                    .filter(user -> user.getProfile().equals(Constants.PROFILE_PROVIDER))
                    .collect(Collectors.toList());
        }
        if (profile.equals(Constants.PROFILE_ADMIN)) {
            users = users
                    .stream()
                    .filter(user -> user.getProfile().equals(Constants.PROFILE_ADMIN))
                    .collect(Collectors.toList());
        }

        if (state.equals("deleted")) {
            users = users
                    .stream()
                    .filter(user -> user.getDeletedAt() != null)
                    .collect(Collectors.toList());
        }
        /*if (state.equals("deleted")) {
            categories = categories
                    .stream()
                    .filter(category -> category.getDeletedAt() != null)
                    .collect(Collectors.toList());
        }*/

        return users.size();
    }
}
