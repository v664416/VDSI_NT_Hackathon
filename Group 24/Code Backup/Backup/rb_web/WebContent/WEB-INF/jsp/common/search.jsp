<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
  <h2>Rongu-Bombu Inc Online library</h2>
  <form role="form">
    <div class="form-group">
      <input type="text" class="form-control" name="q" id="q" placeholder="Search Library">
    </div>
   <a href="#" id="srh" class="btn btn-info btn-lg"><span class="glyphicon glyphicon-search"></span> Search</a>
      <div class="table-responsive"> 
      </div>
  </form>
</div>


 <script type="text/javascript">
  	$("#srh").click(function(){
  		var qval = $("#q").val();
  		if(qval == ""){
  			return false;
  		}
  		$('.table-responsive').html("Searching.....");
  	    $.get("search/query?q="+qval, function(data, status){
  	      $('.table-responsive').html(data);
  	    });
  	    return false;
  	});
  </script>

</body>
</html>
