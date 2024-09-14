package br.com.leicam.pocjasperreport.service;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletOutputStream;
import net.sf.jasperreports.engine.JasperReport;

public interface ReportGenerate {
    <T> void toPdf(String jasperFileName, String exportFileName, Map<String, Object> parameters, List<T> data);
    <T> void toStream(String jasperFileName, String exportFileName, Map<String, Object> parameters, List<T> data, ServletOutputStream servletOutputStream);
    JasperReport toCompile(String jrxmlFileName);
    void toCompileFile(String jrxmlFileName);
}
