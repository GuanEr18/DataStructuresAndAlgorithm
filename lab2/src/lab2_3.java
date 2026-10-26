public class lab2_3 {
    /**
     * 这个是检查的
     */
    private static final class Check {
        final boolean balance;
        final String message;
        final int position;
        Check(boolean balance, String message, int position) {
            this.balance = balance;
            this.message = message;
            this.position = position;
        }
    }
    /**
     * top 直指栈顶结点，push/pop/peek
     * 之所以元素类型取 String 而非 Character，是因为注释符 "/*" 占两个字符，
     * 用字符串可以让单字符括号和双字符注释共用同一个栈。
     */
    public static class myStack {
        private static class Node<E> {
            E data;
            Node next;
            Node(E data,Node<E> next) {
                this.data = data;
                this.next = next;
            }
        }
        private Node top;
        public void push(Object data) {
            Node newNode = new Node(data,top);
            top = newNode;
        }
        public Object pop() {
            if(isEmpty())
                return null;
            Object data = top.data;
            top = top.next;
            return data;
        }
        public Object peek() {
            if(isEmpty())
                return null;
            return top.data;
        }
        public boolean isEmpty() {
            return top == null;
        }
        public int size() {
            int size = 0;
            Node current = top;
            while(current != null) {
                size++;
                current = current.next;
            }
            return size;
        }
    }
//    字符判断
    private static boolean isRight(char c) {
        return c == '(' || c == '[' || c == '{';
    }
    private static boolean isLeft(char c) {
        return c == ')' || c == ']' || c == '}';
    }
}
