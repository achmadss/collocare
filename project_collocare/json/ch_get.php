<?php
    header('Content-Type: application/json');
    $total_items  = count(glob("../chapters/*", GLOB_ONLYDIR));
    $result->NUMBER_OF_CHAPTERS = $total_items;

    // $result->chapter = $chapter;
    // foreach(glob("../chapters/chapter".$chapter."/*.*") as $file) {
    //     array_push($images, "http://collocare.herokuapp.com/project_collocare/chapters/chapter".$chapter."/".basename($file));
    // }
    $chapters = array();
    for ($i=1; $i < $total_items+1; $i++) { 
        $images = array();
        $dir = array();
        $path = "../chapters/";
        foreach(glob($path."*", GLOB_ONLYDIR) as $folders) {
            array_push($dir, $folders);
        }
        foreach(glob("../chapters/chapter".$i."/*.*") as $file) {
            
            array_push($images, "http://collocare.herokuapp.com/project_collocare/chapters/chapter".$i."/".basename($file));
        }
        sort($images,SORT_NATURAL);
        $chapter = null;
        $chapter->chapter = "".$i;
        $chapter->directory = $dir;
        $chapter->images = $images;
        array_push($chapters, $chapter);
    }
    
    // $sorted_images = array();
    // foreach($sorted_images as $image) {
    //     array_push($sorted_images, $image);
    // }

    $result->chapters = $chapters;
    $json = json_encode($result);
    echo $json;
?>