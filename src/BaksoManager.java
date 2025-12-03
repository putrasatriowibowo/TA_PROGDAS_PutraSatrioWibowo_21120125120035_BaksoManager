
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Collections;

// --- KELAS UTAMA APLIKASI ---
public class BaksoManager extends JFrame {

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
    private JPanel porsiPanelContainer;
    private JPanel porsiPanel;
    private JPanel pesananPanelContainer;
    private JPanel pesananPanel;
    private JPanel antrianPanelContainer;
    private JPanel antrianPanel;

    // --- State Management ---
    private Map<String, JButton> menuButtons = new LinkedHashMap<>();
    private Map<String, PorsiItemPanel> currentPorsiItems = new LinkedHashMap<>();
    private List<PesananItemPanel> currentPesananItems = new ArrayList<>();
    private List<AntrianItemPanel> currentAntrianItems = new ArrayList<>();

    private int orderCounter = 0;
    private int antrianCounter = 0;

    private List<Integer> reusableOrderIds = new ArrayList<>();

    public BaksoManager() {
        // 1. Setup Frame
        setTitle("Bakso Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Hapus pack() dan pastikan setExtendedState dipanggil setelah semua komponen ditambahkan
        setLayout(new BorderLayout());

        // 2. Setup Main Panel (Menggunakan GridBagLayout untuk kontrol lebar dan tinggi)
        mainPanel = new JPanel(new GridBagLayout());
        add(mainPanel, BorderLayout.CENTER);

        // 3. Setup Sub-Panels
        setupMenuPanel();
        setupPorsiPanel();
        setupPesananPanel();
        setupAntrianPanel();

        // --- Mengatur Bobot Kolom (weightx) dan Tinggi Penuh (weighty = 1.0) ---
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 0;
        gbc.weighty = 1.0; // Mengambil seluruh tinggi vertikal

        // Kolom 0: Menu (Paling kecil, 1% dari lebar)
        gbc.gridx = 0;
        gbc.weightx = 0.01;
        mainPanel.add(menuPanel, gbc);

        // Kolom 1: Separator 1
        gbc.gridx = 1;
        gbc.weightx = 0.001;
        mainPanel.add(new JSeparator(SwingConstants.VERTICAL), gbc);

        // Kolom 2: Porsi (35% dari lebar)
        gbc.gridx = 2;
        gbc.weightx = 0.35;
        mainPanel.add(porsiPanelContainer, gbc);

        // Kolom 3: Separator 2
        gbc.gridx = 3;
        gbc.weightx = 0.001;
        mainPanel.add(new JSeparator(SwingConstants.VERTICAL), gbc);

        // Kolom 4: Pesanan (35% dari lebar)
        gbc.gridx = 4;
        gbc.weightx = 0.35;
        mainPanel.add(pesananPanelContainer, gbc);

        // Kolom 5: Separator 3
        gbc.gridx = 5;
        gbc.weightx = 0.001;
        mainPanel.add(new JSeparator(SwingConstants.VERTICAL), gbc);

        // Kolom 6: Antrian (40% dari lebar)
        gbc.gridx = 6;
        gbc.weightx = 0.4;
        mainPanel.add(antrianPanelContainer, gbc);


        // --- Perbaikan Fullscreen Otomatis ---
        // Atur ukuran ke fullscreen
        setExtendedState(Frame.MAXIMIZED_BOTH);
        // Agar perubahan diperhitungkan.
        setVisible(true);
    }

    // --- UTILITY METHODS ---

    private JPanel createPanelWithTitle(String title, Color bgColor) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(bgColor);
        container.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel judul = new JLabel(title);
        judul.setFont(new Font("Times New Roman", Font.BOLD, 25));
        judul.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(judul);
        container.add(Box.createVerticalStrut(10));

        return container;
    }

    // --- SETUP PANEL ---

    private void setupMenuPanel() {
        menuPanel = createPanelWithTitle("MENU", new Color(240, 240, 240));

        JPanel menuContentPanel = new JPanel();
        menuContentPanel.setLayout(new BoxLayout(menuContentPanel, BoxLayout.Y_AXIS));
        menuContentPanel.setOpaque(false);

        for (Map.Entry<String, Integer> entry : MENU_DATA.entrySet()) {
            String namaMenu = entry.getKey();
            int hargaMenu = entry.getValue();

            JButton menuButton = new JButton();
            menuButton.setLayout(new BorderLayout());

            menuButton.setMinimumSize(new Dimension(150, 40));
            menuButton.setMaximumSize(new Dimension(300, 40));
            menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel namaLabel = new JLabel(namaMenu);
            namaLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            menuButton.add(namaLabel, BorderLayout.WEST);

            JLabel hargaLabel = new JLabel(String.format("Rp %,d", hargaMenu));
            hargaLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
            hargaLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
            hargaLabel.setPreferredSize(new Dimension(90, 20));
            menuButton.add(hargaLabel, BorderLayout.EAST);

            menuButton.addActionListener(e -> handleMenuButtonClick(namaMenu, hargaMenu, (JButton) e.getSource()));

            menuContentPanel.add(menuButton);
            menuContentPanel.add(Box.createVerticalStrut(5));

            menuButtons.put(namaMenu, menuButton);
        }
        menuContentPanel.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(menuContentPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        menuPanel.add(scrollPane);
    }

    private void setupPorsiPanel() {
        porsiPanelContainer = createPanelWithTitle("PORSI", new Color(200, 220, 255));

        porsiPanel = new JPanel();
        porsiPanel.setLayout(new BoxLayout(porsiPanel, BoxLayout.Y_AXIS));
        porsiPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        porsiPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(porsiPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setBorder(null);

        JButton porsiPanelButton = new JButton("Tambahkan ke Pesanan");
        porsiPanelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        porsiPanelButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        porsiPanelButton.addActionListener(e -> handleAddToPesanan());

        porsiPanelContainer.add(scrollPane);
        porsiPanelContainer.add(Box.createVerticalStrut(10));
        porsiPanelContainer.add(porsiPanelButton);
    }

    private void setupPesananPanel() {
        pesananPanelContainer = createPanelWithTitle("PESANAN", new Color(255, 200, 200));

        pesananPanel = new JPanel();
        pesananPanel.setLayout(new BoxLayout(pesananPanel, BoxLayout.Y_AXIS));
        pesananPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        pesananPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(pesananPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
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
        antrianPanelContainer = createPanelWithTitle("ANTRIAN", new Color(200, 255, 200));

        antrianPanel = new JPanel();
        antrianPanel.setLayout(new BoxLayout(antrianPanel, BoxLayout.Y_AXIS));
        antrianPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        antrianPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(antrianPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
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

    public void removeItemFromPorsi(PorsiItemPanel itemPanel, PorsiItem itemData) {
        porsiPanel.remove(itemPanel);
        currentPorsiItems.remove(itemData.namaMenu);
        itemData.sourceMenuButton.setEnabled(true);

        porsiPanel.revalidate();
        porsiPanel.repaint();
    }

    private void handleAddToPesanan() {
        if (currentPorsiItems.isEmpty()) return;

        int newOrderId;
        if (!reusableOrderIds.isEmpty()) {
            Collections.sort(reusableOrderIds);
            newOrderId = reusableOrderIds.remove(0);
        } else {
            orderCounter++;
            newOrderId = orderCounter;
        }

        List<PorsiItem> items = currentPorsiItems.values().stream()
                .map(panel -> panel.itemData)
                .collect(Collectors.toList());

        PesananItemPanel pesananView = new PesananItemPanel(this, newOrderId, items);
        currentPesananItems.add(pesananView);
        pesananPanel.add(pesananView);

        for (PorsiItemPanel view : currentPorsiItems.values()) {
            view.itemData.sourceMenuButton.setEnabled(true);
        }
        currentPorsiItems.clear();
        porsiPanel.removeAll();

        porsiPanel.revalidate();
        porsiPanel.repaint();
        pesananPanel.revalidate();
        pesananPanel.repaint();
    }

    public void deletePesanan(PesananItemPanel pesananView) {
        if (!reusableOrderIds.contains(pesananView.orderId)) {
            reusableOrderIds.add(pesananView.orderId);
        }

        pesananPanel.remove(pesananView);
        currentPesananItems.remove(pesananView);
        pesananPanel.revalidate();
        pesananPanel.repaint();
    }

    public void editPesanan(PesananItemPanel pesananView, int orderId, List<PorsiItem> items) {
        pesananPanel.remove(pesananView);
        currentPesananItems.remove(pesananView);

        for (PorsiItem itemData : items) {
            itemData.sourceMenuButton.setEnabled(false);

            PorsiItemPanel porsiItemView = new PorsiItemPanel(this, itemData);
            currentPorsiItems.put(itemData.namaMenu, porsiItemView);
            porsiPanel.add(porsiItemView);
        }

        if (!reusableOrderIds.contains(orderId)) {
            reusableOrderIds.add(orderId);
        }

        pesananPanel.revalidate();
        pesananPanel.repaint();
        porsiPanel.revalidate();
        porsiPanel.repaint();
    }

    private void handleAddToAntrian() {
        if (currentPesananItems.isEmpty()) return;

        antrianCounter++;

        List<PesananItemData> allPesananData = new ArrayList<>();
        List<PesananItemPanel> sortedPesananItems = currentPesananItems.stream()
                .sorted((p1, p2) -> Integer.compare(p1.orderId, p2.orderId))
                .collect(Collectors.toList());

        for (PesananItemPanel pesananView : sortedPesananItems) {
            allPesananData.add(new PesananItemData(pesananView.orderId, pesananView.items));
        }

        AntrianItemPanel antrianView = new AntrianItemPanel(this, antrianCounter, allPesananData);
        currentAntrianItems.add(antrianView);
        antrianPanel.add(antrianView);

        reusableOrderIds.clear();

        if (!sortedPesananItems.isEmpty()) {
            orderCounter = sortedPesananItems.get(sortedPesananItems.size() - 1).orderId;
        }

        currentPesananItems.clear();
        pesananPanel.removeAll();

        pesananPanel.revalidate();
        pesananPanel.repaint();
        antrianPanel.revalidate();
        antrianPanel.repaint();
    }

    public void editAntrian(AntrianItemPanel antrianView, int antrianId, List<PesananItemData> pesananDataList) {
        antrianPanel.remove(antrianView);
        currentAntrianItems.remove(antrianView);

        for (PesananItemData data : pesananDataList) {
            PesananItemPanel pesananView = new PesananItemPanel(this, data.orderId, data.items);
            currentPesananItems.add(pesananView);
            pesananPanel.add(pesananView);
        }

        for (PesananItemData data : pesananDataList) {
            if (!reusableOrderIds.contains(data.orderId)) {
                reusableOrderIds.add(data.orderId);
            }
        }

        Collections.sort(currentPesananItems, (p1, p2) -> Integer.compare(p1.orderId, p2.orderId));
        pesananPanel.removeAll();
        for (PesananItemPanel p : currentPesananItems) {
            pesananPanel.add(p);
        }

        antrianPanel.revalidate();
        antrianPanel.repaint();
        pesananPanel.revalidate();
        pesananPanel.repaint();
    }

    public void finishAntrian(AntrianItemPanel antrianView) {
        antrianPanel.remove(antrianView);
        currentAntrianItems.remove(antrianView);

        antrianPanel.revalidate();
        antrianPanel.repaint();
    }

    private void handleFinishAll() {
        currentAntrianItems.clear();
        antrianPanel.removeAll();
        antrianPanel.revalidate();
        antrianPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BaksoManager::new);
    }
}

// --- DATA MODEL UNTUK ITEM PORSI ---
class PorsiItem {
    String namaMenu;
    int hargaSatuan;
    int jumlah = 1;
    JButton sourceMenuButton;

    public PorsiItem(String namaMenu, int hargaSatuan, JButton sourceMenuButton) {
        this.namaMenu = namaMenu;
        this.hargaSatuan = hargaSatuan;
        this.sourceMenuButton = sourceMenuButton;
    }

    public int getTotalHarga() {
        return hargaSatuan * jumlah;
    }
}

// --- DATA MODEL UNTUK ITEM PESANAN YANG AKAN DIKIRIM KE ANTRIAN ---
class PesananItemData {
    int orderId; // Nomor Porsi
    List<PorsiItem> items;

    public PesananItemData(int orderId, List<PorsiItem> items) {
        this.orderId = orderId;
        this.items = items;
    }

    public int getTotalHarga() {
        return items.stream().mapToInt(PorsiItem::getTotalHarga).sum();
    }
}


// --- PANEL PORSI ITEM (Dynamic Item di Porsi Panel) ---
class PorsiItemPanel extends JPanel {
    private final BaksoManager parentFrame;
    PorsiItem itemData;

    private JLabel jumlahLabel;
    private JLabel totalHargaLabel;

    public PorsiItemPanel(BaksoManager parentFrame, PorsiItem itemData) {
        this.parentFrame = parentFrame;
        this.itemData = itemData;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel namaLabel = new JLabel(itemData.namaMenu);
        namaLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(namaLabel);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        controlPanel.setOpaque(false);

        JButton minusButton = new JButton("-");
        jumlahLabel = new JLabel(String.valueOf(itemData.jumlah));
        jumlahLabel.setPreferredSize(new Dimension(30, 20));
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

        add(Box.createHorizontalGlue());
        add(controlPanel);

        totalHargaLabel = new JLabel(String.format("Rp %,d", itemData.getTotalHarga()));
        totalHargaLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        totalHargaLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        totalHargaLabel.setPreferredSize(new Dimension(100, 20));
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
    private final BaksoManager parentFrame;
    int orderId;
    List<PorsiItem> items;

    public PesananItemPanel(BaksoManager parentFrame, int orderId, List<PorsiItem> items) {
        this.parentFrame = parentFrame;
        this.orderId = orderId;
        this.items = items;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.BLUE));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        int totalOrderPrice = items.stream().mapToInt(PorsiItem::getTotalHarga).sum();

        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setOpaque(false);

        JLabel header = new JLabel("Porsi ke-" + orderId + ": (Total: " + items.size() + " item)");
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        detailPanel.add(header);

        for (PorsiItem item : items) {
            String itemText = item.namaMenu + (item.jumlah > 1 ? " " + item.jumlah + "x" : "");
            JLabel itemLabel = new JLabel("  - " + itemText);
            detailPanel.add(itemLabel);
        }
        detailPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        detailPanel.add(Box.createHorizontalGlue());
        add(detailPanel);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        controlPanel.setOpaque(false);
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Hapus");

        editButton.addActionListener(e -> parentFrame.editPesanan(this, orderId, items));
        deleteButton.addActionListener(e -> parentFrame.deletePesanan(this));

        controlPanel.add(editButton);
        controlPanel.add(deleteButton);

        add(Box.createHorizontalGlue());
        add(controlPanel);

        JLabel totalLabel = new JLabel(String.format("Rp %,d", totalOrderPrice));
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        totalLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        totalLabel.setPreferredSize(new Dimension(100, 20));
        add(totalLabel);
    }
}

// --- PANEL ANTRIAN ITEM (Dynamic Item di Antrian Panel) ---
class AntrianItemPanel extends JPanel {
    private final BaksoManager parentFrame;
    int antrianId;
    List<PesananItemData> pesananDataList;

    public AntrianItemPanel(BaksoManager parentFrame, int antrianId, List<PesananItemData> pesananDataList) {
        this.parentFrame = parentFrame;
        this.antrianId = antrianId;
        this.pesananDataList = pesananDataList;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        int totalAntrianPrice = pesananDataList.stream()
                .mapToInt(PesananItemData::getTotalHarga)
                .sum();

        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setOpaque(false);
        detailPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel header = new JLabel("Pesanan ke-" + antrianId + ":");
        header.setFont(new Font("SansSerif", Font.BOLD, 16));
        detailPanel.add(header);
        detailPanel.add(Box.createVerticalStrut(5));

        for (PesananItemData pesananData : pesananDataList) {

            JLabel porsiHeader = new JLabel("  Porsi ke-" + pesananData.orderId + ":");
            porsiHeader.setFont(new Font("SansSerif", Font.ITALIC, 14));
            detailPanel.add(porsiHeader);

            for (PorsiItem item : pesananData.items) {
                String itemText = item.namaMenu + (item.jumlah > 1 ? " " + item.jumlah + "x" : "");
                JLabel itemLabel = new JLabel("    - " + itemText);
                detailPanel.add(itemLabel);
            }
            detailPanel.add(Box.createVerticalStrut(5));
        }

        add(detailPanel);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        controlPanel.setOpaque(false);
        JButton editButton = new JButton("Edit");
        JButton finishButton = new JButton("Finish");

        editButton.addActionListener(e -> parentFrame.editAntrian(this, antrianId, pesananDataList));
        finishButton.addActionListener(e -> parentFrame.finishAntrian(this));

        controlPanel.add(editButton);
        controlPanel.add(finishButton);
        add(Box.createHorizontalGlue());
        add(controlPanel);

        JLabel totalLabel = new JLabel(String.format("Rp %,d", totalAntrianPrice));
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        totalLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        totalLabel.setPreferredSize(new Dimension(100, 20));
        add(totalLabel);
    }
}