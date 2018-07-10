package Method;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class ThreadT{
    public void one() throws InterruptedException {

        System.out.println("-----------------"+1+"-----------------");
        sleep(1);
        System.out.println("-----------------"+2+"-----------------");

    }
    public static void main(String[] args) {
        int count=5;
        //定义线程池大小
        ExecutorService pool = Executors.newFixedThreadPool(count);
        //结束的倒数锁
        final CountDownLatch latch = new CountDownLatch(count);
        final ThreadT ok=new ThreadT();
        for (int i = 0; i < count; i++) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try{
                        ok.one();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally{
                        //有一个线程进来就减1
                        latch.countDown();
                    }
                }
            };
            pool.submit(runnable);
        }
        try {
            //阻塞，直到latch为0才执行下面的输出语句
            latch.await();
            System.out.println("所有线程执行完毕！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdown();
    }

}
