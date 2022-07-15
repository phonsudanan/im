package landTax;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//public class login extends  JInternalFrame{
public class login extends  JFrame{
    private Connection con = Connect.ConnectDB();
    private ResultSet rs = null;
    private PreparedStatement pre = null;

    private JLabel เข้าสู่ระบบ;
    private JTextField userName;
    private JButton register;
    private JButton singInButton;
    private JPanel panelLogin;
    private JPasswordField password;
    private JButton logIn;
    public static void main(String[] args) {
        new login().setVisible(true);
    }
    public login() {
            UIManager.put("OptionPane.messageFont", new Font("Leelawadee", Font.PLAIN, 12));
            setTitle("เข้าสู่ระบบ");
            setSize(550, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setContentPane(panelLogin);
            setLocationRelativeTo(null);
        register.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                register re = new register();
                re.setVisible(true);

            }
        });
        logIn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
    }


    }