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
                style="background: #EFEFEF; border: #D8D8D8 solid black; width: 930px; height: 430px;"
                id="outerslider">

            <!-- SLIDER -->
            <%--<%--%>
                <%--Users user = (Users) request.getAttribute("oneUser");--%>
            <%--%>--%>
            <%
                List vaList = (List)request.getAttribute("valist");
                  //  String token = (String)request.getAttribute("token");
                String token = (String)request.getSession().getAttribute("token");//.getAttribute("token");
            %>

            <br>
            <h5 align="center">增值业务受理中心</h5>
            <form method="post" id="vaForm">
            <table>
                <tr>
                    <td class="bluebg" align="center">序号</td>
                    <td class="bluebg" align="center">业务名</td>
                    <td class="bluebg" align="center">增值包说明</td>
                    <td class="bluebg" align="center">套餐价</td>
                    <td class="bluebg" align="center">办理</td>
                </tr>
                <%
                    if (vaList!=null && vaList.size()>0){
                        for(int i=0;i<vaList.size();i++){
                            Va vaEntry = (Va) vaList.get(i);


                %>

                <tr class="writebg">
                    <td align="center" height="30"><%=i+1%></td>
                    <td align="center"><%=vaEntry.getVaname()%></td>
                    <td align="center"><%=vaEntry.getVadesc()%></td>
                    <td align="centre"><%=vaEntry.getVacost()%>元</td>
                    <td align="center">
                        <a href="vaservice!obtain.action?id=<%=vaEntry.getId()%>&cost=<%=vaEntry.getVacost()%>&vaname=<%=vaEntry.getVaname()%>&token=<%=token%>">立即获得</a></td>
                        <%--<a href="javascript:void(0)" onclick="getva('<%=vaEntry.getId()%>>')"></a></td>--%>
                </tr>
                <%
                    }}
                %>
            </table>
                <%--<input hidden value="" name="id" id="id"/>--%>
                <%--<input hidden value="" name="cost" id="cost"/>--%>
                <%--<input hidden value="" name=""/>--%>
                <%--<input hidden value="<%=token%>" name="token"/>--%>

            </form>
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

    // function getva(id) {
    //     $("#vaname").attr("value",id);
    //     $("#vaForm").attr("action", "vaservice!obtain.action");
    //     $("#vaForm").submit();
    //
    // }

</script>
</html>
