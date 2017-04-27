/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parkir;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Miechel
 */
public class AppMotor extends javax.swing.JFrame {
    java.util.Date tglsekarang = new java.util.Date();
    private SimpleDateFormat smpdtfmt = new SimpleDateFormat("dd MMMMMMM yyyy",Locale.getDefault());
    private SimpleDateFormat smpdtfmt2 = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
    private String tanggal = smpdtfmt.format(tglsekarang);
    private String tanggal2 = smpdtfmt2.format(tglsekarang);

    /**
     * Creates new form AppMotor
     */    
    public void setJam(){
        ActionListener taskPerformer = new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                String nol_jam="",nol_menit="",nol_detik="";
                java.util.Date dateTime = new java.util.Date();
                int nilai_jam = dateTime.getHours();
                int nilai_menit = dateTime.getMinutes();
                int nilai_detik = dateTime.getSeconds();
                
                if(nilai_jam<=9) nol_jam="0";
                if(nilai_menit<=9) nol_menit="0";
                if(nilai_detik<=9) nol_detik="0";
                
                String waktu = nol_jam+Integer.toString(nilai_jam);
                String menit = nol_menit+Integer.toString(nilai_menit);
                String detik = nol_detik+Integer.toString(nilai_detik);
                
               txtJam.setText(waktu+":"+menit+":"+detik+"");
               txtJamIn.setText(waktu+":"+menit+":"+detik+"");
               txtJamOut2.setText(waktu+":"+menit+":"+detik+"");
            }
        };
        new Timer(1000,taskPerformer).start();
    }
    public AppMotor() {
        initComponents();
        txtTgl.setText(tanggal);
        txtTglIn.setText(tanggal2);
        setJam();
        no_karcis();
    }
    private DefaultTableModel model;
    private final Connection con = koneksi.getConnection();
    private Statement stt;
    private ResultSet rss;
    private PreparedStatement pst;
    
    private void InitTable(){
        model = new DefaultTableModel();
        model.addColumn("No Karcis");
        model.addColumn("No Polisi");
        model.addColumn("Jenis Motor");
        model.addColumn("TGL Masuk");
        model.addColumn("Blok Parkir");
        model.addColumn("Jam Masuk");
        model.addColumn("Jam Keluar");
        model.addColumn("Tarif");   
        jTable1.setModel(model);
    }
    
    private void TampilKarcis(){
        try{
            String sql = "SELECT * FROM karcis_motor order by id_karcis desc";
            stt = con.createStatement();
            rss = stt.executeQuery(sql);
            while(rss.next()){
                Object[] o = new Object[8];
                o[0] = rss.getString("id_karcis");
                o[1] = rss.getString("nopol_motor");
                o[2] = rss.getString("id_jenis_mtr");
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
    public void no_karcis(){
        try{
        String sql = "SELECT max(id_karcis) as maxid from karcis_motor";
        stt = con.createStatement();
        rss = stt.executeQuery(sql);
        rss.next();
            int id_karcis = rss.getInt("maxid")+1;
            String id = Integer.toString(id_karcis);
            txtNoKarcis.setText(id);
//            String p = id;
//            System.out.println(p);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
//    public void karcis_next(){
//        int row = 0;
//        LBkarcis.setText(model.getValueAt(row+1, 0).toString());
//    }
    
    public void TambahData(String nopol, String jenis_motor,String tanggal_in,String blok,
            String jam_in){
        try{
            String sql = "INSERT INTO karcis_motor VALUES (NULL,'"+nopol+"','"+jenis_motor+"',"
                    + "'"+tanggal_in+"','"+blok+"','"+jam_in+"',NULL,NULL)";
            stt = con.createStatement();
            stt.executeUpdate(sql);
            model.addRow(new Object[]{nopol,jenis_motor,tanggal_in,blok,jam_in});
        }catch(SQLException e){
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

        panel1 = new usu.widget.Panel();
        buttonGlass1 = new usu.widget.ButtonGlass();
        buttonGlass2 = new usu.widget.ButtonGlass();
        buttonGlass3 = new usu.widget.ButtonGlass();
        buttonGlass4 = new usu.widget.ButtonGlass();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtTgl = new javax.swing.JLabel();
        txtJam = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNopol = new javax.swing.JTextField();
        txtTglIn = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtJamIn = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtJamOut = new javax.swing.JTextField();
        BTNtambah = new javax.swing.JButton();
        BTNsimpan = new javax.swing.JButton();
        BTNulang = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        CMBjenis = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        txtNopol1 = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtNopol3 = new javax.swing.JTextField();
        txtTglIn2 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtJamIn2 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtJamOut2 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtTglIn3 = new javax.swing.JTextField();
        BTNout = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        BTNstruk = new javax.swing.JButton();
        BTNbersih = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        LBlama_parkir = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        LBtarif = new javax.swing.JLabel();
        CMBblok = new javax.swing.JComboBox<>();
        buttonGlass5 = new usu.widget.ButtonGlass();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtNoKarcis = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Aplikasi Parkir GRAND CITY MALL Version 1.0");
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

        panel1.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/image/mainapp.jpg"))); // NOI18N
        panel1.setPreferredSize(new java.awt.Dimension(1366, 730));

        buttonGlass1.setForeground(new java.awt.Color(255, 255, 255));
        buttonGlass1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/circular-clock (1).png"))); // NOI18N
        buttonGlass1.setText("Info Tarif");
        buttonGlass1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        buttonGlass1.setRoundRect(true);

        buttonGlass2.setForeground(new java.awt.Color(255, 255, 255));
        buttonGlass2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/struk.png"))); // NOI18N
        buttonGlass2.setText("Rekap Data");
        buttonGlass2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        buttonGlass2.setRoundRect(true);

        buttonGlass3.setForeground(new java.awt.Color(255, 255, 255));
        buttonGlass3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/bars-chart (1).png"))); // NOI18N
        buttonGlass3.setText("Data Laporan");
        buttonGlass3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        buttonGlass3.setRoundRect(true);

        buttonGlass4.setForeground(new java.awt.Color(255, 255, 255));
        buttonGlass4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/exit.png"))); // NOI18N
        buttonGlass4.setText("EXIT");
        buttonGlass4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        buttonGlass4.setRoundRect(true);
        buttonGlass4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGlass4ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Tanggal :");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Pukul :");

        txtTgl.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txtTgl.setForeground(new java.awt.Color(255, 255, 255));
        txtTgl.setText("LabelTanggal");

        txtJam.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txtJam.setForeground(new java.awt.Color(255, 255, 255));
        txtJam.setText("LabelJam");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("No Polisi");

        txtNopol.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        txtTglIn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Tanggal Masuk");

        txtJamIn.setEditable(false);
        txtJamIn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Jam In Parkir");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Jam Out Parkir");

        txtJamOut.setEditable(false);
        txtJamOut.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtJamOut.setText("NULL");

        BTNtambah.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        BTNtambah.setText("TAMBAH");
        BTNtambah.setOpaque(false);
        BTNtambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNtambahActionPerformed(evt);
            }
        });

        BTNsimpan.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        BTNsimpan.setText("SIMPAN");
        BTNsimpan.setOpaque(false);
        BTNsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNsimpanActionPerformed(evt);
            }
        });

        BTNulang.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        BTNulang.setText("ULANGI");
        BTNulang.setOpaque(false);
        BTNulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNulangActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No Karcis", "No Polisi", "Jenis Motor", "TGL Masuk", "Blok Parkir", "Jam Masuk", "Jam Keluar", "Tarif"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("Jenis Motor");

        CMBjenis.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        CMBjenis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Motor Matic", "Motor Bebek", "Motor Sport" }));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cari Kendaraan (Masukan Plat Nomor)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel1.setOpaque(false);

        txtNopol1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jButton7.setText("Cari");
        jButton7.setOpaque(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtNopol1, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtNopol1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setText("No Polisi");

        txtNopol3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        txtTglIn2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Tanggal Masuk");

        txtJamIn2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Jam In Parkir");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("Jam Out Parkir");

        txtJamOut2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("Jenis Motor");

        jComboBox3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Motor Matic", "Motor Bebek", "Motor Sport" }));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setText("Blok Parkir");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("Blok Parkir");

        txtTglIn3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        BTNout.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        BTNout.setText("OUT");
        BTNout.setOpaque(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Rincian Detail", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel2.setOpaque(false);

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("----------- Karcis Parkir Kendaraan -----------\n-----------------------------------------------\nNo. Polisi  :\nJenis Motor :\nTanggal     :\nBlok Parkir :\nJam Masuk   :\nJam Keluar  :\nTarif       :");
        jScrollPane2.setViewportView(jTextArea1);

        BTNstruk.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        BTNstruk.setText("CETAK STRUK");
        BTNstruk.setOpaque(false);

        BTNbersih.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        BTNbersih.setText("BERSIHKAN");
        BTNbersih.setOpaque(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(BTNbersih, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BTNstruk, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(BTNstruk, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BTNbersih, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("Lama Parkir :");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setText("Total Tarif :");

        LBlama_parkir.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        LBlama_parkir.setText("00:00:00");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel22.setText("Rp.");

        LBtarif.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        LBtarif.setText("00000");

        CMBblok.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        CMBblok.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A1", "A2" }));

        buttonGlass5.setForeground(new java.awt.Color(255, 255, 255));
        buttonGlass5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/circular-clock (1).png"))); // NOI18N
        buttonGlass5.setText("About");
        buttonGlass5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        buttonGlass5.setRoundRect(true);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 92, 226));
        jLabel9.setText("Sisa Blok A1");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 92, 226));
        jLabel10.setText("50");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 92, 226));
        jLabel11.setText("Sisa Blok A2");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 92, 226));
        jLabel12.setText("50");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("No Karcis");

        txtNoKarcis.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(buttonGlass1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonGlass2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonGlass3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonGlass5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonGlass4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(97, 97, 97)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(txtTgl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(27, 27, 27))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtJam, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 631, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addComponent(BTNtambah, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(14, 14, 14)
                                        .addComponent(BTNsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(BTNulang, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel1Layout.createSequentialGroup()
                                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(28, 28, 28)
                                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(CMBjenis, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtTglIn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(panel1Layout.createSequentialGroup()
                                                .addComponent(txtNopol, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel21)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtNoKarcis, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel1Layout.createSequentialGroup()
                                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtJamIn)
                                            .addComponent(txtJamOut)
                                            .addComponent(CMBblok, 0, 325, Short.MAX_VALUE))))
                                .addGap(18, 18, 18)
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel11)
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, 0)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel14))
                                        .addGap(29, 29, 29)
                                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtTglIn2, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtNopol3)
                                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(panel1Layout.createSequentialGroup()
                                            .addComponent(jLabel18)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtTglIn3, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panel1Layout.createSequentialGroup()
                                            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                                                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel15)
                                                        .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGap(29, 29, 29))
                                                .addGroup(panel1Layout.createSequentialGroup()
                                                    .addComponent(BTNout, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(75, 75, 75)))
                                            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(txtJamIn2, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtJamOut2, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(panel1Layout.createSequentialGroup()
                                                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(LBlama_parkir, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(panel1Layout.createSequentialGroup()
                                                            .addComponent(jLabel22)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(LBtarif, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))))))))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(44, 44, 44))
            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel1Layout.createSequentialGroup()
                    .addGap(562, 562, 562)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(734, Short.MAX_VALUE)))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonGlass1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonGlass2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonGlass3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonGlass4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonGlass5, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, 0)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtJam, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTgl))))
                .addGap(31, 31, 31)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNopol3, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTglIn2)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTglIn3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtJamIn2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtJamOut2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BTNout, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel20))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(LBlama_parkir, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LBtarif, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNopol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9)
                                .addComponent(txtNoKarcis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(CMBjenis))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTglIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CMBblok)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtJamIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtJamOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BTNtambah, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BTNsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BTNulang, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel1Layout.createSequentialGroup()
                    .addGap(140, 140, 140)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(530, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonGlass4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGlass4ActionPerformed
        // TODO add your handling code here:
        int pilih = JOptionPane.showConfirmDialog(this, "Ingin Keluar Dari Aplikasi ?","Confirm Keluar",JOptionPane.YES_OPTION);
        if(pilih==JOptionPane.YES_OPTION){
        System.exit(0);
        }
    }//GEN-LAST:event_buttonGlass4ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        BTNsimpan.setEnabled(false);
        BTNulang.setEnabled(false);
        txtJamIn.setEnabled(false);
        txtJamOut.setEnabled(false);
        txtNopol.setEnabled(false);
        txtTglIn.setEnabled(false);
    }//GEN-LAST:event_formWindowOpened

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        InitTable();
        TampilKarcis();
    }//GEN-LAST:event_formComponentShown

    private void BTNtambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNtambahActionPerformed
        // TODO add your handling code here:
        BTNsimpan.setEnabled(true);
        BTNulang.setEnabled(true);
        txtJamIn.setEnabled(true);
        txtJamOut.setEnabled(true);
        txtNopol.setEnabled(true);
        txtTglIn.setEnabled(true);
    }//GEN-LAST:event_BTNtambahActionPerformed

    private void BTNulangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNulangActionPerformed
        // TODO add your handling code here:
        txtNopol.setText("");
    }//GEN-LAST:event_BTNulangActionPerformed

    private void BTNsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNsimpanActionPerformed
        // TODO add your handling code here:
        int pilih = JOptionPane.showConfirmDialog(this, "Yakin ingin simpan data ?","Confirm Simpan",JOptionPane.YES_OPTION);
        if(pilih==JOptionPane.YES_OPTION){

        String nopol = txtNopol.getText();
        String jenis_motor = CMBjenis.getSelectedItem().toString();
            if (jenis_motor.equals("Motor Matic")) {
                jenis_motor = "1";
            }else if (jenis_motor.equals("Motor Bebek")) {
                jenis_motor = "2";
            }if (jenis_motor.equals("Motor Sport")) {
                jenis_motor = "3";
            }
        String tanggal_in = txtTglIn.getText();
        String blok = CMBblok.getSelectedItem().toString();
        String jam_in = txtJamIn.getText();
        TambahData(nopol,jenis_motor,tanggal_in,blok,jam_in);
        bersih();
        }
    }                                  
     public void bersih(){
        txtNopol.setText("");
        CMBjenis.setSelectedItem("Motor Bebek");
    }//GEN-LAST:event_BTNsimpanActionPerformed

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
            java.util.logging.Logger.getLogger(AppMotor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AppMotor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AppMotor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AppMotor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AppMotor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTNbersih;
    private javax.swing.JButton BTNout;
    private javax.swing.JButton BTNsimpan;
    private javax.swing.JButton BTNstruk;
    private javax.swing.JButton BTNtambah;
    private javax.swing.JButton BTNulang;
    private javax.swing.JComboBox<String> CMBblok;
    private javax.swing.JComboBox<String> CMBjenis;
    private javax.swing.JLabel LBlama_parkir;
    private javax.swing.JLabel LBtarif;
    private usu.widget.ButtonGlass buttonGlass1;
    private usu.widget.ButtonGlass buttonGlass2;
    private usu.widget.ButtonGlass buttonGlass3;
    private usu.widget.ButtonGlass buttonGlass4;
    private usu.widget.ButtonGlass buttonGlass5;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private usu.widget.Panel panel1;
    private javax.swing.JLabel txtJam;
    private javax.swing.JTextField txtJamIn;
    private javax.swing.JTextField txtJamIn2;
    private javax.swing.JTextField txtJamOut;
    private javax.swing.JTextField txtJamOut2;
    private javax.swing.JTextField txtNoKarcis;
    private javax.swing.JTextField txtNopol;
    private javax.swing.JTextField txtNopol1;
    private javax.swing.JTextField txtNopol3;
    private javax.swing.JLabel txtTgl;
    private javax.swing.JTextField txtTglIn;
    private javax.swing.JTextField txtTglIn2;
    private javax.swing.JTextField txtTglIn3;
    // End of variables declaration//GEN-END:variables
}
