 <%--works only with Helper.longListByPages(...) !!!--%>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<ul class="nav navbar-nav navbar-fixed-bottom">
    <c:set value="${0}" var="n" scope="page"/>
    <c:forEach var="i" begin="0" step="${requestScope.rowsOnPage}" end="${requestScope.quantity - 1}">
        <c:set value="${n + 1}" var="n" scope="page"/>
        <li>
            <form action="${requestScope.url}" method="get" class="horizontal">
                <input type="hidden" name="offset" value="${i}"/>
                <input type="hidden" name="pageNumber" value="${n}"/>
                <input type="hidden" name="rowsOnPage" value="${requestScope.rowsOnPage}"/>
                <input type="submit" value="${n}"
                <c:if test="${requestScope.pageNumber eq n}">
                       disabled="true"
                </c:if>
                       class="btn btn-default">
            </form>
        </li>
    </c:forEach>
    <li>Записей на странице: <b>${requestScope.rowsOnPage}</b>  </li>

    <li>
        <form action="${requestScope.url}" method="get" id="pagesTune" class="horizontal">
            <input form="pagesTune" type="hidden" name="changeRowsOnPage" value="${true}"/>
            <input form="pagesTune" type="submit" value="Установить:">
            <select required name="rowsOnPage">
                <%--<option <c:if test="${requestScope.rowsOnPage ==  5}"> selected disabled </c:if> value= "5" >5</option>--%>
                <%--<option <c:if test="${requestScope.rowsOnPage == 10}"> selected disabled </c:if> value="10">10</option>--%>
                <%--<option <c:if test="${requestScope.rowsOnPage == 20}"> selected disabled </c:if> value="20">20</option>--%>
                <%--<option <c:if test="${requestScope.rowsOnPage == 50}"> selected disabled </c:if> value="50">50</option>--%>
                <c:if test="${requestScope.rowsOnPage !=  5}"> <option value= "5"> 5</option> </c:if>
                <c:if test="${requestScope.rowsOnPage != 10}"> <option value="10">10</option> </c:if>
                <c:if test="${requestScope.rowsOnPage != 20}"> <option value="20">20</option> </c:if>
                <c:if test="${requestScope.rowsOnPage != 50}"> <option value="50">50</option> </c:if>
            </select>
        </form>
    </li>
</ul>
