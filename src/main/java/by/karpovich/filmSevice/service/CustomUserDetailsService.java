package by.karpovich.filmSevice.service;

import by.karpovich.filmSevice.jpa.model.UserModel;
import by.karpovich.filmSevice.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<UserModel> userModel = userRepository.findByLogin(login);
        if (userModel == null) {
            throw new UsernameNotFoundException("Unknown user: " + login);
        }
        UserDetails user = User.builder()
                .username(userModel.get().getLogin())
                .password(userModel.get().getPassword())
                .roles(userModel.get().getRole())
                .build();
        return user;
    }
}