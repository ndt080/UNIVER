<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="blocks/header.jsp">
  <jsp:param name="title" value="XBet. About" />
  <jsp:param name="pageStyle" value="${pageContext.request.contextPath}/assets/styles/pages/about.css" />
</jsp:include>

<main class="main about-page title-regular-14">
  <% if(response.getStatus() == 500){ %>
  <section class="page-error"
           style="padding: 16px; margin-bottom: 16px; background-color: #d4d4d4; border-radius: 12px;"
  >
    <h2 class="page-error__title title-regular-28" style="color: rgb(247, 114, 73)">Server error</h2>
<%--    <p class="title-regular-14"><%=exception.getMessage() %></p>--%>
  </section>

  <%-- include login page --%>
  <%@ include file="index.jsp"%>
  <%}else {%>
  Hi There, error code is <%=response.getStatus() %><br>
  Please go to <a href="${pageContext.request.contextPath}/">Home</a>
  <%} %>
</main>

<script>const onLoadPage = () => {}</script>
<jsp:include page="blocks/footer.jsp"/>

