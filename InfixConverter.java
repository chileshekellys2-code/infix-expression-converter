import java.util.Stack;

/**
 * InfixConverter.java
 * Converts Infix expressions to Postfix and Prefix notations.
 * 
 * Supports: +, -, *, /, ^ operators and parentheses
 * 
 * Author: Assignment Solution
 */
public class InfixConverter {

    // ─────────────────────────────────────────────
    //  UTILITY: Operator Precedence
    // ─────────────────────────────────────────────
    private static int precedence(char op) {
        switch (op) {
            case '+':
            case '-': return 1;
            case '*':
            case '/': return 2;
            case '^': return 3;
            default:  return -1;
        }
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    private static boolean isOperand(char c) {
        return Character.isLetterOrDigit(c);
    }

    // ─────────────────────────────────────────────
    //  INFIX TO POSTFIX  (Shunting-Yard Algorithm)
    // ─────────────────────────────────────────────
    public static String infixToPostfix(String expression) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        expression = expression.replaceAll("\\s+", ""); // strip spaces

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // 1. Operand → directly to output
            if (isOperand(c)) {
                result.append(c).append(' ');
            }
            // 2. Left parenthesis → push onto stack
            else if (c == '(') {
                stack.push(c);
            }
            // 3. Right parenthesis → pop until matching '('
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop()).append(' ');
                }
                if (!stack.isEmpty()) stack.pop(); // discard '('
            }
            // 4. Operator → pop higher/equal precedence operators first
            else if (isOperator(c)) {
                // '^' is right-associative, so strictly greater for it
                while (!stack.isEmpty()
                        && stack.peek() != '('
                        && ((c != '^' && precedence(stack.peek()) >= precedence(c))
                         || (c == '^' && precedence(stack.peek()) > precedence(c)))) {
                    result.append(stack.pop()).append(' ');
                }
                stack.push(c);
            }
        }

        // 5. Pop remaining operators
        while (!stack.isEmpty()) {
            result.append(stack.pop()).append(' ');
        }

        return result.toString().trim();
    }

    // ─────────────────────────────────────────────
    //  INFIX TO PREFIX
    //  Strategy: reverse → convert to postfix → reverse result
    // ─────────────────────────────────────────────
    public static String infixToPrefix(String expression) {
        expression = expression.replaceAll("\\s+", "");

        // Step 1: Reverse the expression and swap brackets
        StringBuilder reversed = new StringBuilder();
        for (int i = expression.length() - 1; i >= 0; i--) {
            char c = expression.charAt(i);
            if      (c == '(') reversed.append(')');
            else if (c == ')') reversed.append('(');
            else               reversed.append(c);
        }

        // Step 2: Apply postfix conversion on the modified string
        // For prefix, '^' becomes left-associative in this reversed pass
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        String rev = reversed.toString();

        for (int i = 0; i < rev.length(); i++) {
            char c = rev.charAt(i);

            if (isOperand(c)) {
                result.append(c).append(' ');
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop()).append(' ');
                }
                if (!stack.isEmpty()) stack.pop();
            } else if (isOperator(c)) {
                while (!stack.isEmpty()
                        && stack.peek() != '('
                        && precedence(stack.peek()) > precedence(c)) {
                    result.append(stack.pop()).append(' ');
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            result.append(stack.pop()).append(' ');
        }

        // Step 3: Reverse the postfix result → prefix
        String[] tokens = result.toString().trim().split("\\s+");
        StringBuilder prefix = new StringBuilder();
        for (int i = tokens.length - 1; i >= 0; i--) {
            prefix.append(tokens[i]);
            if (i != 0) prefix.append(' ');
        }

        return prefix.toString();
    }

    // ─────────────────────────────────────────────
    //  MAIN – Test Cases
    // ─────────────────────────────────────────────
    public static void main(String[] args) {
        String[] testExpressions = {
            "A+B",
            "A+B*C",
            "(A+B)*C",
            "A+B*C-D/E",
            "(A+B)*(C-D)",
            "A^B^C",
            "A*(B+C)-D/(E+F)"
        };

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║         INFIX → POSTFIX & PREFIX CONVERTER                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();

        for (String expr : testExpressions) {
            System.out.println("  Infix    : " + expr);
            System.out.println("  Postfix  : " + infixToPostfix(expr));
            System.out.println("  Prefix   : " + infixToPrefix(expr));
            System.out.println("  ─────────────────────────────────────────────");
        }
    }
}
