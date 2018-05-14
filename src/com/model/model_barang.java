package com.model;

import com.controller.controller_barang;
import com.koneksi.koneksi;
import com.view.FrmBarang;
import java.awt.HeadlessException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class model_barang implements controller_barang{  

    @Override
    public void Simpan(FrmBarang brg) throws SQLException {
        long hargabrg = Long.parseLong(brg.txthargabrg.getText());
        double panjangbrg = Double.parseDouble(brg.txtpanjangbrg.getText());
        double lebarbrg = Double.parseDouble(brg.txtlebarbrg.getText());
        double tebalbrg = Double.parseDouble(brg.txttebalbrg.getText());
        int qtybrg = Integer.parseInt(brg.txtqtybrg.getText());
        double volumebrg = panjangbrg * lebarbrg;
        try {
            Connection con = koneksi.getKoneksi();
            String sql = "insert barang values(?,?,?,?,?,?,?,?)";
            PreparedStatement prepare = con.prepareStatement(sql);
            
            prepare.setString(1, brg.txtkode_barang.getText());
            prepare.setString(2, brg.txtnama.getText());
            prepare.setLong(3, hargabrg);
            prepare.setDouble(4, panjangbrg);
            prepare.setDouble(5, lebarbrg);
            prepare.setDouble(6, tebalbrg);
            prepare.setDouble(7, volumebrg);
            prepare.setInt(8, qtybrg);
            prepare.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            prepare.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            Tampil(brg);
            brg.setLebarKolom();
            Bersih(brg);
        } 
    }

    @Override
    public void Ubah(FrmBarang brg) throws SQLException {
        long hargabrg = Long.parseLong(brg.txthargabrg.getText());
        double panjangbrg = Double.parseDouble(brg.txtpanjangbrg.getText());
        double lebarbrg = Double.parseDouble(brg.txtlebarbrg.getText());
        double tebalbrg = Double.parseDouble(brg.txttebalbrg.getText());
        int qtybrg = Integer.parseInt(brg.txtqtybrg.getText());
        double volumebrg = panjangbrg * lebarbrg;
        try {
            Connection con = koneksi.getKoneksi();
            String sql = "UPDATE barang SET nama = ?, "
                    + "harga = ?, "
                    + "panjang = ?, "
                    + "lebar = ?, "
                    + "tebal = ?, "
                    + "volum_brg = ?, "
                    + "qty = ? "
                    + "WHERE kode_barang = ? ";
            try (PreparedStatement prepare = con.prepareStatement(sql)) {
                prepare.setString(1, brg.txtnama.getText());
                prepare.setLong(2, hargabrg);
                prepare.setDouble(3, panjangbrg);
                prepare.setDouble(4, lebarbrg);
                prepare.setDouble(5, tebalbrg);
                prepare.setDouble(6, volumebrg);
                prepare.setInt(7, qtybrg);
                prepare.setString(8, brg.txtkode_barang.getText());
                prepare.executeUpdate();
                
                JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
            }
        } catch (HeadlessException | SQLException e) {
            System.out.println(e);
        } finally {
            Tampil(brg);
            brg.setLebarKolom();
            Bersih(brg);
        }
    }

    @Override
    public void Hapus(FrmBarang brg) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            String sql = "delete from barang where kode_barang = ?";
            try (PreparedStatement prepare = con.prepareStatement(sql)) {
                prepare.setString(1, brg.txtkode_barang.getText());
                prepare.executeUpdate();
                
                JOptionPane.showMessageDialog(null, "Data Berhasi Dihapus");
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            Tampil(brg);
            brg.setLebarKolom();
            Bersih(brg);
        }
    }

    @Override
    public void Tampil(FrmBarang brg) throws SQLException {
        brg.tbl.getDataVector().removeAllElements();
        brg.tbl.fireTableDataChanged();
        try {
            Connection con = koneksi.getKoneksi();
            Statement st = con.createStatement();
            String sql = "select * from barang order by kode_barang asc";
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
                brg.tbl.addRow(ob);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void Bersih(FrmBarang brg) {
        brg.txtkode_barang.setText(null);
        brg.txtnama.setText(null);
        brg.txthargabrg.setText(null);
        brg.txtpanjangbrg.setText(null);
        brg.txtlebarbrg.setText(null);
        brg.txttebalbrg.setText(null);
        brg.txtqtybrg.setText(null);
        brg.txtkode_barang.requestFocus();
    }

    @Override
    public void KlikTabel(FrmBarang brg) throws SQLException {
        try {
            int pilih = brg.tblBarang.getSelectedRow();
            if (pilih == -1) {
                return;
            }
            
            brg.txtkode_barang.setText(brg.tbl.getValueAt(pilih, 0).toString());
            brg.txtnama.setText(brg.tbl.getValueAt(pilih, 1).toString());
            brg.txthargabrg.setText(brg.tbl.getValueAt(pilih, 2).toString());
            brg.txtpanjangbrg.setText(brg.tbl.getValueAt(pilih, 3).toString());
            brg.txtlebarbrg.setText(brg.tbl.getValueAt(pilih, 4).toString());
            brg.txttebalbrg.setText(brg.tbl.getValueAt(pilih, 5).toString());
            brg.txtqtybrg.setText(brg.tbl.getValueAt(pilih, 7).toString());
        } catch (Exception e) {
        }
        try {
            Connection con = koneksi.getKoneksi();
            Statement st = con.createStatement();
            String sql = "select nama from barang where nama = '"+brg.txtnama.getText()+"'";
            ResultSet rs = st.executeQuery(sql);
        
            rs.close(); st.close();
        } catch (Exception e) {
        }
    }    

   @Override
    public void Combo(FrmBarang brg) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            Statement st = con.createStatement();
            String sql = "select nama from barang";
            ResultSet rs = st.executeQuery(sql);
        
            rs.close(); st.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
    }

    @Override
    public void Cari(FrmBarang brg) throws SQLException {
        brg.tbl.getDataVector().removeAllElements();
        brg.tbl.fireTableDataChanged();
        try {
            Connection con = koneksi.getKoneksi();
            Statement st = con.createStatement();
            String sql = "select * from barang where nama like '%" + brg.txtCari.getText() + "%'";
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
                brg.tbl.addRow(ob);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void Combo2(FrmBarang brg) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*@Override
    public void Combo2(FrmBarang brg) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            Statement st = con.createStatement();
            String sql = "select id_kategori from kategori where nama = '"+"'";
            ResultSet rs = st.executeQuery(sql);
        
            while(rs.next()){
                Object[] ob = new Object[3];
                ob[1] = rs.getString(1);

                brg.txtharga.setText(rs.getString("id_kategori"));
            }
            rs.close(); st.close();
        } catch (Exception e) {
        }
    }*/
}
