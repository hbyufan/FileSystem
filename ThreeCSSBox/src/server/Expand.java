package server;

import java.util.TimeZone;

import config.CommonConfigBox;
import http.HOpCodeBox;
import http.HOpCodeUCenter;
import init.IExpand;
import init.Init;
import service.BoxInfoService;
import service.LoginService;
import service.MutliOperateService;
import service.UploadService;
import service.UserFileService;
import service.UserFoldService;

public class Expand implements IExpand {

	@Override
	public void init() throws Exception {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
		HOpCodeBox.init();
		CommonConfigBox.init();
		HOpCodeUCenter.init();
		Init.registerService(UploadService.class);
		Init.registerService(LoginService.class);
		Init.registerService(UserFoldService.class);
		Init.registerService(UserFileService.class);
		Init.registerService(MutliOperateService.class);
		Init.registerService(BoxInfoService.class);
	}

	@Override
	public void threadInit() throws Exception {

	}

}
