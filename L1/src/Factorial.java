public class Factorial {

    private void permute(boolean[] used, int limit, int n) {
        if (limit == n) {
            return;
        }
        for (int i = 0; i < n; i++) {
            if (!used[i]) {
                used[i] = true;
                permute(used, limit + 1, n);
                used[i] = false;
            }
        }
    }

    public void measureFactorialComplexity(int n) {
        long start = System.nanoTime();
        permute(new boolean[n], 0, n);
        long end = System.nanoTime();
        double seconds = (end - start) / 1_000_000_000.0;

        System.out.printf("%-10s%-15s\n", "Factorial", "Tiempo (s)");
        System.out.printf("%-10s%-10d%-15.9f\n", "N:", n, seconds);
    }
}