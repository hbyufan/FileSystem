package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import log.LogManager;

public class HDFSUtil {
	private static Configuration configuration;
	private static FileSystem fileSystem;

	public static void init() {
		try {
			configuration = new Configuration();
			fileSystem = FileSystem.get(configuration);
		} catch (Exception e) {
			LogManager.initLog.error("HdfsUtil链接失败", e);
		}

	}

	public static boolean mkdirs(String pathStr) {
		try {
			Path path = new Path(pathStr);
			boolean result = fileSystem.mkdirs(path);
			return result;
		} catch (Exception e) {
			LogManager.initLog.error("mkdirs path:" + pathStr, e);
			return false;
		}
	}

	public static boolean append(String pathStr, InputStream in) throws Exception {
		FSDataOutputStream newOut = null;
		try {
			Path path = new Path(pathStr);
			if (!fileSystem.exists(path)) {
				LogManager.initLog.warn("append 未获取文件流path：" + pathStr);
				return false;
			}
			newOut = fileSystem.append(path);
			byte[] buffer = new byte[65536];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				newOut.write(buffer, 0, bytesRead);
			}
			newOut.flush();
			return true;
		} catch (Exception e) {
			LogManager.initLog.error("append error path:" + pathStr, e);
			return false;
		} finally {
			if (newOut != null) {
				try {
					newOut.close();
				} catch (Exception e2) {
					LogManager.initLog.error("关闭输出流失败 path:" + pathStr, e2);
				}
			}
		}
	}

	public static FSDataOutputStream append(String pathStr, InputStream in, FSDataOutputStream oldOut) throws Exception {
		FSDataOutputStream newOut = oldOut;
		try {
			if (newOut == null) {
				Path path = new Path(pathStr);
				if (!fileSystem.exists(path)) {
					LogManager.initLog.warn("append 未获取文件流path：" + pathStr);
					return null;
				}
				newOut = fileSystem.append(path);
			}
			byte[] buffer = new byte[65536];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				newOut.write(buffer, 0, bytesRead);
			}
			newOut.flush();
			return newOut;
		} catch (Exception e) {
			LogManager.initLog.error("append error path:" + pathStr, e);
			if (newOut != null) {
				try {
					newOut.close();
				} catch (Exception e2) {
					LogManager.initLog.error("关闭输出流失败 path:" + pathStr, e2);
				}
			}
			return null;
		}

	}

	public static FSDataOutputStream getOutPutCreate(String pathStr) throws Exception {
		FSDataOutputStream newOut = null;
		try {
			Path path = new Path(pathStr);
			if (fileSystem.exists(path)) {
				LogManager.initLog.warn("getOutPutCreate 已存在文件path：" + pathStr);
				return null;
			}
			newOut = fileSystem.create(path);
			return newOut;
		} catch (Exception e) {
			LogManager.initLog.error("getOutPutCreate error path:" + pathStr, e);
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		//初始化
		HDFSUtil.init();
		//创建文件夹
		StringBuilder pathStr = new StringBuilder();
		pathStr.append(File.separator);
		pathStr.append("box_company");
		HDFSUtil.mkdirs(pathStr.toString());
		//创建文件
		File file = new File("C:\\Users\\admin\\Desktop\\github\\deployment-server\\trunk\\python\\Python-3.6.2.tgz");
		InputStream stream = new FileInputStream(file);
		pathStr.append(File.separator);
		pathStr.append("Python-3.6.2.tgz");
		FSDataOutputStream fs = HDFSUtil.getOutPutCreate(pathStr.toString());
		//追加进去
		HDFSUtil.append(pathStr.toString(), stream, fs);
	}

}
