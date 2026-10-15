class CircularQueue<E> {
    private Object[] items;
    private int front;
    private int rear;
    private int size;
    
    public CircularQueue(int capacity) {
        items = new Object[capacity];// 至少是1
        front = 0;
        rear = 0;
        size = 0;
    }
    public CircularQueue() {
        this(10);
    }
    public void resize(int newCapacity) {
//        新建 newCapacity 大小的数组 把元素按逻辑顺序(front 起)拷过去
        Object[] newItems = new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newItems[i] = items[(front + i) % items.length];
        }
        items = newItems;
        front = 0;
        rear = size%newCapacity;
//        size(元素个数)保持不变 不要在这里修改它
    }
    public boolean isEmpty() {
        return size==0;
    }
    public void insert(E item) {
        if (size==items.length) {
            resize(items.length*2);
        }
        items[rear] = item;
        rear = (rear+1)%items.length;
        size++;
    }
    public E pop() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        E item=(E)items[front];
        items[front]=null;
        front=(front+1)%items.length;
        size--;
//        减少空间浪费
        if (size>0 && size== items.length/2) {
            resize(items.length/2);
        }
        return item;
    }
    public E peek() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        return (E)items[front];
    }
    public int size() {
        return size;
    }
    public int capacity() {
        return items.length;
    }
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("[");
        for (int i=0;i<size;i++) {
            sb.append(items[(front+i)%items.length]);
            if (i!=size-1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
public class homework3_2 {
    public static void main(String[] args) {
        CircularQueue<Integer> queue = new CircularQueue<>(5);
        queue.insert(1);
        queue.insert(2);
        queue.insert(3);
        queue.insert(4);
        queue.insert(5);
        while (!queue.isEmpty()) {
            System.out.println("出栈元素:"+queue.pop());
            System.out.println("栈大小:"+queue.size());
            System.out.println("是不是空的"+queue.isEmpty());
        }
        System.out.println("====================");
        System.out.println("扩列实验");
        for (int i = 1; i <= 32; i++) {
            queue.insert(i);
            System.out.println("插入"+i+"->元素个数："+queue.size()+"，当前容量："+queue.capacity()+"，内容省略");
        }
    }
}
