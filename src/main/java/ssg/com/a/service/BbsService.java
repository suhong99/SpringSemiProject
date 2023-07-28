package ssg.com.a.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ssg.com.a.dto.BbsDto;
import ssg.com.a.dto.BbsParam;
import ssg.com.a.dto.NetflixComment;
import ssg.com.a.dto.BbsComment;

public interface BbsService {
	List<BbsDto> bbslist(BbsParam param);
	int getAllBbs(BbsParam param);
	
	boolean bbswrite(BbsDto dto);
	
	BbsDto bbsdetail(int seq);
	
	boolean bbsupdate(BbsDto dto) throws Exception;
	int bbsdelete(int seq) throws Exception ;
	
	boolean BbsAnswer(BbsDto dto);
	
	boolean commentWrite(BbsComment comment);
	List<BbsComment> commentList(Long seq);
	boolean commentDelete(BbsComment comment);
	
	void readcount(int seq);
	int readcountSelect(BbsDto dto);					
	void readcountInsert(BbsDto dto);					// 조회수
	
	
	
	void commnetcount(							// 댓글 수 추가
			@Param("seq") int seq,
			@Param("amount") int amount);
}

