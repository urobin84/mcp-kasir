package com.model;

import com.controller.controller_faktur;
import com.koneksi.koneksi;
import com.view.FrmFaktur;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class model_faktur implements controller_faktur {

    @Override
    public void Simpan(FrmFaktur fkt) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            String sql = "insert faktur values(?,?,?)";
            PreparedStatement prepare = con.prepareStatement(sql);
            
            prepare.setString(1, fkt.txtid_faktur.getText());
            prepare.setString(2, fkt.txtnomer_faktur.getText());
            prepare.setString(3, fkt.txttanggal.getText());
            prepare.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            prepare.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            Tampil(fkt);
            fkt.setLebarKolom();
            Bersih(fkt);
            AutoNomor(fkt);
            Tanggal(fkt);
        } 
    }

    @Override
    public void Hapus(FrmFaktur fkt) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            String sql = "delete from faktur where id_faktur = ?";
            PreparedStatement prepare = con.prepareStatement(sql);
            
            prepare.setString(1, fkt.txtid_faktur.getText());
            prepare.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Data Berhasi Dihapus");
            prepare.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            Tampil(fkt);
            fkt.setLebarKolom();
            Bersih(fkt);
            AutoNomor(fkt);
            Tanggal(fkt);
        }
    }

    @Override
    public void Tampil(FrmFaktur fkt) throws SQLException {
        fkt.tbl.getDataVector().removeAllElements();
        fkt.tbl.fireTableDataChanged();
        try {
            Connection con = koneksi.getKoneksi();
            Statement st = con.createStatement();
            String sql = "select * from faktur order by id_faktur asc";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Object[] ob = new Object[8];
                ob[0] = rs.getString(1);
                ob[1] = rs.getString(2);
                ob[2] = rs.getString(3);
                fkt.tbl.addRow(ob);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void Bersih(FrmFaktur fkt) throws SQLException {
        fkt.txtid_faktur.setText(null);
        fkt.txtnomer_faktur.setText(null);
        fkt.txttanggal.setText(null);
        fkt.txtnomer_faktur.requestFocus();
    }

    @Override
    public void KlikTabel(FrmFaktur fkt) throws SQLException {
        try {
            int pilih = fkt.tblfaktur.getSelectedRow();
            if (pilih == -1) {
                return;
            }
            fkt.txtid_faktur.setText(fkt.tbl.getValueAt(pilih, 0).toString());
            fkt.txtnomer_faktur.setText(fkt.tbl.getValueAt(pilih, 1).toString());
            fkt.txttanggal.setText(fkt.tbl.getValueAt(pilih, 2).toString());
        } catch (Exception e) {
        }
    }

    @Override
    public void Tanggal(FrmFaktur fkt) throws SQLException {
        java.util.Date sekarang = new java.util.Date();
        java.text.SimpleDateFormat kal = new java.text.SimpleDateFormat("yyyy-MM-dd");
        fkt.txttanggal.setText(kal.format(sekarang));        
    }

    @Override
    public void AutoNomor(FrmFaktur fkt) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            
            Statement st = con.createStatement();
            String sql = "select max(id_faktur) from faktur";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                int a = rs.getInt(1);
                fkt.txtid_faktur.setText(""+ Integer.toString(a+1));
            }
        } catch (Exception e) {
            System.out.println(""+ e.getMessage());
        }
    }
    
}
