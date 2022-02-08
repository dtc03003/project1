package com.fitmate.backend.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class MailDto {
    private String address;
    private String title;
    private String message;
}
