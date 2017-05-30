package action;

import config.CommonConfigBox;
import http.AllowParam;
import http.HOpCodeUCenter;
import http.HttpPacket;
import http.HttpUtil;
import protobuf.http.UCErrorProto.UCError;
import protobuf.http.UserGroupProto.GetUserC;
import protobuf.http.UserGroupProto.GetUserS;
import protobuf.http.UserGroupProto.UserData;

public class UserAction {
	public static UserData getUser(String token) {
		GetUserC.Builder builder = GetUserC.newBuilder();
		builder.setHOpCode(HOpCodeUCenter.GET_USER);
		HttpPacket httpPacket = new HttpPacket(HOpCodeUCenter.GET_USER, builder.build());
		HttpPacket returnHttpPacket = HttpUtil.send(httpPacket, CommonConfigBox.UCENTER_URL, AllowParam.SEND_TYPE_PROTOBUF, AllowParam.RECEIVE_TYPE_PROTOBUF, token);
		if (returnHttpPacket != null) {
			// 如果返回的是错误类型,说明用户中心拦截器没通过
			if ((returnHttpPacket.getData()) instanceof UCError) {
				return null;
			}
			GetUserS message = (GetUserS) returnHttpPacket.getData();
			return message.getUser();
		}
		return null;
	}
}
