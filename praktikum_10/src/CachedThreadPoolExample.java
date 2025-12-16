import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPoolExample {
    public static void main(String[] args) {
        int taskCount = 10;

        ExecutorService service = Executors.newCachedThreadPool(); 

        for (int i = 0; i < taskCount; i++) {
            service.execute(() -> {
                System.out.println("Printing document by thread: " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        service.shutdown();
    }
}