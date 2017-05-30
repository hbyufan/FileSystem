// package action;
//
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
//
// import org.apache.ibatis.session.SqlSession;
//
// import dao.dao.base.UserFileMapper;
// import dao.dao.base.UserFoldMapper;
// import dao.model.base.UserFile;
// import dao.model.base.UserFileCriteria;
// import dao.model.base.UserFold;
// import dao.model.base.UserFoldCriteria;
// import fileSystem.FileNode;
// import fileSystem.FileSystemNode;
// import fileSystem.FolderNode;
// import keylock.BoxKeyLockType;
// import keylock.KeyLockManager;
// import log.LogManager;
// import mbatis.MybatisManager;
//
// public class FileSystemAction {
//
// // 保存已构建成功的用户树，此处作为一个临时策略，将来这玩意要放到共享内存中去的。
// public static Map<String, FileSystemNode> userFileSystemTreeMap = new
// HashMap<String, FileSystemNode>();
//
// /**
// * 根据userID获取用户的文件系统树，如果已经构建过，则从内存（或共享内存）中获取，否则直接构建一根
// *
// * @param userID
// * @return
// */
// public static FileSystemNode getFileSystemTree(String userID) {
// FileSystemNode userFileTree = null;
// // 首先试图从已存在的文件系统树中获得已构建的文件树。
// userFileTree = getExistFileSystemTree(userID);
// if (userFileTree == null) { // 如果文件树并没有被构建，则重新构建
// SqlSession sqlSession = null;
// List<UserFold> userFolds = null;
// FileSystemNode topNode = null;
//
// UserFoldCriteria userFoldCriteria = new UserFoldCriteria();
// UserFoldCriteria.Criteria foldCriteria = userFoldCriteria.createCriteria();
// foldCriteria.andCreateUserIdEqualTo(userID);
//
// try {
// // 在数据库中查询出所有用户的文件夹信息
// sqlSession = MybatisManager.getSqlSession();
// UserFoldMapper userFoldMapper = sqlSession.getMapper(UserFoldMapper.class);
// userFolds = userFoldMapper.selectByExample(userFoldCriteria);
//
// // 找到并构建顶级节点
// UserFold topFold = null;
// for (UserFold userFold : userFolds) {
// if (userFold.getUserFoldParentId() == null) {
// topFold = userFold;
// break;
// }
// }
// userFolds.remove(topFold);
// topNode = new FolderNode(topFold);
//
// // 锁住文件树根节点，并且开始构建文件树
// userFileTree = (FileSystemNode)
// KeyLockManager.lockMethod(topNode.getNodeID(),
// BoxKeyLockType.FILESYSTEM_TREE_ROOT, (params) -> creatFileSystemTree(params),
// new Object[] { userID });
//
// userFileSystemTreeMap.put(userID, userFileTree);
// } catch (Exception e) {
// LogManager.mariadbLog.error("查询用户文件或文件夹失败");
// } finally {
// if (sqlSession != null) {
// sqlSession.close();
// }
// }
// }
//
// return userFileTree;
// }
//
// /**
// * 获取已经构建完成保存在XXXX（暂时是内存中）的文件系统树。
// *
// * @param userID
// * @return
// */
// private static FileSystemNode getExistFileSystemTree(String userID) {
// return userFileSystemTreeMap.get(userID);
// }
//
// /**
// * 创建用户的文件系统树
// *
// * @param UserID
// * 用户的ID
// * @return 已经构建好的树的根节点
// */
// private static FileSystemNode creatFileSystemTree(Object... params) {
// String UserID = (String) params[0];
// SqlSession sqlSession = null;
// List<UserFile> userFiles = null;
// List<UserFold> userFolds = null;
// FileSystemNode topNode = null;
//
// UserFileCriteria userFileCriteria = new UserFileCriteria();
// UserFileCriteria.Criteria fileCriteria = userFileCriteria.createCriteria();
// fileCriteria.andCreateUserIdEqualTo(UserID);
//
// UserFoldCriteria userFoldCriteria = new UserFoldCriteria();
// UserFoldCriteria.Criteria foldCriteria = userFoldCriteria.createCriteria();
// foldCriteria.andCreateUserIdEqualTo(UserID);
//
// try {
// // 在数据库中查询出所有用户的文件夹信息
// sqlSession = MybatisManager.getSqlSession();
// UserFoldMapper userFoldMapper = sqlSession.getMapper(UserFoldMapper.class);
// userFolds = userFoldMapper.selectByExample(userFoldCriteria);
//
// // 找到并构建顶级节点
// UserFold topFold = null;
// for (UserFold userFold : userFolds) {
// if (userFold.getUserFoldParentId() == null) {
// topFold = userFold;
// break;
// }
// }
// userFolds.remove(topFold);
// topNode = new FolderNode(topFold);
//
// // 向顶级节点中添加文件夹节点
// for (UserFold userFold : userFolds) {
// FileSystemNode foldNode = new FolderNode(userFold);
// topNode.addNodeForRootNode(foldNode);
// }
//
// // 查询出用户所有的文件信息
// // sqlSession = MybatisManager.getSqlSession();
// UserFileMapper userFileMapper = sqlSession.getMapper(UserFileMapper.class);
// userFiles = userFileMapper.selectByExample(userFileCriteria);
// for (UserFile userFile : userFiles) {
// FileSystemNode fileSystemNode = new FileNode(userFile);
// topNode.addNodeForRootNode(fileSystemNode);
// }
//
// // 构建文件系统树
// topNode.creatFileSystemTree();
//
// } catch (Exception e) {
// LogManager.mariadbLog.error("查询用户文件或文件夹失败");
// } finally {
// if (sqlSession != null) {
// sqlSession.close();
// }
// }
//
// return topNode;
// }
//
// }
