import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class SimpleDelete extends JFrame {
    static int width = 800;
    static int height = 600;
    static int clientWidth;
    static int clientHeight;
    private BufferedImage image;
    public JPanel panel;
    public JLabel label;
    public LinkedList<JLabel> labels = new LinkedList<JLabel>();
    public SimpleDelete() {
        super("simple delete");
        try {
            initGui();
        } catch (IOException ignored) {
            System.out.println("initGui error");
        }
    }
    private void initGui() throws IOException {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(SimpleDelete.width, SimpleDelete.height));
        this.setLocation(d.width / 2 - SimpleDelete.width / 2, d.height / 2 - SimpleDelete.height / 2);
        this.getContentPane().setBackground(Color.lightGray);
        this.setResizable(false);

        panel = new JPanel();
        panel.setBackground(Color.lightGray);
        panel.setFocusable(true);
        panel.setLayout(null);
        this.getContentPane().add(panel);

        image = ImageIO.read(new File("object-50.jpg"));

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (e.getButton() == MouseEvent.BUTTON1) {
                    label = new JLabel();
                    label.setIcon(new ImageIcon(image));
                    panel.add(label);

                    int newX = e.getX();
                    if (clientWidth - newX < image.getWidth()) {
                        newX = clientWidth - image.getWidth();
                    }
                    int newY = e.getY();
                    if (clientHeight - newY < image.getHeight()) {
                        newY = clientHeight - image.getHeight();
                    }
                    label.setBounds(newX, newY, image.getWidth(), image.getHeight());

                    labels.addFirst(label);
                } else if (e.getButton() == MouseEvent.BUTTON2) {
                    for (JLabel l : labels) {
                        if (l.getBounds().contains(new Point(e.getX(), e.getY()))) {
                            labels.remove(l);
                            panel.remove(l);
                            panel.repaint();
                            break;
                        }
                    }
                }
            }
        });
    }
    public void setVisible(boolean b) {
        super.setVisible(b);
        clientWidth = SimpleDelete.width;
        clientHeight = SimpleDelete.height;
        if (isResizable()) {
            clientWidth = getContentPane().getWidth();
            clientHeight = getContentPane().getHeight();
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SimpleDelete frame = new SimpleDelete();
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
