<?php
    $total_items  = count(glob("../chapters/*", GLOB_ONLYDIR));
    $result->NUMBER_OF_CHAPTERS = $total_items;

    // $result->chapter = $chapter;
    // foreach(glob("../chapters/chapter".$chapter."/*.*") as $file) {
    //     array_push($images, "http://collocare.herokuapp.com/project_collocare/chapters/chapter".$chapter."/".basename($file));
    // }
    $chapters = array();
    for ($i=1; $i < $total_items+1; $i++) { 
        
        foreach(glob("../chapters/chapter".$i."/*.*") as $file) {
            $images = array();
            array_push($images, "http://collocare.herokuapp.com/project_collocare/chapters/chapter".$i."/".basename($file));
        }
        sort($images,SORT_NATURAL);
        array_push($chapters, "".$i);
        array_push($chapters, $images);
    }
    
    // $sorted_images = array();
    // foreach($sorted_images as $image) {
    //     array_push($sorted_images, $image);
    // }

    $result->chapters = $chapters;
    $json = json_encode($result);
    echo $json;
    header('Content-Type: application/json');
?>