package com.example.utils;
/**
 * Url列表
 * @author Administrator
 *
 */
public class Config {
      //ip和域名
	public static String ip="http://118.244.212.82:9092/newsClient/";
	//news_list?ver=版本号&subid=分类名&dir=1&nid=新闻id&stamp=20140321&cnt=20  新闻
	public static String new_list="news_list?";
	
	//user_login?ver=版本号&uid=用户名&pwd=密码&device=0    *登陆
	public static String user_login="user_login?";
	//user_register?ver=版本号&uid=用户名&email=邮箱&pwd=登陆密码    *注册
	public static String user_register="user_register?";
	//user_forgetpass?ver=版本号&email=邮箱    * 忘记密码
	public static String user_forgetpass="user_forgetpass?";
	//user_home?ver=版本号&imei=手机标识符&token =用户令牌  * 个人中心
	public static String user_home="user_home?";
	// 头像上传user_image?token=用户令牌& portrait =头像
	public static String user_image="user_image?";
	//cmt_commit?ver=版本号&nid=新闻编号&token=用户令牌&imei=手机标识符&ctx=评论内容
	public static String cmt_commit="cmt_commit?";
	//cmt_list ?ver=版本号&nid=新闻id&type=1&stamp=yyyyMMdd&cid=评论id&dir=0&cnt=20
	public static String cmt_list="cmt_list?";
}
