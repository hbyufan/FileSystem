package service;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import action.FileBaseAction;
import action.UserFileAction;
import config.CommonConfigBox;
import config.FileBaseConfig;
import dao.model.base.FileBase;
import dao.model.ext.UserFileExt;
import http.HOpCodeBox;
import http.HSession;
import http.HttpPacket;
import http.IHttpListener;
import http.filter.FileData;
import protobuf.http.UploadFileProto.MD5CheckC;
import protobuf.http.UploadFileProto.MD5CheckS;
import protobuf.http.UploadFileProto.UploadFileC;
import protobuf.http.UploadFileProto.UploadFileS;
import protobuf.http.UploadFileProto.UserFileDownloadC;
import protobuf.http.UserGroupProto.UserData;
import tool.StringUtil;

public class UploadService implements IHttpListener, IService {

	@Override
	public Map<Integer, String> getHttps() throws Exception {
		HashMap<Integer, String> map = new HashMap<>();
		map.put(HOpCodeBox.MD5_CHECK, "md5CheckHandle");
		map.put(HOpCodeBox.UPLOAD_FILE, "uploadFileHandle");
		map.put(HOpCodeBox.USERFILE_DOWNLOAD, "userFileDownloadHandle");
		return map;
	}

	@Override
	public Object getInstance() {
		return this;
	}

	public HttpPacket md5CheckHandle(HSession hSession) {
		MD5CheckC message = (MD5CheckC) hSession.httpPacket.getData();
		UserFileExt userFile = null;
		if (!StringUtil.stringIsNull(message.getUserFileId())) {
			userFile = UserFileAction.getUserFile(message.getUserFileId());
			if (userFile != null && userFile.getFileBase().getFileBaseState().intValue() == FileBaseConfig.STATE_COMPLETE) {
				MD5CheckS.Builder builder = MD5CheckS.newBuilder();
				builder.setHOpCode(hSession.headParam.hOpCode);
				builder.setResult(1);
				builder.setUserFileId(userFile.getUserFileId());
				HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
				return packet;
			}
		}
		UserData userData = (UserData) hSession.otherData;
		FileBase fileBase = FileBaseAction.getFileBaseByMd5(message.getFileBaseMd5());
		if (userFile == null) {
			userFile = UserFileAction.createUserFile(message.getUserFileName(), message.getUserFoldParentId(), userData.getUserId(), message.getFileBaseMd5(), message.getFileBaseTotalSize(), fileBase);
			if (userFile == null) {
				return null;
			}
			if (fileBase != null) {
				// 秒传
				MD5CheckS.Builder builder = MD5CheckS.newBuilder();
				builder.setHOpCode(hSession.headParam.hOpCode);
				builder.setResult(1);
				builder.setUserFileId(userFile.getUserFileId());
				HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
				return packet;
			} else {
				// 通知从哪开始传
				MD5CheckS.Builder builder = MD5CheckS.newBuilder();
				builder.setHOpCode(hSession.headParam.hOpCode);
				builder.setResult(2);
				builder.setUserFileId(userFile.getUserFileId());
				builder.setFileBasePos(userFile.getFileBase().getFileBasePos());
				builder.setUploadMaxLength(CommonConfigBox.UPLOAD_MAX_LENGTH);
				HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
				return packet;
			}
		} else {
			if (fileBase != null) {
				// 更新，然后秒传
				boolean result = UserFileAction.changeFileBase(userFile.getUserFileId(), fileBase.getFileBaseId());
				if (!result) {
					return null;
				}
				MD5CheckS.Builder builder = MD5CheckS.newBuilder();
				builder.setHOpCode(hSession.headParam.hOpCode);
				builder.setResult(1);
				builder.setUserFileId(userFile.getUserFileId());
				HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
				return packet;
			} else {
				// 通知从哪开始传
				MD5CheckS.Builder builder = MD5CheckS.newBuilder();
				builder.setHOpCode(hSession.headParam.hOpCode);
				builder.setResult(2);
				builder.setUserFileId(userFile.getUserFileId());
				builder.setFileBasePos(userFile.getFileBase().getFileBasePos());
				builder.setUploadMaxLength(CommonConfigBox.UPLOAD_MAX_LENGTH);
				HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
				return packet;
			}
		}
	}

	public HttpPacket uploadFileHandle(HSession hSession) {
		UploadFileC message = (UploadFileC) hSession.httpPacket.getData();
		UserFileExt userFile = UserFileAction.getUserFile(message.getUserFileId());
		if (userFile == null) {
			return null;
		}
		// 位置不对
		if (message.getFileBasePos() != userFile.getFileBase().getFileBasePos().longValue()) {
			return null;
		}
		// 不能大于默认长度
		if (message.getUploadLength() > CommonConfigBox.UPLOAD_MAX_LENGTH) {
			return null;
		}
		// 没有文件
		if (hSession.fileList == null || hSession.fileList.size() == 0) {
			return null;
		}
		FileData fileData = hSession.fileList.get(0);
		// 文件长度与消息长度不符
		if ((int) fileData.getFile().length() != message.getUploadLength()) {
			return null;
		}
		// 不存在这个文件
		File file = UserFileAction.getFile(userFile.getFileBase().getFileBaseRealPath());
		if (file == null) {
			return null;
		}
		if (file.length() != message.getFileBasePos()) {
			return null;
		}
		Date date = new Date();
		// 是否在规定时间后上传
		if (date.getTime() < userFile.getFileBase().getFileBaseNextUploadTime().getTime()) {
			return null;
		}
		FileBase fileBase = FileBaseAction.getFileBaseByMd5(userFile.getFileBase().getFileBaseMd5());
		if (fileBase != null) {
			// 更新，然后秒传
			boolean result = UserFileAction.changeFileBase(userFile.getUserFileId(), fileBase.getFileBaseId());
			if (!result) {
				return null;
			}
			UploadFileS.Builder builder = UploadFileS.newBuilder();
			builder.setHOpCode(hSession.headParam.hOpCode);
			builder.setResult(1);
			builder.setUserFileId(userFile.getUserFileId());
			HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
			return packet;
		}
		boolean result = UserFileAction.updateFile(file, fileData.getFile());
		if (!result) {
			return null;
		}
		result = UserFileAction.updateUserFile(userFile, message.getUploadLength());
		if (!result) {
			return null;
		}
		if ((message.getFileBasePos() + message.getUploadLength()) == userFile.getFileBase().getFileBaseTotalSize().longValue()) {
			UploadFileS.Builder builder = UploadFileS.newBuilder();
			builder.setHOpCode(hSession.headParam.hOpCode);
			builder.setResult(3);
			builder.setUserFileId(userFile.getUserFileId());
			HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
			return packet;
		} else {
			UploadFileS.Builder builder = UploadFileS.newBuilder();
			builder.setHOpCode(hSession.headParam.hOpCode);
			builder.setResult(2);
			builder.setUserFileId(userFile.getUserFileId());
			builder.setWaitTime(CommonConfigBox.WAIT_TIME);
			builder.setUploadMaxLength(CommonConfigBox.UPLOAD_MAX_LENGTH);
			builder.setFileBasePos((message.getFileBasePos() + message.getUploadLength()));
			HttpPacket packet = new HttpPacket(hSession.headParam.hOpCode, builder.build());
			return packet;
		}
	}

	public FileData userFileDownloadHandle(HSession hSession) {
		UserFileDownloadC message = (UserFileDownloadC) hSession.httpPacket.getData();
		UserFileExt userFile = UserFileAction.getUserFileComplete(message.getUserFileId());
		if (userFile == null) {
			return null;
		}
		File file = UserFileAction.getFile(userFile.getFileBase().getFileBaseRealPath());
		if (file == null) {
			return null;
		}
		FileData fileData = new FileData(file, userFile.getUserFileName());
		return fileData;
	}

	@Override
	public void init() throws Exception {
		UserFileAction.createFileBaseDir();
	}

}
