package me.idess.web.common;

/**
 * Constants
 * 
 * @version 1.0
 * @author jrkang
 */
public class Constants {
	
	/**
	 * 데이터 구조 관련
	 * 
	 * <pre>
	 * DataInfo --- Header --- SessionData
	 *           |- Body   --- Parameter
	 *                      |- ResultData --- 사용자 결과 데이터
	 *                                     |- Message --- Type 
	 *                                                 |- Code
	 *                                                 |- Content
	 * </pre>
	 */
	// 데이터 구조의 최상위
	public static final String	DataInfo			= "DataInfo";
	// 헤더 부분
	public static final String	Header				= "Header";
	// 세션 정보
	public static final String	SessionData			= "SessionData";
	// 바디 부분
	public static final String	Body				= "Body";
	// 클라이언트로 부터 넘어오는 파라미터
	public static final String	Parameter			= "Parameter";
	// 클라이언트로 넘겨 줄 결과 데이터
	public static final String	ResultData			= "ResultData";
	// 메시지 타입(0:Error, ...)
	public static final String	Type				= "Type";
	// 에러 발생시 데이터
	public static final String	Error				= "Error";
	// 에러 코드
	public static final String	Code				= "Code";
	// 에러 내용
	public static final String	Message				= "Message";
	
	// request 에 저장될 세션 정보 키
	public static final String	UserInfo			= "UserInfo";
	
	// 로그인 정보 key
	public static final String	LoginKey			= "LoginBean";
	// 로그인 사용자 ID key
	public static final String	LoginId				= "id";
	
	// client단에서 사용하는 템플릿 파일의 경로
	public static final String	Template_File_Path	= "/js/tpl/";
}
