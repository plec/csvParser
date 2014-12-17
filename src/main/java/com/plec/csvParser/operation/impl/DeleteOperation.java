package com.plec.csvParser.operation.impl;

import java.util.List;

import com.plec.csvParser.operation.AbstractSimpleOperation;

public class DeleteOperation extends AbstractSimpleOperation {

	@Override
	public void processHeader(List<String> newHeaders, String currentHeader) {
		//nothing to do because we delete this cel

	}

	@Override
	public void processLines(List<String> operatedCols, String currentCell) {
		//nothing to do because we delete this cel
	}

}
