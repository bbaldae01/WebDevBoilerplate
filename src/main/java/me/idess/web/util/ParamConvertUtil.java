package me.idess.web.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import me.idess.web.common.Constants;
import me.idess.web.model.CommonBean;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 클라이언트로 받아온 "q" 데이터 포맷(String)을 파싱하는 유틸 주 역할은 Parameter 데이터를 해당 객체에 매핑한다.
 * 
 * <pre>
 * DataInfo --- Header --- SessionData
 *           |- Body   --- Parameter
 *                      |- ResultData --- 사용자 결과 데이터
 *                                     |- Message --- Type
 *                                                 |- Code
 *                                                 |- Content
 * </pre>
 * 
 * @version 1.0
 * @author jrkang
 */
public class ParamConvertUtil {
	
	private static final Logger	logger	= LoggerFactory.getLogger(ParamConvertUtil.class);
	
	/**
	 * 데이터 포맷(String)을 파싱하여 파라미터 Object만을 리턴한다.
	 * 
	 * @param String
	 *            param
	 * @return JSONObject
	 * @throws Exception
	 * @author jrkang
	 * @date 2014-07-17
	 */
	public static JSONObject getParamToParamObject(String param) throws Exception {
		return getParameterData(param);
	}
	
	/**
	 * 데이터 포맷(String)을 파싱하여 파라미터를 해당 Bean에 매핑한다.
	 * 
	 * @param String
	 *            param, CommonBean cb
	 * @return
	 * @throws Exception
	 * @author jrkang
	 * @date 2014-07-17
	 */
	public static void getParamToBean(String param, CommonBean cb) throws Exception {
		logger.debug("getParamBean[" + param + "]");
		try {
			JSONObject parameterObject = getParameterData(param);
			logger.debug("getParamToBean > parameterObject[" + parameterObject.toString() + "]");
			paramToBean(parameterObject, cb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void paramToBean(JSONObject parameterObject, CommonBean cb)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Class<?> clazz = null;
		String clazzNm = "";
		
		if (!ObjectUtil.isNull(cb)) {
			
			clazz = cb.getClass();
			clazzNm = clazz.getSimpleName();
			logger.debug("clazzNm[" + clazzNm + "]");
			
			Method[] methods = clazz.getMethods();
			JSONObject jObj = (JSONObject) parameterObject;
			for (Method method : methods) {
				String nm = method.getName();
				if (method.getParameterTypes().length > 0 && nm.indexOf("set") > -1
						&& nm.indexOf("set") == 0) { // setter check
					String fnm = nm.substring(3, 4).toLowerCase() + nm.substring(4);
					if (jObj.containsKey(fnm)) {
						method.invoke(cb, jObj.get(fnm));
					}
				}
			}
			
		}
		logger.debug(cb.toString());
		
	}
	
	/**
	 * 데이터 포맷(String)을 파싱하여 파라미터를 여러개의 해당 Bean에 매핑한다.
	 * 
	 * @param String
	 *            param, CommonBean... cbArr
	 * @return
	 * @throws Exception
	 * @author jrkang
	 * @date 2014-07-17
	 */
	public static void getParamToBeanArr(String param, CommonBean... cbArr) throws Exception {
		logger.debug("getParamBean[" + param + "]");
		try {
			JSONObject parameterObject = getParameterData(param);
			logger.debug("getParamToBean > parameterObject[" + parameterObject.toString() + "]");
			paramToBeanArr(parameterObject, cbArr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void paramToBeanArr(JSONObject parameterObject, CommonBean... cbArr)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Class<?> clazz = null;
		String clazzNm = "";
		
		if (cbArr.length > 0) {
			
			for (CommonBean cb : cbArr) {
				
				clazz = cb.getClass();
				clazzNm = clazz.getSimpleName();
				logger.debug("clazzNm[" + clazzNm + "]");
				
				if (parameterObject.containsKey(clazzNm)) {
					Method[] methods = clazz.getMethods();
					JSONObject jObj = (JSONObject) parameterObject.getJSONObject(clazzNm);
					for (Method method : methods) {
						String nm = method.getName();
						if (method.getParameterTypes().length > 0 && nm.indexOf("set") > -1
								&& nm.indexOf("set") == 0) { // setter check
							String fnm = nm.substring(3, 4).toLowerCase() + nm.substring(4);
							if (jObj.containsKey(fnm)) {
								method.invoke(cb, jObj.get(fnm));
							}
						}
					}
				}
				logger.debug(cb.toString());
			}
			
		}
		
	}
	
	/**
	 * 데이터 포맷(String)을 파싱하여 파라미터를 해당 Map에 매핑한다.(단일)
	 * 
	 * @param String
	 *            param, Map map
	 * @return
	 * @throws Exception
	 * @author jrkang
	 * @date 2014-07-17
	 */
	@SuppressWarnings("rawtypes")
	public static void getParamToMap(String param, Map map) throws Exception {
		logger.debug("getParamToMap[" + param + "]");
		JSONObject parameterObject = getParameterData(param);
		logger.debug("getParamToMap > parameterObject[" + parameterObject.toString() + "]");
		dataToNoKeyMap(parameterObject, map);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void dataToNoKeyMap(JSONObject parameterObject, Map map) throws Exception {
		if (!ObjectUtil.isNull(map)) {
			JSONObject jObj = (JSONObject) parameterObject;
			Iterator<String> i = jObj.keys();
			while (i.hasNext()) {
				String k = i.next();
				Object o = jObj.get(k);
				map.put(k, o);
			}
			logger.debug(map.toString());
		}
	}
	
	/**
	 * 데이터 포맷(String)을 파싱하여 파라미터를 해당 Map에 key로 매핑한다.(단일)
	 * 
	 * @param String
	 *            param, String keyStr, Map map
	 * @return
	 * @throws Exception
	 * @author jrkang
	 * @date 2014-07-17
	 */
	@SuppressWarnings("rawtypes")
	public static void getParamToMap(String param, String keyStr, Map map) throws Exception {
		logger.debug("getParamToMap[" + param + "]");
		JSONObject parameterObject = getParameterData(param);
		logger.debug("getParamToMap > parameterObject[" + parameterObject.toString() + "]");
		dataToMap(parameterObject, keyStr, map);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void dataToMap(JSONObject parameterObject, String keyStr, Map map)
			throws Exception {
		if (!ObjectUtil.isNull(keyStr) && !ObjectUtil.isNull(map)) {
			if (parameterObject.containsKey(keyStr)) {
				JSONObject jObj = (JSONObject) parameterObject.getJSONObject(keyStr);
				Iterator<String> i = jObj.keys();
				while (i.hasNext()) {
					String k = i.next();
					Object o = jObj.get(k);
					map.put(k, o);
				}
				logger.debug(keyStr + " " + map.toString());
			}
		}
	}
	
	/**
	 * 데이터 포맷(String)을 파싱하여 파라미터를 해당 Map에 매핑한다.(다중)
	 * 
	 * @param String
	 *            param, String[] keyArr, Map... mapArr
	 * @return
	 * @throws Exception
	 * @author jrkang
	 * @date 2014-07-17
	 */
	@SuppressWarnings("rawtypes")
	public static void getParamToMap(String param, String[] keyArr, Map... mapArr) throws Exception {
		logger.debug("getParamToMap[" + param + "]");
		JSONObject parameterObject = getParameterData(param);
		logger.debug("getParamToMap > parameterObject[" + parameterObject.toString() + "]");
		dataToMap(parameterObject, keyArr, mapArr);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void dataToMap(JSONObject parameterObject, String[] keyArr, Map... mapArr)
			throws Exception {
		if (mapArr.length > 0 && keyArr.length == mapArr.length) {
			Integer mapCnt = 0;
			for (String keyStr : keyArr) {
				if (parameterObject.containsKey(keyStr)) {
					JSONObject jObj = (JSONObject) parameterObject.getJSONObject(keyStr);
					Iterator<String> i = jObj.keys();
					while (i.hasNext()) {
						String k = i.next();
						Object o = jObj.get(k);
						mapArr[mapCnt].put(k, o);
					}
					logger.debug(keyStr + " " + mapArr[mapCnt].toString());
				}
				mapCnt++;
			}
		}
	}
	
	/**
	 * 데이터 포맷(String)을 파싱하여 세션데이터를 해당 Bean에 매핑한다.
	 * 
	 * @param String
	 *            param, CommonBean cb
	 * @return
	 * @throws Exception
	 * @author jrkang
	 * @date 2014-07-17
	 */
	public static void getSessionToBean(String param, CommonBean cb) throws Exception {
		logger.debug("getParamBean[" + param + "]");
		JSONObject sessionObject = getSessionData(param);
		logger.debug("getSessionToBean > sessionObject[" + sessionObject.toString() + "]");
		sessionToBean(sessionObject, cb);
	}
	
	private static void sessionToBean(JSONObject sessionObject, CommonBean cb)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Class<?> clazz = null;
		String clazzNm = "";
		
		if (!ObjectUtil.isNull(cb)) {
			
			clazz = cb.getClass();
			clazzNm = clazz.getSimpleName();
			logger.debug("clazzNm[" + clazzNm + "]");
			
			Method[] methods = clazz.getMethods();
			
			for (Method method : methods) {
				String nm = method.getName();
				if (method.getParameterTypes().length > 0 && nm.indexOf("set") > -1
						&& nm.indexOf("set") == 0) { // setter check
					String fnm = nm.substring(3, 4).toLowerCase() + nm.substring(4);
					if (sessionObject.containsKey(fnm)) {
						method.invoke(cb, sessionObject.get(fnm));
					}
				}
			}
			
			logger.debug(cb.toString());
			
		}
		
	}
	
	/**
	 * 데이터 포맷(String)을 파싱하여 세션데이터를 해당 Map에 매핑한다.
	 * 
	 * @param String
	 *            param, Map map
	 * @return
	 * @throws Exception
	 * @author jrkang
	 * @date 2014-07-17
	 */
	@SuppressWarnings("rawtypes")
	public static void getSessionToMap(String param, Map map) throws Exception {
		logger.debug("getSessionToMap[" + param + "]");
		JSONObject sessionObject = getSessionData(param);
		logger.debug("getSessionToMap > sessionObject[" + sessionObject.toString() + "]");
		sessionToMap(sessionObject, map);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void sessionToMap(JSONObject sessionObject, Map map) throws Exception {
		if (!ObjectUtil.isNull(map)) {
			Iterator<String> i = sessionObject.keys();
			while (i.hasNext()) {
				String k = i.next();
				Object o = sessionObject.get(k);
				map.put(k, o);
			}
			logger.debug("SessionData " + map.toString());
		}
	}
	
	/**
	 * 데이터 포맷(String)을 파싱하여 세션데이터(JSONObject)를 리턴한다.
	 * 
	 * @param String
	 *            param
	 * @return JSONObject
	 * @throws Exception
	 * @author jrkang
	 * @date 2014-07-17
	 */
	private static JSONObject getSessionData(String param) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject(param);
		JSONObject dataInfoObject = (JSONObject) jsonObject.get(Constants.DataInfo);
		JSONObject headerObject = (JSONObject) dataInfoObject.get(Constants.Header);
		return (JSONObject) headerObject.getJSONObject(Constants.SessionData);
	}
	
	/**
	 * 데이터 포맷(String)을 파싱하여 파라미터데이터(JSONObject)를 리턴한다.
	 * 
	 * @param String
	 *            param
	 * @return JSONObject
	 * @throws Exception
	 * @author jrkang
	 * @date 2014-07-17
	 */
	private static JSONObject getParameterData(String param) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject(param);
		JSONObject dataInfoObject = (JSONObject) jsonObject.get(Constants.DataInfo);
		JSONObject bodyObject = (JSONObject) dataInfoObject.get(Constants.Body);
		return (JSONObject) bodyObject.get(Constants.Parameter);
	}
	
}
