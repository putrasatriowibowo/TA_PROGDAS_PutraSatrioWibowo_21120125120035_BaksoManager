//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//public class TestAI2 extends JFrame {
//
//    // --- Data Menu ---
//    private static final Map<String, Integer> MENU_DATA = new LinkedHashMap<>();
//    static {
//        // Nama Menu -> Harga (dalam Rupiah)
//        MENU_DATA.put("Bakso Kecil", 1000);
//        MENU_DATA.put("Bakso Besar", 3000);
//        MENU_DATA.put("Bakso Urat Kecil", 4000);
//        MENU_DATA.put("Bakso Urat Besar", 7000);
//        MENU_DATA.put("Bakso Isi Cincang", 3000);
//        MENU_DATA.put("Bakso Isi Telur", 3000);
//        MENU_DATA.put("Bakso Isi Keju", 3000);
//        MENU_DATA.put("Tahu Bakso", 2000);
//        MENU_DATA.put("Mi", 1000);
//    }
//
//    // --- Komponen Utama ---
//    private JPanel mainPanel;
//    private JPanel menuPanel;
//    private JPanel porsiPanel;
//    private JPanel pesananPanel; // Dibiarkan kosong untuk saat ini
//    private JPanel antrianPanel; // Dibiarkan kosong untuk saat ini
//
//    // Map untuk melacak tombol mana yang telah ditambahkan ke porsiPanel (dinonaktifkan)
//    private Map<String, JButton> menuButtons = new LinkedHashMap<>();
//
//    // Map untuk melacak item yang sedang dipesan di porsiPanel
//    // Key: Nama Menu, Value: PorsiItemPanel (Panel yang dibuat dinamis)
//    private Map<String, PorsiItemPanel> currentPorsiItems = new LinkedHashMap<>();
//
//
//    public TestAI2() {
//        // --- 1. Frame Utama (frame) ---
//        setTitle("Bakso Manager App");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
//        setLayout(new BorderLayout());
//
//        // --- 2. mainPanel (BoxLayout Horizontal) ---
//        mainPanel = new JPanel();
//        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
//        add(mainPanel, BorderLayout.CENTER);
//
//        // --- 3. Inisialisasi 4 Panel Anak ---
//        menuPanel = createPanel("menuPanel", new BoxLayout(new JPanel(), BoxLayout.Y_AXIS), new Color(240, 240, 240));
//        porsiPanel = createPanel("porsiPanel", new BoxLayout(new JPanel(), BoxLayout.Y_AXIS), new Color(200, 220, 255));
//        pesananPanel = createPanel("pesananPanel", new BoxLayout(new JPanel(), BoxLayout.Y_AXIS), new Color(255, 200, 200));
//        antrianPanel = createPanel("antrianPanel", new BoxLayout(new JPanel(), BoxLayout.Y_AXIS), new Color(200, 255, 200));
//
//        // Tambahkan panel ke mainPanel. Beri bobot agar ukurannya dibagi rata
//        mainPanel.add(createWeightedPanel(menuPanel));
//        mainPanel.add(createWeightedPanel(porsiPanel));
//        mainPanel.add(createWeightedPanel(pesananPanel));
//        mainPanel.add(createWeightedPanel(antrianPanel));
//
//        // --- 4. Isi menuPanel ---
//        setupMenuPanel();
//
//        // Agar porsiPanel menampilkan komponen secara langsung
//        porsiPanel.add(Box.createVerticalGlue()); // Untuk mendorong komponen ke atas jika perlu
//
//        pack();
//        setVisible(true);
//    }
//
//    // Metode bantuan untuk membuat JPanel dengan nama dan layout
//    private JPanel createPanel(String name, LayoutManager layout, Color bgColor) {
//        JPanel panel = new JPanel(layout);
//        panel.setName(name);
//        panel.setBackground(bgColor);
//        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding
//        return panel;
//    }
//
//    // Metode bantuan untuk membuat panel pembungkus (wrapper) dengan bobot
//    // untuk memastikan mainPanel membagi ruang secara merata.
//    private JPanel createWeightedPanel(JPanel contentPanel) {
//        JPanel wrapper = new JPanel(new GridLayout(1, 1));
//        wrapper.add(contentPanel);
//        wrapper.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Garis pemisah visual
//        return wrapper;
//    }
//
//    // --- Implementasi menuPanel ---
//    private void setupMenuPanel() {
//        // Label "MENU"
//        JLabel menuLabel = new JLabel("MENU");
//        menuLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
//        menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        menuPanel.add(menuLabel);
//        menuPanel.add(Box.createVerticalStrut(15)); // Spasi
//
//        // Tombol-tombol Menu
//        for (Map.Entry<String, Integer> entry : MENU_DATA.entrySet()) {
//            String namaMenu = entry.getKey();
//            int hargaMenu = entry.getValue();
//
//            JButton menuButton = new JButton();
//            menuButton.setLayout(new BorderLayout());
//
//            // Label Kiri (Nama Menu)
//            JLabel namaLabel = new JLabel(namaMenu);
//            namaLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
//            menuButton.add(namaLabel, BorderLayout.WEST);
//
//            // Label Kanan (Harga Menu)
//            JLabel hargaLabel = new JLabel(String.format("%,d", hargaMenu));
//            hargaLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
//            menuButton.add(hargaLabel, BorderLayout.EAST);
//
//            // Listener tombol
//            menuButton.addActionListener(e -> handleMenuButtonClick(namaMenu, hargaMenu, (JButton) e.getSource()));
//
//            menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//            menuPanel.add(menuButton);
//            menuPanel.add(Box.createVerticalStrut(5)); // Spasi antar tombol
//
//            menuButtons.put(namaMenu, menuButton);
//        }
//
//        menuPanel.add(Box.createVerticalGlue()); // Mendorong komponen ke atas
//    }
//
//    // --- Logika penanganan klik tombol menu ---
//    private void handleMenuButtonClick(String namaMenu, int hargaMenu, JButton sourceButton) {
//        if (currentPorsiItems.containsKey(namaMenu)) {
//            // Seharusnya tidak terjadi jika tombol sudah didisable
//            JOptionPane.showMessageDialog(this, namaMenu + " sudah ada di porsi pesanan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//        // 1. Nonaktifkan tombol menu
//        sourceButton.setEnabled(false);
//
//        // 2. Buat dan tambahkan panel item porsi baru
//        PorsiItemPanel porsiItem = new PorsiItemPanel(this, namaMenu, hargaMenu, sourceButton);
//        currentPorsiItems.put(namaMenu, porsiItem);
//
//        // Tambahkan ke porsiPanel
//        porsiPanel.add(porsiItem, porsiPanel.getComponentCount() - 1); // Tambahkan sebelum VerticalGlue
//
//        // Perbarui tampilan
//        porsiPanel.revalidate();
//        porsiPanel.repaint();
//    }
//
//    // --- Metode yang dipanggil dari PorsiItemPanel saat tombol Hapus diklik ---
//    public void removeItemFromPorsi(PorsiItemPanel itemPanel, String namaMenu, JButton menuButton) {
//        // 1. Hapus dari porsiPanel
//        porsiPanel.remove(itemPanel);
//
//        // 2. Hapus dari pelacakan
//        currentPorsiItems.remove(namaMenu);
//
//        // 3. Aktifkan kembali tombol di menuPanel
//        menuButton.setEnabled(true);
//
//        // Perbarui tampilan
//        porsiPanel.revalidate();
//        porsiPanel.repaint();
//    }
//
//    // --- Main Method ---
//    public static void main(String[] args) {
//        // Memastikan Swing dijalankan di Event Dispatch Thread (EDT)
//        SwingUtilities.invokeLater(TestAI2::new);
//    }
//}
//
//// --- Kelas Panel Item Porsi Dinamis (PorsiPanel Item) ---
//class PorsiItemPanel extends JPanel {
//    private final TestAI2 parentFrame;
//    private final String namaMenu;
//    private final int hargaSatuan;
//    private final JButton sourceMenuButton;
//
//    private JLabel jumlahLabel;
//    private JLabel totalHargaLabel;
//    private int jumlah = 1;
//
//    public PorsiItemPanel(TestAI2 parentFrame, String namaMenu, int hargaSatuan, JButton sourceMenuButton) {
//        this.parentFrame = parentFrame;
//        this.namaMenu = namaMenu;
//        this.hargaSatuan = hargaSatuan;
//        this.sourceMenuButton = sourceMenuButton;
//
//        // Set layout horizontal untuk item panel
//        setLayout(new BorderLayout());
//        setBorder(BorderFactory.createLineBorder(Color.GRAY));
//
//        // --- Kiri: namaMenu ---
//        JLabel namaLabel = new JLabel(namaMenu);
//        namaLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
//        namaLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//        add(namaLabel, BorderLayout.WEST);
//
//        // --- Tengah: Kontrol Jumlah ---
//        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//
//        JButton minusButton = new JButton("-");
//        jumlahLabel = new JLabel(String.valueOf(jumlah));
//        jumlahLabel.setPreferredSize(new Dimension(30, 20)); // Lebar tetap
//        jumlahLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        JButton plusButton = new JButton("+");
//        JButton deleteButton = new JButton("Hapus");
//
//        // Listeners
//        minusButton.addActionListener(this::handleMinus);
//        plusButton.addActionListener(this::handlePlus);
//        deleteButton.addActionListener(this::handleDelete);
//
//        controlPanel.add(minusButton);
//        controlPanel.add(jumlahLabel);
//        controlPanel.add(plusButton);
//        controlPanel.add(deleteButton);
//
//        add(controlPanel, BorderLayout.CENTER);
//
//        // --- Kanan: Total Harga ---
//        totalHargaLabel = new JLabel(String.format("Rp %,d", hargaSatuan * jumlah));
//        totalHargaLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
//        totalHargaLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//        add(totalHargaLabel, BorderLayout.EAST);
//    }
//
//    private void handleMinus(ActionEvent e) {
//        if (jumlah > 1) {
//            jumlah--;
//            updatePorsiItem();
//        }
//    }
//
//    private void handlePlus(ActionEvent e) {
//        if (jumlah < 99) {
//            jumlah++;
//            updatePorsiItem();
//        }
//    }
//
//    private void handleDelete(ActionEvent e) {
//        // Panggil metode penghapusan di frame utama
//        parentFrame.removeItemFromPorsi(this, namaMenu, sourceMenuButton);
//    }
//
//    private void updatePorsiItem() {
//        jumlahLabel.setText(String.valueOf(jumlah));
//        int totalHarga = hargaSatuan * jumlah;
//        totalHargaLabel.setText(String.format("Rp %,d", totalHarga));
//
//        // Memastikan tampilan diperbarui
//        revalidate();
//        repaint();
//    }
//}