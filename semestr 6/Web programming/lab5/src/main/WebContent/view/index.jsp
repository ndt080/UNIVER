<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="blocks/header.jsp">
  <jsp:param name="title" value="XBet. Home" />
  <jsp:param name="pageStyle" value="${pageContext.request.contextPath}/assets/styles/pages/home.css" />
</jsp:include>

<main class="main home-preview">
  <div class="home-page home-preview__title">
    <div class="home-preview__top_container">
      <h1>Бонус <span style="color: rgb(255,216,0)">20 BYN</span> всем новым клиентам</h1>
    </div>
    <div class="home-preview__bottom_container">
      <h1>С большими выигрышами <br> приходит большая ответственность</h1>
    </div>
  </div>

</main>

<script>const onLoadPage = () => {}</script>
<jsp:include page="blocks/footer.jsp"/>
