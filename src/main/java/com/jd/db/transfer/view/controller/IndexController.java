package com.jd.db.transfer.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 功能：
 * 
 * @author miaojundong
 *
 */
@Controller
public class IndexController {

	/**
	 * index.jsp
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	
}
