<?php 
$username = "s2709427";
$password = "s2709427";
$database = "d2709427";
$host = "127.0.0.1";

$link = mysqli_connect($host, $username, $password, $database);

if (!$link) {
    die("Connection failed: " . mysqli_connect_error());
}

// Get request parameters
$user_id_1 = $_REQUEST["user_id_1"];
$user_id_2 = $_REQUEST["user_id_2"];
$new_status = $_REQUEST["status"]; //'accepted'/'declined'
$output = [];

// Validate status
$valid_statuses = ['accepted', 'declined'];
if (!in_array($new_status, $valid_statuses)) {
    $output["status"] = "error";
    $output["message"] = "Invalid status value.";
    echo json_encode($output);
    exit;
}

// Update the status
$query = "
    UPDATE friends 
    SET status = '$new_status'
    WHERE user_id_1 = $user_id_1 AND user_id_2 = $user_id_2
";

if (mysqli_query($link, $query)) {
    if (mysqli_affected_rows($link) > 0) {
        $output["status"] = "success";
        $output["message"] = "Friendship status updated to '$new_status'.";
    } else {
        $output["status"] = "error";
        $output["message"] = "No matching friendship found.";
    }
} else {
    $output["status"] = "error";
    $output["message"] = "Failed to update status.";
}

mysqli_close($link);
echo json_encode($output);
?>
