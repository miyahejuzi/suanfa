package cn.miya.suanfashiyan.shiyan1;

/**
 * 1.实验题目
 * 求两个自然数m和n的最大公约数。
 * 2. 实验目的
 * ⑴ 复习数据结构课程的相关知识，实现课程间的平滑过渡；
 * ⑵ 掌握并应用算法的数学分析和后验分析方法；
 * ⑶ 理解这样一个观点：不同的算法能够解决相同的问题，这些算法的解题思路不同，复杂程度不同，解题效率也不同。
 * 3. 实验要求
 * ⑴ 至少设计出三个版本的求最大公约数算法；
 * ⑵ 对所设计的算法采用大O符号进行时间复杂性分析；
 * ⑶上机实现算法，并用计数法和计时法分别测算算法的运行时间；
 * ⑷ 通过分析对比，得出自己的结论。
 * 补齐本文档中的第4项和第5项。
 * 4.实验步骤及过程
 *
 * @author orange
 */
public class CommonDivisor {
    public static void main(String[] args) {
        int gcd = GCD(12, 20);
        System.out.println("gcd = " + gcd);
    }

    /**
     * 以下使用辗转相除法实现
     */
    private static int GCD(int a, int b) {
        if (b == 0) {
            return a;
        }
        return a % b == 0 ? b : GCD(b, a % b);
    }


}

