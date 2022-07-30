package landTax;

import information.FormPeople;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;

public class Calculator extends JInternalFrame {
    private Connection con = Connect.ConnectDB();
    private ResultSet rs = null;
    private PreparedStatement pre = null;

    private JTextField area;
    private JTextField price;
    private JComboBox status;
    private JButton enter;
    private JButton reset;
    private JPanel cal;
    private JLabel taxBaht;
    public Calculator(){
        super("TAX", false, true, true, false);
        setSize(950,550);
        setContentPane(cal);

        initComponents();

        reset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                area.setText("");
                price.setText("");
                taxBaht.setText("0 บาท");
                status.setSelectedIndex(0);
            }
        });
        enter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                calculate(e);
            }
        });
    }

    private void calculate(MouseEvent e){
        String a = area.getText().trim();
        String b = price.getText().trim();

        FormPeople p = new FormPeople();
        if( !p.isInt(a) || !p.isInt(b) || a.equals("") || b.equals("")) {
            JOptionPane.showMessageDialog(this, "กรุณาใส่เฉพาะตัวเลข","ERROR",JOptionPane.ERROR_MESSAGE);
        } else if (status.getSelectedIndex()==0) {
            JOptionPane.showMessageDialog(this, "กรุณาเลือกลักษณะที่ดิน","ERROR",JOptionPane.ERROR_MESSAGE);
        } else {
            double area = Integer.parseInt(a);
            double price = Integer.parseInt(b);
            double count = area * price;

            int sta = status.getSelectedIndex();
            double taxB = 0;

           switch (sta){
               case 1 :
                   try {
                       String sql = "SELECT tax,price FROM  land_tax WHERE status_no=1";
                       pre = con.prepareStatement(sql);
                       rs = pre.executeQuery(sql);
                           while (rs.next()) {
                               if (count >= rs.getDouble("price")) {
                                   taxB = count * rs.getDouble("tax");
                                   taxBaht.setText(String.valueOf(format(taxB)));
                                   break;
                               }
                           }
                   }catch (Exception ev){
                       ev.printStackTrace();
                   }
                   break;

               case 2 :
                   try {
                       String sql = "SELECT tax,price FROM  land_tax WHERE status_no=2";
                       pre = con.prepareStatement(sql);
                       rs = pre.executeQuery(sql);
                       while (rs.next()) {
                           if (count >= rs.getDouble("price")) {
                               taxB = count * rs.getDouble("tax");
                               taxBaht.setText(String.valueOf(format(taxB)));
                               break;
                           }
                       }
                   }catch (Exception ev){
                       ev.printStackTrace();
                   }
                   break;

               case 3 :
                   try {
                       String sql = "SELECT tax,price FROM  land_tax WHERE status_no=3";
                       pre = con.prepareStatement(sql);
                       rs = pre.executeQuery(sql);
                       while (rs.next()) {
                           if (count >= rs.getDouble("price")) {
                               taxB = count * rs.getDouble("tax");
                               taxBaht.setText(String.valueOf(format(taxB)) );
                               break;
                           }
                       }
                   }catch (Exception ev){
                       ev.printStackTrace();
                   }
                   break;

               case 4 :
                   try {
                       String sql = "SELECT tax,price FROM  land_tax WHERE status_no=4";
                       pre = con.prepareStatement(sql);
                       rs = pre.executeQuery(sql);
                       while (rs.next()) {
                           if (count >= rs.getDouble("price")) {
                               taxB = count * rs.getDouble("tax");
                               taxBaht.setText(String.valueOf(format(taxB)));
                               break;
                           }
                       }
                   }catch (Exception ev){
                       ev.printStackTrace();
                   }
                   break;
           }

        }

    }

    private void initComponents(){
        try {
        String sql = "SELECT name FROM land_status ORDER BY status_no ASC ";
        pre = con.prepareStatement(sql);
        rs = pre.executeQuery(sql);

            while (rs.next()) {
                String taxName = rs.getString("name");
                status.addItem(taxName);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public String format(double a) {
        double n = Math.round(a);
        if (a <= 0) {
            return "ไม่เสียภาษี";
        } else {
            DecimalFormat dF = new DecimalFormat("#,###.00");
            return dF.format(n) + "\t บาท";
        }
    }

}
