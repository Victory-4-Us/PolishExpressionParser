// The parser evaluates an arithmetic expression using recursive descent
public class Parser {
    private Tokenizer tokenizer;
    private Token currentToken; // The current token we're looking at

    //constructor
    Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
        this.currentToken = tokenizer.nextToken(); // Initialize by reading the first token
    }

    //"eats" the current token so the next can be processed
    private void eat(TokenType expected) {
        if (currentToken.type == expected) {
            currentToken = tokenizer.nextToken();
        } else {
            throw new RuntimeException("Error in eat: Expected " + expected + " but got " + currentToken.type);
        }
    }

    //parses through the expression
    int parseExpr() {
        TokenType op = currentToken.type;

        if (op == TokenType.NUMBER) {
            int value = Integer.parseInt(currentToken.text);
            eat(currentToken.type);

            return value;
        }

        eat(currentToken.type);
        //recursion
        int left = parseExpr();
        int right = parseExpr();

        switch (op) {
            case PLUS:
            case MINUS:
            case MUL:
            case DIV:
                return polishExpr(op, left, right);
            default:
                throw new RuntimeException("Error in parseExpr: Expected " + op + " but got " + currentToken.type);
        }
    }

    // solves and returns the arithmetic expression as a result
    private int polishExpr(TokenType op, int left, int right) {
            int result;
            switch (op) {
                case PLUS:
                    result = left + right;
                    break;
                case MINUS:
                    result = left - right;
                    break;
                case DIV:
                    result = left / right;
                    break;
                case MUL:
                    result = left * right;
                    break;
                default:
                    throw new RuntimeException("Unexpected token in Polish Expression: " + op);
            }
            return result;
    }
}