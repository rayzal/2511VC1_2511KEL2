/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parkir;

import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Miechel
 */
public class Admin extends javax.swing.JFrame {

    /**
     * Creates new form Admin
     */
    public Admin() {
        int row=0;
        initComponents();
        id_user();
    }

    private DefaultTableModel model;
    private DefaultTableModel model1;
    private DefaultTableModel model2;
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
        model = new DefaultTableModel();
        model.addColumn("No Karcis");
        model.addColumn("NOPOL");
        model.addColumn("Jenis Motor");
        model.addColumn("TGL Masuk");
        model.addColumn("Blok Parkir");
        model.addColumn("Jam Masuk");
        model.addColumn("Jam Keluar");
        model.addColumn("Tarif");   
        jTable1.setModel(model);
    }
    private void InitTableMobil(){
        model2 = new DefaultTableModel();
        model2.addColumn("No Karcis");
        model2.addColumn("NOPOL");
        model2.addColumn("Jenis Mobil");
        model2.addColumn("TGL Masuk");
        model2.addColumn("Blok Parkir");
        model2.addColumn("Jam Masuk");
        model2.addColumn("Jam Keluar");
        model2.addColumn("Tarif");   
        jTable2.setModel(model2);
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
    public void TambahDataUser(String id,String username, String password, String level){
        try{
            String sql = "INSERT INTO user VALUES ('"+id+"','"+username+"','"+password+"','"+level+"')";
            stt = con.createStatement();
            stt.executeUpdate(sql);
            model1.addRow(new Object[]{id,username,password,level});
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
    private void TampilKarcisMotor(){
        try{
            String sql = "SELECT * FROM karcis_motor";
            stt = con.createStatement();
            rss = stt.executeQuery(sql);
            while(rss.next()){
                Object[] o = new Object[8];
                o[0] = rss.getString("id_karcis");
                o[1] = rss.getString("nopol_motor");
                o[2] = rss.getString("jenis_mtr");
                o[3] = rss.getString("tgl_parkir");
                o[4] = rss.getString("blok_parkir");
                o[5] = rss.getString("jam_masuk_mtr");
                o[6] = rss.getString("jam_keluar_mtr");
                o[7] = rss.getString("tarif");
                model.addRow(o);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    private void TampilKarcisMobil(){
        try{
            String sql = "SELECT * FROM karcis_mobil";
            stt = con.createStatement();
            rss = stt.executeQuery(sql);
            while(rss.next()){
                Object[] o = new Object[8];
                o[0] = rss.getString("id_karcis");
                o[1] = rss.getString("nopol_mobil");
                o[2] = rss.getString("jenis_mbl");
                o[3] = rss.getString("tgl_parkir");
                o[4] = rss.getString("blok_parkir");
                o[5] = rss.getString("jam_masuk_mbl");
                o[6] = rss.getString("jam_keluar_mbl");
                o[7] = rss.getString("tarif");
                model2.addRow(o);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void TampilKarcis2(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate1 = sdf.format(Date1.getDate());
        String strDate2 = sdf.format(Date2.getDate());

        try{
            String sql = "SELECT * FROM karcis_motor WHERE tgl_parkir>='"+strDate1+"' AND tgl_parkir<='"+strDate2+"'";
            stt = con.createStatement();
            rss = stt.executeQuery(sql);
            while(rss.next()){
                Object[] o = new Object[8];
                o[0] = rss.getString("id_karcis");
                o[1] = rss.getString("nopol_motor");
                o[2] = rss.getString("jenis_mtr");
                o[3] = rss.getString("tgl_parkir");
                o[4] = rss.getString("blok_parkir");
                o[5] = rss.getString("jam_masuk_mtr");
                o[6] = rss.getString("jam_keluar_mtr");
                o[7] = rss.getString("tarif");
                model.addRow(o);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void TampilKarcis4(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate3 = sdf.format(Date3.getDate());
        String strDate4 = sdf.format(Date4.getDate());

        try{
            String sql = "SELECT * FROM karcis_mobil WHERE tgl_parkir>='"+strDate3+"' AND tgl_parkir<='"+strDate4+"'";
            stt = con.createStatement();
            rss = stt.executeQuery(sql);
            while(rss.next()){
                Object[] o = new Object[8];
                o[0] = rss.getString("id_karcis");
                o[1] = rss.getString("nopol_mobil");
                o[2] = rss.getString("jenis_mbl");
                o[3] = rss.getString("tgl_parkir");
                o[4] = rss.getString("blok_parkir");
                o[5] = rss.getString("jam_masuk_mbl");
                o[6] = rss.getString("jam_keluar_mbl");
                o[7] = rss.getString("tarif");
                model2.addRow(o);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void CetakLaporan(String namaFile,HashMap hash){
    try{
        InputStream report;
        report = getClass().getResourceAsStream(namaFile);
        JasperPrint jprint = JasperFillManager.fillReport(report, hash,con);
            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setFitPageZoomRatio();
            viewer.setVisible(true);
         }catch(Exception e){
        System.out.println(e.getMessage());
        }
    }
    public void bersih(){
        txtUsername.setText("");
        txtPassword.setText("");
    }
    public void clear(){
        BTNsimpan.setEnabled(true);
        BTNubah.setEnabled(false);
        txtUsername.setText("");
        txtPassword.setText("");

    }
    public void TampilDataPetugas(){
    BTNsimpan.setEnabled(false);
    BTNubah.setEnabled(true);
    BTNhapus.setEnabled(true);
    int row = TBLpetugas.getSelectedRow();
    txtID.setText(model1.getValueAt(row, 0).toString());
    txtUsername.setText(model1.getValueAt(row, 1).toString());
    txtPassword.setText(model1.getValueAt(row, 2).toString());
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Home = new usu.widget.Panel();
        BTNhome = new javax.swing.JLabel();
        BTNdata = new javax.swing.JLabel();
        BTNreport = new javax.swing.JLabel();
        BTNabout = new javax.swing.JLabel();
        BTNclose = new javax.swing.JLabel();
        Data = new usu.widget.Panel();
        BTNhome1 = new javax.swing.JLabel();
        BTNdata2 = new javax.swing.JLabel();
        BTNreport2 = new javax.swing.JLabel();
        BTNabout2 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        BTNclear = new javax.swing.JButton();
        txtID = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        TBLpetugas = new javax.swing.JTable();
        BTNsimpan = new javax.swing.JButton();
        BTNubah = new javax.swing.JButton();
        BTNhapus = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        BTNclose1 = new javax.swing.JLabel();
        ReportMotor = new usu.widget.Panel();
        BTNhome3 = new javax.swing.JLabel();
        BTNdata3 = new javax.swing.JLabel();
        BTNreport3 = new javax.swing.JLabel();
        BTNabout3 = new javax.swing.JLabel();
        BTNmtr_mtr = new javax.swing.JLabel();
        BTNmtr_mbl = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        Date1 = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        Date2 = new com.toedter.calendar.JDateChooser();
        BTNcari = new javax.swing.JButton();
        BTNreset = new javax.swing.JButton();
        BTNcetak = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        BTNclose2 = new javax.swing.JLabel();
        ReportMobil = new usu.widget.Panel();
        BTNhome4 = new javax.swing.JLabel();
        BTNdata4 = new javax.swing.JLabel();
        BTNreport4 = new javax.swing.JLabel();
        BTNabout4 = new javax.swing.JLabel();
        BTNmbl_mtr = new javax.swing.JLabel();
        BTNmbl_mbl = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        Date3 = new com.toedter.calendar.JDateChooser();
        Date4 = new com.toedter.calendar.JDateChooser();
        BTNcari1 = new javax.swing.JButton();
        BTNreset1 = new javax.swing.JButton();
        BTNcetak1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        BTNclose3 = new javax.swing.JLabel();
        About = new usu.widget.Panel();
        BTNhome2 = new javax.swing.JLabel();
        BTNdata1 = new javax.swing.JLabel();
        BTNreport1 = new javax.swing.JLabel();
        BTNabout1 = new javax.swing.JLabel();
        BTNclose4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("[ADMINISTRATOR] Grand City Mall");
        setUndecorated(true);
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.CardLayout());

        Home.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/image/adminarea.jpg"))); // NOI18N
        Home.setPreferredSize(new java.awt.Dimension(900, 600));

        BTNhome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/home.png"))); // NOI18N
        BTNhome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        BTNdata.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/data2.png"))); // NOI18N
        BTNdata.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNdata.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNdataMousePressed(evt);
            }
        });

        BTNreport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/report2.png"))); // NOI18N
        BTNreport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNreport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNreportMousePressed(evt);
            }
        });

        BTNabout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/about2.png"))); // NOI18N
        BTNabout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNabout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNaboutMousePressed(evt);
            }
        });

        BTNclose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/close.png"))); // NOI18N
        BTNclose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNclose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNcloseMousePressed(evt);
            }
        });

        javax.swing.GroupLayout HomeLayout = new javax.swing.GroupLayout(Home);
        Home.setLayout(HomeLayout);
        HomeLayout.setHorizontalGroup(
            HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomeLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(BTNhome)
                .addGap(0, 0, 0)
                .addComponent(BTNdata)
                .addGap(0, 0, 0)
                .addComponent(BTNreport)
                .addGap(0, 0, 0)
                .addComponent(BTNabout)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 225, Short.MAX_VALUE)
                .addComponent(BTNclose)
                .addGap(29, 29, 29))
        );
        HomeLayout.setVerticalGroup(
            HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomeLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BTNclose)
                    .addComponent(BTNabout)
                    .addComponent(BTNreport)
                    .addComponent(BTNdata)
                    .addComponent(BTNhome))
                .addContainerGap(485, Short.MAX_VALUE))
        );

        getContentPane().add(Home, "card2");

        Data.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/image/data_app.jpg"))); // NOI18N
        Data.setPreferredSize(new java.awt.Dimension(900, 600));

        BTNhome1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/home2.png"))); // NOI18N
        BTNhome1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNhome1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNhome1MousePressed(evt);
            }
        });

        BTNdata2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/data.png"))); // NOI18N
        BTNdata2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        BTNreport2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/report2.png"))); // NOI18N
        BTNreport2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNreport2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNreport2MousePressed(evt);
            }
        });

        BTNabout2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/about2.png"))); // NOI18N
        BTNabout2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNabout2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNabout2MousePressed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel21.setText("ID");

        BTNclear.setText("CLEAR");
        BTNclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNclearActionPerformed(evt);
            }
        });

        txtID.setEditable(false);
        txtID.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel22.setText("Username");

        txtUsername.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel23.setText("Password");

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
        jScrollPane3.setViewportView(TBLpetugas);

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

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/ptg2.jpg"))); // NOI18N

        BTNclose1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/close.png"))); // NOI18N
        BTNclose1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNclose1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNclose1MousePressed(evt);
            }
        });

        javax.swing.GroupLayout DataLayout = new javax.swing.GroupLayout(Data);
        Data.setLayout(DataLayout);
        DataLayout.setHorizontalGroup(
            DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DataLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DataLayout.createSequentialGroup()
                        .addGroup(DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addGroup(DataLayout.createSequentialGroup()
                                .addGroup(DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtUsername)
                                        .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(DataLayout.createSequentialGroup()
                                            .addComponent(BTNsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(BTNubah, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(BTNhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(BTNclear, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(44, Short.MAX_VALUE))
                    .addGroup(DataLayout.createSequentialGroup()
                        .addComponent(BTNhome1)
                        .addGap(0, 0, 0)
                        .addComponent(BTNdata2)
                        .addGap(0, 0, 0)
                        .addComponent(BTNreport2)
                        .addGap(0, 0, 0)
                        .addComponent(BTNabout2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BTNclose1)
                        .addGap(29, 29, 29))))
        );
        DataLayout.setVerticalGroup(
            DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DataLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BTNabout2)
                    .addComponent(BTNreport2)
                    .addComponent(BTNdata2)
                    .addComponent(BTNhome1)
                    .addComponent(BTNclose1))
                .addGroup(DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DataLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addGroup(DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(DataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BTNubah, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BTNhapus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BTNclear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BTNsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DataLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 150, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85))))
        );

        getContentPane().add(Data, "card2");

        ReportMotor.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/image/reportdata.jpg"))); // NOI18N
        ReportMotor.setPreferredSize(new java.awt.Dimension(900, 600));

        BTNhome3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/home2.png"))); // NOI18N
        BTNhome3.setToolTipText("");
        BTNhome3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNhome3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNhome3MousePressed(evt);
            }
        });

        BTNdata3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/data2.png"))); // NOI18N
        BTNdata3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNdata3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNdata3MousePressed(evt);
            }
        });

        BTNreport3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/report.png"))); // NOI18N
        BTNreport3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        BTNabout3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/about2.png"))); // NOI18N
        BTNabout3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNabout3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNabout3MousePressed(evt);
            }
        });

        BTNmtr_mtr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Lap_Mtr_Act.jpg"))); // NOI18N
        BTNmtr_mtr.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNmtr_mtr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNmtr_mtrMousePressed(evt);
            }
        });

        BTNmtr_mbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Lap_Mbl.jpg"))); // NOI18N
        BTNmtr_mbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNmtr_mbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNmtr_mblMousePressed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Leelawadee UI", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 106, 175));
        jLabel13.setText("Periode Awal");

        Date1.setDateFormatString("yyyy-MM-dd");
        Date1.setMinimumSize(new java.awt.Dimension(15, 13));
        Date1.setOpaque(false);

        jLabel14.setFont(new java.awt.Font("Leelawadee UI", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 106, 175));
        jLabel14.setText("Periode Akhir");

        Date2.setDateFormatString("yyyy-MM-dd");
        Date2.setOpaque(false);

        BTNcari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Zoom.png"))); // NOI18N
        BTNcari.setText("CARI");
        BTNcari.setOpaque(false);
        BTNcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNcariActionPerformed(evt);
            }
        });

        BTNreset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Cancel.png"))); // NOI18N
        BTNreset.setText("RESET");
        BTNreset.setOpaque(false);
        BTNreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNresetActionPerformed(evt);
            }
        });

        BTNcetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Print.png"))); // NOI18N
        BTNcetak.setText("CETAK");
        BTNcetak.setOpaque(false);
        BTNcetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNcetakActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        BTNclose2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/close.png"))); // NOI18N
        BTNclose2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNclose2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNclose2MousePressed(evt);
            }
        });

        javax.swing.GroupLayout ReportMotorLayout = new javax.swing.GroupLayout(ReportMotor);
        ReportMotor.setLayout(ReportMotorLayout);
        ReportMotorLayout.setHorizontalGroup(
            ReportMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReportMotorLayout.createSequentialGroup()
                .addGroup(ReportMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ReportMotorLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(ReportMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 797, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ReportMotorLayout.createSequentialGroup()
                                .addGroup(ReportMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Date1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(ReportMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addGroup(ReportMotorLayout.createSequentialGroup()
                                        .addComponent(Date2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(BTNcari)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(BTNreset)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(BTNcetak))))))
                    .addGroup(ReportMotorLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(BTNmtr_mtr)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BTNmtr_mbl)))
                .addContainerGap(51, Short.MAX_VALUE))
            .addGroup(ReportMotorLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(BTNhome3)
                .addGap(0, 0, 0)
                .addComponent(BTNdata3)
                .addGap(0, 0, 0)
                .addComponent(BTNreport3)
                .addGap(0, 0, 0)
                .addComponent(BTNabout3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BTNclose2)
                .addGap(29, 29, 29))
        );
        ReportMotorLayout.setVerticalGroup(
            ReportMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReportMotorLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(ReportMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BTNabout3)
                    .addComponent(BTNreport3)
                    .addComponent(BTNdata3)
                    .addComponent(BTNhome3)
                    .addComponent(BTNclose2))
                .addGap(38, 38, 38)
                .addGroup(ReportMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BTNmtr_mtr, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BTNmtr_mbl, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(ReportMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ReportMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Date1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Date2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ReportMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(BTNcari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(ReportMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BTNreset, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BTNcetak, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        getContentPane().add(ReportMotor, "card2");

        ReportMobil.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/image/reportdata.jpg"))); // NOI18N
        ReportMobil.setPreferredSize(new java.awt.Dimension(900, 600));

        BTNhome4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/home2.png"))); // NOI18N
        BTNhome4.setToolTipText("");
        BTNhome4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNhome4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNhome4MousePressed(evt);
            }
        });

        BTNdata4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/data2.png"))); // NOI18N
        BTNdata4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNdata4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNdata4MousePressed(evt);
            }
        });

        BTNreport4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/report.png"))); // NOI18N
        BTNreport4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        BTNabout4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/about2.png"))); // NOI18N
        BTNabout4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNabout4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNabout4MousePressed(evt);
            }
        });

        BTNmbl_mtr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Lap_Mtr.jpg"))); // NOI18N
        BTNmbl_mtr.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNmbl_mtr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNmbl_mtrMousePressed(evt);
            }
        });

        BTNmbl_mbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Lap_Mbl_Act.jpg"))); // NOI18N
        BTNmbl_mbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNmbl_mbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNmbl_mblMousePressed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Leelawadee UI", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 106, 175));
        jLabel20.setText("Periode Awal");

        jLabel24.setFont(new java.awt.Font("Leelawadee UI", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 106, 175));
        jLabel24.setText("Periode Akhir");

        Date3.setDateFormatString("yyyy-MM-dd");
        Date3.setMinimumSize(new java.awt.Dimension(15, 13));
        Date3.setOpaque(false);

        Date4.setDateFormatString("yyyy-MM-dd");
        Date4.setOpaque(false);

        BTNcari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Zoom.png"))); // NOI18N
        BTNcari1.setText("CARI");
        BTNcari1.setOpaque(false);
        BTNcari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNcari1ActionPerformed(evt);
            }
        });

        BTNreset1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Cancel.png"))); // NOI18N
        BTNreset1.setText("RESET");
        BTNreset1.setOpaque(false);
        BTNreset1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNreset1ActionPerformed(evt);
            }
        });

        BTNcetak1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Print.png"))); // NOI18N
        BTNcetak1.setText("CETAK");
        BTNcetak1.setOpaque(false);
        BTNcetak1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNcetak1ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        BTNclose3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/close.png"))); // NOI18N
        BTNclose3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNclose3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNclose3MousePressed(evt);
            }
        });

        javax.swing.GroupLayout ReportMobilLayout = new javax.swing.GroupLayout(ReportMobil);
        ReportMobil.setLayout(ReportMobilLayout);
        ReportMobilLayout.setHorizontalGroup(
            ReportMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReportMobilLayout.createSequentialGroup()
                .addGroup(ReportMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ReportMobilLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(ReportMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 797, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ReportMobilLayout.createSequentialGroup()
                                .addGroup(ReportMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Date3, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel20))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(ReportMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel24)
                                    .addGroup(ReportMobilLayout.createSequentialGroup()
                                        .addComponent(Date4, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(BTNcari1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(BTNreset1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(BTNcetak1))))))
                    .addGroup(ReportMobilLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(BTNmbl_mtr)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BTNmbl_mbl)))
                .addContainerGap(51, Short.MAX_VALUE))
            .addGroup(ReportMobilLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(BTNhome4)
                .addGap(0, 0, 0)
                .addComponent(BTNdata4)
                .addGap(0, 0, 0)
                .addComponent(BTNreport4)
                .addGap(0, 0, 0)
                .addComponent(BTNabout4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BTNclose3)
                .addGap(29, 29, 29))
        );
        ReportMobilLayout.setVerticalGroup(
            ReportMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReportMobilLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(ReportMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BTNabout4)
                    .addComponent(BTNreport4)
                    .addComponent(BTNdata4)
                    .addComponent(BTNhome4)
                    .addComponent(BTNclose3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(ReportMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BTNmbl_mbl)
                    .addComponent(BTNmbl_mtr))
                .addGap(38, 38, 38)
                .addGroup(ReportMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ReportMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Date3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Date4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ReportMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(BTNcari1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(ReportMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BTNreset1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BTNcetak1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        getContentPane().add(ReportMobil, "card2");

        About.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/image/aboutarea.jpg"))); // NOI18N
        About.setPreferredSize(new java.awt.Dimension(900, 600));

        BTNhome2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/home2.png"))); // NOI18N
        BTNhome2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNhome2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNhome2MousePressed(evt);
            }
        });

        BTNdata1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/data2.png"))); // NOI18N
        BTNdata1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNdata1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNdata1MousePressed(evt);
            }
        });

        BTNreport1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/report2.png"))); // NOI18N
        BTNreport1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNreport1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNreport1MousePressed(evt);
            }
        });

        BTNabout1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/about.png"))); // NOI18N
        BTNabout1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNabout1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNabout1MousePressed(evt);
            }
        });

        BTNclose4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/close.png"))); // NOI18N
        BTNclose4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNclose4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNclose4MousePressed(evt);
            }
        });

        javax.swing.GroupLayout AboutLayout = new javax.swing.GroupLayout(About);
        About.setLayout(AboutLayout);
        AboutLayout.setHorizontalGroup(
            AboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AboutLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(BTNhome2)
                .addGap(0, 0, 0)
                .addComponent(BTNdata1)
                .addGap(0, 0, 0)
                .addComponent(BTNreport1)
                .addGap(0, 0, 0)
                .addComponent(BTNabout1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 225, Short.MAX_VALUE)
                .addComponent(BTNclose4)
                .addGap(29, 29, 29))
        );
        AboutLayout.setVerticalGroup(
            AboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AboutLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(AboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BTNclose4)
                    .addComponent(BTNabout1)
                    .addComponent(BTNreport1)
                    .addComponent(BTNdata1)
                    .addComponent(BTNhome2))
                .addContainerGap(485, Short.MAX_VALUE))
        );

        getContentPane().add(About, "card2");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void BTNcetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNcetakActionPerformed
        // TODO add your handling code here:
        String namaFile;
        HashMap hash = new HashMap();
        if(Date1.getDate()==null && Date2.getDate()==null){
            namaFile = "/laporan/laporan_parkir_motor.jasper";
        }else{
            namaFile = "/laporan/laporan_mtr_2.jasper";
            hash.put("date1",Date1.getDate());
            hash.put("date2",Date2.getDate());
        }
        CetakLaporan(namaFile, hash);
    }//GEN-LAST:event_BTNcetakActionPerformed

    private void BTNresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNresetActionPerformed
        // TODO add your handling code here:
        Date1.setDate(null);
        Date2.setDate(null);
    }//GEN-LAST:event_BTNresetActionPerformed

    private void BTNcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNcariActionPerformed
        // TODO add your handling code here:
        if(Date1.getDate()==null || Date2.getDate()==null){
            JOptionPane.showMessageDialog(null, "Tentukan Periode Dulu ","Maaf",JOptionPane.INFORMATION_MESSAGE);
        }else{
            InitTableMotor();
            TampilKarcis2();
        }
    }//GEN-LAST:event_BTNcariActionPerformed

    private void BTNmtr_mblMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNmtr_mblMousePressed
        // TODO add your handling code here:
        ReportMobil.setVisible(true);
        ReportMotor.setVisible(false);
        //TampilKarcisMobil();
    }//GEN-LAST:event_BTNmtr_mblMousePressed

    private void BTNmtr_mtrMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNmtr_mtrMousePressed
        // TODO add your handling code here:
        ReportMotor.setVisible(true);
        ReportMobil.setVisible(false);
    }//GEN-LAST:event_BTNmtr_mtrMousePressed

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

    private void BTNsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNsimpanActionPerformed
        // TODO add your handling code here:
        int pilih = JOptionPane.showConfirmDialog(this, "Yakin ingin simpan data ?","Confirm Simpan",JOptionPane.YES_OPTION);
        if(pilih==JOptionPane.YES_OPTION){

            String id = txtID.getText();
            String level = "1";
            String username = txtUsername.getText();
            String  password = txtPassword.getText();
            TambahDataUser(id,username,password,level);
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

    private void BTNmbl_mtrMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNmbl_mtrMousePressed
        // TODO add your handling code here:
        ReportMotor.setVisible(true);
        ReportMobil.setVisible(false);
        //TampilKarcisMotor();
    }//GEN-LAST:event_BTNmbl_mtrMousePressed

    private void BTNmbl_mblMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNmbl_mblMousePressed
        // TODO add your handling code here:
        ReportMobil.setVisible(true);
        ReportMotor.setVisible(false);
        //TampilKarcisMobil();
    }//GEN-LAST:event_BTNmbl_mblMousePressed

    private void BTNcari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNcari1ActionPerformed
        // TODO add your handling code here:
        if(Date3.getDate()==null || Date4.getDate()==null){
            JOptionPane.showMessageDialog(null, "Tentukan Periode Dulu ","Maaf",JOptionPane.INFORMATION_MESSAGE);
        }else{
            InitTableMobil();
            TampilKarcis4();
        }
    }//GEN-LAST:event_BTNcari1ActionPerformed

    private void BTNreset1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNreset1ActionPerformed
        // TODO add your handling code here:
        Date3.setDate(null);
        Date4.setDate(null);
    }//GEN-LAST:event_BTNreset1ActionPerformed

    private void BTNcetak1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNcetak1ActionPerformed
        // TODO add your handling code here:
        String namaFile;
        HashMap hash = new HashMap();
        if(Date3.getDate()==null && Date4.getDate()==null){
            namaFile = "/laporan/laporan_parkir_mobil.jasper";
        }else{
            namaFile = "/laporan/laporan_mbl_2.jasper";
            hash.put("date1",Date3.getDate());
            hash.put("date2",Date4.getDate());
        }
        CetakLaporan(namaFile, hash);
    }//GEN-LAST:event_BTNcetak1ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        InitTableMotor();
        TampilKarcisMotor();
        InitTableMobil();
        TampilKarcisMobil();
    }//GEN-LAST:event_formWindowOpened

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        InitTablePetugas();
        TampilPetugas();
        InitTableMotor();
        InitTableMobil();
    }//GEN-LAST:event_formComponentShown

    private void BTNdataMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNdataMousePressed
        // TODO add your handling code here:
        Home.setVisible(false);
        Data.setVisible(true);
        ReportMotor.setVisible(false);
        ReportMobil.setVisible(false);
        About.setVisible(false);
    }//GEN-LAST:event_BTNdataMousePressed

    private void BTNreportMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNreportMousePressed
        // TODO add your handling code here:
        Home.setVisible(false);
        Data.setVisible(false);
        ReportMotor.setVisible(true);
        ReportMobil.setVisible(false);
        About.setVisible(false);
    }//GEN-LAST:event_BTNreportMousePressed

    private void BTNhome1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNhome1MousePressed
        // TODO add your handling code here:
        Home.setVisible(true);
        Data.setVisible(false);
        ReportMotor.setVisible(false);
        ReportMobil.setVisible(false);
        About.setVisible(false);
    }//GEN-LAST:event_BTNhome1MousePressed

    private void BTNreport2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNreport2MousePressed
        // TODO add your handling code here:
        Home.setVisible(false);
        Data.setVisible(false);
        ReportMotor.setVisible(true);
        ReportMobil.setVisible(false);
        About.setVisible(false);
    }//GEN-LAST:event_BTNreport2MousePressed

    private void BTNhome3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNhome3MousePressed
        // TODO add your handling code here:
        Home.setVisible(true);
        Data.setVisible(false);
        ReportMotor.setVisible(false);
        ReportMobil.setVisible(false);
        About.setVisible(false);
    }//GEN-LAST:event_BTNhome3MousePressed

    private void BTNdata3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNdata3MousePressed
        // TODO add your handling code here:
        Home.setVisible(false);
        Data.setVisible(true);
        ReportMotor.setVisible(false);
        ReportMobil.setVisible(false);
        About.setVisible(false);
    }//GEN-LAST:event_BTNdata3MousePressed

    private void BTNhome4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNhome4MousePressed
        // TODO add your handling code here:
        Home.setVisible(true);
        Data.setVisible(false);
        ReportMotor.setVisible(false);
        ReportMobil.setVisible(false);
        About.setVisible(false);
    }//GEN-LAST:event_BTNhome4MousePressed

    private void BTNdata4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNdata4MousePressed
        // TODO add your handling code here:
        Home.setVisible(false);
        Data.setVisible(true);
        ReportMotor.setVisible(false);
        ReportMobil.setVisible(false);
        About.setVisible(false);
    }//GEN-LAST:event_BTNdata4MousePressed

    private void BTNcloseMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNcloseMousePressed
        // TODO add your handling code here:
        this.dispose();
        new LoginForm().setVisible(true);
    }//GEN-LAST:event_BTNcloseMousePressed

    private void BTNclose1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNclose1MousePressed
        // TODO add your handling code here:
        this.dispose();
        new LoginForm().setVisible(true);
    }//GEN-LAST:event_BTNclose1MousePressed

    private void BTNclose2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNclose2MousePressed
        // TODO add your handling code here:
        this.dispose();
        new LoginForm().setVisible(true);
    }//GEN-LAST:event_BTNclose2MousePressed

    private void BTNclose3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNclose3MousePressed
        // TODO add your handling code here:
        this.dispose();
        new LoginForm().setVisible(true);
    }//GEN-LAST:event_BTNclose3MousePressed

    private void BTNdata1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNdata1MousePressed
        // TODO add your handling code here:
        Home.setVisible(false);
        Data.setVisible(true);
        ReportMotor.setVisible(false);
        ReportMobil.setVisible(false);
        About.setVisible(false);
    }//GEN-LAST:event_BTNdata1MousePressed

    private void BTNreport1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNreport1MousePressed
        // TODO add your handling code here:
        Home.setVisible(false);
        Data.setVisible(false);
        ReportMotor.setVisible(true);
        ReportMobil.setVisible(false);
        About.setVisible(false);
    }//GEN-LAST:event_BTNreport1MousePressed

    private void BTNclose4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNclose4MousePressed
        // TODO add your handling code here:
        this.dispose();
        new LoginForm().setVisible(true);
    }//GEN-LAST:event_BTNclose4MousePressed

    private void BTNabout1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNabout1MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BTNabout1MousePressed

    private void BTNaboutMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNaboutMousePressed
        // TODO add your handling code here:
        Home.setVisible(false);
        Data.setVisible(false);
        ReportMotor.setVisible(false);
        ReportMobil.setVisible(false);
        About.setVisible(true);
    }//GEN-LAST:event_BTNaboutMousePressed

    private void BTNabout2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNabout2MousePressed
        // TODO add your handling code here:
        Home.setVisible(false);
        Data.setVisible(false);
        ReportMotor.setVisible(false);
        ReportMobil.setVisible(false);
        About.setVisible(true);
    }//GEN-LAST:event_BTNabout2MousePressed

    private void BTNabout3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNabout3MousePressed
        // TODO add your handling code here:
        Home.setVisible(false);
        Data.setVisible(false);
        ReportMotor.setVisible(false);
        ReportMobil.setVisible(false);
        About.setVisible(true);
    }//GEN-LAST:event_BTNabout3MousePressed

    private void BTNabout4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNabout4MousePressed
        // TODO add your handling code here:
        Home.setVisible(false);
        Data.setVisible(false);
        ReportMotor.setVisible(false);
        ReportMobil.setVisible(false);
        About.setVisible(true);
    }//GEN-LAST:event_BTNabout4MousePressed

    private void BTNhome2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNhome2MousePressed
        // TODO add your handling code here:
        Home.setVisible(true);
        Data.setVisible(false);
        ReportMotor.setVisible(false);
        ReportMobil.setVisible(false);
        About.setVisible(false);
    }//GEN-LAST:event_BTNhome2MousePressed

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
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private usu.widget.Panel About;
    private javax.swing.JLabel BTNabout;
    private javax.swing.JLabel BTNabout1;
    private javax.swing.JLabel BTNabout2;
    private javax.swing.JLabel BTNabout3;
    private javax.swing.JLabel BTNabout4;
    private javax.swing.JButton BTNcari;
    private javax.swing.JButton BTNcari1;
    private javax.swing.JButton BTNcetak;
    private javax.swing.JButton BTNcetak1;
    private javax.swing.JButton BTNclear;
    private javax.swing.JLabel BTNclose;
    private javax.swing.JLabel BTNclose1;
    private javax.swing.JLabel BTNclose2;
    private javax.swing.JLabel BTNclose3;
    private javax.swing.JLabel BTNclose4;
    private javax.swing.JLabel BTNdata;
    private javax.swing.JLabel BTNdata1;
    private javax.swing.JLabel BTNdata2;
    private javax.swing.JLabel BTNdata3;
    private javax.swing.JLabel BTNdata4;
    private javax.swing.JButton BTNhapus;
    private javax.swing.JLabel BTNhome;
    private javax.swing.JLabel BTNhome1;
    private javax.swing.JLabel BTNhome2;
    private javax.swing.JLabel BTNhome3;
    private javax.swing.JLabel BTNhome4;
    private javax.swing.JLabel BTNmbl_mbl;
    private javax.swing.JLabel BTNmbl_mtr;
    private javax.swing.JLabel BTNmtr_mbl;
    private javax.swing.JLabel BTNmtr_mtr;
    private javax.swing.JLabel BTNreport;
    private javax.swing.JLabel BTNreport1;
    private javax.swing.JLabel BTNreport2;
    private javax.swing.JLabel BTNreport3;
    private javax.swing.JLabel BTNreport4;
    private javax.swing.JButton BTNreset;
    private javax.swing.JButton BTNreset1;
    private javax.swing.JButton BTNsimpan;
    private javax.swing.JButton BTNubah;
    private usu.widget.Panel Data;
    private com.toedter.calendar.JDateChooser Date1;
    private com.toedter.calendar.JDateChooser Date2;
    private com.toedter.calendar.JDateChooser Date3;
    private com.toedter.calendar.JDateChooser Date4;
    private usu.widget.Panel Home;
    private usu.widget.Panel ReportMobil;
    private usu.widget.Panel ReportMotor;
    private javax.swing.JTable TBLpetugas;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
