package information;

import landTax.Connect;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public  class FormDeed extends JInternalFrame {
    private Connection con = Connect.ConnectDB();
    private ResultSet rs = null;
    private PreparedStatement pre = null;
    private DefaultTableModel modelDeed;
    public FormDeed() {
        setTitle("DEED");
        setSize(900, 550);
        setContentPane(homeDeed);
        setClosable(true);
        setMaximizable(true);

        initComponents();
        people();
        land_status();
        modelDeed = (DefaultTableModel) tableDeed.getModel();
this.addInternalFrameListener(new InternalFrameAdapter() {
    @Override
    public void internalFrameOpened(InternalFrameEvent e) {
       showData();
    }
});

//        this.addWindowListener(new WindowAdapter() {
//            public void windowOpened(WindowEvent e) {
//                showData();
//            }
//        });


    }


    private void showData() {
        try {
            int totalRow = tableDeed.getRowCount() - 1;
            while (totalRow > -1) {
                modelDeed.removeRow(totalRow);
                totalRow--;
            }
            String search = seachDeed.getText().trim();
            String sql = "SELECT * FROM title_deed "
                    + "where "
                    + "title_deed_no LIKE ('%" + search + "%')"
                    + "OR land_location LIKE ('%" + search + "%')"
                    + "OR area_of_land LIKE ('%" + search + "%')"
                    + "OR price_of_square_wa LIKE ('%" + search + "%')"
                    + "OR identification LIKE ('%" + search + "%')"
                    + "OR status_no LIKE ('%" + search + "%')";
            pre = con.prepareStatement(sql);
            rs = con.createStatement().executeQuery(sql);
            int row = 0;
            while (rs.next()) {
                modelDeed.addRow(new Object[0]);
                modelDeed.setValueAt(rs.getString("title_deed_no"), row, 0);
                modelDeed.setValueAt(rs.getString("land_location"), row, 1);
                modelDeed.setValueAt(rs.getString("area_of_land"), row, 2);
                modelDeed.setValueAt(rs.getString("price_of_square_wa"), row, 3);
                modelDeed.setValueAt(rs.getString("identification"), row, 4);
                modelDeed.setValueAt(rs.getString("status_no"), row, 5);
                row++;
            }
            tableDeed.setModel(modelDeed);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void people() {
        try {
            String sql = "SELECT identification FROM  people ORDER BY identification ASC";
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery(sql);

            while (rs.next()) {
                String peopleNo = rs.getString("identification");
                people.addItem(peopleNo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void land_status() {
        try {
            String sql = "SELECT status_no FROM  land_status ORDER BY status_no ASC";
            pre = con.prepareStatement(sql);
            rs = pre.executeQuery(sql);

            while (rs.next()) {
                String taxName = rs.getString("status_no");
//                String taxName = rs.getString("name");
                tax.addItem(taxName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {

        seachDeed.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtSeachDeedKeyReleased(e);
            }
        });
        tableDeed.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null},
                        {null, null, null, null, null, null}
                },
                new String[]{
                        "เลขที่โฉนด", "ตำแหน่งที่ดิน", "เนื้อที่ (ตร.ม.)", "ราคา/ตร.ม.", "ผู้ถือโฉนด", "ประเภทที่ดิน"
                }));
        tableDeed.getTableHeader().setFont(new Font("Leelawadee", Font.BOLD, 14));
        tableDeed.setRowHeight(15);
        scrollbar.setViewportView(tableDeed);
        tableDeed.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableDeedMouseClicked(e);
            }
        });

        dSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                saveActionPerformed(e);
            }
        });
        dDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                deleteActionPerformed(e);
            }
        });
        dAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addActionPerformed(e);
            }
        });
        dEdit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                editActionPerformed(e);
            }
        });
    }

    private void txtSeachDeedKeyReleased(java.awt.event.KeyEvent evt) {
        String searchText = seachDeed.getText();
        try {
            if (!searchText.isEmpty()) {
                showData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        showData();
    }

    private void saveActionPerformed(MouseEvent evt) {
            try {
                String sql = "INSERT INTO title_deed (title_deed_no,land_location,area_of_land,price_of_square_wa,identification,status_no)"
                        + "Values(?,?,?,?,?,?)";
                pre = con.prepareStatement(sql);
                pre.setString(1, deedNo.getText());
                pre.setString(2, location.getText());
                pre.setString(3, area.getText());
                pre.setString(4, price.getText());
                pre.setString(5, people.getSelectedItem().toString());
                pre.setString(6, tax.getSelectedItem().toString());

                if (pre.executeUpdate() != -1) {
                    JOptionPane.showMessageDialog(this, "บันทึกรายการแล้ว", "ผลการบันทึกรายการ", JOptionPane.INFORMATION_MESSAGE);

                    showData();
                    deedNo.setText("");
                    location.setText("");
                    area.setText("");
                    price.setText("");
                    people.setSelectedIndex(0);
                    tax.setSelectedIndex(0);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    private void editActionPerformed(MouseEvent evt) {
        try {
            if (tableDeed.getSelectedRow() == -1) {
                return;
            }
            String sql = "update title_deed set "
                    + " land_location = ? ,"
                    + " area_of_land = ? ,"
                    + " price_of_square_wa = ? ,"
                    + " identification = ? ,"
                    + " status_no = ? "
                    + " where title_deed_no = ? ";

            pre = con.prepareStatement(sql);
            pre.setString(1, location.getText().trim());
            pre.setString(2, area.getText().trim());
            pre.setString(3, price.getText().trim());
            pre.setString(4, people.getSelectedItem().toString().trim());
            pre.setString(5, tax.getSelectedItem().toString().trim());
            pre.setString(6, deedNo.getText().trim());

            if (pre.executeUpdate() != -1) {
                JOptionPane.showMessageDialog(this, "แก้ไขรายการแล้ว", "ผลการบันทึกรายการ", JOptionPane.INFORMATION_MESSAGE);

                deedNo.setEditable(true);
                deedNo.setText("");
                location.setText("");
                area.setText("");
                price.setText("");
                people.setSelectedIndex(0);
                tax.setSelectedIndex(0);
                showData();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private void addActionPerformed(MouseEvent evt) {
    deedNo.setEditable(true);
        deedNo.setText("");
        location.setText("");
        area.setText("");
        price.setText("");
        people.setSelectedIndex(0);
        tax.setSelectedIndex(0);

    showData();

}
    private void deleteActionPerformed(MouseEvent evt) {
        if (JOptionPane.showConfirmDialog(this, "คุณต้องการลบรายการ ?", "ยืนยันการลบรายการ", JOptionPane.OK_CANCEL_OPTION)
                == JOptionPane.OK_OPTION) {
            try {
                if (tableDeed.getSelectedRow() == -1) {
                    return;
                }
                String sql = "delete from  title_deed where title_deed_no = ? ";
                pre = con.prepareStatement(sql);
                pre.setString(1, deedNo.getText().trim());

                if (pre.executeUpdate() != -1) {
                    deedNo.setEditable(true);
                    deedNo.setText("");
                    location.setText("");
                    area.setText("");
                    price.setText("");
                    people.setSelectedIndex(0);
                    tax.setSelectedIndex(0);
                    showData();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void tableDeedMouseClicked(java.awt.event.MouseEvent evt) {
        int index = tableDeed.getSelectedRow();
        deedNo.setEditable(false);
        deedNo.setText(tableDeed.getValueAt(index, 0).toString());
        location.setText(tableDeed.getValueAt(index, 1).toString());
        area.setText(tableDeed.getValueAt(index, 2).toString());
        price.setText(tableDeed.getValueAt(index, 3).toString());
        people.setSelectedItem(tableDeed.getValueAt(index, 4).toString());
        tax.setSelectedItem(tableDeed.getValueAt(index, 5).toString());

    }


    private JTable tableDeed;
    private JTextField seachDeed;
    private JTextField deedNo;
    private JTextField location;
    private JTextField area;
    private JTextField price;
    private JComboBox tax;
    private JComboBox people;
    private JButton dDelete;
    private JButton dAdd;
    private JButton dEdit;
    private JPanel homeDeed;
    private JScrollPane scrollbar;
    private JButton dSave;




}
