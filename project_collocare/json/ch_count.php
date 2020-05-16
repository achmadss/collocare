<?php
    $title = $_GET["title"];
    $target_dir = "../chapters/";
    $target_file = $target_dir . basename($_FILES["fileToUpload"]["name"]);

    echo $title+"<br>"+$target_file;

?>