public class DownloadAppLambda {
    public static void downloadTask(String fileName) {
        for (int i = 10; i <= 100; i += 10) {
            System.out.println(fileName + " progress: " + i + "%");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(fileName + " selesai diunduh!");
    }
    
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> downloadTask("File-1"));
        Thread t2 = new Thread(() -> downloadTask("File-2"));
        Thread t3 = new Thread(() -> downloadTask("File-3"));

        t1.start();
        t2.start();
        t3.start();
    }

}