<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.List,DAO.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Part0 for 8578167 Die JIN</title>
<style>
table, th, td {
    border: 1px solid black;
}
</style>
</head>
<body>
<table>
<tr>
	<th colspan="4">CD Product table</th>
</tr>
<tr>
	<th>ID</th>
	<th>title</th>
	<th>price</th>
	<th>category</th>
</tr>
<% List<CD> cd_list= (List<CD>)request.getAttribute("cd_list"); 
	for (CD cd :cd_list) {
		String id = cd.getId();
		String title = cd.getTitle();
		Integer price = cd.getPrice();
		String category = cd.getCategory();
	%>
	<tr>
		<td><%=id%></td>
		<td><%=title%></td>
		<td><%=price%></td>
		<td><%=category%></td>
	</tr>
<% } %>
</table>
</body>
</html>