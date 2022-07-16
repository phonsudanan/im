package landTax;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class register extends JFrame {
    private Connection con = Connect.ConnectDB();
    private ResultSet rs = null;
    private PreparedStatement pre = null;
    private JTextField user;
    private JPasswordField pass;
    private JPasswordField CFpass;
    private JTextField address;
    private JTextField phone;
    private JButton OKButton;
    private JButton cancelButton;
    private JPanel regis;
    private JTextField name;

//    public static void main(String[] args) {
//       new register().setVisible(true);
//    }

    public register(){
        UIManager.put("OptionPane.messageFont", new Font("Leelawadee", Font.PLAIN, 12));
        setTitle("เข้าสู่ระบบ");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(regis);
        setLocationRelativeTo(null);


        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               int confirm = JOptionPane.showConfirmDialog
                       (null,"คุณต้องการออกจากระบบ", "ยกเลิก",JOptionPane.YES_NO_OPTION);
               if( confirm == JOptionPane.YES_OPTION){
                   System.exit(0);
               }
            }
        });
        OKButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setRegis();
            }
        });
    }

    private void setRegis(){

        String a = new String(pass.getPassword());
        String b = new String(CFpass.getPassword());
        String u = user.getText().trim();
        int i = 0;
        try {
            String sql = "SELECT username FROM login";
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery(sql);
            while (rs.next()){
                if (u.equals(rs.getString("username"))){
               i++;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        if ( !a.equals(b) ){
            JOptionPane.showMessageDialog
                    (this,"คุณใส่รหัสผ่านไม่ตรงกัน","ERROR",JOptionPane.ERROR_MESSAGE);
        } else if ( user.getText().trim().equals("") || a.equals("") || b.equals("") || name.getText().trim().equals("") || address.getText().trim().equals("") || phone.getText().trim().equals("")) {
            JOptionPane.showMessageDialog
                    (this,"คุณใส่ค่าว่างไม่ได้","ERROR",JOptionPane.ERROR_MESSAGE);
        } else if (i>0) {
            JOptionPane.showMessageDialog
                    (this,"Username นี้ ถูกใช้ไปแล้ว","ERROR",JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                String sql = "INSERT INTO login (no, username, password, name, address, phone)"
                                + "Values(?,?,?,?,?,?)";
                pre = con.prepareStatement(sql);
                pre.setString(1,null);
                pre.setString(2, user.getText());
                pre.setString(3, new String(CFpass.getPassword()));
                pre.setString(4, name.getText());
                pre.setString(5, address.getText());
                pre.setString(6, phone.getText());

                if (pre.executeUpdate() != -1) {
                    JOptionPane.showMessageDialog
                            (this, "เข้าระบบอีกครั้ง", "WELCOME", JOptionPane.INFORMATION_MESSAGE);
                    login l = new login();
                    this.setVisible(false);
                    l.setVisible(true);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

}
