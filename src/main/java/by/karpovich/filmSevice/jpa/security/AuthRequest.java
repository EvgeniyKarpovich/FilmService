package by.karpovich.filmSevice.jpa.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthRequest {

    private String login;
    private String password;
}
