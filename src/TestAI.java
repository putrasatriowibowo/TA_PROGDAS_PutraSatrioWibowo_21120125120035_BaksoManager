import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

public class TestAI extends JFrame {

    // --- VARIABEL STATIS UNTUK AKSES GLOBAL ---
    private static JPanel porsiPanel;
    private static int porsiCounter = 1;

    // --- KELAS MODEL DATA (Inner Class) ---
    static class Menu {
        private String nama;
        private int harga;
        private int stock;

        public Menu(String n, int h, int s) {
            this.nama = n;
            this.harga = h;
            this.stock = s;
        }

        public String getNama() { return nama; }
        public int getHarga() { return harga; }
        public int getStock() { return stock; }
    }
    // ------------------------------------------

    public TestAI() {
        // --- DATA MENU ---
        Menu[] menu = {
                new Menu("Bakso Kecil", 1000, 100), new Menu("Bakso Besar", 3000, 100),
                new Menu("Bakso Urat Kecil", 4000, 100), new Menu("Bakso Urat Besar", 7000, 100),
                new Menu("Bakso Isi Cincang", 4000, 100), new Menu("Bakso Isi Telur", 4000, 100),
                new Menu("Bakso Isi Keju", 4000, 100), new Menu("Tahu Bakso", 2000, 100),
                new Menu("Mi", 1000, 100),
        };

        // FRAME ===========================================
        setTitle("Aplikasi Kasir Sederhana");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(Frame.MAXIMIZED_BOTH);

        // --- PANEL UTAMA ---
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.setBackground(new Color(100, 100, 0)); // Cokelat tua

        // 1. MENU PANEL ===========================================
        JPanel menuPanel = createMenuPanel(menu);
        mainPanel.add(menuPanel);

        // 2. PORSI PANEL ===========================================
        // Deklarasi lokal dan hubungkan ke statis porsiPanel
        JPanel localPorsiPanel = createPorsiPanel();
        porsiPanel = localPorsiPanel; // Hubungkan statis ke instance
        mainPanel.add(localPorsiPanel);

        // 3. PESANAN PANEL ===========================================
        JPanel pesananPanel = createLayoutPanel("PESANAN", new Color(0, 255, 0), 400);
        mainPanel.add(pesananPanel);

        // 4. ANTRIAN PANEL ===========================================
        JPanel antrianPanel = createLayoutPanel("ANTRIAN", new Color(255, 0, 255), 500);
        mainPanel.add(antrianPanel);

        // PENUTUP ===================================================
        add(mainPanel);
        setVisible(true);
    }

    // --- HELPER METHODS UNTUK MEMBUAT PANEL LAYOUT STANDAR ---

    /**
     * Membuat panel dasar dengan judul, warna latar belakang, dan lebar tetap.
     */
    private JPanel createLayoutPanel(String title, Color bgColor, int width) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(width, 0));
        panel.setMaximumSize(new Dimension(width, Integer.MAX_VALUE));
        panel.setBackground(bgColor);

        panel.add(createJudulLabel(title));
        panel.add(Box.createVerticalStrut(10));
        panel.add(Box.createVerticalGlue()); // Glue agar konten rata atas
        return panel;
    }

    /**
     * Membuat JLabel Judul yang rata tengah.
     */
    private JLabel createJudulLabel(String judul) {
        JLabel label = new JLabel(judul);
        label.setFont(new Font("Times New Roman", Font.BOLD, 25));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(Color.BLACK); // Agar terlihat di semua warna latar belakang
        return label;
    }

    // --- KHUSUS MENU PANEL ---
    private JPanel createMenuPanel(Menu[] menu) {
        JPanel panel = createLayoutPanel("MENU", new Color(255, 255, 0), 300);
        // Hapus glue yang ditambahkan di createLayoutPanel agar tombol bisa ditambahkan
        panel.remove(panel.getComponentCount() - 1);

        for (Menu m : menu) {
            panel.add(createMenuButton(m));
            panel.add(Box.createVerticalStrut(10));
        }
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    // --- KHUSUS PORSI PANEL ---
    private JPanel createPorsiPanel() {
        // Menggunakan createLayoutPanel untuk konfigurasi dasar
        JPanel panel = createLayoutPanel("PORSI", new Color(0, 255, 255), 350);
        return panel;
    }

    // --- HELPER METHODS UNTUK KOMPONEN INTERAKTIF ---

    /**
     * Membuat JButton untuk item menu, dilengkapi dengan ActionListener.
     */
    private JButton createMenuButton(Menu menu) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout()); // Gunakan BorderLayout pada tombol untuk menampung panel
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50)); // Lebar penuh panel

        // Tambahkan padding di dalam tombol
        JPanel panelButton = new JPanel(new BorderLayout());
        panelButton.setOpaque(false); // Warisan dari button
        panelButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding

        // Kiri: Nama dan Stok
        JPanel panelButtonKiri = new JPanel();
        panelButtonKiri.setLayout(new BoxLayout(panelButtonKiri, BoxLayout.Y_AXIS));
        panelButtonKiri.setOpaque(false);

        JLabel labelNama = new JLabel(menu.getNama());
        labelNama.setFont(new Font("Times New Roman", Font.BOLD, 16));

        JLabel labelStock = new JLabel("Stock: " + menu.getStock());
        labelStock.setFont(new Font("Times New Roman", Font.PLAIN, 12));

        panelButtonKiri.add(labelNama);
        panelButtonKiri.add(labelStock);

        // Kanan: Harga
        JLabel labelHarga = new JLabel(formatRupiah(menu.getHarga()));
        labelHarga.setFont(new Font("Times New Roman", Font.BOLD, 18));

        // Susun Horizontal di panelButton: Kiri | Glue (Otomatis dari BorderLayout) | Kanan
        panelButton.add(panelButtonKiri, BorderLayout.WEST); // Rata Kiri
        panelButton.add(labelHarga, BorderLayout.EAST); // Rata Kanan

        button.add(panelButton, BorderLayout.CENTER);

        // --- ACTION LISTENER ---
        button.addActionListener(e -> {
            // 1. Hapus VerticalGlue sementara (yang ada di posisi terakhir)
            if (porsiPanel.getComponentCount() > 0) {
                porsiPanel.remove(porsiPanel.getComponentCount() - 1);
            }

            // 2. Tambahkan porsi baru
            JPanel newPorsi = createPorsiItem(menu, porsiCounter);
            porsiPanel.add(newPorsi);
            porsiPanel.add(Box.createVerticalStrut(5));

            // 3. Tambahkan VerticalGlue kembali
            porsiPanel.add(Box.createVerticalGlue());

            // 4. Perbarui UI
            porsiPanel.revalidate();
            porsiPanel.repaint();

            // 5. Tingkatkan counter
            porsiCounter++;
        });

        return button;
    }

    /**
     * Membuat JPanel untuk satu item Porsi.
     */
    private JPanel createPorsiItem(Menu menu, int nomorPorsi) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Batas tipis

        // KIRI: Info Porsi
        JPanel panelKiri = new JPanel();
        panelKiri.setLayout(new BoxLayout(panelKiri, BoxLayout.Y_AXIS));
        panelKiri.setOpaque(false);

        JLabel labelNomorPorsi = new JLabel("Porsi Ke-" + nomorPorsi);
        labelNomorPorsi.setFont(new Font("Times New Roman", Font.ITALIC, 12));

        JLabel labelNama = new JLabel(menu.getNama());
        labelNama.setFont(new Font("Times New Roman", Font.BOLD, 16));

        panelKiri.add(labelNomorPorsi);
        panelKiri.add(labelNama);

        // TENGAH: Kontrol Jumlah
        JPanel panelTengah = new JPanel();
        panelTengah.setLayout(new BoxLayout(panelTengah, BoxLayout.X_AXIS));
        panelTengah.setOpaque(false);

        JLabel labelJumlah = new JLabel("1"); // Default jumlah
        labelJumlah.setFont(new Font("Times New Roman", Font.BOLD, 20));

        JButton minButton = new JButton("-");
        minButton.setPreferredSize(new Dimension(30,30));
        minButton.setFont(new Font("Times New Roman", Font.BOLD, 15));

        JButton plusButton = new JButton("+");
        plusButton.setPreferredSize(new Dimension(30,30));
        plusButton.setFont(new Font("Times New Roman", Font.BOLD, 15));

        JButton hapusButton = new JButton("Hapus");
        hapusButton.setMaximumSize(new Dimension(80,30));
        hapusButton.setFont(new Font("Times New Roman", Font.BOLD, 15));

        // Listener Hapus
        hapusButton.addActionListener(e -> {
            Container parent = panel.getParent();

            // Hapus panel porsi ini
            parent.remove(panel);

            // Perlu mengatur ulang Vertical Glue agar tetap di bawah
            if (parent.getComponentCount() > 0) {
                parent.remove(parent.getComponentCount() - 1); // Hapus glue
                parent.remove(parent.getComponentCount() - 1); // Hapus strut (jarak) sebelumnya
                parent.add(Box.createVerticalGlue()); // Tambah glue lagi
            }

            parent.revalidate();
            parent.repaint();
        });

        panelTengah.add(minButton);
        panelTengah.add(Box.createHorizontalStrut(5));
        panelTengah.add(labelJumlah);
        panelTengah.add(Box.createHorizontalStrut(5));
        panelTengah.add(plusButton);
        panelTengah.add(Box.createHorizontalStrut(10)); // Jarak sebelum Hapus
        panelTengah.add(hapusButton);

        // SUSUN PANEL UTAMA PORSI
        panel.add(Box.createHorizontalStrut(10)); // Padding kiri
        panel.add(panelKiri);
        panel.add(Box.createHorizontalGlue());
        panel.add(panelTengah);
        panel.add(Box.createHorizontalStrut(10)); // Padding kanan

        return panel;
    }

    /**
     * Helper untuk format harga ke Rupiah
     */
    private String formatRupiah(int number) {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        return format.format(number).replace(",00", "");
    }

    public static void main(String[] args) {
        // Selalu jalankan aplikasi Swing di Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new TestAI());
    }
}