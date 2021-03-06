<%@ page import="models.entities.Auth" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />

<fmt:setLocale value="${language}" />
<fmt:setBundle basename="locale" />

<%Auth user = (Auth) session.getAttribute("user"); %>
<%String userType = (user == null ? "null" : user.getUserType()); %>

<nav class="navbar" id="navbar">
  <ul class="navbar__list title-regular-18">
    <%if (!userType.equals("null")) {%>
    <li class="navbar__item">
      <a href="${pageContext.request.contextPath}/?command=bets" class="navbar__link">
        <fmt:message key="nav.item.bets"/>
      </a>
    </li>
    <li class="navbar__item">
      <a href="${pageContext.request.contextPath}/?command=races" class="navbar__link">
        <fmt:message key="nav.item.races"/>
      </a>
    </li>
    <li class="navbar__item">
      <a href="${pageContext.request.contextPath}/?command=horses" class="navbar__link">
        <fmt:message key="nav.item.horses"/>
      </a>
    </li>
    <li class="navbar__item">
      <a href="${pageContext.request.contextPath}/?command=about" class="navbar__link">
        <fmt:message key="nav.item.about"/>
      </a>
    </li>
    <li class="navbar__item">
      <a href="${pageContext.request.contextPath}/?command=chat" class="navbar__link">
        <fmt:message key="nav.item.chat"/>
      </a>
    </li>
    <%}%>

    <%if (userType.equals("null")) {%>
    <li class="navbar__item">
      <a href="${pageContext.request.contextPath}/?command=about" class="navbar__link">
        <fmt:message key="nav.item.about"/>
      </a>
    </li>
    <li class="navbar__item">
      <a href="${pageContext.request.contextPath}/?command=authorization" class="navbar__link">
        <fmt:message key="nav.item.sign_in"/>
      </a>
    </li>
    <li class="navbar__item">
      <a href="${pageContext.request.contextPath}/?command=registration" class="navbar__link">
        <fmt:message key="nav.item.sign_up"/>
      </a>
    </li>
    <%}%>

    <%if (!userType.equals("null")) {%>
    <li class="navbar__item">
      <a href="${pageContext.request.contextPath}/?command=logout" class="navbar__link">
        <fmt:message key="nav.item.logout"/>
      </a>
    </li>
    <%}%>

    <li class="navbar__item">
      <form>
        <select id="language" name="language" onchange="submit()">
          <option value="en_En" ${language == 'en_En' ? 'selected' : ''}>En</option>
          <option value="ru_RU" ${language == 'ru_RU' ? 'selected' : ''}>Ru</option>
        </select>
      </form>
    </li>

    <li class="navbar__item">
      <a href="#" class="navbar__link title-semi-18">
        <fmt:message key="nav.item.github"/>
      </a>
    </li>
  </ul>
</nav>

<div class="navbar__button" id="navbar-btn">
  <div></div>
</div>
