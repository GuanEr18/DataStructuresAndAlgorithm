import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class homework2_3 {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        System.out.println("空集合");
        list.addAll(Collections.emptyList());
        System.out.print("After addAll(empty): ");
        System.out.println(list);
        System.out.println("size = " + list.size());
        System.out.println("==================");
        
        System.out.println("测试 removeAll:");
        MyArrayList<Integer> list2 = new MyArrayList<>();
        for (int x : new Integer[]{1, 2, 3, 4, 5, 3, 6, 3}) {
            list2.add(x);
        }
        System.out.println("原来是：" + list2 + "  (size=" + list2.size() + ")");
        list2.removeAll(Arrays.asList(3, 5));
        System.out.println("removeAll([3,5]) 后：" + list2 + "  (size=" + list2.size() + ")");
        list2.removeAll(Arrays.asList(99));
        System.out.println("removeAll([99]) 后(不存在)：" + list2);
        list2.removeAll(Arrays.asList(1, 2, 4, 6));
        System.out.println("removeAll(剩余全部) 后：" + list2 + "  (size=" + list2.size() + ")");
        System.out.println("==================");
    }
}
