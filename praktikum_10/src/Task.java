import javax.swing.*;

public class Task implements Runnable {
    private int taskId;
    private JTextArea logArea;
    private DefaultListModel<String> taskListModel;

    public Task(int taskId, JTextArea logArea, DefaultListModel<String> taskListModel) {
        this.taskId = taskId;
        this.logArea = logArea;
        this.taskListModel = taskListModel;
    }

    @Override
    public void run() {
        SwingUtilities.invokeLater(() -> {
            logArea.append("[START] Task #" + taskId + " mulai diproses...\n");
            updateTaskList("Task #" + taskId + " : Sedang Berjalan...");
        });

        try {
            int processingTime = new java.util.Random().nextInt(3000) + 1000;
            Thread.sleep(processingTime);

            SwingUtilities.invokeLater(() -> {
                logArea.append("[DONE]  Task #" + taskId + " selesai (" + processingTime + "ms)\n");
                updateTaskList("Task #" + taskId + " : Selesai");
            });

        } catch (InterruptedException e) {
            SwingUtilities.invokeLater(() -> {
                logArea.append("[FAIL]  Task #" + taskId + " Terganggu!\n");
            });
            Thread.currentThread().interrupt();
        }
    }

    private void updateTaskList(String status) {
        for (int i = 0; i < taskListModel.size(); i++) {
            if (taskListModel.get(i).startsWith("Task #" + taskId)) {
                taskListModel.set(i, status);
                break;
            }
        }
    }
}