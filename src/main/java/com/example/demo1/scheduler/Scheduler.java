package com.example.demo1.scheduler;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

//Java Cron Expression, Fixed Delay are other techniques
@Component
public class Scheduler {
	static int seconds;
	@Autowired
	RestTemplate restTemplate;
   @Scheduled(fixedRate = 100000)
   public void fixedRateSch() {
	   HttpHeaders headers = new HttpHeaders();
	   headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	   HttpEntity <String> entity = new HttpEntity<String>(headers);
	   Double sal=restTemplate.exchange("http://localhost:8080/highestSalary", HttpMethod.GET, entity, Double.class).getBody();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
      Date now = new Date();
      String strDate = sdf.format(now);
      System.out.println("Highest salary is:"+sal+" at time " + strDate);
   }
}
