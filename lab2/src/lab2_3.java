import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

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
    public static class myStack<E> {
        private static class Node<E> {
            E data;
            Node next;
            Node(E data,Node<E> next) {
                this.data = data;
                this.next = next;
            }
        }
        private Node<E> top;
        void push(E data) {
            top = new Node<>(data, top);
        }
        E pop() {
            if(isEmpty())
                return null;
            E data = top.data;
            top=top.next;
            return data;
        }
        E peek() {
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
    /*核对是不是同类的括号*/
    private static boolean match(String left,String right) {
        return switch (right) {
            case ")" -> left.equals("(");
            case "}" -> left.equals("{");
            case "]" -> left.equals("[");
            default -> false;
        };
    }
    /**
     * 用自定义的 stack 读取文本
     */
    private static Check checkMyStack(String text) {
        myStack<String> stack = new myStack<>();
        int i=0;
        while (i<text.length()) {
            char c=text.charAt(i);
            if(isLeft(c)) {
                stack.push(String.valueOf(c));
                i++;
            } else if (isRight(c)) {
//                如果是空 那么就没有对应上
                if(stack.isEmpty()) {
                    return new Check(false,"有多余的右括号:"+c,i);
                }
                String left = stack.pop();
                if(!match(left,String.valueOf(c))) {
                    return new Check(false,"括号不匹配:"+left+String.valueOf(c),i);
                }
                i++;
            } else if (c=='/' && i+1<text.length() && text.charAt(i+1)=='*') {
                // 注释开始 "/*"：作为双字符整体压栈，并跳过两个字符
                stack.push("/*");
                i+=2;
            } else if (c=='*' && i+1<text.length() && text.charAt(i+1)=='/') {
                // 注释结束 "* /"：弹栈并核对栈顶确为注释开始符
                if (stack.isEmpty() || !stack.pop().equals("/*")) {
                    return new Check(false,"多余的注释结束符'*/'",i);
                }
                i+=2;
            } else {
                // 字母、数字、空格、单独运算符等一律忽略（要求 c 的混合文本）
                i++;
            }
        }
        // 扫描完毕仍有残留 => 有开符号无人闭合
        if (!stack.isEmpty()) {
            return new Check(false,"未闭合的'"+stack.peek() +"'",text.length());
        }
        return new Check(true,"全部符号配对平衡",-1);
    }
    /**
     * 使用标准库 java.util.Stack 实现同一算法（要求 b），
     * 逻辑与 checkWithMyStack 逐分支对应，两者结论应完全一致。
     * 到时候提交的时候可以删掉一点
     */
    private static Check checkWithJavaStack(String text) {
        java.util.Stack<String> stack = new java.util.Stack<>(); // JDK 遗留栈类，API 同名
        int i = 0;
        while (i < text.length()) {
            char c = text.charAt(i);
            if (isLeft(c)) {
                stack.push(String.valueOf(c)); // push 语义与自定义栈一致
                i++;
            } else if (isRight(c)) {
                if (stack.isEmpty()) {
                    return new Check(false, "多余的闭合符 '" + c + "'", i);
                }
                String open = stack.pop();
                if (!match(open, String.valueOf(c))) {
                    return new Check(false, "'" + open + "' 与 '" + c + "' 不匹配", i);
                }
                i++;
            } else if (c == '/' && i + 1 < text.length() && text.charAt(i + 1) == '*') {
                stack.push("/*");
                i += 2;
            } else if (c == '*' && i + 1 < text.length() && text.charAt(i + 1) == '/') {
                if (stack.isEmpty() || !stack.pop().equals("/*")) {
                    return new Check(false, "多余的注释结束符 '*/'", i);
                }
                i += 2;
            } else {
                i++;
            }
        }
        if (!stack.isEmpty()) {
            return new Check(false, "未闭合的 '" + stack.peek() + "'", text.length());
        }
        return new Check(true, "全部符号配对平衡", -1);
    }
    /** 测试样例文件内容：含英文字母、数字、四类符号，整体平衡（要求 c） */
    private static final String SAMPLE_FILE_TEXT = """
            Lab2 Question 3 test file (English letters, digits and symbols)
            function example(a, b) {
                if ((a + b) > 0) {
                    /** this is a block comment, it contains ( ) and [ ] */
                    result = { key : (a - b) / (c + d) };
                }
                return result;
            }
            All brackets, braces and comments above are balanced.
            """;
    /**
     * 定位测试文件：按候选相对路径依次探测（兼容不同工作目录配置），
     * 若均不存在则在首选位置自动创建样例文件，保证程序可独立运行。
     */
    private static Path locateTestFile() throws IOException {
        String[] candidates = {"lab2/test_balance.txt", "test_balance.txt", "src/test_balance.txt"};
        for (String c : candidates) {
            Path p = Path.of(c);
            if (Files.exists(p)) {
                return p; // 找到已存在的测试文件
            }
        }
        Path created = Path.of(candidates[0]); // 兜底：自动创建
        if (created.getParent() != null) {
            Files.createDirectories(created.getParent());
        }
        Files.writeString(created, SAMPLE_FILE_TEXT, StandardCharsets.UTF_8);
        System.out.println("(未找到测试文件，已自动生成 " + created + ")");
        return created;
    }
    /** 同时用两种栈跑一段文本并打印对比结果与耗时 */
    private static void runCase(String title, String text) {
        long t0 = System.nanoTime();
        Check r1 = checkMyStack(text);   // 自定义栈
        double ms1 = (System.nanoTime() - t0) / 1_000_000.0;
        
        t0 = System.nanoTime();
        Check r2 = checkWithJavaStack(text); // 标准库栈
        double ms2 = (System.nanoTime() - t0) / 1_000_000.0;
        
        System.out.printf("【%s】(N=%d 字符)%n", title, text.length());
        System.out.printf("  MyStack       : %s%s (%.4f ms)%n",
            r1.balance ? "平衡" : "不平衡", r1.message, ms1);
        System.out.printf("  java.util.Stack: %s%s (%.4f ms)%n",
            r2.balance ? "平衡" : "不平衡", r2.message, ms2);
        System.out.println("  两种实现结论一致: " + (r1.balance == r2.balance));
        System.out.println();
    }
    public static void main(String[] args) throws IOException {
        // ---------- 主测试：读取真实文件并检查（题目正文要求） ----------
        Path file = locateTestFile();
        String content = Files.readString(file, StandardCharsets.UTF_8); // UTF-8 一次读入全文
        System.out.println("======== 文件检查：" + file + " ========");
        runCase("文件内容", content);
        System.out.println("======== 内置补充用例 ========");
        runCase("平衡：混合括号嵌套", "a*(b+c)-{d/[e,f]}");
        runCase("不平衡：缺少右括号", "(a+b");
        runCase("不平衡：多余右括号", "a+b)");
        runCase("不平衡：交叉错配", "([)]");
        runCase("平衡：完整注释", "/* hello */");
        runCase("不平衡：注释未闭合", "/* hello");
        runCase("不平衡：无主注释尾", "hello */");
    }
}
