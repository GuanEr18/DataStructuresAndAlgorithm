public class homework1 {
    public static void main(String[] args) {
        int [] ns= {100,1000,10000,100000,1000000,10000000,100000000};
        for (int s:ns) {
            System.out.println("range: "+s);
            long startTime=System.nanoTime();
            int sum=0;
            for (int i=0;i<s;i++) {
                for (int j=0;j<i*i;j++) {
                    if (j%i==0) {
                        for (int k=0;k<j;k++) {
                            sum++;
                        }
                    }
                }
            }
            long endTime=System.nanoTime();
            System.out.println("Time cost: "+(endTime-startTime)+" ns");
        }
    }
}

/**
 * 1代码
 * int [] ns= {100,1000,10000,100000,1000000,10000000,100000000};
 *         for (int s:ns) {
 *             System.out.println("range: "+s);
 *             long startTime=System.nanoTime();
 *             int sum=0;
 *             for (int i=0;i<s;i++) {
 *                 sum++;
 *             }
 *             long endTime=System.nanoTime();
 *             System.out.println("Time cost: "+(endTime-startTime)+" ns");
 *         }
 * 第二题代码改动部分
 * for (int i=0;i<s;i++) {
 *     for (int j=0;j<s;j++) {
 *         sum++;
 *     }
 * }
 * 第三题代码改动部分
 *
 *     for (int i=0;i<s;i++) {
 *         for (int j=0;j<s*s;j++) {
 *              sum++;
 *         }
 *     }
 */