package cn.miya.suanfashiyan.shiyan3;

import java.util.Arrays;

/**
 * 串匹配的其他算法 kmp
 *
 * @author orange
 */
public class CharacterMatchOther {
    public static void main(String[] args) {
        String a = "ababa";
        String b = "ssdfgasdbababa";
        int[] next = kmpnext(a);
        int res = KMP(b, a, next);
        System.out.println(res);
        System.out.println("next = " + Arrays.toString(next));
    }

    public static int[] kmpnext(String dest) {
        int[] next = new int[dest.length()];
        next[0] = 0;
        int j = 0;
        for (int i = 1; i < dest.length(); i++) {
            while (j > 0 && dest.charAt(j) != dest.charAt(i)) {
                j = next[j - 1];
            }
            if (dest.charAt(i) == dest.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }

    /**
     * @param str  str文本串
     * @param dest 模式串
     */
    public static int KMP(String str, String dest, int[] next) {
        for (int i = 0, j = 0; i < str.length(); i++) {
            while (j > 0 && str.charAt(i) != dest.charAt(j)) {
                j = next[j - 1];
            }
            if (str.charAt(i) == dest.charAt(j)) {
                j++;
            }
            if (j == dest.length()) {
                return i - j + 1;
            }
        }
        return 0;
    }
}
