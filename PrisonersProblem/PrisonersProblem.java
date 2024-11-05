package PrisonersProblem;

import java.util.*;

public class PrisonersProblem {
    //TIP 人数/盒子数
    private static final Integer num = 100;

    //TIP 循环次数
    private static final Integer cycle = 1000000;

    //TIP 每个人最多可以打开的盒子数
    private static final Integer maxIndex = num / 2;

    // 每个人打开的盒子数，大于等于51就判定此次循环失败，因为要对递归进行计数，所以只能放在类里了
    private static int openNum = 0;

    //TIP 成功次数
    private static Integer successNum = 0;

    //TIP 失败次数
    private static Integer errorNum = 0;

    //TIP 成功率
    private static double successRate;

    public static void main(String[] args) {

        for (int i = 0; i < cycle; i++) {
            // 创建一个装有随机且不重复的号码牌的盒子
            Map<Integer,Integer> boxes = creatBoxes();
            // 每个循环所有人打开的盒子总数，超过50个就判定此次循环成功，因为不会再出现单次超过50的情况了
            int allOpenNum = 0;
            // 每次循环开始前假定成功
            boolean isSuccess = true;
            // 每个人打开的盒子数，大于等于51就判定此次循环失败
            openNum = 0;

            // 依次打开盒子，并将盒子里的号码作为下一个盒子的索引持续查询，直到号码与第一个盒子索引相等，然后从盒子中删除这串索引链
            for (int boxIndex = 0; boxIndex < num;boxIndex++) {
                // 索引对应的盒子是否还存在
                if (boxes.containsKey(boxIndex)) {
                    openBoxes(boxIndex, boxIndex, boxes);
                }

                // 如果打开的盒子数目大于总数的一半，则说明此次失败，直接退出整个循环进行计数
                if (openNum > maxIndex){
                    isSuccess = false;
                    break;
                }

                // 已经被打开的盒子数目如果大于总数的一半，则说明此次已经成功，直接退出整个循环即可
                allOpenNum += openNum;
                if (allOpenNum > maxIndex) break;

                // 已找到（成功），重置打开次数，让下一个人来
                openNum = 0;
                isSuccess = true;
            }

            // 计数
            count(isSuccess);
            // 计算成功率
            successRate = (double) successNum / cycle;
        }
        System.out.println("成功次数：" + successNum);
        System.out.println("失败次数：" + errorNum);
        System.out.println("成功概率：" + successRate);
        }

        //TIP 计数
        public static void count (Boolean isSuccess){
            if (isSuccess) {
                successNum++;
            } else {
                errorNum++;
            }
        }

        //TIP 创建一百个存有随机的、不重复的、0-99号码牌的盒子
        public static Map<Integer,Integer> creatBoxes() {
            // 1 创建一百个顺序的、不重复的号码牌
            List<Integer> numbers = new ArrayList<Integer>(num);
            for (int i = 0; i < num; i++) {
                numbers.add(i);
            }
            // 2 创建一百个空盒子
            Map boxs = new HashMap<Integer, Integer>(num);
            // 3 将号码牌存入盒子
            Random random = new Random();
            // 3.1 随机取一个号码牌，一直循环直到取完
            for (int i = 0; !numbers.isEmpty(); i++) {
                // 3.2 根据剩余号码牌数量缩小随机数范围
                int index = random.nextInt(numbers.size());
                // 3.3 读取这个索引下的号码牌，并存进盒子里
                boxs.put(i,numbers.get(index));
                // 3.4 删除号码牌
                numbers.remove(index);
            }
            return boxs;
        }

        //TIP 开盒子，并返回开了多少次。在初始索引不变的情况下采用递归法
        public static void openBoxes(int firstBoxIndex, int nextBoxIndex, Map<Integer,Integer> boxes) {
            // 拿到号码牌
            int number = boxes.get(nextBoxIndex);
            // 判断号码牌与第一个盒子的索引是否相等
            if (number != firstBoxIndex) {
                openBoxes(firstBoxIndex, number, boxes);
            }
            // 删除前后衔接的链中的所有盒子
            boxes.remove(number);
            openNum++;
        }
    }