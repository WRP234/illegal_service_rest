package com.xcs.illegal.p2.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;

import com.xcs.illegal.p2.model.Lawbreaker;
import com.xcs.illegal.p2.request.LawbreakerReq;

public class LawbreakerExt {
	
	private static final Logger log = LoggerFactory.getLogger(LawbreakerExt.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	protected String getSequences(String strSql) {
		log.info("[SQL]  : " + strSql);
		return getJdbcTemplate().queryForObject(strSql, String.class);
	}



}
