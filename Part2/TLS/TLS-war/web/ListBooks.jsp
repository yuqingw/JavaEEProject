<%--
    file: ListBooks.jsp
--%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList, appHelper.BookState, java.util.Date" %>

<html>
    <head>
        <title>ListBooks</title>
    </head>
    <body>
        <h1 align="center">List of books borrowed</h1>
        <form action="Renew" method="post">
        <table align="center" border="1">
            <tr>
                <th></th>
                <th>ID</th>
                <th>Loan Date</th>
                <th>Due Date</th>
        <%
            ArrayList bookStates = (ArrayList)
                request.getAttribute("data");
            for (int i=0; i<bookStates.size(); i++) {
                BookState bs = (BookState)bookStates.get(i);
        %>
            <tr>
                <td align="center"><input type ="checkbox" name="bookId" value=<%=bs.getId()%>></td>
                <td align="center"><%= bs.getId()%></td>
                <td align="center"><%= bs.getLoanDate().getTime()%></td>
                <td align="center"><%= bs.getDueDate().getTime()%></td>
            </tr>
        <%
            }
        %>
        </table>
                <center>
                    <p align="center"><input type="submit" value="Renew"/></p>
                </center>
        </form>
        <form action="index" method="post">
            <center>
                <p align="center"><input type="submit" value="Log Off"></p>
            </center>
        </form>
    </body>
</html>
