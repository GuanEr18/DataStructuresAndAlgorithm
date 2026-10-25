public class lab1_5 {
    /**
     * 题目 5：编写程序打印单链表的所有元素。
     * 采用无头结点(哨兵)架构：first 直接指向第一个真实数据结点，空表时 first == null。
     */
    // 单链表结点：静态私有泛型内部类，只存数据与后继指针
    private static class Node<E> {
        E data;           // 结点存储的元素
        Node<E> next;     // 指向下一个结点，尾结点的 next 为 null
        Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }
    private Node<Integer> first;   // 指向链表第一个结点，无哨兵结点
    private int size;              // 维护元素个数
    public lab1_5() {
        first = null;              // 初始为空表
        size = 0;
    }
    // 在链表尾部追加一个元素（遍历到末尾再接新结点）
    public void add(int value) {
        Node<Integer> newNode = new Node<>(value, null);
        if (first == null) {
            first = newNode;       // 空表：新结点即为首结点
        } else {
            Node<Integer> cur = first;
            while (cur.next != null) {   // 走到最后一个结点
                cur = cur.next;
            }
            cur.next = newNode;          // 尾结点后继指向新结点
        }
        size++;
    }
    // 核心：顺序遍历链表并打印每个元素
    public void print() {
        Node<Integer> cur = first;      // 从头结点开始
        while (cur != null) {           // 直到越过尾结点(cur.next==null)后 cur 变 null
            System.out.print(cur.data);
            if (cur.next != null) {
                System.out.print(",");
            }
            cur = cur.next;             // 指针后移
        }
        System.out.println(" -> null"); // 结尾标注 null，直观展示链表结构
    }
    // 空表判断（供打印前的提示使用）
    public boolean isEmpty() {
        return first == null;
    }
    public static void main(String[] args) {
        lab1_5 list = new lab1_5();
        long startTime = System.nanoTime();       // nanoTime 测量纳秒级耗时
        // 构建链表：10,20,30,40,50
        for (int v : new int[]{}) {
            list.add(v);
        }
        // 打印所有元素
        if (list.isEmpty()) {
            System.out.println("链表为空，无元素可打印。");
        } else {
            System.out.print("链表元素：");
            list.print();
        }
        long endTime = System.nanoTime();
        System.out.println("程序运行时间： " + (endTime - startTime) + " ns");
        // 边界测试：空链表打印提示
        System.out.print("空链表测试：");
    }
}
