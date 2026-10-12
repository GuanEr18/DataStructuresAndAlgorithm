import java.util.Scanner;

public class homework2 {
    public static void main(String[] args) {
        MyLinkedList theList = new MyLinkedList();
        theList.add(3);
        theList.add(5);
        theList.add(7);
        theList.add(9);
        theList.add(1);
        System.out.print("The list contains: ");
        theList.printList();
        // 测试 contains 方法
        System.out.println("contains 5 ? "+theList.contains(5));
        System.out.println("contains 7 ? "+theList.contains(7));
        System.out.println("contains 4 ? "+theList.contains(4));
        System.out.println("contains 1 ? "+theList.contains(1));
        System.out.println("contains 9 ? "+theList.contains(9));
    }
//    public static void main(String[] args) {
//        MyLinkedList list = new MyLinkedList();
//        Scanner scanner = new Scanner(System.in);
//
//        // 输入链表数据:先输入元素个数,再依次输入每个元素
//        System.out.print("请输入链表元素个数: ");
//        int n = scanner.nextInt();
//        System.out.println("请依次输入 " + n + " 个元素:");
//        for (int i = 0; i < n; i++) {
//            list.add(scanner.nextInt());
//        }
//
//        System.out.print("The list contains: ");
//        list.printList();
//
//        // 输入要查找的值,测试 contains 方法(-1 结束)
//        while (true) {
//            System.out.print("请输入要查找的值(输入 -1 结束): ");
//            int x = scanner.nextInt();
//            if (x == -1) {
//                break;
//            }
//            System.out.println("contains " + x + " ? " + list.contains(x));
//        }
//        scanner.close();
//    }
}

class MyLinkedList {
    private static class Node {
        int data;
        Node next;
        Node(int data) {
            this.data = data;
            this.next = null;
        }
        Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }
    }
    private Node header;
    private int size;
    public MyLinkedList () {
        header=new Node(0);
//        头节点
        size=0;
    }
//    时间复杂度O(n)
    public boolean contains (int x) {
        Node current=header.next;
        while (current!=null){
            if (current.data==x){
                return true;
            }
            current=current.next;
        }
        return false;
    }
//    头插入法
    public void add (int x) {
        header.next=new Node (x,header.next);
    }
    public void printList() {
        Node current = header.next;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }
    
    public int size() {
        return size;
    }
    
    public boolean isEmpty() {
        return header.next == null;
    }
}
