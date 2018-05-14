/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.view.FrmSuplier;
import java.sql.SQLException;
/**
 *
 * @author LENOVO IP300
 */
public interface controller_pegawai {
    public void Simpan (FrmSuplier spl) throws SQLException;
    public void Ubah (FrmSuplier spl) throws SQLException;
    public void Hapus (FrmSuplier spl) throws SQLException;
    public void Tampil (FrmSuplier spl) throws SQLException;
    public void Bersih (FrmSuplier spl) throws SQLException;
    public void KlikTabel (FrmSuplier spl) throws SQLException;
    public void AutoNomor (FrmSuplier spl) throws SQLException;
}







    