<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="locale" var="loc" />
<fmt:message bundle="${loc}" key="local.message" var="message" />
<fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
<fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
<fmt:message bundle="${loc}" key="title.documents" var="title_documents" />
<fmt:message bundle="${loc}" key="title.catalogs" var="title_catalogs"/>

<form action="/localizator" method="post">
    <input type="hidden" name="local" value="ru" /> <input type="submit" value="${ru_button}" /><br />
</form>
<form action="/localizator" method="post">
    <input type="hidden" name="local" value="en" /> <input type="submit" value="${en_button}" /><br />
</form>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <%--<li><a href="#">Link</a></li>--%>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                        ${title_catalogs} <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/catalog/goods">Goods</a></li>
                        <li><a href="/catalog/producers">Producers</a></li>
                        <li><a href="/catalog/countries">Countries</a></li>
                        <%--<li role="separator" class="divider"></li>--%>
                        <%--<li><a href="#">Separated link</a></li>--%>
                    </ul>
                </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                            ${title_documents} <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="#">Action</a></li>
                            <li><a href="#">Another action</a></li>
                            <li><a href="#">Something else here</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#">Separated link</a></li>
                        </ul>
                    </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
