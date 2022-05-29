package by.karpovich.filmSevice.api.dto;

import lombok.Data;

@Data
public class JwtResponse {

    private String username;
    private String token;

}
