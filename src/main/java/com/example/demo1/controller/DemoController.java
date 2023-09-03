package com.example.demo1.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo1.exception.DemoException;
import com.example.demo1.model.DemoModel;
import com.example.demo1.service.DemoService;
@RestController
public class DemoController {
	@Autowired
	RestTemplate restTemplate;
	DemoService d;
	public DemoController(DemoService d) {
		this.d=d;
	}
	Logger logger = Logger.getLogger("DemoController");
		@GetMapping("/client/{name}")
		public String client(@PathVariable("name") String name)
		{
			DemoModel o=new DemoModel();
			o.setId(Integer.toString((int)(Math.random()*100+Math.random()*1000)));
			o.setName(name);
			o.setSalary(Math.random()*1000000);
			o.setAge((int)(Math.random()*100));
			HttpHeaders headers = new HttpHeaders();
		    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		    HttpEntity<DemoModel> entity = new HttpEntity<DemoModel>(o,headers);
			return restTemplate.exchange(
			         "http://localhost:8080/server", HttpMethod.POST, entity, String.class).getBody();
		}
		@PostMapping("/server")
		public String server(@RequestBody DemoModel o)
		{
			logger.info("Processing request");
			if(o.getAge()<20)
				throw new DemoException();
			else {
			d.create(o);
			if(o.getSalary()<700000)
				return o.getName()+" need not pay income tax";
			else
				return o.getName()+" must pay income tax";
		}}
		
		@RequestMapping(value = "/highestSalary")
		   public Double getProductList() {
			List<DemoModel> l=d.getAll();
			Iterator<DemoModel> i=l.iterator();
			Double largest=Double.NEGATIVE_INFINITY;
			while(i.hasNext()) {
				DemoModel obj=i.next();
				if(obj.getSalary()>largest)
					largest=obj.getSalary();
			}
		      return largest;
		   }
		 @RequestMapping(value = "/download", method = RequestMethod.GET) 
		   public ResponseEntity<Object> downloadFile(@RequestParam(name="filename",required=true) String filename) throws IOException  {
			 
		      File file = new File(filename);
		      if(!file.exists()) {
		    	  ResponseEntity<Object> responseEntity = ResponseEntity.internalServerError().body("File not Found");
		    	  return responseEntity;
		      }
		    	  
		      InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		      HttpHeaders headers = new HttpHeaders();
		      
		      headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
		      headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		      headers.add("Pragma", "no-cache");
		      headers.add("Expires", "0");
		      
		      ResponseEntity<Object> 
		      responseEntity = ResponseEntity.ok().headers(headers).contentLength(
		         file.length()).contentType(MediaType.parseMediaType("application/txt")).body(resource);
		      
		      return responseEntity;
		   }
		 
		 @RequestMapping(value = "/upload", method = RequestMethod.POST, 
			      consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
			   public String fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
			 	Path currentPath = Paths.get(System.getProperty("user.dir"));
			 	Path filePath = Paths.get(currentPath.toString(), "Uploads", file.getOriginalFilename());
			 	File convertFile = new File(filePath.toString());
			 	convertFile.createNewFile();
			    FileOutputStream fout = new FileOutputStream(convertFile);
			    fout.write(file.getBytes());
			    fout.close();
			    return "File uploaded successfully";
			   }
		 
}
