package server;

import config.CommonConfigBox;
import http.HOpCodeBox;
import http.HOpCodeUCenter;
import init.IExpand;
import init.Init;
import service.LoginService;
import service.MutliOperateService;
import service.UploadService;
import service.UserFileService;
import service.UserFoldService;

public class Expand implements IExpand {

	@Override
	public void init() throws Exception {
		HOpCodeBox.init();
		CommonConfigBox.init();
		HOpCodeUCenter.init();
		Init.registerService(UploadService.class);
		Init.registerService(LoginService.class);
		Init.registerService(UserFoldService.class);
		Init.registerService(UserFileService.class);
		Init.registerService(MutliOperateService.class);
	}

	@Override
	public void threadInit() throws Exception {

	}

}
