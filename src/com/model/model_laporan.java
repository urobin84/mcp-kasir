
package com.model;
import com.controller.controller_laporan;
import com.koneksi.koneksi;
import com.view.FrmLaporanPenjualan;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

    

public class model_laporan implements controller_laporan{
    
    JasperReport jasperReport;
    JasperDesign jasperDesign;
    JasperPrint jasperPrint;
    
    Map<String, Object> parameter = new HashMap<String, Object>();
    
    @Override
    public void Tampilkan(FrmLaporanPenjualan Lp) throws SQLException {
        try {
            parameter.put("tgldari", Lp.tglFrom);
            parameter.put("tglsampai", Lp.tglUntil);
            File file = new File("src/com/report/Lpenjualan.jrxml");
            jasperDesign = JRXmlLoader.load(file);
            jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, koneksi.getKoneksi());
            JasperViewer.viewReport(jasperPrint, false);
            //JasperPrintManager.printReport(jasperPrint, false);
        } catch (Exception e) {
        }
    }
     
}
