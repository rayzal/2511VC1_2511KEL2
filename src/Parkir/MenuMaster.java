/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parkir;

import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Miechel
 */
public class MenuMaster extends javax.swing.JFrame {

    /**
     * Creates new form MasterMenu
     */
    ImageIcon imageicon;
    int row=0;
    public MenuMaster() {
        initComponents();
        imageicon = new ImageIcon("src/icon/Icon2.png");
        setIconImage(imageicon.getImage());
        id_user();
        id_jenis();
    }
    
    private DefaultTableModel model1;
    private DefaultTableModel model2;
    private DefaultTableModel model3;
    private final Connection con = koneksi.getConnection();
    private Statement stt;
    private ResultSet rss;
    
    private void InitTablePetugas(){
        model1 = new DefaultTableModel();
        model1.addColumn("ID Petugas");
        model1.addColumn("Username");
        model1.addColumn("Password");  
        TBLpetugas.setModel(model1);
    }
    private void InitTableMotor(){
        model2 = new DefaultTableModel();
        model2.addColumn("ID Tipe");
        model2.addColumn("Jenis Kendaraan"); 
        TBLmotor.setModel(model2);
    }
    private void InitTableMobil(){
        model3 = new DefaultTableModel();
        model3.addColumn("ID Tipe");
        model3.addColumn("Jenis Kendaraan");  
        TBLmobil.setModel(model3);
    }
    
    private void TampilPetugas(){
        try{
            String sql = "SELECT * FROM user";
            stt = con.createStatement();
            rss = stt.executeQuery(sql);
            while(rss.next()){
                Object[] o = new Object[3];
                o[0] = rss.getString("id");
                o[1] = rss.getString("username");
                o[2] = rss.getString("password");
                model1.addRow(o);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    private void TampilMotor(){
        try{
            String Motor = "Motor";
            String sql = "SELECT * FROM tipe_kendaraan WHERE jenis_kendaraan like '%"+Motor+"%';";
            stt = con.createStatement();
            rss = stt.executeQuery(sql);
            while(rss.next()){
                Object[] o = new Object[2];
                o[0] = rss.getString("id_tipe");
                o[1] = rss.getString("jenis_kendaraan");
                model2.addRow(o);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    private void TampilMobil(){
        String Mobil = "Mobil";
        try{
            String sql = "SELECT * FROM tipe_kendaraan WHERE jenis_kendaraan like '%"+Mobil+"%';";
            stt = con.createStatement();
            rss = stt.executeQuery(sql);
            while(rss.next()){
                Object[] o = new Object[2];
                o[0] = rss.getString("id_tipe");
                o[1] = rss.getString("jenis_kendaraan");
                model3.addRow(o);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void id_user(){
        try{
        String sql = "SELECT MAX(id) as maxid from user";
        stt = con.createStatement();
        rss = stt.executeQuery(sql);
        rss.next();
            int id_karcis = rss.getInt("maxid")+1;
            String id = Integer.toString(id_karcis);
            txtID.setText(id);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void id_jenis(){
        try{
        String sql = "SELECT MAX(id_tipe) as maxid from tipe_kendaraan";
        stt = con.createStatement();
        rss = stt.executeQuery(sql);
        rss.next();
            int id_jenis = rss.getInt("maxid")+1;
            String id = Integer.toString(id_jenis);
            txtIDmotor.setText(id);
            txtIDmobil.setText(id);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void TambahDataUser(String id,String username, String password){
        try{
            String sql = "INSERT INTO user VALUES ('"+id+"','"+username+"','"+password+"')";
            stt = con.createStatement();
            stt.executeUpdate(sql);
            model1.addRow(new Object[]{id,username,password});
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public boolean UbahDataUser(String username, String password){
        String id_user = txtID.getText();
        try{
            String sql = "UPDATE user SET username='"+username+"',password='"+password+"' WHERE id="+id_user+";";
            stt = con.createStatement();
            stt.executeUpdate(sql);
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean HapusDataUser(){
        String id_user = txtID.getText();
        try{
            String sql = "DELETE FROM user WHERE id="+id_user+";";
            stt = con.createStatement();
            stt.executeUpdate(sql);
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public void TambahDataJenis(String id_tipe,String jenis_kendaraan){
        try{
            String sql = "INSERT INTO tipe_kendaraan VALUES ('"+id_tipe+"','"+jenis_kendaraan+"')";
            stt = con.createStatement();
            stt.executeUpdate(sql);
            model1.addRow(new Object[]{id_tipe,jenis_kendaraan});
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public boolean UbahDataJenisMtr(String jenis_kendaraan){
        String id_tipe = txtIDmotor.getText();
        try{
            String sql = "UPDATE tipe_kendaraan SET jenis_kendaraan='"+jenis_kendaraan+"' WHERE id_tipe="+id_tipe+";";
            stt = con.createStatement();
            stt.executeUpdate(sql);
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean HapusDataJenisMtr(){
        String id_tipe = txtIDmotor.getText();
        try{
            String sql = "DELETE FROM tipe_kendaraan WHERE id_tipe="+id_tipe+";";
            stt = con.createStatement();
            stt.executeUpdate(sql);
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean UbahDataJenisMbl(String jenis_kendaraan){
        String id_tipe = txtIDmobil.getText();
        try{
            String sql = "UPDATE tipe_kendaraan SET jenis_kendaraan='"+jenis_kendaraan+"' WHERE id_tipe="+id_tipe+";";
            stt = con.createStatement();
            stt.executeUpdate(sql);
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean HapusDataJenisMbl(){
        String id_tipe = txtIDmobil.getText();
        try{
            String sql = "DELETE FROM tipe_kendaraan WHERE id_tipe="+id_tipe+";";
            stt = con.createStatement();
            stt.executeUpdate(sql);
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public void bersih(){
        txtUsername.setText("");
        txtPassword.setText("");
        txtJenisMtr.setText("");
        txtJenisMbl.setText("");
        id_jenis();
    }
    public void clear(){
        BTNsimpan.setEnabled(true);
        BTNubah.setEnabled(false);
        BTNhapus.setEnabled(false);
        BTNsimpan2.setEnabled(true);
        BTNubah2.setEnabled(false);
        BTNhapus2.setEnabled(false);
        BTNsimpan3.setEnabled(true);
        BTNubah3.setEnabled(false);
        BTNhapus3.setEnabled(false);
        txtUsername.setText("");
        txtPassword.setText("");
        txtJenisMtr.setText("");
        txtJenisMbl.setText("");
        id_jenis();
    }
    public void TampilDataPetugas(){
    BTNsimpan.setEnabled(false);
    BTNubah.setEnabled(true);
    BTNhapus.setEnabled(true);
    row = TBLpetugas.getSelectedRow();
    txtID.setText(model1.getValueAt(row, 0).toString());
    txtUsername.setText(model1.getValueAt(row, 1).toString());
    txtPassword.setText(model1.getValueAt(row, 2).toString());
    }
    public void TampilDataJenisMtr(){
    BTNsimpan2.setEnabled(false);
    BTNubah2.setEnabled(true);
    BTNhapus2.setEnabled(true);
    row = TBLmotor.getSelectedRow();
    txtIDmotor.setText(model2.getValueAt(row, 0).toString());
    txtJenisMtr.setText(model2.getValueAt(row, 1).toString());
    }
    public void TampilDataJenisMbl(){
    BTNsimpan3.setEnabled(false);
    BTNubah3.setEnabled(true);
    BTNhapus3.setEnabled(true);
    row = TBLmobil.getSelectedRow();
    txtIDmobil.setText(model3.getValueAt(row, 0).toString());
    txtJenisMbl.setText(model3.getValueAt(row, 1).toString());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelUtama = new usu.widget.Panel();
        BTNexit = new javax.swing.JLabel();
        TBpetugas = new javax.swing.JLabel();
        TBmotor = new javax.swing.JLabel();
        TBmobil = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        PanelPetugas = new usu.widget.Panel();
        BTNexit1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TBLpetugas = new javax.swing.JTable();
        BTNsimpan = new javax.swing.JButton();
        BTNubah = new javax.swing.JButton();
        BTNhapus = new javax.swing.JButton();
        BTNclear = new javax.swing.JButton();
        PanelMotor = new usu.widget.Panel();
        BTNexit2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtIDmotor = new javax.swing.JTextField();
        txtJenisMtr = new javax.swing.JTextField();
        BTNsimpan2 = new javax.swing.JButton();
        BTNubah2 = new javax.swing.JButton();
        BTNhapus2 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        TBLmotor = new javax.swing.JTable();
        BTNclear1 = new javax.swing.JButton();
        PanelMobil = new usu.widget.Panel();
        BTNexit3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtIDmobil = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtJenisMbl = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        TBLmobil = new javax.swing.JTable();
        BTNsimpan3 = new javax.swing.JButton();
        BTNubah3 = new javax.swing.JButton();
        BTNhapus3 = new javax.swing.JButton();
        BTNclear2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        getContentPane().setLayout(new java.awt.CardLayout());

        PanelUtama.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/image/mastermenu.jpg"))); // NOI18N
        PanelUtama.setPreferredSize(new java.awt.Dimension(700, 500));

        BTNexit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/btnExit.jpg"))); // NOI18N
        BTNexit.setToolTipText("Kembali");
        BTNexit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNexit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNexitMousePressed(evt);
            }
        });

        TBpetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/TBpetugas.jpg"))); // NOI18N
        TBpetugas.setEnabled(false);

        TBmotor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/TBmotor.jpg"))); // NOI18N
        TBmotor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TBmotor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TBmotorMousePressed(evt);
            }
        });

        TBmobil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/TBmobil.jpg"))); // NOI18N
        TBmobil.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TBmobil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TBmobilMousePressed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(0, 102, 255));
        jLabel1.setText("*) Hanya Khusus Admin");

        javax.swing.GroupLayout PanelUtamaLayout = new javax.swing.GroupLayout(PanelUtama);
        PanelUtama.setLayout(PanelUtamaLayout);
        PanelUtamaLayout.setHorizontalGroup(
            PanelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelUtamaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BTNexit)
                .addGap(27, 27, 27))
            .addGroup(PanelUtamaLayout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(PanelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(PanelUtamaLayout.createSequentialGroup()
                        .addComponent(TBpetugas)
                        .addGap(40, 40, 40)
                        .addComponent(TBmotor)
                        .addGap(43, 43, 43)
                        .addComponent(TBmobil)))
                .addContainerGap(81, Short.MAX_VALUE))
        );
        PanelUtamaLayout.setVerticalGroup(
            PanelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelUtamaLayout.createSequentialGroup()
                .addContainerGap(200, Short.MAX_VALUE)
                .addGroup(PanelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TBpetugas, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(TBmotor, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(TBmobil, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(62, 62, 62)
                .addComponent(BTNexit)
                .addGap(32, 32, 32))
        );

        getContentPane().add(PanelUtama, "card3");

        PanelPetugas.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/image/FormPetugas.jpg"))); // NOI18N

        BTNexit1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/btnExit.jpg"))); // NOI18N
        BTNexit1.setToolTipText("Kembali");
        BTNexit1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNexit1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNexit1MousePressed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("ID");

        txtID.setEditable(false);
        txtID.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Username");

        txtUsername.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Password");

        txtPassword.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        TBLpetugas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID Petugas", "Username", "Password"
            }
        ));
        TBLpetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TBLpetugasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TBLpetugas);
        if (TBLpetugas.getColumnModel().getColumnCount() > 0) {
            TBLpetugas.getColumnModel().getColumn(2).setHeaderValue("Password");
        }

        BTNsimpan.setText("SIMPAN");
        BTNsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNsimpanActionPerformed(evt);
            }
        });

        BTNubah.setText("UBAH");
        BTNubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNubahActionPerformed(evt);
            }
        });

        BTNhapus.setText("HAPUS");
        BTNhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNhapusActionPerformed(evt);
            }
        });

        BTNclear.setText("CLEAR");
        BTNclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNclearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelPetugasLayout = new javax.swing.GroupLayout(PanelPetugas);
        PanelPetugas.setLayout(PanelPetugasLayout);
        PanelPetugasLayout.setHorizontalGroup(
            PanelPetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPetugasLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(PanelPetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelPetugasLayout.createSequentialGroup()
                        .addGroup(PanelPetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelPetugasLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(BTNexit1)))
                        .addGap(27, 27, 27))
                    .addGroup(PanelPetugasLayout.createSequentialGroup()
                        .addGroup(PanelPetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(PanelPetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PanelPetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtUsername)
                                .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(PanelPetugasLayout.createSequentialGroup()
                                    .addComponent(BTNsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(BTNubah, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(BTNhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(BTNclear, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        PanelPetugasLayout.setVerticalGroup(
            PanelPetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelPetugasLayout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addGroup(PanelPetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelPetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PanelPetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(BTNhapus, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                        .addComponent(BTNclear, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                    .addComponent(BTNubah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BTNsimpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BTNexit1)
                .addGap(32, 32, 32))
        );

        getContentPane().add(PanelPetugas, "card2");

        PanelMotor.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/image/FormMotor.jpg"))); // NOI18N

        BTNexit2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/btnExit.jpg"))); // NOI18N
        BTNexit2.setToolTipText("Kembali");
        BTNexit2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNexit2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNexit2MousePressed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("Jenis Motor");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("ID");

        txtIDmotor.setEditable(false);
        txtIDmotor.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        txtJenisMtr.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        BTNsimpan2.setText("SIMPAN");
        BTNsimpan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNsimpan2ActionPerformed(evt);
            }
        });

        BTNubah2.setText("UBAH");
        BTNubah2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNubah2ActionPerformed(evt);
            }
        });

        BTNhapus2.setText("HAPUS");
        BTNhapus2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNhapus2ActionPerformed(evt);
            }
        });

        TBLmotor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID Jenis", "Jenis Motor"
            }
        ));
        TBLmotor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TBLmotorMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TBLmotor);

        BTNclear1.setText("CLEAR");
        BTNclear1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNclear1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelMotorLayout = new javax.swing.GroupLayout(PanelMotor);
        PanelMotor.setLayout(PanelMotorLayout);
        PanelMotorLayout.setHorizontalGroup(
            PanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelMotorLayout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addGroup(PanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(PanelMotorLayout.createSequentialGroup()
                            .addGroup(PanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(PanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtIDmotor, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(PanelMotorLayout.createSequentialGroup()
                                    .addComponent(BTNsimpan2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(BTNubah2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(BTNhapus2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(BTNclear1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtJenisMtr))
                            .addGap(173, 173, 173))
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(BTNexit2))
                .addGap(27, 27, 27))
        );
        PanelMotorLayout.setVerticalGroup(
            PanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelMotorLayout.createSequentialGroup()
                .addGap(135, 135, 135)
                .addGroup(PanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIDmotor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtJenisMtr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BTNubah2, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(BTNsimpan2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BTNhapus2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BTNclear1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(BTNexit2)
                .addGap(32, 32, 32))
        );

        getContentPane().add(PanelMotor, "card5");

        PanelMobil.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/image/FormMobil.jpg"))); // NOI18N

        BTNexit3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/btnExit.jpg"))); // NOI18N
        BTNexit3.setToolTipText("Kembali");
        BTNexit3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNexit3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNexit3MousePressed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("ID");

        txtIDmobil.setEditable(false);
        txtIDmobil.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Jenis Mobil");

        txtJenisMbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        TBLmobil.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID Jenis", "Jenis Mobil"
            }
        ));
        TBLmobil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TBLmobilMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TBLmobil);

        BTNsimpan3.setText("SIMPAN");
        BTNsimpan3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNsimpan3ActionPerformed(evt);
            }
        });

        BTNubah3.setText("UBAH");
        BTNubah3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNubah3ActionPerformed(evt);
            }
        });

        BTNhapus3.setText("HAPUS");
        BTNhapus3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNhapus3ActionPerformed(evt);
            }
        });

        BTNclear2.setText("CLEAR");
        BTNclear2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNclear2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelMobilLayout = new javax.swing.GroupLayout(PanelMobil);
        PanelMobil.setLayout(PanelMobilLayout);
        PanelMobilLayout.setHorizontalGroup(
            PanelMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelMobilLayout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addGroup(PanelMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelMobilLayout.createSequentialGroup()
                        .addGroup(PanelMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(PanelMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtIDmobil, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PanelMobilLayout.createSequentialGroup()
                                .addComponent(BTNsimpan3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BTNubah3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BTNhapus3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BTNclear2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtJenisMbl))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelMobilLayout.createSequentialGroup()
                        .addGroup(PanelMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BTNexit3))
                        .addGap(27, 27, 27))))
        );
        PanelMobilLayout.setVerticalGroup(
            PanelMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelMobilLayout.createSequentialGroup()
                .addGap(135, 135, 135)
                .addGroup(PanelMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIDmobil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtJenisMbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(PanelMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BTNubah3, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(BTNsimpan3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BTNhapus3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BTNclear2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(BTNexit3)
                .addGap(32, 32, 32))
        );

        getContentPane().add(PanelMobil, "card4");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void BTNexitMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNexitMousePressed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_BTNexitMousePressed

    private void BTNexit1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNexit1MousePressed
        // TODO add your handling code here:
        PanelUtama.setVisible(true);
        PanelPetugas.setVisible(false);
    }//GEN-LAST:event_BTNexit1MousePressed

    private void BTNexit3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNexit3MousePressed
        // TODO add your handling code here:
        PanelUtama.setVisible(true);
        PanelMobil.setVisible(false);
    }//GEN-LAST:event_BTNexit3MousePressed

    private void TBmotorMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TBmotorMousePressed
        // TODO add your handling code here:
        PanelUtama.setVisible(false);
        PanelPetugas.setVisible(false);
        BTNubah.setEnabled(false);
        BTNhapus.setEnabled(false);
        PanelMotor.setVisible(true);
        PanelMobil.setVisible(false);
    }//GEN-LAST:event_TBmotorMousePressed

    private void TBmobilMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TBmobilMousePressed
        // TODO add your handling code here:
        PanelUtama.setVisible(false);
        PanelPetugas.setVisible(false);
        BTNubah.setEnabled(false);
        BTNhapus.setEnabled(false);
        PanelMotor.setVisible(false);
        PanelMobil.setVisible(true);
    }//GEN-LAST:event_TBmobilMousePressed

    private void BTNexit2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNexit2MousePressed
        // TODO add your handling code here:
        PanelUtama.setVisible(true);
        PanelMotor.setVisible(false);
    }//GEN-LAST:event_BTNexit2MousePressed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        InitTablePetugas();
        TampilPetugas();
        InitTableMotor();
        TampilMotor();
        InitTableMobil();
        TampilMobil();
    }//GEN-LAST:event_formComponentShown

    private void BTNsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNsimpanActionPerformed
        // TODO add your handling code here:
        int pilih = JOptionPane.showConfirmDialog(this, "Yakin ingin simpan data ?","Confirm Simpan",JOptionPane.YES_OPTION);
        if(pilih==JOptionPane.YES_OPTION){
        
        String id = txtID.getText();
        String username = txtUsername.getText();
        String  password = txtPassword.getText();
        TambahDataUser(id,username,password);
        bersih();
        }
        InitTablePetugas();
        TampilPetugas();
        id_user();
    }//GEN-LAST:event_BTNsimpanActionPerformed

    private void TBLpetugasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TBLpetugasMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount()==1){
        TampilDataPetugas();
        }
    }//GEN-LAST:event_TBLpetugasMouseClicked

    private void BTNclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNclearActionPerformed
        // TODO add your handling code here:
        clear();
        id_user();
    }//GEN-LAST:event_BTNclearActionPerformed

    private void BTNubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNubahActionPerformed
        // TODO add your handling code here:
        int pilih = JOptionPane.showConfirmDialog(this, "Yakin ingin ubah data ?","Confirm Ubah",JOptionPane.YES_OPTION);
        if(pilih==JOptionPane.YES_OPTION){
        String username = txtUsername.getText();
        String password = txtPassword.getText();

            UbahDataUser(username,password);
            JOptionPane.showMessageDialog(null,"Berhasil Update");
            bersih();
        }
        InitTablePetugas();
        TampilPetugas();
        id_user();
    }//GEN-LAST:event_BTNubahActionPerformed

    private void BTNhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNhapusActionPerformed
        // TODO add your handling code here:
        int pilih = JOptionPane.showConfirmDialog(this, "Yakin ingin hapus data ?","Confirm Hapus",JOptionPane.YES_OPTION);
        if(pilih==JOptionPane.YES_OPTION){
            HapusDataUser();
            JOptionPane.showMessageDialog(null,"Berhasil Hapus User");
            bersih();
        }
        InitTablePetugas();
        TampilPetugas();
        id_user();
    }//GEN-LAST:event_BTNhapusActionPerformed

    private void BTNclear1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNclear1ActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_BTNclear1ActionPerformed

    private void BTNclear2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNclear2ActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_BTNclear2ActionPerformed

    private void BTNsimpan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNsimpan2ActionPerformed
        // TODO add your handling code here:
        int pilih = JOptionPane.showConfirmDialog(this, "Yakin ingin simpan data ?","Confirm Simpan",JOptionPane.YES_OPTION);
        if(pilih==JOptionPane.YES_OPTION){
        
        String id_tipe = txtIDmotor.getText();
        String jenis_kendaraan = txtJenisMtr.getText();
        TambahDataJenis(id_tipe,jenis_kendaraan);
        bersih();
        }
        InitTableMotor();
        TampilMotor();
        id_jenis();
    }//GEN-LAST:event_BTNsimpan2ActionPerformed

    private void TBLmotorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TBLmotorMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount()==1){
        TampilDataJenisMtr();
        }
    }//GEN-LAST:event_TBLmotorMouseClicked

    private void BTNubah2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNubah2ActionPerformed
        // TODO add your handling code here:
        int pilih = JOptionPane.showConfirmDialog(this, "Yakin ingin ubah data ?","Confirm Ubah",JOptionPane.YES_OPTION);
        if(pilih==JOptionPane.YES_OPTION){
        String jenis = txtJenisMtr.getText();

            UbahDataJenisMtr(jenis);
            JOptionPane.showMessageDialog(null,"Berhasil Update");
            clear();
        }
        InitTableMotor();
        TampilMotor();
        id_jenis();
    }//GEN-LAST:event_BTNubah2ActionPerformed

    private void BTNhapus2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNhapus2ActionPerformed
        // TODO add your handling code here:
        int pilih = JOptionPane.showConfirmDialog(this, "Yakin ingin hapus data ?","Confirm Hapus",JOptionPane.YES_OPTION);
        if(pilih==JOptionPane.YES_OPTION){
            HapusDataJenisMtr();
            JOptionPane.showMessageDialog(null,"Berhasil Hapus Data");
            bersih();
        }
        InitTableMotor();
        TampilMotor();
        id_jenis();
    }//GEN-LAST:event_BTNhapus2ActionPerformed

    private void BTNsimpan3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNsimpan3ActionPerformed
        // TODO add your handling code here:
        int pilih = JOptionPane.showConfirmDialog(this, "Yakin ingin simpan data ?","Confirm Simpan",JOptionPane.YES_OPTION);
        if(pilih==JOptionPane.YES_OPTION){
        
        String id_tipe = txtIDmobil.getText();
        String jenis_kendaraan = txtJenisMbl.getText();
        TambahDataJenis(id_tipe,jenis_kendaraan);
        bersih();
        }
        InitTableMobil();
        TampilMobil();
        id_jenis();
    }//GEN-LAST:event_BTNsimpan3ActionPerformed

    private void BTNubah3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNubah3ActionPerformed
        // TODO add your handling code here:
        int pilih = JOptionPane.showConfirmDialog(this, "Yakin ingin ubah data ?","Confirm Ubah",JOptionPane.YES_OPTION);
        if(pilih==JOptionPane.YES_OPTION){
        String jenis = txtJenisMbl.getText();

            UbahDataJenisMbl(jenis);
            JOptionPane.showMessageDialog(null,"Berhasil Update");
            clear();
        }
        InitTableMobil();
        TampilMobil();
        id_jenis();
    }//GEN-LAST:event_BTNubah3ActionPerformed

    private void BTNhapus3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNhapus3ActionPerformed
        // TODO add your handling code here:
        int pilih = JOptionPane.showConfirmDialog(this, "Yakin ingin hapus data ?","Confirm Hapus",JOptionPane.YES_OPTION);
        if(pilih==JOptionPane.YES_OPTION){
            HapusDataJenisMbl();
            JOptionPane.showMessageDialog(null,"Berhasil Hapus Data");
            bersih();
        }
        InitTableMobil();
        TampilMobil();
        id_jenis();
    }//GEN-LAST:event_BTNhapus3ActionPerformed

    private void TBLmobilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TBLmobilMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount()==1){
        TampilDataJenisMbl();
        }
    }//GEN-LAST:event_TBLmobilMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuMaster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuMaster().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTNclear;
    private javax.swing.JButton BTNclear1;
    private javax.swing.JButton BTNclear2;
    private javax.swing.JLabel BTNexit;
    private javax.swing.JLabel BTNexit1;
    private javax.swing.JLabel BTNexit2;
    private javax.swing.JLabel BTNexit3;
    private javax.swing.JButton BTNhapus;
    private javax.swing.JButton BTNhapus2;
    private javax.swing.JButton BTNhapus3;
    private javax.swing.JButton BTNsimpan;
    private javax.swing.JButton BTNsimpan2;
    private javax.swing.JButton BTNsimpan3;
    private javax.swing.JButton BTNubah;
    private javax.swing.JButton BTNubah2;
    private javax.swing.JButton BTNubah3;
    private usu.widget.Panel PanelMobil;
    private usu.widget.Panel PanelMotor;
    private usu.widget.Panel PanelPetugas;
    private usu.widget.Panel PanelUtama;
    private javax.swing.JTable TBLmobil;
    private javax.swing.JTable TBLmotor;
    private javax.swing.JTable TBLpetugas;
    private javax.swing.JLabel TBmobil;
    private javax.swing.JLabel TBmotor;
    private javax.swing.JLabel TBpetugas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtIDmobil;
    private javax.swing.JTextField txtIDmotor;
    private javax.swing.JTextField txtJenisMbl;
    private javax.swing.JTextField txtJenisMtr;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
