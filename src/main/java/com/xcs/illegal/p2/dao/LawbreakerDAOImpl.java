package com.xcs.illegal.p2.dao;


import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.xcs.illegal.p2.constant.Pattern;
import com.xcs.illegal.p2.model.Lawbreaker;
import com.xcs.illegal.p2.request.LawbreakerReq;

@Service
@Transactional
public class LawbreakerDAOImpl extends LawbreakerExt implements LawbreakerDAO{
	
	private static final Logger log = LoggerFactory.getLogger(LawbreakerDAOImpl.class);
	
	@Override
	public List<Lawbreaker> LawbreakergetData(LawbreakerReq req) {
		
		String tempOccurrenceDateFrom  = "";
		String tempOccurrenceDateTo = "";
		
		String tempCastDateFrom   = "";
		String tempCastDateTo = "";
		
		if(!"".equals(req.getOCCURRENCE_DATE_FROM())) {
			tempOccurrenceDateFrom = String.format("%s %s", req.getOCCURRENCE_DATE_FROM(), Pattern.FORMAT_DATE);
		}	
		if(!"".equals(req.getOCCURRENCE_DATE_TO())) {
			tempOccurrenceDateFrom = String.format("%s %s", req.getOCCURRENCE_DATE_TO(), Pattern.FORMAT_DATE);
		}
		
		if(!"".equals(req.getCASE_DATE_FROM())) {
			tempOccurrenceDateFrom = String.format("%s %s", req.getCASE_DATE_FROM(), Pattern.FORMAT_DATE);
		}		
		if(!"".equals(req.getCASE_DATE_TO())) {
			tempOccurrenceDateFrom = String.format("%s %s", req.getCASE_DATE_TO(), Pattern.FORMAT_DATE);
		}
		
		String str = "";
		
		if(req.getOFFICE_CODE() != null && !"".equals(req.getOFFICE_CODE()) && !"00".equals(req.getOFFICE_CODE())) {			
			if(req.getOFFICE_CODE().length() == 6) {
				if("00".equals(req.getOFFICE_CODE().substring(0, 2))) {
					str = " ";
				}else if("0000".equals(req.getOFFICE_CODE().substring(2, 6))) {
					str = " AND (SUBSTR(aa.OFFCODE,1,2) = SUBSTR('"+req.getOFFICE_CODE()+"',1,2)) ";
				}else if("00".equals(req.getOFFICE_CODE().substring(4, 6))) {
					str = " AND (SUBSTR(aa.OFFCODE,1,4) = SUBSTR('"+req.getOFFICE_CODE()+"',1,4)) ";
				}else {
					str = " AND (aa.OFFCODE = '"+req.getOFFICE_CODE()+"') ";
				}			
			}			
		}
		
		StringBuilder sqlBuilder = new StringBuilder()
		.append( "SELECT DISTINCT "+
//		--ข้อมูลรายละเอียดจับกุม
		"aa.TRACK_NO, "+
		"bi.CASE_ID, "+
		"st.TITLE_NAME ||''|| st.FIRST_NAME ||' '|| st.LAST_NAME AS ACCUSER_NAME, "+
		"st.POS_NAME ||''|| st.STAFF_LEVEL AS POSITION_NAME, "+
		"aa.OFFCODE, "+
		"eo.SHORT_NAME, "+
		"aa.OCCURRENCE_DATE, "+
//		--ข้อมูลสถานที่เกิดเหตุ
		"aa.SCENE_NAME, "+
		"aa.SCENE_NO, "+
		"aa.SCENE_MOO, "+
		"aa.SCENE_SOI, "+
		"aa.SCENE_ROAD, "+
		"aa.SCENE_SUBDISTRICT, "+
		"aa.SCENE_DISTRICT, "+
		"aa.SCENE_PROVINCE, "+
		"sdt.SUBDISTRICT_NAME, "+
		"dt.DISTRICT_NAME, "+
		"pv.PROVINCE_NAME, "+
//		--สถานะคดี
		"aa.HAVE_CULPRIT, "+
		"CASE WHEN aa.HAVE_CULPRIT = 'Y' THEN 'มีตัวผู้กระทำผิด' ELSE 'ไม่มีตัวผู้กระทำผิด' END HAVE_CULPRIT_NAME, "+
		"CASE WHEN bi.CASE_LAST =1 THEN 'กรมสรรพสามิต' WHEN bi.CASE_LAST =2 THEN 'ศาล' ELSE 'พนักงานสอบสวน/พนักงานอัยการ' END CASE_LAST, "+
		"CASE WHEN bi.CASE_QUALITY =1 THEN 'เปรียบเทียบปรับ' ELSE 'ส่งฟ้องศาล' END CASE_QUALITY "+
		""+
		"FROM ILLEGAL.APPLICATION_ARREST aa "+
		"LEFT JOIN ILLEGAL.BOOK_IMPEACHMENT bi ON aa.TRACK_NO = bi.TRACK_NO and bi.TRACK_NO is not null "+
		"LEFT JOIN ILLEGAL.APPLICATION_ARREST_INDICTMENT aai ON aa.TRACK_NO = aai.TRACK_NO "+
		"INNER JOIN ILLEGAL.INDICTMENT im ON aai.INDICTMENT_ID = im.INDICTMENT_ID "+
		"LEFT JOIN ILLEGAL.STAFF st ON aa.ACCUSER_IDCARDNO = st.IDCARD_NO "+
		"LEFT JOIN ILLEGAL.ED_OFFICE eo ON aa.OFFCODE = eo.OFFCODE "+
//		"LEFT JOIN ILLEGAL.APPLICATION_ARREST_LAWBREAKER aal ON bi.TRACK_NO = aal.TRACK_NO "+
//		"LEFT JOIN title tt ON tt.title_code = aal.title_code "+
//		"LEFT JOIN title tc ON tc.title_code = aal.company_title_code "+
//		"LEFT JOIN ILLEGAL.COMPARE_CASE cc ON aal.TRACK_NO = cc.TRACK_NO "+
		"LEFT JOIN ILLEGAL.APPLICATION_ARREST_EXHIBIT aae ON aa.TRACK_NO = aae.TRACK_NO "+
		"LEFT JOIN ILLEGAL.DUTY_TABLE dt ON aae.DUTY_CODE = dt.DUTY_CODE "+
//		"INNER JOIN ILLEGAL.LEGISLATION lt ON aa.LEGISLATION_ID =lt.ID "+
		"LEFT JOIN ILLEGAL.SUBDISTRICT sdt ON aa.SCENE_SUBDISTRICT = sdt.SUBDISTRICT_CODE "+
		"LEFT JOIN ILLEGAL.DISTRICT dt ON aa.SCENE_DISTRICT = dt.DISTRICT_CODE "+
		"LEFT JOIN ILLEGAL.PROVINCE pv ON aa.SCENE_PROVINCE = pv.PROVINCE_CODE "+
		"WHERE aa.del_flag = 'N' ");
		
		if(req.getTRACK_NO() != null && !"" .equals(req.getTRACK_NO())) {
			sqlBuilder.append( " AND aa.TRACK_NO LIKE '%"+req.getTRACK_NO()+"%' ");
		}
		
		if(req.getOCCURRENCE_DATE_FROM() != null && !"".equals(req.getOCCURRENCE_DATE_FROM()) && req.getOCCURRENCE_DATE_TO() != null && !"".equals(req.getOCCURRENCE_DATE_TO())) {
		sqlBuilder.append(" AND aa.OCCURRENCE_DATE BETWEEN  to_date(nvl('"+tempOccurrenceDateFrom+"','0001-01-01'),'YYYY-MM-DD') and to_date(nvl('"+tempOccurrenceDateTo+"','9999-12-31'),'YYYY-MM-DD')");
		}
		
		if(req.getCASE_DATE_FROM() != null && !"".equals(req.getCASE_DATE_FROM()) && req.getCASE_DATE_TO() != null && !"".equals(req.getCASE_DATE_TO())) {
			sqlBuilder.append(" AND bi.CASE_DATE BETWEEN  to_date(nvl('"+tempCastDateFrom+"','0001-01-01'),'YYYY-MM-DD') and to_date(nvl('"+tempCastDateTo+"','9999-12-31'),'YYYY-MM-DD')");
		}
		
		if(req.getDUTY_NAME() != null && !"" .equals(req.getDUTY_NAME())) {
			sqlBuilder.append( " AND aae.DUTY_NAME LIKE '"+req.getDUTY_NAME()+"%' ");
		}
		
		if(req.getCASE_LAW_ID() != null && !"" .equals(req.getCASE_LAW_ID())) {
			sqlBuilder.append( " AND im.CASE_LAW_ID '"+req.getCASE_LAW_ID()+"%' ");
		}
		
		sqlBuilder.append("ORDER BY aa.OCCURRENCE_DATE desc");
		
		log.info("[SQL] : "+sqlBuilder.toString());
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<Lawbreaker> dataList = getJdbcTemplate().query(sqlBuilder.toString(), new RowMapper() {
			@Override
			public  Lawbreaker mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				Lawbreaker item = new Lawbreaker();
//				--ข้อมูลรายละเอียดจับกุม
				item.setTRACK_NO(rs.getString("TRACK_NO"));
				item.setCASE_ID(rs.getString("CASE_ID"));
				item.setACCUSER_NAME(rs.getString("ACCUSER_NAME"));
				item.setPOSITION_NAME(rs.getString("POSITION_NAME"));
				item.setOFFCODE(rs.getString("OFFCODE"));
				item.setSHORT_NAME(rs.getString("SHORT_NAME"));
				item.setOCCURRENCE_DATE(rs.getString("OCCURRENCE_DATE"));
//				--ข้อมูลสถานที่เกิดเหตุ
				item.setSCENE_NAME(rs.getString("SCENE_NAME"));
				item.setSCENE_NO(rs.getString("SCENE_NO"));
				item.setSCENE_MOO(rs.getString("SCENE_MOO"));
				item.setSCENE_SOI(rs.getString("SCENE_SOI"));
				item.setSCENE_ROAD(rs.getString("SCENE_ROAD"));
				item.setSCENE_SUBDISTRICT(rs.getString("SCENE_SUBDISTRICT"));
				item.setSCENE_DISTRICT(rs.getString("SCENE_DISTRICT"));
				item.setSCENE_PROVINCE(rs.getString("SCENE_PROVINCE"));
				item.setSUBDISTRICT_NAME(rs.getString("SUBDISTRICT_NAME"));
				item.setDISTRICT_NAME(rs.getString("DISTRICT_NAME"));
				item.setPROVINCE_NAME(rs.getString("PROVINCE_NAME"));
//				--สถานะคดี
				item.setHAVE_CULPRIT(rs.getString("HAVE_CULPRIT"));
				item.setHAVE_CULPRIT_NAME(rs.getString("HAVE_CULPRIT_NAME"));
				item.setCASE_QUALITY(rs.getString("CASE_QUALITY"));
				item.setCASE_LAST(rs.getString("CASE_LAST"));
				
				return item;
			}
			
		});
		return dataList;
	}

}
