import java.util.*;

public class homework2_2 {
    public static void main(String[] args) {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println("把普通数组变成Integer数组后的传入效果");
        System.out.println("原来是："+list);
        Integer[] addArray = {4, 5, 6};
        list.addAll(Arrays.asList(addArray));
        System.out.println("添加后是："+list);
        System.out.println("==================");
        
        System.out.println("用另一个ArrayList传入,测试通配符 ? extends");
        List<Integer> another = new ArrayList<>();
        another.add(7);
        another.add(8);
        list.addAll(another);
        System.out.println("另一个ArrayList是："+another);
        System.out.print("After addAll(List): ");
        System.out.println(list);
        System.out.println("==================");
        
        System.out.println("空集合");
        list.addAll(Collections.emptyList());
        System.out.print("After addAll(empty): ");
        System.out.println(list);
        System.out.println("size = " + list.size());
        System.out.println("==================");
    }
}
//基于数组的泛型顺序表
class MyArrayList<AnyType> implements Iterable<AnyType> {
    private static final int DEFAULT_CAPACITY = 10;
    private int theSize;
    private AnyType[] theItems;
    public MyArrayList() {
        theSize=0;
        theItems=(AnyType[])new Object[DEFAULT_CAPACITY];
    }
    
    public int size() {
        return theSize;
    }
    
    public boolean isEmpty() {
        return size()==0;
    }
    
    public void clear() {
        theSize=0;
    }
    public void add (AnyType x) {
        ensureCapacity(theSize+1);
        theItems[theSize++]=x;
    }
    public AnyType get(int idx) {
        if (idx<0 || idx>=size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return theItems[idx];
    }
    public void addAll(Iterable<? extends AnyType> items) {
        for (AnyType item : items) {
            add(item);
        }
    }
    private void ensureCapacity(int newCapacity) {
        if (newCapacity<theSize) {
            return;
        }
        int oldCapacity=theItems.length;
        if (newCapacity<=oldCapacity) return;
        int newCap=Math.max(newCapacity,oldCapacity+oldCapacity*2);
        AnyType[] newArray=(AnyType[])new Object[newCap];
        for (int i=0;i<theSize;i++) {
            newArray[i]=theItems[i];
        }
        theItems=newArray;
    }
    public java.util.Iterator<AnyType> iterator() {
        return new java.util.Iterator<AnyType>() {
            private int current=0;
            public boolean hasNext() {
                return current<size();
            }
            public AnyType next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                return theItems[current++];
            }
        };
    }
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < theSize; i++) {
            sb.append(theItems[i]);
            if (i != theSize - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    public void removeAll(Iterable<? extends AnyType> items) {
        Set<AnyType> toRemove = new HashSet<>();
        for (AnyType item : items) {
            toRemove.add(item);
        }
        int newIndex = 0;
        for (int i = 0; i < theSize; i++) {
            if (!toRemove.contains(theItems[i])) {
                theItems[newIndex++] = theItems[i];
            }
        }
        for (int i = newIndex; i < theSize; i++) {
            theItems[i] = null;
        }
        theSize = newIndex;
    }
}
