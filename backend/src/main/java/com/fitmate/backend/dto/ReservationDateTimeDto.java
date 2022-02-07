package com.fitmate.backend.dto;


import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReservationDateTimeDto {
    private LocalDate localDate;
    private Integer hours;
}
