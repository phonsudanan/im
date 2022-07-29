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
import java.util.ArrayList;

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

    public Tax() {
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

    public void taxTable() {
        try {
            int totalRow = table.getRowCount() - 1;
            while (totalRow > -1) {
                taxModel.removeRow(totalRow);
                totalRow--;
            }
            String num = number.getText().trim();
            String sql = "SELECT  title_deed_no, land_location, area_of_land, price_of_square_wa, name, identification, t.status_no, area_of_land*price_of_square_wa as ta " +
                    "FROM title_deed as t, land_status as l where t.status_no = l.status_no  and identification = " + num;
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery();

            ArrayList<Double> myList1 = new ArrayList(); //area_of_land*price_of_square_wa
            ArrayList<String> myList2 = new ArrayList(); //t.status_no

            int row = 0;
            while (rs.next()) {
                taxModel.addRow(new Object[0]);
                taxModel.setValueAt(rs.getString("title_deed_no"), row, 0);
                taxModel.setValueAt(rs.getString("land_location"), row, 1);
                taxModel.setValueAt(rs.getDouble("area_of_land"), row, 2);
                taxModel.setValueAt(rs.getDouble("price_of_square_wa"), row, 3);
                taxModel.setValueAt(rs.getString("name"), row, 4);
                myList1.add(row, rs.getDouble("ta"));
                myList2.add(row, rs.getString("t.status_no"));
                row++;
            }
            double[] target1 = new double[myList1.size()];
            String[] target2 = new String[myList2.size()];
            for (int i = 0; i < myList1.size(); i++) {
                target1[i] = myList1.get(i);
                target2[i] = myList2.get(i);
            }
            double moneyBath = 0;
            double[] t = new double[myList1.size()];
            for (int i = 0; i < myList1.size(); i++) {
                t[i] = calculate(target1[i], target2[i]);
                moneyBath += t[i];
                taxModel.setValueAt(t[i], i, 5);
            }
            bath.setText(moneyBath+"");
            table.setModel(taxModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private double calculate(double count, String name) {
        double taxB = 0;
        int sta = Integer.parseInt(name);
        switch (sta) {
            case 1:
                try {
                    String sql = "SELECT tax,price FROM  land_tax WHERE status_no=1";
                    pre = con.prepareStatement(sql);
                    rs = pre.executeQuery();
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

            case 2:
                try {
                    String sql = "SELECT tax,price FROM  land_tax WHERE status_no=2";
                    pre = con.prepareStatement(sql);
                    rs = pre.executeQuery();
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
                    rs = pre.executeQuery();
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
                    rs = pre.executeQuery();
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


}
