<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="blocks/header.jsp">
  <jsp:param name="title" value="XBet. Bets" />
  <jsp:param name="pageStyle" value="${pageContext.request.contextPath}/assets/styles/pages/bets.css" />
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

  <form
      id="bets-form"
      class="form bets-form"
      data-form="bets-form"
  >
  </form>

  <div class="bets-page__result" id="bets-result">
    <table class="bets-table">
      <tr class="bets-table__header">
        <th class="bets-table__col">ID</th>
        <th class="bets-table__col">CLIENT</th>
        <th class="bets-table__col">RACE</th>
        <th class="bets-table__col">HORSE</th>
        <th class="bets-table__col">AMOUNT</th>
        <th class="bets-table__col">COEFFICIENT</th>
        <th class="bets-table__col">CREATEDATE</th>
      </tr>

      <c:forEach items="${betsList}" var="bet">
        <tr class="bets-table__row">
          <td class="bets-table__col">${bet.getId()}</td>
          <td class="bets-table__col">${bet.getClient().toString()}</td>
          <td class="bets-table__col">${bet.getRace().toString()}</td>
          <td class="bets-table__col">${bet.getHorse().toString()}</td>
          <td class="bets-table__col">${bet.getAmount()}</td>
          <td class="bets-table__col">${bet.getCoefficient()}</td>
          <td class="bets-table__col">${bet.getCreatedAt()}</td>
          <td class="bets-table__col">${bet.getCreator()}</td>
        </tr>
      </c:forEach>
    </table>
  </div>
</main>

<script src="${pageContext.request.contextPath}/assets/js/form/form-generator.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/form/form-validation.js"></script>
<script>
    const BetsFormInputs = [
        {
            label: 'Client id:',
            name: 'clientId',
            placeholder: 'Enter id...',
            styleClasses: [],
            type: "number",
            attributes: new Map([
                ['data-validation', 'true'],
                ['data-validation-params', 'number'],
            ])
        },
        {
            label: 'Bet date:',
            name: 'betDate',
            placeholder: 'Enter race id...',
            styleClasses: [],
            type: "date",
            attributes: new Map([
                ['data-validation', 'true'],
                ['data-validation-params', 'require'],
            ])
        },
    ]

    function onLoadPage() {
        generateForm('bets-form', BetsFormInputs, "Show bets");

        const form = document.getElementById('bets-form');
        form.onsubmit = (e) => {
            e.preventDefault();
            const formData = new FormData(e.target);
            const queryParams = {
                date: formData.get('betDate'),
                client: formData.get('clientId'),
            }
            const query = 'date=' + queryParams.date + '&client=' + queryParams.client;
            window.location = `${pageContext.request.contextPath}/?command=bets&` + query;
        };
        initInputValidation('bets-form');
    }
</script>
<jsp:include page="blocks/footer.jsp"/>