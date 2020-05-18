<?php
    $title = $_POST["title"];
    if (isset($_FILES['inputAssets'])) {
        $myFile = $_FILES['inputAssets'];
        $fileCount = count($myFile["name"]);
        mkdir("tes");
    }
    print_r(error_get_last());
    // if(count($_FILES['uploads']['inputAssets'])) {
    //     foreach ($_FILES['uploads']['inputAssets'] as $file) {
            
    //         //do your upload stuff here
    //         echo $file;
            
    //     }
    // }

?>