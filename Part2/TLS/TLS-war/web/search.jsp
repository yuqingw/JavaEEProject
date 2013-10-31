<%--
    Document   : action
    Created on : Mar 27, 2013, 10:06:22 AM
    Author     : surfer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head><title>The Library System</title></head>
    <body bgcolor="white">
        <h1 align="center">Search for Books<br></h1>
                    <form action="searchAuthor" method="POST">
                        <table align="center">
				<tr>
					<td align="center">Author name:</td>
					<td align="left"><input type="text" name="name" length="30"/>
					</td>
				</tr>
			</table>
			<p align="center"><input type="submit" value="search"/></p>
                   </form>
                   <form action="searchTitle" method="POST">
                        <table align="center">
				<tr>
					<td align="center">Title:</td>
					<td align="left"><input type="text" name="title" length="30"/>
					</td>
				</tr>
			</table>
			<p align="center"><input type="submit" value="search"/></p>
                   </form>
    </body>
</html>
