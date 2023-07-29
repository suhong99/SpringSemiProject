package ssg.com.a.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.protobuf.Message;
import com.mysql.cj.Session;

import ssg.com.a.dto.MemberDto;
import ssg.com.a.service.MemberService;
import ssg.com.a.util.SHA256;

@Controller
public class MemberController {

	@Autowired
	MemberService service;
		
	@RequestMapping(value = "login.do")
	public String login() {
		System.out.println("MemberController login() " + new Date());
		return "login";
	}
	
	@GetMapping("regi.do")
	public String regi() {
		System.out.println("MemberController regi() " + new Date());
		return "regi";
	}
	@GetMapping("findPW.do")
	public String findPW() {
		System.out.println("MemberController findPW() " + new Date());
		return "findPW";
	}
	
	@ResponseBody
	@PostMapping("idcheck.do")
	public String idcheck(String id) {
		System.out.println("MemberController idcheck() " + new Date());
		boolean isS = service.idcheck(id);
		String msg = "YES";
		if(isS == true) {
			msg = "NO";
		}
		
		return msg;
	}
	
	@ResponseBody
	@PostMapping("regiAf.do")
	public String regiAf(MemberDto mem) {
		System.out.println("MemberController regiAf() " + new Date());
		String rawPW = mem.getPwd();
	    String hashedPW = SHA256.encodeSha256(rawPW);
	    mem.setPwd(hashedPW);
		boolean isS = service.addmember(mem);
		String regimsg = "MEMBER_YES";
		if(isS == false) {
			regimsg = "MEMBER_NO";
		}
		
		return regimsg;
	}	
	
	@ResponseBody
	@PostMapping("loginAf.do")
	public String login(MemberDto mem, HttpServletRequest request) {
		System.out.println("MemberController login() " + new Date());
		String rawPW = mem.getPwd();
	    String hashedPW = SHA256.encodeSha256(rawPW);
	    mem.setPwd(hashedPW);
		MemberDto dto = service.login(mem);
		String loginmsg = "LOGIN_NO";
		if(dto != null) {
			request.getSession().setAttribute("login", dto);	// session에 저장			
			loginmsg = "LOGIN_YES";
		}
		
		return loginmsg;
	}
	
	@ResponseBody
	@RequestMapping(value = "logout.do")
	public void logout(HttpServletRequest request) {
		System.out.println("MemberController logout() " + new Date());		
		request.getSession().removeAttribute("login");		
	}
	
	@ResponseBody
	@PostMapping("kakaoLogin.do")
	public String kakaoLogin(MemberDto mem, HttpServletRequest request) {
		System.out.println("MemberController kakaoLogin() " + new Date());
		MemberDto dto = service.kakaoLogin(mem);
		String kakaomsg = "LOGIN_NO";
		if(dto != null) {
			request.getSession().setAttribute("login", dto);	// session에 저장			
			kakaomsg = "LOGIN_YES";
		}	else if(dto==null){
			dto = service.kakaoAddmember(mem);
			kakaomsg = "MEMBER_NO";
			if(dto != null) {
				request.getSession().setAttribute("login", dto);			
				kakaomsg = "MEMBER_YES";
			}
		}
		
		return kakaomsg;
	}
	
	@ResponseBody
	@PostMapping("findMember.do")
	public String findMember(MemberDto mem, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("MemberController findMember() " + new Date());
		
		MemberDto dto= service.findMember(mem);
		if(dto != null) {
			Random r = new Random();
			int num = r.nextInt(999999); // 랜덤난수설정
			session.setAttribute("email", dto.getEmail());

			String setfrom = "ivedot@naver.com"; // naver 
			String tomail = dto.getEmail(); //받는사람
			String title = "[넷리뷰] 비밀번호변경 인증 이메일 입니다"; 
			String content = System.getProperty("line.separator") + "안녕하세요 회원님" + System.getProperty("line.separator")
					+ "넷리뷰 비밀번호찾기(변경) 인증번호는 " + num + " 입니다." + System.getProperty("line.separator"); // 
			
			
		}
		
		return "emailChk";
	}
}













