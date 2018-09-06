<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<!--[if lt IE 7 ]><html class="ie ie6" lang="en"> <![endif]-->
<!--[if IE 7 ]><html class="ie ie7" lang="en"> <![endif]-->
<!--[if IE 8 ]><html class="ie ie8" lang="en"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--><html lang="en"> <!--<![endif]-->
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
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    

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
	<link rel="apple-touch-icon" sizes="72x72" href="images/apple-touch-icon-72x72.png" />
	<link rel="apple-touch-icon" sizes="114x114" href="images/apple-touch-icon-114x114.png" />
    
    
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />




    <base href="<%=basePath%>">
    
    

  </head>
  
 
<body onload="queryBillNum()">
<div id="bodychild">
	<div id="outercontainer">
    
        <!-- HEADER -->
        <div id="outerheader">
        	<div class="container">
            <header id="top" class="twelve columns">
            	<div id="logo" class="three columns alpha"><img src="images/newlogo.png" alt="" /></div>
                <section id="navigation" class="twelve columns">
                    <nav id="nav-wrap">
                        <ul id="topnav" class="sf-menu">
                         <li><a href="index.jsp">首页</a></li>
                            <li><a href="user!oneUser.action?userId=${userId }">用户个人信息</a></li>
                            <li><a href="business!findAllBusiness.action">套餐变更</a></li>
                            <li><a href="bill!findUserBill.action?userId=${userId }">通话详单</a></li>
                            <li  class="current"><a href="genBillReport.jsp">话单报表</a></li>  
                            <li><a href="user!logOff.action">退出系统</a></li>
                        </ul><!-- topnav -->
                    </nav><!-- nav -->	
                    <div class="clear"></div>
                </section>
                <div class="clear"></div>
            </header>
            </div>
        </div>
        <!-- END HEADER -->
         <div  style=" background:#EFEFEF; border:#D8D8D8 solid black; width:930px; height:430px;"id="outerslider">
       
        <!-- SLIDER -->
         <br/><br/><br/><br/><br/>
         <div style="text-align:center">
       	  	<p id="tips" style="font-size:20px;"></p>
         	<input type="button" value="生成报表" id="gen" disabled="disabled"/>
         	<input type="button" value="下载报表" id="down" disabled="disabled"/>
         	<input type="text" id="reportUrl" name="reportUrl" hidden="hidden"/>
         </div>
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
	          animation: "fade",              //String: Select your animation type, "fade" or "slide"
			  directionNav: false, //Boolean: Create navigation for previous/next navigation? (true/false)
			  controlNav: true  //Boolean: Create navigation for paging control of each clide? Note: Leave true for manualControls usage
	        });
	});


	function queryBillNum(){
		$.ajax({
			type:"POST",
			url:"bill!findAllUserBill.action",
			data:"",
			success:function(data){
				if(data !=null && data!= "0" ){
					$("#tips").html("你有"+ data +"条账单记录");
					$("#gen").attr("disabled",false);
				}else{
					$("#tips").html("你当前没有账单记录");
				}	
			},
		});
	}
	
	$("#gen").click(function(){
		$.ajax({
			type:"POST",
			url:"bill!genUserBillReport.action",
			data:"",
			success:function(data){
				if(data !=null){
					$("#down").attr("disabled",false);
					$("#gen").attr("disabled",true);
					$("#reportUrl").val(data);
				}
				
			},
		});
	});
	
	$("#down").click(function(){
		
		var form=$("<form>");
		form.attr("style","display:none");
		form.attr("target","");
		form.attr("method","post");
		form.attr("action","bill!downloadUserBillReport.action");
		var input=$("<input>");
		input.attr("type","hidden");
		input.attr("name","path");
		input.attr("value",$("#reportUrl").val());
		$("body").append(form);
		form.append(input);
		form.submit();
	});
	
	
	
</script>	
</html>



