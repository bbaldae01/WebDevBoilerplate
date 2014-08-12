package me.idess.web.filter;

import java.util.HashMap;
import java.util.Map;

import me.idess.web.util.NumberUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 사용자 토큰 관련
 * 
 * @version 1.0
 * @author jrkang
 */
public class TokenObject {
	
	private static final Logger			logger	= LoggerFactory.getLogger(TokenObject.class);
	
	private static Map<String, String>	userToken;
	
	static {
		Object obj = new Object();
		synchronized (obj) {
			if (userToken == null)
				userToken = new HashMap<String, String>();
		}
	}
	
	/**
	 * 로그인 사용자의 토큰값을 얻는다.
	 * 
	 * @param String
	 *            userId
	 * @return String
	 * @throws
	 * @author jrkang
	 * @date 2014-07-17
	 */
	public static String getToken(String userId) {
		String token = null;
		try {
			token = userToken.get(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}
	
	/**
	 * 로그인 사용자의 토큰값을 설정한다.
	 * 
	 * @param String
	 *            userId, String token
	 * @return
	 * @throws
	 * @author jrkang
	 * @date 2014-07-17
	 */
	public static void setToken(String userId, String token) {
		try {
			userToken.put(userId, token);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 토큰 생성
	 * 
	 * @return 생성된 토큰
	 */
	public static String makeToken(String username) {
		return username + NumberUtil.getRandomValue();
	}
	
}
