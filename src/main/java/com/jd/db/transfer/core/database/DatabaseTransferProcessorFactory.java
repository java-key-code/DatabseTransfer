package com.jd.db.transfer.core.database;

import com.jd.db.transfer.view.service.TransferTablesService;

/**
 * 功能：
 * 
 * @author miaojundong
 *
 */
public class DatabaseTransferProcessorFactory {
	
	private static DatabaseTransferProcessorFactory instance = new DatabaseTransferProcessorFactory();
	
	public static DatabaseTransferProcessorFactory newInstance() {
		return instance;
	}
	
	public DatabaseTransferProcessor buildDatabaseTransferProcessor(TransferTablesService transferTablesService) {
		return new DatabaseTransferProcessor(transferTablesService);
	}
	
	private DatabaseTransferProcessorFactory() {}
}
