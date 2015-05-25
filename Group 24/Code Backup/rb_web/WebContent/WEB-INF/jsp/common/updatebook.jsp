<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Book Details</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<style>
body{
	background-color:#cyan;
	color: #333;
    font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
    font-size: 14px;
    line-height: 1.42857;
    font-weight:bold;
}
</style>

<script type="text/javascript">

	function t1() {
		var urlp = "updateBook?" + "authoname=" + $("#authoname").val()+ "&category=" + $("#category").val() +"&bookName=" + $("#bookName").val() + "&publisher=" + $("#publisher").val()
					+ "&publisher=" + $("#publisher").val()+ "&publisher=" + $("#publisher").val()+ "&type=" + $("#type").val() + "&docid=" + $("#docid").val();
		  $.ajax({url: urlp, success: function(result){
		        if (result == "success") {
					$("#d1").html("<span style=\"color:#b07200;\">Data Updated succssfullly</span>");
				} else {
					$("#msg").html(result);
				}
		    }});
		return false;
	}
</script>
</head>
<body>
	<div id="msg" style="color:#f00;"></div>
	<div id="d1">
		<h1>Update Book Details</h1>
			<div class="lbl">Author Name:</div> <input type="text" value="<%= request.getParameter("authoname") %>" name="authoname" id="authoname" required="required"/><br/><br/>
			<div class="lbl">Category:</div> <input type="text" value="<%= request.getParameter("category") %>" name="category" id="category" required="required"/><br/><br/>
			<div class="lbl">Book Name:</div> <input type="text" value="<%= request.getParameter("bookName") %>" name="bookName" id="bookName" required="required"/><br/><br/>
			<div class="lbl">Publisher:</div> <input type="text" value="<%= request.getParameter("publisher") %>" name="publisher" id="publisher" required="required"/><br/><br/><br/>
			<input type="hidden" name="type" id="type"  value="<%= request.getParameter("type") %>"/>
			<input type="hidden" name="docid" id="docid" value="<%= request.getParameter("docid") %>"/>
			<input id="fm1" type="button" value="UpdateBook" onclick="{return t1();}"> 
			
	</div>
</body>
</html>