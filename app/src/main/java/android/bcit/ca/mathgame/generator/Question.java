package android.bcit.ca.mathgame.generator;

public class Question {
    private Operator operator;
    private int a;
    private int b;
    private int answer;

    public Question(int a, int b, Operator operator, int answer) {
        this.a = a;
        this.b = b;
        this.operator = operator;
        this.answer = answer;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getAnswer() {
        return answer;
    }

    public Operator getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return a + " " + operator + " " + b;
    }
}
