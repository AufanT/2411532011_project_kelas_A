class Cooking {
    private String task;

    Cooking (String task) {
        this.task = task;
    }

    public void run() {
        System.out.println(task + " is in progress.");
    }
}

public class RestaurantWitoutThreads {
    public static void main(String[] args) {
        Cooking t1 = new Cooking("Pasta");
        Cooking t2 = new Cooking("Pizza");
        Cooking t3 = new Cooking("Salad");
        Cooking t4 = new Cooking("Soup");

        t1.run();
        t2.run();
        t3.run();
        t4.run();
    }

}
