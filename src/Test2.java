//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//class MainFrame{
//    MainFrame(){
//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
//        frame.setMinimumSize(new Dimension(400,400));
//
//        frame.add(MainPanel.createMainPanel());
//        frame.setVisible(true);
//    }
//}
//
//class MainPanel{
//    public static JPanel createMainPanel(){
//        JPanel panel = new JPanel();
//        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
//        panel.setPreferredSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
//        panel.setBackground(new Color(100,100,100));
//
//        panel.add(SubPanel.createSubPanel("MENU", 255,255,0));
//
//        return panel;
//    }
//}
//
//class SubPanel implements ActionListener {
//    public static JPanel createSubPanel(String judul, int r, int g, int b){
//        Menu[] menu = {
//                new Menu("Bakso Urat", 4000),
//                new Menu("Bakso Cincang", 5000),
//                new Menu("Bakso Telur", 3000),
//        };
//
//        JPanel panel = new JPanel();
//        JLabel labelJudul = new JLabel();
//
//        labelJudul.setText(judul);
//        labelJudul.setFont(new Font("Times New Roman", Font.BOLD, 25));
//        labelJudul.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//        panel.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));
//        panel.setBackground(new Color(r,g,b));
//
//        panel.add(labelJudul);
//        for(Menu m : menu){
//            TombolMenu button = new TombolMenu(m.getNama(), m.getHarga());
//            JButton
//
//            panel.add();
//        }
//
//        return panel;
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//    }
//}
//
//class TombolMenu{
//    TombolMenu(String namaMenu, int hargaMenu){
//        JButton button = new JButton();
//        JLabel labelNamaMenu = new JLabel();
//        JLabel labelHargaMenu = new JLabel();
//        Font font = new Font("Times New Roman", Font.BOLD, 15);
//
//        button.add(labelNamaMenu);
//        button.add(Box.createHorizontalGlue());
//        button.add(labelHargaMenu);
//
//        button.setLayout(new BoxLayout(button, BoxLayout.X_AXIS));
//        button.setAlignmentX(Component.CENTER_ALIGNMENT);
//        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
//
//        labelNamaMenu.setText(namaMenu);
//        labelNamaMenu.setFont(font);
//        labelHargaMenu.setText(Integer.toString(hargaMenu));
//        labelHargaMenu.setFont(font);
//    }
//}
//
//public class Test2 {
//    public static void main(String[] args) {
//        MainFrame frame = new MainFrame();
//    }
//}
