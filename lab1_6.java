public class lab1_6 {
    /**
     * 题目 6：非递归地在 O(N) 时间内反转单链表。
     * 三指针迭代：prev 保存已反转部分的前驱，curr 为当前处理结点，
     * nxt 先暂存 curr.next，以免改向后继丢失。
     */
    // 单链表结点：静态私有内部类，无哨兵结点
    private static class Node {
        int data;
        Node next;
        Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }
    }
    private Node first;   // 指向首结点，空表为 null
    // 尾部追加，构建测试用链表
    public void add(int value) {
        Node newNode = new Node(value, null);
        if (first == null) {
            first = newNode;
        } else {
            Node cur = first;
            while (cur.next != null) cur = cur.next;
            cur.next = newNode;
        }
    }
    /**
     * 核心：非反转链表。仅通过调整指针(不改动 data)完成，O(N) 时间、O(1) 空间。
     */
    public void reverse() {
        Node prev = null;      // 反转后当前结点应指向的前驱，初始为 null(成为新尾)
        Node curr = first;     // 从原首结点开始逐个处理
        while (curr != null) {
            Node nxt = curr.next;  // 先保存后继，防止下面改向后丢失剩余链表
            curr.next = prev;      // 关键：把当前结点的指针反转指向前驱
            prev = curr;           // prev 前进一步，指向已反转部分的头
            curr = nxt;            // curr 前进一步，继续处理未反转部分
        }
        first = prev;              // 遍历结束时 prev 指向原尾结点，即新链表的首结点
    }
    // 顺序打印链表，用于验证反转效果
    public void print() {
        Node cur = first;
        while (cur != null) {
            System.out.print(cur.data);
            if (cur.next != null) System.out.print(",");
            cur = cur.next;
        }
        System.out.println(" -> null");
    }
    public static void main(String[] args) {
        lab1_6 list = new lab1_6();
        long startTime = System.nanoTime();   // nanoTime 测量纳秒级耗时
        // 构建 1,2,3,4,5
        for (int v : new int[]{}) list.add(v);
        System.out.print("反转前：");
        list.print();
        // 执行非递归反转
        list.reverse();
        System.out.print("反转后：");
        list.print();
        long endTime = System.nanoTime();
        System.out.println("程序运行时间： " + (endTime - startTime) + " ns");
        // 边界测试：空表与单结点反转不应出错
        lab1_6 empty = new lab1_6();
        empty.reverse();
        System.out.print("空表反转：");
        empty.print();
        lab1_6 single = new lab1_6();
        single.add(99);
        single.reverse();
        System.out.print("单结点反转：");
        single.print();
    }
}
