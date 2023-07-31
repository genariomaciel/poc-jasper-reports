package br.com.leicam.pocjasperreport.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.com.leicam.pocjasperreport.dto.DataBean;
import br.com.leicam.pocjasperreport.dto.DataBeanList;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;

public class JasperReportFill {
   public void export() {
      String sourceFileName = "C:\\Users\\genario\\source\\projetos\\poc-jasper-report\\src\\main\\resources\\templates\\report.jrxml";
      String printFileName = null;
      DataBeanList DataBeanList = new DataBeanList();
      ArrayList<DataBean> dataList = DataBeanList.getDataBeanList();
      JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);

      Map parameters = new HashMap();
      try {
         printFileName = JasperFillManager.fillReportToFile(sourceFileName, parameters, beanColDataSource);
         if (printFileName != null) {
            /**
             * 1- export to PDF
             */
            JasperExportManager.exportReportToPdfFile(printFileName,
               "sample_report.pdf");

            /**
             * 2- export to HTML
             */
            JasperExportManager.exportReportToHtmlFile(printFileName,
               "sample_report.html");

            /**
             * 3- export to Excel sheet
             */
            JRXlsExporter exporter = new JRXlsExporter();

            exporter.setParameter(JRExporterParameter.INPUT_FILE_NAME,
               printFileName);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
               "sample_report.xls");

            exporter.exportReport();
         }
      } catch (JRException e) {
         e.printStackTrace();
      }
   }
}

