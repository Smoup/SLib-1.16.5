package splug.slib.utils.strings;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringsSimilarity {
    public static double calculate(char[] array1, char[] array2) {
        final int distance = calculateLevenshteinDistance(array1, array2);
        final int maxLength = Math.max(array1.length, array2.length);

        if (maxLength == 0) {
            return 100.0;
        }

        return (1 - ((double) distance / maxLength)) * 100;
    }

    private static int calculateLevenshteinDistance(char[] array1, char[] array2) {
        final int len1 = array1.length;
        final int len2 = array2.length;
        final int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                int cost = (array1[i - 1] == array2[j - 1]) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
            }
        }

        return dp[len1][len2];
    }
}
