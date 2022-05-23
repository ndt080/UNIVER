<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/tag.tld" prefix="do"%>

<jsp:include page="blocks/header.jsp">
  <jsp:param name="title" value="XBet. Horses"/>
  <jsp:param name="pageStyle" value="${pageContext.request.contextPath}/assets/styles/pages/horses.css"/>
</jsp:include>

<main class="main horses-page title-regular-14">
<%--  <c:if test="${errorString != null}">--%>
<%--    <section class="page-error"--%>
<%--             style="padding: 16px; margin-bottom: 16px; background-color: #d4d4d4; border-radius: 12px;"--%>
<%--    >--%>
<%--      <h2 class="page-error__title title-regular-28" style="color: rgb(247, 114, 73)">Server error</h2>--%>
<%--      <p class="title-regular-14">${errorString}</p>--%>
<%--    </section>--%>
<%--  </c:if>--%>

  <form id="horses-form" class="form horses-form" data-form="horses-form"></form>
  <form id="all-horses-form" class="form"></form>

  <div class="horses-page__result" id="horses-result">
    <do:TableTag items="${horsesList}"> </do:TableTag>
  </div>
</main>


<script src="${pageContext.request.contextPath}/assets/js/form/form-generator.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/form/form-validation.js"></script>
<script>
    const HorsesFormInputs = [
        {
            label: 'Race id:',
            name: 'raceId',
            placeholder: 'Enter race id...',
            styleClasses: [],
            type: "number",
            attributes: new Map([
                ['data-validation', 'true'],
                ['data-validation-params', 'require'],
            ])
        },
    ]

    function onLoadPage() {
        generateForm('horses-form', HorsesFormInputs, "Show horses");
        generateForm('all-horses-form', [], "Show all horses");

        const horsesForm = document.getElementById('horses-form');
        horsesForm.onsubmit = (e) => {
            e.preventDefault();
            const formData = new FormData(e.target);
            window.location = `${pageContext.request.contextPath}/?command=horses&raceId=` + formData.get('raceId');
        };

        const allHorsesForm = document.getElementById('all-horses-form');
        allHorsesForm.onsubmit = (e) => {
            e.preventDefault();
            window.location = `${pageContext.request.contextPath}/?command=horses`
        };

        initInputValidation('horses-form');
    }
</script>
<jsp:include page="blocks/footer.jsp"/>