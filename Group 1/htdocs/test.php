<HTML>
<head>
</head>
<body>
<div id="result" style="font-size:200%; color:red;
    position: absolute;
    top: 50%;
    left: 35%;
    margin-top: -50px;
    margin-left: -50px;
      ">
<?php
$b=$_POST['bookname'];
$q="http://localhost:9200/book_db/book/_search?q=title:".$b;

$JSON = file_get_contents($q); 
$values = json_decode($JSON, true);
$time=$values['took']; 
$var1=$values['hits'];
$total=$var1['total'];
$var2=$var1['hits'];
$i=0;
while($i<$total)
{
	
$v=$var2[$i];
$var3=$v['_source'];
$var4=$var3['title'];
$var5=$var3['author'];
$var6=$var3['_id'];
echo "Book id:".$var6." Name:".$var4."  Author:".$var5."  Time taken:".$time."ms";
$i=$i+1;
?>
<br>
<?php
}
if($total==0)
	echo "book not found";


?>
</div>
</body>
</HTML>