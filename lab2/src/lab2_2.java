import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
/**
 * Lab2 第二题：分别用 链表、数组、循环数组 三种数据结构实现队列，
 * 并用 java.util.Queue 对相同操作序列进行对比测试。
 * 队列遵循 FIFO（先进先出）原则：队尾入队（enqueue），队头出队（dequeue）。
 * 为了让四种队列跑完全相同的测试代码，这里先定义统一接口 MyQueue，
 * 三个自定义实现与 JDK 队列的适配器都实现该接口。
 */
interface MyQueue<E> {
    void enqueue(E item); // 队尾入队
    E dequeue();         // 队头出队
    E peek();            // 获取队头元素但不删除
    boolean isEmpty();   // 判断队列是否为空
    int size();          // 获取队列中元素个数
}
/**
 * 适配器：把 JDK 的 java.util.Queue 包装成 MyQueue 接口，
 * 使标准库队列能与三个自定义实现共用同一段测试代码。
 * 注意语义差异：java.util.Queue 空队 remove 抛 NoSuchElementException，
 * 这里原样透传，用于对比自定义实现的 IllegalStateException。
 * 说实话。这部分我其实还是不太明白 ，只是AI说这样能只写一份测试代码
 */
class JavaQueueAdapter<E> implements MyQueue<E> {
    private final java.util.Queue<E> inner; // 被包装的 JDK 队列实例
    JavaQueueAdapter(java.util.Queue<E> inner) {
        this.inner = inner;
    }
    public void enqueue(E item) { inner.add(item); }        // add ≈ enqueue
    public E dequeue() { return inner.remove(); }           // remove ≈ dequeue（空队抛异常）
    public E peek() { return inner.element(); }             // element ≈ peek，空队抛 NoSuchElementException
    public boolean isEmpty() { return inner.isEmpty(); }
    public int size() { return inner.size(); }
}
/**
 * (a) 链表队列：无哨兵结点设计。
 * front 直指队头真实结点（出队端），back 直指队尾真实结点（入队端）。
 * 两端都持有指针，因此入队、出队均无需遍历，都是 O(1)。
 */
class LinkedQueue<E> implements MyQueue<E> {
//    嵌套了内部私有类 ，这个静态问AI了也看不懂
    private static class Node<E> {
        E data;
        Node<E> next;
//        构造函数
//        这里的有参构造是为了创建新节点的时候将数据连接到下一个节点
        Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }
    private Node<E> front;
    private Node<E> back;
    private int size;
//    空构造 ，是为了创建一个空队列 ，属于LinkedQueue类
    public LinkedQueue() {
        front = null;
        back = null;
        size = 0;
    }
    /**
     * 方法部分
     * 尾部压入 。头部弹出
     */
    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }
    public void enqueue(E item) {
        Node<E> newNode = new Node<>(item, null);
        if (isEmpty()) {
            front = newNode;
            back = newNode;
        } else {
            back.next = newNode;
            back = newNode;
        }
        size++;
    }
    public E dequeue() {
        if(isEmpty())
            throw new IllegalStateException("Queue is empty");
        E data= front.data;;
        front=front.next;
        size--;
        return data; // 返回出队元素
    }
    public E peek() {
        if(isEmpty())
            throw new IllegalStateException("Queue is empty");
        return front.data;
    }
}
/**
 * (b) 数组队列（非循环版）：元素紧凑存放在数组的前 size 个位置。
 * 入队写到 items[size]，O(1)；出队读 items[0]，但读完后必须把
 * 其余元素整体左移一格以保持"队头永远在下标 0"，出队代价 O(n)。
 * 该实现故意保留低效的左移，用于与循环数组（c）对比。
 */
class ArrayQueue<E> implements MyQueue<E> {
    private E[] items;
    private int size;
//    final关键词表示这个变量的值不能被修改，这里表示默认容量是8
    private static final int DEFAULT_CAPACITY = 8;
    public ArrayQueue() {
        items=(E[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }
    /**
     * 方法部分
     */
    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }
//    array 的特性就是满了之后容量翻倍
//    这里的size指的是内部存了多少个数字 而length指的是这个队列开了多少内存
    public void enqueue(E item) {
        if(size==items.length) {
            E[] longer=(E[]) new Object[items.length*2];
            for(int i=0;i<size;i++)
                longer[i]=items[i];
            items=longer;
        }
        items[size++] = item; // 放到队尾并后移逻辑队尾
    }
    public E dequeue() {
        if(size==0)
            throw new IllegalStateException("Queue is empty");
        E data = items[0];
        for(int i=0;i<size-1;i++)
            items[i]=items[i+1];
        items[size-1]=null;
        size--;
        return data;
    }
    public E peek() {
        if(size==0)
            throw new IllegalStateException("Queue is empty");
        return items[0];
    }
}
/**
 * (c) 循环数组队列：把数组逻辑上首尾相接成环。
 * 用 head（队头下标）+ size（元素个数）两个变量描述状态：
 *   队头位置   = head
 *   队尾的下一位置 = (head + size) % capacity
 * 出队只推进 head，不做任何搬移 —— 入队、出队均 O(1)。
 */
class CircularArrayQueue<E> implements MyQueue<E> {
    private E[] items;
    private int head;   // 队头下标
//    private int tail;   // 队尾下标
    private int size;
    private static final int DEFAULT_CAPACITY = 8;
    public CircularArrayQueue() {
        items=(E[]) new Object[DEFAULT_CAPACITY];
        head = 0;
        size = 0;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }
    /**
     * 入队：写进「head + size」对容量取模的位置（即队尾的下一个空位）。
     * 满（size == capacity）时扩容为 2 倍，并把元素按队头优先的顺序
     * 重新到新数组的 0..size-1（此时 head 复位为 0）。
     */
    public void enqueue(E item) {
        if (size == items.length) {
            E[] longer = (E[]) new Object[items.length * 2];
            for (int i = 0; i < size; i++) {
//                从旧head开始依次抄写，保证复制后顺序与逻辑队一致
//                但是一般上head都是0 后续代码就体现了 扩容之后就会把头重新定义成0
                longer[i] = items[(head + i) % items.length];
            }
            items = longer;
            head = 0;
        }
        items[(head + size) % items.length] = item;
        size++;
    }
    public E dequeue() {
        if (size == 0) {
            throw new IllegalStateException("Queue is empty");
        }
        E data = items[head];
        head = (head + 1) % items.length;
        size--;
        return data;
    }
    public E peek() {
        if (size == 0) {
            throw new IllegalStateException("Queue is empty");
        }
        return items[head];
    }
}

/**
 * AI 搞出来的测试方法
 */
public class lab2_2 {
    public static void runTestSequence(String name, MyQueue<Integer> q) {
        long start = System.nanoTime(); // 记录开始时间，用于测试表格的耗时列
        
        System.out.println("---- 测试 " + name + " ----");
        // 第 1 步：入队整数序列 1,2,3,4
        for (int v = 1; v <= 4; v++) {
            q.enqueue(v);
        }
        System.out.println("入队 1,2,3,4 后：size=" + q.size() + ", 队头=" + q.peek());
        // 第 2 步：出队两次（按 FIFO 应依次得到 1 和 2）
        System.out.println("出队：" + q.dequeue());
        System.out.println("出队：" + q.dequeue());
        // 第 3 步：再入队 5（此时队中为 3,4,5）
        q.enqueue(5);
        System.out.println("入队 5 后：size=" + q.size());
        // 第 4 步：排空队列（应依次得到 3,4,5）
        while (!q.isEmpty()) {
            System.out.println("出队：" + q.dequeue());
        }
        System.out.println("排空后 isEmpty=" + q.isEmpty());
        // 第 5 步：对空队列出队，验证异常保护（各实现异常类型可能不同，均打印类名）
        try {
            q.dequeue();
        } catch (IllegalStateException | NoSuchElementException e) {
            System.out.println("空队出队捕获异常：" + e.getClass().getSimpleName());
        }
        
        double ms = (System.nanoTime() - start) / 1_000_000.0; // 纳秒转毫秒
        System.out.printf("总耗时：%.4f ms%n%n", ms);
    }
    public static void main(String[] args) {
        // (a) 链表队列
        runTestSequence("(a) LinkedQueue 链表实现", new LinkedQueue<>());
        // (b) 非循环数组队列（出队需左移）
        runTestSequence("(b) ArrayQueue 数组实现", new ArrayQueue<>());
        // (c) 循环数组队列
        runTestSequence("(c) CircularArrayQueue 循环数组实现", new CircularArrayQueue<>());
        runTestSequence("(d) java.util.Queue (LinkedList)",
            new JavaQueueAdapter<>(new LinkedList<>()));
    }
}
