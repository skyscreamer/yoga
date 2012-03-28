package org.skyscreamer.yoga.mapper;

import javax.servlet.http.HttpServletResponse;

public class ResultTraverserContext
{
   private String hrefSuffix;
   private HttpServletResponse response;


   public ResultTraverserContext(String hrefSuffix, HttpServletResponse response)
   {
      this.hrefSuffix = hrefSuffix;
      this.response = response;
   }

   public String getHrefSuffix()
   {
      return hrefSuffix;
   }

   public void setHrefSuffix(String hrefSuffix)
   {
      this.hrefSuffix = hrefSuffix;
   }

   public HttpServletResponse getResponse()
   {
      return response;
   }

   public void setResponse(HttpServletResponse response)
   {
      this.response = response;
   }

}
