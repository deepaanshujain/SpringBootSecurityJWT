package com.dj.security.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dj.common.utils.DateTimeUtility;
import com.dj.security.entity.OauthTokens;
import com.dj.security.repo.OauthTokensRepo;

@Service
public class TokenService {
	
	@Autowired
	OauthTokensRepo oauthTokensRepo;
	
	public boolean saveTokenForUser(HashMap<String, String> tokenData, Map<String, Object> userMap) {
		Long userId = Long.parseLong(userMap.get("userId").toString());
		
		OauthTokens userToken = oauthTokensRepo.findByUserId(userId);
		
		try {
			if(Objects.nonNull(userToken)) {
				userToken.setExpiryTime(DateTimeUtility.tokentimeformat(tokenData.get("expiryTime")));
				userToken.setCreatedAt(DateTimeUtility.tokentimeformat(tokenData.get("issueAt")));
				userToken.setFullToken(tokenData.get("token"));
				userToken.setToken(tokenData.get("hash"));
				oauthTokensRepo.save(userToken);
			} else {
				OauthTokens oAuthToken = new OauthTokens(tokenData.get("hash"), userId, DateTimeUtility.tokentimeformat(tokenData.get("expiryTime")),
						DateTimeUtility.tokentimeformat(tokenData.get("issueAt")), tokenData.get("token"));
				oauthTokensRepo.save(oAuthToken);
				
			}	
			return true;
			
		} catch (Exception e) {
			return false;
		}
		
	}

}
