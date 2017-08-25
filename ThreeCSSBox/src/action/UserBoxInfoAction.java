package action;

import org.apache.ibatis.session.SqlSession;

import dao.dao.base.UserBoxinfoMapper;
import dao.model.base.UserBoxinfo;
import log.LogManager;
import mbatis.MybatisManager;
import tool.StringUtil;

public class UserBoxInfoAction {
	public static UserBoxinfo createUserBoxInfo(String userId, int boxSizeOffset) {
		if (StringUtil.stringIsNull(userId)) {
			return null;
		}
		UserBoxinfo userBoxInfo = new UserBoxinfo();
		userBoxInfo.setUserId(userId);
		userBoxInfo.setBoxSizeOffset(boxSizeOffset);
		SqlSession sqlSession = null;
		try {
			sqlSession = MybatisManager.getSqlSession();
			UserBoxinfoMapper userBoxinfoMapper = sqlSession.getMapper(UserBoxinfoMapper.class);
			int result = userBoxinfoMapper.insert(userBoxInfo);
			if (result != 1) {
				LogManager.mariadbLog.warn("创建UserBoxinfo失败");
				return null;
			}
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			LogManager.mariadbLog.error("创建UserBoxinfo失败", e);
			return null;
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return userBoxInfo;
	}

	public static UserBoxinfo updateUserBoxinfo(String userId, int boxSizeOffset) {
		if (StringUtil.stringIsNull(userId)) {
			return null;
		}
		UserBoxinfo userBoxinfo = getUserBoxinfoById(userId);
		if (userBoxinfo == null) {
			return null;
		}
		UserBoxinfo userBoxinfoNew = new UserBoxinfo();
		userBoxinfoNew.setUserId(userId);
		userBoxinfoNew.setBoxSizeOffset(boxSizeOffset);
		SqlSession sqlSession = null;
		try {
			sqlSession = MybatisManager.getSqlSession();
			UserBoxinfoMapper userBoxinfoMapper = sqlSession.getMapper(UserBoxinfoMapper.class);
			int result = userBoxinfoMapper.updateByPrimaryKeySelective(userBoxinfoNew);
			if (result != 1) {
				LogManager.mariadbLog.warn("修改UserBoxinfo失败");
				return null;
			}
			sqlSession.commit();
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			LogManager.mariadbLog.error("修改app异常", e);
			return null;
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return getUserBoxinfoById(userBoxinfoNew.getUserId());
	}

	public static UserBoxinfo getUserBoxinfoById(String userId) {
		if (StringUtil.stringIsNull(userId)) {
			return null;
		}
		SqlSession sqlSession = null;
		UserBoxinfo userBoxinfo;
		try {
			sqlSession = MybatisManager.getSqlSession();
			UserBoxinfoMapper userBoxinfoMapper = sqlSession.getMapper(UserBoxinfoMapper.class);
			userBoxinfo = userBoxinfoMapper.selectByPrimaryKey(userId);
			if (userBoxinfo == null) {
				LogManager.mariadbLog.warn("通过userId:" + userId + "获取UserBoxinfo为空");
			}
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			LogManager.mariadbLog.error("获取UserBoxinfo异常", e);
			return null;
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return userBoxinfo;
	}
}
