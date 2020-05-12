<?php
    $chapter = $_GET["ch"];
    $total_items  = count(glob("../chapters/*", GLOB_ONLYDIR));
    $result->NUMBER_OF_CHAPTERS = $total_items;
    // $result->chapter = $chapter;
    
    $images = array();
    // foreach(glob("../chapters/chapter".$chapter."/*.*") as $file) {
    //     array_push($images, "http://collocare.herokuapp.com/project_collocare/chapters/chapter".$chapter."/".basename($file));
    // }

    foreach(glob("../chapters/*.*") as $file) {
        array_push($images, $file);
    }
    sort($images,SORT_NATURAL);
    // $sorted_images = array();
    // foreach($sorted_images as $image) {
    //     array_push($sorted_images, $image);
    // }
    $result->images = $images;
    $json = json_encode($result);
    echo $json;
    header('Content-Type: application/json');
?>