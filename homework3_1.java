/**
 * 基于单向链表实现的栈 无头尾节点
 * top 指向顶部 push/pop都在栈的头部完成
 */
class LinkedStack<E> {
//    内部类 链表节点
    private static class Node<E> {
        E data;
        Node<E> next;
        Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }
    private Node<E> top;
    private int size;
    
    public LinkedStack() {
        top=null;
        size=0;
    }
//    为什么 isEmpty 方法是无参方法？因为 top 指针即可表示栈是否为空
    public boolean isEmpty() {
        return top==null;
    }
    
    public void push (E data) {
        top=new Node<>(data, top);
        size++;
    }
    public E pop() {
        if (isEmpty()) {
            return null;
        }
        else {
            E data=top.data;
            top=top.next;
            size--;
            return data;
        }
    }
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        else {
            return top.data;
        }
    }
    public int size() {
        return size;
    }
}

public class homework3_1 {
    public static void main(String[] args) {
        LinkedStack<Integer> stack = new LinkedStack<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.push(40);
        stack.push(50);
        System.out.println("栈顶的元素："+stack.peek());
        System.out.println("栈的大小："+stack.size());
        System.out.println("================");
        System.out.println("弹出的元素："+stack.pop());
        System.out.println("栈顶的元素："+stack.peek());
        System.out.println("栈的大小："+stack.size());
        System.out.println("================");
        while (!stack.isEmpty()) {
            System.out.println("弹出的元素："+stack.pop());
            System.out.println("是否为空："+stack.isEmpty());
        }
    }
}
