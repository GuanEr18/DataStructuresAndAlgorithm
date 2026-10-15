public class homework4_1 {
    static class TreeNode {
        String val;
        TreeNode left;
        TreeNode right;
        TreeNode(String x) { val = x; }
    }
//    前序遍历 根 左 右
    public static void preorderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.print(root.val + " ");
        preorderTraversal(root.left);
        preorderTraversal(root.right);
    }
//    中序遍历 左 根 右
    public static void inorderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        inorderTraversal(root.left);
        System.out.print(root.val + " ");
        inorderTraversal(root.right);
    }
//    后序遍历 左 右 根
    public static void postorderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        postorderTraversal(root.left);
        postorderTraversal(root.right);
        System.out.print(root.val + " ");
    }
    public static void main(String[] args) {
        TreeNode root = new TreeNode("-");
        TreeNode mul = new TreeNode("*");
        TreeNode e = new TreeNode("e");
        root.left=mul;
        root.right=e;
        TreeNode mulLeft = new TreeNode("*");
        TreeNode mulRight = new TreeNode("+");
        mul.left=mulLeft;
        mul.right=mulRight;
        TreeNode mulLeftLeft = new TreeNode("a");
        TreeNode mulLeftRight = new TreeNode("b");
        mulLeft.left=mulLeftLeft;
        mulLeft.right=mulLeftRight;
        TreeNode mulRightLeft = new TreeNode("c");
        TreeNode mulRightRight = new TreeNode("d");
        mulRight.left=mulRightLeft;
        mulRight.right=mulRightRight;
        System.out.print("前序遍历: ");
        preorderTraversal(root);
        System.out.println();
        System.out.println("==============");
        System.out.print("中序遍历: ");
        inorderTraversal(root);
        System.out.println();
        System.out.println("==============");
        System.out.print("后序遍历: ");
        postorderTraversal(root);
        System.out.println();
        System.out.println("==============");
    }
}
