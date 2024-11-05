package PrisonersProblem;

import java.util.Random;

public class PrisonersProblem_ChatGPT {
    // 囚犯数量
    private static final int NUM_PRISONERS = 100;
    // 盒子数量
    private static final int NUM_BOXES = 100;
    // 每个囚犯最多打开的盒子数，为盒子总数的一半
    private static final int MAX_TRIES = NUM_BOXES / 2;
    // 模拟的次数
    private static final int NUM_TRIALS = 1000000;
    // 成功次数
    private static int successfulTrials = 0;
    // 失败次数
    private static int failedTrials = 0;
    // 成功率
    private static double successRate = 0;

    public static void main(String[] args) {
        // 模拟多次实验
        for (int i = 0; i < NUM_TRIALS; i++) {
            if (simulate()) {
                successfulTrials++;
            }else {
                failedTrials++;
            }
        }

        // 计算成功的概率
        successRate = (double) successfulTrials / NUM_TRIALS;
        System.out.println("成功次数: " + successfulTrials);
        System.out.println("失败概率: " + failedTrials);
        System.out.println("成功概率: " + successRate);
    }

    // 模拟一次囚犯盒子问题
    public static boolean simulate() {
        // 创建一个数组表示100个盒子
        int[] boxes = new int[NUM_BOXES];
        for (int i = 0; i < NUM_BOXES; i++) {
            boxes[i] = i + 1;  // 每个盒子里写的号码是1到100
        }

        // 随机打乱盒子里的号码
        shuffleArray(boxes);

        // 模拟每个囚犯依次打开盒子
        for (int prisoner = 0; prisoner < NUM_PRISONERS; prisoner++) {
            int currentBox = prisoner;  // 囚犯从自己的编号对应的盒子开始
            int tries = 0;

            // 每个囚犯最多尝试打开50个盒子
            while (tries < MAX_TRIES) {
                int boxNumber = boxes[currentBox];  // 盒子里的号码
                if (boxNumber == prisoner + 1) {
                    break;  // 找到自己的号码
                }
                currentBox = boxNumber - 1;  // 转到下一个盒子
                tries++;
            }

            // 如果某个囚犯没有在50次以内找到自己的号码，实验失败
            if (tries == MAX_TRIES) {
                return false;
            }
        }

        // 如果所有囚犯都成功，返回true
        return true;
    }

    // 随机打乱数组
    public static void shuffleArray(int[] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}
