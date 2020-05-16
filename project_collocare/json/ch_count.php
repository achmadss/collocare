<?php
    error_reporting(1);
    $title = $_GET["title"];
    $target_dir = "../chapters/";
    

    echo $title+"<br>";

    foreach ($_FILES["inputAssets"]["name"] as $file) {
        echo $file+"<br>";
    }

?>