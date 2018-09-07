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
									<li><a href="index.jsp">首页</a></li>
									<li><a href="user!oneUser.action?userId=${userId }">用户个人信息</a></li>
									<li class="current"><a
										href="business!findAllBusiness.action">套餐变更</a></li>
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
					List list = (List) request.getAttribute("businessList");
					Users userlist = (Users) request.getAttribute("userList");
					String token = (String) request.getAttribute("token");
					if (userlist != null) {
						if (userlist.getUserBusiness() != null) {
				%>
				<BR>
				<h5 align="center">

					用户：

					<%=userlist.getUserPhonenum()%>您当前的套餐为<b><%=userlist.getUserBusiness()%></b>


				</h5>

				<%
					} else {
				%>
				<h5 style="color: red;" align="center">您还没有套餐!</h5>
				<%
					}
					}
				%>

				<form id="changeForm" action="" method="post">
					<table>
						<h5 align="center">请选择套餐</h5>
						<div class="table_con">
							<table width="100%" cellspacing="1" cellpadding="0" border="0"
								class="contentTab" style="margin-top: 10px;">
								<tbody>
									<tr class="writebg" style="margin: 5px 0" height="30">
										<th align="center" class="bluebg">序号</th>
										<th align="center" class="bluebg">业务名称</th>
										<th align="center" class="bluebg">业务说明</th>
										<th align="center" class="bluebg">是否更换</th>
									</tr>
									<%
										if (list != null && list.size() > 0) {
											for (int i = 0; i < list.size(); i++) {
												Business vo = (Business) list.get(i);
									%>

									<tr class="writebg">
										<td align="center" height="30"><%=i%></td>
										<td align="center"><%=vo.getBusinessName()%></td>
										<td align="center"><%=vo.getBusinessInfo()%></td>

										<td align="center"><a href="javascript:void(0)"
											onclick="changeBus('<%=vo.getBusinessName()%>')">更换套餐</a></td>
									</tr>

									<%
										}
										}
									%>
									<input type="hidden" id="userBusiness" name="userBusiness"
										value="" />
									<input type="hidden" name="token" value="<%=token%>" />
								</tbody>
							</table>
							</form>
						</div>
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

	function changeBus(userBusiness) {
		document.getElementById("userBusiness").value = userBusiness;
		document.getElementById("changeForm").action = "business!updateuserbusiness.action";
		document.getElementById("changeForm").submit();

	}
</script>
</html>



