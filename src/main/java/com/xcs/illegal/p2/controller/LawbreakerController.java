package com.xcs.illegal.p2.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.xcs.illegal.p2.constant.Message;
import com.xcs.illegal.p2.dao.LawbreakerDAO;
import com.xcs.illegal.p2.model.Lawbreaker;
import com.xcs.illegal.p2.request.LawbreakerReq;
import com.xcs.illegal.p2.response.MessageResponse;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class LawbreakerController {
	
	private static final Logger log = LoggerFactory.getLogger(LawbreakerController.class);
	
	@Autowired
    private LawbreakerDAO lawbreakerDAO;
	
	@PostMapping(value = "/LawbreakergetData" ,produces = "application/json")
    public ResponseEntity<?> getLawbreakerData(@RequestBody LawbreakerReq req) {

        log.info("============= Start API LawbreakergetData ================");
        MessageResponse msg = new MessageResponse();
        List<Lawbreaker> res;
        boolean checkType = true;

        try {
            res = lawbreakerDAO.LawbreakergetData(req);

            if (res == null) {
                msg.setIsSuccess(Message.FALSE);
                msg.setMsg("No data found for the given request.");
                return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            checkType = false;
            msg.setIsSuccess(Message.FALSE);
            msg.setMsg("Error: " + e.getMessage());
            return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("============= End API LawbreakergetData ================");
        return new ResponseEntity<>(checkType ? res : msg, HttpStatus.OK);
    }
}
