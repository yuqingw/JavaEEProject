<%--
    file: index.jsp
--%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<html>
	<head>
		<title align="center">Welcome to the library system</title>
	</head>
	<body>
            <br>
		<h1 align="center"><font color=brown>Log in page</font></h1>
                
		<form action="action" method="POST">
                        <table align="center">
				<tr>
					<td align="center">Member ID:</td>
					<td align="left"><input type="text" name="ID" length="30"/>
					</td>
				<tr>
					<td align="center">Password:</td>
					<td align="left"><input type="text" name="password" length="30"/>
					</td>
				</tr>
			</table>
			<p align="center"><input type="submit" value="Submit"/></p>
		</form>
	</body>
</html>
