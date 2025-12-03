import javax.swing.*;
import java.awt.*;
import java.awt.Frame;

public class Test3 {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel mainPanel = new JPanel();
        JPanel menuPanel = new JPanel();
        JPanel porsiPanel = new JPanel();
        JPanel pesananPanel = new JPanel();
        JPanel antrianPanel = new JPanel();
        JLabel judulMenuPanel = new JLabel();
        JLabel judulPorsiPanel = new JLabel();
        JLabel judulPesananPanel = new JLabel();
        JLabel judulAntrianPanel = new JLabel();
        JButton baksoKecilButton = new JButton();
        JButton baksoBesarButton = new JButton();
        JButton baksoUratKecilButton = new JButton();
        JButton baksoUratBesarButton = new JButton();

        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.X_AXIS));
        mainPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        mainPanel.setBackground(new Color(100,100,100));

        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));
        menuPanel.setBackground(new Color(255,255,0));

        judulMenuPanel.setText("MENU");
        judulMenuPanel.setFont(new Font("Times New Roman", Font.BOLD, 25));
        judulMenuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        porsiPanel.setLayout(new BoxLayout(porsiPanel, BoxLayout.Y_AXIS));
        porsiPanel.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));
        porsiPanel.setBackground(new Color(0,255,0));
        pesananPanel.setLayout(new BoxLayout(pesananPanel, BoxLayout.Y_AXIS));
        pesananPanel.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));
        pesananPanel.setBackground(new Color(0,255,255));
        antrianPanel.setLayout(new BoxLayout(antrianPanel, BoxLayout.Y_AXIS));
        antrianPanel.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));
        antrianPanel.setBackground(new Color(255,0,255));

        judulPorsiPanel.setText("PORSI");
        judulPorsiPanel.setFont(new Font("Times New Roman", Font.BOLD, 25));
        judulPorsiPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        judulPesananPanel.setText("PESANAN");
        judulPesananPanel.setFont(new Font("Times New Roman", Font.BOLD, 25));
        judulPesananPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        judulAntrianPanel.setText("ANTRIAN");
        judulAntrianPanel.setFont(new Font("Times New Roman", Font.BOLD, 25));
        judulAntrianPanel.setAlignmentX(Component.CENTER_ALIGNMENT);


        frame.add(mainPanel);
        mainPanel.add(menuPanel);
        mainPanel.add(porsiPanel);
        mainPanel.add(pesananPanel);
        mainPanel.add(antrianPanel);

        menuPanel.add(judulMenuPanel);
        menuPanel.add(baksoKecilButton);
        menuPanel.add(baksoBesarButton);
        menuPanel.add(baksoUratKecilButton);
        menuPanel.add(baksoUratBesarButton);

        frame.setVisible(true);

    }
}
