package com.example.demo.Interface;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.example.demo.ModelVO.ActivityVo;

public interface ActivityInterface {
	public void postActivity(String key,ActivityVo activities) throws FileNotFoundException, IOException, ParseException;
	public JSONObject getActivity(String key) throws FileNotFoundException, IOException, ParseException, java.text.ParseException;
}
