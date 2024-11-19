package com.xcs.illegal.p2.request;

import lombok.Data;

@Data
public class LawbreakerReq {
	
	private String TRACK_NO;
	private String OFFICE_CODE;
	private String OCCURRENCE_DATE_FROM;
	private String OCCURRENCE_DATE_TO;
	private String CASE_DATE_FROM;
	private String CASE_DATE_TO;
	private String DUTY_NAME;
	private String CASE_LAW_ID;

}