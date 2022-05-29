package by.karpovich.filmSevice.jpa.security;

import by.karpovich.filmSevice.jpa.model.UserModel;
import by.karpovich.filmSevice.jpa.security.jwt.JwtUser;
import by.karpovich.filmSevice.jpa.security.jwt.JwtUserFactory;
import by.karpovich.filmSevice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserModel user = userService.findByLogin(login);

        if (user == null) {
            throw new UsernameNotFoundException("User with login: " + login + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("IN loadUserByUsername - user with login: {} successfully loaded", login);
        return jwtUser;
    }
}
