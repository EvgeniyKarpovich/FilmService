package by.karpovich.filmSevice.api.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDto {

    private String login;
    private String password;

}
