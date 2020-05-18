<?php
    $title = $_POST["title"];
    if (isset($_FILES['inputAssets'])) {
        $myFile = $_FILES['inputAssets'];
        $fileCount = count($myFile["name"]);
        $dir = "project_collocare/chapters/"+$title;
        
        rmdir($dir);
        // for($i = 0; $i < $fileCount; $i++) {
        //     $temp = $myFile["tmp_name"][$i];
        //     $newDir = $dir . $myFile["name"][$i];
        //     move_uploaded_file($temp, $newDir);
        // }
    }
    print_r(error_get_last());
    // if(count($_FILES['uploads']['inputAssets'])) {
    //     foreach ($_FILES['uploads']['inputAssets'] as $file) {
            
    //         //do your upload stuff here
    //         echo $file;
            
    //     }
    // }

?>