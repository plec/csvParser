package com.plec.csvParser.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping(value = "/api/")
@SessionAttributes(value={"fileContent", "fileContent2", "fileHeaders", "initialFileHeader", "operations", "nbLines"})
public class CsvApiController {
	private static final Logger LOGGER = Logger.getLogger(CsvApiController.class);
//	private static final int MAX_LINE = 20;
	
	@RequestMapping(value="/data", method=RequestMethod.GET)
    public @ResponseBody Map<String, Object> getData(Model model){
		List<List<String>> data = (List<List<String>>) model.asMap().get("fileContent2");
		List<String> headers = (List<String>) model.asMap().get("fileHeaders");
		Map<String, Object> json = new HashMap<String, Object>();
		List<Map<String, String>> jsonLines = new ArrayList<Map<String,String>>();
		json.put("total", model.asMap().get("nbLines"));
		for (List<String> line : data) {
			int currentCel = 0;
			Map<String,String> currentJsonLine = new HashMap<String,String>();
			currentJsonLine.put("id", Integer.toString(currentCel));
			while (currentCel < headers.size()) {
				currentJsonLine.put(headers.get(currentCel), line.get(currentCel));
				currentCel++;
			}
			jsonLines.add(currentJsonLine);
		}
		json.put("rows", jsonLines);
	    return json;
    }
}
