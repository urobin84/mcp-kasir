package com.controller;

import com.view.FrmKasir;
import java.sql.SQLException;


public interface controller_kasir {
    public void Simpan (FrmKasir ksr) throws SQLException;
    public void Ubah (FrmKasir ksr) throws SQLException;
    public void Hapus (FrmKasir ksr) throws SQLException;
    public void Tampil (FrmKasir ksr) throws SQLException;
    public void KlikTabel (FrmKasir ksr) throws SQLException;
    public void Bersih (FrmKasir ksr) throws SQLException;
    public void Tanggal (FrmKasir ksr) throws SQLException;
    public void ScanBarcode (FrmKasir ksr) throws SQLException;
    public void AutoNomor (FrmKasir ksr) throws SQLException;
    public void AutoNomorKode (FrmKasir ksr) throws SQLException;
    public void Total (FrmKasir ksr) throws SQLException;
    public void Kembalian (FrmKasir ksr) throws SQLException;
    public void UbahJumlah (FrmKasir ksr) throws SQLException;
    public void CetakStruk (FrmKasir ksr) throws SQLException;
}
