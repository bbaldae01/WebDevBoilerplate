package me.idess.web.util;

import java.util.List;
import java.util.Map;

import me.idess.web.common.Constants;
import me.idess.web.model.CommonBean;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 클라이언트로 받아온 "q" 데이터 포맷(String)을 파싱하는 유틸 주 역할은 결과 데이터를 ResultData에 매핑한다.
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
public class ResultConvertUtil {
	
	private static final Logger	logger	= LoggerFactory.getLogger(ResultConvertUtil.class);
	
	/**
	 * param(String)을 JSONObject로 변환 후 ResultData를 리턴한다.
	 * 
	 * @param String
	 *            param
	 * @return JSONObject
	 * @throws Exception
	 * @author jrkang
	 * @date 2014-07-17
	 */
	public static JSONObject getResultToResultObject(String param) throws Exception {
		return getResultData(param);
	}
	
	/**
	 * 결과 데이터(Bean)를 ResultData에 매핑하여 리턴한다. 키는 해당 빈의 클래스 이름이 된다.
	 * 
	 * @param String
	 *            param, CommonBean cb
	 * @return JSONObject
	 * @throws Exception
	 * @author jrkang
	 * @date 2014-07-17
	 */
	public static JSONObject makeResultBeanToJson(String param, CommonBean cb) throws Exception {
		return makeResultBeanToJson(param, cb, cb.getClass().getSimpleName());
	}
	
	/**
	 * 결과 데이터(Bean)를 ResultData에 매핑하여 리턴한다. 키는 넘겨준 key가 된다.
	 * 
	 * @param String
	 *            param, CommonBean cb, String key
	 * @return JSONObject
	 * @throws Exception
	 * @author jrkang
	 * @date 2014-07-17
	 */
	public static JSONObject makeResultBeanToJson(String param, CommonBean cb, String key)
			throws Exception {
		
		JSONObject jsonObject = JSONObject.fromObject(param);
		JSONObject dataInfoObject = (JSONObject) jsonObject.get(Constants.DataInfo);
		JSONObject bodyObject = (JSONObject) dataInfoObject.get(Constants.Body);
		JSONObject resultDataObject = (JSONObject) bodyObject.get(Constants.ResultData);
		
		if (ObjectUtil.isNull(resultDataObject)) {
			resultDataObject = new JSONObject();
			resultDataObject.put(key, cb);
			bodyObject.put(Constants.ResultData, resultDataObject);
		} else {
			resultDataObject.clear();
			resultDataObject.put(key, cb);
		}
		
		return jsonObject;
		
	}
	
	/**
	 * 결과 데이터(map)를 ResultData에 매핑하여 리턴한다. 키는 해당 Map의 클래스 이름이 된다.
	 * 
	 * @param String
	 *            param, Map map
	 * @return JSONObject
	 * @throws Exception
	 * @author jrkang
	 * @date 2014-07-17
	 */
	@SuppressWarnings("rawtypes")
	public static JSONObject makeResultMapToJson(String param, Map map) throws Exception {
		return makeResultMapToJson(param, map, map.getClass().getSimpleName());
	}
	
	/**
	 * 결과 데이터(Map)를 ResultData에 매핑하여 리턴한다. 키는 넘겨준 key가 된다.
	 * 
	 * @param String
	 *            param, Map map, String key
	 * @return JSONObject
	 * @throws Exception
	 * @author jrkang
	 * @date 2014-07-17
	 */
	@SuppressWarnings("rawtypes")
	public static JSONObject makeResultMapToJson(String param, Map map, String key)
			throws Exception {
		
		JSONObject jsonObject = JSONObject.fromObject(param);
		JSONObject dataInfoObject = (JSONObject) jsonObject.get(Constants.DataInfo);
		JSONObject bodyObject = (JSONObject) dataInfoObject.get(Constants.Body);
		JSONObject resultDataObject = (JSONObject) bodyObject.get(Constants.ResultData);
		
		if (ObjectUtil.isNull(resultDataObject)) {
			resultDataObject = new JSONObject();
			resultDataObject.put(key, map);
			bodyObject.put(Constants.ResultData, resultDataObject);
		} else {
			resultDataObject.clear();
			resultDataObject.put(key, map);
		}
		
		return jsonObject;
	}
	
	/**
	 * 결과 데이터(list)를 ResultData에 매핑하여 리턴한다. 키는 해당 list의 클래스 이름이 된다.
	 * 
	 * @param String
	 *            param, List list
	 * @return JSONObject
	 * @throws Exception
	 * @author jrkang
	 * @date 2014-07-17
	 */
	@SuppressWarnings("rawtypes")
	public static JSONObject makeResultListBeanToJson(String param, List list) throws Exception {
		return makeResultListBeanToJson(param, list, list.getClass().getSimpleName());
	}
	
	/**
	 * 결과 데이터(list)를 ResultData에 매핑하여 리턴한다. 키는 넘겨준 key가 된다.
	 * 
	 * @param String
	 *            param, List list, String key
	 * @return JSONObject
	 * @throws Exception
	 * @author jrkang
	 * @date 2014-07-17
	 */
	@SuppressWarnings("rawtypes")
	public static JSONObject makeResultListBeanToJson(String param, List list, String key)
			throws Exception {
		
		JSONObject jsonObject = JSONObject.fromObject(param);
		JSONObject dataInfoObject = (JSONObject) jsonObject.get(Constants.DataInfo);
		JSONObject bodyObject = (JSONObject) dataInfoObject.get(Constants.Body);
		JSONObject resultDataObject = (JSONObject) bodyObject.get(Constants.ResultData);
		
		if (ObjectUtil.isNull(resultDataObject)) {
			resultDataObject = new JSONObject();
			resultDataObject.put(key, list);
			bodyObject.put(Constants.ResultData, resultDataObject);
		} else {
			resultDataObject.clear();
			resultDataObject.put(key, list);
		}
		
		return jsonObject;
	}
	
	/**
	 * 결과 데이터(Bean)를 SessionData에 매핑하여 리턴한다.
	 * 
	 * @param String
	 *            param, CommonBean cb
	 * @return JSONObject
	 * @throws Exception
	 * @author jrkang
	 * @date 2014-07-17
	 */
	public static JSONObject makeSessionBeanToJson(String param, CommonBean cb) throws Exception {
		
		JSONObject jsonObject = JSONObject.fromObject(param);
		JSONObject dataInfoObject = (JSONObject) jsonObject.get(Constants.DataInfo);
		JSONObject bodyObject = (JSONObject) dataInfoObject.get(Constants.Header);
		bodyObject.put(Constants.SessionData, cb);
		
		return jsonObject;
	}
	
	/**
	 * param(String)을 JSONObject로 변환 후 ResultData를 리턴한다.
	 * 
	 * @param String
	 *            param
	 * @return JSONObject
	 * @throws Exception
	 * @author jrkang
	 * @date 2014-07-17
	 */
	private static JSONObject getResultData(String param) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject(param);
		JSONObject dataInfoObject = (JSONObject) jsonObject.get(Constants.DataInfo);
		JSONObject bodyObject = (JSONObject) dataInfoObject.get(Constants.Body);
		return (JSONObject) bodyObject.get(Constants.ResultData);
	}
}
