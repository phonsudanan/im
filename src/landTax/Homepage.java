package landTax;

import information.FormDeed;
import information.FormPeople;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Homepage extends JFrame {
    JDesktopPane desktop;

    public static void main(String[] args) {
        UIManager.put("OptionPane.messageFont", new Font("Leelawadee", Font.PLAIN, 12));
        UIManager.put("InternalFrame.titleFont", new Font("Leelawadee", Font.PLAIN, 12));
        new Homepage().setVisible(true);
    }

    private JPanel home;
    private JPanel north;
    private JButton Calculate;
    private JButton People;
    private JButton land;
    private JButton logOutButton;
    private JLabel user_name;
    private JPanel l;
    private JPanel p;
    private JPanel c;

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

        land.setIcon(new ImageIcon("C:\\Users\\phons\\IdeaProjects\\landAndBuildingTax\\src\\photo\\location.png"));
        land.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormDeed fDeed = new FormDeed();
                fDeed.setVisible(true);
                layeredPane.add(fDeed, new Integer(3));
            }
        });

        People.setIcon(new ImageIcon("C:\\Users\\phons\\IdeaProjects\\landAndBuildingTax\\src\\photo\\person.png"));
        People.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FormPeople fPeople = new FormPeople();
                fPeople.setVisible(true);
                layeredPane.add(fPeople, new Integer(3));
            }
        });
        Calculate.setIcon(new ImageIcon("C:\\Users\\phons\\IdeaProjects\\landAndBuildingTax\\src\\photo\\calculate.png"));
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


//    private void createUIComponents() {
//        Calculate.setSize(150,100);
//        Calculate.setIcon(new ImageIcon("photo/calculate.png"));
//        Calculate.setVisible(true);
//    }
}
