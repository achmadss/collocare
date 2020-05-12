<?php
    $total_items  = count(glob("../chapters/*", GLOB_ONLYDIR));
    $result->NUMBER_OF_CHAPTERS = $total_items;
    // $chapters = array();
    // $result->chapters = $chapters;
    $json = json_encode($result);
    echo $json;
    header('Content-Type: application/json');
?>