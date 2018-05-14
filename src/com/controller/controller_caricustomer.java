/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.view.FrmBarang;
import java.sql.SQLException;


public interface controller_caricustomer {
    public void Simpan(FrmBarang cus) throws SQLException;
    public void Ubah (FrmBarang cus) throws SQLException;
    public void Hapus(FrmBarang cus) throws SQLException;
    public void Tampil (FrmBarang cus) throws SQLException;
    public void Bersih(FrmBarang cus) throws SQLException;
    public void KlikTabel (FrmBarang cus) throws SQLException;
    public void Combo (FrmBarang cus) throws SQLException;
    public void Combo2 (FrmBarang cus) throws SQLException;
    public void Cari (FrmBarang cus) throws SQLException;
}