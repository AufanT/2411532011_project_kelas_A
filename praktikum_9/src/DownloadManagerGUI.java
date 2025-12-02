import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

public class DownloadManagerGUI extends JFrame {

    private RoundedProgressBar progressBar1;
    private RoundedProgressBar progressBar2;
    private RoundedProgressBar progressBar3;
    private JButton btnStart;

    public DownloadManagerGUI() {
        setTitle("Download Manager App");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null); 
        getContentPane().setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Download Manager App", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitle.setForeground(new Color(50, 50, 50));
        lblTitle.setBounds(0, 20, 450, 30);
        add(lblTitle);

        Font labelFont = new Font("SansSerif", Font.PLAIN, 14);

        JLabel lblFile1 = new JLabel("File 1");
        lblFile1.setFont(labelFont);
        lblFile1.setBounds(40, 80, 50, 25);
        add(lblFile1);

        progressBar1 = new RoundedProgressBar();
        progressBar1.setBounds(100, 80, 280, 25);
        add(progressBar1);

        JLabel lblFile2 = new JLabel("File 2");
        lblFile2.setFont(labelFont);
        lblFile2.setBounds(40, 130, 50, 25);
        add(lblFile2);

        progressBar2 = new RoundedProgressBar();
        progressBar2.setBounds(100, 130, 280, 25);
        add(progressBar2);

        JLabel lblFile3 = new JLabel("File 3");
        lblFile3.setFont(labelFont);
        lblFile3.setBounds(40, 180, 50, 25);
        add(lblFile3);

        progressBar3 = new RoundedProgressBar();
        progressBar3.setBounds(100, 180, 280, 25);
        add(progressBar3);

        btnStart = new JButton("Start Download");
        styleButton(btnStart);
        btnStart.setBounds(150, 250, 150, 40);
        add(btnStart);

        btnStart.addActionListener(e -> startDownloadProcess());
    }

    private void startDownloadProcess() {
        btnStart.setEnabled(false);
        btnStart.setText("Downloading...");

        progressBar1.updateProgress(0);
        progressBar2.updateProgress(0);
        progressBar3.updateProgress(0);

        Thread t1 = new Thread(() -> performDownload(progressBar1));
        Thread t2 = new Thread(() -> performDownload(progressBar2));
        Thread t3 = new Thread(() -> performDownload(progressBar3));

        t1.start();
        t2.start();
        t3.start();

        new Thread(() -> {
            try {
                t1.join(); 
                t2.join(); 
                t3.join(); 
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this, "Semua file berhasil diunduh!");
                btnStart.setEnabled(true);
                btnStart.setText("Start Download");
            });
        }).start();
    }

    private void performDownload(RoundedProgressBar bar) {
        for (int i = 0; i <= 100; i++) {
            final int progress = i;
            SwingUtilities.invokeLater(() -> bar.updateProgress(progress));
            try {
                Thread.sleep((long) (Math.random() * 50) + 20);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void styleButton(JButton btn) {
        btn.setFocusPainted(false);
        btn.setBackground(new Color(60, 130, 246));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder());
        
        btn.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(c.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);
                super.paint(g, c);
            }
        });
    }

    class RoundedProgressBar extends JComponent {
        private int progress = 0;
        private final Color barColor = new Color(60, 130, 246); 
        private final Color trackColor = new Color(230, 230, 230);

        public void updateProgress(int progress) {
            this.progress = progress;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int arcSize = height; 

            g2.setColor(trackColor);
            g2.fillRoundRect(0, 0, width, height, arcSize, arcSize);

            if (progress > 0) {
                int fillWidth = (int) ((width * progress) / 100.0);
                g2.setColor(barColor);
                g2.fillRoundRect(0, 0, fillWidth, height, arcSize, arcSize);
            }

            String text = progress + "%";
            g2.setFont(new Font("SansSerif", Font.BOLD, 12));
            
            FontMetrics fm = g2.getFontMetrics();
            int textX = (width - fm.stringWidth(text)) / 2;
            int textY = (height - fm.getHeight()) / 2 + fm.getAscent();

            if (progress > 52) {
                g2.setColor(Color.WHITE);
            } else {
                g2.setColor(Color.DARK_GRAY);
            }
            
            g2.drawString(text, textX, textY);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DownloadManagerGUI().setVisible(true);
        });
    }
}