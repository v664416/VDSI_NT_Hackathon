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

#table-wrapper {
  position:relative;
}
#bookGrid {
  height:600px;
  overflow:auto;  
  margin-top:20px;
}

#table-wrapper table tbody tr:nth-of-type(odd) {
   font-family: Verdana,Geneva,sans-serif;
   font-size: 14px;
   background-color:#F2F2F2;
}

#table-wrapper table tbody tr:nth-of-type(even) {
   font-family: Verdana,Geneva,sans-serif;
   font-size: 14px;
   background-color:#fff;
}

#table-wrapper table tbody tr td {
   padding-left:10px;
}

#table-wrapper table thead th .text {
  position:absolute;   
  top:-20px;
  z-index:2;
  height:25px;
  width: 50%;
  background-color:#E5E4E2;
  font-family: Verdana,Geneva,sans-serif;
  font-size: 14px;
}

</style>

</head>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="js/jquery.columns-1.0.min.js"></script> 
<script>
	function searchBooks(startRow){
		$("#bookGrid").empty();
		$("#responseDiv").empty();
		
		$("#bookGrid").html("Loading...");
		
		var bookName = document.SearchForm.bookName.value;
		var authorName = document.SearchForm.authorName.value;
		
		var gridHTML = "<table cellpadding = 2 cellpadding = 2 width='95%'>";
		gridHTML += "<tr><thead>";
		gridHTML += "<th><span class='text'>Book Name</span></th>";
		gridHTML += "<th><span class='text'>Author Name</span></th>";
		gridHTML += "</tr></thead><tbody>";
		
		var paginationHTML = "";
		var responseTimeHTML = "";
		
		var sendDate = (new Date()).getTime();
		
		var searchURL = "searchServlet?startRow="+startRow+"&bookName="+bookName+"&authorName="+authorName+"&rand="+Math.random();
	     $.ajax({url: searchURL, dataType: 'json', success: function(result){
	    	 var displayResult = result.RESULTS;
	    	 var nextRow = result.NEXT_ROW;
	    	 var searchFlag = result.SEARCH_FLAG;
	    	 
	    	 for(var i=0; i< displayResult.length; i++){
	    		 gridHTML += "<tr>";
	    		 gridHTML += "<td>" + displayResult[i].bookName + "</td>";
	    		 gridHTML += "<td>" + displayResult[i].authorName + "</td>";
	    		 gridHTML += "</tr>";
	    	 }
	    	 
	    	 gridHTML += "</tbody></table>";
	    	 
	    	 $("#bookGrid").html(gridHTML);
	    	 
	    	 paginationHTML += "<table width='95%' border=0>";
	    	 
	    	 paginationHTML += "<tr><td align='right'><table cellspacing=5 align='right'><tr>";
	    	 
	    	 if(startRow > 0)
	    		 paginationHTML += "<td><a href='#' onclick='searchBooks("+(startRow - 1000)+")'><img src='images/arrow-left.png'/></a></td>";
	    	 	
	    	 paginationHTML += "<td><a href='#' onclick='searchBooks("+(startRow + 1000)+")'><img src='images/arrow-right.png'/></a></td>";
	    	 
	    		 
	    	 paginationHTML += "</tr></table></td></tr></table>";
	    	 
	    	 $("#paginationDiv").html(paginationHTML);
	    	 
	    	 var receivedDate = (new Date()).getTime();
	    	 
	    	 var responseTime = receivedDate - sendDate;
	    	 
	    	 responseTimeHTML += "<table><tr><td style='font-family: Verdana,Geneva,sans-serif;font-size:12px;'><b>Response Time: </b>" + (responseTime * 0.001) + " in sec(s)." + "</td></tr></table>";
	    	 
	    	 $("#responseDiv").html(responseTimeHTML);
	    	 
	    	 if(displayResult.length > 0 && searchFlag == 1){
	    		 parallelSearch(nextRow, 0);
	    	 }
	    	
	     }});
	}
	
	function parallelSearch(startRow, totalTimes){
		var bookName = document.SearchForm.bookName.value;
		var authorName = document.SearchForm.authorName.value;
		
		var searchURL = "searchServlet?startRow="+startRow+"&bookName="+bookName+"&authorName="+authorName+"&totalTimes="+totalTimes+"&isParallelSearch=true&rand="+Math.random();
		 $.ajax({url: searchURL, dataType: 'json', success: function(result){
			 if(result.NEXT_ROW > 0 && totalTimes <= 5){
			 	parallelSearch(result.NEXT_ROW, result.totalTimes);
			 }
		 }});
	}
</script>
<body>
<form name="SearchForm" method = "POST" action = "searchServlet">
	<div>
		<table cellspacing = 5 cellpadding = 5>
			<tr>
				<td>Book Name: </td>
				<td>Author Name: </td>
			</tr>
			<tr>
				<td><input type="text" name="bookName" id="bookName"/>
				</td>
				<td><input type="text" name="authorName" id="authorName"/>
				</td>
			</tr>
			<tr>
				<td colspan=2><input type="button"  value="Search" name="searchBtn" onclick="searchBooks(0, 0);"/>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="table-wrapper">
  		<div id="bookGrid">
  		</div>
  	</div>
  	<div id="paginationDiv"></div>
  	<div id="responseDiv"></div>
	
 </form>
</body>
</html>