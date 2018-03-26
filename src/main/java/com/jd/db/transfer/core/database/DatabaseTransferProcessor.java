package com.jd.db.transfer.core.database;

import java.sql.Connection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jd.db.transfer.core.database.listener.ProcessorListener;
import com.jd.db.transfer.core.pojo.DatabaseBean;
import com.jd.db.transfer.core.pojo.DatabaseConfig.DatabaseResource;
import com.jd.db.transfer.view.service.TransferTablesService;

/**
 * 功能：
 * 
 * @author miaojundong
 *
 */
public class DatabaseTransferProcessor  implements Runnable {
	
	private static Logger logger = LoggerFactory.getLogger(DatabaseTransferProcessor.class);
	
	private ExecutorService executorService = null;
	private ConnectionPool connectionPool = null;
	private List<String> tablesName;
	
	private boolean initialized = false;
    private boolean started = false;
    
    private Thread thread = null;
    
    private TransferTablesService transferTablesService;
    
    private ProcessorListener processorListener;
    
    DatabaseTransferProcessor(TransferTablesService transferTablesService) {
    	this.transferTablesService = transferTablesService;
    }
    
    
    public void initialize(DatabaseBean databaseFrom, DatabaseBean databaseTo, List<String> tablesName) throws Exception {
        if (initialized)
        	throw new Exception("DatabaseTransferProcessor already Initialized");

        this.initialized = true;

        if(null == tablesName || tablesName.size() <= 0) {
        	throw new Exception("Transfer tables is empty");
		}
        
        processorListener = new ProcessorListener(this, tablesName.size());
        try {
        	executorService = Executors.newFixedThreadPool(tablesName.size() < 5 ? tablesName.size() : 5);
        	connectionPool = ConnectionPool.newInstance(databaseFrom, databaseTo);
        	connectionPool.init(tablesName.size() < 5 ? tablesName.size() : 5);
        	ConnectionPoolManager.setConnectionPool(connectionPool);
        	this.tablesName = tablesName;
        } catch (Exception e) {
        	 throw new Exception(e);
        }
    }
    
	public void start() throws Exception {
		if (started) {
    		throw new Exception("DatabaseTransferProcessor already Started");
    	}
		started = true;
		
		threadStart();
        
        logger.info("------DatabaseTransferProcessor is Started-------");
    }
	
	public void stop() throws Exception {
        if (!started) {
        	throw new Exception("DatabaseTransferProcessor not Started");
        }
        
        started = false;
        connectionPool.destory();
        logger.info("------DatabaseTransferProcessor is Stopped-------");
    }
	
	public void run() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ProcessorDelegate processorDelegate  = ProcessorDelegate.newInstance(this);
		processorDelegate.handle(tablesName);
    }

    private void threadStart() {
        thread = new Thread(this, "DatabaseTransferProcessor");
        thread.setDaemon(true);
        thread.start();
    }

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public ConnectionPool getConnectionPool() {
		return connectionPool;
	}

	public TransferTablesService getTransferTablesService() {
		return transferTablesService;
	}


	protected ProcessorListener getProcessorListener() {
		return processorListener;
	}
}

class ProcessorDelegate {
	private static final ProcessorDelegate instance = new ProcessorDelegate();
	
	private DatabaseTransferProcessor databaseTransferProcessor;
	
	static ProcessorDelegate newInstance(DatabaseTransferProcessor databaseTransferProcessor) {
		instance.databaseTransferProcessor = databaseTransferProcessor;
		return instance;
	}
	
	public void handle(List<String> tablesName) {
		for (String tableName : tablesName) {
			databaseTransferProcessor.getExecutorService().submit(new ThreadProcessor(databaseTransferProcessor, tableName));
		}
	}
	
	private ProcessorDelegate() {}
}

class ThreadProcessor implements Callable<Integer> {
	private DatabaseTransferProcessor databaseTransferProcessor;
	private String tableName;
	
	public ThreadProcessor(DatabaseTransferProcessor databaseTransferProcessor, String tableName) {
		this.databaseTransferProcessor = databaseTransferProcessor;
		this.tableName = tableName;
	}
	
	@Override
	public Integer call() throws Exception {
		ConnectionPool connectionPool = databaseTransferProcessor.getConnectionPool();
		Connection databaseFrom = connectionPool.getConnectionOfFrom();
		Connection databaseTo= connectionPool.getConnectionOfTo();
		
		TransferTablesService transferTablesService = databaseTransferProcessor.getTransferTablesService();
		Long count = transferTablesService.getMoveDataCount(databaseFrom, tableName);
		System.out.println(count);
		Integer result = transferTablesService.moveTablesData(databaseFrom, databaseTo, tableName);
		connectionPool.recycle(databaseFrom, DatabaseResource.FROM);
		connectionPool.recycle(databaseTo, DatabaseResource.TO);
		databaseTransferProcessor.getProcessorListener().updateProgress(tableName);
		return result;
	}
}