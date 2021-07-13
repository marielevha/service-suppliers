package org.ssdlv.userservice.users;

import org.springframework.security.crypto.password.PasswordEncoder;

public class UserFaker {

    public static void userFaker(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        String password = passwordEncoder.encode("password");
        userRepository.save(new User("Carols", "DENVER", "000000000", "denver@ssdlv.com", password, null,null));
        userRepository.save(new User("Jessica", "JONES", "000000000", "jones@ssdlv.com", password, null, null));
        userRepository.save(new User("Pepper", "POTTS", "000000000", "potts@ssdlv.com", password, null, null));
    }
}
