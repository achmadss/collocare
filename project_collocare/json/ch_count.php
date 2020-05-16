<?php
    error_reporting(1);
    $title = $_GET["title"];
    $target_dir = "../chapters/";
    $target_file = $target_dir . basename($_FILES["file"]["name"]);

    echo $title+"<br>"+$target_file;

?>