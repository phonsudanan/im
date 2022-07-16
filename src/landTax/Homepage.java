package landTax;

import information.FormDeed;
import information.FormPeople;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Homepage extends JFrame {
    JDesktopPane desktop;

//    public static void main(String[] args) {
//        UIManager.put("OptionPane.messageFont", new Font("Leelawadee", Font.PLAIN, 12));
//        UIManager.put("InternalFrame.titleFont", new Font("Leelawadee", Font.PLAIN, 12));
//        new Homepage().setVisible(true);
//    }


    private JPanel home;
    private JPanel north;
    private JButton Calculate;
    private JButton People;
    private JButton land;
    private JButton logOutButton;
    private JLabel user_name;

    void getName(String n){
        user_name.setText("สวัสดี \t"+n);
    }

    public Homepage() {
        UIManager.put("OptionPane.messageFont", new Font("Leelawadee", Font.PLAIN, 12));
        UIManager.put("InternalFrame.titleFont", new Font("Leelawadee", Font.PLAIN, 12));

        setTitle("Homepage");
        setSize(1000,600);
        setContentPane(home);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        desktop = new JDesktopPane();
        JLayeredPane layeredPane = getLayeredPane();
        layeredPane.add(desktop, new Integer(1));
        layeredPane.add(home, new Integer(2));

        land.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormDeed fDeed = new FormDeed();
                fDeed.setVisible(true);
                layeredPane.add(fDeed, new Integer(3));
            }
        });

        People.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FormPeople fPeople = new FormPeople();
                fPeople.setVisible(true);
                layeredPane.add(fPeople, new Integer(3));
            }
        });

        Calculate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Calculator cal = new Calculator();
                cal.setVisible(true);
                layeredPane.add(cal, new Integer(3));
            }
        });

//        this.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowOpened(WindowEvent e) {
//                login l = new login();
//                l.setVisible(true);
//                layeredPane.add(l, new Integer(3));
//            }
//        });

        logOutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int confirm = JOptionPane.showConfirmDialog
                        (null,"คุณต้องการออกจากระบบ","",JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });





    }


}
