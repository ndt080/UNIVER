<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />

<fmt:setLocale value="${language}" />
<fmt:setBundle basename="locale" />

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

  <h1><fmt:message key="auth.title.sign_up" /></h1>
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
    const SignUpFormInputs = [
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
        {
            label: 'Confirm password:',
            name: 'confirm_password',
            placeholder: 'Enter password...',
            styleClasses: [],
            type: "password",
            attributes: new Map([
                ['data-validation', 'true'],
                ['data-validation-params', 'require, min=6'],
            ])
        },
    ]

    function onLoadPage() {
        generateForm('auth-form', SignUpFormInputs, "Create account");
        initInputValidation('auth-form');
    }
</script>
<jsp:include page="blocks/footer.jsp"/>