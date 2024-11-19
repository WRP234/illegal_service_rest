package com.xcs.illegal.p2.dao;

import java.util.List;

import com.xcs.illegal.p2.model.Lawbreaker;
import com.xcs.illegal.p2.request.LawbreakerReq;


public interface LawbreakerDAO {
	
	public List<Lawbreaker> LawbreakergetData(LawbreakerReq req);

}
