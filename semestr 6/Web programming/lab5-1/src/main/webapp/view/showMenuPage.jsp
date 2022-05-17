<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Menu</title>
    </head>
    <body>
        <div class="page">
            <h3>All Dishes</h3>

            <table border="1" cellpadding="5" cellspacing="1" style="border-collapse: collapse">
                   <tr>
                      <th>ID</th>
                      <th>Description</th>
                      <th>Price</th>
                   </tr>
                   <c:forEach items="${menuList}" var="dish" >
                      <tr>
                         <td>${dish.getId()}</td>
                         <td>${dish.getDescription()}</td>
                         <td>${dish.getPrice()}</td>

                      </tr>
                   </c:forEach>
                </table>
        </div>
    </body>
</html>