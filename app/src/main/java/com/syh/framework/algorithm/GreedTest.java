package com.syh.framework.algorithm;

import java.util.ArrayList;

/**
 * Created by shenyonghe on 2020/4/23.
 * 贪心
 * 贪心算法，又名贪婪法，是寻找最优解问题的常用方法，这种方法模式一般将求解过程分成若干个步骤，
 * 但每个步骤都应用贪心原则，选取当前状态下最好/最优的选择（局部最有利的选择），
 * 并以此希望最后堆叠出的结果也是最好/最优的解。
 * <p>
 * 步骤：
 * 1 从某个初始解出发
 * 2 采用迭代的过程，当可以向目标前进一步时，就根据局部最优策略，得到一部分解，缩小问题规模
 * 3 将所有解综合起来
 * <p>
 * 优点：简单，高效，省去了为了找最优解可能需要穷举操作，通常作为其它算法的辅助算法来使用；
 * 缺点：不从总体上考虑其它可能情况，每次选取局部最优解，不再进行回溯处理，所以很少情况下得到最优解。
 */
public class GreedTest {

    private static final int TWENTYFINEFEN = 25;
    private static final int TENFEN = 10;
    private static final int FIVEFEN = 5;
    private static final int ONEFEN = 1;

    public static void main(String[] args) {
        change();
        maxValue();
    }


    /**
     * 找零问题
     * 假设你开了间小店，不能电子支付，钱柜里的货币只有 25 分、10 分、5 分和 1 分四种硬币，如果你是售货员且要找给客户 41 分钱的硬币，
     * 如何安排才能找给客人的钱既正确且硬币的个数又最少？
     * 1 找给顾客sum_money=41分钱，可选择的是25 分、10 分、5 分和 1 分四种硬币。能找25分的，不找10分的原则，初次先找给顾客25分
     * 2 还差顾客sum_money=41-25=16。然后从25 分、10 分、5 分和 1 分四种硬币选取局部最优的给顾客，也就是选10分的，
     * 此时sum_money=16-10=6。重复迭代过程，还需要sum_money=6-5=1,sum_money=1-1=0。至此，顾客收到零钱，交易结束
     * 3 此时41分，分成了1个25，1个10，1个5，1个1，共四枚硬币
     */
    public static void change() {
        int sum = 41;
        int num25 = 0, num10 = 0, num5 = 0, num1 = 0;
        while (sum >= TWENTYFINEFEN) {
            num25++;
            sum -= TWENTYFINEFEN;
        }
        while (sum >= TENFEN) {
            num10++;
            sum -= TENFEN;
        }
        while (sum >= FIVEFEN) {
            num5++;
            sum -= FIVEFEN;
        }
        while (sum >= ONEFEN) {
            num1++;
            sum -= ONEFEN;
        }
        System.out.println("25分硬币数：" + num25);
        System.out.println("10分硬币数：" + num10);
        System.out.println("5分硬币数：" + num5);
        System.out.println("1分硬币数：" + num1);
    }

    /**
     * 背包最大价值问题
     * 有一个背包，最多能承载重量为 C=150的物品，现在有7个物品（物品不能分割成任意大小），编号为 1~7，
     * 重量分别是 wi=[35,30,60,50,40,10,25]，价值分别是 pi=[10,40,30,50,35,40,30]，
     * 现在从这 7 个物品中选择一个或多个装入背包，要求在物品总重量不超过 C 的前提下，所装入的物品总价值最高。
     * 1 铵价格重量比[0.286,1.333,0.5,1.0,0.875,4.0,1.2] 6,2,7,4,5,3,1
     * 2 根据这个策略最终选择装入背包的物品编号依次是 6、2、7、4、1
     * 3 此时包中物品的总重量是 150，总价值是 170
     */
    public static void maxValue() {
        ArrayList<Product> list = new ArrayList<>();
        list.add(new Product(10, 40));
        list.add(new Product(30, 40));
        list.add(new Product(25, 30));
        list.add(new Product(50, 50));
        list.add(new Product(40, 35));
        list.add(new Product(60, 30));
        list.add(new Product(35, 10));


        int totalWeight = 150;

        for (int i = 0; i < list.size(); i++) {
            Product product = list.get(i);
            if (totalWeight >= product.getWeight()) {
                totalWeight -= product.getWeight();
                product.addNum();
            }
        }

        for (int i = 0; i < list.size(); i++) {
            Product product = list.get(i);
            System.out.println("重量：" + product.getWeight() + "= " + product.getNum());
        }
    }

    public static class Product {
        private int weight;
        private int value;
        private int num;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public void addNum() {
            this.num++;
        }

        public Product(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
