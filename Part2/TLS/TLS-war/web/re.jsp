
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList, appHelper.ReservationState, java.util.Date" %>

<html>
    <head>
        <title>Reservations</title>
    </head>
    <body>
        <h1 align="center">Reservations</h1>
        <form action="index" method="post">
            <table align="center" border="1">
                <tr>
                    <th>ID</th>
                    <th>Date</th>
                    <th>Book Title</th>
                    <th>Book ID</th>
                    <th>Reservation Result</th>

                    <%
            ArrayList reserveStates = (ArrayList) request.getAttribute("data");
            for (int i = 0; i < reserveStates.size(); i++) {
                ReservationState rs = (ReservationState) reserveStates.get(i);

                if (rs.getStatus().equals("Reservation successful!")) {
                    %>
                <tr>
                    <td align="center"><%=rs.getId()%></td>
                    <td align="center"><%=rs.getReserveDate().getTime()%></td>
                    <td align="center"><%=rs.getBook().getTitle().getTitle()%></td>
                    <td align="center"><%=rs.getBook().getId()%></td>
                    <td align="center"><%=rs.getStatus()%></td>
                </tr>
                <%
                } else {
                %>
                <tr>
                    <td align="center"> - </td>
                    <td align="center"> - </td>
                    <td align="center"> - </td>
                    <td align="center"> - </td>
                    <td align="center"><%=rs.getStatus()%></td>
                </tr>
                <%
                }
                %>
                <%
            }
                %>

            </table>
            <center>
                <p align="center"><input type="submit" value="Log off"/></p>
            </center>
        </form>
    </body>
</html>
