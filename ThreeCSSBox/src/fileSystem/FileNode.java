// package fileSystem;
//
// import java.util.ArrayList;
//
// import dao.model.base.UserFile;
//
// public class FileNode extends FileSystemNode {
//
// // 文件系统节点的属性，<0表示是没有初始化的文件节点，0表示是文件节点，1表示是文件夹节点
// private int nodeType = -1;
// // 用户文件对象。与数据库字段对应
// private UserFile userFile;
// // 父节点，如果是顶级节点，则为空
// private FileSystemNode fatherNode;
//
// public FileNode(UserFile uFile) {
// userFile = uFile;
// nodeType = 0;
//
// }
//
// /**
// * 添加父节点
// */
// @Override
// public boolean addFatherNode(FileSystemNode fsNode) {
// fatherNode = fsNode;
// return true;
// }
//
// @Override
// public int getType() {
// return nodeType;
// }
//
// /**
// * 添加子节点 文件节点作为叶子节点不具备此功能。
// */
// @Override
// public boolean addNode(FileSystemNode fsNode) {
// return false;
// }
//
// /**
// * 批量添加子节点 文件节点作为叶子节点不具备此功能。
// */
// @Override
// public boolean addNodes(ArrayList<FileSystemNode> fsNodess) {
// return false;
// }
//
// /**
// * 节点入回收站
// */
// @Override
// public boolean recoverNode() {
// if (nodeType >= 0) {
// userFile.setUserFileState((byte) 2);
// userFile.setUserFileRecyclebinState((byte) 2);
// return true;
// }
// return false;
// }
//
// /**
// * 随着父节点一起进入回收站
// */
// @Override
// public boolean recoverNodeFollowFather() {
// if (nodeType >= 0) {
// userFile.setUserFileState((byte) 2);
// return true;
// }
// return false;
// }
//
// /**
// * 节点出回收站
// */
// @Override
// public boolean restoreNode() {
// if (nodeType >= 0) {
// userFile.setUserFileState((byte) 1);
// userFile.setUserFileRecyclebinState((byte) 1);
// return true;
// }
// return false;
// }
//
// /**
// * 节点跟随父节点出回收站
// */
// @Override
// public boolean restoreNodeFollowFather() {
// if (nodeType >= 0) {
// if (userFile.getUserFileRecyclebinState() == (byte) 1 &&
// userFile.getUserFileState() == (byte) 2) {
// userFile.setUserFileState((byte) 1);
// userFile.setUserFileRecyclebinState((byte) 1);
// }
// return true;
// }
// return false;
// }
//
// /**
// * 节点出回收站，要同步出站父节点
// */
// @Override
// public boolean restoreFatherNode() {
// fatherNode.restoreFatherNode();
// return true;
// }
//
// /**
// * 删除节点
// */
// @Override
// public boolean delNode() {
// if (nodeType == 0) {
// userFile.setUserFileState((byte) 3);
// return true;
// }
// return false;
// }
//
// /**
// * 跟随父节点一起被删除
// */
// @Override
// public boolean delNodeFollowFather() {
// if (nodeType == 0) {
// if (userFile.getUserFileRecyclebinState() != (byte) 2) {
// userFile.setUserFileState((byte) 3);
// return true;
// }
// }
// return false;
// }
//
// /**
// * 删除子节点 文件节点作为叶子节点不具备此功能
// */
// @Override
// public boolean delNode(String nodeID) {
// return false;
// }
//
// /**
// * 获取子节点 文件节点作为叶子节点不具备此功能
// */
// @Override
// public FileSystemNode getNode(String nodeID) {
// return null;
// }
//
// /**
// * 获取全部子节点 文件节点作为叶子节点不具备此功能
// */
// @Override
// public ArrayList<FileSystemNode> getAllNodes() {
// return null;
// }
//
// /**
// * 获取节点详情
// *
// * @return 用户文件对象
// */
// @Override
// public Object getNodeInfo() {
// return userFile;
// }
//
// /**
// * 是否是父节点 文件节点直接返回否
// */
// @Override
// public boolean isRoot() {
// return false;
// }
//
// /**
// * 为根节点添加全部节点 文件节点不具备此功能，直接返回否
// */
// @Override
// public boolean addNodeForRootNode(FileSystemNode fsNode) {
// return false;
// }
//
// /**
// * 获得节点的ID
// */
// @Override
// public String getNodeID() {
// if (nodeType == 0) {
// return userFile.getUserFileId();
// }
// return null;
// }
//
// /**
// * 获取节点当前的状态
// */
// @Override
// public byte getNodeState() {
// if (nodeType == 0) {
// return userFile.getUserFileState();
// }
// return -1;
// }
//
// /**
// * 获取当前节点的根节点ID
// */
// @Override
// public String getRootNodeID() {
// if (nodeType == 0) {
// return userFile.getUserFoldTopId();
// }
// return null;
// }
//
// /**
// * 设置当前节点的状态
// */
// @Override
// public boolean setNodeState(byte state) {
// if (nodeType == 0) {
// userFile.setUserFileState(state);
// return true;
// }
// return false;
// }
//
// /**
// * 文件作为叶子节点不具备此功能。
// */
// @Override
// public boolean creatFileSystemTree() {
// return false;
// }
//
// /**
// * 返回父节点的ID； 这个ID需要从自身取，不能从对象属性中的父节点中直接去ID，因为这个方法是为了构建文件树添加父节点用的
// */
// @Override
// public String getFatherNodeID() {
// if (nodeType == 0) {
// return userFile.getUserFoldParentId();
// }
// return null;
// }
//
// /**
// * 移动节点到目标节点下
// */
// @Override
// public boolean removeNode(FileSystemNode targetNode) {
// if (nodeType == 0) {
// fatherNode = targetNode;
// if (targetNode.isRoot()) {
// userFile.setUserFoldTopId(targetNode.getNodeID());
// } else {
// userFile.setUserFoldTopId(targetNode.getRootNodeID());
// }
// }
// return false;
// }
//
// /**
// * 跟随父节点移动
// */
// @Override
// public boolean removeNodeWithFather(FileSystemNode targetNode) {
// if (nodeType == 0) {
// if (targetNode.isRoot()) {
// userFile.setUserFoldTopId(targetNode.getNodeID());
// } else {
// userFile.setUserFoldTopId(targetNode.getRootNodeID());
// }
// }
// return false;
// }
//
// /**
// * 拷贝节点至目标节点
// */
// @Override
// public boolean copyNode(FileSystemNode targetNode) {
// // TODO Auto-generated method stub
// return false;
// }
//
// @Override
// public boolean copyNodeWhitFather(FileSystemNode targetNode) {
// // TODO Auto-generated method stub
// return false;
// }
//
// }
