package http.filter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import action.UserAction;
import action.UserType2Action;
import http.HOpCodeBox;
import http.HSession;
import http.exception.HttpErrorException;
import protobuf.http.UserGroupProto.UserData;

public class TokenHttpFilter implements IHttpFilter {
	private Map<String, Integer> tokenTypeMap = new ConcurrentHashMap<>();

	@Override
	public boolean httpFilter(HSession hSession) throws HttpErrorException {
		if (hSession.headParam.token == null) {
			if (HOpCodeBox.LOGIN == hSession.headParam.hOpCode) {
				// 可以通过
				return true;
			} else {
				return false;
			}
		}
		UserData userData = null;
		Integer type = tokenTypeMap.get(hSession.headParam.token);
		if (type == null) {
			userData = UserAction.getUser(hSession.headParam.token);
			if (userData == null) {
				userData = UserType2Action.getUser(hSession.headParam.token);
				if (userData == null) {
					return false;
				} else {
					tokenTypeMap.put(hSession.headParam.token, 2);
				}
			} else {
				tokenTypeMap.put(hSession.headParam.token, 1);
			}
		} else if (type.intValue() == 1) {
			userData = UserAction.getUser(hSession.headParam.token);
			if (userData == null) {
				return false;
			}
		} else if (type.intValue() == 2) {
			userData = UserType2Action.getUser(hSession.headParam.token);
			if (userData == null) {
				return false;
			}
		}

		hSession.otherData = userData;
		return true;
	}

}
