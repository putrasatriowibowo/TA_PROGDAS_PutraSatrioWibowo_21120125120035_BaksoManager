import javax.swing.*;
import java.awt.*;

public class SizeComparisonExample extends JFrame {
        public static void main(String[] args) {
            JPanel flowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            flowPanel.setBorder(BorderFactory.createTitledBorder("FlowLayout"));

            JButton btn1 = new JButton("Kecil");
            JButton btn2 = new JButton("Ideal 150x50");
            btn2.setPreferredSize(new Dimension(150, 50)); // Diperhatikan
            JButton btn3 = new JButton("Normal");

            flowPanel.add(btn1);
            flowPanel.add(btn2);
            flowPanel.add(btn3);

        }

}
