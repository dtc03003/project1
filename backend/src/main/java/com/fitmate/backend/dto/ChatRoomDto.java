package com.fitmate.backend.dto;

import com.fitmate.backend.entity.Member;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChatRoomDto {
    private String nickname;
}
