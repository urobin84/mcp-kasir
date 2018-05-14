/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import com.controller.controller_suplier;
import com.koneksi.koneksi;
import com.view.FrmPegawai;
import com.view.FrmSuplier;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author LENOVO IP300
 */
public class model_pegawai implements controller_suplier{
    private Object pr;
    private Object lk;


    public void Simpan(FrmPegawai peg) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            String sql = "insert pegawai values (?,?,?,?,?,?,?,?)";
            PreparedStatement prepare = con.prepareStatement(sql);
            
            prepare.setString(1, peg.id_pegawai.getText());
            prepare.setString(2, peg.nm_pegawai.getText());
            prepare.setString(3, peg.jkelamin);
            prepare.setString(4, peg.kota_asal_peg.getText());
            prepare.setString(5, peg.tgl_lhr_peg.getText());
            prepare.setString(6, peg.tgl_msuk_peg.getText());
            prepare.setString(7, peg.jabatan.getText());
            prepare.setString(8, peg.gaji_peg.getText());
            prepare.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            prepare.close();
        } catch (HeadlessException | SQLException e){
            System.out.println(e);
        } finally {
            Tampil(peg);
            peg.setLebarKolom();
            Bersih(peg);
            AutoNomor(peg);
        }
    }

    public void Ubah(FrmPegawai peg) throws SQLException {
         try {
            Connection con = koneksi.getKoneksi();
            String sql = "update pegawai set id_pegawai = ?,"
                    + "namapgw = ?, "
                    + "jeniskelamin = ?, "
                    + "tmp_lahir = ?, "
                    + "tgl_lahir = ?, "
                    + "tgl_masuk = ?, "
                    + "jabatan = ? "
                    + "gaji = ?";
            PreparedStatement prepare = con.prepareStatement(sql);
            
            prepare.setString(1, peg.id_pegawai.getText());
            prepare.setString(2, peg.nm_pegawai.getText());
            prepare.setString(3, peg.jkelamin);
            prepare.setString(4, peg.kota_asal_peg.getText());
            prepare.setString(5, peg.tgl_lhr_peg.getText());
            prepare.setString(6, peg.tgl_msuk_peg.getText());
            prepare.setString(7, peg.jabatan.getText());
            prepare.setString(8, peg.gaji_peg.getText());
            prepare.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
            prepare.close();
        } catch (HeadlessException | SQLException e) {
            System.out.println(e);
        } finally {
            Tampil(peg);
            peg.setLebarKolom();
            Bersih(peg);
            AutoNomor(peg);
        }
    }

    public void Hapus(FrmPegawai peg) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            String sql = "delete from pegawai where id_pegawai = ?";
            PreparedStatement prepare = con.prepareStatement(sql);
            
            prepare.setString(1, peg.id_pegawai.getText());
            prepare.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
            prepare.close();
        } catch (HeadlessException | SQLException e){
            System.out.println(e);
        } finally {
            Tampil(peg);
            peg.setLebarKolom();
            Bersih(peg);
            AutoNomor(peg);
        }
    }

    public void Tampil(FrmPegawai peg) throws SQLException {
        peg.tbl.getDataVector().removeAllElements();
        peg.tbl.fireTableDataChanged();
        try {
            Connection con = koneksi.getKoneksi();
            Statement st = con.createStatement();
            String sql = "select * from pegawai order by id_pegawai asc";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Object[] ob = new Object[8];
                ob[0] = rs.getString(1);
                ob[1] = rs.getString(2);
                ob[2] = rs.getString(3);
                ob[3] = rs.getString(4);
                ob[4] = rs.getString(5);
                ob[5] = rs.getString(6);
                ob[6] = rs.getString(7);
                ob[7] = rs.getString(8);
                peg.tbl.addRow(ob);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void Bersih(FrmPegawai peg) throws SQLException {
        peg.id_pegawai.setText(null);
        peg.nm_pegawai.setText(null);
        peg.jkelamin = "Laki-laki";
        peg.kota_asal_peg.setText(null);
        peg.tgl_lhr_peg.setText(null);
        peg.tgl_msuk_peg.setText(null);
        peg.jabatan.setText(null);
        peg.gaji_peg.setText(null);
        peg.nm_pegawai.requestFocus();
    }

    public void KlikTabel(FrmPegawai peg) throws SQLException {
        try {
            int pilih = peg.tblpegawai.getSelectedRow();
            if (pilih == -1) {
                return;
            }
            peg.id_pegawai.setText(peg.tbl.getValueAt(pilih, 0).toString());
            peg.nm_pegawai.setText(peg.tbl.getValueAt(pilih, 1).toString());
            peg.jkelamin = (peg.tbl.getValueAt(pilih, 2).toString());
            peg.kota_asal_peg.setText(peg.tbl.getValueAt(pilih, 3).toString());
            peg.tgl_lhr_peg.setText(peg.tbl.getValueAt(pilih,4).toString());
            peg.tgl_msuk_peg.setText(peg.tbl.getValueAt(pilih, 5).toString());
            peg.jabatan.setText(peg.tbl.getValueAt(pilih, 6).toString());
            peg.gaji_peg.setText(peg.tbl.getValueAt(pilih, 7).toString());
        } catch (Exception e) {
            
        }
    }

    public void AutoNomor(FrmPegawai peg) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            
            Statement st = con.createStatement();
            String sql = "select max(id_pegawai) from pegawai";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                int a = rs.getInt(1);
                peg.id_pegawai.setText(""+ Integer.toString(a+1));
            }
        } catch (Exception e) {
            System.out.println(""+ e.getMessage());
        }
    }

    @Override
    public void Simpan(FrmSuplier spl) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Ubah(FrmSuplier spl) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Hapus(FrmSuplier spl) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Tampil(FrmSuplier spl) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Bersih(FrmSuplier spl) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void KlikTabel(FrmSuplier spl) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void AutoNomor(FrmSuplier spl) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
