
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList, appHelper.ReservationState, java.util.Date" %>

<html>
    <head>
        <title>Reservations</title>
    </head>
    <body>
        <h1 align="center">Reservations made</h1>
        <form action="delete" method="post">
            <table align="center" border="1">
                <tr>
                    <th></th>
                    <th>ID</th>
                    <th>Member</th>
                    <th>Book Title</th>
                    <th>Book ID</th>

                    <%
                        ArrayList reserveStates = (ArrayList) request.getAttribute("data");
                        for (int i = 0; i < reserveStates.size(); i++) {
                            ReservationState rs = (ReservationState) reserveStates.get(i);

                    %>
                <tr>
                    <td align="center"><input type ="checkbox" name="reId" value=<%=rs.getId()%>></td>
                    <td align="center"><%=rs.getId()%></td>
                    <td align="center"><%=rs.getMember().getName()%></td>
                    <td align="center"><%=rs.getBook().getTitle().getTitle()%></td>
                    <td align="center"><%=rs.getBook().getId()%></td>
                </tr>
                <%
                    }
                %>
            </table>
            <center>
                <input type="submit" value="Delete reservation"/>
            </center>
        </form>
            <form action="index" method="post">
                <center>
                    <p align="center"><input type="submit" value="Log Off"/></p>
                </center>
            </form>
    </body>
</html>
