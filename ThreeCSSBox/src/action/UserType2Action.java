package action;

import config.CommonConfigBox;
import log.LogManager;
import net.sf.json.JSONObject;
import protobuf.http.UserGroupProto.UserData;
import tool.StringUtil;

public class UserType2Action {

	public static UserData getUser(String userId) {
		if (StringUtil.stringIsNull(userId)) {
			LogManager.httpLog.warn("获取用户信息参数userId为空，获取信息失败");
			return null;
		}
		String getUserUrl = CommonConfigBox.TJSMESP_URL + CommonConfigBox.GET_USER_MSG_BY_USER_CODEURL + userId;
		JSONObject result = HttpUtilToTjsAction.sendToTjsMesp(null, getUserUrl, null, null);
		JSONObject data = result.getJSONObject("data");
		int status = result.getInt("status");
		if (status != 1 || !data.containsKey("userMsg")) {
			LogManager.httpLog.warn("获取用户userId为" + userId + "的用户信息失败,status=" + status);
			return null;
		}
		JSONObject userMsg = data.getJSONObject("userMsg");
		UserData.Builder user = UserData.newBuilder();
		user.setUserId(StringUtil.blank(userMsg.getString("userCode")));
		user.setUserRealName(StringUtil.blank(userMsg.getString("name")));
		return user.build();
	}

}
