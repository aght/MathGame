package android.bcit.ca.mathgame.generator;

public enum Operator {
    ADDITION("\u002B"), SUBTRACTION("\u2212"), MULTIPLICATION("\u00D7"), DIVISION("\u00F7");

    private String operator;

    Operator(String operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return operator;
    }
}
