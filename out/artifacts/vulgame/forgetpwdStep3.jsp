<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<!--[if lt IE 7 ]><html class="ie ie6" lang="en"> <![endif]-->
<!--[if IE 7 ]><html class="ie ie7" lang="en"> <![endif]-->
<!--[if IE 8 ]><html class="ie ie8" lang="en"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!-->
<html lang="en">
<!--<![endif]-->
<head>

<!-- Basic Page Needs
  ================================================== -->
<meta charset="utf-8" />
<title>模拟计费系统</title>
<meta name="robots" content="index, follow" />
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta name="author" content="" />

<!-- Mobile Specific Metas
  ================================================== -->
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />


<!-- CSS
  ================================================== -->
<link rel="stylesheet" href="styles/skeleton.css" />
<link rel="stylesheet" href="styles/style.css" />
<link rel="stylesheet" href="styles/flexslider.css" />
<link rel="stylesheet" href="styles/color.css" />
<link rel="stylesheet" href="styles/layout.css" />



<!--[if lt IE 9]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->

<!-- Favicons
	================================================== -->
<link rel="shortcut icon" href="images/favicon.ico" />
<link rel="apple-touch-icon" href="images/apple-touch-icon.png" />
<link rel="apple-touch-icon" sizes="72x72"
	href="images/apple-touch-icon-72x72.png" />
<link rel="apple-touch-icon" sizes="114x114"
	href="images/apple-touch-icon-114x114.png" />


<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<script type="text/javascript">
	function checkPass() {
		var pass = document.getElementById("user.userPassword").value;
		var passRepet = document.getElementById("passwordRepet").value;
		
		if (pass.length < 6 || pass.length > 16) {
			alert("请输入6-16位密码");
			//让控件获得光标
			document.getElementById("user.userPassword").focus();
			return;
		}
		if(checkStrength(pass) < 2){
			alert("密码过于简单，至少包含两种字符");
			//让控件获得光标
			document.getElementById("user.userPassword").focus();
			return;
		}if(pass !== passRepet){
			alert("两次密码输入不一致");
			//让控件获得光标
			document.getElementById("passwordRepet").focus();
			return;
		}
		
		document.getElementById("form").submit();
	}
	
	
	//check pass strength
	function bitTotal(num){
		modes = 0;
		for(i = 0; i < 4; i++){
			if(num & 1)
				modes++;
			num >>>= 1;
		}
		return modes;
	}
	
	function charMode(iN){
		if(iN >= 48 && iN <=57)
			return 1;
		if(iN >= 65 && iN <=90)
			return 2;
		if(iN >= 97 && iN <=122)
			return 4;
		else
			return 8;
		
	}
	
	function checkStrength(sPWD){
		modes = 0;
		for(i = 0; i < sPWD.length; i++){
			modes |=charMode(sPWD.charCodeAt(i));
		}
		
		return bitTotal(modes);
	}
	
	
	
	
</script>


<base href="<%=basePath%>">



</head>


<body>
	<div id="bodychild">
		<div id="outercontainer">

			<!-- HEADER -->
			<div id="outerheader">
				<div class="container">
					<header id="top" class="twelve columns">
						<div id="logo" class="three columns alpha">
							<img src="images/newlogo.png" alt="" />
						</div>

						<section id="navigation" class="twelve columns">
							<nav id="nav-wrap">
								<ul id="topnav" class="sf-menu">
									<li class="current"><a href="index.jsp">首页</a></li>
									<li><a href="user!oneUser.action?userId=${userId }">用户个人信息</a></li>
									<li><a
										href="business!findAllBusiness.action">套餐变更</a></li>
									<li><a href="bill!findUserBill.action?userId=${userId }">通话详单</a></li>
									<li><a href="user!logOff.action">退出系统</a></li>
								</ul>
								<!-- topnav -->
							</nav>
							<!-- nav -->
							<div class="clear"></div>
						</section>
						<div class="clear"></div>
					</header>
				</div>
			</div>
			<!-- END HEADER -->
			<div
				style="background: #EFEFEF;width: 930px; height: 430px;"
				id="outerslider">
				<br />
				<div style="text-align: center">
					<img src="images/forgetpwd/step3.png">
				</div>
				<!-- SLIDER -->
				<s:form id="form" action="user!findpwdThree.action" method="post">
					<br /></br>
					<div style="text-align: center">
						<input type="password" id="user.userPassword" name="user.userPassword"
							placeholder="请输入6-16位密码，至少包含两种字符" maxlength="11"
							style="font-size: 12px; width: 400px; height: 35px;" /><br/> </br>
							<input type="password" id="passwordRepet" name="passwordRepet"
							placeholder="请再次确认新密码" maxlength="11"
							style="font-size: 12px; width: 400px; height: 35px;" /><br /> 

						<input type="button" name="next_step"  onclick="checkPass()" value="下一步" 
						style="position:relative;top:30px" />
					</div>

				</s:form>
				<!-- END SLIDER -->


				<script type="text/javascript" src="js/jquery-1.6.4.min.js"></script>

				<!-- jQuery Superfish -->
				<script type="text/javascript" src="js/hoverIntent.js"></script>
				<script type="text/javascript" src="js/superfish.js"></script>
				<script type="text/javascript" src="js/supersubs.js"></script>

				<!-- jQuery Flexslider -->
				<script type="text/javascript" src="js/jquery.flexslider-min.js"></script>

				<!-- jQuery Twitter -->
				<script type="text/javascript" src="js/jquery.tweetable.js"></script>

				<!-- Custom Script -->
				<script type="text/javascript" src="js/custom.js"></script>

				<script type="text/javascript">
					jQuery(window).load(function() {
						jQuery('.flexslider').flexslider({
							animation : "fade", //String: Select your animation type, "fade" or "slide"
							directionNav : false, //Boolean: Create navigation for previous/next navigation? (true/false)
							controlNav : true
						//Boolean: Create navigation for paging control of each clide? Note: Leave true for manualControls usage
						});
					});
				</script>
</html>


