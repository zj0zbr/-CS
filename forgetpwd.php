<?php
session_start();
function write_to_log( $logthis ){
    file_put_contents('logfile.log', date("Y-m-d H:i:s"). " " . $logthis. "\r\n", FILE_APPEND | LOCK_EX);
}
//session传输一次长期保存，保存在服务器端
header("content-type:text/html; charset=UTF-8");

$userName = $_POST['hiddenNum'];
$password = $_POST['hiddenPass'];
$rpassword = $_POST['hiddenrPass'];

$bFlg = "0";
$isAdmin = "0";


write_to_log('username:'.$userName );
write_to_log('password:'.$password );
write_to_log('rpassword:'.$rpassword );

//echo "g".$bFlg;
//echo "d".$isAdmin;

//$myContents = str_replace("\r\n",",",$contents);
//$myContents = substr($myContents,0,strlen($myContents)-1);
//echo $myContents;
//if(strpos($myContents,",")==false){
//    $array = explode('_', $myContents);

//echo "aa";
//    $dbusername = $array[0];
//    $dbpassword = $array[1];
//echo $dbusername."==".$userName."&&".$dbpassword."==".$password;
//echo "xxx";
//    if ($dbusername==$userName) {
//            //echo "yyy";
//            $bFlg = "1";
//            $_SESSION['myusername'] = $userName;
//            if ($userName == "12345678900") {
//                $isAdmin = "1";
//            }
//        }
//    }
//}else {
//    $arr = explode(',', $myContents);

//    foreach ($arr as $str) {
//        $array = explode('_', $str);

//       $dbusername = $array[0];
//       $dbpassword = $array[1];

//        if ($dbusername==$userName && $dbpassword==$password) {
//           $bFlg = "1";
//            $_SESSION['myusername'] = $userName;
//            if ($userName == "12345678900") {
//               $isAdmin = "1";
//         }
//       }
//   }
//}

$dbusername = "15122053766";

$dbpassword = "123456a";
$dbrpassword ="123456a";

//echo $userName;
//echo $password;

if ($dbusername==$userName && $dbpassword==$password && $dbrpassword==$rpassword) {
    $bFlg = "1";

    $_SESSION['userName'] = $userName;

    //$_SESSION['myusername'] = $userName;
    //if ($userName == "12345678900") {
    //    $isAdmin = "1";
    //}
}


//echo "aaa".$bFlg;
//echo "bbb".$isAdmin;
//if($bFlg=="1" && $isAdmin=="1"){
//   echo "2";
//}else
if($bFlg=="1"){
    echo "1";
}else{
    echo "0";
}