package com.model;

import com.controller.controller_suplier;
import com.koneksi.koneksi;
import com.view.FrmSuplier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class model_suplier implements controller_suplier{
    private Object jRadioButton2;
    private Object jRadioButton1;

    @Override
    public void Simpan(FrmSuplier spl) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            String sql = "insert suplier values (?,?,?,?,?,?,?,?)";
            PreparedStatement prepare = con.prepareStatement(sql);
            
            prepare.setString(1, spl.txtidrekanan.getText());
            prepare.setString(2, spl.status);
            prepare.setString(3, spl.txtnama_suplier.getText());
            prepare.setString(4, spl.txtalamat.getText());
            prepare.setString(5, spl.txtno_telp.getText());
            prepare.setString(6, spl.txtemail.getText());
            prepare.setString(7, spl.txtbank.getText());
            prepare.setString(8, spl.txtnomor_rekening.getText());
            prepare.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            prepare.close();
        } catch (Exception e){
            System.out.println(e);
        } finally {
            Tampil(spl);
            spl.setLebarKolom();
            Bersih(spl);
            AutoNomor(spl);
        }
    }

    @Override
    public void Ubah(FrmSuplier spl) throws SQLException {
         try {
            Connection con = koneksi.getKoneksi();
            String sql = "update suplier set status = ?,"
                    + "nama = ?, "
                    + "alamat = ?, "
                    + "no_telp = ?, "
                    + "bank = ?, "
                    + "email = ?, "
                    + "nomor_rekening = ? "
                    + "where idrekanan = ?";
            PreparedStatement prepare = con.prepareStatement(sql);
            
            prepare.setString(1, spl.status);
            prepare.setString(2, spl.txtnama_suplier.getText());
            prepare.setString(3, spl.txtalamat.getText());
            prepare.setString(4, spl.txtno_telp.getText());
            prepare.setString(5, spl.txtemail.getText());
            prepare.setString(6, spl.txtbank.getText());
            prepare.setString(7, spl.txtnomor_rekening.getText());
            prepare.setString(8, spl.txtidrekanan.getText());
            prepare.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
            prepare.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            Tampil(spl);
            spl.setLebarKolom();
            Bersih(spl);
            AutoNomor(spl);
        }
    }

    @Override
    public void Hapus(FrmSuplier spl) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            String sql = "delete from suplier where idrekanan = ?";
            PreparedStatement prepare = con.prepareStatement(sql);
            
            prepare.setString(1, spl.txtidrekanan.getText());
            prepare.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
            prepare.close();
        } catch (Exception e){
            System.out.println(e);
        } finally {
            Tampil(spl);
            spl.setLebarKolom();
            Bersih(spl);
            AutoNomor(spl);
        }
    }

    @Override
    public void Tampil(FrmSuplier spl) throws SQLException {
        spl.tbl.getDataVector().removeAllElements();
        spl.tbl.fireTableDataChanged();
        try {
            Connection con = koneksi.getKoneksi();
            Statement st = con.createStatement();
            String sql = "select * from suplier order by idrekanan asc";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Object[] ob = new Object[10];
                ob[0] = rs.getString(1);
                ob[1] = rs.getString(2);
                ob[2] = rs.getString(3);
                ob[3] = rs.getString(4);
                ob[4] = rs.getString(5);
                ob[5] = rs.getString(6);
                ob[6] = rs.getString(7);
                ob[7] = rs.getString(8);
                spl.tbl.addRow(ob);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void Bersih(FrmSuplier spl) throws SQLException {
        spl.txtidrekanan.setText(null);
        spl.txtnama_suplier.setText(null);
        spl.txtalamat.setText(null);
        spl.txtno_telp.setText(null);
        spl.txtbank.setText(null);
        spl.txtemail.setText(null);
        spl.txtnomor_rekening.setText(null);
        spl.txtnama_suplier.requestFocus();
    }

    @Override
    public void KlikTabel(FrmSuplier spl) throws SQLException {
        try {
            int pilih = spl.tblsuplier.getSelectedRow();
            if (pilih == -1) {
                return;
            }
            spl.status =(spl.tbl.getValueAt(pilih, 1).toString());
            spl.txtidrekanan.setText(spl.tbl.getValueAt(pilih, 0).toString());
            spl.txtnama_suplier.setText(spl.tbl.getValueAt(pilih, 2).toString());
            spl.txtalamat.setText(spl.tbl.getValueAt(pilih, 3).toString());
            spl.txtno_telp.setText(spl.tbl.getValueAt(pilih, 4).toString());
            spl.txtemail.setText(spl.tbl.getValueAt(pilih,5).toString());
            spl.txtbank.setText(spl.tbl.getValueAt(pilih, 6).toString());
            spl.txtnomor_rekening.setText(spl.tbl.getValueAt(pilih, 7).toString());
        } catch (Exception e) {
            
        }
    }

    @Override
    public void AutoNomor(FrmSuplier spl) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            
            Statement st = con.createStatement();
            String sql = "select max(idrekanan) from suplier";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                int a = rs.getInt(1);
                spl.txtidrekanan.setText(""+ Integer.toString(a+1));
            }
        } catch (SQLException e) {
            System.out.println(""+ e.getMessage());
        }
    }
    
}
