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

public class FormPeople extends JInternalFrame {
//    public class FormPeople extends JFrame {

    private Connection con = Connect.ConnectDB();
    private ResultSet rs = null;
    private PreparedStatement pre = null;
    private DefaultTableModel modelPeople;

//    MaskFormatter mask = null;
//    public static void main(String[] args) {
//
//        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font(
//                "Leelawadee", Font.PLAIN, 12)));
//        new FormPeople().setVisible(true);
//    }
    public FormPeople() {
        super("PEOPLE", false, true, true, false);
        setSize(950, 550);
        setContentPane(homePeople);


        initComponents();

        modelPeople = (DefaultTableModel) tablePeople.getModel();
        this.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameOpened(InternalFrameEvent e) {
               showData();
            }
        });
//this.addWindowListener(new WindowAdapter() {
//    @Override
//    public void windowOpened(WindowEvent e) {
//       showData();
//    }
//});


        view.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Tax t = new Tax();
                t.number.setText(peopleNo.getText());
                t.n.setText(peopleName.getText());
                t.address.setText(address.getText());
                t.setVisible(true);
            }
        });
    }
    private void showData(){
        try{
            int totalRow = tablePeople.getRowCount() -1 ;
            while ( totalRow > -1){
                modelPeople.removeRow(totalRow);
                totalRow--;
            }
            String search = seachPeople.getText().trim();
            String sql = " SELECT * FROM people "
                    + " where "
                    + " identification LIKE ('%" + search + "%') "
                    + " OR full_name LIKE ('%" + search + "%')"
                    + " OR address LIKE ('%" + search + "%')"
                    + " OR phone LIKE ('%" + search + "%')";
            pre = con.prepareStatement(sql);
            rs = con.createStatement().executeQuery(sql);

            int row = 0;
            while (rs.next()){
                modelPeople.addRow(new Object[0]);
                modelPeople.setValueAt(rs.getString("identification"), row, 0);
                modelPeople.setValueAt(rs.getString("full_name"), row, 1);
                modelPeople.setValueAt(rs.getString("address"), row, 2);
                modelPeople.setValueAt(rs.getString("phone"), row, 3);
                row++;
            }
            tablePeople.setModel(modelPeople);
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    private void seachPeopleKeyReleased(java.awt.event.KeyEvent evt) {
        String searchText = seachPeople.getText();
        try {
            if (!searchText.isEmpty()) {
                showData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        showData();
    }
    private void initComponents() {

        seachPeople.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                seachPeopleKeyReleased(e);
            }
        });
        tablePeople.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String[]{
                        "เลขที่ผู้เสียภาษี", "ชื่อ", "ที่อยู่", "เบอร์ติดต่อ"
                }));
        tablePeople.getTableHeader().setFont(new Font("Leelawadee", Font.BOLD, 14));
        tablePeople.setRowHeight(25);
//        tablePeople.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        scrollbar.setViewportView(tablePeople);

        tablePeople.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tablePeopleMouseClicked(e);
            }
        });

        pDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pDeleteActionPerformed(e);
            }
        });
        pAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pAddActionPerformed(e);
            }
        });
        pEdit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pEditActionPerformed(e);
            }
        });
        pSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pSaveActionPerformed(e);
            }
        });


    }

    private void pDeleteActionPerformed(MouseEvent evt) {
        if (JOptionPane.showConfirmDialog(this, "คุณต้องการลบรายการ ?", "ยืนยันการลบรายการ", JOptionPane.OK_CANCEL_OPTION)
                == JOptionPane.OK_OPTION) {
            try {
                if (tablePeople.getSelectedRow() == -1) {
                    return;
                }

                String sql = "delete from people " + " where identification = ? ";

                PreparedStatement pre = con.prepareStatement(sql);
                pre.setString(1, peopleNo.getText().trim());

                if (pre.executeUpdate() != -1) {
                    JOptionPane.showMessageDialog(this, "ลบรายการแล้ว", "ผลการลบรายการ", JOptionPane.INFORMATION_MESSAGE);
                }

                peopleNo.setText("");
                peopleName.setText("");
                address.setText("");
                phone.setText("");
                showData();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    private void pAddActionPerformed(MouseEvent evt) {
        peopleNo.setEditable(true);
        peopleNo.setText("");
        peopleName.setText("");
        address.setText("");
        phone.setText("");

        showData();
    }
    private void pEditActionPerformed(MouseEvent evt) {
        try {
            if (tablePeople.getSelectedRow() == -1) {
                return;
            }

            String sql = "update people set full_name = ?, address = ?, phone = ? where identification = ? ";

            pre = con.prepareStatement(sql);
            pre.setString(1, peopleName.getText().trim());
            pre.setString(2, address.getText().trim());
            pre.setString(3, phone.getText().trim());
            pre.setString(4, peopleNo.getText().trim());

            if (pre.executeUpdate() != -1) {
                JOptionPane.showMessageDialog(this, "แก้ไขรายการแล้ว", "ผลการบันทึกรายการ", JOptionPane.INFORMATION_MESSAGE);
            }
            peopleNo.setEditable(true);
            peopleNo.setText("");
            peopleName.setText("");
            address.setText("");
            phone.setText("");
            showData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void pSaveActionPerformed(MouseEvent evt) {
            int peopleLength = peopleNo.getText().trim().length();

        if (peopleNo.getText().trim().equals("") || peopleName.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "ใส่ค่าว่างไม่ได้", "แจ้งเตือน", JOptionPane.ERROR_MESSAGE);
        }  else if (peopleLength != 13) {
            JOptionPane.showMessageDialog(this, "เลขที่ผู้เสียภาษี ต้องใส่เป็นตัวเลข 13 หลัก เท่านั้น", "แจ้งเตือน", JOptionPane.ERROR_MESSAGE);
        }  else if ( !isInt(peopleNo.getText().trim())) {
            JOptionPane.showMessageDialog(this, "เลขที่ผู้เสียภาษี ต้องใส่เป็นตัวเลขเท่านั้น", "แจ้งเตือน", JOptionPane.ERROR_MESSAGE);
           } else {
                try {
                    String sql = "INSERT INTO people (identification, full_name, address, phone)"
                            + "Values(?,?,?,?)";
                    pre = con.prepareStatement(sql);
                    pre.setString(1, peopleNo.getText());
                    pre.setString(2, peopleName.getText());
                    pre.setString(3, address.getText());
                    pre.setString(4, phone.getText());


                    if (pre.executeUpdate() != -1) {
                        JOptionPane.showMessageDialog(this, "บันทึกรายการแล้ว", "ผลการบันทึกรายการ", JOptionPane.INFORMATION_MESSAGE);

                        showData();
                        peopleNo.setText("");
                        peopleName.setText("");
                        address.setText("");
                        phone.setText("");

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
   }

    public boolean isInt(String s){
                    for (int i = 0; i < s.length(); i++) {
                    char sNo = s.charAt(i);
                   if (!Character.isDigit(sNo)) {
                       return false;
                        }
                }
        return true;
        }




    private void tablePeopleMouseClicked(MouseEvent evt){
        int index = tablePeople.getSelectedRow();
        peopleNo.setEditable(false);
        peopleNo.setText(tablePeople.getValueAt(index, 0).toString());
        peopleName.setText(tablePeople.getValueAt(index, 1).toString());
        address.setText(tablePeople.getValueAt(index, 2).toString());
        phone.setText(tablePeople.getValueAt(index, 3).toString());

    }


    JTextField peopleNo;
    private JTextField seachPeople;
    JTextField peopleName;
    JTextField address;
    private JTextField phone;
    private JTable tablePeople;
    private JButton pDelete;
    private JButton pAdd;
    private JButton pEdit;
    private JPanel homePeople;
    private JScrollPane scrollbar;
    private JButton pSave;
    private JButton view;
    private JScrollBar scrollBar;


   }

