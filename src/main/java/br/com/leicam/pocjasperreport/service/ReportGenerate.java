package br.com.leicam.pocjasperreport.service;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletOutputStream;

public interface ReportGenerate {
    <T> void toPdf(String jrxml, String exportFileName, Map<String, Object> parameters, List<T> data);
    <T> void toStream(String jrxml, String exportFileName, Map<String, Object> parameters, List<T> data, ServletOutputStream servletOutputStream);
    
}
