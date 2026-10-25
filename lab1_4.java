import java.util.ArrayList;
import java.util.List;

public class lab1_4 {
    /**
     * 题目 4：计算一个整数列表的最大值、最小值、平均值。
     * 用一个三元素的数组一次性返回结果：[max, min, average]。
     * 核心思想：单次遍历（single pass）同时维护 max、min、sum，
     * 时间复杂度 O(n)，额外空间 O(1)。
     * 约定：若列表为空，则返回 null（调用方需处理）。
     */
    public static double[] maxMinAvg(List<Integer> list) {
        if (list.isEmpty()) {
            return null;    // 空集不求
        }
//        从0位开始初始化 还能防止越位
        int max = list.get(0);
        int min = list.get(0);
        long sum = 0;
//        遍历
        for (int x : list) {
            if (x > max) max = x;
            if (x < min) min = x;
            sum += x;
        }
        double avg=(double)sum/list.size();
        return new double[]{max, min, avg};
    }
    public static void main(String[] args) {
        // 构造测试列表list=[3,1,4,1,5,9,2,6]
        List<Integer> list = new ArrayList<>();
        for (int x : new int[]{2000000000,2000000000}) list.add(x);
        long startTime = System.nanoTime();          // nanoTime
        double[] result = maxMinAvg(list);
        long endTime = System.nanoTime();
        // 输出三项结果；result 非空时才解析
        if (result != null) {
            System.out.println("列表内容：" + list);
            System.out.print("max=" + (int) result[0]+",");
            System.out.print("min=" + (int) result[1]+",");
            System.out.println("avg=" + result[2]);
        } else {
            System.out.println("列表为空，无法计算。");
        }
        System.out.println("程序运行时间： " + (endTime - startTime) + " ns");
        // 边界测试：空列表应返回 null
        System.out.println("空列表测试：" + maxMinAvg(new ArrayList<>()));
    }
}
