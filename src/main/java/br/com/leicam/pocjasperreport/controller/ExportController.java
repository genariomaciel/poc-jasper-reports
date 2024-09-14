package br.com.leicam.pocjasperreport.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.leicam.model.Bairro;
import br.com.leicam.model.DataBean;
import br.com.leicam.pocjasperreport.service.ReportGenerate;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/pdf")
public class ExportController {
    private final ReportGenerate service;

    public ExportController(ReportGenerate service) {
        this.service = service;
    }

    @GetMapping("/compile/{jrxmlFileName}")
    public void compile(@PathVariable String jrxmlFileName) {
        service.toCompileFile(jrxmlFileName.replace("_","/"));
    }

    @PostMapping("/create/{jasperFileName}/{pdfFileName}")
    public void create(@PathVariable String jasperFileName, @PathVariable String pdfFileName, @RequestBody List<DataBean> data) throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        
        service.toPdf(jasperFileName.replace("_","/"), pdfFileName.replace("_","/"), parameters, data);

    }

    // @ResponseBody
    @GetMapping("/download")
    public void download(HttpServletResponse response)  throws IOException{
        Map<String, Object> parameters = new HashMap<>();

        var templateFileName = "src/main/resources/templates/list-template-model.jrxml";
        var exportFileName = "export/list-template-model.pdf";
        var list = new ArrayList<DataBean>();
        ServletOutputStream servletOutputStream = response.getOutputStream();
        service.toStream(templateFileName, exportFileName, parameters, list, servletOutputStream);
        
        response.setContentType("application/pdf");
        servletOutputStream.flush();
        servletOutputStream.close();

    }

    @GetMapping
    public ResponseEntity<?> get() {
        return ResponseEntity.ok().body(getDataBeans());
    }

    private List<DataBean> getDataBeans() {
        var bairros = new ArrayList<Bairro>();
        bairros.add(new Bairro("Vila Curuca"));
        
        var list = new ArrayList<DataBean>();
        list.add(new DataBean("Brasil", "Santo André", bairros));
        return list;
    }
    
}
