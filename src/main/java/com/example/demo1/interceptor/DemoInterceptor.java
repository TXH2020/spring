package com.example.demo1.interceptor;
import java.io.PrintWriter;
import java.time.LocalTime;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class DemoInterceptor implements HandlerInterceptor {
	static LocalTime start;
	static LocalTime end;
   @Override
   public boolean preHandle
      (HttpServletRequest request, HttpServletResponse response, Object handler) 
      throws Exception {
	   String parts[]=request.getRequestURI().split("/");
	   if(!parts[1].equals("client"))
		   return true;
      String name=parts[2];
      for(int i=0;i<name.length();i++)
    	  if(!Character.isLetter(name.charAt(i))) {
    		  System.out.println("Request doesnt have a valid name");
    		  PrintWriter out=response.getWriter();
    		  out.println("Request doesnt have a valid name");
    		  return false;}
      start=LocalTime.now();
      System.out.println("Recieved request from "+name);
      System.out.println("Sent for processing");
      return true;
   }
   @Override
   public void postHandle(HttpServletRequest request, HttpServletResponse response, 
      Object handler, ModelAndView modelAndView) throws Exception {
      System.out.println("Response sent successfully");
      end=LocalTime.now();
   }
   @Override
   public void afterCompletion
      (HttpServletRequest request, HttpServletResponse response, Object 
      handler, Exception exception) throws Exception {
      System.out.println("Request-Response cycle completed. Start Time="+start+",End Time="+end);
   }
}