package by.karpovich.filmSevice.jpa.security.jwt;

import by.karpovich.filmSevice.jpa.model.Role;
import by.karpovich.filmSevice.jpa.model.Status;
import by.karpovich.filmSevice.jpa.model.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(UserModel user) {
        return new JwtUser(
                user.getId(),
                user.getLogin(),
                user.getName(),
                user.getLastname(),
                user.getPassword(),
                user.getEmail(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles())),
                user.getStatus().equals(Status.ACTIVE)
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }
}
