package service;

import java.util.HashMap;
import java.util.Map;

import action.BoxErrorSAction;
import action.UserFileAction;
import action.UserFoldAction;
import config.UserFileConfig;
import config.UserFoldConfig;
import http.HOpCodeBox;
import http.HSession;
import http.HttpPacket;
import http.IHttpListener;
import http.exception.HttpErrorException;
import protobuf.http.BoxErrorProto.BoxErrorCode;
import protobuf.http.BoxErrorProto.BoxErrorS;
import protobuf.http.MutliOperateProto.MutilOperateClearRecyclebinC;
import protobuf.http.MutliOperateProto.MutilOperateClearRecyclebinS;
import protobuf.http.MutliOperateProto.MutilOperateMoveToC;
import protobuf.http.MutliOperateProto.MutilOperateMoveToS;
import protobuf.http.MutliOperateProto.MutilOperateOffRecyclebinC;
import protobuf.http.MutliOperateProto.MutilOperateOffRecyclebinS;
import protobuf.http.MutliOperateProto.MutilOperateRemoveC;
import protobuf.http.MutliOperateProto.MutilOperateRemoveS;
import protobuf.http.MutliOperateProto.MutilOperateToRecyclebinC;
import protobuf.http.MutliOperateProto.MutilOperateToRecyclebinS;

public class MutliOperateService implements IHttpListener {

	@Override
	public Map<Integer, String> getHttps() throws Exception {
		HashMap<Integer, String> map = new HashMap<>();
		map.put(HOpCodeBox.MUTLI_TO_RECYCLEBIN, "mutliToRecycleBinHandle");
		map.put(HOpCodeBox.MUTLI_OFF_RECYCLEBIN, "mutliOffRecycleBinHandle");
		map.put(HOpCodeBox.MUTLI_MOVE_TO, "mutliMoveToHandle");
		map.put(HOpCodeBox.MUTLI_REMOVE, "mutliRemoveHandle");
		map.put(HOpCodeBox.MUTLI_CLEAR_RECYCLEBIN, "mutliClearRecycleBinHandle");
		return map;
	}

	@Override
	public Object getInstance() {
		return this;
	}

	public HttpPacket mutliToRecycleBinHandle(HSession hSession) throws HttpErrorException {
		MutilOperateToRecyclebinC message = (MutilOperateToRecyclebinC) hSession.httpPacket.getData();
		boolean result;
		if (message.getUserFoldIdsList().size() != 0) {
			result = UserFoldAction.updateUserFoldListState(message.getUserFoldIdsList(), UserFoldConfig.STATE_IN_RECYCLEBIN);
			if (!result) {
				BoxErrorS boxErrorS = BoxErrorSAction.create(BoxErrorCode.ERROR_CODE_20, hSession.headParam.hOpCode);
				throw new HttpErrorException(HOpCodeBox.BOX_ERROR, boxErrorS);
			}
		}
		if (message.getUserFileIdsList().size() != 0) {
			result = UserFileAction.updateUserFileListState(message.getUserFileIdsList(), UserFileConfig.STATE_IN_RECYCLEBIN);
			if (!result) {
				BoxErrorS boxErrorS = BoxErrorSAction.create(BoxErrorCode.ERROR_CODE_20, hSession.headParam.hOpCode);
				throw new HttpErrorException(HOpCodeBox.BOX_ERROR, boxErrorS);
			}
		}
		MutilOperateToRecyclebinS.Builder builder = MutilOperateToRecyclebinS.newBuilder();
		builder.setHOpCode(hSession.headParam.hOpCode);
		HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket mutliOffRecycleBinHandle(HSession hSession) throws HttpErrorException {
		MutilOperateOffRecyclebinC message = (MutilOperateOffRecyclebinC) hSession.httpPacket.getData();
		boolean result;
		if (message.getUserFoldIdsList().size() != 0) {
			result = UserFoldAction.updateUserFoldListState(message.getUserFoldIdsList(), UserFoldConfig.STATE_CAN_USE);
			if (!result) {
				BoxErrorS boxErrorS = BoxErrorSAction.create(BoxErrorCode.ERROR_CODE_21, hSession.headParam.hOpCode);
				throw new HttpErrorException(HOpCodeBox.BOX_ERROR, boxErrorS);
			}
		}
		if (message.getUserFileIdsList().size() != 0) {
			result = UserFileAction.updateUserFileListState(message.getUserFileIdsList(), UserFileConfig.STATE_CAN_USE);
			if (!result) {
				BoxErrorS boxErrorS = BoxErrorSAction.create(BoxErrorCode.ERROR_CODE_21, hSession.headParam.hOpCode);
				throw new HttpErrorException(HOpCodeBox.BOX_ERROR, boxErrorS);
			}
		}
		MutilOperateOffRecyclebinS.Builder builder = MutilOperateOffRecyclebinS.newBuilder();
		builder.setHOpCode(hSession.headParam.hOpCode);
		HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket mutliMoveToHandle(HSession hSession) throws HttpErrorException {
		MutilOperateMoveToC message = (MutilOperateMoveToC) hSession.httpPacket.getData();
		boolean result;
		if (message.getUserFoldIdsList().size() != 0) {
			result = UserFoldAction.updateUserFoldListParentId(message.getUserFoldIdsList(), message.getUserFoldParentId());
			if (!result) {
				BoxErrorS boxErrorS = BoxErrorSAction.create(BoxErrorCode.ERROR_CODE_22, hSession.headParam.hOpCode);
				throw new HttpErrorException(HOpCodeBox.BOX_ERROR, boxErrorS);
			}
		}
		if (message.getUserFileIdsList().size() != 0) {
			result = UserFileAction.updateUserFileListParentId(message.getUserFileIdsList(), message.getUserFoldParentId());
			if (!result) {
				BoxErrorS boxErrorS = BoxErrorSAction.create(BoxErrorCode.ERROR_CODE_22, hSession.headParam.hOpCode);
				throw new HttpErrorException(HOpCodeBox.BOX_ERROR, boxErrorS);
			}
		}
		MutilOperateMoveToS.Builder builder = MutilOperateMoveToS.newBuilder();
		builder.setHOpCode(hSession.headParam.hOpCode);
		HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket mutliRemoveHandle(HSession hSession) throws HttpErrorException {
		MutilOperateRemoveC message = (MutilOperateRemoveC) hSession.httpPacket.getData();
		boolean result;
		if (message.getUserFoldIdsList().size() != 0) {
			result = UserFoldAction.updateUserFoldListState(message.getUserFoldIdsList(), UserFoldConfig.STATE_DELETE);
			if (!result) {
				BoxErrorS boxErrorS = BoxErrorSAction.create(BoxErrorCode.ERROR_CODE_23, hSession.headParam.hOpCode);
				throw new HttpErrorException(HOpCodeBox.BOX_ERROR, boxErrorS);
			}
		}
		if (message.getUserFileIdsList().size() != 0) {
			result = UserFileAction.updateUserFileListState(message.getUserFileIdsList(), UserFileConfig.STATE_DELETE);
			if (!result) {
				BoxErrorS boxErrorS = BoxErrorSAction.create(BoxErrorCode.ERROR_CODE_23, hSession.headParam.hOpCode);
				throw new HttpErrorException(HOpCodeBox.BOX_ERROR, boxErrorS);
			}
		}
		MutilOperateRemoveS.Builder builder = MutilOperateRemoveS.newBuilder();
		builder.setHOpCode(hSession.headParam.hOpCode);
		HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket mutliClearRecycleBinHandle(HSession hSession) throws HttpErrorException {
		MutilOperateClearRecyclebinC message = (MutilOperateClearRecyclebinC) hSession.httpPacket.getData();
		boolean result = UserFoldAction.clearRecycleBin(message.getUserFoldTopId());
		if (!result) {
			BoxErrorS boxErrorS = BoxErrorSAction.create(BoxErrorCode.ERROR_CODE_24, hSession.headParam.hOpCode);
			throw new HttpErrorException(HOpCodeBox.BOX_ERROR, boxErrorS);
		}
		result = UserFileAction.clearRecycleBin(message.getUserFoldTopId());
		if (!result) {
			BoxErrorS boxErrorS = BoxErrorSAction.create(BoxErrorCode.ERROR_CODE_24, hSession.headParam.hOpCode);
			throw new HttpErrorException(HOpCodeBox.BOX_ERROR, boxErrorS);
		}
		MutilOperateClearRecyclebinS.Builder builder = MutilOperateClearRecyclebinS.newBuilder();
		builder.setHOpCode(hSession.headParam.hOpCode);
		HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
		return packet;
	}

}
