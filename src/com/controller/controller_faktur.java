package com.controller;

import com.view.FrmFaktur;
import java.sql.SQLException;


public interface controller_faktur {
    public void Simpan (FrmFaktur fkt) throws SQLException;
    public void Hapus (FrmFaktur fkt) throws SQLException;
    public void Tampil (FrmFaktur fkt) throws SQLException;
    public void Bersih(FrmFaktur fkt) throws SQLException;
    public void KlikTabel (FrmFaktur fkt) throws SQLException;
    public void Tanggal (FrmFaktur fkt) throws SQLException;
    public void AutoNomor (FrmFaktur fkt) throws SQLException;
}
