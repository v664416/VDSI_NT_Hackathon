<?php
$host="localhost"; // Host name
$username="root"; // Mysql username
$password=""; // Mysql password
$db_name="library"; // Database name
$tbl_name="books"; // Table name

// Connect to server and select database.
mysql_connect("$host", "$username", "$password")or
die("cannot connect");
mysql_select_db("$db_name")or die("cannot select DB");

// username and password sent from form
$book=$_POST['bookname'];
$sql="SELECT * FROM $tbl_name WHERE bookname='$book'";
$result=mysql_query($sql);
$count=mysql_num_rows($result);
if($count==1)
	echo "Success";
else
 echo "Failed";
?>