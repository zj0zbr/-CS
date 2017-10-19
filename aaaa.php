<?php
/*$data = array(
    'name' => '脚本之家',
    'infor' => 'Gonn',
        'age' => 'xxxxxxx@163.com',


);
$json_string = json_encode($data);
echo "getProfile($json_string)";*/
function write_to_log( $logthis ){
    file_put_contents('logfile.log', date("Y-m-d H:i:s"). " " . $logthis. "\r\n", FILE_APPEND | LOCK_EX);
}
write_to_log("1111111111111111111111111111111");
write_to_log("1111111111111111111111111111111111111111" );
 echo  json_encode(array(
    'name' =>'脚本之家',
    'infor' => 'Gonn',
    'age' => 'xxxxxxx@163.com',
));
?>