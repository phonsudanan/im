package landTax;

import information.FormDeed;
import information.FormPeople;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Homepage extends JFrame {
    JDesktopPane desktop;

    public static void main(String[] args) {
        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font(
                "Leelawadee", Font.PLAIN, 12)));
        new Homepage().setVisible(true);
    }


    private JPanel home;
    private JPanel north;
    private JButton Calculate;
    private JButton People;
    private JButton land;
    private JButton logOutButton;

    public Homepage() {
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
    }


}
