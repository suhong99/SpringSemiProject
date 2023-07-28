<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<style type="text/css">
.authHeader {
	color :white;
	padding: 15px;
}
.authContainer {
	display: flex;
	justify-content: center;
	align-items: center;
	padding: 10px;
	width: 500px;	
}

.innerContainer{
	width: 100%;
	height: 100%;
	
}

.authInput{
	width: 100%;
	height: 50px;
	padding: 10px;
	margin-bottom: 20px;
	border-radius: 8px;
	font-family: 15px;
	font-weight: 700;
}


.sec-100{
	width: 100%;
	height: 100%;
	padding: 20px;
}
</style>
<script type="text/javascript" src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script src="http://lab.alexcican.com/set_cookies/cookie.js" type="text/javascript" ></script>

</head>
<body>
<div class="authContainer">
<section class="sec-100">

  <div class="container-fluid" >
		<h1 class="authHeader"> 로그인</h1>
  		
      <form>
        <!-- ID input -->
        <div>
            <input class="authInput" type="text" id="loginId" name="loginId" placeholder="아이디" />            
        </div>

        <!-- Password input -->
        <div class="form-outline mb-3">
            <input class="authInput" type="password" id="loginPwd" name="loginPwd" placeholder="비밀번호" />            
        </div>

        <div>
            <button type="button" class="btn btn-primary btn-lg" style="background-color: #e50914; border: none; width: 100%" onclick="login()">Login</button>
            <!-- Checkbox -->
            <div style="display: flex; flex-direction:row; justify-content: space-around; align-items: flex-end; margin: 10px 0px 15px 0px">
                <div>
                    <input class="form-check-input me-2" id="chk_save_id" style="accent-color :black;" type="checkbox" value="" id="form2Example3" />
                    <label class="form-check-label" style="color: #b3b3b3; font-size: 13px;"  for="form2Example3">
                        로그인 정보 저장
                    </label>
                </div>
                <p class="small fw-bold mt-2 pt-1 mb-0">
                    <a href="#" class="link-danger" style="color: #b3b3b3; font-size: 13px;"  onclick="toggleForm('regi')">회원가입</a>
                </p>
                <p class="small fw-bold mt-2 pt-1 mb-0">
                    <a href="#" class="link-danger" style="color: #b3b3b3; font-size: 13px;"  onclick="toggleForm('findPW')">비밀번호찾기</a>
                </p>
            </div>
            <!-- 카카오로그인  -->
           <div style="width: 100%; display: flex; justify-content: center;margin-top: 10px">
				<img onclick="kakaoLogin()" alt="카카오 로그인" src="./images/kakao_login.png" style="width:250px;">
            </div>
        </div>
    </form>
        
  </div>  
</section>
</div>
<script type="text/javascript">
    Kakao.init('822d3eac697dcc5530d84f54ee93e535');
    function kakaoLogin() {
        Kakao.Auth.login({
            success: function (response) {
                Kakao.API.request({
                    url: '/v2/user/me',
                    success: function (response) {
                         // console.log(JSON.stringify(response))
                         //	console.log(response.id);
                         // console.log(response.properties.nickname);
                         // console.log(response.kakao_account.email);
                          const formData = {
					        id: response.id+'',
					        pwd : response.id+'',
					        name: response.properties.nickname,
					        email : response.kakao_account.email,
					        auth : 10
					   		 };
                          console.log(formData);
                          $.ajax({
              		        url: "kakaoLogin.do",
              		        type: "post",
              		        data: formData,
              		        async : false,
              		        success: function(response) {
              		        	 const kakaoMsg = response.trim();
              		             if (kakaoMsg === "MEMBER_NO") {
              		                 alert("회원가입에 실패하셨습니다.");
              		             } else if (kakaoMsg === "LOGIN_YES") {
              		                 alert("로그인 되었습니다.");
              		                 location.reload();
              		             } else if(kakaoMsg ==="MEMBER_YES"){
              		            	 alert("회원가입 되었습니다.")
              		                 location.reload();
              		             }else {
              		                 alert("로그인 처리 중 에러가 발생했습니다.");
              		             }
              		        },
              		        error: function() {
              		            alert("서버와 통신 중 에러가 발생했습니다.");
              		        }
              		    });
                       	 
                    },
                    fail: function (error) {
                        alert(JSON.stringify(error))
                    },
                })
            },
            fail: function (error) {
                alert(JSON.stringify(error))
            },
        })
    }
</script>

<script type="text/javascript">
		function login(event) {
			// event.preventDefault();
		    // 폼 데이터 가져오기
		    const formData = {
		        id: $("#loginId").val(),
		        pwd: $("#loginPwd").val()
		    };
		    // Ajax로 서버에 POST 요청 보내기
		    $.ajax({
		        url: "loginAf.do",
		        type: "post",
		        data: formData,
		        success: function(response) {
		        	console.log(response);
		        	 const loginMsg = response.trim();
		             if (loginMsg === "LOGIN_NO") {
		                 alert("아이디나 비밀번호를 확인해 주십시오");
		             } else if (loginMsg === "LOGIN_YES") {
		                 alert("로그인 되었습니다.");
		                 location.reload();
		             } else {
		                 alert("로그인 처리 중 에러가 발생했습니다.");
		             }
		        },
		        error: function() {
		            alert("서버와 통신 중 에러가 발생했습니다.");
		        }
		    });
		}



/*
	session : server에 저장. login 정보.	Object
	cookie : client에 저장. id를 저장. pw 저장	String 
*/

let user_id = $.cookie("user_id");
if(user_id != null){	// 저장한 아이디가 있음
	$("#loginId").val( user_id );
	$("#chk_save_id").prop("checked", true);	// 첵크박스에 첵크가 되게 해준다
}

$("#chk_save_id").click(function(){
	// alert('click');
	// alert( $("#chk_save_id").is(":checked") );
	
	if( $("#chk_save_id").is(":checked") == true ){	// id를 저장 -> cookie
		
		if( $("#loginId").val().trim() == "" ){	// 빈문자열이었을 경우
			alert('id를 입력해 주십시오');
			$("#chk_save_id").prop("checked", false);
		}else{	
			// cookie 저장
			$.cookie("user_id", $("#loginId").val().trim(), { expires:7, path:'./' });
		}		
		
	}else{	// cookie에 저장하지 않음(삭제)
		
		$.removeCookie("user_id", { path:'./' });
	}	
});

</script>

</body>
</html>