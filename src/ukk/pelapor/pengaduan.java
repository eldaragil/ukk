/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ukk.pelapor;

import Koneksi.Koneksi;
import ukk.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.swing.JRViewer;
import ukk.session;


/**
 *
 * @author dppra
 */
public class pengaduan extends javax.swing.JFrame {

    /**
     * Creates new form 
     */
    
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    String pathFoto = null;

   private void setTanggalHariIni() {
    jd_tglpengaduan.setDate(new Date());
    jd_tglpengaduan.getDateEditor().setEnabled(false);
    jd_tglpengaduan.getCalendarButton().setEnabled(false);
}
    private DefaultTableModel model;
    
    public pengaduan() {
        initComponents();
        setLocationRelativeTo(null);// membuat tengah form
        setTanggalHariIni();
        conn = Koneksi.KoneksiDB();
        this.setLocationRelativeTo(null);
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        txt_nik.setText(session.getNik());
        txt_nik.setEditable(false);
         

       // getData();//javaConnect = nama file || Connection = nama method
        
        
        //memberi penamaan pada judul kolom ; 
        model = new DefaultTableModel();
        jTable1.setModel(model);
        model.addColumn("id pengaduan");
        model.addColumn("nik");
        model.addColumn("nama");
        model.addColumn("Tanggal Pengaduan");
        model.addColumn("Isi Laporan");
        model.addColumn("Foto");
        model.addColumn("Kategori");
        model.addColumn("lokasi");
        model.addColumn("Status");
        
        
        tampilIdPengaduan();
        getData();//memanggil method getdata
       }
    //menampilkan data kejTabel1
    void getData() {

    try {
        conn = Koneksi.KoneksiDB();
        model.setRowCount(0);

        String sql = "SELECT * FROM pengaduan WHERE nik=? AND status=? ORDER BY id_pengaduan DESC";
        pst = conn.prepareStatement(sql);
        pst.setString(1, session.getNik());
        pst.setString(2, "menunggu");

        rs = pst.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("id_pengaduan"),
                rs.getString("nik"),
                rs.getString("nama"),
                rs.getString("tgl_pengaduan"),
                rs.getString("isi_laporan"),
                rs.getString("foto"),
                rs.getString("kategori"),
                rs.getString("lokasi"),
                rs.getString("status")
            });
        }

        jTable1.setModel(model);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
}
    
    void getDataByKategori() {

    try {
        conn = Koneksi.KoneksiDB();
        model.setRowCount(0);

        String kategori = cmb_kategori.getSelectedItem().toString();

        String sql = "SELECT * FROM pengaduan WHERE nik=? AND status=? AND kategori=? ORDER BY id_pengaduan DESC";
        pst = conn.prepareStatement(sql);
        pst.setString(1, session.getNik());
        pst.setString(2, "menunggu");
        pst.setString(3, kategori);

        rs = pst.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("id_pengaduan"),
                rs.getString("nik"),
                rs.getString("nama"),
                rs.getString("tgl_pengaduan"),
                rs.getString("isi_laporan"),
                rs.getString("foto"),
                rs.getString("kategori"),
                rs.getString("lokasi"),
                rs.getString("status")
            });
        }

        jTable1.setModel(model);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
}

 //menampilkan data dr tabel ke masing-masing komponen cara 2
   void pilihData() {
       int i = jTable1.getSelectedRow();
       txt_pengaduan.setText(model.getValueAt(i, 0).toString());
       txt_nik.setText(model.getValueAt(i, 1).toString());
       txt_nama.setText(model.getValueAt(i, 2).toString());
       lokasi.setText(model.getValueAt(i, 3).toString());
       
       txt_isi.setText(model.getValueAt(i, 4).toString());
     lbl_foto.setText(model.getValueAt(i, 5).toString());
       
   } 
   
   void bersih() {
     //  txt_pengaduan.setText("");
        txt_nama.setText("");
    txt_isi.setText("");
    lokasi.setText("");

    lbl_foto.setIcon(null);
    pathFoto = null;

    // Reset tanggal input ke hari ini (kalau ada methodnya)
    setTanggalHariIni();
   }
   
   void refreshSemua() {

    // Reset filter tanggal
    jDateChooser1.setDate(null);
    jDateChooser2.setDate(null);

    // Reset combobox
    cmb_kategori.setSelectedIndex(0);

    // Reset pencarian
    txt_cari.setText("");

    // Hapus selection tabel
    jTable1.clearSelection();

    // Load ulang data awal
    getData();
}
   
   void tampilIdPengaduan() {
    try {
        String sql = "SELECT IFNULL(MAX(id_pengaduan), 0) + 1 AS id FROM pengaduan";
        pst = conn.prepareStatement(sql);
        rs = pst.executeQuery();

        if (rs.next()) {
            txt_pengaduan.setText(rs.getString("id"));
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
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

        jPanel1 = new javax.swing.JPanel();
        jd_tglpengaduan = new com.toedter.calendar.JDateChooser();
        txt_nik = new javax.swing.JTextField();
        txt_nama = new javax.swing.JTextField();
        txt_isi = new javax.swing.JTextArea();
        jButton8 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        filtter = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        lbl_foto = new javax.swing.JLabel();
        btn_upload = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        kategori = new javax.swing.JComboBox<>();
        txt_pengaduan = new javax.swing.JTextField();
        lokasi = new javax.swing.JTextField();
        cmb_kategori = new javax.swing.JComboBox<>();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        txt_cari = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PENGADUAN");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jd_tglpengaduan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel1.add(jd_tglpengaduan, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 320, 30));

        txt_nik.setBackground(new java.awt.Color(0,0,0,0));
        txt_nik.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_nik.setBorder(null);
        jPanel1.add(txt_nik, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, 320, 30));

        txt_nama.setEditable(false);
        txt_nama.setBackground(new java.awt.Color(0,0,0,0));
        txt_nama.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_nama.setBorder(null);
        txt_nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaActionPerformed(evt);
            }
        });
        jPanel1.add(txt_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, 320, 30));

        txt_isi.setBackground(new java.awt.Color(0,0,0,0));
        txt_isi.setColumns(20);
        txt_isi.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txt_isi.setRows(5);
        txt_isi.setBorder(null);
        jPanel1.add(txt_isi, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 560, 320, 70));

        jButton8.setBackground(new java.awt.Color(0,0,0,0));
        jButton8.setBorder(null);
        jButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton8MouseClicked(evt);
            }
        });
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 180, 80, 30));

        jButton1.setBackground(new java.awt.Color(0,0,0,0));
        jButton1.setBorder(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 630, 130, 50));

        jButton3.setBackground(new java.awt.Color(0,0,0,0));
        jButton3.setBorder(null);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 630, 160, 50));

        jButton7.setBackground(new java.awt.Color(0,0,0,0));
        jButton7.setBorder(null);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 630, 120, 50));

        filtter.setBackground(new java.awt.Color(0,0,0,0));
        filtter.setBorder(null);
        filtter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtterActionPerformed(evt);
            }
        });
        jPanel1.add(filtter, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 40, 120, 40));

        jButton4.setBackground(new java.awt.Color(0,0,0,0));
        jButton4.setBorder(null);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 630, 160, 50));

        jButton2.setBackground(new java.awt.Color(0,0,0,0));
        jButton2.setBorder(null);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 640, 150, 40));

        lbl_foto.setBackground(new java.awt.Color(0,0,0,0));
        jPanel1.add(lbl_foto, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 460, 130, 170));

        btn_upload.setText("UPLOAD FOTO");
        btn_upload.setBorder(null);
        btn_upload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_uploadActionPerformed(evt);
            }
        });
        jPanel1.add(btn_upload, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 380, 120, 40));

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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 130, 730, 490));

        kategori.setBackground(new java.awt.Color(0,0,0,0));
        kategori.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        kategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sarana", "Prasarana" }));
        kategori.setBorder(null);
        jPanel1.add(kategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, 320, 30));

        txt_pengaduan.setEditable(false);
        txt_pengaduan.setBackground(new java.awt.Color(0,0,0,0));
        txt_pengaduan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_pengaduan.setBorder(null);
        txt_pengaduan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_pengaduanActionPerformed(evt);
            }
        });
        jPanel1.add(txt_pengaduan, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 320, 30));

        lokasi.setBackground(new java.awt.Color(0,0,0,0));
        lokasi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lokasi.setBorder(null);
        jPanel1.add(lokasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 480, 320, 30));

        cmb_kategori.setBackground(new java.awt.Color(0,0,0,0));
        cmb_kategori.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cmb_kategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sarana", "Prasarana" }));
        cmb_kategori.setBorder(null);
        cmb_kategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_kategoriActionPerformed(evt);
            }
        });
        jPanel1.add(cmb_kategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 40, 100, 40));
        jPanel1.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 90, 260, 30));
        jPanel1.add(jDateChooser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 90, 250, 30));

        txt_cari.setBackground(new java.awt.Color(0,0,0,0));
        txt_cari.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_cari.setBorder(null);
        txt_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cariActionPerformed(evt);
            }
        });
        txt_cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cariKeyReleased(evt);
            }
        });
        jPanel1.add(txt_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 40, 500, 40));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/from pengaduan.png"))); // NOI18N
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 770));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String tampilan="yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(tampilan);
        String tanggal=String.valueOf(fm.format(jd_tglpengaduan.getDate()));
     
        try{
          String sql = "INSERT INTO pengaduan (nik, tgl_pengaduan, isi_laporan, nama, foto, status, Kategori, lokasi) VALUES (?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_nik.getText());
            pst.setString(2, tanggal);
            pst.setString(3, txt_isi.getText());
            pst.setString(4, txt_nama.getText());
            pst.setString(5, pathFoto); // status default
            pst.setString(6, "menunggu");
            pst.setString(7, kategori.getSelectedItem().toString());
            pst.setString(8, lokasi.getText());

           pst.execute();
           JOptionPane.showMessageDialog(null, "saved");
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, e);
       }
       getData();
       bersih();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaActionPerformed

    private void jButton8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton8MouseClicked
        String sql = "select * from pelapor where nik='" + txt_nik.getText() + "'";  
        try {
            
            
            pst = conn.prepareStatement(sql);
            rs  = pst.executeQuery();
            
            if (rs.next()) {
                String addl = rs.getString("nama");
                txt_nama.setText(addl);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8MouseClicked

    private void filtterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtterActionPerformed
        if (jDateChooser1.getDate() == null || jDateChooser2.getDate() == null) {
         JOptionPane.showMessageDialog(this, "Silakan pilih rentang tanggal!");
         return; }

        DefaultTableModel modelBaru = new DefaultTableModel();
        modelBaru.addColumn("ID Pengaduan");
        modelBaru.addColumn("NIK");
        modelBaru.addColumn("Nama");
        modelBaru.addColumn("Tanggal");
        modelBaru.addColumn("Isi Laporan");
        modelBaru.addColumn("Foto");
        modelBaru.addColumn("Kategori");
        modelBaru.addColumn("Lokasi");
        modelBaru.addColumn("Status");

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dari = sdf.format(jDateChooser1.getDate());
            String sampai = sdf.format(jDateChooser2.getDate());

            String sql = "SELECT * FROM pengaduan WHERE tgl_pengaduan BETWEEN ? AND ? AND nik=? AND status=? ORDER BY tgl_pengaduan ASC";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, dari);
            pst.setString(2, sampai);
            pst.setString(3, session.getNik());
            pst.setString(4, "menunggu"); 

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                modelBaru.addRow(new Object[]{
                    rs.getString("id_pengaduan"),
                    rs.getString("nik"),
                    rs.getString("nama"),
                    rs.getString("tgl_pengaduan"),
                    rs.getString("isi_laporan"),
                    rs.getString("foto"),
                    rs.getString("Kategori"),
                    rs.getString("lokasi"),
                    rs.getString("status")
                });
            }

            jTable1.setModel(modelBaru);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal filter: " + e.getMessage());
        }

         // TODO add your handling code here:
    }//GEN-LAST:event_filtterActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jTable1MouseClicked(evt);
        }
       });

        
        int i = jTable1.getSelectedRow(); // Mendapatkan baris yang dipilih
        TableModel model = jTable1.getModel();

        txt_pengaduan.setText(model.getValueAt(i, 0).toString());
        txt_nik.setText(model.getValueAt(i, 1).toString());
        txt_nama.setText(model.getValueAt(i, 2).toString());
        txt_isi.setText(model.getValueAt(i, 4).toString());
        // Mengisi data kategori ke ComboBox
        kategori.setSelectedItem(jTable1.getValueAt(i, 6).toString());
        lokasi.setText(model.getValueAt(i, 7).toString());
        
        // TANGGAL
    try {
        String tanggal = model.getValueAt(i, 3).toString();
        java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(tanggal);
        jd_tglpengaduan.setDate(date);
    } catch (Exception e) {
        e.printStackTrace();
    }

    // ================= FOTO =================
    pathFoto = model.getValueAt(i, 5).toString();

    ImageIcon icon = new ImageIcon(pathFoto);
    Image img = icon.getImage().getScaledInstance(
        lbl_foto.getWidth(),
        lbl_foto.getHeight(),
        Image.SCALE_SMOOTH
    );
    lbl_foto.setIcon(new ImageIcon(img));
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
       ukk.menu.menuPelapor menu = new ukk.menu.menuPelapor();
    
    // 2. Menampilkan Menu Admin
    menu.setVisible(true);
    
    // 3. Menutup halaman Riwayat Aspirasi saat ini
    this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       int baris = jTable1.getSelectedRow();

if (baris == -1) {
    JOptionPane.showMessageDialog(null, "Pilih data dulu!");
    return;
}

try {
    conn = Koneksi.KoneksiDB(); // WAJIB

    String id = jTable1.getValueAt(baris, 0).toString();

    String sql = "DELETE FROM pengaduan WHERE id_pengaduan=?";
    PreparedStatement pst = conn.prepareStatement(sql);
    pst.setString(1, id);

    int rows = pst.executeUpdate();

    if (rows > 0) {
        JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
    } else {
        JOptionPane.showMessageDialog(null, "Data tidak ditemukan!");
    }

    pst.close();
    conn.close();

} catch (Exception e) {
    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
}

getData();
bersih();

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        bersih();
        refreshSemua();
        tampilIdPengaduan();
        getData();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       String tampilan = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(tampilan);

        if (jd_tglpengaduan.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Tanggal belum dipilih!");
            return;
        }

        String tanggal = fm.format(jd_tglpengaduan.getDate());

try {
    String id_pengaduan = txt_pengaduan.getText();
    String nik = txt_nik.getText();
    String isi = txt_isi.getText();

    // DEBUG (biar ketahuan nilainya)
    System.out.println("ID: " + id_pengaduan);

    String sql = "UPDATE pengaduan SET nik=?, tgl_pengaduan=?, isi_laporan=? WHERE id_pengaduan=?";
    pst = conn.prepareStatement(sql);

    pst.setString(1, nik);
    pst.setString(2, tanggal);
    pst.setString(3, isi);
    pst.setInt(4, Integer.parseInt(id_pengaduan)); // WAJIB int kalau di DB int

    int rows = pst.executeUpdate();

    System.out.println("Baris terupdate: " + rows);

    if (rows > 0) {
        JOptionPane.showMessageDialog(null, "UPDATE SUKSES");
    } else {
        JOptionPane.showMessageDialog(null, "ID TIDAK DITEMUKAN / TIDAK ADA PERUBAHAN");
    }

} catch (Exception e) {
    JOptionPane.showMessageDialog(null, e);
}
        getData();
        bersih();

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btn_uploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_uploadActionPerformed
    javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
    int hasil = chooser.showOpenDialog(null);

    if (hasil == javax.swing.JFileChooser.APPROVE_OPTION) {
        java.io.File file = chooser.getSelectedFile();
        pathFoto = file.getAbsolutePath();

        ImageIcon icon = new ImageIcon(pathFoto);
        Image img = icon.getImage().getScaledInstance(
                lbl_foto.getWidth(),
                lbl_foto.getHeight(),
                Image.SCALE_SMOOTH
        );
        lbl_foto.setIcon(new ImageIcon(img));
    }

        // TODO add your handling code here:
    }//GEN-LAST:event_btn_uploadActionPerformed

    private void txt_pengaduanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_pengaduanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_pengaduanActionPerformed

    private void cmb_kategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_kategoriActionPerformed
        getDataByKategori();
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_kategoriActionPerformed

    private void txt_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cariActionPerformed

    private void txt_cariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cariKeyReleased
     // Membuat model tabel baru (wadah untuk menampung data hasil query)
        DefaultTableModel model = new DefaultTableModel();

        // Menambahkan nama kolom pada tabel
//        model.addColumn("No");                // Nomor urut
        model.addColumn("ID Pengaduan");      // ID pengaduan
        model.addColumn("NIK");               // NIK pelapor
        model.addColumn("Nama");              // Nama pelapor
        model.addColumn("Tanggal");           // Tanggal pengaduan
        model.addColumn("Isi Laporan");       // Isi laporan
        model.addColumn("Foto");              // Foto bukti
        model.addColumn("Kategori");          // Kategori (Sarana/Prasarana)
        model.addColumn("Lokasi");            // Lokasi kejadian
        model.addColumn("Status");            // Status pengaduan

        try {

            // Mengambil teks dari field pencarian dan menghapus spasi berlebih
            String cari = txt_cari.getText().trim();

            // Mengambil pilihan kategori dari combobox
            String kategori = cmb_kategori.getSelectedItem().toString();

            // Query dasar (1=1 supaya mudah ditambahkan kondisi AND)
            String sql = "SELECT * FROM pengaduan WHERE status = 'menunggu'";

            // Jika kolom pencarian tidak kosong, tambahkan filter nama
            if (!cari.equals("")) {
                sql += " AND nama LIKE ?";
            }

            // Jika kategori bukan "Semua", tambahkan filter kategori
            if (!kategori.equals("Semua")) {
                sql += " AND Kategori = ?";
            }

            // Mengurutkan data berdasarkan tanggal terbaru
            sql += " ORDER BY tgl_pengaduan DESC";

            // Menyiapkan prepared statement
            PreparedStatement pst = conn.prepareStatement(sql);

            int index = 1; // Untuk mengatur urutan tanda ? pada query

            // Jika ada pencarian nama, isi parameter pertama
            if (!cari.equals("")) {
                pst.setString(index++, "%" + cari + "%"); 
                // % digunakan agar bisa mencari sebagian kata
            }

            // Jika ada filter kategori, isi parameter berikutnya
            if (!kategori.equals("Semua")) {
                pst.setString(index++, kategori);
            }

            // Menjalankan query
            ResultSet res = pst.executeQuery();

            int no = 1; // Nomor urut tabel

            // Perulangan untuk mengambil semua data dari hasil query
            while (res.next()) {

                // Menambahkan setiap baris data ke dalam tabel
                model.addRow(new Object[]{
                   // no++,                                   // Nomor urut otomatis
                    res.getString("id_pengaduan"),           // Ambil data id_pengaduan
                    res.getString("nik"),                    // Ambil data nik
                    res.getString("nama"),                   // Ambil data nama
                    res.getString("tgl_pengaduan"),          // Ambil tanggal
                    res.getString("isi_laporan"),            // Ambil isi laporan
                    res.getString("foto"),                   // Ambil foto
                    res.getString("Kategori"),               // Ambil kategori
                    res.getString("lokasi"),                 // Ambil lokasi
                    res.getString("status")                  // Ambil status
                });
            }

            // Menampilkan model ke JTable
            jTable1.setModel(model);

           

        } catch (Exception e) {

            // Menampilkan error di console (agar tidak mengganggu auto-search)
            System.out.println("Error search: " + e.getMessage());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cariKeyReleased

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
            java.util.logging.Logger.getLogger(pengaduan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pengaduan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pengaduan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pengaduan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pengaduan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_upload;
    private javax.swing.JComboBox<String> cmb_kategori;
    private javax.swing.JButton filtter;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private com.toedter.calendar.JDateChooser jd_tglpengaduan;
    private javax.swing.JComboBox<String> kategori;
    private javax.swing.JLabel lbl_foto;
    private javax.swing.JTextField lokasi;
    private javax.swing.JTextField txt_cari;
    private javax.swing.JTextArea txt_isi;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_nik;
    private javax.swing.JTextField txt_pengaduan;
    // End of variables declaration//GEN-END:variables
    
}
