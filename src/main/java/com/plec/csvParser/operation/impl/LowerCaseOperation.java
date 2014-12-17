package com.plec.csvParser.operation.impl;

import java.util.List;

import com.plec.csvParser.operation.AbstractSimpleOperation;

public class LowerCaseOperation extends AbstractSimpleOperation {

	@Override
	public void processHeader(List<String> newHeaders, String currentHeader) {
		newHeaders.add(currentHeader);

	}

	@Override
	public void processLines(List<String> operatedCols, String currentCell) {
		operatedCols.add(currentCell.toLowerCase());
	}

}
