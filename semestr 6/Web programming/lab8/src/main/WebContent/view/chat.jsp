<%@ page import="models.entities.Auth" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:requestEncoding value="UTF-8"/>

<jsp:include page="blocks/header.jsp">
  <jsp:param name="title" value="XBet. Chat"/>
  <jsp:param name="pageStyle" value="${pageContext.request.contextPath}/assets/styles/pages/about.css"/>
</jsp:include>

<main class="main about-page title-regular-14">
  <h6 class="title-regular-28">Support chat</h6>

  <%Auth user = (Auth) session.getAttribute("user"); %>
  <%String userType = (user == null ? "null" : user.getUserType()); %>
  <%String userLogin = (user == null ? "null" : user.getLogin()); %>


  <input type="hidden" id="senderId" value="${senderId}">
  <div>
    <%if (userType.equals("M")) {%>
      <p>Manager</p>
    <%}%>
    <label for="chatWindow"></label>
    <textArea id="chatWindow" rows="10" style="width: 44em;margin: 15px" readonly></textArea>
  </div>
  <div>
    <input type="text" id="chatInput" style="width: 40em;margin: 15px" />
    <input id="sendBtn" type="button" class="btn btn-primary" value="Send" onclick="sendMessage('<%=userLogin%>')"/>
  </div>
</main>

<script lang="text/javascript">
    let websocket = null;
    let lang = null;

    const onLoadPage = () => {
        websocket = new WebSocket('ws://localhost:8080/chat');
        websocket.onopen = (event) => {
            console.log("webSocketOpen invoked");
            websocket.send("[web_socket_message]:=:" + document.getElementById('senderId').value);
        };

        websocket.onmessage = (event) => {
            console.log("websocketMessage invoked");
            document.getElementById('chatWindow').value += '\n' + event.data;
        };

        websocket.onerror = (event) => console.log("websocketError invoked");
    };

    function sendMessage(login) {
        const input = document.getElementById('chatInput');
        const message = input?.value;
        if (message === "") return;

        websocket.send(login + ': ' + message);
        document.getElementById('chatWindow').value += '\n' + login + ': ' + message;
        input.value = "";
    }

    window.addEventListener("unload", () => websocket.close());
</script>
<jsp:include page="blocks/footer.jsp"/>

