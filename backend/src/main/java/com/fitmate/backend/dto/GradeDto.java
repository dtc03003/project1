package com.fitmate.backend.dto;

import com.fitmate.backend.entity.Grade;
import com.fitmate.backend.entity.Member;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GradeDto {
    private String stylistNickName;
    private int followCount;
    private float grade;

    public static Grade toEntity(GradeDto gradeDto, Member stylist){
        return Grade.builder()
                .stylist(stylist)
                .followCount(gradeDto.followCount)
                .grade(gradeDto.grade)
                .build();
    }

    public static GradeDto of(Grade grade){
        return GradeDto.builder()
                .stylistNickName(grade.getStylist().getNickname())
                .followCount(grade.getFollowCount())
                .grade(grade.getGrade())
                .build();
    }
}
