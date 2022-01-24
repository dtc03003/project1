package com.fitmate.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Table(name ="refresh_token")
@Entity
public class RefreshToken {
    @Id
    @Column(name = "token_key")
    private String key;
    private String value;

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }
    @Builder
    public RefreshToken(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
