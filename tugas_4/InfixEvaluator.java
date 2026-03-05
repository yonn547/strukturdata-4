import java.util.*;

class Stack {
    private int maxSize;
    private String[] stackArray;
    private int top;
    
    public Stack(int size) {
        maxSize = size;
        stackArray = new String[maxSize];
        top = -1;
    }
    
    public void push(String item) {
        stackArray[++top] = item;
        System.out.println("Push: " + item);
    }
    
    public String pop() {
        String item = stackArray[top--];
        System.out.println("Pop: " + item);
        return item;
    }
    
    public String peek() {
        return stackArray[top];
    }
    
    public boolean isEmpty() {
        return (top == -1);
    }
    
    public boolean isFull() {
        return (top == maxSize - 1);
    }
    
    public void displayStack(String message) {
        System.out.print(message + " [");
        for (int i = 0; i <= top; i++) {
            System.out.print(stackArray[i]);
            if (i < top) System.out.print(", ");
        }
        System.out.println("]");
    }
}

public class InfixEvaluator {
    
    // Menentukan prioritas operator
    private static int precedence(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "^":
                return 3;
            default:
                return -1;
        }
    }
    
    // Mengecek apakah karakter adalah operator
    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || 
               token.equals("*") || token.equals("/") || 
               token.equals("^");
    }
    
    // Mengecek apakah string adalah angka
    private static boolean isNumeric(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    // Konversi infix ke postfix
    public static String infixToPostfix(String expression) {
        System.out.println("\n=== KONVERSI INFIX KE POSTFIX ===");
        String[] tokens = expression.split(" ");
        Stack stack = new Stack(tokens.length);
        StringBuilder postfix = new StringBuilder();
        
        for (String token : tokens) {
            if (isNumeric(token)) {
                // Jika token adalah operand, tambahkan ke postfix
                postfix.append(token).append(" ");
                System.out.println("Operand: " + token + " -> langsung ke output");
            } 
            else if (token.equals("(")) {
                stack.push(token);
                stack.displayStack("Stack setelah push '('");
            } 
            else if (token.equals(")")) {
                // Pop sampai menemukan '('
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    postfix.append(stack.pop()).append(" ");
                    stack.displayStack("Stack setelah pop operator");
                }
                if (!stack.isEmpty() && stack.peek().equals("(")) {
                    stack.pop(); // Pop '('
                    System.out.println("Pop: ( (dibuang)");
                }
                stack.displayStack("Stack setelah memproses )");
            } 
            else if (isOperator(token)) {
                // Untuk operator
                while (!stack.isEmpty() && !stack.peek().equals("(") && 
                       precedence(token) <= precedence(stack.peek())) {
                    postfix.append(stack.pop()).append(" ");
                    stack.displayStack("Stack setelah pop operator dengan prioritas lebih tinggi/sama");
                }
                stack.push(token);
                stack.displayStack("Stack setelah push operator " + token);
            }
        }
        
        // Pop semua sisa operator
        while (!stack.isEmpty()) {
            postfix.append(stack.pop()).append(" ");
            stack.displayStack("Stack setelah pop sisa operator");
        }
        
        return postfix.toString().trim();
    }
    
    // Evaluasi postfix expression
    public static double evaluatePostfix(String postfix) {
        System.out.println("\n=== EVALUASI POSTFIX ===");
        String[] tokens = postfix.split(" ");
        Stack stack = new Stack(tokens.length);
        
        for (String token : tokens) {
            if (isNumeric(token)) {
                // Push operand ke stack
                stack.push(token);
                stack.displayStack("Stack setelah push operand " + token);
            } 
            else if (isOperator(token)) {
                // Pop dua operand
                double operand2 = Double.parseDouble(stack.pop());
                double operand1 = Double.parseDouble(stack.pop());
                
                System.out.println("Menghitung: " + operand1 + " " + token + " " + operand2);
                
                double result = 0;
                switch (token) {
                    case "+":
                        result = operand1 + operand2;
                        break;
                    case "-":
                        result = operand1 - operand2;
                        break;
                    case "*":
                        result = operand1 * operand2;
                        break;
                    case "/":
                        if (operand2 == 0) {
                            throw new ArithmeticException("Pembagian dengan nol!");
                        }
                        result = operand1 / operand2;
                        break;
                    case "^":
                        result = Math.pow(operand1, operand2);
                        break;
                }
                
                System.out.println("Hasil: " + result);
                stack.push(String.valueOf(result));
                stack.displayStack("Stack setelah push hasil");
            }
        }
        
        // Hasil akhir ada di top stack
        return Double.parseDouble(stack.pop());
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("PROGRAM EVALUASI EKSPRESI ARITMATIKA");
        System.out.println("======================================");
        System.out.println("Catatan: Gunakan spasi antar token");
        System.out.println("Contoh: ( 3 + 5 ) * 2 - 8 / 4");
        System.out.print("\nMasukkan ekspresi infix: ");
        
        String infix = scanner.nextLine();
        
        try {
            // Konversi ke postfix
            String postfix = infixToPostfix(infix);
            System.out.println("\nHasil Postfix: " + postfix);
            
            // Evaluasi postfix
            double result = evaluatePostfix(postfix);
            System.out.println("\n======================================");
            System.out.println("Hasil Akhir: " + result);
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        scanner.close();
    }
}