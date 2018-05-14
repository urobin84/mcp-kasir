package com.model;

import com.controller.controller_pembelian;
import com.koneksi.koneksi;
import com.view.FrmPembalian;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class model_pembelian implements controller_pembelian {

    @Override
    public void Simpan(FrmPembalian pmbl) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            String sql = "insert pembelian values(?,?,?,?,?)";
            PreparedStatement prepare = con.prepareStatement(sql);
            
            prepare.setString(1, pmbl.txtid_pembelian.getText());
            prepare.setString(2, pmbl.txtid_faktur.getText());
            prepare.setString(3, pmbl.txtid_suplier.getText());
            prepare.setString(4, pmbl.txtTanggal.getText());
            prepare.setString(5, pmbl.txtJumlah.getText());
            prepare.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            prepare.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            Tampil(pmbl);
            pmbl.setLebarKolom();
            Bersih(pmbl);
            AutoNomor(pmbl);
        } 
    }

    @Override
    public void Hapus(FrmPembalian pmbl) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            String sql = "delete from pembelian where id_pembelian = ?";
            PreparedStatement prepare = con.prepareStatement(sql);
            
            prepare.setString(1, pmbl.txtid_pembelian.getText());
            prepare.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Data Berhasi Dihapus");
            prepare.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            Tampil(pmbl);
            pmbl.setLebarKolom();
            Bersih(pmbl);
            AutoNomor(pmbl);
        }
    }

    @Override
    public void Tampil(FrmPembalian pmbl) throws SQLException {
        pmbl.tbl.getDataVector().removeAllElements();
        pmbl.tbl.fireTableDataChanged();
        try {
            Connection con = koneksi.getKoneksi();
            Statement st = con.createStatement();
            String sql = "select * from pembelian order by id_pembelian asc";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Object[] ob = new Object[8];
                ob[0] = rs.getString(1);
                ob[1] = rs.getString(2);
                ob[2] = rs.getString(3);
                ob[3] = rs.getString(4);
                ob[4] = rs.getString(5);
                pmbl.tbl.addRow(ob);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void KlikTabel(FrmPembalian pmbl) throws SQLException {
        try {
            int pilih = pmbl.tblPembelian.getSelectedRow();
            if (pilih == -1) {
                return;
            }
            pmbl.txtid_pembelian.setText(pmbl.tbl.getValueAt(pilih, 0).toString());
            pmbl.txtid_faktur.setText(pmbl.tbl.getValueAt(pilih, 1).toString());
            pmbl.txtid_suplier.setText(pmbl.tbl.getValueAt(pilih, 2).toString());
            pmbl.txtTanggal.setText(pmbl.tbl.getValueAt(pilih, 3).toString());
            pmbl.txtJumlah.setText(pmbl.tbl.getValueAt(pilih, 4).toString());
        } catch (Exception e) {
        }
        try {
            Connection con = koneksi.getKoneksi();
            Statement st = con.createStatement();
            String sql = "select nomer_faktur from faktur where id_faktur = '"+pmbl.txtid_faktur.getText()+"'";
            ResultSet rs = st.executeQuery(sql);
        
            while(rs.next()){
                Object[] ob = new Object[3];
                ob[1] = rs.getString(1);

                pmbl.cmbFaktur.setSelectedItem(rs.getString("nomer_faktur"));
            }
            rs.close(); st.close();
        } catch (Exception e) {
        }
        try {
            Connection con = koneksi.getKoneksi();
            Statement st = con.createStatement();
            String sql = "select nama from suplier where id_suplier = '"+pmbl.txtid_suplier.getText()+"'";
            ResultSet rs = st.executeQuery(sql);
        
            while(rs.next()){
                Object[] ob = new Object[3];
                ob[1] = rs.getString(1);

                pmbl.cmbSuplier.setSelectedItem(rs.getString("nama"));
            }
            rs.close(); st.close();
        } catch (Exception e) {
        }
    }

    @Override
    public void Bersih(FrmPembalian pmbl) throws SQLException {
        pmbl.txtid_pembelian.setText(null);
        pmbl.cmbFaktur.setSelectedItem("Faktur");
        pmbl.cmbSuplier.setSelectedItem("Suplier");
        pmbl.txtTanggal.setText(null);
        pmbl.txtJumlah.setText(null);
    }

    @Override
    public void Combo1(FrmPembalian pmbl) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            Statement st = con.createStatement();
            String sql = "select nomer_faktur from faktur";
            ResultSet rs = st.executeQuery(sql);
        
            while(rs.next()){
                Object[] ob = new Object[3];
                ob[1] = rs.getString(1);

                pmbl.cmbFaktur.addItem((String) ob[1]);                                    
            }
            rs.close(); st.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
    }

    @Override
    public void Combo2(FrmPembalian pmbl) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            Statement st = con.createStatement();
            String sql = "select nama from suplier";
            ResultSet rs = st.executeQuery(sql);
        
            while(rs.next()){
                Object[] ob = new Object[3];
                ob[1] = rs.getString(1);

                pmbl.cmbSuplier.addItem((String) ob[1]);                                    
            }
            rs.close(); st.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
    }

    @Override
    public void AutoNomor(FrmPembalian pmbl) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            
            Statement st = con.createStatement();
            String sql = "select max(id_pembelian) from pembelian";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                int a = rs.getInt(1);
                pmbl.txtid_pembelian.setText(""+ Integer.toString(a+1));
            }
        } catch (Exception e) {
            System.out.println(""+ e.getMessage());
        }
    }

    @Override
    public void Combo3(FrmPembalian pmbl) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            Statement st = con.createStatement();
            String sql = "select id_faktur from faktur where nomer_faktur = '"+pmbl.cmbFaktur.getSelectedItem()+"'";
            ResultSet rs = st.executeQuery(sql);
        
            while(rs.next()){
                Object[] ob = new Object[3];
                ob[1] = rs.getString(1);

                pmbl.txtid_faktur.setText(rs.getString("id_faktur"));
            }
            rs.close(); st.close();
        } catch (Exception e) {
        }
    }

    @Override
    public void Combo4(FrmPembalian pmbl) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            Statement st = con.createStatement();
            String sql = "select id_suplier from suplier where nama = '"+pmbl.cmbSuplier.getSelectedItem()+"'";
            ResultSet rs = st.executeQuery(sql);
        
            while(rs.next()){
                Object[] ob = new Object[3];
                ob[1] = rs.getString(1);

                pmbl.txtid_suplier.setText(rs.getString("id_suplier"));
            }
            rs.close(); st.close();
        } catch (Exception e) {
        }
    }
    
}
