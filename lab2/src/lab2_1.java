import java.util.EmptyStackException;

class LinkedStack<E> {
    private static class Node<E> {
        E data;
        Node<E> next;
//        Node<E> next; // 自引用特性
//        构造函数
        Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }
    private Node<E> top;
    private int size;
    //        无参构造
    public LinkedStack() {
        top = null;
        size = 0;
    }
//    isEmpty
    public boolean isEmpty() {
        return size == 0;
//        return top == null;
    }
    public void push(E data) {
        top=new Node<>(data,top);// 新节点压入顶部 头节点再赋值
        size++;
    }
    public E pop() {
        if(isEmpty()) {
            throw new EmptyStackException();//  空栈
        }
        E data=top.data;
        top=top.next;
        size--;
        return data;
    }
//    查看栈的顶端但是不弹出
    public E peek() {
        if(isEmpty()) {
            throw new EmptyStackException();//  空栈
        }
        return top.data;
    }
    public int size() {
        return size;
    }
}
public class lab2_1 {
    /**
     * 实现一个基于链表的栈（Stack）类，并编写一个程序来测试它所支持的操作。
     * 栈（Stack）是一种后进先出的线性结构，只允许在一端（栈顶）
     * 进行插入（push）和删除（pop）
     */
    public static void main(String[] args) {
        LinkedStack<Integer> stack = new LinkedStack<>();
        long startTime=System.nanoTime();
        System.out.print("isEmpty方法测试输出：");
        System.out.println(stack.isEmpty());
        for (int i:new int[]{1,2,3,4,5})
            stack.push(i);
        System.out.println("push5个元素后，栈顶：" + stack.peek()); // 5
        System.out.println("栈的大小：" + stack.size());              // 5
        
        System.out.println("================");
        // 应弹出栈顶5，剩余栈顶变为4
        System.out.println("弹出的元素：" + stack.pop()); // 5
        System.out.println("弹出后栈顶：" + stack.peek()); // 4
        System.out.println("弹出后大小：" + stack.size()); // 4
        System.out.println("================");
        // 对空栈执行 pop，验证是否正确抛出异常
        try {
            stack.pop();
        } catch (EmptyStackException e) {
            System.out.println("空栈 pop 捕获到异常：正确");
        }
        long endTime=System.nanoTime();
        System.out.println("程序运行时间： "+(endTime-startTime)+"纳秒");
    }
}
