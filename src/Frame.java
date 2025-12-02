import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Frame {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setTitle("Bakso Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(screenSize.width,screenSize.height); // widht: 1536, height: 864
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.getContentPane().setBackground(new Color(0,0 ,255));

    }
}
