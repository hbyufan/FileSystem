package service;

import java.util.HashMap;
import java.util.Map;

import action.UserBoxInfoAction;
import action.UserFileAction;
import config.CommonConfigBox;
import dao.model.base.UserBoxinfo;
import http.HOpCodeBox;
import http.HSession;
import http.HttpPacket;
import http.IHttpListener;
import http.exception.HttpErrorException;
import protobuf.http.BoxInfoProto.BoxInfoC;
import protobuf.http.BoxInfoProto.BoxInfoS;
import protobuf.http.UserGroupProto.UserData;
import util.SizeUtil;

public class BoxInfoService implements IHttpListener {

	@Override
	public Map<Integer, String> getHttps() throws Exception {
		HashMap<Integer, String> map = new HashMap<>();
		map.put(HOpCodeBox.GET_BOX_INFO, "getBoxInfoHandle");
		return map;
	}

	@Override
	public Object getInstance() {
		return this;
	}

	public HttpPacket getBoxInfoHandle(HSession hSession) throws HttpErrorException {
		BoxInfoC message = (BoxInfoC) hSession.httpPacket.getData();
		Long countSize = UserFileAction.getUserFileTotalSize(message.getUserFoldTopId());
		BoxInfoS.Builder builder = BoxInfoS.newBuilder();
		builder.setHOpCode(hSession.headParam.hOpCode);
		if (countSize == null) {
			builder.setUseSize(0);
		} else {
			int countM = (int) Math.ceil(countSize.longValue() / (SizeUtil.KB_SIZE + 0.0));
			builder.setUseSize(countM);
		}
		UserData userData = (UserData) hSession.otherData;
		UserBoxinfo userBoxinfo = UserBoxInfoAction.getUserBoxinfoById(userData.getUserId());
		if (userBoxinfo == null) {
			builder.setTotalSize(CommonConfigBox.BOX_INIT_SIZE);
		} else {
			builder.setTotalSize(CommonConfigBox.BOX_INIT_SIZE + userBoxinfo.getBoxSizeOffset());
		}
		HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
		return packet;
	}

}
