<?php
    header('Content-Type: application/json');
    $total_items  = count(glob("../chapters/*", GLOB_ONLYDIR));
    $result->NUMBER_OF_CHAPTERS = $total_items;

    $dir = array();
    $path = "../chapters/";
    foreach(glob($path."*", GLOB_ONLYDIR) as $folders) {
        $folder2 = substr($folders, 12);
        $folderFix = "".$folder2;
        array_push($dir, $folderFix);
    }
    $chapters = array();
    for ($i=1; $i < $total_items+1; $i++) { 
        $images = array();
        
        foreach(glob("../chapters/chapter".$i."/*.*") as $file) {
            
            array_push($images, "http://collocare.herokuapp.com/project_collocare/chapters/chapter".$i."/".basename($file));
        }
        sort($images,SORT_NATURAL);
        $chapter = null;
        $chapter->chapter = "".$i;
        $chapter->folder = $dir[$i-1];
        $chapter->images = $images;
        array_push($chapters, $chapter);
    }

    $result->chapters = $chapters;
    $json = json_encode($result);
    echo $json;
?>