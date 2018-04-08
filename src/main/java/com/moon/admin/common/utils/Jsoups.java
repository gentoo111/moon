package com.moon.admin.common.utils;

import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by zhao.wu on 2016/11/29.
 */
public class Jsoups {
	static {
		try {
			// 重置HttpsURLConnection的DefaultHostnameVerifier，使其对任意站点进行验证时都返回true
			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});

			// 创建随机证书生成工厂
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new X509TrustManager[] { new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}
			} }, new SecureRandom());

			// 重置httpsURLConnection的DefaultSSLSocketFactory， 使其生成随机证书
			HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 使用ssl的方式去获取远程html的dom， 该方法在功能上与Jsoup本身的转换工具一样，
	 * 仅仅是用来告诉代码阅读者这个方法已经对SSL进行了扩展
	 * 
	 * @param url
	 *            需要转换的页面地址
	 * @param timeoutMillis
	 *            请求超市时间
	 * @return 该页面的dom树
	 * @throws IOException
	 *             请求异常或者转换异常时抛出
	 */
	public static Document parse(URL url, int timeoutMillis) throws IOException {
		Connection con = HttpConnection.connect(url);
		con.timeout(timeoutMillis);
		return con.get();
	}
}