// package fileSystem;
//
// import java.util.ArrayList;
//
// import dao.model.base.UserFold;
//
// public class FolderNode extends FileSystemNode {
//
// // 用户的文件夹对象，与数据库字段对应
// private UserFold userFold;
// // 文件系统节点的属性，<0表示是没有初始化的文件节点，0表示是文件节点，1表示是文件夹节点
// private int nodeType = -1;
// // 文件系统子节点
// private ArrayList<FileSystemNode> nodes = new ArrayList<FileSystemNode>();
// // 根节点持有文件系统所有子节点。
// private ArrayList<FileSystemNode> allNodes = new ArrayList<FileSystemNode>();
// // 父节点，如果是顶级节点，则为空
// private FileSystemNode fatherNode;
//
// private boolean isRootNode = true;
//
// public FolderNode(UserFold uFold) {
// userFold = uFold;
// nodeType = 1;
// }
//
// /**
// * 添加父节点
// */
// @Override
// public boolean addFatherNode(FileSystemNode fsNode) {
// fatherNode = fsNode;
// isRootNode = false;
// return true;
// }
//
// @Override
// public int getType() {
// return nodeType;
// }
//
// /**
// * 添加子节点
// *
// * @param fsNode
// * 本节点的子节点
// */
// @Override
// public boolean addNode(FileSystemNode fsNode) {
// nodes.add(fsNode);
// return true;
// }
//
// /**
// * 批量添加子节点
// */
// @Override
// public boolean addNodes(ArrayList<FileSystemNode> fsNodess) {
// nodes.addAll(fsNodess);
// return true;
// }
//
// /**
// * 子节点入回收站
// */
// @Override
// public boolean recoverNode() {
// if (nodeType >= 0) {
// userFold.setUserFoldState((byte) 2);
// userFold.setUserFoldRecyclebinState((byte) 2);
// for (FileSystemNode fileSystemNode : nodes) {
// fileSystemNode.recoverNodeFollowFather();
// }
// return true;
// }
// return false;
//
// }
//
// /**
// * 跟随父节点一同进入回收站
// */
// @Override
// public boolean recoverNodeFollowFather() {
// if (nodeType >= 0) {
// userFold.setUserFoldState((byte) 2);
// for (FileSystemNode fileSystemNode : nodes) {
// fileSystemNode.recoverNodeFollowFather();
// }
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
// userFold.setUserFoldState((byte) 1);
// userFold.setUserFoldRecyclebinState((byte) 1);
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
// if (userFold.getUserFoldState() == (byte) 1) {
// userFold.setUserFoldRecyclebinState((byte) 1);
// for (FileSystemNode fileSystemNode : nodes) {
// fileSystemNode.restoreNodeFollowFather();
// }
// }
// return true;
// }
// return false;
// }
//
// /**
// * 节点出回收站同步出站父节点
// */
// @Override
// public boolean restoreFatherNode() {
// if (fatherNode != null) {
// ((UserFold) fatherNode.getNodeInfo()).setUserFoldState((byte) 1);
// fatherNode.restoreFatherNode();
// }
// return true;
// }
//
// /**
// * 删除本节点
// */
// @Override
// public boolean delNode() {
// if (nodeType == 1) {
// userFold.setUserFoldState((byte) 3);
// for (FileSystemNode fileSystemNode : nodes) {
// fileSystemNode.delNodeFollowFather();
// }
// }
// return true;
// }
//
// /**
// * 跟随父节点一起被删除，要递归下去，所有子节点（自身在回收站的除外）全部删除
// */
// @Override
// public boolean delNodeFollowFather() {
// if (nodeType == 1) {
// if (userFold.getUserFoldRecyclebinState() != (byte) 2) {
// userFold.setUserFoldState((byte) 3);
// for (FileSystemNode fileSystemNode : nodes) {
// fileSystemNode.delNodeFollowFather();
// }
// }
// return true;
// }
// return false;
// }
//
// /**
// * 根据节点ID删除节点，根节点独有方法
// */
// @Override
// public boolean delNode(String nodeID) {
// if (isRootNode && nodeType == 1) {
// for (FileSystemNode fileSystemNode : allNodes) {
// String ID = fileSystemNode.getNodeID();
// if (nodeID.equals(ID)) {
// return fileSystemNode.delNode();
// }
// }
// }
// return false;
// }
//
// /**
// * 获取节点 根节点专属方法。
// */
// @Override
// public FileSystemNode getNode(String nodeID) {
// if (nodeType == 1) {
// String ID;
// if (isRootNode) {
// for (FileSystemNode fileSystemNode : allNodes) {
// ID = fileSystemNode.getNodeID();
// if (nodeID.equals(ID)) {
// return fileSystemNode;
// }
// }
// }
// }
// return null;
// }
//
// /**
// * 获取当前节点下的所有子节点
// */
// @Override
// public ArrayList<FileSystemNode> getAllNodes() {
// if (nodeType == 1) {
// return nodes;
// }
// return null;
// }
//
// @Override
// public Object getNodeInfo() {
// return userFold;
// }
//
// @Override
// public boolean isRoot() {
// return isRootNode;
// }
//
// /**
// * 为根节点添加全部子节点 根节点持有全部子节点主要是为了操作方便
// */
// @Override
// public boolean addNodeForRootNode(FileSystemNode fsNode) {
// allNodes.add(fsNode);
// return true;
// }
//
// /**
// * 获取节点的ID
// */
// @Override
// public String getNodeID() {
// if (nodeType == 1) {
// return userFold.getUserFoldId();
// }
// return null;
// }
//
// /**
// * 获取节点当前的状态
// */
// @Override
// public byte getNodeState() {
// if (nodeType == 1) {
// return userFold.getUserFoldState();
// }
// return -1;
// }
//
// /**
// * 设置当前节点的状态
// */
// @Override
// public boolean setNodeState(byte state) {
// if (nodeType == 1) {
// userFold.setUserFoldState(state);
// return true;
// }
// return false;
// }
//
// /**
// * 构建文件系统树 只有根节点具备此功能
// */
// @Override
// public boolean creatFileSystemTree() {
// for (FileSystemNode fileSystemNode : allNodes) {
// String fatherNodeID = fileSystemNode.getFatherNodeID();
// if (!fatherNodeID.equals(null)) {
// FileSystemNode fNode = getNode(fatherNodeID);
// fileSystemNode.addFatherNode(fNode);
// fNode.addNode(fileSystemNode);
// }
// }
// return true;
// }
//
// /**
// * 返回父节点的ID； 这个ID需要从自身取，不能从对象属性中的父节点中直接去ID，因为这个方法是为了构建文件树添加父节点用的
// */
// @Override
// public String getFatherNodeID() {
// if (nodeType == 1) {
// return userFold.getUserFoldParentId();
// }
// return null;
// }
//
// /**
// * 获取跟节点的ID
// */
// @Override
// public String getRootNodeID() {
// if (nodeType == 1) {
// if (isRootNode) {
// return userFold.getUserFoldId();
// } else {
// return userFold.getUserFoldTopId();
// }
// }
// return null;
// }
//
// /**
// * 移动本节点到目标节点
// */
// @Override
// public boolean removeNode(FileSystemNode targetNode) {
// if (nodeType == 1) {
// fatherNode = targetNode;
// if (!getRootNodeID().equals(targetNode.getRootNodeID())) {
// userFold.setUserFoldTopId(targetNode.getRootNodeID());
// for (FileSystemNode fileSystemNode : nodes) {
// fileSystemNode.removeNodeWithFather(targetNode);
// }
// }
// }
// return false;
// }
//
// /**
// * 跟随父节点同步移动到目标节点
// */
// @Override
// public boolean removeNodeWithFather(FileSystemNode targetNode) {
// if (nodeType == 1) {
// userFold.setUserFoldTopId(targetNode.getRootNodeID());
// for (FileSystemNode fileSystemNode : nodes) {
// fileSystemNode.removeNodeWithFather(targetNode);
// }
// }
// return false;
// }
//
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
