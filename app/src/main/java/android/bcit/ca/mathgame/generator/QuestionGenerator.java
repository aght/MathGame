package android.bcit.ca.mathgame.generator;

import android.bcit.ca.mathgame.utility.RandomNumberGenerator;

public class QuestionGenerator {

    private static final int MIN_RANGE = 0;

    private static final int MAX_RANGE = 100;

    public static Question generateQuestion() {
        int a = RandomNumberGenerator.randomInt(MIN_RANGE, MAX_RANGE);
        int b = RandomNumberGenerator.randomInt(MIN_RANGE, MAX_RANGE);
        Operator operator = generateOperator();

        return resolveQuestion(a, b, operator);
    }

    private static Question resolveQuestion(int a, int b, Operator operator) {
        switch (operator) {
            case SUBTRACTION:
                if (a < b) {
                    int temp = b;
                    b = a;
                    a = temp;
                }
                break;
            case DIVISION:
                double answer = -1;
                while (!isValidAnswer(answer)) {
                    a = RandomNumberGenerator.randomInt(MIN_RANGE, MAX_RANGE);
                    b = RandomNumberGenerator.randomInt(MIN_RANGE, MAX_RANGE);

                    if (isPrime(a) || a == 0 || b == 1 || a == b) {
                        continue;
                    }

                    answer = answerQuestion(a, b, operator);
                }
                break;
            default:
                break;
        }

        return new Question(a, b, operator, (int) answerQuestion(a, b, operator));
    }

    private static boolean isPrime(int n) {
        if (n % 2 == 0) return false;

        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0)
                return false;
        }

        return true;
    }

    private static boolean isValidAnswer(double answer) {
        return answer % 1 == 0 && !(answer < 0);
    }

    private static double answerQuestion(int a, int b, Operator operator) {
        switch (operator) {
            case ADDITION:
                return a + b;
            case SUBTRACTION:
                return a - b;
            case MULTIPLICATION:
                return a * b;
            default:
                return ((double) a) / b;
        }
    }

    private static Operator generateOperator() {
        return Operator.values()[RandomNumberGenerator.randomInt(0, Operator.values().length - 1)];
    }
}
