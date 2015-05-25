<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<link id="style" href="css/clean.css" rel="stylesheet" media="screen">
<link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">

<style>
body {
font-family: helvetica, sans-serif;
}
.source {
font-family: monospace;
padding: 10px;
}
</style>

</head>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="js/jquery.columns-1.0.min.js"></script> 
<script>
	
	function prePopulate(){
		 var bookJson = <%=request.getAttribute("BOOK_GRID") %>;

		
		document.SearchForm.bookName.value = '<%=request.getAttribute("bookName") == null ? "" : request.getAttribute("bookName")%>';
		document.SearchForm.authorName.value = '<%=request.getAttribute("authorName") == null ? "" : request.getAttribute("authorName") %>';
		
		 
		$('.bookGrid').columns({
			data:bookJson
		}); 
	}
	
	function searchBooks(){
		document.SearchForm.submit();
	}
</script>
<body onload="prePopulate();"s>
<form name="SearchForm" method = "POST" action = "searchServlet">
	<div>
		<table cellspacing = 5 cellpadding = 5>
			<tr>
				<td>Book Name: </td>
				<td>Author Name: </td><c:out value="This is JSTL"/>
			</tr>
			<tr>
				<td><input type="text" name="bookName" id="bookName"/>
				</td>
				<td><input type="text" name="authorName" id="authorName"/>
				</td>
			</tr>
			<tr>
				<td colspan=2><input type="button"  value="Search" name="searchBtn" onclick="searchBooks();"/>
				</td>
			</tr>
		</table>
	</div>
	<div class="bookGrid" style="height: 400px"></div>
 </form>
</body>
</html>