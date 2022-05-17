<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Orders</title>
    </head>
    <body>
        <div class="page">
            <h3>Client Orders</h3>

            <table border="1" cellpadding="5" cellspacing="1" style="border-collapse: collapse">
                <tr>
                    <th>ID</th>
                    <th>price</th>
                    <th>client_id</th>
                    <th>isApproved</th>
                    <th>isPaid</th>
                </tr>
                   <c:forEach items="${ordersList}" var="order" >
                      <tr>
                          <td>${order.getId()}</td>
                          <td>${order.getPrice()}</td>
                          <td>${order.getClient_id()}</td>
                          <td>${order.isApproved()}</td>
                          <td>${order.isPaid()}</td>
                      </tr>
                   </c:forEach>
                </table>
        </div>
    </body>
</html>