package ssg.com.a.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ssg.com.a.dto.BbsDto;
import ssg.com.a.dto.BbsParam;
import ssg.com.a.dto.BbsComment;

@Repository
public class BbsDaoImpl implements BbsDao {

	@Autowired
	SqlSessionTemplate session;

	String ns = "Bbs.";
	
	// 게시판
	@Override
	public List<BbsDto> bbslist(BbsParam param) {
		return session.selectList(ns + "bbslist", param);
	}

	@Override
	public int getAllBbs(BbsParam param) {
		return session.selectOne(ns + "getAllBbs", param);
	}
	
	// 글쓰기
	@Override
	public int bbswrite(BbsDto dto) {
		return session.insert(ns + "bbswrite", dto);
	}
	
	// 상세글보기
	@Override
	public BbsDto bbsdetail(int seq) {
		return session.selectOne(ns + "bbsdetail", seq);
	}
	
	// 글수정
	@Override
	public int bbsupdate(BbsDto dto) throws Exception {
		return session.update(ns + "bbsupdate", dto);
	}
		
	// 글삭제  
	@Override
	public int bbsdelete(int seq) throws Exception {
		return session.delete(ns + "bbsdelete", seq);
	}
	
	// 답글
	@Override
	public void BbsAnswerUpdate(BbsDto dto) {
		session.update(ns + "BbsAnswerUpdate", dto);
	}

	@Override
	public int BbsAnswerInsert(BbsDto dto) {		
		return session.insert(ns + "BbsAnswerInsert", dto);
	}
	// 댓글 
	@Override
	public int commentWrite(BbsComment comment) {
		return session.insert(ns + "commentWriteBoard", comment);
	}


	// 댓글 리스트
	@Override
	public List<BbsComment> commentList(Long seq) {
		return session.selectList(ns + "commentListBoard", seq);
	}
	// 댓글 삭제
	@Override
	public int commentDelete(BbsComment comment) {
		return session.delete(ns + "commentDeleteBoard", comment);
	}
	
	// 조회수
	
	
		@Override
		public int readcountSelect(BbsDto dto) {
			return session.selectOne(ns + "readcountSelect", dto);
		}
		@Override
		public int readcount(int seq) {
			return session.update(ns + "readcount", seq);
		}

		@Override
		public int readcountInsert(BbsDto dto) {
			return session.insert(ns + "readcountInsert", dto);
		}

		
		
		
		
		
		
		
		
		@Override
		public void commnetcount(int seq, int amount) {
			// TODO Auto-generated method stub
			
		}
	
}
