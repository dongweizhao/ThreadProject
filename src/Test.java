import java.util.concurrent.*;

/**
 * Created by dongweizhao on 17/9/5.
 */
public class Test {
    private int a = 10;
    private int b = 20;
    static int COUNT_BITS = Integer.SIZE - 3;
    static int CAPACITY = (1 << COUNT_BITS) - 1;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        System.out.println("args = [" + (Integer.SIZE - 3) + "]");
        System.out.println("CAPACITY = [" + Integer.toBinaryString(CAPACITY) + "]");
        System.out.println("CAPACITY+2 = [" + Integer.toBinaryString(CAPACITY + 2) + "]");
        System.out.println("workerCountOf = [" + workerCountOf(CAPACITY + 2) + "]");
        System.out.println("args = [" + CAPACITY + "]");
        System.out.println("-1 << COUNT_BITS = [" + Integer.toBinaryString(-1 << COUNT_BITS) + "]");
        System.out.println("Hello World");
        final CountDownLatch countDownLatch=new CountDownLatch(2);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("1");
                countDownLatch.countDown();
            }
        });
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("2");
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        System.out.println("threadPoolExecutor.getActiveCount() = [" + threadPoolExecutor.getActiveCount() + "]");

        threadPoolExecutor.shutdown();
        threadPoolExecutor.shutdownNow();
        System.out.println("结束");
        TimeUnit.SECONDS.sleep(100);

    }

    public static int workerCountOf(int c) {
        return c & CAPACITY;
    }
}
