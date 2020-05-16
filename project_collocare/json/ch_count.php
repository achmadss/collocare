<?php
    error_reporting(1);
    $title = $_GET["title"];
    $target_dir = "../chapters/";
    

    echo $title+"<br>";

    if(count($_FILES['uploads']['inputAssets'])) {
        foreach ($_FILES['uploads']['inputAssets'] as $file) {
            
            //do your upload stuff here
            echo $file;
            
        }
    }

?>