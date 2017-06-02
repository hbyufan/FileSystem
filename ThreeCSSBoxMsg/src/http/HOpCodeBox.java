package http;

import protobuf.http.BoxErrorProto.BoxErrorS;
import protobuf.http.BoxInfoProto.BoxInfoC;
import protobuf.http.BoxInfoProto.BoxInfoS;
import protobuf.http.LoginProto.LoginC;
import protobuf.http.LoginProto.LoginS;
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
import protobuf.http.UploadFileProto.MD5CheckC;
import protobuf.http.UploadFileProto.MD5CheckS;
import protobuf.http.UploadFileProto.UploadFileC;
import protobuf.http.UploadFileProto.UploadFileS;
import protobuf.http.UploadFileProto.UserFileDownloadC;
import protobuf.http.UserFoldProto.CreateUserFoldC;
import protobuf.http.UserFoldProto.CreateUserFoldS;
import protobuf.http.UserFoldProto.GetRecycleBinListC;
import protobuf.http.UserFoldProto.GetRecycleBinListS;
import protobuf.http.UserFoldProto.GetUserFoldChildrenC;
import protobuf.http.UserFoldProto.GetUserFoldChildrenS;
import protobuf.http.UserFoldProto.GetUserFoldChildrenUserFoldC;
import protobuf.http.UserFoldProto.GetUserFoldChildrenUserFoldS;
import protobuf.http.UserFoldProto.UpdateUserFileC;
import protobuf.http.UserFoldProto.UpdateUserFileS;
import protobuf.http.UserFoldProto.UpdateUserFoldC;
import protobuf.http.UserFoldProto.UpdateUserFoldS;

public class HOpCodeBox extends HOpCode {
	public static int BOX_ERROR = 49999;
	public static int MD5_CHECK = 50000;
	public static int UPLOAD_FILE = 50001;
	public static int LOGIN = 50002;
	public static int CREATE_USERFOLD = 50003;
	public static int UPDATE_USERFOLD = 50004;
	public static int GET_USERFOLD_CHILDREN = 50005;
	public static int GET_RECYCLEBIN_LIST = 50006;
	public static int UPDATE_USERFILE = 50007;
	public static int USERFILE_DOWNLOAD = 50008;
	public static int MUTLI_TO_RECYCLEBIN = 50009;
	public static int MUTLI_OFF_RECYCLEBIN = 50010;
	public static int MUTLI_MOVE_TO = 50011;
	public static int MUTLI_REMOVE = 50012;
	public static int MUTLI_CLEAR_RECYCLEBIN = 50013;
	public static int GET_USERFOLD_CHILDREN_USERFOLD = 50014;
	public static int GET_BOX_INFO = 50015;

	public static void init() {
		Class<?>[] sendAndReturn = new Class[2];
		sendAndReturn[0] = null;
		sendAndReturn[1] = BoxErrorS.class;
		hOpCodeMap.put(BOX_ERROR, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = MD5CheckC.class;
		sendAndReturn[1] = MD5CheckS.class;
		hOpCodeMap.put(MD5_CHECK, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = UploadFileC.class;
		sendAndReturn[1] = UploadFileS.class;
		hOpCodeMap.put(UPLOAD_FILE, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = LoginC.class;
		sendAndReturn[1] = LoginS.class;
		hOpCodeMap.put(LOGIN, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = CreateUserFoldC.class;
		sendAndReturn[1] = CreateUserFoldS.class;
		hOpCodeMap.put(CREATE_USERFOLD, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = UpdateUserFoldC.class;
		sendAndReturn[1] = UpdateUserFoldS.class;
		hOpCodeMap.put(UPDATE_USERFOLD, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = GetUserFoldChildrenC.class;
		sendAndReturn[1] = GetUserFoldChildrenS.class;
		hOpCodeMap.put(GET_USERFOLD_CHILDREN, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = GetRecycleBinListC.class;
		sendAndReturn[1] = GetRecycleBinListS.class;
		hOpCodeMap.put(GET_RECYCLEBIN_LIST, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = UpdateUserFileC.class;
		sendAndReturn[1] = UpdateUserFileS.class;
		hOpCodeMap.put(UPDATE_USERFILE, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = UserFileDownloadC.class;
		sendAndReturn[1] = null;
		hOpCodeMap.put(USERFILE_DOWNLOAD, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = MutilOperateToRecyclebinC.class;
		sendAndReturn[1] = MutilOperateToRecyclebinS.class;
		hOpCodeMap.put(MUTLI_TO_RECYCLEBIN, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = MutilOperateOffRecyclebinC.class;
		sendAndReturn[1] = MutilOperateOffRecyclebinS.class;
		hOpCodeMap.put(MUTLI_OFF_RECYCLEBIN, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = MutilOperateMoveToC.class;
		sendAndReturn[1] = MutilOperateMoveToS.class;
		hOpCodeMap.put(MUTLI_MOVE_TO, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = MutilOperateRemoveC.class;
		sendAndReturn[1] = MutilOperateRemoveS.class;
		hOpCodeMap.put(MUTLI_REMOVE, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = MutilOperateClearRecyclebinC.class;
		sendAndReturn[1] = MutilOperateClearRecyclebinS.class;
		hOpCodeMap.put(MUTLI_CLEAR_RECYCLEBIN, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = GetUserFoldChildrenUserFoldC.class;
		sendAndReturn[1] = GetUserFoldChildrenUserFoldS.class;
		hOpCodeMap.put(GET_USERFOLD_CHILDREN_USERFOLD, sendAndReturn);

		sendAndReturn = new Class[2];
		sendAndReturn[0] = BoxInfoC.class;
		sendAndReturn[1] = BoxInfoS.class;
		hOpCodeMap.put(GET_BOX_INFO, sendAndReturn);

	}
}
