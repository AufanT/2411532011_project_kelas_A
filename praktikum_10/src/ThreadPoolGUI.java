import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ThreadPoolGUI extends JFrame {
    private JTextField threadCountField;
    private JTextField taskCountField;
    private JTextArea logArea;
    private DefaultListModel<String> taskListModel;
    private JLabel statusLabel;
    private JButton startButton;

    private final Color COLOR_BG = new Color(250, 250, 250); 
    private final Color COLOR_PRIMARY = new Color(52, 152, 219); 
    private final Color COLOR_DANGER = new Color(231, 76, 60);
    private final Color COLOR_TEXT = new Color(44, 62, 80);
    
    private final Font FONT_UI = new Font("Segoe UI", Font.PLAIN, 14); 
    private final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    private final Font FONT_CONTENT = new Font("Segoe UI", Font.PLAIN, 13);

    public ThreadPoolGUI() {
        setTitle("ThreadPool Simulator");
        setSize(850, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_BG);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(new LineBorder(new Color(230, 230, 230), 1));

        threadCountField = createStyledTextField("3");
        taskCountField = createStyledTextField("20");

        topPanel.add(createStyledLabel("Threads:"));
        topPanel.add(threadCountField);
        topPanel.add(createStyledLabel("Tasks:"));
        topPanel.add(taskCountField);

        startButton = createStyledButton("Mulai Proses", COLOR_PRIMARY);
        startButton.addActionListener(e -> startProcessing());
        topPanel.add(startButton);

        JButton clearButton = createStyledButton("Reset", COLOR_DANGER);
        clearButton.addActionListener(e -> clearLog());
        topPanel.add(clearButton);

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 25, 0));
        centerPanel.setBackground(COLOR_BG);
        centerPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        taskListModel = new DefaultListModel<>();
        JList<String> taskList = new JList<>(taskListModel);
        taskList.setFont(FONT_CONTENT); 
        taskList.setFixedCellHeight(30); 
        taskList.setSelectionBackground(new Color(236, 240, 241)); 
        taskList.setSelectionForeground(COLOR_TEXT);
        taskList.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        JScrollPane taskScroll = new JScrollPane(taskList);
        styleScrollPane(taskScroll, "Status Antrian");
        centerPanel.add(taskScroll);

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(FONT_CONTENT); 
        logArea.setForeground(new Color(80, 80, 80));
        logArea.setMargin(new Insets(10, 10, 10, 10));
        logArea.setLineWrap(true);     
        logArea.setWrapStyleWord(true);
        
        JScrollPane logScroll = new JScrollPane(logArea);
        styleScrollPane(logScroll, "Log Sistem");
        centerPanel.add(logScroll);

        add(centerPanel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(new EmptyBorder(10, 25, 10, 25));
        
        statusLabel = new JLabel("Siap dijalankan...");
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        statusLabel.setForeground(Color.GRAY);
        footerPanel.add(statusLabel, BorderLayout.WEST);

        add(footerPanel, BorderLayout.SOUTH);
    }


    private JLabel createStyledLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_BOLD);
        lbl.setForeground(COLOR_TEXT);
        return lbl;
    }

    private JTextField createStyledTextField(String text) {
        JTextField tf = new JTextField(text, 5);
        tf.setFont(FONT_UI);
        tf.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200)), 
            new EmptyBorder(5, 10, 5, 10)
        ));
        tf.setHorizontalAlignment(JTextField.CENTER);
        return tf;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BOLD);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(10, 25, 10, 25));
        return btn;
    }

    private void styleScrollPane(JScrollPane scroll, String title) {
        scroll.setBorder(BorderFactory.createTitledBorder(
            new LineBorder(new Color(230, 230, 230), 1), 
            title, 
            0, 
            0, 
            FONT_BOLD, 
            COLOR_TEXT
        ));
        scroll.setBackground(Color.WHITE);
        scroll.getViewport().setBackground(Color.WHITE);
    }


    private void startProcessing() {
        try {
            int threadCount = Integer.parseInt(threadCountField.getText());
            int taskCount = Integer.parseInt(taskCountField.getText());

            if (threadCount < 1 || taskCount < 1) {
                JOptionPane.showMessageDialog(this, "Angka harus > 0");
                return;
            }

            startButton.setEnabled(false);
            startButton.setBackground(Color.LIGHT_GRAY);
            taskListModel.clear();
            logArea.setText("");
            
            logArea.append("=== SYSTEM STARTED ===\n");
            logArea.append("Active Threads : " + threadCount + "\n");
            logArea.append("Total Tasks    : " + taskCount + "\n");
            logArea.append("----------------------\n\n");
            
            statusLabel.setText("Sedang memproses " + taskCount + " tugas...");

            for (int i = 1; i <= taskCount; i++) {
                taskListModel.addElement("Task #" + i + " : Menunggu...");
            }

            new Thread(() -> {
                ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);

                for (int i = 1; i <= taskCount; i++) {
                    Task task = new Task(i, logArea, taskListModel);
                    threadPool.execute(task);
                }

                threadPool.shutdown();
                try {
                    if (threadPool.awaitTermination(10, TimeUnit.MINUTES)) {
                        SwingUtilities.invokeLater(() -> {
                            logArea.append("\n=== COMPLETED ===\n");
                            statusLabel.setText("Semua tugas selesai.");
                            startButton.setEnabled(true);
                            startButton.setBackground(COLOR_PRIMARY);
                        });
                    }
                } catch (InterruptedException e) {
                    threadPool.shutdownNow();
                }
            }).start();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Input harus angka!");
        }
    }

    private void clearLog() {
        logArea.setText("");
        taskListModel.clear();
        statusLabel.setText("Log dibersihkan.");
    }

    public static void main(String[] args) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        
        SwingUtilities.invokeLater(() -> new ThreadPoolGUI().setVisible(true));
    }
}