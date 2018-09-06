<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
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

									<s:if
										test="0==#session.userId||null==#session.userId || #session.userId.isEmpty()">
										<li><a>用户个人信息</a></li>
									</s:if>
									<s:else>
										<li><a href="user!oneUser.action?userId=${userId }">用户个人信息</a>
										</li>
									</s:else>
									<s:if
										test="0==#session.userId||null==#session.userId || #session.userId.isEmpty()">
										<li><a>套餐变更</a></li>
									</s:if>
									<s:else>
										<li><a href="business!findAllBusiness.action">套餐变更</a></li>
									</s:else>

									<s:if
										test="0==#session.userId||null==#session.userId || #session.userId.isEmpty()">
										<li><a>通话详单</a></li>
									</s:if>
									<s:else>
										<li><a href="bill!findUserBill.action?userId=${userId }">通话详单</a></li>
									</s:else>
									<s:if
										test="0==#session.userId||null==#session.userId || #session.userId.isEmpty()">

										<li><a>话单报表</a></li>
									</s:if>
									<s:else>
										<li><a href="genBillReport.jsp">话单报表</a></li>
									</s:else>
									<s:if
										test="0==#session.userId||null==#session.userId || #session.userId.isEmpty()">

										<li><a>退出系统</a></li>
									</s:if>
									<s:else>
										<li><a href="user!logOff.action">退出系统</a></li>
									</s:else>

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
				style="background-image: url('images/background.jpg');  width: 930px; height: 430px;"
				id="outerslider">

				<!-- SLIDER -->
				<%
					if(null == session.getAttribute("userId") || session.getAttribute("userId")==""){
						 %>

				<%-- 				<s:if test="flag==4 || null==#session.userId || #session.userId.isEmpty()"> --%>
				<form id="loginform" method="post">
					<table style="border: 0;">

						<s:if test="flag==1">
							<h3 style="color: red" align="center">用户名或密码错误!</h3>
						</s:if>
						<s:if test="flag==2">
							<h3 style="color: red" align="center">账户已锁定!</h3>
						</s:if>
						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td align="right"><b>用户电话</b></td>
							<td><input type="text" id="phonenum"
								name="user.userPhonenum" /></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td align="right"><b>用户密码</b></td>
							<td><input type="password" id="password"
								name="user.userPassword" /></td>
						</tr>
						<tr>
							<td colspan="10"><input type="submit" value="登录"
								onclick="checkPhoneNum()" /> <input type="button" value="忘记密码"
								onclick="javascript:window.location.href='forgetpwdStep1.jsp'" />
							</td>

						</tr>
						<td></td>
						<td></td>
						<td></td>
					</table>
				</form>
				<%
} 
				%>
				<%-- 				</s:if> --%>

			</div>
			<!-- END SLIDER -->
		</div>
	</div>
</body>

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

				function checkPhoneNum() {
					var phonenum = document.getElementById("phonenum").value;
					var password = document.getElementById("password").value;
					var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(19[0-9]{1})|(1[0-9]{2}))+\d{8})$/;
					
			    	if(!myreg.test(phonenum)) { 
			    		alert('请输入正确的手机号码！'); 
			    		document.getElementById("phonenum").value = ""; 
			    		return false; 
			    	}else if(password == ""){
			    		alert('密码不可为空！'); 
			    		return false; 
					}else{
			    		document.getElementById("loginform").action = "user!userLogin.action";
						document.getElementById("loginform").submit();
			    	}
				}
				
			</script>
</html>
