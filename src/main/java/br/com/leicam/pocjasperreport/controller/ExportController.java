package br.com.leicam.pocjasperreport.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.leicam.pocjasperreport.dto.DataBean;
import br.com.leicam.pocjasperreport.dto.DataBeanList;
import br.com.leicam.pocjasperreport.dto.HtmlBean;
import br.com.leicam.pocjasperreport.service.ReportGenerate;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;

@RestController
@RequestMapping("/api/pdf")
public class ExportController {
    private final ReportGenerate service;

    public ExportController(ReportGenerate service) {
        this.service = service;
    }

    @PostMapping("/create")
    public void create(@RequestBody List<DataBean> data) throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        

        var templateFileName = "src/main/resources/templates/html-template-model.jrxml";
        var exportFileName = "export/html-template-model.pdf";

        List<HtmlBean> list = new ArrayList<>();
        list.add(new DataBeanList().getHtml("/Users/genario/Documents/_Jeova/quadro/Quadro/NVMC-12-2020.html"));

        service.toPdf(templateFileName, exportFileName, parameters, list);



    }

    // @ResponseBody
    @GetMapping("/download")
    public void download(HttpServletResponse response)  throws IOException{
        Map<String, Object> parameters = new HashMap<>();

        var templateFileName = "src/main/resources/templates/report.jrxml";
        var exportFileName = "export/report.pdf";
        var list = new DataBeanList().getDataBeanList();
        ServletOutputStream servletOutputStream = response.getOutputStream();
        service.toStream(templateFileName, exportFileName, parameters, list, servletOutputStream);
        
        response.setContentType("application/pdf");
        servletOutputStream.flush();
        servletOutputStream.close();

    }

    @GetMapping
    public ResponseEntity<?> get() {
        var list = new DataBeanList().getDataBeanList();

        return ResponseEntity.ok().body(list);
    }
    
}
