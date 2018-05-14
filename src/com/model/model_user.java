package com.model;

import com.controller.controller_user;
import com.koneksi.koneksi;
import com.view.FrmUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class model_user implements controller_user{

    @Override
    public void Simpan(FrmUser usr) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            String sql = "insert users values (?,?,?,?)";
            PreparedStatement prepare = con.prepareStatement(sql);
            
            prepare.setString(1, usr.txtid_user.getText());
            prepare.setString(2, usr.txtusername.getText());
            prepare.setString(3, usr.txtpassword.getText());
            prepare.setString(4, usr.cmblevel.getSelectedItem().toString());
            prepare.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            prepare.close();
        } catch (Exception e){
            System.out.println(e);
        } finally {
            Tampil(usr);
            usr.setLebarKolom();
            Bersih(usr);
            AutoNomor(usr);
        }
    }

    @Override
    public void Ubah(FrmUser usr) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            String sql = "update users set username = ?, "
                    + "password = ?, "
                    + "level = ? "
                    + "where id = ?";
            PreparedStatement prepare = con.prepareStatement(sql);
            
            prepare.setString(1, usr.txtusername.getText());
            prepare.setString(2, usr.txtpassword.getText());
            prepare.setString(3, usr.cmblevel.getSelectedItem().toString());
            prepare.setString(4, usr.txtid_user.getText());
            prepare.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
            prepare.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            Tampil(usr);
            usr.setLebarKolom();
            Bersih(usr);
            AutoNomor(usr);
        }
    }

    @Override
    public void Hapus(FrmUser usr) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            String sql = "delete from users where id = ?";
            PreparedStatement prepare = con.prepareStatement(sql);
            
            prepare.setString(1, usr.txtid_user.getText());
            prepare.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
            prepare.close();
        } catch (Exception e){
            System.out.println(e);
        } finally {
            Tampil(usr);
            usr.setLebarKolom();
            Bersih(usr);
            AutoNomor(usr);
        }
    }

    @Override
    public void Tampil(FrmUser usr) throws SQLException {
        usr.tbl.getDataVector().removeAllElements();
        usr.tbl.fireTableDataChanged();
        try {
            Connection con = koneksi.getKoneksi();
            Statement st = con.createStatement();
            String sql = "select * from users order by id asc";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Object[] ob = new Object[8];
                ob[0] = rs.getString(1);
                ob[1] = rs.getString(2);
                ob[2] = rs.getString(3);
                ob[3] = rs.getString(4);
                usr.tbl.addRow(ob);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void Bersih(FrmUser usr) throws SQLException {
        usr.txtid_user.setText(null);
        usr.txtusername.setText(null);
        usr.txtpassword.setText(null);
        usr.cmblevel.setSelectedItem("Level");
        usr.txtusername.requestFocus();
    }

    @Override
    public void KlikTabel(FrmUser usr) throws SQLException {
        try {
            int pilih = usr.tblUser.getSelectedRow();
            if (pilih == -1) {
                return;
            }
            usr.txtid_user.setText(usr.tbl.getValueAt(pilih, 0).toString());
            usr.txtusername.setText(usr.tbl.getValueAt(pilih, 1).toString());
            usr.txtpassword.setText(usr.tbl.getValueAt(pilih, 2).toString());
            usr.cmblevel.setSelectedItem(usr.tbl.getValueAt(pilih, 3).toString());
        } catch (Exception e) {
            
        }
    }

    @Override
    public void AutoNomor(FrmUser usr) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            
            Statement st = con.createStatement();
            String sql = "select max(id) from users";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                int a = rs.getInt(1);
                usr.txtid_user.setText(""+ Integer.toString(a+1));
            }
        } catch (Exception e) {
            System.out.println(""+ e.getMessage());
        }
    }
}
    

