 <%--works only with Helper.longListByPages(...) !!!--%>

<%--<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>--%>

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
    <!--li>Записей на странице: <b>${requestScope.rowsOnPage}</b>  </li-->

    <li>
        <form action="${requestScope.url}" method="get" id="pagesTune" class="horizontal">
            <input form="pagesTune" type="hidden" name="changeRowsOnPage" value="${true}"/>
            <select required name="rowsOnPage" onchange="$(this).parent().submit();">
                <option value="5" <c:if test="${requestScope.rowsOnPage == 5}">selected</c:if>>5</option>
                <option value="10" <c:if test="${requestScope.rowsOnPage == 10}">selected</c:if>>10</option>
                <option value="20" <c:if test="${requestScope.rowsOnPage == 20}">selected</c:if>>20</option>
                <option value="50" <c:if test="${requestScope.rowsOnPage == 50}">selected</c:if>>50</option>
            </select>
        </form>
    </li>
</ul>
