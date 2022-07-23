package information;

import landTax.Connect;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Tax extends JFrame {
    private JPanel taxReport;
    private JTable table;
    JLabel number;
    JLabel address;
    private JLabel bath;
    JLabel n;
    private JScrollPane sco;
    private Connection con = Connect.ConnectDB();
    private ResultSet rs = null;
    private PreparedStatement pre = null;
    private DefaultTableModel taxModel;
    public Tax(){
        setTitle("รายงานการเสียภาษี");
        setContentPane(taxReport);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        initComponents();
        taxModel = (DefaultTableModel) table.getModel();

    this.addWindowListener(new WindowAdapter() {
        @Override
        public void windowOpened(WindowEvent e) {
            taxTable();
        }
    });

    }

    private void initComponents() {
        table.setModel(new DefaultTableModel(
                new Object[][]{
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null}},
                new String[]{
                        "เลขที่โฉนด", "ตำแหน่งที่ดิน", "พื้นที่ ตรว.", "ราคาประเมิน", "การใช้ประโยชน์", "ภาษีที่ต้องจ่าย/ปี"
                }));
        sco.setViewportView(table);
        table.getTableHeader().setFont(new Font("Leelawadee", Font.BOLD, 14));
    }

public void taxTable(){
        try {
            int totalRow = table.getRowCount() -1 ;
            while ( totalRow > -1){
                taxModel.removeRow(totalRow);
                totalRow--;
            }
        String num = number.getText().trim();
        String sql = "SELECT title_deed_no, land_location, area_of_land, price_of_square_wa, name " +
                "FROM title_deed as t, land_status as l where t.status_no = l.status_no  and identification = " + num ;

            rs = con.createStatement().executeQuery(sql);
            int row = 0;
            while (rs.next()) {
                taxModel.addRow(new Object[0]);
                taxModel.setValueAt(rs.getString("title_deed_no"), row, 0);
                taxModel.setValueAt(rs.getString("land_location"), row, 1);
                taxModel.setValueAt(rs.getString("area_of_land"), row, 2);
                taxModel.setValueAt(rs.getString("price_of_square_wa"), row, 3);
                taxModel.setValueAt(rs.getString("name"), row, 4);


     double aa = calculate(rs.getDouble("area_of_land"),
                        rs.getDouble("price_of_square_wa"), rs.getString("name"));
                taxModel.setValueAt(aa, row,5);




                row++;
            }

            table.setModel(taxModel);
        } catch (SQLException e) {
           e.printStackTrace();
        }
    }

    private double calculate(double area, double price, String name) {

        double count = area * price;
        double taxB = 0;
        int sta = 0;
        if (name.equals("ที่ดินเพื่อการเกษตร")) {
            sta = 1;
        } else if (name.equals("ที่อยู่อาศัย")) {
            sta = 2;
        } else if (name.equals("ที่ดินเพื่อการพาณิชย์")) {
            sta = 3;
        } else if (name.equals("ที่ดินรกร้างว่างเปล่า")) {
            sta = 4;
        } else {
            sta = 0;
        }

        switch (sta) {
            case 1:
                try {
                    String sql = "SELECT tax,price FROM  land_tax WHERE status_no=1";
                    pre = con.prepareStatement(sql);
                    rs = pre.executeQuery(sql);
                    while (rs.next()) {
                        if (count >= rs.getDouble("price")) {
                            taxB = count * rs.getDouble("tax");
//                            bath.setText(String.valueOf(taxB));
                            break;
                        }
                    }
                } catch (Exception ev) {
                    ev.printStackTrace();
                }
                break;

            case 2:
                try {
                    String sql = "SELECT tax,price FROM  land_tax WHERE status_no=2";
                    pre = con.prepareStatement(sql);
                    rs = pre.executeQuery(sql);
                    while (rs.next()) {
                        if (count >= rs.getDouble("price")) {
                            taxB = count * rs.getDouble("tax");
                            break;
                        }
                    }
                } catch (Exception ev) {
                    ev.printStackTrace();
                }
                break;

            case 3:
                try {
                    String sql = "SELECT tax,price FROM  land_tax WHERE status_no=3";
                    pre = con.prepareStatement(sql);
                    rs = pre.executeQuery(sql);
                    while (rs.next()) {
                        if (count >= rs.getDouble("price")) {
                            taxB = count * rs.getDouble("tax");
                            break;
                        }
                    }
                } catch (Exception ev) {
                    ev.printStackTrace();
                }
                break;

            case 4:
                try {
                    String sql = "SELECT tax,price FROM  land_tax WHERE status_no=4";
                    pre = con.prepareStatement(sql);
                    rs = pre.executeQuery(sql);
                    while (rs.next()) {
                        if (count >= rs.getDouble("price")) {
                            taxB = count * rs.getDouble("tax");
                            break;
                        }
                    }
                } catch (Exception ev) {
                    ev.printStackTrace();
                }
                break;
        }
        return taxB;
    }
//        tab--;
//    }

}
