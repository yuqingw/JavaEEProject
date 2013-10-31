<%--
    file: Renew.jsp
--%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList, appHelper.BookState, java.util.Date" %>

<html>
    <head>
        <title>RenewBooks</title>
    </head>
    <body>
        <h1 align="center">Status of all books renewed</h1>
        <table align="center" border="1">
            <tr>
                <th>ID</th>
                <th>Loan Date</th>
                <th>Due Date</th>
                <th>Renew Information</th>
                <%
                    ArrayList bookStates = (ArrayList) request.getAttribute("data");
                    for (int i = 0; i < bookStates.size(); i++) {
                        BookState bs = (BookState) bookStates.get(i);
                %>
            <tr>
                <td align="center"><%= bs.getId()%></td>
                <td align="center"><%= bs.getLoanDate().getTime()%></td>
                <td align="center"><%= bs.getDueDate().getTime()%></td>
                <td align="center"><%= bs.getRenew()%></td>
            </tr>
            <%
                }
            %>
        </table>
        <form action="index" method="post">
            <p align="center"><input type="submit" value="Log off"/></p>
        </form>

    </body>
</html>
