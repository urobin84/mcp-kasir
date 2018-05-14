package com.controller;

import com.view.FrmSuplier;
import java.sql.SQLException;


public interface controller_suplier {
    public void Simpan (FrmSuplier spl) throws SQLException;
    public void Ubah (FrmSuplier spl) throws SQLException;
    public void Hapus (FrmSuplier spl) throws SQLException;
    public void Tampil (FrmSuplier spl) throws SQLException;
    public void Bersih (FrmSuplier spl) throws SQLException;
    public void KlikTabel (FrmSuplier spl) throws SQLException;
    public void AutoNomor (FrmSuplier spl) throws SQLException;
}
