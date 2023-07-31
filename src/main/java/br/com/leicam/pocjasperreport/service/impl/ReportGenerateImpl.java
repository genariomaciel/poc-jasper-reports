package br.com.leicam.pocjasperreport.service.impl;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.leicam.pocjasperreport.service.ReportGenerate;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ReportGenerateImpl implements ReportGenerate {

    @Override
    public <T> void toPdf(String jrxml, String exportFileName, Map<String, Object> parameters, List<T> data) {
        
        String printFileName = null;
        try {
            JasperPrint jasperPrint = processar(jrxml, exportFileName, parameters, data);
            JasperExportManager.exportReportToPdfFile(jasperPrint,  exportFileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> void toStream(String jrxml, String exportFileName, Map<String, Object> parameters, List<T> data, ServletOutputStream servletOutputStream) {
        
        JasperPrint jasperPrint = processar(jrxml, exportFileName, parameters, data);
        try {
            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        } catch (JRException e) {
            e.printStackTrace();
            throw new RuntimeException("Problema oa gerar o relatorio.");
        }

    }

    private <T> JasperPrint processar(String jrxml, String exportFileName, Map<String, Object> parameters, List<T> data) {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(data);
        JasperPrint jasperPrint = null;
        try {
            
            JasperReport jasperReport = compileReport(jrxml);
            jasperPrint =  JasperFillManager.fillReport(jasperReport, parameters, beanCollectionDataSource);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (jasperPrint == null) {
            throw new RuntimeException("Problema oa gerar o relatorio.");
        }
        return jasperPrint; 
    }

    private JasperReport  compileReport(String jrxml) throws JRException {
        return JasperCompileManager.compileReport(jrxml);
    }

    
}
