package com.plec.csvParser.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.plec.csvParser.Constants;
import com.plec.csvParser.bean.Data;
import com.plec.csvParser.bean.Operation;
import com.plec.csvParser.bean.OperationName;
import com.plec.csvParser.operation.CsvOperationsProcessor;

@Controller
@RequestMapping(value = "/csv/")
@SessionAttributes(value={"fileContent", "fileContent2", "fileHeaders", "initialFileHeader", "operations", "nbLines"})
public class CsvController {
	private static final Logger LOGGER = Logger.getLogger(CsvController.class);
	private static final int MAX_LINE = 20;
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String init() {
		return "index";
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
    public /*@ResponseBody*/ String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("charset") String charset, Model model){
		try {
			 List<String> fileContent = new ArrayList<String>();
			 List<String> fileHeaders = new ArrayList<String>();
			 String initialFileHeader = null;
		        if (file != null ) {	                 
	                System.out.println("Saving file: " + file.getOriginalFilename());
	                 
	                if (!file.getOriginalFilename().equals("")) {
	                	InputStream inputStream = file.getInputStream();
	                	DataInputStream in = new DataInputStream(inputStream);
	                	BufferedReader br = new BufferedReader(new InputStreamReader(in, charset));
	        			String currentLine;
	        			int i = 0;
	        			LOGGER.debug("############## File content #############");
	        			while ((currentLine = br.readLine()) != null /*&& i < MAX_LINE*/)   {
	        				LOGGER.debug(currentLine);
	        				fileContent.add(currentLine);
	        				i++;
	        			}
	        			fileHeaders = fileContent.subList(0, 1);
	        			initialFileHeader = fileHeaders.get(0);
	        			fileContent = fileContent.subList(1, fileContent.size());
	        			model.addAttribute("initialFileHeader", initialFileHeader);
	        			model.addAttribute("fileContent", fileContent);
	        			model.addAttribute("nbLines", i);
	        			LOGGER.debug("############## end #############");
	                }
		        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	    // returns to the view "Result"
	    return "content";
    }
	@RequestMapping(value="/update", method=RequestMethod.GET)
    public String update(String split, Model model){
		List<String> data = (List<String>) model.asMap().get("fileContent");
		String headers = (String) model.asMap().get("initialFileHeader");
		List<List<String>> returnedData = new ArrayList<List<String>>();
		List<String> fileHeaders = new ArrayList<String>();
		fileHeaders = Arrays.asList(StringUtils.splitPreserveAllTokens(headers, split));
		for (String s : data) {
			String[] splitedLine = StringUtils.splitPreserveAllTokens(s, split);
			returnedData.add(Arrays.asList(splitedLine));
		}
		model.addAttribute("lineSize", fileHeaders.size());
		model.addAttribute("fileHeaders", fileHeaders);
		model.addAttribute("fileContent2", returnedData);
	    // returns to the view "Result"
	    return "contentData";
    }
	@RequestMapping(value="/operation", method=RequestMethod.GET)
    public String operation(@RequestParam Map<String,String> allRequestParams, Model model){
		String operation = allRequestParams.get("operation");
		LOGGER.info("operation : " + operation);
		Data dataSet = new Data();
		dataSet.setContent((List<List<String>>) model.asMap().get("fileContent2"));
		dataSet.setHeaders((List<String>) model.asMap().get("fileHeaders"));
		HashMap<String, Object> operationParameters = new HashMap<String, Object>();
		OperationName operationName = null;
		if ("splitColumn".equalsIgnoreCase(operation)) {
			operationName = OperationName.Split;
			String colonneToSplit = allRequestParams.get("colonneToSplit");
			String colonneToSplit_separator = allRequestParams.get("colonneToSplit_separator");
			String newColName1 = allRequestParams.get("newColName1");
			String newColName2 = allRequestParams.get("newColName2");
			
			checkParameters(colonneToSplit, colonneToSplit_separator, newColName1, newColName2);

			operationParameters.put(Constants.OPERATION_PARAMETER_SPLIT_ID_COLUMN, Integer.parseInt(colonneToSplit));
			operationParameters.put(Constants.OPERATION_PARAMETER_SPLIT_SEPARATOR, colonneToSplit_separator);
			operationParameters.put(Constants.OPERATION_PARAMETER_SPLIT_NEW_NAME_COL1, newColName1);
			operationParameters.put(Constants.OPERATION_PARAMETER_SPLIT_NEW_NAME_COL2, newColName2);
			
		} else if ("joinColumn".equalsIgnoreCase(operation)) {
			operationName = OperationName.Join;
			String col1ToJoin = allRequestParams.get("colonne1ToJoin");
			String col2ToJoin = allRequestParams.get("colonne2ToJoin");
			String colToJoinSeparator = allRequestParams.get("colonneToJoin_separator");
			String newName = allRequestParams.get("newColName");

			checkParameters(col1ToJoin, col2ToJoin, newName, colToJoinSeparator);

			operationParameters.put(Constants.OPERATION_PARAMETER_JOIN_ID_COLUMN1, Integer.parseInt(col1ToJoin));
			operationParameters.put(Constants.OPERATION_PARAMETER_JOIN_ID_COLUMN2, Integer.parseInt(col2ToJoin));
			operationParameters.put(Constants.OPERATION_PARAMETER_JOIN_NEW_NAME_COL, newName);
			operationParameters.put(Constants.OPERATION_PARAMETER_JOIN_SEPARATOR, colToJoinSeparator);
		} else if ("copyColumn".equalsIgnoreCase(operation)) {
			operationName = OperationName.Copy;
			String colToCopy = allRequestParams.get("colonneToCopy");
			String newColName = allRequestParams.get("newColName");
			if (StringUtils.isEmpty(newColName) ||
					StringUtils.isEmpty(colToCopy)) {
						throw new RuntimeException("champs obligatoires");
					}
			int colId = Integer.parseInt(colToCopy);
			operationParameters.put(Constants.OPERATION_PARAMETER_COL_ID, colId);
			operationParameters.put(Constants.OPERATION_PARAMETER_COPY_NEW_NAME_COL, newColName);			
		} else if ("upperCase".equalsIgnoreCase(operation)) {
			operationName = OperationName.UpperCase;
			String colid = allRequestParams.get("colonneToProcessSimple");
			operationParameters.put(Constants.OPERATION_PARAMETER_COL_ID, colid);
		} else if ("lowerCase".equalsIgnoreCase(operation)) {
			operationName = OperationName.LowerCase;
			String colid = allRequestParams.get("colonneToProcessSimple");
			operationParameters.put(Constants.OPERATION_PARAMETER_COL_ID, colid);
		} else if ("firstLetterUpperCase".equalsIgnoreCase(operation)) {
			operationName = OperationName.FirstLetterUpperCase;
			String colid = allRequestParams.get("colonneToProcessSimple");
			operationParameters.put(Constants.OPERATION_PARAMETER_COL_ID, colid);
		} else if ("firstLetterEachWordUpperCase".equalsIgnoreCase(operation)) {
			operationName = OperationName.FirstLetterEachWordUpperCase;
			String colid = allRequestParams.get("colonneToProcessSimple");
			operationParameters.put(Constants.OPERATION_PARAMETER_COL_ID, colid);
		} else if ("delete".equalsIgnoreCase(operation)) {
			operationName = OperationName.Delete;
			String colid = allRequestParams.get("colonneToProcessSimple");
			operationParameters.put(Constants.OPERATION_PARAMETER_COL_ID, colid);
		} else if ("deleteMotif".equalsIgnoreCase(operation)) {
			operationName = OperationName.DeleteMotif;
			String colid = allRequestParams.get("colonneToProcessSimple");
			String motif = allRequestParams.get("motif");
			operationParameters.put(Constants.OPERATION_PARAMETER_COL_ID, Integer.parseInt(colid));
			operationParameters.put(Constants.OPERATION_PARAMETER_CHANGE_MOTIF_SEARCH, motif);
		} else if ("replaceMotif".equalsIgnoreCase(operation)) {
			operationName = OperationName.ReplaceMotif;
			String colid = allRequestParams.get("colonneToProcessSimple");
			String motif = allRequestParams.get("motif");
			String replace = allRequestParams.get("replace");
			operationParameters.put(Constants.OPERATION_PARAMETER_COL_ID, Integer.parseInt(colid));
			operationParameters.put(Constants.OPERATION_PARAMETER_CHANGE_MOTIF_SEARCH, motif);
			operationParameters.put(Constants.OPERATION_PARAMETER_CHANGE_MOTIF_REPLACE, replace);
		} else if ("addMotif".equalsIgnoreCase(operation)) {
			operationName = OperationName.addMotif;
			String colid = allRequestParams.get("colonneToProcessSimple");
			String addMotif = allRequestParams.get("addMotif");
			String whereToAdd = allRequestParams.get("whereToAddMotif");
			operationParameters.put(Constants.OPERATION_PARAMETER_COL_ID, Integer.parseInt(colid));
			operationParameters.put(Constants.OPERATION_PARAMETER_ADD_MOTIF, addMotif);			
			operationParameters.put(Constants.OPERATION_PARAMETER_WHERE_TO_ADD_MOTIF, whereToAdd);			
		} else if ("changeColName".equalsIgnoreCase(operation)) {
			operationName = OperationName.ChangeColName;
			String colid = allRequestParams.get("colonneToProcessSimple");
			String newName = allRequestParams.get("newColName");
			operationParameters.put(Constants.OPERATION_PARAMETER_COL_ID, Integer.parseInt(colid));
			operationParameters.put(Constants.OPERATION_PARAMETER_COPY_NEW_NAME_COL, newName);			
		}
		applyOperation(dataSet, operationName, operationParameters);
		model.addAttribute("fileHeaders", dataSet.getHeaders());
		model.addAttribute("fileContent2", dataSet.getContent());
	    return "contentData";
    }
	@RequestMapping(value="/exportCSV", method=RequestMethod.GET)
	 public void exportCSV(@RequestParam Map<String,String> allRequestParams, Model model, HttpServletResponse response) {
		try {
			 String separator = allRequestParams.get("separator");
			 String withQuotes = allRequestParams.get("withQuotes");
			 List<List<String>> lines = (List<List<String>>) model.asMap().get("fileContent2");
			 List<String> headers = (List<String>) model.asMap().get("fileHeaders");
	
			 response.setContentType("data:text/csv;charset=utf-8"); 
			 response.setHeader("Content-Disposition","attachment; filename=\"exportCSV.csv\"");
			 OutputStream resOs= response.getOutputStream();  
			    OutputStream buffOs= new BufferedOutputStream(resOs);   
			    OutputStreamWriter outputwriter = new OutputStreamWriter(buffOs);  
	
				 for (String header : headers) {
					 outputwriter.write(header);
					 outputwriter.write(separator);
				 }
				 outputwriter.write("\n");
				 for (List<String> currentLine : lines) {
					 for (String col : currentLine) {
						 outputwriter.write(col);
						 outputwriter.write(separator);
					 }
					 outputwriter.write("\n");
				 }
			       
			    outputwriter.flush();   
			    outputwriter.close();		 
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	 }
	@RequestMapping(value="/data", method=RequestMethod.GET)
    public @ResponseBody List<Map<String, String>> getData(Model model){
		List<List<String>> data = (List<List<String>>) model.asMap().get("fileContent2");
		List<String> headers = (List<String>) model.asMap().get("fileHeaders");
		List<Map<String, String>> jsonLines = new ArrayList<Map<String,String>>();
		int currentLine = 0;
		for (List<String> line : data) {
			int currentCel = 0;
			Map<String,String> currentJsonLine = new HashMap<String,String>();
			currentJsonLine.put("lineNumber", Integer.toString(currentLine));
			while (currentCel < headers.size()) {
				String currentValue = line.get(currentCel);
				if (currentValue == null) {
					currentValue = "";
				}
				currentJsonLine.put(headers.get(currentCel), currentValue);
				currentCel++;
			}
			currentLine++;
			jsonLines.add(currentJsonLine);
		}
	    return jsonLines;
    }

	private void applyOperation(Data dataSet, OperationName operationName, Map<String, Object> parameters) {
		Operation op = new Operation(operationName);
		op.setParameters(parameters);
		CsvOperationsProcessor.applyOneOperation(op, dataSet);
	}
	private void checkParameters(String ... args) {
		for (String s : args) {
			if(StringUtils.isEmpty(s)) {
				LOGGER.warn("At least one mandatory parameter is empty");
				throw new RuntimeException("At least one mandatory parameter is empty");
			}
		}
	}


}
