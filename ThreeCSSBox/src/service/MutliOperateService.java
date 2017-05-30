package service;

import java.util.HashMap;
import java.util.Map;

import action.UserFileAction;
import action.UserFoldAction;
import config.UserFileConfig;
import config.UserFoldConfig;
import http.HOpCodeBox;
import http.HSession;
import http.HttpPacket;
import http.IHttpListener;
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

	public HttpPacket mutliToRecycleBinHandle(HSession hSession) {
		MutilOperateToRecyclebinC message = (MutilOperateToRecyclebinC) hSession.httpPacket.getData();
		boolean result;
		if (message.getUserFoldIdsList().size() != 0) {
			result = UserFoldAction.updateUserFoldListState(message.getUserFoldIdsList(), UserFoldConfig.STATE_IN_RECYCLEBIN);
			if (!result) {
				return null;
			}
		}
		if (message.getUserFileIdsList().size() != 0) {
			result = UserFileAction.updateUserFileListState(message.getUserFileIdsList(), UserFileConfig.STATE_IN_RECYCLEBIN);
			if (!result) {
				return null;
			}
		}
		MutilOperateToRecyclebinS.Builder builder = MutilOperateToRecyclebinS.newBuilder();
		builder.setHOpCode(hSession.headParam.hOpCode);
		HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket mutliOffRecycleBinHandle(HSession hSession) {
		MutilOperateOffRecyclebinC message = (MutilOperateOffRecyclebinC) hSession.httpPacket.getData();
		boolean result;
		if (message.getUserFoldIdsList().size() != 0) {
			result = UserFoldAction.updateUserFoldListState(message.getUserFoldIdsList(), UserFoldConfig.STATE_CAN_USE);
			if (!result) {
				return null;
			}
		}
		if (message.getUserFileIdsList().size() != 0) {
			result = UserFileAction.updateUserFileListState(message.getUserFileIdsList(), UserFileConfig.STATE_CAN_USE);
			if (!result) {
				return null;
			}
		}
		MutilOperateOffRecyclebinS.Builder builder = MutilOperateOffRecyclebinS.newBuilder();
		builder.setHOpCode(hSession.headParam.hOpCode);
		HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket mutliMoveToHandle(HSession hSession) {
		MutilOperateMoveToC message = (MutilOperateMoveToC) hSession.httpPacket.getData();
		boolean result;
		if (message.getUserFoldIdsList().size() != 0) {
			result = UserFoldAction.updateUserFoldListParentId(message.getUserFoldIdsList(), message.getUserFoldParentId());
			if (!result) {
				return null;
			}
		}
		if (message.getUserFileIdsList().size() != 0) {
			result = UserFileAction.updateUserFileListParentId(message.getUserFileIdsList(), message.getUserFoldParentId());
			if (!result) {
				return null;
			}
		}
		MutilOperateMoveToS.Builder builder = MutilOperateMoveToS.newBuilder();
		builder.setHOpCode(hSession.headParam.hOpCode);
		HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket mutliRemoveHandle(HSession hSession) {
		MutilOperateRemoveC message = (MutilOperateRemoveC) hSession.httpPacket.getData();
		boolean result;
		if (message.getUserFoldIdsList().size() != 0) {
			result = UserFoldAction.updateUserFoldListState(message.getUserFoldIdsList(), UserFoldConfig.STATE_DELETE);
			if (!result) {
				return null;
			}
		}
		if (message.getUserFileIdsList().size() != 0) {
			result = UserFileAction.updateUserFileListState(message.getUserFileIdsList(), UserFileConfig.STATE_DELETE);
			if (!result) {
				return null;
			}
		}
		MutilOperateRemoveS.Builder builder = MutilOperateRemoveS.newBuilder();
		builder.setHOpCode(hSession.headParam.hOpCode);
		HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
		return packet;
	}

	public HttpPacket mutliClearRecycleBinHandle(HSession hSession) {
		MutilOperateClearRecyclebinC message = (MutilOperateClearRecyclebinC) hSession.httpPacket.getData();
		boolean result = UserFoldAction.clearRecycleBin(message.getUserFoldTopId());
		if (!result) {
			return null;
		}
		result = UserFileAction.clearRecycleBin(message.getUserFoldTopId());
		if (!result) {
			return null;
		}
		MutilOperateClearRecyclebinS.Builder builder = MutilOperateClearRecyclebinS.newBuilder();
		builder.setHOpCode(hSession.headParam.hOpCode);
		HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
		return packet;
	}

}
