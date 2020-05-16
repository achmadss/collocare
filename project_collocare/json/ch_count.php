<?php
    error_reporting(1);
    // $title = $_GET["title"];
    if (isset($_FILES['inputAssets'])) {
        $myFile = $_FILES['inputAssets'];
        $fileCount = count($myFile["name"]);

        for ($i = 0; $i < $fileCount; $i++) {
            ?>
                <p>File #<?= $i+1 ?>:</p>
                <p>
                    Name: <?= $myFile["name"][$i] ?><br>
                    Temporary file: <?= $myFile["tmp_name"][$i] ?><br>
                    Type: <?= $myFile["type"][$i] ?><br>
                    Size: <?= $myFile["size"][$i] ?><br>
                    Error: <?= $myFile["error"][$i] ?><br>
                </p>
            <?php
        }
    }

    // if(count($_FILES['uploads']['inputAssets'])) {
    //     foreach ($_FILES['uploads']['inputAssets'] as $file) {
            
    //         //do your upload stuff here
    //         echo $file;
            
    //     }
    // }

?>