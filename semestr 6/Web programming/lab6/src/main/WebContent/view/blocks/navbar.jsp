<%@ page import="models.entities.Auth" %>
<%@ page language="java" contentType="text/html;charset=UTF-16BE" pageEncoding="UTF-16BE"%>

<%Auth user = (Auth) session.getAttribute("user"); %>
<%String userType = (user == null ? "null" : user.getUserType()); %>

<nav class="navbar" id="navbar">
  <ul class="navbar__list title-regular-18">
    <%if (!userType.equals("null")) {%>
      <li class="navbar__item">
        <a href="${pageContext.request.contextPath}/?command=bets" class="navbar__link">Bets</a>
      </li>
      <li class="navbar__item">
        <a href="${pageContext.request.contextPath}/?command=races" class="navbar__link">Races</a>
      </li>
    <%}%>

    <li class="navbar__item">
      <a href="${pageContext.request.contextPath}/?command=about" class="navbar__link">About</a>
    </li>

    <%if (userType.equals("null")) {%>

      <li class="navbar__item">
        <a href="${pageContext.request.contextPath}/?command=authorization" class="navbar__link">SignIn</a>
      </li>
      <li class="navbar__item">
        <a href="${pageContext.request.contextPath}/?command=registration" class="navbar__link">SignUp</a>
      </li>
    <%}%>

    <%if (!userType.equals("null")) {%>
      <li class="navbar__item">
        <a href="${pageContext.request.contextPath}/?command=logout" class="navbar__link">logOut</a>
      </li>
    <%}%>

    <li class="navbar__item">
      <a href="#" class="navbar__link title-semi-18">Github</a>
    </li>
  </ul>
</nav>

<div class="navbar__button" id="navbar-btn">
  <div></div>
</div>
