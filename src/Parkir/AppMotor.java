/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parkir;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Miechel
 */
public final class AppMotor extends javax.swing.JFrame {
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
//               txtJamOut2.setText(waktu+":"+menit+":"+detik+"");
            }
        };
        new Timer(1000,taskPerformer).start();
    }
    ImageIcon imageicon;
    public AppMotor() {
        initComponents();
        imageicon = new ImageIcon("src/icon/Icon2.png");
        setIconImage(imageicon.getImage());
        txtTgl.setText(tanggal);
        txtTglIn.setText(tanggal2);
        setJam();
        no_karcis();
        jumlah_a1();
        combo_jenis();
        combo_blok();
    }
    private DefaultTableModel model;
    private final Connection con = koneksi.getConnection();
    private Statement stt;
    private ResultSet rss;
    private PreparedStatement pst;
    
    private void InitTable(){
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
    
    private void TampilKarcis(){
        try{
            String sql = "SELECT * FROM karcis_motor order by id_karcis desc";
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
    
    public void combo_jenis(){
        String Motor = "Motor";
        try{
        String sql = "SELECT jenis_kendaraan from tipe_kendaraan where jenis_kendaraan like '%"+Motor+"%'";
        stt = con.createStatement();
        rss = stt.executeQuery(sql);
        while(rss.next()){
            Object[] ob = new Object[2];
            ob[0] = rss.getString(1);
            CMBjenis.addItem((String) ob[0]);
        }
        rss.close();
        stt.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public void combo_blok(){
        String blok_A = "A";
        try{
        String sql = "SELECT id_blok from blok where id_blok like '%"+blok_A+"%'";
        stt = con.createStatement();
        rss = stt.executeQuery(sql);
        while(rss.next()){
            Object[] ob = new Object[2];
            ob[0] = rss.getString(1);
            CMBblok.addItem((String) ob[0]);
        }
        rss.close();
        stt.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public void no_karcis(){
        try{
        String sql = "SELECT MAX(id_karcis) as maxid from karcis_motor";
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
    
    public void Cetak_Parkir(){
            String id_karcis = txtNoKarcis2.getText();
            try{
                String sql = "SELECT ceiling((jam_keluar_mtr-jam_masuk_mtr)/9000) as lama from karcis_motor where id_karcis='"+id_karcis+"'";
                stt = con.createStatement();
                rss = stt.executeQuery(sql);
                rss.next();
                int lama = rss.getInt("lama");
                String lama_parkir = Integer.toString(lama);
                LBlama_parkir.setText(lama_parkir+" jam");
                int tarif_dasar = 2000;
                int tarif_max = 8000;
                switch (LBlama_parkir.getText()) {
                    case "0 jam":
                        LBtarif.setText("Belum Out");
                        break;
                    case "1 jam": 
                        LBtarif.setText("2000");
                        break;
                    case "2 jam":
                        {
                            int baru = tarif_dasar*lama;
                            String baruu = Integer.toString(baru);
                            LBtarif.setText(baruu);
                            break;
                        }
                    case "3 jam":
                        {
                            int baru = tarif_dasar*lama;
                            String baruu = Integer.toString(baru);
                            LBtarif.setText(baruu);
                            break;
                        }
                    case "4 jam":
                        {
                            int baru = tarif_dasar*lama;
                            String baruu = Integer.toString(baru);
                            LBtarif.setText(baruu);
                            break;
                        }
                    default:
                        {
                            int baru = tarif_max;
                            String baruu = Integer.toString(baru);
                            LBtarif.setText(baruu);
                            break;
                        }
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }          
        }
    
    public void detail_karcis(){
        String no,nopol,jenis,tgl,blok,jamin,jamout,lama,tarif;
        no = txtNoKarcis2.getText();
        nopol = txtNopol3.getText();
        tgl = txtTglIn2.getText();
        blok = txtBlok2.getText();
        jamin = txtJamIn2.getText();
        jamout = txtJamOut2.getText();
        lama = LBlama_parkir.getText();
        tarif = LBtarif.getText();
        TAkarcis.setText("---------- GRAND CITY MALL SAMARINDA -----------\n"+
                         "----------- Karcis Parkir Kendaraan ------------\n"+
                         "------------------------------------------------"+
                            "\nNo. Karcis\t: "+no+
                            "\nNo. Polisi\t: "+nopol+
                            "\nTanggal\t\t: "+tgl+
                            "\nBlok Parkir\t: "+blok+
                            "\nJam Masuk\t: "+jamin+
                            "\nJam Keluar\t: "+jamout+
                            "\nLama Parkir\t: "+lama+
                            "\nTarif\t\t: Rp "+tarif+
                         "\n------------------------------------------------"+
                         "\n-------- Terima Kasih Telah Berkunjung ---------");
    }
    
    public void TambahData(String id_karcis,String nopol, String jenis_motor,String tanggal_in,String blok,
            String jam_in){
        try{
            String sql = "INSERT INTO karcis_motor VALUES ('"+id_karcis+"','"+nopol+"','"+jenis_motor+"',"
                    + "'"+tanggal_in+"','"+blok+"','"+jam_in+"',NULL,NULL)";
            stt = con.createStatement();
            stt.executeUpdate(sql);
            model.addRow(new Object[]{id_karcis,nopol,jenis_motor,tanggal_in,blok,jam_in});
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public boolean KeluarParkir(String jam_out){
        String id_karcis = txtNoKarcis2.getText();
        String blok = "OUT";
        try{
            String sql = "UPDATE karcis_motor SET blok_parkir='"+blok+"',jam_keluar_mtr='"+jam_out+"' WHERE id_karcis="+id_karcis+";";
//            String sql2 = "SELECT timediff(jam_keluar_mtr,jam_masuk_mtr) as lama_parkir from karcis_motor where id_karcis='"+id_karcis+"'";
            stt = con.createStatement();
            stt.executeUpdate(sql);
//            rss = stt.executeQuery(sql2);
//            rss.next();
//            String lama_parkir = rss.getString("lama_parkir");
//            LBlama_parkir.setText(lama_parkir);
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public boolean Bayar(){
        String id_karcis = txtNoKarcis2.getText();
        String bayar = LBtarif.getText();
        try{
            String sql = "UPDATE karcis_motor SET tarif='"+bayar+"' WHERE id_karcis="+id_karcis+";";
            stt = con.createStatement();
            stt.executeUpdate(sql);
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public boolean jumlah_a1(){
        try{
            String sql = "SELECT count(blok_parkir) as jumlah from karcis_motor WHERE blok_parkir='A1'";
           
            stt = con.createStatement();
            rss = stt.executeQuery(sql);
            rss.next();
            int jumlahh = rss.getInt("jumlah");
            String jumlah_a1= Integer.toString(jumlahh);
            jumlah1.setText(jumlah_a1);
            int batas = 100;
            int sisa = batas-jumlahh;
            String sisa_a1= Integer.toString(sisa);
            LBsisa_a1.setText(sisa_a1);
            
            String sql2 = "SELECT count(blok_parkir) as jumlah2 from karcis_motor WHERE blok_parkir='A2'";
            stt = con.createStatement();
            rss = stt.executeQuery(sql2);
            rss.next();
            int jumlahhh = rss.getInt("jumlah2");
            String jumlah_a2= Integer.toString(jumlahhh);
            jumlah2.setText(jumlah_a2);
            int batass = 100;
            int sisaa = batass-jumlahhh;
            String sisa_a2= Integer.toString(sisaa);
            LBsisa_a2.setText(sisa_a2);
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
     }
    
     public void bersih(){
        txtNopol.setText("");
        CMBjenis.setSelectedItem("-- Pilih Jenis Motor --");
        CMBblok.setSelectedItem("-- Pilih Blok Parkir --");
    }                                         
    
    public void clear(){
        TAkarcis.setText("");
        txtNopol2.setText("");
        txtNopol3.setText("");
        txtNoKarcis2.setText("");
        txtBlok2.setText("");
        txtTglIn2.setText("");
        txtJamIn2.setText("");
        txtJamOut2.setText("");
        LBlama_parkir.setText("00 Jam");
        LBtarif.setText("00000");
        BTNout1.setEnabled(false);
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
        BTNtarif = new usu.widget.ButtonGlass();
        BTNmenu = new usu.widget.ButtonGlass();
        BTNlaporan = new usu.widget.ButtonGlass();
        BTNlogout = new usu.widget.ButtonGlass();
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
        txtNopol2 = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtNopol3 = new javax.swing.JTextField();
        txtTglIn2 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtJamIn2 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtJamOut2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtBlok2 = new javax.swing.JTextField();
        BTNupdate = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TAkarcis = new javax.swing.JTextArea();
        BTNstruk = new javax.swing.JButton();
        BTNbersih = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        LBlama_parkir = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        LBtarif = new javax.swing.JLabel();
        CMBblok = new javax.swing.JComboBox<>();
        BTNabout = new usu.widget.ButtonGlass();
        jLabel9 = new javax.swing.JLabel();
        LBsisa_a2 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        LBsisa_a1 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtNoKarcis = new javax.swing.JTextField();
        BTNout1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        txtNoKarcis2 = new javax.swing.JTextField();
        jumlah2 = new javax.swing.JLabel();
        jumlah1 = new javax.swing.JLabel();

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

        BTNtarif.setForeground(new java.awt.Color(255, 255, 255));
        BTNtarif.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/circular-clock (1).png"))); // NOI18N
        BTNtarif.setText("Info Tarif");
        BTNtarif.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        BTNtarif.setRoundRect(true);
        BTNtarif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNtarifActionPerformed(evt);
            }
        });

        BTNmenu.setForeground(new java.awt.Color(255, 255, 255));
        BTNmenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/struk.png"))); // NOI18N
        BTNmenu.setText("Menu Master");
        BTNmenu.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        BTNmenu.setRoundRect(true);
        BTNmenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNmenuActionPerformed(evt);
            }
        });

        BTNlaporan.setForeground(new java.awt.Color(255, 255, 255));
        BTNlaporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/bars-chart (1).png"))); // NOI18N
        BTNlaporan.setText("Data Laporan");
        BTNlaporan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        BTNlaporan.setRoundRect(true);
        BTNlaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNlaporanActionPerformed(evt);
            }
        });

        BTNlogout.setForeground(new java.awt.Color(255, 255, 255));
        BTNlogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/exit.png"))); // NOI18N
        BTNlogout.setText("Logout");
        BTNlogout.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        BTNlogout.setRoundRect(true);
        BTNlogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNlogoutActionPerformed(evt);
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
        txtNopol.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtNopolCaretUpdate(evt);
            }
        });

        txtTglIn.setEditable(false);
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
        BTNtambah.setIconTextGap(2);
        BTNtambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNtambahActionPerformed(evt);
            }
        });

        BTNsimpan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
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
                "No", "No Polisi", "Jenis Motor", "TGL Masuk", "Blok Parkir", "Jam Masuk", "Jam Keluar", "Tarif"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.setEnabled(false);
        jScrollPane1.setViewportView(jTable1);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("Jenis Motor");

        CMBjenis.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        CMBjenis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Jenis Motor --" }));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cari Kendaraan (Masukan Plat Nomor)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel1.setOpaque(false);

        txtNopol2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtNopol2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNopol2KeyPressed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Zoom.png"))); // NOI18N
        jButton7.setText("Cari");
        jButton7.setOpaque(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtNopol2, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNopol2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setText("No Polisi");

        txtNopol3.setEditable(false);
        txtNopol3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtNopol3.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtNopol3CaretUpdate(evt);
            }
        });

        txtTglIn2.setEditable(false);
        txtTglIn2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Tanggal Masuk");

        txtJamIn2.setEditable(false);
        txtJamIn2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Jam In Parkir");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("Jam Out Parkir");

        txtJamOut2.setEditable(false);
        txtJamOut2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setText("Blok Parkir");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("Blok Parkir");

        txtBlok2.setEditable(false);
        txtBlok2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        BTNupdate.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        BTNupdate.setText("UPDATE");
        BTNupdate.setOpaque(false);
        BTNupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNupdateActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Rincian Detail", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel2.setOpaque(false);

        TAkarcis.setEditable(false);
        TAkarcis.setColumns(20);
        TAkarcis.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        TAkarcis.setLineWrap(true);
        TAkarcis.setRows(5);
        TAkarcis.setText("----------- Karcis Parkir Kendaraan -----------\n-----------------------------------------------\nNo. Polisi  :\nJenis Motor :\nTanggal     :\nBlok Parkir :\nJam Masuk   :\nJam Keluar  :\nTarif       :");
        jScrollPane2.setViewportView(TAkarcis);

        BTNstruk.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        BTNstruk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Print.png"))); // NOI18N
        BTNstruk.setText("CETAK STRUK");
        BTNstruk.setOpaque(false);
        BTNstruk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNstrukActionPerformed(evt);
            }
        });

        BTNbersih.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        BTNbersih.setText("BERSIHKAN");
        BTNbersih.setOpaque(false);
        BTNbersih.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BTNbersihMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BTNstruk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BTNbersih, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(BTNstruk, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BTNbersih, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 94, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("Lama Parkir :");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setText("Total Tarif :");

        LBlama_parkir.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        LBlama_parkir.setText("00 jam");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel22.setText("Rp.");

        LBtarif.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        LBtarif.setText("00000");

        CMBblok.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        CMBblok.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Blok Parkir --" }));

        BTNabout.setForeground(new java.awt.Color(255, 255, 255));
        BTNabout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/circular-clock (1).png"))); // NOI18N
        BTNabout.setText("About");
        BTNabout.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        BTNabout.setRoundRect(true);
        BTNabout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNaboutActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 92, 226));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Sisa Blok A1");

        LBsisa_a2.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        LBsisa_a2.setForeground(new java.awt.Color(0, 92, 226));
        LBsisa_a2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBsisa_a2.setText("100");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 92, 226));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Sisa Blok A2");
        jLabel11.setToolTipText("");

        LBsisa_a1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        LBsisa_a1.setForeground(new java.awt.Color(0, 92, 226));
        LBsisa_a1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LBsisa_a1.setText("100");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("No Karcis");

        txtNoKarcis.setEditable(false);
        txtNoKarcis.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        BTNout1.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        BTNout1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Cancel.png"))); // NOI18N
        BTNout1.setText("OUT");
        BTNout1.setOpaque(false);
        BTNout1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNout1ActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "No Karcis", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel3.setOpaque(false);

        txtNoKarcis2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtNoKarcis2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNoKarcis2KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtNoKarcis2, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtNoKarcis2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jumlah2.setText("jumlah a2");

        jumlah1.setText("jumlah a1");

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(BTNtarif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BTNmenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BTNlaporan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BTNabout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BTNlogout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTgl, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtJam, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 631, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
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
                                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(panel1Layout.createSequentialGroup()
                                                .addComponent(jumlah1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jumlah2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtJamIn, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtJamOut, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(CMBblok, javax.swing.GroupLayout.Alignment.LEADING, 0, 325, Short.MAX_VALUE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel1Layout.createSequentialGroup()
                                                .addComponent(BTNtambah, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(BTNsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(BTNulang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(LBsisa_a2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(68, 68, 68)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel13)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14)
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel15)
                                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtJamIn2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtJamOut2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtTglIn2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtNopol3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtBlok2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(panel1Layout.createSequentialGroup()
                                    .addComponent(BTNupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(BTNout1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(33, 33, 33)
                                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(LBlama_parkir))
                                    .addGap(18, 18, 18)
                                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(panel1Layout.createSequentialGroup()
                                            .addComponent(jLabel22)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(LBtarif, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(73, 73, 73))))))
                .addGap(44, 44, 44))
            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel1Layout.createSequentialGroup()
                    .addGap(552, 552, 552)
                    .addComponent(LBsisa_a1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(740, Short.MAX_VALUE)))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BTNtarif, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BTNmenu, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BTNlaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BTNlogout, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BTNabout, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNopol3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTglIn2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBlok2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtJamIn2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtJamOut2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(BTNupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(BTNout1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LBlama_parkir, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LBtarif, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNopol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9)
                                .addComponent(txtNoKarcis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                            .addComponent(LBsisa_a2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BTNtambah, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BTNsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BTNulang, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jumlah2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jumlah1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel1Layout.createSequentialGroup()
                    .addGap(140, 140, 140)
                    .addComponent(LBsisa_a1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(524, Short.MAX_VALUE)))
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

    private void BTNlogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNlogoutActionPerformed
        // TODO add your handling code here:
        int pilih = JOptionPane.showConfirmDialog(this, "Ingin Keluar Dari Aplikasi ?","Confirm Keluar",JOptionPane.YES_OPTION);
        if(pilih==JOptionPane.YES_OPTION){
        this.dispose();
        new LoginForm().setVisible(true);
        }
    }//GEN-LAST:event_BTNlogoutActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        BTNsimpan.setEnabled(false);
        BTNulang.setEnabled(false);
        BTNupdate.setEnabled(false);
        BTNout1.setEnabled(false);
        txtJamIn.setEnabled(false);
        txtJamOut.setEnabled(false);
        txtNopol.setEnabled(false);
        txtTglIn.setEnabled(false);
        CMBjenis.setEnabled(false);
        CMBblok.setEnabled(false);
        jumlah1.setVisible(false);
        jumlah2.setVisible(false);
    }//GEN-LAST:event_formWindowOpened

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        InitTable();
        TampilKarcis();
    }//GEN-LAST:event_formComponentShown

    private void BTNtambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNtambahActionPerformed
        // TODO add your handling code here:
        BTNsimpan.setEnabled(false);
        BTNulang.setEnabled(true);
        txtJamIn.setEnabled(true);
        txtJamOut.setEnabled(true);
        txtNopol.setEnabled(true);
        txtTglIn.setEnabled(true);
        CMBjenis.setEnabled(true);
        CMBblok.setEnabled(true);
    }//GEN-LAST:event_BTNtambahActionPerformed

    private void BTNulangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNulangActionPerformed
        // TODO add your handling code here:
        txtNopol.setText("");
    }//GEN-LAST:event_BTNulangActionPerformed

    private void BTNsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNsimpanActionPerformed
        // TODO add your handling code here:
        int pilih = JOptionPane.showConfirmDialog(this, "Yakin ingin simpan data ?","Confirm Simpan",JOptionPane.YES_OPTION);
        if(pilih==JOptionPane.YES_OPTION){        
            String id_karcis = txtNoKarcis.getText();
            String nopol = txtNopol.getText();
            String jenis_motor = CMBjenis.getSelectedItem().toString();
            if (jenis_motor.equals("-- Pilih Jenis Motor --")) {
                JOptionPane.showMessageDialog(null, "Pilih Jenis Motor Dahulu ! ","Maaf",JOptionPane.INFORMATION_MESSAGE);
            }
            String tanggal_in = txtTglIn.getText();
            String blok = CMBblok.getSelectedItem().toString();
        if (blok.equals("-- Pilih Blok Parkir --")) {
                JOptionPane.showMessageDialog(null, "Pilih Blok Parkir Dahulu ! ","Maaf",JOptionPane.INFORMATION_MESSAGE);
            }
            String jam_in = txtJamIn.getText();
            TambahData(id_karcis,nopol,jenis_motor,tanggal_in,blok,jam_in);
            bersih();
        }
        InitTable();
        TampilKarcis();
        jumlah_a1();
        no_karcis();
    }//GEN-LAST:event_BTNsimpanActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        String nopol_motor = txtNopol2.getText();
        try
        {
        String sql = "SELECT * FROM karcis_motor WHERE nopol_motor like '"+nopol_motor+"' AND tarif is NULL";
        stt = con.createStatement();
        rss = stt.executeQuery(sql);
        if (rss.next())
        {
            txtNoKarcis2.setText(rss.getString(1));
            txtNopol3.setText(rss.getString(2));
            txtTglIn2.setText(rss.getString(4));
            txtBlok2.setText(rss.getString(5));
            txtJamIn2.setText(rss.getString(6));
            txtJamOut2.setText(txtJam.getText());
            JOptionPane.showMessageDialog(null, "Data Parkir Ditemukan","Cari Data",JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
        JOptionPane.showMessageDialog(null, "Data tidak ditemukan atau sudah keluar","Cari Data",JOptionPane.INFORMATION_MESSAGE);
        }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        } 
    }//GEN-LAST:event_jButton7ActionPerformed

    private void BTNupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNupdateActionPerformed
        // TODO add your handling code here:
        int pilih = JOptionPane.showConfirmDialog(this, "Yakin ingin ubah data ?","Confirm Simpan",JOptionPane.YES_OPTION);
        if(pilih==JOptionPane.YES_OPTION){
            String jam_out = txtJamOut2.getText();
            KeluarParkir(jam_out);
            JOptionPane.showMessageDialog(null,"Berhasil Update Jam Keluar, Klik OUT");
            jumlah_a1();
            Cetak_Parkir();
            Bayar();
            InitTable();
            TampilKarcis();
            BTNout1.setEnabled(true);
        }
    }//GEN-LAST:event_BTNupdateActionPerformed

    private void BTNout1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNout1ActionPerformed
        // TODO add your handling code here:
        detail_karcis();
    }//GEN-LAST:event_BTNout1ActionPerformed

    private void BTNstrukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNstrukActionPerformed
        // TODO add your handling code here:
        try {
          new AppMotor().printComponents(null);
            TAkarcis.print();
            clear();
        } catch(Exception e) {
            dispose();
        }
    }//GEN-LAST:event_BTNstrukActionPerformed

    private void BTNbersihMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BTNbersihMousePressed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_BTNbersihMousePressed

    private void BTNaboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNaboutActionPerformed
        // TODO add your handling code here:
        new about().setVisible(true);
    }//GEN-LAST:event_BTNaboutActionPerformed

    private void txtNopol2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNopol2KeyPressed
        // TODO add your handling code here:
        txtNopol2.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e){
                char karakter = e.getKeyChar();
                
                if(karakter == '\n'){
                    e.consume();
                    txtNopol2.removeKeyListener(this);
                    String nopol_motor = txtNopol2.getText();
        try
        {
        String sql = "SELECT * FROM karcis_motor WHERE nopol_motor like '"+nopol_motor+"' AND tarif is NULL";
        
        stt = con.createStatement();
        rss = stt.executeQuery(sql);
        if (rss.next())
        {
        txtNoKarcis2.setText(rss.getString(1));
        txtNopol3.setText(rss.getString(2));
        txtTglIn2.setText(rss.getString(4));
        txtBlok2.setText(rss.getString(5));
        txtJamIn2.setText(rss.getString(6));
        txtJamOut2.setText(txtJam.getText());
        JOptionPane.showMessageDialog(null, "Data Parkir Ditemukan ","Cari Data",JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
        JOptionPane.showMessageDialog(null, "Data tidak ditemukan atau sudah keluar","Cari Data",JOptionPane.INFORMATION_MESSAGE);
        }
        }
        catch(SQLException ef){
            System.out.println(ef.getMessage());
        } 
                }
                txtNopol2.removeKeyListener(this);
            }
        });
    }//GEN-LAST:event_txtNopol2KeyPressed

    private void txtNopolCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtNopolCaretUpdate
        // TODO add your handling code here:
        if (txtNopol.getText().length()== 0){
            BTNsimpan.setEnabled(false);
        }
        else{
            BTNsimpan.setEnabled(true);
        }
    }//GEN-LAST:event_txtNopolCaretUpdate

    private void BTNmenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNmenuActionPerformed
        // TODO add your handling code here:
        new MenuMaster().setVisible(true);
    }//GEN-LAST:event_BTNmenuActionPerformed

    private void BTNtarifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNtarifActionPerformed
        // TODO add your handling code here:
        new Infotarif().setVisible(true);
    }//GEN-LAST:event_BTNtarifActionPerformed

    private void BTNlaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNlaporanActionPerformed
        // TODO add your handling code here:
        new Laporan().setVisible(true);
    }//GEN-LAST:event_BTNlaporanActionPerformed

    private void txtNoKarcis2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoKarcis2KeyPressed
        // TODO add your handling code here:
        txtNoKarcis2.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e){
                char karakter = e.getKeyChar();
                
                if(karakter == '\n'){
                    e.consume();
                    txtNoKarcis2.removeKeyListener(this);
                    String no_karcis = txtNoKarcis2.getText();
        try
        {
        String sql = "SELECT * FROM karcis_motor WHERE id_karcis like '"+no_karcis+"' AND tarif is NULL";
        
        stt = con.createStatement();
        rss = stt.executeQuery(sql);
        if (rss.next())
        {
        txtNopol3.setText(rss.getString(2));
        txtTglIn2.setText(rss.getString(4));
        txtBlok2.setText(rss.getString(5));
        txtJamIn2.setText(rss.getString(6));
        txtJamOut2.setText(txtJam.getText());
        JOptionPane.showMessageDialog(null, "Data Parkir Ditemukan","Insert Data",JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
        JOptionPane.showMessageDialog(null, "Data tidak ditemukan atau sudah keluar ","Insert Data",JOptionPane.INFORMATION_MESSAGE);
        }
        }
        catch(SQLException ef){
            System.out.println(ef.getMessage());
        } 
                }
                txtNoKarcis2.removeKeyListener(this);
            }
        });
    }//GEN-LAST:event_txtNoKarcis2KeyPressed

    private void txtNopol3CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtNopol3CaretUpdate
        // TODO add your handling code here:
        if (txtNopol3.getText().length()== 0){
            BTNupdate.setEnabled(false);
        }
        else{
            BTNupdate.setEnabled(true);
        }
    }//GEN-LAST:event_txtNopol3CaretUpdate

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
    private usu.widget.ButtonGlass BTNabout;
    private javax.swing.JButton BTNbersih;
    private usu.widget.ButtonGlass BTNlaporan;
    private usu.widget.ButtonGlass BTNlogout;
    private usu.widget.ButtonGlass BTNmenu;
    private javax.swing.JButton BTNout1;
    private javax.swing.JButton BTNsimpan;
    private javax.swing.JButton BTNstruk;
    private javax.swing.JButton BTNtambah;
    private usu.widget.ButtonGlass BTNtarif;
    private javax.swing.JButton BTNulang;
    private javax.swing.JButton BTNupdate;
    private javax.swing.JComboBox<String> CMBblok;
    private javax.swing.JComboBox<String> CMBjenis;
    private javax.swing.JLabel LBlama_parkir;
    private javax.swing.JLabel LBsisa_a1;
    private javax.swing.JLabel LBsisa_a2;
    private javax.swing.JLabel LBtarif;
    private javax.swing.JTextArea TAkarcis;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel jumlah1;
    private javax.swing.JLabel jumlah2;
    private usu.widget.Panel panel1;
    private javax.swing.JTextField txtBlok2;
    private javax.swing.JLabel txtJam;
    private javax.swing.JTextField txtJamIn;
    private javax.swing.JTextField txtJamIn2;
    private javax.swing.JTextField txtJamOut;
    private javax.swing.JTextField txtJamOut2;
    private javax.swing.JTextField txtNoKarcis;
    private javax.swing.JTextField txtNoKarcis2;
    private javax.swing.JTextField txtNopol;
    private javax.swing.JTextField txtNopol2;
    private javax.swing.JTextField txtNopol3;
    private javax.swing.JLabel txtTgl;
    private javax.swing.JTextField txtTglIn;
    private javax.swing.JTextField txtTglIn2;
    // End of variables declaration//GEN-END:variables
}
