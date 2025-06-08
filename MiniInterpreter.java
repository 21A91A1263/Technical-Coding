import java.util.*;

public class MiniInterpreter {

    private final Map<String, Integer> variables = new HashMap<>();

    public Integer evaluateExpression(String expr) {
        expr = replaceVariables(expr);
        return evaluateSimpleExpression(expr);
    }

    private String replaceVariables(String expr) {
        for (String var : variables.keySet()) {
            expr = expr.replaceAll("\\b" + var + "\\b", variables.get(var).toString());
        }
        return expr;
    }

    // Evaluate expressions like "10 + 5", "4 * 3", "10 > 5"
    private Integer evaluateSimpleExpression(String expr) {
        String[] tokens;

        if (expr.contains("==")) {
            tokens = expr.split("==");
            return parseInt(tokens[0]) == parseInt(tokens[1]) ? 1 : 0;
        } else if (expr.contains(">")) {
            tokens = expr.split(">");
            return parseInt(tokens[0]) > parseInt(tokens[1]) ? 1 : 0;
        } else if (expr.contains("<")) {
            tokens = expr.split("<");
            return parseInt(tokens[0]) < parseInt(tokens[1]) ? 1 : 0;
        } else if (expr.contains("+")) {
            tokens = expr.split("\\+");
            return parseInt(tokens[0]) + parseInt(tokens[1]);
        } else if (expr.contains("-")) {
            tokens = expr.split("-");
            return parseInt(tokens[0]) - parseInt(tokens[1]);
        } else if (expr.contains("*")) {
            tokens = expr.split("\\*");
            return parseInt(tokens[0]) * parseInt(tokens[1]);
        } else if (expr.contains("/")) {
            tokens = expr.split("/");
            return parseInt(tokens[0]) / parseInt(tokens[1]);
        } else {
            return parseInt(expr);
        }
    }

    private int parseInt(String s) {
        return Integer.parseInt(s.trim());
    }

    private Integer executeLine(String line) {
        line = line.trim();

        if (line.startsWith("let ")) {
            String[] parts = line.substring(4).split("=");
            String varName = parts[0].trim();
            String expr = parts[1].trim();
            int value = evaluateExpression(expr);
            variables.put(varName, value);
            return null;
        }

        if (line.startsWith("if ")) {
            int thenIndex = line.indexOf("then");
            int elseIndex = line.indexOf("else");

            String condition = line.substring(3, thenIndex).trim();
            String thenExpr = line.substring(thenIndex + 4, elseIndex).trim();
            String elseExpr = line.substring(elseIndex + 4).trim();

            int condResult = evaluateExpression(condition);
            return condResult != 0 ? evaluateExpression(thenExpr) : evaluateExpression(elseExpr);
        }

        return evaluateExpression(line);
    }

    public void run(String input) {
        String[] lines = input.split("\\n");
        for (String line : lines) {
            Integer result = executeLine(line);
            if (result != null) {
                System.out.println(result);
            }
        }
    }

    public static void main(String[] args) {
        String code = """
                let x = 10
                let y = 5
                if x > y then x else y
                let z = x + y
                z
                """;

        MiniInterpreter interpreter = new MiniInterpreter();
        interpreter.run(code);
    }
}