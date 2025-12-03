//import javax.swing.*;
//import java.awt.Frame;
//import java.util.ArrayList;
//import java.util.jar.JarEntry;
//
//class TombolMenu extends JButton{
//    JLabel labelNama;
//    JLabel labelHarga;
//    JButton button;
//
//    TombolMenu(String nama, int harga){
//        button = new JButton();
//        button.setLayout(new BoxLayout(button, BoxLayout.X_AXIS));
//
//        labelNama = new JLabel();
//        labelNama.setText(nama);
//
//        labelHarga = new JLabel();
//        labelHarga.setText(Integer.toString(harga));
//
//        button.add(this.labelNama);
//        button.add(Box.createHorizontalGlue());
//        button.add(this.labelHarga);
//    }
//}
//
//public class Test4 {
//    public static void main(String[] args) {
//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
//
//        JPanel mainPanel = new JPanel();
//        JPanel menuPanel = new JPanel();
//        JPanel porsiPanel = new JPanel();
//
//        Menu[] menu = {
//                new Menu("Bakso Kecil", 1000),
//                new Menu("Bakso Besar", 3000),
//                new Menu("Bakso Urat Kecil", 4000),
//                new Menu("Bakso Urat Besar", 7000),
//        };
//
//        ArrayList<JButton> menuButton = new ArrayList<>();
//        for(Menu m : menu){
//            menuButton.add(new TombolMenu(m.getNama(), m.getHarga()));
//            menuPanel.add(menuButton);
//        }
//
//        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
//        mainPanel.
//
//        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS);
//
//        frame.setVisible(true);
//
//    }
//}
