package config;

import config.ConfigManager.JsonConfigType;
import log.LogManager;
import net.sf.json.JSONObject;

public class CommonConfigBox extends CommonConfig {
	// 用户中心url
	public static String UCENTER_URL;
	// 是否使用hdfs
	public static boolean IS_USE_HDFS;
	// 基础文件路径
	public static String FILE_BASE_PATH;
	// 最大上传长度
	public static int UPLOAD_MAX_LENGTH;
	// 客户端下一次上传文件间隔
	public static int WAIT_TIME;
	// 一次性写入文件的大小
	public static int ONCE_WRITE_FILE_SIZE;

	public static void init() {
		LogManager.initLog.info("初始化CommonConfigBox");
		JSONObject configExt = ConfigManager.getJsonData(JsonConfigType.CONFIGEXT.getTypeValue());
		UCENTER_URL = configExt.getJSONArray("uCenterUrl").getString(0);
		IS_USE_HDFS = configExt.getJSONArray("isUseHDFS").getBoolean(0);
		FILE_BASE_PATH = configExt.getJSONArray("fileBasePath").getString(0);
		UPLOAD_MAX_LENGTH = configExt.getJSONArray("uploadMaxLength").getInt(0);
		WAIT_TIME = configExt.getJSONArray("waitTime").getInt(0);
		ONCE_WRITE_FILE_SIZE = configExt.getJSONArray("onceWriteFileSize").getInt(0);
		LogManager.initLog.info("初始化CommonConfigBox完成");
	}

}
