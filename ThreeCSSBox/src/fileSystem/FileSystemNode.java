// package fileSystem;
//
// import java.util.ArrayList;
//
// import com.rabbitmq.client.AMQP.Basic.Publish;
//
// public abstract class FileSystemNode {
//
// public abstract Object getNodeInfo();
//
// //节点的状态
// public abstract int getType();
// public abstract boolean isRoot();
//
// //添加节点
// public abstract boolean addNode(FileSystemNode fsNode);
// public abstract boolean addNodes(ArrayList<FileSystemNode> fsNodess);
// public abstract boolean addNodeForRootNode(FileSystemNode fsNode);
// public abstract boolean addFatherNode(FileSystemNode fsNode);
//
// //节点入回收站
// public abstract boolean recoverNode();
// public abstract boolean recoverNodeFollowFather();
//
// //恢复节点
// public abstract boolean restoreNode();
// public abstract boolean restoreNodeFollowFather();
// public abstract boolean restoreFatherNode();
//
// //删除节点
// public abstract boolean delNode(String nodeID);
// public abstract boolean delNode();
// public abstract boolean delNodeFollowFather();
//
// //移动节点
// public abstract boolean removeNode(FileSystemNode targetNode);
// public abstract boolean removeNodeWithFather(FileSystemNode targetNode);
//
// //拷贝节点
// public abstract boolean copyNode(FileSystemNode targetNode);
// public abstract boolean copyNodeWhitFather(FileSystemNode targetNode);
//
// //获得节点
// public abstract FileSystemNode getNode(String nodeID);
// public abstract ArrayList<FileSystemNode> getAllNodes();
//
//
// //节点属性相关
// public abstract String getNodeID();
// public abstract byte getNodeState();
// public abstract boolean setNodeState(byte state);
// public abstract String getFatherNodeID();
// public abstract String getRootNodeID();
//
// //操作相关
// public abstract boolean creatFileSystemTree();
// }
