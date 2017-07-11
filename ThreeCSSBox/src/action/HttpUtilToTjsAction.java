package action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.DefaultCookieSpecProvider;
import org.apache.http.util.EntityUtils;

import http.HttpConfig;
import log.LogManager;
import net.sf.json.JSONObject;

public class HttpUtilToTjsAction {

	public static JSONObject sendToTjsMesp(String data, String url, Map<String, String> headMap, Map<String, String> cookieMap) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse httpResponse = null;
		try {
			httpClient = HttpClients.custom().build();
			HttpPost httpPost = new HttpPost(url);

			// 设置cookie策略
			HttpClientContext context = HttpClientContext.create();
			Registry<CookieSpecProvider> registry = RegistryBuilder.<CookieSpecProvider>create().register(CookieSpecs.DEFAULT, new DefaultCookieSpecProvider()).build();
			context.setCookieSpecRegistry(registry);
			// 添加头部信息
			if (headMap != null) {
				Set<String> keySet = headMap.keySet();
				for (String str : keySet) {
					httpPost.addHeader(str, (String) headMap.get(str));
				}
			}
			// 设置携带的cookie
			// CookieStore cookieStore = new BasicCookieStore();
			String cookieStr = "";
			if (cookieMap != null) {
				Set<String> keySet = cookieMap.keySet();

				for (String key : keySet) {
					/*
					 * BasicClientCookie cookie = new
					 * BasicClientCookie(key,cookieMap.get(key));
					 * cookie.setVersion(0); cookie.setDomain("localhost");
					 * cookie.setPath("/tjsmesp");
					 * cookieStore.addCookie(cookie);
					 */
					cookieStr = key + "=" + cookieMap.get(key) + ";";
				}
			}
			if (!cookieStr.equals("")) {
				cookieStr = cookieStr.substring(0, cookieStr.length() - 1);
				httpPost.setHeader("Cookie", cookieStr);
			}

			if (data != null && !"".equals(data)) {
				HttpEntity entity = new StringEntity(data, ContentType.create(HttpConfig.CONTENT_TYPE_JSON, "UTF-8"));
				httpPost.setEntity(entity);
			}
			httpResponse = httpClient.execute(httpPost, context);

			Map<String, String> headerMap = new HashMap<>();
			HeaderIterator iterator = httpResponse.headerIterator();
			while (iterator.hasNext()) {
				Header header = (Header) iterator.next();
				headerMap.put(header.getName(), header.getValue());
			}

			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity responseEntity = httpResponse.getEntity();
				if (responseEntity != null) {
					byte[] result = EntityUtils.toByteArray(responseEntity);
					String stringResult = new String(result, "UTF-8");
					return JSONObject.fromObject(stringResult);
				} else {
					LogManager.httpLog.warn("responseEntity为空");
					return null;
				}
			} else {
				LogManager.httpLog.warn("http返回码为：" + statusCode + "请注意");
				return null;
			}

		} catch (Exception e) {
			LogManager.httpLog.error("http请求异常", e);
			return null;
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				LogManager.httpLog.error("关闭httpClient异常", e);
			}
			try {
				httpResponse.close();
			} catch (IOException e) {
				LogManager.httpLog.error("关闭httpResponse异常", e);
			}
		}
	}

	public static void main(String[] args) {
		String url = "http://172.26.80.110/tjsmesp/system/isLoginByUserCode.action?param.userCode=c8298dd1a9224a0781959537c31ea0b3";
		String url2 = "http://172.26.80.110/tjsmesp/system/getUserMsgByUserCode.action?param.userCode=c8298dd1a9224a0781959537c31ea0b3";
		String url3 = "http://172.26.80.110/tjsmesp/system/returnTimeOut.action";
		String url4 = "http://172.26.104.76:8080/tjsmesp/system/selectUserListByEnterprise.action?param.userCode=5a2285fbd87b4966b9d9023a6c2dad80";
		Map map = new HashMap<String, String>();
		map.put("JSESSIONID", "8A46DFE0F9D27F60ACB42431F24CCE45");
		JSONObject result = sendToTjsMesp(null, url3, null, map);
		System.out.println(result);
	}
}
