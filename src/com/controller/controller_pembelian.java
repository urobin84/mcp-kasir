package com.controller;

import com.view.FrmPembalian;
import java.sql.SQLException;


public interface controller_pembelian {
    public void Simpan (FrmPembalian pmbl) throws SQLException;
    public void Hapus (FrmPembalian pmbl) throws SQLException;
    public void Tampil (FrmPembalian pmbl) throws SQLException;
    public void KlikTabel (FrmPembalian pmbl) throws SQLException;
    public void Bersih (FrmPembalian pmbl) throws SQLException;
    public void Combo1 (FrmPembalian pmbl) throws SQLException;
    public void Combo2 (FrmPembalian pmbl) throws SQLException;
    public void Combo3 (FrmPembalian pmbl) throws SQLException;
    public void Combo4 (FrmPembalian pmbl) throws SQLException;
    public void AutoNomor (FrmPembalian pmbl) throws SQLException;
}
