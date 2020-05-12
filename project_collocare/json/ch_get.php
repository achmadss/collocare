<?php
    $chapter = $_GET["ch"];
    $result->chapter = $chapter;
    $images = array();
    foreach(glob("../chapters/chapter".$chapter."/*.*") as $file) {
        array_push($images, "http://collocare.herokuapp.com/project_collocare/chapters/chapter".$chapter."/".basename($file));
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