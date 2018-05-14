package com.controller;

import com.view.FrmCariBarang;
import java.sql.SQLException;


public interface controller_caribarang {
    public void Tampil (FrmCariBarang cb) throws SQLException;
    public void KlikTabel (FrmCariBarang cb) throws SQLException;
    public void Cari (FrmCariBarang cb) throws SQLException;
}
