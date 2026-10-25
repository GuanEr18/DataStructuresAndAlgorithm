public class lab1_9 {
    /**
     * 题目 9：多项式的加法与乘法。
     * 用链表存储，结点 = 一项 (coef 系数, exp 非负指数)，按 exp 降序排列。
     * P(x) = a1 x^e1 + a2 x^e2 + ...
     */
    // 多项式的一项，链表结点
    private static class Term {
        int coef;      // 系数
        int exp;       // 指数
        Term next;
        Term(int coef, int exp) { this.coef = coef; this.exp = exp; }
    }
    // 多项式：仅一个head引用，指向降序排列的首项；null表示零多项式
    private static class Polynomial {
        Term head;
    }
    // 插入一项并维持降序+合并同类项(系数相加，和为 0 则删除该项)
    private static void insertTerm(Polynomial p, int coef, int exp) {
        if (coef == 0) return;                 // 系数为 0 的项不进链表
        Term prev = null, cur = p.head;
        // 找到第一个 exp <= 待插入指数 的位置(降序遍历)
        while (cur != null && cur.exp > exp) {
            prev = cur;
            cur = cur.next;
        }
        if (cur != null && cur.exp == exp) {   // 存在同类项，合并系数
            cur.coef += coef;
            if (cur.coef == 0) {               // 合并后为 0，删除该结点
                if (prev == null) p.head = cur.next;
                else prev.next = cur.next;
            }
        } else {                               // 未发现同类项，前插新结点
            Term t = new Term(coef, exp);
            t.next = cur;
            if (prev == null) p.head = t;
            else prev.next = t;
        }
    }
    // 便捷构造：按给定 (coef,exp) 数组建立多项式
    private static Polynomial build(int[][] terms) {
        Polynomial p = new Polynomial();
        for (int[] t : terms) insertTerm(p, t[0], t[1]);
        return p;
    }
    /**
     * 加法：两个降序链表做双指针归并，指数相同则系数相加，
     * 结果按降序拼接且自动丢弃系数为 0 的项。时间 O(m+n)。
     */
    private static Polynomial add(Polynomial p, Polynomial q) {
        Polynomial r = new Polynomial();
        Term a = p.head, b = q.head;
        Term tail = null;                       // 结果链表尾部，O(1) 追加
        while (a != null && b != null) {
            int coef, exp;
            if (a.exp > b.exp) {                // a 指数大，先取 a
                coef = a.coef; exp = a.exp; a = a.next;
            } else if (a.exp < b.exp) {         // b 指数大，先取 b
                coef = b.coef; exp = b.exp; b = b.next;
            } else {                            // 指数相等，合并同类项
                coef = a.coef + b.coef; exp = a.exp; a = a.next; b = b.next;
            }
            if (coef == 0) continue;            // 抵消为 0 的项跳过
            Term t = new Term(coef, exp);
            if (r.head == null) r.head = t; else tail.next = t;
            tail = t;
        }
        while (a != null) {                     // 接上 p 的剩余项
            Term t = new Term(a.coef, a.exp);
            if (r.head == null) r.head = t; else tail.next = t;
            tail = t; a = a.next;
        }
        while (b != null) {                     // 接上 q 的剩余项
            Term t = new Term(b.coef, b.exp);
            if (r.head == null) r.head = t; else tail.next = t;
            tail = t; b = b.next;
        }
        return r;
    }
    /**
     * 乘法：p 的每项分别乘 q 的每项，得到 m*n 个乘积项，
     * 逐个 insertTerm 进结果(自动按降序排列并合并同类项)。
     */
    private static Polynomial multiply(Polynomial p, Polynomial q) {
        Polynomial r = new Polynomial();
        for (Term a = p.head; a != null; a = a.next) {
            for (Term b = q.head; b != null; b = b.next) {
                // 系数相乘、指数相加
                insertTerm(r, a.coef * b.coef, a.exp + b.exp);
            }
        }
        return r;
    }
    // 将多项式格式化为可读字符串，如 "30x^2990 - 20x^2492 + ... + 5"
    private static String toString(Polynomial p) {
        if (p.head == null) return "0";
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Term cur = p.head; cur != null; cur = cur.next) {
            int c = cur.coef, e = cur.exp;
            // 系数绝对值为 1 且非常数项时省略数字 1，只显示 x
            String coefStr = (e != 0 && Math.abs(c) == 1) ? "" : String.valueOf(Math.abs(c));
            String varPart = (e == 0) ? "" : (e == 1 ? "x" : "x^" + e);
            if (first) {
                if (c < 0) sb.append("-");       // 首项负号直接打在最前
                sb.append(coefStr).append(varPart);
                first = false;
            } else {
                sb.append(c < 0 ? " - " : " + ").append(coefStr).append(varPart);
            }
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        long startTime = System.nanoTime();     // nanoTime 测量纳秒级耗时
        // P1(x) = 10x^1000 + 5x^14 + 1
        Polynomial p1 = build(new int[][]{{10, 1000}, {5, 14}, {1, 0}});
        // P2(x) = 3x^1990 - 2x^1492 + 11x + 5
        Polynomial p2 = build(new int[][]{{3, 1990}, {-2, 1492}, {11, 1}, {5, 0}});
        System.out.println("P1(x) = " + toString(p1));
        System.out.println("P2(x) = " + toString(p2));
        System.out.println("P1 + P2 = " + toString(add(p1, p2)));
        System.out.println("P1 * P2 = " + toString(multiply(p1, p2)));
        System.out.println();
        // P3 = x^2 + 1, P4 = -x^2 + 2x + 3 → P3 + P4 = 2x + 4 (x^2 抵消)
        Polynomial p3 = build(new int[][]{{1, 2}, {1, 0}});
        Polynomial p4 = build(new int[][]{{-1, 2}, {2, 1}, {3, 0}});
        System.out.println("P3(x) = " + toString(p3));
        System.out.println("P4(x) = " + toString(p4));
        System.out.println("P3 + P4 = " + toString(add(p3, p4)));
        // P3 * P4 = (x^2+1)(-x^2+2x+3)
        System.out.println("P3 * P4 = " + toString(multiply(p3, p4)));
        long endTime = System.nanoTime();
        System.out.println("程序运行时间： " + (endTime - startTime) + " ns");
    }
}
