package examples.problems.palindrome;

public class Palindrome {
    public static void main(String[] args) {
        System.out.println(isPalindrome("Ili"));
    }

    private static boolean isPalindrome(String str) {
        String palindrome = new StringBuilder(str)
                .reverse()
                .toString();
        return palindrome.equalsIgnoreCase(str);
    }

}
