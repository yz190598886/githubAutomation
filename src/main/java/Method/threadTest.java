package Method;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class threadTest {
    private static int thread_num = 2;//线程数,设置同时并发线程数
    private static int client_num = 4;

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();

        final Semaphore semp = new Semaphore(thread_num);

        for (int index = 0; index < client_num; index++) {

            final int NO = index;

            Runnable run = new Runnable() {
                public void run() {
                    try {
                        semp.acquire();
                        System.out.println("Thread:" + NO);
                        //业务逻辑
                        semp.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            exec.execute(run);
        }
        exec.shutdown();
    }
} 