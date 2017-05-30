package service;

import java.util.HashMap;
import java.util.Map;

import action.UserAction;
import action.UserFoldAction;
import config.UserFoldConfig;
import dao.model.base.UserFold;
import http.HOpCodeBox;
import http.HSession;
import http.HttpPacket;
import http.IHttpListener;
import protobuf.http.LoginProto.LoginC;
import protobuf.http.LoginProto.LoginS;
import protobuf.http.UserGroupProto.UserData;

public class LoginService implements IHttpListener {

	@Override
	public Map<Integer, String> getHttps() throws Exception {
		HashMap<Integer, String> map = new HashMap<>();
		map.put(HOpCodeBox.LOGIN, "loginHandle");
		return map;
	}

	@Override
	public Object getInstance() {
		return this;
	}

	public HttpPacket loginHandle(HSession hSession) {
		LoginC message = (LoginC) hSession.httpPacket.getData();
		UserData userData = UserAction.getUser(message.getToken());
		if (userData == null) {
			return null;
		}
		UserFold userFold = UserFoldAction.getUserFoldTopType(UserFoldConfig.OWNER_TYPE_USER, userData.getUserId());
		if (userFold == null) {
			userFold = UserFoldAction.createUserFoldTopType(UserFoldConfig.OWNER_TYPE_USER, userData.getUserId());
			if (userFold == null) {
				userFold = UserFoldAction.getUserFoldTopType(UserFoldConfig.OWNER_TYPE_USER, userData.getUserId());
			}
		}
		if (userFold == null) {
			return null;
		}
		LoginS.Builder builder = LoginS.newBuilder();
		builder.setHOpCode(hSession.headParam.hOpCode);
		builder.setUserFoldTopId(userFold.getUserFoldId());
		builder.setUserId(userData.getUserId());
		builder.setUserRealName(userData.getUserRealName());
		HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
		return packet;
	}
}
