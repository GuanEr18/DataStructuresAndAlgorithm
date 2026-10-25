public class lab1_7 {
    /**
     * 题目 3.2：仅调整链接(不交换 data)来交换两个相邻元素。
     *  a) 单链表 Singly   b) 双向链表 Doubly
     * swapAdjacent(x, y)：找到值 x 的结点 A 且其紧邻后继为值 y 的结点 B，翻转链接使顺序变为 ... B A ...
     */
    //a 单链表（无哨兵，first 指向首结点）
    private static class Singly {
        private static class SNode {
            int data;
            SNode next;
            SNode(int data, SNode next) { this.data = data; this.next = next; }
        }
        private SNode first;   // 首指针，空表为 null
        // 尾插构建
        void add(int v) {
            SNode n = new SNode(v, null);
            if (first == null) { first = n; return; }
            SNode cur = first;
            while (cur.next != null) cur = cur.next;
            cur.next = n;
        }
        /**
         * 交换相邻结点 A(x) 与 B(y)，只改链接。
         * 需要 A 的前驱 pred：若 A 是首结点则 pred==null，单独更新 first。
         * 返回是否找到并交换成功。
         */
        boolean swapAdjacent(int x, int y) {
            SNode pred = null, A = first;
            // 定位 A，使 A.data==x 且 A.next.data==y
            while (A != null && A.next != null) {
                if (A.data == x && A.next.data == y) break;   // 找到相邻对
                pred = A;                                      // pred 跟随前移
                A = A.next;
            }
            if (A == null || A.next == null) return false;     // 未找到该相邻对
            SNode B = A.next;          // 待交换的后继结点
            A.next = B.next;           // ① A 越过 B，指向 B 的原后继
            if (pred == null) first = B;   // ② A 原是头 → 新头改为 B
            else pred.next = B;            //    否则让前驱指向 B
            B.next = A;                    // ③ B 指向 A，完成 A、B 互换
            return true;
        }
        void print() {
            SNode cur = first;
            while (cur != null) {
                System.out.print(cur.data);
                if (cur.next != null) System.out.print("->");
                cur = cur.next;
            }
            System.out.println(" -> null");
        }
    }
    // b 双向链表（带头哨兵，简化边界处理）
    private static class Doubly {
        private static class DNode {
            int data;
            DNode prev, next;
            DNode(int data) { this.data = data; }
        }
        private final DNode head;   // 哨兵头结点，head.next 才是首元素
        Doubly() { head = new DNode(0); head.next = null; }
        // 尾插构建，维护双向链接
        void add(int v) {
            DNode n = new DNode(v);
            DNode cur = head;
            while (cur.next != null) cur = cur.next;   // 走到最后一个真实结点
            n.prev = cur;
            n.next = null;
            cur.next = n;
        }
        /**
         * 交换相邻结点 A(x) 与 B(y)，只改链接。
         * 原顺序 pred <-> A <-> B <-> succ，交换后 pred <-> B <-> A <-> succ。
         * 因有哨兵，pred 永远非空；succ 可能为 null(尾部)，需判空。
         */
        boolean swapAdjacent(int x, int y) {
            DNode pred = head, A = head.next;
            // 定位相邻对 A(x), B(y)
            while (A != null && A.next != null) {
                if (A.data == x && A.next.data == y) break;
                pred = A;
                A = A.next;
            }
            if (A == null || A.next == null) return false;
            DNode B = A.next;        // 后继结点
            DNode succ = B.next;     // B 的原后继，可能为 null
            pred.next = B;           // 前驱改指向 B
            B.prev = pred;
            B.next = A;              // B 与 A 互换位置
            A.prev = B;
            A.next = succ;           // A 接到原 succ
            if (succ != null) succ.prev = A;   // 维护后继的 prev，空则跳过
            return true;
        }
        void printForward() {
            DNode cur = head.next;
            while (cur != null) {
                System.out.print(cur.data);
                if (cur.next != null) System.out.print("<->");
                cur = cur.next;
            }
            System.out.println();
        }
        // 反向打印，验证 prev 链接也正确  不用这个了
//        void printBackward() {
//            DNode cur = head.next;
//            if (cur == null) { System.out.println("(empty)"); return; }
//            while (cur.next != null) cur = cur.next;   // 先走到尾部
//            while (cur.prev != null) {                 // 沿 prev 回退到哨兵前
//                System.out.print(cur.data);
//                if (cur.prev != null) System.out.print("<->");
//                cur = cur.prev;
//            }
//            System.out.println();
//        }
    }
    public static void main(String[] args) {
        long startTime = System.nanoTime();   // nanoTime 测量纳秒级耗时
        // ---- a) 单链表：交换头相邻对 1,2 与中间对 3,4 ----
        Singly s = new Singly();
        for (int v : new int[]{1, 2, 3, 4, 5}) s.add(v);
        System.out.print("单链表 原始：");   s.print();
        s.swapAdjacent(1, 2);                // 交换头部相邻对
        System.out.print("单链表(交换1,2)："); s.print();
        System.out.println();
        // ---- b) 双向链表：交换中间对 2,3 并正反向验证 ----
        Doubly d = new Doubly();
        for (int v : new int[]{1, 2, 3, 4, 5}) d.add(v);
        System.out.print("双向链表 原始(正向)："); d.printForward();
        d.swapAdjacent(2, 3);
        System.out.print("双向(交换2,3)正向：");   d.printForward();
        long endTime = System.nanoTime();
        System.out.println("程序运行时间： " + (endTime - startTime) + " ns");
    }
}

