package TestCase;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class testThrea implements Runnable{
    private int num=50;
    private final Lock lock =new ReentrantLock();

    public void run() {
        for (int i=0;i<50;i++) {
            eat();
        }
    }
    public void eat(){

             lock.lock();
            if(num>0){
                try {
                    System.out.println(Thread.currentThread().getName()+"吃咯编号为"+num+"的苹果");
                    Thread.sleep(100);
                    num--;
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }

            }

        }
    }


    public class ThreadTest {
        public static void main(String[] args) {
            testThrea a=new testThrea();
            new Thread(a,"小A").start();
            new Thread(a,"小B").start();
            new Thread(a,"小C").start();
    }


}
