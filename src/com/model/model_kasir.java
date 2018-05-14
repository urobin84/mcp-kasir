
package com.model;

import com.controller.controller_kasir;
import com.koneksi.UserID;
import com.koneksi.koneksi;
import com.view.FrmKasir;
import static com.view.FrmKasir.jBkembali;
import static com.view.FrmKasir.txttotal2;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.table.TableModel;
import static mondrian.olap.fun.vba.Vba.string;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import static javax.swing.UIManager.getInt;



public class model_kasir implements controller_kasir{
    private static Object Grand_total;
    
    JasperReport jasperReport;
    JasperDesign jasperDesign;
    JasperPrint jasperPrint;

    
    public void uang() {
        String tampung_harga = txttotal2.getText();
        try {
            double harga = Double.parseDouble(txttotal2.getText());
            String ubah =  FrmKasir.txttotal2.getText();
            FrmKasir.terbilang.setText(new Terbilang(ubah)+" rupiah".toString());
            DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setCurrencySymbol("");
            dfs.setMonetaryDecimalSeparator(',');
            dfs.setGroupingSeparator('.');
            df.setDecimalFormatSymbols(dfs);
            String hsl = "Rp." + df.format(harga);
            txttotal2.setText(hsl);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Pengisian harga tidak boleh kosong");
        }
    }
    public void uang1() {
        String tampung_harga = FrmKasir.txttotal2.getText();
        try {
            double harga = Double.parseDouble(FrmKasir.txttotal2.getText());
            String ubah =  FrmKasir.txttotal2.getText();
            FrmKasir.terbilang.setText(new Terbilang(ubah)+" rupiah".toString());
            DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setCurrencySymbol("");
            dfs.setMonetaryDecimalSeparator(',');
            dfs.setGroupingSeparator('.');
            df.setDecimalFormatSymbols(dfs);
            String hsl = "Rp." + df.format(harga);
            FrmKasir.txttotal2.setText(hsl);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    Map<String, Object> parameter = new HashMap<String, Object>();
    
    @Override
    public void Simpan(FrmKasir ksr) throws SQLException {
        String hargaksr = FrmKasir.txthargaksr.getText();
        String panjangksr = FrmKasir.txtpanjangksr.getText();
        String lebarksr = FrmKasir.txtlebarksr.getText();
        String qtyksr = FrmKasir.txtqtyksr.getText();
        double hargaksr_int = Double.parseDouble( hargaksr.replace(",",".") );
        double panjangksr_int = Double.parseDouble( panjangksr.replace(",",".") );
        double lebarksr_int = Double.parseDouble( lebarksr.replace(",",".") );
        int qtyksr_int = Integer.parseInt(qtyksr);
        double volumksr = panjangksr_int * lebarksr_int;
        double totalksr_int = volumksr * hargaksr_int;
        try {
            Connection con = koneksi.getKoneksi();
            
            String sql = "insert transaksi values (?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement prepare = con.prepareStatement(sql);
            
            prepare.setString(1, ksr.txtid_transaksi.getText());
            prepare.setString(2, ksr.txtkode_transaksi.getText());
            prepare.setString(3, ksr.txtnama_kasir.getText());
            prepare.setString(4, ksr.txtTanggal.getText());
            prepare.setString(5, FrmKasir.txtkode_barang.getText());
            prepare.setString(6, FrmKasir.txtnama.getText());
            prepare.setDouble(7, hargaksr_int);
            prepare.setDouble(8, panjangksr_int);
            prepare.setDouble(9, lebarksr_int);
            prepare.setDouble(10, volumksr);
            prepare.setInt(11, qtyksr_int);
            prepare.setDouble(12, totalksr_int);
            prepare.executeUpdate();
            
            prepare.close();
        } catch (Exception e){
            System.out.println(e);
        }
        try {
            Connection con = koneksi.getKoneksi();
            String sql = "insert detail_transaksi values (?,?)";
            PreparedStatement prepare = con.prepareStatement(sql);
            
            prepare.setString(1, ksr.txtid_transaksi.getText());
            prepare.setString(2, FrmKasir.txtkode_barang.getText());
            prepare.executeUpdate();
           
            prepare.close();
        } catch (Exception e) {
        }
        finally {
            Tampil(ksr);
            AutoNomor(ksr);
            Total(ksr);
            Updatebrg(ksr);
            Bersih(ksr);
            ksr.setLebarKolom();
        }
    }    
    @Override
    public void Tampil(FrmKasir ksr) throws SQLException {
        ksr.tbl.getDataVector().removeAllElements();
        ksr.tbl.fireTableDataChanged();
        try {
            Connection con = koneksi.getKoneksi();
            Statement st = con.createStatement();
            String sql = "select a.id_transaksi, "
                    + "c.kode_barang, c.nama, c.harga, a.panjang, a.lebar, a.volume, a.qtyksr, a.totalksr from transaksi a "
                    + "join detail_transaksi b "
                    + "on b.id_transaksi=a.id_transaksi "
                    + "join barang c on c.kode_barang=b.kode_barang "
                    + "where a.kode_transaksi= '"+ksr.txtkode_transaksi.getText()+"'"
                    + "order by a.id_transaksi asc";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Object[] ob = new Object[9];
                ob[0] = rs.getString(1);
                ob[1] = rs.getString(2);
                ob[2] = rs.getString(3);
                ob[3] = rs.getString(4);
                ob[4] = rs.getString(5);
                ob[5] = rs.getString(6);
                ob[6] = rs.getString(7);
                ob[7] = rs.getString(8);
                ob[8] = rs.getString(9);
                ksr.tbl.addRow(ob);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void Tanggal(FrmKasir ksr) throws SQLException {
        java.util.Date sekarang = new java.util.Date();
        java.text.SimpleDateFormat kal = new java.text.SimpleDateFormat("yyyy-MM-dd");
        ksr.txtTanggal.setText(kal.format(sekarang));
    }

    @Override
    public void ScanBarcode(FrmKasir ksr) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            Statement st = con.createStatement();
            String sql = "select * from barang where kode_barang = '"+FrmKasir.txtkode_barang.getText()+"'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                FrmKasir.txtnama.setText(rs.getString("nama"));
                FrmKasir.txthargaksr.setText(rs.getString("harga"));
                FrmKasir.txtqtyksr.setText("1");
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            FrmKasir.txthargaksr.requestFocus();
        }
    }

    @Override
    public void AutoNomor(FrmKasir ksr) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            
            Statement st = con.createStatement();
            String sql = "select max(id_transaksi) from transaksi";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                int a = rs.getInt(1);
                ksr.txtid_transaksi.setText(""+ Integer.toString(a+1));
            }
        } catch (Exception e) {
            System.out.println(""+ e.getMessage());
        }
    }

    @Override
    public void AutoNomorKode(FrmKasir ksr) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            
            Statement st = con.createStatement();
            String sql = "select max(kode_transaksi) from transaksi";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                int a = rs.getInt(1);
                ksr.txtkode_transaksi.setText(""+ Integer.toString(a+1));
            }
        } catch (Exception e) {
            System.out.println(""+ e.getMessage());
        }
    }

    public void Updatebrg(FrmKasir ksr) throws SQLException {
        
        try {
            Connection con = koneksi.getKoneksi();
            String up = FrmKasir.txtpanjangksr.getText();
            String upp;
            double updouble = Double.parseDouble( up.replace(",",".") );
            Statement st = con.createStatement();
            String sql = "select panjang from barang where kode_barang = '"+FrmKasir.txtkode_barang.getText()+"'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                 double a = rs.getDouble(1);
                 double b = a - updouble;
                 FrmKasir.labelsoft.setText(Double.toString(b));
                 }
            } catch (Exception e) {
            System.out.println(""+ e.getMessage());
            }finally {
            Upd(ksr);
        }
            }
    public void Upd(FrmKasir ksr) throws SQLException {
        FrmKasir.labelsoft.setVisible(false);
        String updp = FrmKasir.labelsoft.getText();
        double b = Double.parseDouble( updp.replace(",",".") );
            try{
                
                Connection col = koneksi.getKoneksi();
            String sqlp = "UPDATE barang SET "
                    + "panjang = ? "
                    + "WHERE kode_barang = '"+FrmKasir.txtkode_barang.getText()+"'";
            PreparedStatement prepare = col.prepareStatement(sqlp);
                prepare.setDouble(1, b);
                prepare.executeUpdate();
            }catch (Exception e) {
            System.out.println(""+ e.getMessage());
            }
    }
  
    @Override
    public void Bersih(FrmKasir ksr) throws SQLException {
        FrmKasir.txtkode_barang.setText(null);
        FrmKasir.txtnama.setText(null);
        FrmKasir.txthargaksr.setText(null);
        FrmKasir.txtpanjangksr.setText(null);
        FrmKasir.txtlebarksr.setText(null);
        FrmKasir.txtqtyksr.setText(null);
        FrmKasir.txtkode_barang.requestFocus();
    }

    @Override
    public void Total(FrmKasir ksr) throws SQLException {
        int JumlahBaris = FrmKasir.tblKasir.getRowCount();
        long Total = 0;
        int Harga_Barang;
        TableModel tableModel;
        tableModel = FrmKasir.tblKasir.getModel();
        for (int i = 0; i < JumlahBaris; i++) {
            Harga_Barang = Integer.parseInt(tableModel.getValueAt(i, 8).toString());
            Total = (Total + Harga_Barang);
            ksr.txttotal.setValue(Total);
            FrmKasir.txttotal2.setText(String.valueOf(Total));
            uang();
        }
    }
    public void Grand_Total(FrmKasir ksr) throws SQLException {
        long Nilai_diskon = (long)ksr.txtdiskonksr.getValue();
        long Nilai_total = (long)ksr.txttotal.getValue();
            Grand_total = (Nilai_total - Nilai_diskon);
            FrmKasir.txtgtotal.setValue(Grand_total);
            FrmKasir.txttotal2.setText(String.valueOf(Grand_total));
            uang();
            ksr.txtdpksr.requestFocus();
    }

    /**
     *
    
     */
    public void dp(FrmKasir ksr) throws SQLException {
        long gtotal = (long)FrmKasir.txtgtotal.getValue();
        long dp = (long) ksr.txtdpksr.getValue();
        long sisabayar = 0;    
        sisabayar = (gtotal - dp);
            ksr.txtsisabyrksr.setValue(sisabayar);
    }
  
    

    @Override
    public void KlikTabel(FrmKasir ksr) throws SQLException {
        try {
            int pilih = FrmKasir.tblKasir.getSelectedRow();
            if (pilih == -1) {
                return;
            }
            ksr.txtid_transaksi2.setText(ksr.tbl.getValueAt(pilih, 0).toString());
            FrmKasir.txtkode_barang.setText(ksr.tbl.getValueAt(pilih, 1).toString());
            FrmKasir.txtnama.setText(ksr.tbl.getValueAt(pilih, 2).toString());
            FrmKasir.txthargaksr.setText(ksr.tbl.getValueAt(pilih, 3).toString());
            FrmKasir.txtqtyksr.setText(ksr.tbl.getValueAt(pilih, 4).toString());
        } catch (Exception e) {
        } finally {
            FrmKasir.txtqtyksr.requestFocus();
            FrmKasir.txtqtyksr.setText(null);
        }
    }
    
    public void refresh(FrmKasir ksr){
        FrmKasir.txtkode_barang.setText(null);
        FrmKasir.txtnama.setText(null);
        FrmKasir.txthargaksr.setText(null);
        FrmKasir.txtpanjangksr.setText(null);
        FrmKasir.txtlebarksr.setText(null);
        FrmKasir.txtqtyksr.setText(null);
        ksr.txttotal.setText(null);
        FrmKasir.txttotal2.setText(null);
        FrmKasir.terbilang.setText(null);
        FrmKasir.txtkode_barang.requestFocus();
    }
    
    @Override
    public void UbahJumlah(FrmKasir ksr) throws SQLException {
        double harga = Double.valueOf(FrmKasir.txthargaksr.getText());
        double jumlah = Double.valueOf(FrmKasir.txtqtyksr.getText());
        double jumlah_baru = (harga*jumlah);
        FrmKasir.txthargaksr.setText(String.valueOf(jumlah_baru));
        Ubah(ksr);
        Total(ksr);
    }

    @Override
    public void Ubah(FrmKasir ksr) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            String sql = "update transaksi set harga_barang = '"+FrmKasir.txthargaksr.getText()+"', "
                    + "jumlah_barang = '"+FrmKasir.txtqtyksr.getText()+"' "
                    + "where id_transaksi = '"+ksr.txtid_transaksi2.getText()+"'";
            PreparedStatement prepare = con.prepareStatement(sql);
            prepare.executeUpdate();
            prepare.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            Tampil(ksr);
            Bersih(ksr);
            FrmKasir.txtkode_barang.requestFocus();
        }
    }

    @Override
    public void Hapus(FrmKasir ksr) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            String sql = "delete from transaksi where id_transaksi = ?";
            PreparedStatement prepare = con.prepareStatement(sql);
            
            prepare.setString(1, ksr.txtid_transaksi2.getText());
            prepare.executeUpdate();
            
            prepare.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            Tampil(ksr);
            AutoNomor(ksr);
            Bersih(ksr);
            ksr.setLebarKolom();
            Total(ksr);
        }
    }

    @Override
    public void CetakStruk(FrmKasir ksr) throws SQLException {
        try {
            parameter.put("kode_transaksi", ksr.txtkode_transaksi.getText());
            parameter.put("total", ksr.txttotal.getText());
            parameter.put("diskon", ksr.txtdiskonksr.getText());
            parameter.put("grand_total", FrmKasir.txtgtotal.getText());
            parameter.put("dp", ksr.txtdpksr.getText());
            parameter.put("sisabayar", ksr.txtsisabyrksr.getText());
            parameter.put("nmkasir", FrmKasir.txtnama_kasir.getText());
            parameter.put("idtransaksi", ksr.txtid_transaksi.getText());
            parameter.put("nmcustomer", ksr.customer.getText());
            parameter.put("alamat", ksr.Alamat.getText());
            parameter.put("terbilang", FrmKasir.terbilang.getText());
            parameter.put("telp", ksr.telp.getText());
            File file = new File("src/com/report/report1.jrxml");
            jasperDesign = JRXmlLoader.load(file);
            jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, koneksi.getKoneksi());
            JasperViewer.viewReport(jasperPrint, false);
            //printReport = JasperPrintManager.printReport(jasperPrint, false);
        } catch (Exception e) {
        }
    }

    public void Filter(FrmKasir ksr) {
        String fil = FrmKasir.txtnama_kasir.getText();
        String fill = "kasir";
    if(fil != fill){
            jBkembali.setVisible(false);
        }
    }

    @Override
    public void Kembalian(FrmKasir ksr) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}