public class Pattern {
    public static void print(String text) {
        int n = text.length();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                System.out.print(text.charAt(j) + " ");
            }
            int spaces = (n - i - 1) * 4;
            for (int s = 0; s < spaces; s++) {
                System.out.print(" ");
            }
            for (int j = i; j >= 0; j--) {
                System.out.print(text.charAt(j) + " ");
            }
            System.out.println();
        }
        for (int i = n - 2; i >= 0; i--) {
            // Left side
            for (int j = 0; j <= i; j++) {
                System.out.print(text.charAt(j) + " ");
            }
            int spaces = (n - i - 1) * 4;
            for (int s = 0; s < spaces; s++) {
                System.out.print(" ");
            }
            for (int j = i; j >= 0; j--) {
                System.out.print(text.charAt(j) + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        print("HELLO");
    }
}
