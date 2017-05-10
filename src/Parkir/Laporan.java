/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parkir;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author Miechel
 */
public class Laporan extends javax.swing.JFrame {

    /**
     * Creates new form Laporan
     */
    ImageIcon imageicon;
    public Laporan() {
        initComponents();
        imageicon = new ImageIcon("src/icon/Icon2.png");
        setIconImage(imageicon.getImage());
    }
    
    private DefaultTableModel model;
    private DefaultTableModel model2;
    private final Connection con = koneksi.getConnection();
    private Statement stt;
    private ResultSet rss;
    
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LapMotor = new usu.widget.Panel();
        BTNmtr_mtr = new javax.swing.JLabel();
        BTNmtr_mbl = new javax.swing.JLabel();
        Date1 = new com.toedter.calendar.JDateChooser();
        Date2 = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        BTNcetak = new javax.swing.JButton();
        BTNreset = new javax.swing.JButton();
        BTNcari = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        LapMobil = new usu.widget.Panel();
        BTNmbl_mbl = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        BTNcetak1 = new javax.swing.JButton();
        BTNreset1 = new javax.swing.JButton();
        BTNcari1 = new javax.swing.JButton();
        Date3 = new com.toedter.calendar.JDateChooser();
        Date4 = new com.toedter.calendar.JDateChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        BTNmbl_mtr = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.CardLayout());

        LapMotor.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/image/BG_lap_mtr.jpg"))); // NOI18N
        LapMotor.setPreferredSize(new java.awt.Dimension(865, 600));

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

        Date1.setDateFormatString("yyyy-MM-dd");
        Date1.setMinimumSize(new java.awt.Dimension(15, 13));
        Date1.setOpaque(false);

        Date2.setDateFormatString("yyyy-MM-dd");
        Date2.setOpaque(false);

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

        jLabel4.setFont(new java.awt.Font("Leelawadee UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 106, 175));
        jLabel4.setText("Periode Awal");

        jLabel5.setFont(new java.awt.Font("Leelawadee UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 106, 175));
        jLabel5.setText("Periode Akhir");

        BTNcetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Print.png"))); // NOI18N
        BTNcetak.setText("CETAK");
        BTNcetak.setOpaque(false);
        BTNcetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNcetakActionPerformed(evt);
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

        BTNcari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Zoom.png"))); // NOI18N
        BTNcari.setText("CARI");
        BTNcari.setOpaque(false);
        BTNcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNcariActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/btnExit.jpg"))); // NOI18N
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel3MousePressed(evt);
            }
        });

        javax.swing.GroupLayout LapMotorLayout = new javax.swing.GroupLayout(LapMotor);
        LapMotor.setLayout(LapMotorLayout);
        LapMotorLayout.setHorizontalGroup(
            LapMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LapMotorLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(LapMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addGroup(LapMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(LapMotorLayout.createSequentialGroup()
                            .addComponent(BTNmtr_mtr)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(BTNmtr_mbl))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 797, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(LapMotorLayout.createSequentialGroup()
                            .addGroup(LapMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Date1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(LapMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addGroup(LapMotorLayout.createSequentialGroup()
                                    .addComponent(Date2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(BTNcari)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(BTNreset)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(BTNcetak))))))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        LapMotorLayout.setVerticalGroup(
            LapMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LapMotorLayout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addGroup(LapMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BTNmtr_mbl)
                    .addComponent(BTNmtr_mtr))
                .addGap(29, 29, 29)
                .addGroup(LapMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(LapMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Date1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Date2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(LapMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(BTNcari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(LapMotorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BTNreset, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BTNcetak, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(20, 20, 20))
        );

        getContentPane().add(LapMotor, "card2");

        LapMobil.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/image/BG_lap_mbl.jpg"))); // NOI18N
        LapMobil.setPreferredSize(new java.awt.Dimension(865, 600));

        BTNmbl_mbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Lap_Mbl_Act.jpg"))); // NOI18N
        BTNmbl_mbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNmbl_mbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNmbl_mblMousePressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Leelawadee UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 106, 175));
        jLabel7.setText("Periode Awal");

        jLabel8.setFont(new java.awt.Font("Leelawadee UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 106, 175));
        jLabel8.setText("Periode Akhir");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/btnExit.jpg"))); // NOI18N
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel9MousePressed(evt);
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

        BTNreset1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Cancel.png"))); // NOI18N
        BTNreset1.setText("RESET");
        BTNreset1.setOpaque(false);
        BTNreset1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNreset1ActionPerformed(evt);
            }
        });

        BTNcari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Zoom.png"))); // NOI18N
        BTNcari1.setText("CARI");
        BTNcari1.setOpaque(false);
        BTNcari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNcari1ActionPerformed(evt);
            }
        });

        Date3.setDateFormatString("yyyy-MM-dd");
        Date3.setMinimumSize(new java.awt.Dimension(15, 13));
        Date3.setOpaque(false);

        Date4.setDateFormatString("yyyy-MM-dd");
        Date4.setOpaque(false);

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

        BTNmbl_mtr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Lap_Mtr.jpg"))); // NOI18N
        BTNmbl_mtr.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BTNmbl_mtr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNmbl_mtrMousePressed(evt);
            }
        });

        javax.swing.GroupLayout LapMobilLayout = new javax.swing.GroupLayout(LapMobil);
        LapMobil.setLayout(LapMobilLayout);
        LapMobilLayout.setHorizontalGroup(
            LapMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LapMobilLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(LapMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addGroup(LapMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(LapMobilLayout.createSequentialGroup()
                            .addComponent(BTNmbl_mtr)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(BTNmbl_mbl))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 797, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(LapMobilLayout.createSequentialGroup()
                            .addGroup(LapMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Date3, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(LapMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8)
                                .addGroup(LapMobilLayout.createSequentialGroup()
                                    .addComponent(Date4, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(BTNcari1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(BTNreset1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(BTNcetak1))))))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        LapMobilLayout.setVerticalGroup(
            LapMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LapMobilLayout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addGroup(LapMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BTNmbl_mbl)
                    .addComponent(BTNmbl_mtr))
                .addGap(29, 29, 29)
                .addGroup(LapMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(LapMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Date3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Date4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(LapMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(BTNcari1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(LapMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BTNreset1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BTNcetak1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(20, 20, 20))
        );

        getContentPane().add(LapMobil, "card3");

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

    private void BTNreset1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNreset1ActionPerformed
        // TODO add your handling code here:
        Date3.setDate(null);
        Date4.setDate(null);
    }//GEN-LAST:event_BTNreset1ActionPerformed

    private void BTNcari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNcari1ActionPerformed
        // TODO add your handling code here:
        if(Date3.getDate()==null || Date4.getDate()==null){
            JOptionPane.showMessageDialog(null, "Tentukan Periode Dulu ","Maaf",JOptionPane.INFORMATION_MESSAGE);
        }else{
            InitTableMobil();
            TampilKarcis4();
        }
    }//GEN-LAST:event_BTNcari1ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        InitTableMotor();
        TampilKarcisMotor();
        InitTableMobil();
        TampilKarcisMobil();
    }//GEN-LAST:event_formWindowOpened

    private void BTNmtr_mtrMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNmtr_mtrMousePressed
        // TODO add your handling code here:
        LapMotor.setVisible(true);
        LapMobil.setVisible(false);
    }//GEN-LAST:event_BTNmtr_mtrMousePressed

    private void BTNmtr_mblMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNmtr_mblMousePressed
        // TODO add your handling code here:
        LapMobil.setVisible(true);
        LapMotor.setVisible(false);
    }//GEN-LAST:event_BTNmtr_mblMousePressed

    private void BTNmbl_mtrMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNmbl_mtrMousePressed
        // TODO add your handling code here:
        LapMotor.setVisible(true);
        LapMobil.setVisible(false);
    }//GEN-LAST:event_BTNmbl_mtrMousePressed

    private void BTNmbl_mblMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNmbl_mblMousePressed
        // TODO add your handling code here:
        LapMobil.setVisible(true);
        LapMotor.setVisible(false);
    }//GEN-LAST:event_BTNmbl_mblMousePressed

    private void jLabel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MousePressed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jLabel3MousePressed

    private void jLabel9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MousePressed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jLabel9MousePressed

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
            java.util.logging.Logger.getLogger(Laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Laporan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTNcari;
    private javax.swing.JButton BTNcari1;
    private javax.swing.JButton BTNcetak;
    private javax.swing.JButton BTNcetak1;
    private javax.swing.JLabel BTNmbl_mbl;
    private javax.swing.JLabel BTNmbl_mtr;
    private javax.swing.JLabel BTNmtr_mbl;
    private javax.swing.JLabel BTNmtr_mtr;
    private javax.swing.JButton BTNreset;
    private javax.swing.JButton BTNreset1;
    private com.toedter.calendar.JDateChooser Date1;
    private com.toedter.calendar.JDateChooser Date2;
    private com.toedter.calendar.JDateChooser Date3;
    private com.toedter.calendar.JDateChooser Date4;
    private usu.widget.Panel LapMobil;
    private usu.widget.Panel LapMotor;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
