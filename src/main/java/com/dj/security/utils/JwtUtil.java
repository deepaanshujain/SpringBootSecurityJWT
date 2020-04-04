package com.dj.security.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.dj.common.utils.DateTimeUtility;
import com.dj.security.services.MyUserDetailsService;
import com.dj.security.services.TokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	private static  Long userTokenTime;
	
	public JwtUtil(@Value("${jwtTokenTime}") Long userTokenTime) {
		JwtUtil.userTokenTime = userTokenTime;
	}

    private String SECRET_KEY = "secret";
    public static final String USERNAME = "userName";
	public static final String CLIENTID = "clientId";
	public static final String DATE = "date";
	public static final String AUTHTOKEN = "token";
	public static final String ISSUEAT = "issueAt";
	public static final String EXPIRYTIME = "expiryTime";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String userName, HttpServletRequest request,HttpServletResponse response) {
    	Map<String, Object> userMap = userDetailsService.getUserByUsername(userName);
    	HashMap<String, String> tokenData =  createToken(userName, request, response);
    	tokenService.saveTokenForUser(tokenData, userMap);
    	return tokenData.get("hash");
    }

    private HashMap<String, String> createToken(String userName, HttpServletRequest request,HttpServletResponse response) {
    	
    	/*******************CUSTOM LOGIC STARTS****************************************/
    	
    	long nowMillis = System.currentTimeMillis();
		HashMap<String, String> ob = new HashMap<>();
		Claims claims = Jwts.claims();
		claims.setSubject("User Authorization");
		claims.setIssuer(request.getRequestURL().toString());
		claims.put(USERNAME, userName);
		//claims.put(CLIENTID, user.getClientId());
		claims.put(DATE, DateTimeUtility.getCurrentDateTime());
		Date tokengenerate = DateTimeUtility.getExp(nowMillis, 0l);
	
		Date expiry = DateTimeUtility.getExp(nowMillis, userTokenTime);
		claims.setIssuedAt(tokengenerate);
		claims.setExpiration(expiry);
		
		String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
		
		ob.put(AUTHTOKEN, token);
		ob.put(ISSUEAT, "" + tokengenerate);
		ob.put(EXPIRYTIME, "" + expiry);
		
		String[] tokenarray = token.split("\\.".trim());
		String finaltoken = tokenarray[2];
		ob.put("hash", finaltoken);

		return ob;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}