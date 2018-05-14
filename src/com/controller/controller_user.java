package com.controller;

import com.view.FrmUser;
import java.sql.SQLException;


public interface controller_user {
    public void Simpan (FrmUser usr) throws SQLException;
    public void Ubah (FrmUser usr) throws SQLException;
    public void Hapus (FrmUser usr) throws SQLException;
    public void Tampil (FrmUser usr) throws SQLException;
    public void Bersih (FrmUser usr) throws SQLException;
    public void KlikTabel (FrmUser usr) throws SQLException;
    public void AutoNomor (FrmUser usr) throws SQLException;
    
}
