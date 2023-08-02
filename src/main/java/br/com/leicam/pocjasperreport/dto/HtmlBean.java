package br.com.leicam.pocjasperreport.dto;

import java.io.Serializable;

public class HtmlBean implements Serializable{
   private String htmlCode;

   public String getHtmlCode() {
      return htmlCode;
   }

   public void setHtmlCode(String htmlCode) {
      this.htmlCode = htmlCode;
   }

}
