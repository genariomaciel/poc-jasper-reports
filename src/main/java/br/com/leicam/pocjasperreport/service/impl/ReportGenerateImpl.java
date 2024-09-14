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
    public <T> void toPdf(String jasperFileName, String exportFileName, Map<String, Object> parameters, List<T> data) {
        
        String printFileName = null;
        try {
            JasperPrint jasperPrint = processar(jasperFileName, exportFileName, parameters, data);
            JasperExportManager.exportReportToPdfFile(jasperPrint,  exportFileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> void toStream(String jasperFileName, String exportFileName, Map<String, Object> parameters, List<T> data, ServletOutputStream servletOutputStream) {
        
        JasperPrint jasperPrint = processar(jasperFileName, exportFileName, parameters, data);
        try {
            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        } catch (JRException e) {
            e.printStackTrace();
            throw new RuntimeException("Problema oa gerar o relatorio.");
        }

    }

    private <T> JasperPrint processar(String jasperFileName, String exportFileName, Map<String, Object> parameters, List<T> data) {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(data);
        JasperPrint jasperPrint = null;
        try {
            
            // jasperPrint =  JasperFillManager.fillReport(jasperReport, parameters, beanCollectionDataSource);
            jasperPrint =  JasperFillManager.fillReport(jasperFileName, parameters, beanCollectionDataSource);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (jasperPrint == null) {
            throw new RuntimeException("Problema ao gerar o relatorio.");
        }
        return jasperPrint; 
    }

    @Override
    public JasperReport toCompile(String jrxmlFileName) {
        JasperReport jasperReport = null;
        try {
            jasperReport = JasperCompileManager.compileReport(jrxmlFileName);
        } catch (JRException e) {
            System.out.println(e.getMessage());
        }
        return jasperReport;
    }

    @Override
    public void toCompileFile(String jrxmlFileName) {
        try {
            JasperCompileManager.compileReportToFile(jrxmlFileName);
        } catch (JRException e) {
            System.out.println(e.getMessage());
        }
    }
}
