import java.util.ArrayList;
import java.util.List;

public class lab1 {
//    L是ArrayList的时候get方法耗时1
//    L是LinkedList的时候get方法耗时n
    public static void printLots(List<Integer> L, List<Integer> P) {
        /**
         * 用foreach循环遍历了P中的每一个元素，时间复杂度为O(n)
         * 对于每一个元素，都调用了L.get(i)方法，时间复杂度为O(1)
         * 因此，总的时间复杂度为O(n*1)=O(n)
         */
        for(Integer i:P) {
            if(i>=0 && i<L.size()) {
                System.out.print(L.get(i));
            }
        }
        System.out.println();
    }
    public static void main(String[] args) {
//        nanotime 是测量ns时间的
        long startTime= System.nanoTime();
        System.out.println("程序开始执行");
        List<Integer> L=new ArrayList<>();
        for (int i=0;i<10;i++) {
            L.add(i);
        }
        List<Integer> P=new ArrayList<>();
        P.add(1);
        P.add(1);
        P.add(4);
        P.add(100);
        printLots(L,P);
        long endTime=System.nanoTime();
        System.out.println("程序结束执行");
        System.out.println("程序运行时间： "+(endTime-startTime)+"ns");
    }
}
