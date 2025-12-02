class CookingTask extends Thread {
    private String task;

    CookingTask (String task) {
        this.task = task;
    }

    public void run() {
        System.out.println(task + " is being prepared by " + Thread.currentThread().getName());
    }
}

public class RestaurantExtendsThreads {
    public static void main(String[] args) {
        Thread t1 = new CookingTask("Pasta");
        Thread t2 = new CookingTask("Pizza");
        Thread t3 = new CookingTask("Salad");
        Thread t4 = new CookingTask("Soup");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
