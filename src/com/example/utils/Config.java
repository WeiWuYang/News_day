package com.example.utils;
/**
 * Url�б�
 * @author Administrator
 *
 */
public class Config {
      //ip������
	public static String ip="http://118.244.212.82:9092/newsClient/";
	//news_list?ver=�汾��&subid=������&dir=1&nid=����id&stamp=20140321&cnt=20  ����
	public static String new_list="news_list?";
	
	//user_login?ver=�汾��&uid=�û���&pwd=����&device=0    *��½
	public static String user_login="user_login?";
	//user_register?ver=�汾��&uid=�û���&email=����&pwd=��½����    *ע��
	public static String user_register="user_register?";
	//user_forgetpass?ver=�汾��&email=����    * ��������
	public static String user_forgetpass="user_forgetpass?";
	//user_home?ver=�汾��&imei=�ֻ���ʶ��&token =�û�����  * ��������
	public static String user_home="user_home?";
	// ͷ���ϴ�user_image?token=�û�����& portrait =ͷ��
	public static String user_image="user_image?";
	//cmt_commit?ver=�汾��&nid=���ű��&token=�û�����&imei=�ֻ���ʶ��&ctx=��������
	public static String cmt_commit="cmt_commit?";
	//cmt_list ?ver=�汾��&nid=����id&type=1&stamp=yyyyMMdd&cid=����id&dir=0&cnt=20
	public static String cmt_list="cmt_list?";
}
