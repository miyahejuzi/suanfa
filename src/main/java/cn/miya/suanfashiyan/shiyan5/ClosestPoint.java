package cn.miya.suanfashiyan.shiyan5;

import cn.hutool.aop.ProxyUtil;
import cn.hutool.aop.aspects.TimeIntervalAspect;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 实验五——最近对问题
 * 1. 实验题目
 * 设p1=(x1, y1), p2=(x2, y2), …, pn=(xn, yn)是平面上n个点构成的集合S，设计算法找出集合S中距离最近的点对。
 * 2. 实验目的
 * （1）进一步掌握递归算法的设计思想以及递归程序的调试技术；
 * （2）理解这样一个观点：分治与递归经常同时应用在算法设计之中。
 * 3. 实验要求
 * （1）分别用蛮力法和分治法求解最近对问题；
 * （2）分析算法的时间性能，设计实验程序验证分析结论。
 * 4.实验步骤及过程
 * 5.总结
 *
 * @author miya
 * @date 19-10-30
 */
public class ClosestPoint {

    /**
     * 生成代理代理对象, 主要是用来知道, 方法执行的时间
     */
    private static ClosestPoint solution = ProxyUtil.proxy(new ClosestPoint(), new TimeIntervalAspect());

    private static List<Point> points = new ArrayList<>();

    // 初始化 points
    static {
        points.add(new Point(1, 1));
        points.add(new Point(1, 4));
        points.add(new Point(4, 1));
        points.add(new Point(-1, 1));
        points.add(new Point(7, 7));
    }

    /**
     * 蛮力法穷举找出最优解
     */
    public ClosestPointInfo findPointByExhaustivity(List<Point> points) {
        ClosestPointInfo result = new ClosestPointInfo();

        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); ++j) {
                Point start = points.get(i);
                Point end = points.get(j);

                ClosestPointInfo distance = calculatedTwoPointDistance(start, end);
                if (distance.length < result.length) {
                    result = distance;
                }
            }
        }

        return result;
    }

    /**
     * 使用递归算法找出最优解, 类似与 `最短子序列` 里面分治法的计算方式
     */
    public ClosestPointInfo findPointByRecursion(List<Point> points) {
        points.sort(Comparator.comparingInt(p -> p.x));
        return doFindPointByRecursion(points, 0, points.size() - 1);
    }

    private ClosestPointInfo doFindPointByRecursion(List<Point> points, int left, int right) {
        ClosestPointInfo result = new ClosestPointInfo();
        if (left == right) {
            return result;
        } else if (left + 1 == right) {
            return calculatedTwoPointDistance(points.get(left), points.get(right));
        }
        // 1, 分区, 并求取两点之间的最小值
        int middle = (left + right) >> 1;
        ClosestPointInfo maxLeft = doFindPointByRecursion(points, left, middle);
        ClosestPointInfo maxRight = doFindPointByRecursion(points, middle, right);
        result = maxLeft.length <= maxRight.length ? maxLeft : maxRight;

        // 2, 计算两个点分别在左右分区的情况
        // 需要注意符合条件的点是距离中间线 < result.length 的所有点都有可能
        List<Integer> validPointIndex = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            if (Math.abs(points.get(middle).x - points.get(i).x) <= result.length) {
                validPointIndex.add(i);
            }
        }
        // 基于索引, 进一步计算区间内最小两点距离
        for (int i = 0; i < validPointIndex.size() - 1; i++) {
            for (int j = i + 1; j < validPointIndex.size(); j++) {
                // 如果区间内的两点 y 轴距离大于 result.length. 则没必要计算了, 因为, 它们的距离肯定大于 result.length
                if (Math.abs(points.get(validPointIndex.get(i)).y - points.get(validPointIndex.get(j)).y) > result.length) {
                    continue;
                }
                ClosestPointInfo pointInfo = calculatedTwoPointDistance(
                        points.get(validPointIndex.get(i)), points.get(validPointIndex.get(j)));
                result = (pointInfo.length < result.length) ? pointInfo : result;
            }
        }

        return result;
    }

    /**
     * ( a ^ 2 + b ^ 2 ) ^ ( 1 / 2 )
     */
    private ClosestPointInfo calculatedTwoPointDistance(Point a, Point b) {
        final double sqrt = Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
        return new ClosestPointInfo(sqrt, a, b);
    }

    /**
     * Method [cn.miya.shiyan5.ClosestPoint.findPointByExhaustivity] execute spend [13]ms
     * byExhaustivity = 最短距离为: 2.0, 两个点分别是: {1, 1}, {-1, 1}
     * Method [cn.miya.shiyan5.ClosestPoint.findPointByRecursion] execute spend [60]ms
     * byRecursion = 最短距离为: 2.0, 两个点分别是: {-1, 1}, {1, 1}
     * <p>
     * ps: 分治时间亏在频繁的创建对象, 并且数据量也太少了
     */
    public static void main(String[] args) {
        final ClosestPointInfo byExhaustivity = solution.findPointByExhaustivity(points);
        System.out.println("byExhaustivity = " + byExhaustivity);
        final ClosestPointInfo byRecursion = solution.findPointByRecursion(points);
        System.out.println("byRecursion = " + byRecursion);
    }

    /**
     * 点的数据类型
     */
    static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "{" + x + ", " + y + '}';
        }
    }

    /**
     * 用于保存输出信息
     */
    static class ClosestPointInfo {
        double length = Double.MAX_VALUE;
        Point first = null;
        Point second = null;

        ClosestPointInfo() {
        }

        ClosestPointInfo(double length, Point first, Point second) {
            this.length = length;
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return "最短距离为: " + length + ", 两个点分别是: " + first + ", " + second;
        }
    }

}




















