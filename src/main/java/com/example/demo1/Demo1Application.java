package com.example.demo1;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@EnableScheduling
@SpringBootApplication
public class Demo1Application implements CommandLineRunner {
   public static void main(String[] args) {
      SpringApplication.run(Demo1Application.class, args);
   }
   @Bean
   public RestTemplate getRestTemplate() {
      return new RestTemplate();
   }
  
   @Override
   public void run(String... arg0) throws Exception {
	   Path currentPath = Paths.get(System.getProperty("user.dir"));
	   Path uploadFolderPath = Paths.get(currentPath.toString(), "Uploads");
	   File uploadFolder=new File(uploadFolderPath.toString());
	   if(!uploadFolder.exists())
		   uploadFolder.mkdirs();
      System.out.println("Starting the server");
   }
}