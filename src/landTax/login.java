package landTax;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
    private JButton reset;

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

        reset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                userName.setText("");
                password.setText("");
            }
        });
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
                    String u = userName.getText().trim();
                    String p = new String(password.getPassword());
                try {
                    String sql = "SELECT username,password,name FROM login";
                    pre = con.prepareStatement(sql);
                    rs = pre.executeQuery(sql);
                    int i = 0;
                    while (rs.next()){
                            if ( rs.getString("username").equals(u) && rs.getString("password").equals(p) ){
                                String uName = rs.getString("name");
                                ++i;
                                JOptionPane.showMessageDialog
                                        (null, "ยินดีต้อนรับ", "WELCOME", JOptionPane.INFORMATION_MESSAGE);
                                Homepage home = new Homepage();
                                home.getName(uName);
                                home.setVisible(true);
                                break;
                        }
                    }if (i==0){
                                JOptionPane.showMessageDialog
                                        (null, "คุณใส่ Username หรือ Password ไม่ถูกต้อง", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                        ex.printStackTrace();
                }
            }
        });



    }


    }