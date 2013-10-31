
<%--
    file: searchAuthor.jsp
--%>

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList, appHelper.BookState, java.util.Date" %>

<html>
    <head>
        <title>List the Books</title>
    </head>
    <body>
        <h1 align="center">List of books by the author</h1>
        <form action="re" method="post">
        <table align="center" border="1">
            <tr>
                <th></th>
                <th>ID</th>
                <th>Loan Date</th>
                <th>Due Date</th>
                <th>Title</th>
        <%
            ArrayList bookStates = (ArrayList)
                request.getAttribute("data");
            for (int i=0; i<bookStates.size(); i++) {
               BookState bs = (BookState)bookStates.get(i);
        %>
        
              <tr>
                <td align="center"><input type ="checkbox" name="bookId" value=<%=bs.getId()%>></td>
                         <td align="center"><%= bs.getId()%></td>
                         <% 
                            if(bs.getLoanDate()==null){
                         %>
                         <td align="center"><%= "-"%></td>
                         <%
                            } else{
                         %>
                          <td align="center"><%= bs.getLoanDate().getTime()%></td>
                         <%
                           } if(bs.getDueDate()==null){
                         %>
                         <td align="center"><%= "-"%></td>
                         <%
                         } else {
                         %>
                         <td align="center"><%= bs.getDueDate().getTime()%></td>
                         <%
                         }
                         %>
                         <td align="center"><%= bs.getTitle().getTitle()%></td>
            </tr>
        <%
            }
        %>
        </table>
                <center>
                <input type="submit" value="Reserve"/>
                </center>
        </form>
    </body>
</html>
