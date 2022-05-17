<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-16">
    <title>Restaurant</title>

</head>
<body>
<header>
    <p>
        <br>1) Вывести информацию о всех блюдах в меню.
        <br>2) Вывести информацию о всех подтвержденных заказах.
        <br>3) Вывести информацию о всех заказах клиента.
        <br>4) Сделать Заказ
    </p>
</header>
<div class="main-page">
    <div class="menu">
        <h1>MENU</h1>
        <button type="button" onclick="window.open('${pageContext.request.contextPath}?command=showMenu')" >Меню</button>
        <button type="button" onclick="window.open('${pageContext.request.contextPath}?command=showAllOrders')">Все Подтвержденные Заказы</button>
        <button type="button" onclick="window.open('${pageContext.request.contextPath}?command=makeOrder')">Сделать заказ</button>
        <button type="button" onclick="window.open('${pageContext.request.contextPath}?command=getAllOrders')">Все заказы клиента</button>
    </div>
</div>
</body>
</html>