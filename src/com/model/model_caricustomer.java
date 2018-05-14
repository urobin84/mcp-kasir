/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import com.controller.controller_caricustomer;
import com.koneksi.koneksi;
import com.view.FrmBarang;
import com.view.FrmCariBarang;
import com.view.FrmCariCustomer;
import com.view.FrmKasir;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author LENOVO IP300
 */
public class model_caricustomer implements controller_caricustomer{

   
    public void Tampil(FrmCariCustomer cc) throws SQLException {
        cc.tbl.getDataVector().removeAllElements();
        cc.tbl.fireTableDataChanged();
        try {
            Connection con = koneksi.getKoneksi();
            Statement st = con.createStatement();
            String sql = "select nama, alamat, no_telp from suplier where status = 'Customer' order by nama asc";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Object[] ob = new Object[3];
                ob[0] = rs.getString(1);
                ob[1] = rs.getString(2);
                ob[2] = rs.getString(3);
                cc.tbl.addRow(ob);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void KlikTabel(FrmCariCustomer cc) throws SQLException {
        try {
            int pilih = cc.tblCariCustomer.getSelectedRow();
            if (pilih == -1) {
                return;
            }
            FrmKasir.customer.setText(cc.tbl.getValueAt(pilih, 0).toString());
            FrmKasir.Alamat.setText(cc.tbl.getValueAt(pilih, 1).toString());
            FrmKasir.telp.setText(cc.tbl.getValueAt(pilih, 2).toString());
           
            FrmKasir.btnOK.requestFocus();
        } catch (Exception e) {
        }
    }

    public void Cari(FrmCariCustomer cc) throws SQLException {
        cc.tbl.getDataVector().removeAllElements();
        cc.tbl.fireTableDataChanged();
        try {
            Connection con = koneksi.getKoneksi();
            Statement st = con.createStatement();
            String sql = "select nama, alamat, no_telp from suplier where nama like '%" + cc.txtCari.getText() + "%' and status = 'Customer'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Object[] ob = new Object[10];
                ob[0] = rs.getString(1);
                ob[1] = rs.getString(2);
                ob[2] = rs.getString(3);
                cc.tbl.addRow(ob);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void Simpan(FrmBarang brg) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Ubah(FrmBarang brg) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Hapus(FrmBarang brg) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Tampil(FrmBarang brg) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Bersih(FrmBarang brg) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void KlikTabel(FrmBarang brg) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Combo(FrmBarang brg) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Combo2(FrmBarang brg) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Cari(FrmBarang brg) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}
