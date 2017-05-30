package keylock;

public class BoxKeyLockType implements IKeyLockType {

	// 文件系统树的根节点
	public static String FILESYSTEM_TREE_ROOT = "FILESYSTEM_TREE_ROOT";

	@Override
	public String[] getkeyLockType() {
		return new String[] { FILESYSTEM_TREE_ROOT };
	}

}
