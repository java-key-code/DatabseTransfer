package com.jd.db.transfer.view.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jd.db.transfer.core.common.JdbcUtil;
import com.jd.db.transfer.core.common.SQLExecutor;
import com.jd.db.transfer.core.pojo.DatabaseBean;
import com.jd.db.transfer.core.xml.XmlDatabaseConfig;

@Controller
@RequestMapping("/db")
public class DatabaseController {

	@Resource
	private XmlDatabaseConfig config;

	@RequestMapping("/selectFromDb")
	public @ResponseBody List<String> selectFromDb() throws SQLException {
		return config.getAllSourceDbFullName();
	}

	@RequestMapping("/selectToDb")
	public @ResponseBody List<String> selectToDb() throws SQLException {
		return config.getAllTargetDbFullName();
	}

	@RequestMapping("/showDb")
	public @ResponseBody DatabaseBean showDb(String fullName) throws SQLException {
		DatabaseBean database = config.getDatabase(fullName);
		return database;
	}
	
	@RequestMapping("/showTables")
	public String showTables(Model model, String dbFrom, String dbTo) throws SQLException {
		DatabaseBean database = config.getDatabase(dbFrom);
		if(null != database) {
			Connection conn = JdbcUtil.getDbConnection(database);
			model.addAttribute("list", SQLExecutor.newInstance(conn).showTables());
		}
		model.addAttribute("dbFrom", dbFrom);
		model.addAttribute("dbTo", dbTo);
		return "tables";
	}
}
