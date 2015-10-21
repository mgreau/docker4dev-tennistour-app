package com.mgreau.tennistour.batch;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.chunk.AbstractItemReader;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class DatasATPReader extends AbstractItemReader {

	private BufferedReader reader;

	@Inject
	private JobContext jobContext;

	private Integer recordNumber;
	
	private String prefix;

	@Override
	public void open(Serializable checkpoint) throws Exception {

		JobOperator jobOperator = BatchRuntime.getJobOperator();
		Properties jobParameters = jobOperator.getParameters(jobContext
				.getExecutionId());
		String resourceName = (String) jobParameters.get("csvDatasFileName");
		System.out
		.println("[DatasATPReader] resourceName : " + resourceName);
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream(resourceName);
		reader = new BufferedReader(new InputStreamReader(inputStream));

		if (checkpoint != null){
			recordNumber = (Integer) checkpoint;
			System.out
			.println("[DatasATPReader]checkpoint :" +checkpoint);
		}
		else
			recordNumber =  0;
		for (int i = 1; i < recordNumber; i++) { // Skip upto recordNumber
			reader.readLine();
		}
		System.out
				.println("[DatasATPReader] Opened csv file for reading from record number: "
						+ recordNumber);
	}

	@Override
	public String readItem() {
		try {
			String line = reader.readLine();
			if (line == null) return "";
			else if (line.startsWith("ATP") || line.startsWith("WTA")){
				prefix = line.substring(0,3)+",";
				return "";
			}
			return prefix + line;
		} catch (IOException ex) {
			Logger.getLogger(DatasATPReader.class.getName()).log(Level.SEVERE,
					null, ex);
			return null;
		}
	}

	@Override
	public Serializable checkpointInfo() throws Exception {
		return recordNumber;
	}
}
