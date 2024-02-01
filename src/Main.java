import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    static AtomicInteger counter1 = new AtomicInteger(0);
    static AtomicInteger counter2 = new AtomicInteger(0);
    static CountDownLatch latch = new CountDownLatch(100_000);

    public static void main( String[] args ) throws InterruptedException {

        ExecutorService pool = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 100_000; i++) {
            pool.execute(Main::run);
        }

        pool.shutdown();

        // Main thread sleeps until all latches are down.
        latch.await();

        // After all tasks are done, we can print counters
        System.out.println("counter 1: " + counter1);
        System.out.println("counter 1: " + counter2);
    }

    private static void run(){
        counter1.incrementAndGet();
        counter2.incrementAndGet();
        latch.countDown();
    }

}
