package ssg.com.a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssg.com.a.dao.BbsDao;
import ssg.com.a.dto.BbsDto;
import ssg.com.a.dto.BbsParam;
import ssg.com.a.dto.BbsComment;

@Service
public class BbsServiceImpl implements BbsService{
	
	@Autowired
	BbsDao dao;

	@Override
	public List<BbsDto> bbslist(BbsParam param) {		
		return dao.bbslist(param);
	}

	@Override
	public int getAllBbs(BbsParam param) {
		return dao.getAllBbs(param);
	}

	@Override
	public boolean bbswrite(BbsDto dto) {
		return dao.bbswrite(dto)>0?true:false;
	}

	@Override
	public BbsDto bbsdetail(int seq) {
		return dao.bbsdetail(seq);
	}
	
	@Override
	public boolean BbsAnswer(BbsDto dto) {
		dao.BbsAnswerUpdate(dto);		
		return dao.BbsAnswerInsert(dto)>0?true:false;
	}
	
	// 글수정
	
	@Override
	public boolean bbsupdate(BbsDto dto) throws Exception {
		return dao.bbsupdate(dto)>0?true:false;	
	}
		
	 // 글삭제
	 @Override 
	 public int bbsdelete(int seq) throws Exception { 
		 return dao.bbsdelete(seq);	
	 }
	 
	 // 댓글
	@Override
	public boolean commentWrite(BbsComment comment) {		
		return dao.commentWrite(comment)>0?true:false;
	}

	@Override
	public List<BbsComment> commentList(Long seq) {		
		return dao.commentList(seq);
	}
	
	@Override
	public boolean commentDelete(BbsComment comment) {
		return dao.commentDelete(comment)>0?true:false;
	}
	
	// 게시글 확인한 아이디 DB저장. 조회수 증가방지
	@Override
	public void readcount(int seq) {
		dao.readcount(seq);	
	}

	@Override
	public int readcountSelect(BbsDto dto) {
		return dao.readcountSelect(dto);	
	}

	@Override
	public void readcountInsert(BbsDto dto) {
		dao.readcountInsert(dto);
		
	}	
		
		
		
		
		
		
	@Override
	public void commnetcount(int seq, int amount) {
		dao.commnetcount(seq, amount);
	}
/*
	@Transactional
	@Override
	public int commentcount(BbsDto dto) {
		log.info("resiger..." + dto);
		updatecommentcount(dto.getSeq(), 1);
		return mapper.insert(dto);
		}
	
	@Transactional
	@Override
	public int remove(int seq) {
		log.info("remove..." + seq);
		updatecommentcount(dto.getSeq(), -1);
		return mapper.delete(seq);
	}
	*/

	

	

}
