package service;

import java.util.HashMap;
import java.util.Map;

import action.BoxErrorSAction;
import action.UserFileAction;
import dao.model.ext.UserFileExt;
import http.HOpCodeBox;
import http.HSession;
import http.HttpPacket;
import http.IHttpListener;
import http.exception.HttpErrorException;
import protobuf.http.BoxErrorProto.BoxErrorCode;
import protobuf.http.BoxErrorProto.BoxErrorS;
import protobuf.http.UserFoldProto.UpdateUserFileC;
import protobuf.http.UserFoldProto.UpdateUserFileS;

public class UserFileService implements IHttpListener {

	@Override
	public Map<Integer, String> getHttps() throws Exception {
		HashMap<Integer, String> map = new HashMap<>();
		map.put(HOpCodeBox.UPDATE_USERFILE, "updateUserFileHandle");
		return map;
	}

	@Override
	public Object getInstance() {
		return this;
	}

	public HttpPacket updateUserFileHandle(HSession hSession) throws HttpErrorException {
		UpdateUserFileC message = (UpdateUserFileC) hSession.httpPacket.getData();
		UserFileExt userFile = UserFileAction.updateUserFile(message.getUserFileId(), message.getUserFileName(), message.getUserFileState(), message.getIsUpdateUserFoldParent(), message.getUserFoldParentId());
		if (userFile == null) {
			BoxErrorS boxErrorS = BoxErrorSAction.create(BoxErrorCode.ERROR_CODE_19, hSession.headParam.hOpCode);
			throw new HttpErrorException(HOpCodeBox.BOX_ERROR, boxErrorS);
		}
		UpdateUserFileS.Builder builder = UpdateUserFileS.newBuilder();
		builder.setHOpCode(hSession.headParam.hOpCode);
		builder.setUserFile(UserFileAction.getUserFileBuilder(userFile));
		HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
		return packet;
	}
}
