public class Main {

    private static final String[] names = {"A", "B", "C", "D", "E"};
    private static final Long[] times = new Long[names.length];

    public static void registerTime(int index, long tiempo) {
            times[index] = tiempo;
    }

    public static void showTimes() {
        System.out.println("Tiempos de ejecución de los algoritmos:");
        for (int i = 0; i < names.length; i++) {
            System.out.println(names[i] + ": " + times[i]);
        }
    }

    public static int A(int N) {
        int numberOne = 0;
        int numberTwo;
        if (numberOne > N) {
            numberTwo = N;
        } else {
            numberOne = numberOne + 1;
            numberTwo = numberOne;
        }
        return numberTwo;
    }

    public static int B(int N) {
        int numberOne = 0;
        int numberTwo = 0;
        for (int i = 0; i < N; i++) {
            numberOne = numberOne + 1;
            numberTwo = numberTwo + numberOne;
        }
        return numberTwo;
    }

    public static int C(int N) {
        int number = 0;
        for (int i = 0; i < N; i++) {
            number += 1;
            number += 2;
        }
        for (int j = 0; j < N; j++) {
            number += 3;
            number += 4;
        }
        return number;
    }

    public static int D(int E, int x) {
        int number = 0;
        for (int i = 0; i < E; i++) {
            if (i % 2 == 0) {
                number += 1;
                for (int j = 0; j < x; j++) {
                    number += 2;
                    number += 3;
                }
            }
        }
        return number;
    }

    public static int E(int N) {
        int numberOne = 0;
        int numberTwo = 1;
        while (numberTwo < N) {
            numberOne += 1;
            int limit = 0;
            while (limit < numberTwo) {
                numberOne += 1;
                limit++;
            }
            numberTwo++;
        }
        return numberOne;
    }

    public static void executeAlgorithms() {
        int[] tests = {10, 100, 500};

        for (int number : tests) {
            System.out.println("Resultado para: " + number);
            runTests(number);
            printResultsTable(number);
        }
    }

    private static void runTests(int number) {
        for (int i = 0; i < names.length; i++) {
            long start = System.nanoTime();
            int resultado = switch (names[i]) {
                case "A" -> A(number);
                case "B" -> B(number);
                case "C" -> C(number);
                case "D" -> D(number, number);
                case "E" -> E(number);
                default -> 0;
            };
            long end = System.nanoTime();
            times[i] = end - start;
        }
    }

    private static void printResultsTable(int N) {
        System.out.printf("%-10s%-10s%-15s\n", "Algoritmo", "N", "Tiempo (s)");
        for (int i = 0; i < names.length; i++) {
            double seconds = times[i] / 1_000_000_000.0;
            System.out.printf("%-10s%-10d%-15.9f\n", names[i], N, seconds);
        }
    }

    public static void main(String[] args) {
        //executeAlgorithms();
        Factorial factorial = new Factorial();

        int values = 11;
        for (int i = 0; i <= values; i++) {
            factorial.measureFactorialComplexity(i);
        }
    }
}
