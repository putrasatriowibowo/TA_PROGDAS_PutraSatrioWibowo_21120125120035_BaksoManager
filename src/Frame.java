import javax.swing.*;
import java.awt.*;


public class Frame extends JFrame {
    JButton menuButton;
    JPanel menuPanel, porsiPanel, pesananPanel, antrianPanel;

    Menu[] menu = {
            new Menu("Bakso Kecil", 1000, 100),
            new Menu("Bakso Besar", 3000, 100),
            new Menu("Bakso Urat Kecil", 4000, 100),
            new Menu("Bakso Urat Besar", 7000, 100),
            new Menu("Bakso Isi Cincang", 4000, 100),
            new Menu("Bakso Isi Telur", 4000, 100),
            new Menu("Bakso Isi Keju", 4000, 100),
            new Menu("Tahu Bakso", 2000, 100),
            new Menu("Mi", 1000, 100),
    };

    Frame(){
        // ======== MENU PANEL ============
        menuPanel = new JPanel();
        menuPanel.setBounds(0,0,400, 864);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(255,0,0));

        for(Menu menu : menu){
            menuButton = new JButton();
            menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuButton.setPreferredSize(new Dimension(300,50));
            menuButton.setMaximumSize(new Dimension(300,50));
            menuButton.setLayout(new BorderLayout(10,0));

            JPanel panelKiri = new JPanel();
            panelKiri.setLayout(new BoxLayout(panelKiri, BoxLayout.Y_AXIS));
            panelKiri.setOpaque(false);

            JLabel namaLabel = new JLabel();
            namaLabel.setText(menu.getNama());
            namaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel stockLabel = new JLabel();
            stockLabel.setText("Stock: " + Integer.toString(menu.getStock()));
            stockLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel hargaLabel = new JLabel();
            hargaLabel.setText(Integer.toString(menu.getHarga()));
            hargaLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

            panelKiri.add(namaLabel);
            panelKiri.add(Box.createVerticalStrut(5));
            panelKiri.add(stockLabel);

            menuButton.add(panelKiri, BorderLayout.WEST);
            menuButton.add(hargaLabel, BorderLayout.EAST);

            menuPanel.add(menuButton);
            menuPanel.add(Box.createVerticalStrut(10));
        }

        menuButton = new JButton();
        menuButton.setText("Tambahkan ke porsi");
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.setPreferredSize(new Dimension(300,50));
        menuButton.setMaximumSize(new Dimension(300,50));

        menuPanel.add(menuButton);

        // ======= PORSI PANEL =======
        porsiPanel = new JPanel();
        porsiPanel.setBounds(400,0,300, 864);
        porsiPanel.setLayout(new BoxLayout(porsiPanel, BoxLayout.Y_AXIS));
        porsiPanel.setBackground(new Color(0,0,255));

        JLabel porsiLabel = new JLabel();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // widht: 1536, height: 864
        this.setUndecorated(true);
        this.setVisible(true);
        this.getContentPane().setBackground(new Color(255,255 ,255));

        this.add(menuPanel);
        this.add(porsiPanel);
    }
}
