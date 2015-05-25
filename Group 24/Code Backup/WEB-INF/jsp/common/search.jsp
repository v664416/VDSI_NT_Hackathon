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
      <label for="bktitle">Book Title</label>
      <input type="text" class="form-control" id="bookttl" placeholder="Enter Book Title">
    </div>
    <div class="form-group">
      <label for="aname">Author Name</label>
      <input type="text" class="form-control" id="bookaname" placeholder="Enter Author Name">
    </div>
    <div class="form-group">
      <label for="pname">Publisher Name</label>
      <input type="text" class="form-control" id="bookpname" placeholder="Enter Publisher Name">
    </div>
   <a href="#" id="srh" class="btn btn-info btn-lg"><span class="glyphicon glyphicon-search"></span> Search</a>
      <div class="table-responsive"> 
      <br/>         
      <table class="table table-bordered">
        <thead>
          <tr>
            <th>#</th>
            <th>Firstname</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>1</td>
            <td>Anna</td>
          </tr>
          <tr>
            <td>2</td>
            <td>Debbie</td>
          </tr>
          <tr>
            <td>3</td>
            <td>John</td>
          </tr>
        </tbody>
      </table>
      </div>
  </form>
</div>


 <script type="text/javascript">
  	$("#srh").click(function(){
  	    $.get("search/query", function(data, status){
  	      $('.table-responsive').html(data);
  	    });
  	    return false;
  	});
  </script>

</body>
</html>
