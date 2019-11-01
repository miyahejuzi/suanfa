package cn.miya.suanfashiyan.shiyan4;

import cn.hutool.aop.ProxyUtil;
import cn.hutool.aop.aspects.TimeIntervalAspect;
import cn.hutool.core.lang.Console;

/**
 * 实验四——最大子段和问题
 * 1. 实验题目
 * 给定由n个整数组成的序列(a1, a2, …, an)，最大子段和问题要求该序列形如 的最大值（1≤i≤j≤n），
 * 当序列中所有整数均为负整数时，其最大子段和为0。
 * 2．实验目的
 * ⑴ 掌握分治法求解最大子段和问题；
 * ⑵ 掌握动态规划法求解最大子段和问题。
 * ⑶ 对比求解最大子段和的上述两种算法。
 * 3. 实验要求
 * ⑴ 设计并实现用分治法求解最大子段和问题。
 * ⑵ 设计并实现用分治法求解最大子段和问题。
 * ⑶ 对上述两个算法进行时间复杂性分析。
 * 4.实验步骤及过程
 * 5.总结
 *
 * @author miya
 * @date 19-10-16
 */
public class MaxSubNumber {

    /**
     * 主要用来计算运行时间
     */
    private static final MaxSubNumber PROXY = ProxyUtil.proxy(new MaxSubNumber(), new TimeIntervalAspect());

    /**
     * 测试用的数据
     */
    private static int[] numbers = new int[]{-2, -1};
//    private static int[] numbers = new int[]{-6, 2, 4, -7, 5, 3, 2, -1, 6, -9, 10, -2};

    /**
     * 使用分治法实现
     */
    public int maxSubSum1(int[] numbers, int left, int right) {
        if (left == right) {
            return numbers[left] < 0 ? 0 : numbers[left];
        }
        int mid = (left + right) / 2;
        int sumLeft = 0, maxSumLeft = 0;
        for (int i = mid; i >= 0; i--) {
            sumLeft += numbers[i];
            maxSumLeft = Math.max(sumLeft, maxSumLeft);
        }
        int sumRight = 0, maxSumRight = 0;
        for (int i = mid + 1; i < right + 1; i++) {
            sumRight += numbers[i];
            maxSumRight = Math.max(sumRight, maxSumRight);
        }
        int maxSubSumLeft = maxSubSum1(numbers, left, mid);
        int maxSubSumRight = maxSubSum1(numbers, mid + 1, right);
        return Math.max(Math.max(maxSubSumLeft, maxSubSumRight), maxSumLeft + maxSumRight);
    }

    /**
     * 使用蛮力法实现
     */
    public int maxSubSum2(int[] numbers) {
        int maxSum = 0, currentSum = 0;
        for (int i = 0; i < numbers.length; i++) {
            currentSum = 0;
            for (int j = i; j < numbers.length; j++) {
                currentSum += numbers[j];
                maxSum = Math.max(currentSum, maxSum);
            }
        }
        return maxSum;
    }

    /**
     * 使用动态规划实现
     */
    public int maxSubSum3(int[] numbers) {
        int maxSum = 0;
        int currentSum = 0;
        for (int number : numbers) {
            currentSum += number;
            if (currentSum < 0) {
                currentSum = 0;
            }
            if (maxSum < currentSum) {
                maxSum = currentSum;
            }
        }
        return maxSum;
    }

    /**
     * Method [cn.miya.shiyan4.MaxSubNumber.maxSubSum1] execute spend [0]ms
     * 分治法最大子段和 : 0.
     * Method [cn.miya.shiyan4.MaxSubNumber.maxSubSum2] execute spend [25]ms
     * 蛮力法最大子段和 : 0.
     * Method [cn.miya.shiyan4.MaxSubNumber.maxSubSum3] execute spend [1]ms
     * 动态规划最大子段 : 0.
     * <p>
     * ps: 若子段和小于 0, 则返回 0;
     */
    public static void main(String[] args) {
        long currentTime = System.currentTimeMillis();
        final int maxSubSum1Result = new MaxSubNumber().maxSubSum1(numbers, 0, numbers.length - 1);
        Console.log("Method [{}.{}] execute spend [{}]ms", "cn.miya.shiyan4.MaxSubNumber", "maxSubSum1", currentTime - System.currentTimeMillis());
        System.out.printf("分治法最大子段和 : %d.\n", maxSubSum1Result);
        System.out.printf("蛮力法最大子段和 : %d.\n", PROXY.maxSubSum2(numbers));
        System.out.printf("动态规划最大子段 : %d.\n", PROXY.maxSubSum3(numbers));
    }

}















