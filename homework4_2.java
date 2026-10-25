import java.util.Iterator;
import java.util.NoSuchElementException;

public class homework4_2 {
    static class TreeSetNode<E> {
        E element;
        TreeSetNode<E> left;
        TreeSetNode<E> right;
        TreeSetNode<E> parent;
        TreeSetNode(E e) {
            element = e;
        }
    }
    
    /**
     * 本题要求实现一个支持升序和降序遍历的有序集合
     * 请使用二叉查找树实现该集合
     * 请使用二叉查找树实现该集合
     * 为了简单起见，本题中集合元素类型 E 必须实现 Comparable 接口
     * Iterator<E> 是 JDK 自带的接口实现了之后才能使用for each 循环
     * @param <E>
     */
    static class MyTreeSet<E extends Comparable<E>> implements Iterable<E> {
        private TreeSetNode<E> root;
        private int size;
        public boolean isEmpty() {
            return size == 0;
        }
        public int size() {
            return size;
        }
        public boolean add(E e) {
            if(e==null)
                throw new NullPointerException();
            if(root == null) {
                root = new TreeSetNode<E>(e);
                size++;
                return true;
            }
            TreeSetNode<E> cur = root;
            while(true) {
                int cmp=e.compareTo(cur.element);
                if (cmp==0)
                    return false;
                else if (cmp<0) {
                    if (cur.left==null) {
                        cur.left = new TreeSetNode<E>(e);
                        size++;
                        cur.left.parent = cur;
                        return true;
                    }
//                    这个代表如果做指数不是空的花就跳到下一个左节点节点找空的
                    cur = cur.left;
                }
//                如下同理
                else {
                    if (cur.right==null) {
                        cur.right = new TreeSetNode<E>(e);
                        size++;
                        cur.right.parent = cur;
                        return true;
                    }
                    cur = cur.right;
                }
            }
        }
        private TreeSetNode<E> find(E e) {
            TreeSetNode<E> cur=root;
            while(cur!=null) {
                int cmp=e.compareTo(cur.element);
                if (cmp==0)
                    return cur;
//                compareTo返回的是int类型 本质是两个数之间做差
                cur=cmp<0?cur.left:cur.right;
            }
            return null;
        }
        public boolean contains(E e) {
            return find(e)!=null;
        }
//        按照中序的最小节点，
        private TreeSetNode<E> first() {
            TreeSetNode<E> cur = root;
            if (cur == null) return null;
            while (cur.left != null) cur = cur.left;
            return cur;
        }
//        同上 但是寻找最大节点
        private TreeSetNode<E> last() {
            TreeSetNode<E> cur = root;
            if (cur == null) return null;
            while (cur.right != null) cur = cur.right;
            return cur;
        }
//        借助 parent 指针寻找中序后继，即比node大的最小节点) 右子树非空 → 右子树的最小结点
//        否则沿 parent 上溯，直到某个结点是父结点的左孩子
        static <E> TreeSetNode<E> successor(TreeSetNode<E> node) {
            if (node==null)
                return null;
            if(node.right!=null) {
                node=node.right;
                while (node.left!=null) node=node.left;
                return node;
            }
            TreeSetNode<E> child=node;
            TreeSetNode<E> parent=node.parent;
            while (parent!=null&&child==parent.right) {
                child=parent;
                parent=parent.parent;
            }
            return parent;
        }
//        借助 parent 指针找中序前驱（与 successor 对称）
        static <E> TreeSetNode<E> predecessor(TreeSetNode<E> p) {
            if (p == null) return null;
            if (p.left != null) {
                p = p.left;
                while (p.right != null) p = p.right;
                return p;
            }
            TreeSetNode<E> child = p;
            TreeSetNode<E> par = p.parent;
            while (par != null && child == par.left) {
                child = par;
                par = par.parent;
            }
            return par;
        }
        private void deleteNode(TreeSetNode<E> node) {
            if (node.left != null && node.right != null) {
                // 有两个孩子：用中序后继（右子树最小结点）的数据覆盖，再删除后继结点
                TreeSetNode<E> succ = successor(node);
                node.element = succ.element;
                node = succ;
            }
            // 此时 node 至多一个孩子
            TreeSetNode<E> child = (node.left != null) ? node.left : node.right;
            if (child != null) child.parent = node.parent;
            if (node.parent == null) {
                root = child;                        // 删的是根
            } else if (node == node.parent.left) {
                node.parent.left = child;
            } else {
                node.parent.right = child;
            }
        }
        public boolean remove(E e) {
            TreeSetNode<E> node = find(e);
            if (node == null)
                return false;
            deleteNode(node);
            size--;
            return true;
        }
//        更好的打印方法
        public void printLevelOrder() {
            java.util.Queue<TreeSetNode<E>> q = new java.util.LinkedList<>();
            if (root != null) q.offer(root);
            System.out.println("层序遍历 (结点[父结点])：");
            while (!q.isEmpty()) {
                TreeSetNode<E> p = q.poll();
                String par = (p.parent == null) ? "null" : p.parent.element.toString();
                System.out.print(p.element + "[parent=" + par + "]  ");
                if (p.left != null) q.offer(p.left);
                if (p.right != null) q.offer(p.right);
            }
            System.out.println();
        }
        /** 升序迭代器（中序遍历） */
//        @Override 原先AI有这个会报错
        public Iterator<E> iterator() {
            return new TreeSetIterator(first());
        }
        /** 降序迭代器（逆中序遍历），体现 parent 链接的另一个用途 */
        public Iterator<E> descendingIterator() {
            return new DescendingIterator(last());
        }
        private class TreeSetIterator implements Iterator<E> {
            private TreeSetNode<E> nextNode;
            private TreeSetNode<E> lastReturned;
            
            TreeSetIterator(TreeSetNode<E> start) {
                nextNode = start;
            }
            @Override
            public boolean hasNext() {
                return nextNode != null;
            }
            @Override
            public E next() {
                if (nextNode == null) throw new NoSuchElementException();
                lastReturned = nextNode;
                nextNode = successor(nextNode);      // 利用 parent 指针推进
                return lastReturned.element;
            }
            @Override
            public void remove() {
                if (lastReturned == null) throw new IllegalStateException();
                // 若删除的正是待访问的后继来源，先推进指针，避免迭代器失效
                if (lastReturned == nextNode) {
                    nextNode = successor(lastReturned);
                }
                deleteNode(lastReturned);
                size--;
                lastReturned = null;
            }
        }
        /**
         * 这里能实现题目要求的配套迭代器。
         * Iterator<E> 是 JDK 自带的接口实现了之后才能使用for each 循环
         * 这里写成内部类是因为要访问外部类的成员 不用把成员变成public
         */
        private class DescendingIterator implements Iterator<E> {
            private TreeSetNode<E> nextNode;
            DescendingIterator(TreeSetNode<E> start) {
                nextNode = start;
            }
            @Override
            public boolean hasNext() {
                return nextNode != null;
            }
            @Override
            public E next() {
                if (nextNode == null) throw new NoSuchElementException();
                E val = nextNode.element;
                nextNode = predecessor(nextNode);
                return val;
            }
        }
    }
    public static void main(String[] args) {
        MyTreeSet<Integer> set = new MyTreeSet<>();
        int[] input = {50, 30, 70, 20, 40, 60, 80, 35};
        for (int x : input) set.add(x);
        System.out.println("插入 " + java.util.Arrays.toString(input) + " 后:");
        set.printLevelOrder();
        
        System.out.print("迭代器升序遍历: ");
        for (int x : set) System.out.print(x + " "); // 遍历集合
        System.out.println();
        
        System.out.print("降序迭代器遍历: ");
        java.util.Iterator<Integer> desc = set.descendingIterator();
        while (desc.hasNext()) System.out.print(desc.next() + " ");
        System.out.println();
        
        System.out.println("contains(40) = " + set.contains(40)
            + ", contains(45) = " + set.contains(45));
        System.out.println("重复插入 50, add(50) = " + set.add(50));
        
        System.out.println("删除 30 (有两个孩子) 后:");
        set.remove(30);
        set.printLevelOrder();
        System.out.print("删除后的升序遍历: ");
        for (int x : set) System.out.print(x + " ");
        System.out.println();
        System.out.println("size = " + set.size());
    }
}
