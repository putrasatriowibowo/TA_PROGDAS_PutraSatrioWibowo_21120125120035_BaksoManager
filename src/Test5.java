import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test5 {
    public static void main(String[] args) {
        String[][] namaMenu = {
                {"Bakso Kecil", "1000"},
                {"Bakso Besar", "3000"},
                {"Bakso Urat Kecil", "4000"},
                {"Bakso Urat Besar", "7000"},
                {"Bakso Isi Cincang", "3000"},
                {"Bakso Isi Telur", "3000"},
                {"Bakso Isi Keju", "3000"},
                {"Tahu Bakso", "2000"},
                {"Mi", "1000"},
        };

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

        Font fontJudul = new Font("Times New Roman", Font.BOLD, 25);

        judulMenuPanel.setText("MENU");
        judulMenuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        judulMenuPanel.setFont(fontJudul);
        judulPorsiPanel.setText("PORSI");
        judulPorsiPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        judulPorsiPanel.setFont(fontJudul);
        judulPesananPanel.setText("PESANAN");
        judulPesananPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        judulPesananPanel.setFont(fontJudul);
        judulAntrianPanel.setText("ANTRIAN");
        judulAntrianPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        judulAntrianPanel.setFont(fontJudul);

        menuPanel.add(judulMenuPanel);
        porsiPanel.add(judulPorsiPanel);
        pesananPanel.add(judulPesananPanel);
        antrianPanel.add(judulAntrianPanel);

        // menuButton =====================================
        JButton[] menuButton = new JButton[namaMenu.length];
        for(int i = 0; i < namaMenu.length; i++){
            menuButton[i] = new JButton();
            JLabel labelNama = new JLabel();
            JLabel labelHarga = new JLabel();

            labelNama.setText(namaMenu[i][0]);
            labelHarga.setText(namaMenu[i][1]);

            menuButton[i].setLayout(new BoxLayout(menuButton[i], BoxLayout.X_AXIS));
            menuButton[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            menuButton[i].setAlignmentX(Component.CENTER_ALIGNMENT);

            menuButton[i].add(labelNama);
            menuButton[i].add(Box.createHorizontalGlue());
            menuButton[i].add(labelHarga);
            menuPanel.add(menuButton[i]);
            menuPanel.add(Box.createVerticalStrut(5));
        }

        // menuItem ===================================
        int jumlah = 4;
        for (int i = 0; i < namaMenu.length; i++){
            JPanel panel = new JPanel();
            JPanel panelTengah = new JPanel();
            JLabel labelNama = new JLabel();
            JLabel labelHarga = new JLabel();
            JLabel labelJumlah = new JLabel();
            JButton minButton = new JButton();
            JButton plusButton = new JButton();
            JButton deleteButton = new JButton();

            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.setPreferredSize(new Dimension(0, 50));
            panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

            labelNama.setText(namaMenu[i][0]);
            labelHarga.setText(Integer.toString(Integer.parseInt(namaMenu[i][1]) * jumlah));
            labelJumlah.setText(Integer.toString(jumlah));

            minButton.setText("-");
            plusButton.setText("+");
            deleteButton.setText("Hapus");

            panel.add(labelNama);
            panel.add(panelTengah);
            panel.add(labelHarga);
            panelTengah.add(minButton);
            panelTengah.add(labelJumlah);
            panelTengah.add(plusButton);
            panelTengah.add(deleteButton);

            porsiPanel.add(panel);
            porsiPanel.add(Box.createVerticalStrut(5));
        }

        JButton porsiPanelButton = new JButton();
        porsiPanelButton.setText("Tambahkan ke Pesanan");
        porsiPanelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        porsiPanelButton.setPreferredSize(new Dimension(0, 50));
        porsiPanelButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        porsiPanel.add(Box.createVerticalGlue());
        porsiPanel.add(porsiPanelButton);


        // pesanan ======================================
        for (int i = 0; i < namaMenu.length; i++){
            JPanel panel = new JPanel();
            JPanel panelKiri = new JPanel();
            JPanel panelTengah = new JPanel();
            JLabel labelNama = new JLabel();
            JLabel labelHarga = new JLabel();
            JLabel labelJumlah = new JLabel();
            JButton minButton = new JButton();
            JButton plusButton = new JButton();
            JButton editButton = new JButton();
            JButton deleteButton = new JButton();

            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.setPreferredSize(new Dimension(0, 50));
            panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

            labelNama.setText("Porsi ke-" + (i+1) + ":");
            labelHarga.setText(Integer.toString(Integer.parseInt(namaMenu[i][1]) * jumlah));
            labelJumlah.setText(Integer.toString(jumlah));

            panelKiri.setLayout(new BoxLayout(panelKiri, BoxLayout.Y_AXIS));
            panelKiri.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelKiri.add(labelNama);

            for (int j = 0; j < namaMenu.length; j++){
                JLabel labelNama2 = new JLabel();
                labelNama2.setText(namaMenu[j][0]);
                panelKiri.add(labelNama2);
            }

            panelTengah.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelTengah.setAlignmentY(Component.CENTER_ALIGNMENT);

            minButton.setText("-");
            plusButton.setText("+");
            editButton.setText("Edit");
            deleteButton.setText("Hapus");

            panel.add(panelKiri);
            panel.add(panelTengah);
            panel.add(labelHarga);
            panelTengah.add(minButton);
            panelTengah.add(labelJumlah);
            panelTengah.add(plusButton);
            panelTengah.add(editButton);
            panelTengah.add(deleteButton);

            pesananPanel.add(panel);
            pesananPanel.add(Box.createVerticalStrut(5));
        }

        JButton pesananPanelButton = new JButton();
        pesananPanelButton.setText("Tambahkan ke Antrian");
        pesananPanelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        pesananPanelButton.setPreferredSize(new Dimension(0, 50));
        pesananPanelButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        pesananPanel.add(Box.createVerticalGlue());
        pesananPanel.add(pesananPanelButton);

        // antrian ======================================
        for (int i = 0; i < namaMenu.length; i++){
            JPanel panel = new JPanel();
            JPanel panelKiri = new JPanel();
            JPanel panelTengah = new JPanel();
            JLabel labelPesanan = new JLabel();
            JLabel labelPorsi = new JLabel();
            JLabel labelItem = new JLabel();
            JLabel labelHarga = new JLabel();
            JButton editButton = new JButton();
            JButton finishButton = new JButton();

            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.setPreferredSize(new Dimension(0, 50));
            panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

            labelPesanan.setText("Pesanan ke-" + (i+1) + ":");
            labelHarga.setText(Integer.toString(Integer.parseInt(namaMenu[i][1]) * jumlah));

            panelKiri.setLayout(new BoxLayout(panelKiri, BoxLayout.Y_AXIS));
            panelKiri.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
            panelKiri.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelKiri.add(labelPesanan);

            for (int j = 0; j < namaMenu.length; j++){
                labelPorsi = new JLabel();
                labelPorsi.setText("Porsi ke-" + (j+1) + ":");
                panelKiri.add(labelPorsi);
                for (int k = 0; k < 1; k++){
                    labelItem = new JLabel();
                    labelItem.setText(namaMenu[k][0]);
                    panelKiri.add(labelItem);
                }

            }

            panelTengah.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelTengah.setAlignmentY(Component.CENTER_ALIGNMENT);

            editButton.setText("Edit");
            finishButton.setText("Finish");

            panel.add(panelKiri);
            panel.add(panelTengah);
            panel.add(labelHarga);
            panelTengah.add(editButton);
            panelTengah.add(finishButton);

            antrianPanel.add(panel);
            antrianPanel.add(Box.createVerticalStrut(5));
        }

        JButton antrianPanelButton = new JButton();
        antrianPanelButton.setText("Finish semua");
        antrianPanelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        antrianPanelButton.setPreferredSize(new Dimension(0, 50));
        antrianPanelButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        antrianPanelButton.add(Box.createVerticalGlue());
        antrianPanel.add(antrianPanelButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        menuPanel.setLayout(new BoxLayout(menuPanel,BoxLayout.Y_AXIS));
        menuPanel.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));
        menuPanel.setBackground(new Color(255,255,0));
        menuPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        porsiPanel.setLayout(new BoxLayout(porsiPanel,BoxLayout.Y_AXIS));
        porsiPanel.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));
        porsiPanel.setBackground(new Color(0,255,0));
        porsiPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        pesananPanel.setLayout(new BoxLayout(pesananPanel,BoxLayout.Y_AXIS));
        pesananPanel.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));
        pesananPanel.setBackground(new Color(0,255,255));
        pesananPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        antrianPanel.setLayout(new BoxLayout(antrianPanel,BoxLayout.Y_AXIS));
        antrianPanel.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));
        antrianPanel.setBackground(new Color(255,0,255));
        antrianPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));


        frame.add(mainPanel);
        mainPanel.add(menuPanel);
        mainPanel.add(porsiPanel);
        mainPanel.add(pesananPanel);
        mainPanel.add(antrianPanel);


        frame.setVisible(true);
    }
}
