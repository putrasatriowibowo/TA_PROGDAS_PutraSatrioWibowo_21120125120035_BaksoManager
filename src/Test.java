import javax.swing.*;
import java.awt.*;
import java.awt.Frame;
import java.lang.foreign.PaddingLayout;

public class Test {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel mainPanel = new JPanel();
        JPanel menuPanel = new JPanel();
        JPanel porsiPanel = new JPanel();
        JPanel pesananPanel = new JPanel();
        JPanel antrianPanel = new JPanel();

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
        // FRAME ===========================================
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
//        frame.setUndecorated(true);

        // MAIN PANEL ===========================================
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        mainPanel.setBackground(new Color(100,100,0));

        // MENU PANEL ===========================================
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
        menuPanel.setBackground(new Color(255,255,0));
        menuPanel.add(createJudulPanel("MENU"));
        for (Menu m: menu){
            menuPanel.add(createMenuButton(m));
            menuPanel.add(Box.createVerticalStrut(10));
        }
        mainPanel.add(menuPanel);

        // PORSI PANEL ===========================================
        porsiPanel.setLayout(new BoxLayout(porsiPanel, BoxLayout.Y_AXIS));
        porsiPanel.setMaximumSize(new Dimension(350, Integer.MAX_VALUE));
        porsiPanel.setBackground(new Color(0,255,255));
        porsiPanel.add(createJudulPanel("PORSI"));

        porsiPanel.add(createPorsiLabel(menu[0]));
        mainPanel.add(porsiPanel);

        // PESANAN PANEL ===========================================
        pesananPanel.setLayout(new BoxLayout(pesananPanel, BoxLayout.Y_AXIS));
        pesananPanel.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));
        pesananPanel.setBackground(new Color(0,255,0));
        pesananPanel.add(createJudulPanel("PESANAN"));
        mainPanel.add(pesananPanel);

        // ANTRIAN PANEL ===========================================
        antrianPanel.setLayout(new BoxLayout(antrianPanel, BoxLayout.Y_AXIS));
        antrianPanel.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));
        antrianPanel.setBackground(new Color(255,0,255));
        antrianPanel.add(createJudulPanel("ANTRIAN"));
        mainPanel.add(antrianPanel);

        //PENUTUP ===================================================
        frame.add(mainPanel);
        frame.setVisible(true);

    }

    public static JLabel createJudulPanel(String judul){
        JLabel label = new JLabel();
        label.setText(judul);
        label.setFont(new Font("Times New Roman", Font.BOLD, 25));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        return label;
    }

    public static JButton createMenuButton(Menu menu){
        JButton button = new JButton();
        JPanel panelButton = new JPanel();
        JPanel panelButtonKiri = new JPanel();
        JLabel labelHarga = new JLabel(Integer.toString(menu.getHarga()));
        JLabel labelNama = new JLabel(menu.getNama());
        JLabel labelStock = new JLabel("Stock: " + Integer.toString(menu.getStock()));

        button.setLayout(new BoxLayout(button, BoxLayout.X_AXIS));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        button.add(panelButton);

        panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.X_AXIS));
        panelButton.setOpaque(false);
        panelButton.add(panelButtonKiri);
        panelButton.add(Box.createHorizontalGlue());
        panelButton.add(labelHarga);

        panelButtonKiri.setLayout(new BoxLayout(panelButtonKiri, BoxLayout.Y_AXIS));
        panelButtonKiri.setOpaque(false);
        panelButtonKiri.add(labelNama);
        panelButtonKiri.add(labelStock);

        return button;
    }

    public static JPanel createPorsiLabel(Menu menu){
        JPanel panel = new JPanel();
        JPanel panelKiri = new JPanel();
        JPanel panelTengah = new JPanel();
        JPanel panelKanan = new JPanel();

        JLabel labelNomorPorsi = new JLabel();
        JLabel labelNama = new JLabel();

        JButton minButton = new JButton();
        JLabel labelJumlah = new JLabel();
        JButton plusButton = new JButton();
        JButton hapusButton = new JButton();

        JLabel labelHarga = new JLabel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        panelKiri.setLayout(new BoxLayout(panelKiri, BoxLayout.Y_AXIS));
        labelNomorPorsi.setText("Porsi Ke-" + 0);
        labelNama.setText(menu.getNama());

        panelTengah.setLayout(new BoxLayout(panelTengah, BoxLayout.X_AXIS));
        minButton.setMaximumSize(new Dimension(30,30));
        minButton.setText("-");
        minButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        labelJumlah.setText("0");
        labelJumlah.setFont(new Font("Times New Roman", Font.BOLD, 20));
        plusButton.setMaximumSize(new Dimension(30,30));
        plusButton.setText("+");
        plusButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        hapusButton.setMaximumSize(new Dimension(100,30));
        hapusButton.setText("Hapus");
        hapusButton.setFont(new Font("Times New Roman", Font.BOLD, 15));

        panel.add(panelKiri);
        panel.add(Box.createHorizontalGlue());
        panel.add(panelTengah);
        panel.add(Box.createHorizontalGlue());
        panel.add(panelKanan);
        panelKiri.add(labelNomorPorsi);
        panelKiri.add(labelNama);
        panelTengah.add(minButton);
        panelTengah.add(Box.createHorizontalStrut(5));
        panelTengah.add(labelJumlah);
        panelTengah.add(Box.createHorizontalStrut(5));
        panelTengah.add(plusButton);
        panelTengah.add(Box.createHorizontalStrut(5));
        panelTengah.add(hapusButton);


        return panel;
    }
}
