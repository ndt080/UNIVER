<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>

<jsp:include page="blocks/header.jsp">
  <jsp:param name="title" value="XBet. Authorization"/>
  <jsp:param name="pageStyle" value="${pageContext.request.contextPath}/assets/styles/pages/auth.css"/>
</jsp:include>

<main class="main bets-page title-regular-14">
  <c:if test="${errorString != null}">
    <section class="page-error"
             style="padding: 16px; margin-bottom: 16px; background-color: #d4d4d4; border-radius: 12px;"
    >
      <h2 class="page-error__title title-regular-28" style="color: rgb(247, 114, 73)">Server error</h2>
      <p class="title-regular-14">${errorString}</p>
    </section>
  </c:if>

  <h1>Sign In</h1>
  <form
      id="auth-form"
      class="form auth-form"
      data-form="auth-form"
      method="post"
  >
  </form>
</main>

<script src="${pageContext.request.contextPath}/assets/js/form/form-generator.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/form/form-validation.js"></script>
<script>
    const SignInFormInputs = [
        {
            label: 'Login:',
            name: 'login',
            placeholder: 'Enter login...',
            styleClasses: [],
            type: "text",
            attributes: new Map([
                ['data-validation', 'true'],
                ['data-validation-params', 'require,email'],
            ])
        },
        {
            label: 'Password:',
            name: 'password',
            placeholder: 'Enter password...',
            styleClasses: [],
            type: "password",
            attributes: new Map([
                ['data-validation', 'true'],
                ['data-validation-params', 'require,min=6'],
            ])
        },
    ]

    function onLoadPage() {
        generateForm('auth-form', SignInFormInputs, "Sign in");

        <%--const form = document.getElementById('auth-form');--%>
        <%--form.onsubmit = (e) => {--%>
        <%--    e.preventDefault();--%>
        <%--    const formData = new FormData(e.target);--%>
        <%--    const queryParams = {--%>
        <%--        login: formData.get('login'),--%>
        <%--        password: formData.get('password'),--%>
        <%--    }--%>
        <%--    const query = 'login=' + queryParams.login + '&password=' + queryParams.password;--%>
        <%--    window.location = `${pageContext.request.contextPath}/?command=authorization&` + query;--%>
        <%--};--%>
        initInputValidation('auth-form');
    }
</script>
<jsp:include page="blocks/footer.jsp"/>