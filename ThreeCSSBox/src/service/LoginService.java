package service;

import java.util.HashMap;
import java.util.Map;

import action.BoxErrorSAction;
import action.UserAction;
import action.UserFoldAction;
import action.UserType2Action;
import config.UserFoldConfig;
import dao.model.base.UserFold;
import http.HOpCodeBox;
import http.HSession;
import http.HttpPacket;
import http.IHttpListener;
import http.exception.HttpErrorException;
import protobuf.http.BoxErrorProto.BoxErrorCode;
import protobuf.http.BoxErrorProto.BoxErrorS;
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

	public HttpPacket loginHandle(HSession hSession) throws HttpErrorException {
		LoginC message = (LoginC) hSession.httpPacket.getData();
		UserData userData;
		if (message.getType() == 2) {
			userData = UserType2Action.getUser(message.getToken());
		} else {
			userData = UserAction.getUser(message.getToken());
		}

		if (userData == null) {
			BoxErrorS boxErrorS = BoxErrorSAction.create(BoxErrorCode.ERROR_CODE_1, hSession.headParam.hOpCode);
			throw new HttpErrorException(HOpCodeBox.BOX_ERROR, boxErrorS);
		}
		UserFold userFold = UserFoldAction.getUserFoldTopType(UserFoldConfig.OWNER_TYPE_USER, userData.getUserId());
		if (userFold == null) {
			userFold = UserFoldAction.createUserFoldTopType(UserFoldConfig.OWNER_TYPE_USER, userData.getUserId());
			if (userFold == null) {
				userFold = UserFoldAction.getUserFoldTopType(UserFoldConfig.OWNER_TYPE_USER, userData.getUserId());
			}
		}
		if (userFold == null) {
			BoxErrorS boxErrorS = BoxErrorSAction.create(BoxErrorCode.ERROR_CODE_2, hSession.headParam.hOpCode);
			throw new HttpErrorException(HOpCodeBox.BOX_ERROR, boxErrorS);
		}
		LoginS.Builder builder = LoginS.newBuilder();
		builder.setHOpCode(hSession.headParam.hOpCode);
		builder.setUserFoldTopId(userFold.getUserFoldId());
		builder.setUserId(userData.getUserId());
		builder.setUserRealName(userData.getUserRealName());
		builder.setUserImgUrl(userData.getUserImgUrl());
		HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
		return packet;
	}
}
