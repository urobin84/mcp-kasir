package com.controller;

import com.view.FrmBarang;
import java.sql.SQLException;

public interface controller_customer {
    public void Simpan(FrmBarang brg) throws SQLException;
    public void Ubah (FrmBarang brg) throws SQLException;
    public void Hapus(FrmBarang brg) throws SQLException;
    public void Tampil (FrmBarang brg) throws SQLException;
    public void Bersih(FrmBarang brg) throws SQLException;
    public void KlikTabel (FrmBarang brg) throws SQLException;
    public void Combo (FrmBarang brg) throws SQLException;
    public void Combo2 (FrmBarang brg) throws SQLException;
    public void Cari (FrmBarang brg) throws SQLException;
}
