package com.example.demo.Implementation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.Interface.ActivityInterface;
import com.example.demo.ModelVO.ActivityVo;

@Service
public class ActivityImplementation implements ActivityInterface {
	@Value("${file}")
	private String file;
	
	@Override
	public void postActivity(String key,ActivityVo activities) throws FileNotFoundException, IOException, ParseException {
		activities.setCurrentTime(System.currentTimeMillis());  // Setting current time in vo
		JSONParser parse = new JSONParser();
		JSONObject activityObject = (JSONObject)parse.parse(new FileReader(file)); //reading file
		
		JSONObject result = new JSONObject();
		result.put("value", activities.getValue());
		result.put("currentTime", activities.getCurrentTime());
		if(activityObject.get(key) == null) {
			ArrayList<Object> resultArray = new ArrayList<Object>();
			resultArray.add(result);
			activityObject.put(key, resultArray); //check if activity doesn't exist than create one with current value

		}
		else {
			JSONArray activityInstanceArray = (JSONArray) activityObject.get(key);
			
			 //Remove the element with time past 12 hours
			//This is used in the standard loop method instead of iterator, so as to not get concurrent errors while removing elements from list
			for(int i=0;i<activityInstanceArray.size();i++) {
				JSONObject activityInstcanceObject = (JSONObject)activityInstanceArray.get(i);
				
				long millis = Long.parseLong(activityInstcanceObject.get("currentTime").toString());
				if(System.currentTimeMillis()-millis >43200000) {
					activityInstanceArray.remove(i);
				}
			}
			
			//add the new value
			activityInstanceArray.add(result);
			activityObject.put(key, activityInstanceArray); 
		}
		FileWriter activityFile = new FileWriter(file); 
		activityFile.write(activityObject.toJSONString()); //writing the updated values to file
		activityFile.flush();
		
	}
 
	//In this function we can't remove elements which have time greater than 12 hours because it is a get method and it is not a good idea to delay get method results
	@Override
	public JSONObject getActivity(String key) throws FileNotFoundException, IOException, ParseException, java.text.ParseException {
		int totalActivities = 0;
		JSONObject totalResultantActivities = new JSONObject();
		JSONParser parse = new JSONParser();
		
		JSONObject activityObject = (JSONObject)parse.parse(new FileReader(file));
		JSONArray activityInstanceArray = (JSONArray) activityObject.get(key);  // getting the array with this key
		if(activityInstanceArray==null || activityInstanceArray.size()==0) {
			totalResultantActivities.put("value", totalActivities);
			return totalResultantActivities; 
		}
		for(int i=0;i<activityInstanceArray.size();i++) {
			JSONObject activityInstcanceObject = (JSONObject)activityInstanceArray.get(i);
			
			long millis = Long.parseLong(activityInstcanceObject.get("currentTime").toString());
			if(System.currentTimeMillis()-millis <=43200000) {
				totalActivities += Integer.parseInt(activityInstcanceObject.get("value").toString()); //adding the activities done within 12 hours
			}
		}
		
		
		totalResultantActivities.put("value", totalActivities);
		return totalResultantActivities;
	}
	
}
