package Cards;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;


public class test {
    public static void main(String[] args){




        System.out.println(getRandomNumList(4,0,4));
        System.out.println(getRandomNumList(4,0,4));

    }

    //定义生成随机数并且装入集合容器的方法
    //方法的形参列表分别为：生成随机数的个数、生成随机数的值的范围最小值为start(包含start)、值得范围最大值为end(不包含end)  可取值范围可表示为[start,end)
    public static List getRandomNumList(int nums, int start, int end){
        //1.创建集合容器对象
        List list = new ArrayList();

        //2.创建Random对象
        Random r = new Random();
        //循环将得到的随机数进行判断，如果随机数不存在于集合中，则将随机数放入集合中，如果存在，则将随机数丢弃不做操作，进行下一次循环，直到集合长度等于nums
        while(list.size() != nums){
            int num = r.nextInt(end-start) + start;
            if(!list.contains(num)){
                list.add(num);
            }
        }

        return list;
    }

    public static ThreadLocalRandom getRandom() {
        return ThreadLocalRandom.current();
		/*if(random==null){
			synchronized (RandomUtils.class) {
				if(random==null){
					random =new Random();
				}
			}
		}

		return random;*/
    }


    public static int getRandomInt(int max) {
        return getRandom().nextInt(max);
    }

    public static void battle(Cards cards,Cards cards1){

        /*if(cards instanceof Goblin && cards1 instanceof Goblin){

            if(damage(cards.getDamage(),cards1.getDamage())){
                System.out.println(" "+cards.getName()+" win "+cards.getDamage());
            }else {
                System.out.println(" "+cards1.getName()+" win "+cards1.getDamage());
            }
        }else {
            System.out.println("flase");
        }*/

    }

    public static boolean damage(float x,float y){
        return x>y;
    }



}
