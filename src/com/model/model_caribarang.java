package com.model;

import com.controller.controller_caribarang;
import com.koneksi.koneksi;
import com.view.FrmCariBarang;
import com.view.FrmKasir;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class model_caribarang implements controller_caribarang{

    @Override
    public void Tampil(FrmCariBarang cb) throws SQLException {
        cb.tbl.getDataVector().removeAllElements();
        cb.tbl.fireTableDataChanged();
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
                cb.tbl.addRow(ob);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void KlikTabel(FrmCariBarang cb) throws SQLException {
        try {
            int pilih = cb.tblCariBarang.getSelectedRow();
            if (pilih == -1) {
                return;
            }
            FrmKasir.txtkode_barang.setText(cb.tbl.getValueAt(pilih, 0).toString());
            FrmKasir.txtnama.setText(cb.tbl.getValueAt(pilih, 1).toString());
            FrmKasir.txthargaksr.setText(cb.tbl.getValueAt(pilih, 2).toString());
            //FrmKasir.txtpanjangksr.setText(cb.tbl.getValueAt(pilih, 3).toString());
            //FrmKasir.txtlebarksr.setText(cb.tbl.getValueAt(pilih, 4).toString());
            FrmKasir.txtqtyksr.setText("1");
            FrmKasir.txtpanjangksr.setText("1");
            FrmKasir.txtlebarksr.setText("1");
            FrmKasir.btnOK.requestFocus();
        } catch (Exception e) {
        }
    }

    @Override
    public void Cari(FrmCariBarang cb) throws SQLException {
        cb.tbl.getDataVector().removeAllElements();
        cb.tbl.fireTableDataChanged();
        try {
            Connection con = koneksi.getKoneksi();
            Statement st = con.createStatement();
            String sql = "select * from barang where nama like '%" + cb.txtCari.getText() + "%'";
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
                cb.tbl.addRow(ob);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
   
}
