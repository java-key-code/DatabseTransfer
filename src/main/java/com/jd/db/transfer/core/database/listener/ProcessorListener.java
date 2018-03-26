package com.jd.db.transfer.core.database.listener;

import java.util.ArrayList;
import java.util.List;

import com.jd.db.transfer.core.database.DatabaseTransferProcessor;

public class ProcessorListener {
	private final int totalTablesCount;
	private final List<String> handledTables = new ArrayList<String>();
	
	private DatabaseTransferProcessor databaseTransferProcessor;
	
	public ProcessorListener(DatabaseTransferProcessor databaseTransferProcessor, int totalTablesCount) {
		this.totalTablesCount = totalTablesCount;
		this.databaseTransferProcessor = databaseTransferProcessor;
	}
	
	
	public synchronized void updateProgress(String tableName) {
		handledTables.add(tableName);
		if(handledTables.size() >= totalTablesCount) {
			try {
				databaseTransferProcessor.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
