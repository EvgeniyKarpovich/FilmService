package by.karpovich.filmSevice.api.dto;

import lombok.Data;

@Data
public class AuthenticationRequestDto {

    private String login;
    private String password;

}
