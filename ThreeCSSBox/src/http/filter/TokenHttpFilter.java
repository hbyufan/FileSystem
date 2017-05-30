package http.filter;

import action.UserAction;
import http.HOpCodeBox;
import http.HSession;
import http.exception.HttpErrorException;
import protobuf.http.UserGroupProto.UserData;

public class TokenHttpFilter implements IHttpFilter {

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
		UserData userData = UserAction.getUser(hSession.headParam.token);
		if (userData == null) {
			return false;
		}
		hSession.otherData = userData;
		return true;
	}

}
