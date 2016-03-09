<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="in.edu.dsu.cit15.lms.web.WebUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="isAuthenticated()" ><sec:authentication var="user" property="principal" /></sec:authorize>



<!doctype html>
<html class="no-js" lang="">
  <head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <title>ePLS</title>

  <meta name="description" content="Yet Another Library Management System
">


  <!-- Disable tap highlight on IE -->
  <meta name="msapplication-tap-highlight" content="no">

  <!-- Web Application Manifest -->
  <link rel="manifest" href="manifest.json">

  <!-- Add to homescreen for Chrome on Android -->
  <meta name="mobile-web-app-capable" content="yes">
  <meta name="application-name" content="Web Starter Kit">
  <link rel="icon" sizes="192x192" href="images/touch/chrome-touch-icon-192x192.png">

  <!-- Add to homescreen for Safari on iOS -->
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-bar-style" content="black">
  <meta name="apple-mobile-web-app-title" content="Web Starter Kit">
  <link rel="apple-touch-icon" href="images/touch/apple-touch-icon.png">

  <!-- Tile icon for Win8 (144x144 + tile color) -->
  <meta name="msapplication-TileImage" content="images/touch/ms-touch-icon-144x144-precomposed.png">
  <meta name="msapplication-TileColor" content="#2F3BA2">

  <!-- Color the status bar on mobile devices -->
  <meta name="theme-color" content="#2F3BA2">

  <!-- SEO: If your mobile URL is different from the desktop URL, add a canonical link to the desktop page https://developers.google.com/webmasters/smartphone-sites/feature-phones -->
  <!--
  <link rel="canonical" href="http://www.example.com/">
  -->

  <!-- Material Design Lite page styles:
  You can choose other color schemes from the CDN, more info here http://www.getmdl.io/customize/index.html
  Format: material.color1-color2.min.css, some examples:
  material.red-teal.min.css
  material.blue-orange.min.css
  material.purple-indigo.min.css
  -->
  <!-- <link rel="stylesheet" href="https://storage.googleapis.com/code.getmdl.io/1.0.6/material.indigo-pink.min.css"> -->

  <!-- Material Design icons -->
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" >
  <link href='https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en' rel='stylesheet' type='text/css'>

  <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>
  <link href='http://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>

  <link rel="canonical" href="https://yalms.ccdsu2015.cf/contact/index.jsp">
  <link rel="alternate" type="application/rss+xml" title="Library Management System" href="https://yalms.ccdsu2015.cf/feed.xml">

  
  <link rel="stylesheet" href="/styles/main.css" />
   
  <!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script>window.jQuery || document.write('<script src="/bower_components/jquery/dist/jquery.min.js"><\/script>')</script> -->
  <script src=""></script>
  <script type="text/javascript">
    importScript = function(src, defer, id, callback) {
      var sp = document.createElement('script');
      sp.type = "text/javascript";
      sp.src = src;
      sp.async = true;
      id && (sp.id = id);
      defer && (sp.defer = "defer");
      callback && (sp.onload = sp.onreadystatechange = function() {
        var rs = this.readyState;
        if(rs && rs != 'complete' && rs != 'loaded') return;
        try{ callback(); } catch(e) {console.error(e)}
      });
      var s=document.getElementsByTagName('script')[0];
      s.parentNode.insertBefore(sp,s);
    };

    
    importScript('/scripts/main.js',
      false,
     'js-main',
      null);
    
  </script>
</head>

  <body class="">
    <div class="image-preloader"></div>
    

    <svg class="hidden" width="0" height="0" xmlns="http://www.w3.org/2000/svg" version="1.1"><defs><filter id="blur"><feGaussianBlur stdDeviation="5"/></filter></defs></svg>
<header>
  <nav id="mainNav" class="navbar navbar-default navbar-fixed-top">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-inverse-collapse">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="/">Library Management System</a>
    </div>
    <div class="navbar-collapse collapse navbar-inverse-collapse">
      <ul class="nav navbar-nav">
        <li class="active"><a href="/">Home</a></li>
      </ul>
      <form class="navbar-form navbar-left">
        <input type="search" class="form-control col-md-8" placeholder="Search">
      </form>
      <ul class="nav navbar-nav navbar-right">
        <sec:authorize access="isFullyAuthenticated()">
        <li><a href="/user/courses/">My Courses</a></li>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()" >
        <li><a class="" href="/dashboard"><span class="profile-pic-wrapper"><img src="${user.profileImageURL}" width="32" height="32" class="img-circle profile-pic" /></span> ${user.nickname}</a></li>
        <li class="dropdown">
          <a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0)"> <span class="fa fa-caret-down"></span></a>
          <ul class="dropdown-menu">
            <sec:authorize access="isFullyAuthenticated()">
            <li><a href="javascript:void(0)">${fn:escapeXml(user.givenName)} ${fn:escapeXml(user.familyName)}</a></li>
            <li><a href="/user/profile/"><i class="fa fa-user fa-fw"></i> Profile</a></li>
            <li><a href="/user/settings/"><i class="fa fa-cogs fa-fw"></i> Settings</a></li>
            <li class="divider"></li>
            </sec:authorize>
            <li class=""><a href="/auth/signout/"> <i class="fa fa-ban fa-fw"></i> Sign out</a></li>
          </ul>
        </li>
        </sec:authorize>
        <sec:authorize access="isAnonymous()" >
        <li><a href="/auth/signin">Sign in</a></li>
        </sec:authorize>
      </ul>
    </div>
  </div>
</nav>

</header>


    

    <footer class="footer">
  <div class="container text-center">
    <a href="https://developers.google.com/cloud" title="Google Cloud Platform" class="pull-left">
      <img src="/images/google-cloud-small.png" target="_blank" /><br />
      <p>Powered by Google Cloud Platform</p>
    </a>
    <p class="pull-right">&copy; 2016 &middot; The Hive &middot; <a href="/privay">Privacy</a> &middot; <a href="/terms">Terms</a></p>

    <a href="https://apple.com/mac" class="text-center">
      <i class="fa fa-apple fa-2x"></i><br />
      Created on a Mac
    </a>
  </div>
</footer>


    

    <!-- Built with love using Web Starter Kit -->
  </body>

</html>
