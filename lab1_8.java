public class lab1_8 {
    /**
     * 题目 3.11：单链表——带头结点(header node)、无尾结点，
     * 且仅维护一个指向头结点的引用 header。头结点不存真实数据，
     * 首元素是 header.next；空表即 header.next == null。
     */
    // 链表结点：静态私有内部类
    private static class Node {
        int data;
        Node next;
        Node(int data, Node next) { this.data = data; this.next = next; }
    }
    private final Node header;   // 唯一引用：指向头结点(哨兵)，不保存有效数据
    public lab1_8() {
        header = new Node(0, null);   // 初始化头结点，header.next==null 表示空表
    }
    // a) 返回链表长度：从头结点的后继开始逐个计数
    public int size() {
        int count = 0;
        Node cur = header.next;       // 跳过头结点
        while (cur != null) {
            count++;
            cur = cur.next;
        }
        return count;
    }
    // b) 打印链表所有元素
    public void print() {
        Node cur = header.next;
        while (cur != null) {
            System.out.print(cur.data);
            if (cur.next != null) System.out.print("->");
            cur = cur.next;
        }
        System.out.println("->null");
    }
    // c) 判断值 x 是否在链表中：线性查找
    public boolean contains(int x) {
        Node cur = header.next;
        while (cur != null) {
            if (cur.data == x) return true;   // 命中
            cur = cur.next;
        }
        return false;                          // 走到末尾仍未找到
    }
    // d) 若 x 不在链表中则插入(插到尾部)，返回是否发生插入
    public boolean add(int x) {
        if (contains(x)) return false;         // 已存在则不重复插入
        Node newNode = new Node(x, null);
        Node cur = header;                     // 从头结点出发找尾结点
        while (cur.next != null) cur = cur.next;
        cur.next = newNode;                    // 尾结点后继指向新结点
        return true;
    }
    // e) 若 x 在链表中则删除该结点，返回是否删除
    public boolean remove(int x) {
        Node pred = header;                    // 借助头结点保留前驱，无需特判首元素
        Node cur = header.next;
        while (cur != null) {
            if (cur.data == x) {
                pred.next = cur.next;          // 跳过 cur，即把它从链上摘除
                return true;
            }
            pred = cur;                        // 前驱与当前同步后移
            cur = cur.next;
        }
        return false;                          // 未找到 x
    }
    public static void main(String[] args) {
        long startTime = System.nanoTime();   // nanoTime 测量纳秒级耗时
        lab1_8 list = new lab1_8();
        // 演示 d)：插入(重复值不插入)
        System.out.println("add(10)->" + list.add(10));   // true
        System.out.println("add(20)->" + list.add(20));   // true
        System.out.println("add(30)->" + list.add(30));   // true
        System.out.println("add(20)->" + list.add(20));   // false(已存在)
        System.out.print("链表内容：");
        list.print();                         // b) 10 -> 20 -> 30 -> null
        // 演示 a) 与 c)
        System.out.println("size = " + list.size());          // 3
        System.out.println("contains(20)=" + list.contains(20)); // true
        System.out.println("contains(99)=" + list.contains(99)); // false
        // 演示 e)：删除存在与不存在的值
        System.out.println("remove(20)->" + list.remove(20)); // true
        System.out.println("remove(99)->" + list.remove(99)); // false
        System.out.print("删除后：");
        list.print();                         // 10 -> 30 -> null
        System.out.println("size=" + list.size());          // 2
        long endTime = System.nanoTime();
        System.out.println("程序运行时间：" + (endTime - startTime) + " ns");
    }
}
