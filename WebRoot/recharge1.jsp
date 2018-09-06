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
    <style>
        .choose_radio{
            display:block;
            float:left;
            width:20px;
            height:20px;
            border:1px solid #ccc;
            border-radius:2px;
        }
        .choose_radioed{
            display:block;
            float:left;
            width:22px;
            height:22px;
            border-radius:2px;
            background:url(fang_choose.png) no-repeat;
            background-size:100%;
        }
        p{
            float:left;
            margin:0;
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
                style="background: #EFEFEF; border: #D8D8D8 solid black; width: 930px; height: 430px;"
                id="outerslider">
            <br>


            <s:if test="flag==0">
            <h2 align="center">订单已生成</h2>
                <div align="center" width="200px">
                    <%
                        Items itemInfo =(Items)request.getAttribute("itemInfo");
                        String orderId = (String)request.getAttribute("orderId");
                        String telno = (String) request.getAttribute("telno");
                    %>
                    <table width="200px" align="center">
                        <tr>

                            <td>订单编号：</td>
                            <td><%=orderId%></td>

                        </tr>
                        <tr>

                            <td>
                                充值手机号
                            </td>
                            <td>
                                <%=telno%>
                            </td>

                        </tr>
                        <tr>

                            <td>商品信息</td>
                            <td><%=itemInfo.getPname()%></td>


                        </tr>
                        <tr>

                            <td>价格</td>
                            <td><%=itemInfo.getPcost()/100%></td>

                        </tr>

                    </table>
                    <table align="center" style="margin-top: 20px">
                        <tr>
                            <label>输入支付密码：<input type="text" id="payInput"/> </label>
                        </tr>
                        <tr>
                            <input type="button" value="立即支付" id="btn" onclick="payOrder()"/>

                        </tr>
                        <p>支付密码为手机号后四位</p>

                        <br>

                    </table>

                    <s:form method="POST" id="payForm" action="user!payOrder.action">
                        <%--<label>支付密码：</label>--%>
                        <input hidden name="payPwd" id="payPwd" value=""/>
                        <input hidden name="cost" id="cost" value="<%=itemInfo.getPcost()/100%>"/>
                        <input hidden name="orderId" id="orderId" value="<%=orderId%>"/>
                        <input hidden name="productId" id="productId" value="<%=itemInfo.getId()%>" />
                        <input hidden name="telno" id="telno" value="<%=telno%>">
                        <%--<input hidden name="">--%>
                    </s:form>
                </div>
            </s:if>
            <s:if test="flag==1">
                <h2 style="color:red;">错误:<s:property value="errorMsg"></s:property> </h2>
                <a href="javascript:go.history(-1)">后退</a>
            </s:if>

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

//        var productId = $("#recharge option:selected").attr("id");
//        console.log(productId);
//        document.getElementById("productId").value=productId;
//        document.getElementById("cost").value = value;
//        document.getElementById("chargeForm").action = "user!processRecharge.action";
//
//        $("#chargeForm").submit();
    function payOrder() {
        var pwd = $("#payInput").val();
        if(""==pwd){
            alert("密码不能为空");
            return;
        }
        document.getElementById("payPwd").value= pwd;
        $("#payForm").submit();

    }


</script>
</html>
