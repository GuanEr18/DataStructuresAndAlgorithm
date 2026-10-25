import java.util.ArrayList;
import java.util.List;

public class lab1_2 {
    /**
     * 题目 3.4：给定两个升序列表 L1、L2，仅用基本列表操作求交集 L1 ∩ L2。
     * 核心思想：双指针（利用“均已升序”的性质）。
     *  - 比较 p1、p2 指向的元素：
     *      相等 → 是公共元素，加入结果，两个指针同时前进；
     *      a < b → L1 落后，p1 前进；
     *      a > b → L2 落后，p2 前进。
     * 只使用 size()、get()、add() 等基本 Collections API 操作。
     * 时间复杂度：每个元素至多被访问一次，O(|L1| + |L2|)。
     */
    public static List<Integer> intersect(List<Integer> L1, List<Integer> L2) {
        List<Integer> result = new ArrayList<>();
        int p1=0;   // 表示L1的位置
        int p2=0;   // 表示L2的位置
//        只要两个列表还有没有比较过的元素就继续
        while (p1 < L1.size() && p2 < L2.size()) {
            int a=L1.get(p1);
            int b=L2.get(p2);
            if(a==b) {
                result.add(a);
                p1++;
                p2++;
            }
//            选择较小的元素所属的列表前进
            else if (a < b) {
                p1++;
            } else {
                p2++;
            }
        }
        return result;
    }
    public static void main(String[] args) {
//        自己手动构建一个升序
        List<Integer> L1=new ArrayList<>();
        for(int i:new int[]{1,2,2,3,5})
            L1.add(i);
        List<Integer> L2=new ArrayList<>();
        for(int i:new int[]{2,2,4,6})
            L2.add(i);
        /**
         * 先用双指针求交集，计时
         */
        long startTime= System.nanoTime();
        System.out.println("程序开始执行双指针");
        List<Integer> result=intersect(L1,L2);
        long endTime= System.nanoTime();
        System.out.println("交集为："+result.toString());
        System.out.println("程序结束执行双指针");
        System.out.println("双指针用时："+(endTime-startTime)+"纳秒");
        /**
         * 用 retainAll() 方法求交集，计时
         */
        long startTime2= System.nanoTime();
        System.out.println("程序开始执行 retainAll()");
        List<Integer> result2=new ArrayList<>(L1);
//        要先对这个 result 进行赋值 最好不要改动L1
        result2.retainAll(L2);
        long endTime2= System.nanoTime();
        System.out.println("交集为："+result2.toString());
        System.out.println("程序结束执行 retainAll()");
        System.out.println("retainAll()用时："+(endTime2-startTime2)+"纳秒");
    }
}
