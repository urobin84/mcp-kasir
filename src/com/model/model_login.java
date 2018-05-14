package com.model;

import com.controller.controller_login;
import com.koneksi.UserID;
import com.koneksi.koneksi;
import com.view.FrmLogin;
import com.view.FrmMenuAwal;
import com.view.FrmKasir;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class model_login implements controller_login{

    @Override
    public void Login(FrmLogin lgn) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            Statement st = con.createStatement();
            String sql = "SELECT * FROM user WHERE username='"+lgn.txtusername.getText()+"' AND password = '"+lgn.txtpassword.getText()+"'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                UserID.setUserLogin(lgn.txtusername.getText());
                if(rs.getString(4).equals("admin")){
                    new FrmMenuAwal().show();
                    lgn.dispose();
                }else if(rs.getString(4).equals("kasir")){
                    new FrmKasir().show();
                    lgn.dispose();
                } else {
                    JOptionPane.showMessageDialog(lgn, "Password Salah");
                    Bersih(lgn);
                }
            } else {
                JOptionPane.showMessageDialog(lgn, "Username Belum Terdaftar");
                Bersih(lgn);
                lgn.txtusername.requestFocus();
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void Bersih(FrmLogin lgn) throws SQLException {
        lgn.txtusername.setText(null);
        lgn.txtpassword.setText(null);
        lgn.txtusername.requestFocus();
    }
}
