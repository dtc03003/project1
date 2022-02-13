package com.fitmate.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Long id;
    @OneToOne
    @JoinColumn(name = "stylist", nullable = false)
    private Portfolio stylist;
    @Column(name = "grade", nullable = false)
    @ColumnDefault("0")
    private float grade;
    @Column(name = "countOfFollower", nullable = false)
    @ColumnDefault("0")
    private int countOfFollower;

    public Grade increaseFollower(){
        this.countOfFollower++;
        return this;
    }

    public Grade decreaseFollower(){
        this.countOfFollower--;
        return this;
    }

    public Grade updateGrade(float grade){
        if(this.grade==0){
            this.grade = grade;
        } else {
            this.grade+=grade;
            this.grade/=2;
        }
        return this;
    }
}
