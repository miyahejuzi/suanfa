package cn.miya.suanfashiyan.shiyan2;

/**
 * 1. 实验题目
 * 给定一个文本，在该文本中查找并定位任意给定字符串。
 * 2．实验目的
 * ⑴ 深刻理解并掌握蛮力法的设计思想；
 * ⑵ 提高应用蛮力法设计算法的技能；
 * ⑶ 理解这样一个观点：用蛮力法设计的算法，一般来说，经过适度的努力后，都可以对算法的第一个版本进行一定程度的改良，改进其时间性能。
 * 3. 实验要求
 * ⑴ 实现BF算法；
 * ⑵ 实现BF算法的改进算法：KMP算法和BM算法；
 * ⑶ 对上述三个算法进行时间复杂性分析，并设计实验程序验证分析结果。
 * 4.实验步骤及过程
 *
 * @author orange
 */
public class CharacterMatch {
    public static void main(String[] args) {
        String str1 = "abbccaa";
        String str2 = "aa";
        System.out.println(bf(str1, str2));
    }

    public static int bf(String s1, String s2) {
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();
        int i = 0;
        int j = 0;
        while (i < s1.length() && j < s2.length()) {
            if (c1[i] == c2[j]) {
                i++;
                j++;
            } else {
                i = i - j + 1;
                j = 0;
            }
        }
        if (j == s2.length()) {
            return i - j;
        } else {
            return -1;
        }
    }
}
