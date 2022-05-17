<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en" xmlns="">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>${param.title}</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/main.css">
  <link rel="stylesheet" href="${param.pageStyle}">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/navbar.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/header.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/footer.css">
</head>

<body class="layout" onload="onLoadPage()">
  <header class="header">
    <div
        class="header__logo title-semi-32"
        id="logo"
        onclick="window.location = '${pageContext.request.contextPath}/'"
    >
      <img class="header__logo_img" src="${pageContext.request.contextPath}/assets/img/icons/horse.svg" alt="logo"/>
      XBet
    </div>
    <jsp:include page="navbar.jsp"/>
  </header>