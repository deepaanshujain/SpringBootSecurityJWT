package com.dj.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "oauth_tokens")
public class OauthTokens {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@JsonIgnore
    private int id;
	
    private String token;
    
    @Column(name="user_id")
    private Long userId;
    
    @Column(name="expiry_time")
    private Date expiryTime;
    
    @Column(name="created_at")
    private Date createdAt;
    
    @Column(name="full_token")
    private String fullToken;
    
    public OauthTokens(String token, Long userId, Date expiryTime, Date createdAt, String fullToken) {
		this.token = token;
		this.userId = userId;
		this.expiryTime = expiryTime;
		this.createdAt = createdAt;
		this.fullToken = fullToken;
	}
}
