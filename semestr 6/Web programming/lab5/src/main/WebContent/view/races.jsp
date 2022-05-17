<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="blocks/header.jsp">
  <jsp:param name="title" value="XBet. Races"/>
  <jsp:param name="pageStyle" value="${pageContext.request.contextPath}/assets/styles/pages/races.css"/>
</jsp:include>

<main class="main races-page title-regular-14">
  <c:if test="${errorString != null}">
    <section class="page-error"
             style="padding: 16px; margin-bottom: 16px; background-color: #d4d4d4; border-radius: 12px;"
    >
      <h2 class="page-error__title title-regular-28" style="color: rgb(247, 114, 73)">Server error</h2>
      <p class="title-regular-14">${errorString}</p>
    </section>
  </c:if>

  <form id="races-form" class="form races-form" data-form="races-form"></form>
  <form id="all-races-form" class="form"></form>

  <div class="races-page__result" id="races-result">
    <table class="races-table">
      <tr class="races-table__header">
        <th class="races-table__col">ID</th>
        <th class="races-table__col">DATE</th>
        <th class="races-table__col">PARTICIPANTS</th>
        <th class="races-table__col">WINNER</th>
      </tr>

      <c:forEach items="${raceList}" var="race">
        <tr class="races-table__row">
          <td class="races-table__col">${race.getId()}</td>
          <td class="races-table__col">${race.getDate()}</td>
          <td class="races-table__col">${race.getParticipants() != null ? race.getParticipants() : "none"}</td>
          <td class="races-table__col">${race.getWinner() != null ? race.getWinner() : "none"}</td>
        </tr>
      </c:forEach>
    </table>
  </div>
</main>


<script src="${pageContext.request.contextPath}/assets/js/form/form-generator.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/form/form-validation.js"></script>
<script>
    const RacesFormInputs = [
        {
            label: 'Race date:',
            name: 'raceDate',
            placeholder: 'Enter race date...',
            styleClasses: [],
            type: "date",
            attributes: new Map([
                ['data-validation', 'true'],
                ['data-validation-params', 'require'],
            ])
        },
    ]

    function onLoadPage() {
        generateForm('races-form', RacesFormInputs, "Show races");
        generateForm('all-races-form', [], "Show all races");

        const racesForm = document.getElementById('races-form');
        racesForm.onsubmit = (e) => {
            e.preventDefault();
            const formData = new FormData(e.target);
            window.location = `${pageContext.request.contextPath}/?command=races&date=` + formData.get('raceDate');
        };

        const allRacesForm = document.getElementById('all-races-form');
        allRacesForm.onsubmit = (e) => {
            e.preventDefault();
            window.location = `${pageContext.request.contextPath}/?command=races`
        };

        initInputValidation('races-form');
    }
</script>
<jsp:include page="blocks/footer.jsp"/>