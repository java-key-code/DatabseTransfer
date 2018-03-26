package com.jd.db.transfer.view.controller;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jd.db.transfer.core.database.DatabaseTransferProcessor;
import com.jd.db.transfer.core.database.DatabaseTransferProcessorFactory;
import com.jd.db.transfer.core.pojo.DatabaseBean;
import com.jd.db.transfer.core.xml.XmlDatabaseConfig;
import com.jd.db.transfer.view.controller.support.ResultModel;
import com.jd.db.transfer.view.service.TransferTablesService;

/**
 * 功能：
 * 
 * @author miaojundong
 *
 */
@Controller
@RequestMapping("/tables")
public class DatabaseTablesController {
	
	@Resource
	private XmlDatabaseConfig config;
	@Resource
	private TransferTablesService transferTablesService;
	
	@RequestMapping("/move")
	public @ResponseBody ResultModel showDb(String fromDb, String toDb, String tablesName) throws SQLException {
		List<String> tablesList = null;
		if(null != tablesName && !tablesName.equals("")) {
			tablesList = Arrays.asList(tablesName.split(","));
		} else {
			return new ResultModel(false, "没有被迁移数据库表");
		}
		
		DatabaseBean databaseFrom = config.getDatabase(fromDb);
		if(null == databaseFrom) {
			return new ResultModel(false, "被迁移数据库不存在");
		}
		
		DatabaseBean databaseTo = config.getDatabase(toDb);
		if(null == databaseTo) {
			return new ResultModel(false, "目的地数据库不存在");
		}
		
		DatabaseTransferProcessor processor = DatabaseTransferProcessorFactory.newInstance().buildDatabaseTransferProcessor(transferTablesService);
		
		try {
			processor.initialize(databaseFrom, databaseTo, tablesList);
			processor.start();
			//processor.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResultModel(true, null, tablesList);
	}
}
