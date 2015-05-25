<HTML>
<BODY>
<?php
require 'vendor/autoload.php';
echo "Welcome to Elastic Search";

$_esConnParam['hosts'] = array(
'127.0.0.1:9200'
);
$_esConn = new Elasticsearch\Client($_esConnParam);


?> 
</BODY>
</HTML>