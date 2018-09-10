<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.vo.*"%>
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
<style type="text/css">
td, th {
	
}

tr {
	
}
</style>
<link rel="stylesheet" href="styles/skeleton.css" type="text/css" />
<link rel="stylesheet" href="styles/style.css" type="text/css" />
<link rel="stylesheet" href="styles/flexslider.css" type="text/css" />
<link rel="stylesheet" href="styles/color.css" type="text/css" />
<link rel="stylesheet" href="styles/layout.css" type="text/css" />


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
									<li><a href="index.jsp">首页</a></li>
									<li class="current"><a
										href="user!oneUser.action?userId=${userId }">用户个人信息</a></li>
									<li><a href="business!findAllBusiness.action">套餐变更</a></li>
									<li><a href="bill!findUserBill.action?userId=${userId }">通话详单</a></li>
									<li><a href="genBillReport.jsp">话单报表</a></li>
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

				<!-- SLIDER -->
				<%
					Users user = (Users) request.getAttribute("oneUser");
				%>
				<table align="center">
					<tr>
						<td>用户号码</td>
						<td><%=user.getUserPhonenum()%></td>
						<td rowspan="4"><img src="<%=user.getUserAvarurl()%>"
							width="150" height="150"></td>
					</tr>
					<tr>
						<td>用户真实姓名</td>
						<td><%=user.getUserRealname()%></td>
					</tr>
					<tr>
						<td>归属地</td>
						<td><%=user.getUserAddress()%></td>
					</tr>
					<tr>
						<td>电子邮件</td>
						<td><%=user.getUserEmail()%></td>
					</tr>
					<tr>
						<td>余额</td>
						<td><a href="user!recharge.action?userId=${userId}"><%=user.getUserBalance()%></a></td>
						<td width="300">
							<form id="uploadform" action="" method="post"
								enctype="multipart/form-data">
								<s:if test="imageflag==1">
									<h6 style="color: red" align="center">Upload failed!</h6>
								</s:if>
								<s:if test="imageflag==2">
									<h6 style="color: red" align="center">Upload successfully!</h6>
								</s:if>
								请选择要上传的图片： <input type="file" id="uploadImage"
									name="uploadImage" onchange="checkFileExt(this.value)" />
							</form>
						</td>
					</tr>
					<tr>
						<td>套餐类型</td>
						<td><%=user.getUserBusiness()%></td>
						<td><input type="button" value="修改头像" onclick="uploadDeal()" /></td>
					</tr>
					<tr>
						<td>
							业务办理
						</td>
						<td>
							<a href="vaservice!getVA.action" >增值业务办理入口</a>
						</td>
					</tr>

				</table>
			</div>
		</div>
	</div>
</body>

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

					//文件上传检查
					function checkFileExt(filename) {
						var flag = false; //状态
						var arr = [ "jpg", "png" ];
						//取出上传文件的扩展名
						var index = filename.lastIndexOf(".");
						var ext = filename.substr(index + 1);
						//循环比较
						for (var i = 0; i < arr.length; i++) {
							if (ext == arr[i]) {
								flag = true; //一旦找到合适的，立即退出循环
								break;
							}
						}
						//条件判断
						if (flag) {
							return false;
						} else {
							document.getElementById("uploadImage").value = "";
							alert("文件名不合法，请上传以jpg或png结尾的图片文件！");
						}
					}
					
					function uploadDeal(){
						if(document.getElementById("uploadImage").value != ""){
							document.getElementById("uploadform").action = "user!userAvarUpload.action";
							document.getElementById("uploadform").submit();    
						}else{
							alert("请选择要上传的图片");
						}
						return false;
					}
				</script>
</html>
