package com.example.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
public class DemoApplication {
	@Value("${file}")
	public static String file;
	
	public static void main(String[] args) {
		
		SpringApplication.run(DemoApplication.class, args);
	}
	
	/*
	 * Below function checks if file is present or not (where we are storing data) and if not present than, creates the file
	 */
@Bean
	public static void checkFileForStore() throws FileNotFoundException, IOException, ParseException {
		if(file==null) {
			file="activities.json";
		}
		File activityFile = new File(file);
	    if(!activityFile.exists()) {
	    	try (FileWriter newFile = new FileWriter(activityFile)) {	
	    		newFile.write("{}");
	    		newFile.flush();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
}
