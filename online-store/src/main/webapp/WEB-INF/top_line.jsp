<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="local.message" var="message"/>
<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button"/>
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button"/>
<fmt:message bundle="${loc}" key="LogIn" var="LogIn"/>
<fmt:message bundle="${loc}" key="LogOut" var="LogOut"/>
<fmt:message bundle="${loc}" key="Register" var="Register"/>
<fmt:message bundle="${loc}" key="Documents" var="Documents"/>
<fmt:message bundle="${loc}" key="Catalogs" var="Catalogs"/>
<fmt:message bundle="${loc}" key="Goods" var="Goods"/>
<fmt:message bundle="${loc}" key="Producers" var="Producers"/>
<fmt:message bundle="${loc}" key="Countries" var="Countries"/>
<fmt:message bundle="${loc}" key="Stores" var="Stores"/>
<fmt:message bundle="${loc}" key="Persons" var="Persons"/>
<fmt:message bundle="${loc}" key="Orders" var="Orders"/>
<fmt:message bundle="${loc}" key="Invoices" var="Invoices"/>
<fmt:message bundle="${loc}" key="SetsPrice" var="SetsPrice"/>
<fmt:message bundle="${loc}" key="basket.ItemsOfGoodsInTheBasket" var="BasketItemsOfGoodsInTheBasket"/>
<fmt:message bundle="${loc}" key="basket.Sum" var="BasketSum"/>
<fmt:message bundle="${loc}" key="MyOrders" var="MyOrders"/>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

            <ul class="nav navbar-nav navbar-left">
                <li><a href="/"><img src="/hospital26.ico" href="/"></a></li>
                <c:if test="${requestScope.canEdit}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                           aria-expanded="false">
                                ${Catalogs} <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="/catalogs/goods/">${Goods}</a></li>
                            <li><a href="/catalogs/producers/">${Producers}</a></li>
                            <li><a href="/catalogs/countries/">${Countries}</a></li>
                            <li><a href="/catalogs/stores/">${Stores}</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="/catalogs/persons/">${Persons}</a></li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                           aria-expanded="false">
                                ${Documents} <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="/documents/invoices/">${Invoices}</a></li>
                            <li><a href="/documents/sets_price/">${SetsPrice}</a></li>
                            <li><a href="/documents/orders/">${Orders}</a></li>
                        </ul>
                    </li>
                </c:if>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <c:if test="${requestScope.isLoggedIn}">
                    <li><a href="/orders/">${MyOrders}</a></li>
                </c:if>
                <c:if test="${requestScope.basketQuantity > 0}">
                    <li><a href="/basket/"><img src="/basket_26.ico"></a></li>
                    <li>
                        <ul class="basket-list">
                            <li>${BasketItemsOfGoodsInTheBasket} ${requestScope.basketSum}</li>
                            <li>${BasketSum} ${requestScope.basketQuantity}</li>
                        </ul>
                    </li>
                </c:if>

                <li>
                    <div class="btn-group" role="group" aria-label="...">
                        <c:set var="ru" value="ru" scope="page"/>
                        <c:choose>
                            <c:when test="${sessionScope.local eq ru}" >
                                <form action="/localizator" method="post" class="horizontal">
                                    <input type="hidden" name="local" value="en"/>
                                    <input type="submit" value="${en_button}" class="btn btn-default">
                                </form>
                            </c:when>
                            <c:otherwise>
                                <form action="/localizator" method="post" class="horizontal">
                                    <input type="hidden" name="local" value="ru"/>
                                    <input type="submit" value="${ru_button}" class="btn btn-default">
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </li>
                <c:choose>
                    <c:when test="${!requestScope.isLoggedIn}">
                        <li>
                            <form action="/login1.jsp" method="get" class="horizontal">
                                <input type="submit" value="${LogIn}" class="btn btn-default">
                            </form>
                            <form action="/auth" method="get" class="horizontal">
                                <input type="submit" value="${Register}" class="btn btn-default">
                            </form>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li>${requestScope.email}</li>
                        <li>
                            <form action="/auth" method="post" class="horizontal">
                                <input type="hidden" name="logout" value="${true}"/>
                                <input type="submit" value="${LogOut}" class="btn btn-default">
                            </form>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
