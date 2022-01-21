package com.fitmate.backend.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto{
    private String token;
    private String grantType;
    private String accessToken;
    private Long accessTokenExpiresIn;
    private String refreshToken;
}

//        return TokenDto.builder()
//                .grantType(BEARER_TYPE)
//                .accessToken(accessToken)
//                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
//                .refreshToken(refreshToken)
//                .build();