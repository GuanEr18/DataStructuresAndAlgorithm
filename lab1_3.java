import java.util.ArrayList;
import java.util.List;

public class lab1_3 {
    /**
     * 题目 3.5：给定两个升序列表 L1、L2，仅用基本列表操作求并集 L1 ∪ L2。
     * 核心思想：归并（类似归并排序的 merge）+ 相邻去重。
     *  - 每次取两指针中较小者放入结果；
     *  - 相等时只放一份，两个指针同时前进（实现去重）；
     *  - 末尾把剩余那段直接接上。
     * 仅使用 size()、get()、add()等基本Collections API操作。
     * 时间复杂度：O(L1+L2)，且结果仍保持升序。
     */
    public static List<Integer> union(List<Integer> L1, List<Integer> L2) {
        List<Integer> result = new ArrayList<>();
//        分别指向L1 和L2
        int p1 = 0, p2 = 0;
//        两列都还有元素的时候做归并比较
        while (p1 < L1.size() && p2 < L2.size()) {
            int a = L1.get(p1);
            int b = L2.get(p2);
            if (a < b) {
                result.add(a);
                p1++;
            } else if (a > b) {
                result.add(b);
                p2++;
            } else /*相等情况*/{
                result.add(a);
                p1++;
                p2++;
            }
        }
//        把L1剩余的元素接上
        while (p1<L1.size()) {
            result.add(L1.get(p1));
            p1++;
        }
//        L2的接上
        while (p2<L2.size()) {
            result.add(L2.get(p2));
            p2++;
        }
        return result;
    }
    public static void main(String[] args) {
        // 构造升序列表 L1 = [1,3,5,7,9]
        List<Integer> L1 = new ArrayList<>();
        for (int x : new int[]{1, 3, 5}) L1.add(x);
        // 构造升序列表 L2 = [2,3,5,8,10]
        List<Integer> L2 = new ArrayList<>();
        for (int x : new int[]{2,4,6}) L2.add(x);
        // ---- 方法一：自研归并求并集，并计时 ----
        long start1 = System.nanoTime();
        List<Integer> myResult = union(L1, L2);
        long end1 = System.nanoTime();
        System.out.println("归并 union 结果：" + myResult);
        System.out.println("归并耗时：" + (end1 - start1) + " ns");
        // ---- 方法二：使用 removeAll + addAll 求并集 ----
        long start2 = System.nanoTime();
        List<Integer> libResult = new ArrayList<>(L1);   // 拷贝 L1 作为基础
        List<Integer> onlyInL2 = new ArrayList<>(L2);    // 拷贝 L2
        onlyInL2.removeAll(L1);                          // 去掉 L2 中与 L1 重复的元素
        libResult.addAll(onlyInL2);                      // 再把 L2 独有部分并入
        long end2 = System.nanoTime();
        System.out.println("removeAll+addAll 结果（未排序）：" + libResult);
        System.out.println("removeAll+addAll 耗时：" + (end2 - start2) + " ns");
    }
}
