package ssg.com.a.service;

import java.util.List;

import ssg.com.a.dto.FavoriteDto;
import ssg.com.a.dto.NetflixComment;
import ssg.com.a.dto.NetflixContentDto;
import ssg.com.a.dto.NetflixTvDto;

public interface NetflixService {
	
	void insertNetflixcontent(List<NetflixContentDto> contentList);
	void insertNetflixtvcontent(List<NetflixTvDto> contentList);
	NetflixContentDto netflixdetail(Long id);
	NetflixTvDto netflixtvdetail(Long id);
	boolean commentWrite(NetflixComment comment);
	List<NetflixComment> commentList(Long seq);
	Double avg(Long seq);
	boolean commentDelete(NetflixComment comment);
	boolean favorite(FavoriteDto dto);
	List<FavoriteDto> favoriteList(String id);
	boolean favoriteDelete(FavoriteDto dto);
}
