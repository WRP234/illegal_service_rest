package com.xcs.illegal.p2.model;

import lombok.Data;

@Data
public class Lawbreaker extends LawbreakerModel {
	
//	--ข้อมูลรายละเอียดจับกุม
	private String TRACK_NO;
	private String CASE_ID;
	private String ACCUSER_NAME;
	private String POSITION_NAME;
	private String OFFCODE;
	private String SHORT_NAME;
	private String OCCURRENCE_DATE;
//	--ข้อมูลสถานที่เกิดเหตุ
	private String SCENE_NAME;
	private String SCENE_NO;
	private String SCENE_MOO;
	private String SCENE_SOI;
	private String SCENE_ROAD;
	private String SCENE_SUBDISTRICT;
	private String SCENE_DISTRICT;
	private String SCENE_PROVINCE;
	private String SUBDISTRICT_NAME;
	private String DISTRICT_NAME;
	private String PROVINCE_NAME;
//	--สถานะคดี
	private String HAVE_CULPRIT;
	private String HAVE_CULPRIT_NAME;
	private String CASE_QUALITY;
	private String CASE_LAST;

}



