import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

// --- KELAS UTAMA APLIKASI ---
public class Test5AI extends JFrame {

    // --- Data Menu ---
    private static final Map<String, Integer> MENU_DATA = new LinkedHashMap<>();
    static {
        MENU_DATA.put("Bakso Kecil", 1000);
        MENU_DATA.put("Bakso Besar", 3000);
        MENU_DATA.put("Bakso Urat Kecil", 4000);
        MENU_DATA.put("Bakso Urat Besar", 7000);
        MENU_DATA.put("Bakso Isi Cincang", 3000);
        MENU_DATA.put("Bakso Isi Telur", 3000);
        MENU_DATA.put("Bakso Isi Keju", 3000);
        MENU_DATA.put("Tahu Bakso", 2000);
        MENU_DATA.put("Mi", 1000);
    }

    // --- Komponen Utama ---
    private JPanel mainPanel;
    private JPanel menuPanel;
    private JPanel porsiPanelContainer; // Kontainer untuk Porsi Panel agar bisa ditambahkan tombol
    private JPanel porsiPanel; // Panel yang menampung item porsi
    private JPanel pesananPanelContainer;
    private JPanel pesananPanel;
    private JPanel antrianPanelContainer;
    private JPanel antrianPanel;

    // --- State Management ---
    // Map untuk melacak tombol menu di Menu Panel
    private Map<String, JButton> menuButtons = new LinkedHashMap<>();
    // Map untuk melacak item yang sedang dipesan di Porsi Panel
    private Map<String, PorsiItemPanel> currentPorsiItems = new LinkedHashMap<>();
    // List untuk melacak PorsiItem yang siap dipesan (dari porsiPanel)
    private List<PorsiItem> waitingPorsiList = new ArrayList<>();
    // List untuk melacak Pesanan yang sudah dikelompokkan
    private List<PesananItemPanel> currentPesananItems = new ArrayList<>();
    // List untuk melacak Antrian Pesanan
    private List<AntrianItemPanel> currentAntrianItems = new ArrayList<>();

    // Counter untuk penamaan pesanan dan antrian
    private int orderCounter = 0;

    public Test5AI() {
        // 1. Setup Frame
        setTitle("Bakso Manager App - Test5AI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // 2. Setup Main Panel (Horizontal BoxLayout)
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        add(mainPanel, BorderLayout.CENTER);

        // 3. Setup Sub-Panels
        setupMenuPanel();
        setupPorsiPanel();
        setupPesananPanel();
        setupAntrianPanel();

        // Tambahkan ke mainPanel
        mainPanel.add(createWeightedPanel(menuPanel));
        mainPanel.add(createWeightedPanel(porsiPanelContainer));
        mainPanel.add(createWeightedPanel(pesananPanelContainer));
        mainPanel.add(createWeightedPanel(antrianPanelContainer));

        pack();
        setVisible(true);
    }

    // --- UTILITY METHODS ---

    // Metode pembantu untuk membuat panel pembungkus (wrapper) dengan bobot
    private JPanel createWeightedPanel(JPanel contentPanel) {
        JPanel wrapper = new JPanel(new GridLayout(1, 1));
        wrapper.add(contentPanel);
        return wrapper;
    }

    // Metode untuk membuat Panel dengan Judul dan Border
    private JPanel createPanelWithTitle(String title, Color bgColor) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(bgColor);
        container.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2), // Garis luar
                BorderFactory.createEmptyBorder(10, 10, 10, 10) // Padding
        ));

        // Judul
        JLabel judul = new JLabel(title);
        judul.setFont(new Font("Times New Roman", Font.BOLD, 25));
        judul.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(judul);
        container.add(Box.createVerticalStrut(10)); // Spasi

        return container;
    }

    // --- SETUP PANEL ---

    private void setupMenuPanel() {
        menuPanel = createPanelWithTitle("MENU", new Color(255, 255, 0));

        for (Map.Entry<String, Integer> entry : MENU_DATA.entrySet()) {
            String namaMenu = entry.getKey();
            int hargaMenu = entry.getValue();

            JButton menuButton = new JButton();
            menuButton.setLayout(new BorderLayout());

            // Set Max size agar tombol tidak terlalu besar dan alignment center
            menuButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Nama Menu
            JLabel namaLabel = new JLabel(namaMenu);
            namaLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
            menuButton.add(namaLabel, BorderLayout.WEST);

            // Harga Menu (gunakan format Rupiah)
            JLabel hargaLabel = new JLabel(String.format("%,d", hargaMenu));
            hargaLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
            menuButton.add(hargaLabel, BorderLayout.EAST);

            // Listener
            menuButton.addActionListener(e -> handleMenuButtonClick(namaMenu, hargaMenu, (JButton) e.getSource()));

            menuPanel.add(menuButton);
            menuPanel.add(Box.createVerticalStrut(5));

            menuButtons.put(namaMenu, menuButton);
        }
        menuPanel.add(Box.createVerticalGlue());
    }

    private void setupPorsiPanel() {
        porsiPanelContainer = createPanelWithTitle("PORSI", new Color(0, 255, 0));

        // Panel untuk item, agar tombol Tambah ke Pesanan bisa di bawah
        porsiPanel = new JPanel();
        porsiPanel.setLayout(new BoxLayout(porsiPanel, BoxLayout.Y_AXIS));
        porsiPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        porsiPanel.setOpaque(false); // agar warna background container terlihat

        // Wrap porsiPanel dalam JScrollPane (jika item banyak)
        JScrollPane scrollPane = new JScrollPane(porsiPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 9999));
        scrollPane.setBorder(null);

        // Tombol Tambahkan ke Pesanan
        JButton porsiPanelButton = new JButton("Tambahkan ke Pesanan");
        porsiPanelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        porsiPanelButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        porsiPanelButton.addActionListener(e -> handleAddToPesanan());

        // Susun di porsiPanelContainer
        porsiPanelContainer.add(scrollPane);
        porsiPanelContainer.add(Box.createVerticalStrut(10));
        porsiPanelContainer.add(porsiPanelButton);
    }

    private void setupPesananPanel() {
        pesananPanelContainer = createPanelWithTitle("PESANAN", new Color(0, 255, 255));

        pesananPanel = new JPanel();
        pesananPanel.setLayout(new BoxLayout(pesananPanel, BoxLayout.Y_AXIS));
        pesananPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        pesananPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(pesananPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 9999));
        scrollPane.setBorder(null);

        JButton pesananPanelButton = new JButton("Tambahkan ke Antrian");
        pesananPanelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        pesananPanelButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        pesananPanelButton.addActionListener(e -> handleAddToAntrian());

        pesananPanelContainer.add(scrollPane);
        pesananPanelContainer.add(Box.createVerticalStrut(10));
        pesananPanelContainer.add(pesananPanelButton);
    }

    private void setupAntrianPanel() {
        antrianPanelContainer = createPanelWithTitle("ANTRIAN", new Color(255, 0, 255));

        antrianPanel = new JPanel();
        antrianPanel.setLayout(new BoxLayout(antrianPanel, BoxLayout.Y_AXIS));
        antrianPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        antrianPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(antrianPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 9999));
        scrollPane.setBorder(null);

        JButton antrianPanelButton = new JButton("Finish semua");
        antrianPanelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        antrianPanelButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        antrianPanelButton.addActionListener(e -> handleFinishAll());

        antrianPanelContainer.add(scrollPane);
        antrianPanelContainer.add(Box.createVerticalStrut(10));
        antrianPanelContainer.add(antrianPanelButton);
    }

    // --- LOGIC HANDLERS ---

    // Klik tombol di menuPanel
    private void handleMenuButtonClick(String namaMenu, int hargaMenu, JButton sourceButton) {
        if (currentPorsiItems.containsKey(namaMenu)) {
            return;
        }

        sourceButton.setEnabled(false);

        PorsiItem porsiItemData = new PorsiItem(namaMenu, hargaMenu, sourceButton);
        PorsiItemPanel porsiItemView = new PorsiItemPanel(this, porsiItemData);

        currentPorsiItems.put(namaMenu, porsiItemView);

        porsiPanel.add(porsiItemView);
        porsiPanel.revalidate();
        porsiPanel.repaint();
    }

    // Tombol Hapus pada PorsiItemPanel diklik
    public void removeItemFromPorsi(PorsiItemPanel itemPanel, PorsiItem itemData) {
        porsiPanel.remove(itemPanel);
        currentPorsiItems.remove(itemData.namaMenu);
        itemData.sourceMenuButton.setEnabled(true);

        porsiPanel.revalidate();
        porsiPanel.repaint();
    }

    // Tombol "Tambahkan ke Pesanan" diklik
    private void handleAddToPesanan() {
        if (currentPorsiItems.isEmpty()) return;

        orderCounter++;

        // Kumpulkan semua PorsiItemData yang ada di porsiPanel
        List<PorsiItem> items = new ArrayList<>();
        for (PorsiItemPanel view : currentPorsiItems.values()) {
            items.add(view.itemData);
        }

        PesananItemPanel pesananView = new PesananItemPanel(this, orderCounter, items);
        currentPesananItems.add(pesananView);
        pesananPanel.add(pesananView);

        // Bersihkan porsiPanel dan aktifkan kembali tombol menu
        for (PorsiItemPanel view : currentPorsiItems.values()) {
            view.itemData.sourceMenuButton.setEnabled(true);
        }
        currentPorsiItems.clear();
        porsiPanel.removeAll();

        // Perbarui tampilan
        porsiPanel.revalidate();
        porsiPanel.repaint();
        pesananPanel.revalidate();
        pesananPanel.repaint();
    }

    // Tombol Edit pada PesananItemPanel diklik (Kembalikan ke Porsi Panel)
    public void editPesanan(PesananItemPanel pesananView, int orderId, List<PorsiItem> items) {
        // Hapus dari Pesanan Panel
        pesananPanel.remove(pesananView);
        currentPesananItems.remove(pesananView);

        // Pindahkan ke Porsi Panel
        for (PorsiItem itemData : items) {
            // Nonaktifkan tombol menu yang bersangkutan
            itemData.sourceMenuButton.setEnabled(false);

            PorsiItemPanel porsiItemView = new PorsiItemPanel(this, itemData);
            currentPorsiItems.put(itemData.namaMenu, porsiItemView);
            porsiPanel.add(porsiItemView);
        }

        pesananPanel.revalidate();
        pesananPanel.repaint();
        porsiPanel.revalidate();
        porsiPanel.repaint();
    }

    // Tombol "Tambahkan ke Antrian" diklik
    private void handleAddToAntrian() {
        if (currentPesananItems.isEmpty()) return;

        for (PesananItemPanel pesananView : currentPesananItems) {
            AntrianItemPanel antrianView = new AntrianItemPanel(this, pesananView.orderId, pesananView.items);
            currentAntrianItems.add(antrianView);
            antrianPanel.add(antrianView);
        }

        // Bersihkan Pesanan Panel
        currentPesananItems.clear();
        pesananPanel.removeAll();

        pesananPanel.revalidate();
        pesananPanel.repaint();
        antrianPanel.revalidate();
        antrianPanel.repaint();
    }

    // Tombol Edit pada AntrianItemPanel diklik (Kembalikan ke Pesanan Panel)
    public void editAntrian(AntrianItemPanel antrianView, int orderId, List<PorsiItem> items) {
        // Hapus dari Antrian Panel
        antrianPanel.remove(antrianView);
        currentAntrianItems.remove(antrianView);

        // Tambahkan kembali sebagai Pesanan
        PesananItemPanel pesananView = new PesananItemPanel(this, orderId, items);
        currentPesananItems.add(pesananView);
        pesananPanel.add(pesananView);

        antrianPanel.revalidate();
        antrianPanel.repaint();
        pesananPanel.revalidate();
        pesananPanel.repaint();
    }

    // Tombol Finish pada AntrianItemPanel diklik
    public void finishAntrian(AntrianItemPanel antrianView) {
        antrianPanel.remove(antrianView);
        currentAntrianItems.remove(antrianView);

        antrianPanel.revalidate();
        antrianPanel.repaint();
    }

    // Tombol "Finish semua" diklik
    private void handleFinishAll() {
        currentAntrianItems.clear();
        antrianPanel.removeAll();
        antrianPanel.revalidate();
        antrianPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Test5AI::new);
    }
}

// --- DATA MODEL UNTUK ITEM PORSI ---
class PorsiItem {
    String namaMenu;
    int hargaSatuan;
    int jumlah = 1;
    JButton sourceMenuButton; // Tombol di menuPanel yang terkait

    public PorsiItem(String namaMenu, int hargaSatuan, JButton sourceMenuButton) {
        this.namaMenu = namaMenu;
        this.hargaSatuan = hargaSatuan;
        this.sourceMenuButton = sourceMenuButton;
    }

    public int getTotalHarga() {
        return hargaSatuan * jumlah;
    }
}

// --- PANEL PORSI ITEM (Dynamic Item di Porsi Panel) ---
class PorsiItemPanel extends JPanel {
    private final Test5AI parentFrame;
    PorsiItem itemData;

    private JLabel jumlahLabel;
    private JLabel totalHargaLabel;

    public PorsiItemPanel(Test5AI parentFrame, PorsiItem itemData) {
        this.parentFrame = parentFrame;
        this.itemData = itemData;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        // Kiri: Nama Menu
        JLabel namaLabel = new JLabel(itemData.namaMenu);
        namaLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(namaLabel);
        add(Box.createHorizontalStrut(10));

        // Tengah: Kontrol Jumlah (Diperbaiki agar Rapi dan di Tengah)
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        controlPanel.setOpaque(false);

        JButton minusButton = new JButton("-");
        jumlahLabel = new JLabel(String.valueOf(itemData.jumlah));
        jumlahLabel.setPreferredSize(new Dimension(20, 20));
        jumlahLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JButton plusButton = new JButton("+");
        JButton deleteButton = new JButton("Hapus");

        minusButton.addActionListener(e -> handleMinus());
        plusButton.addActionListener(e -> handlePlus());
        deleteButton.addActionListener(e -> parentFrame.removeItemFromPorsi(this, itemData));

        controlPanel.add(minusButton);
        controlPanel.add(jumlahLabel);
        controlPanel.add(plusButton);
        controlPanel.add(deleteButton);

        add(Box.createHorizontalGlue()); // Dorong ke tengah
        add(controlPanel);

        // Kanan: Total Harga
        totalHargaLabel = new JLabel(String.format("Rp %,d", itemData.getTotalHarga()));
        totalHargaLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        totalHargaLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(totalHargaLabel);
        add(Box.createHorizontalStrut(5));
    }

    private void handleMinus() {
        if (itemData.jumlah > 1) {
            itemData.jumlah--;
            updateView();
        }
    }

    private void handlePlus() {
        if (itemData.jumlah < 99) {
            itemData.jumlah++;
            updateView();
        }
    }

    public void updateView() {
        jumlahLabel.setText(String.valueOf(itemData.jumlah));
        totalHargaLabel.setText(String.format("Rp %,d", itemData.getTotalHarga()));
        revalidate();
        repaint();
    }
}

// --- PANEL PESANAN ITEM (Dynamic Item di Pesanan Panel) ---
class PesananItemPanel extends JPanel {
    private final Test5AI parentFrame;
    int orderId;
    List<PorsiItem> items; // List item yang dikelompokkan

    public PesananItemPanel(Test5AI parentFrame, int orderId, List<PorsiItem> items) {
        this.parentFrame = parentFrame;
        this.orderId = orderId;
        this.items = items;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.BLUE));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        // Hitung Total Harga
        int totalOrderPrice = items.stream().mapToInt(PorsiItem::getTotalHarga).sum();

        // Kiri: Detail Pesanan (Diperbaiki agar rapi)
        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setOpaque(false);

        JLabel header = new JLabel("Porsi ke-" + orderId + ":");
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        detailPanel.add(header);

        for (PorsiItem item : items) {
            JLabel itemLabel = new JLabel(item.jumlah + "x " + item.namaMenu);
            detailPanel.add(itemLabel);
        }
        detailPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(detailPanel);

        // Tengah: Kontrol Pesanan
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        controlPanel.setOpaque(false);
        JButton editButton = new JButton("Edit");

        editButton.addActionListener(e -> parentFrame.editPesanan(this, orderId, items));

        controlPanel.add(editButton);
        add(Box.createHorizontalGlue());
        add(controlPanel);

        // Kanan: Total Harga
        JLabel totalLabel = new JLabel(String.format("Rp %,d", totalOrderPrice));
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        totalLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(totalLabel);
    }
}

// --- PANEL ANTRIAN ITEM (Dynamic Item di Antrian Panel) ---
class AntrianItemPanel extends JPanel {
    private final Test5AI parentFrame;
    int orderId;
    List<PorsiItem> items;

    public AntrianItemPanel(Test5AI parentFrame, int orderId, List<PorsiItem> items) {
        this.parentFrame = parentFrame;
        this.orderId = orderId;
        this.items = items;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        int totalOrderPrice = items.stream().mapToInt(PorsiItem::getTotalHarga).sum();

        // Kiri: Detail Antrian
        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setOpaque(false);

        JLabel header = new JLabel("Pesanan ke-" + orderId + ": (Total Item: " + items.size() + ")");
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        detailPanel.add(header);

        for (PorsiItem item : items) {
            JLabel itemLabel = new JLabel("Porsi: " + item.namaMenu + " (" + item.jumlah + "x)");
            detailPanel.add(itemLabel);
        }
        detailPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(detailPanel);

        // Tengah: Kontrol Antrian
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        controlPanel.setOpaque(false);
        JButton editButton = new JButton("Edit");
        JButton finishButton = new JButton("Finish");

        editButton.addActionListener(e -> parentFrame.editAntrian(this, orderId, items));
        finishButton.addActionListener(e -> parentFrame.finishAntrian(this));

        controlPanel.add(editButton);
        controlPanel.add(finishButton);
        add(Box.createHorizontalGlue());
        add(controlPanel);

        // Kanan: Total Harga
        JLabel totalLabel = new JLabel(String.format("Rp %,d", totalOrderPrice));
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        totalLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(totalLabel);
    }
}