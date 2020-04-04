package com.dj.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dj.security.entity.OauthTokens;

@Repository
public interface OauthTokensRepo extends JpaRepository<OauthTokens, Integer > {
	OauthTokens findByUserId(Long userId);
}
