package com.example.demo.Controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Interface.ActivityInterface;
import com.example.demo.ModelVO.ActivityVo;

@RestController
public class ActivityController {
	@Autowired
	ActivityInterface activityInterface;
	
	@PostMapping("/activity/{key}")
	public void postActivity(@PathVariable String key,@RequestBody ActivityVo activity) throws FileNotFoundException, IOException, ParseException{
		 activityInterface.postActivity(key, activity);
	}
	@GetMapping("/activity/{key}/total")
	public JSONObject getActivity(@PathVariable String key) throws FileNotFoundException, IOException, ParseException, java.text.ParseException {
		return activityInterface.getActivity(key);
	}
}
