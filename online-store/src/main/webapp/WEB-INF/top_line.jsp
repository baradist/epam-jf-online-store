<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="locale" var="loc"/>
<fmt:message bundle="${loc}" key="local.message" var="message"/>
<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button"/>
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button"/>
<fmt:message bundle="${loc}" key="title.documents" var="title_documents"/>
<fmt:message bundle="${loc}" key="title.catalogs" var="title_catalogs"/>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

            <ul class="nav navbar-nav navbar-left">
                <li><a href="/"><img src="/hospital26.ico" href="/"></a></li>
                <c:if test="${requestScope.canEdit}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                           aria-expanded="false">
                                ${title_catalogs} <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="/catalogs/goods/">Goods</a></li>
                            <li><a href="/catalogs/producers/">Producers</a></li>
                            <li><a href="/catalogs/countries/">Countries</a></li>
                                <%--<li role="separator" class="divider"></li>--%>
                                <%--<li><a href="#">Separated link</a></li>--%>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                           aria-expanded="false">
                                ${title_documents} <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="/documents/orders/">Orders</a></li>
                            <li><a href="#">Another action</a></li>
                            <li><a href="#">Something else here</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#">Separated link</a></li>
                        </ul>
                    </li>
                </c:if>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <c:if test="${requestScope.basketQuantity > 0}">
                    <li><a href="/orders/">My orders</a> </li>
                    <li><a href="/basket/"><img src="/basket_26.ico"></a></li>
                    <li>
                        <ul class="basket-list">
                            <li>В корзине ${requestScope.basketSum} шт товара</li>
                            <li>на сумму ${requestScope.basketQuantity}</li>
                        </ul>
                    </li>
                </c:if>

                <li>
                    <div class="btn-group" role="group" aria-label="...">
                        <form action="/localizator" method="post" class="horizontal">
                            <input type="hidden" name="local" value="ru"/>
                            <input type="submit" value="${ru_button}" class="btn btn-default">
                        </form>
                        <form action="/localizator" method="post" class="horizontal">
                            <input type="hidden" name="local" value="en"/>
                            <input type="submit" value="${en_button}" class="btn btn-default">
                        </form>
                    </div>
                </li>
                <c:choose>
                    <c:when test="${!requestScope.isLoggedIn}">
                        <li>
                            <form action="/login1.jsp" method="get" class="horizontal">
                                <input type="submit" value="Log In" class="btn btn-default">
                            </form>
                            <form action="/auth" method="get" class="horizontal">
                                <input type="submit" value="Register" class="btn btn-default">
                            </form>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li>${requestScope.email}</li>
                        <li>
                            <form action="/auth" method="post" class="horizontal">
                                <input type="hidden" name="logout" value="${true}"/>
                                <input type="submit" value="Log Out" class="btn btn-default">
                            </form>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
