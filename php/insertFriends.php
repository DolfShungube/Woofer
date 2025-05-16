<?php

$username = "s2709427";
$password = "s2709427";
$database = "d2709427";
$host = "127.0.0.1";

$link = mysqli_connect($host, $username, $password, $database);

if (!$link) {
    die("Connection failed: " . mysqli_connect_error());
}

// Get input values
$user_id_1 = $_REQUEST["user_id_1"];
$user_id_2 = $_REQUEST["user_id_2"];
$output = [];

// Prevent self-friendship
if ($user_id_1 == $user_id_2) {
    $output["status"] = "error";
    $output["message"] = "You cannot add yourself as a friend.";
    echo json_encode($output);
    exit;
}

// Check if users exist
$check_users = mysqli_query($link, "SELECT id FROM users WHERE id IN ($user_id_1, $user_id_2)");
if (mysqli_num_rows($check_users) < 2) {
    $output["status"] = "error";
    $output["message"] = "One or both users do not exist.";
    echo json_encode($output);
    exit;
}

// Check if friendship already exists (in either direction)
$check_friend = mysqli_query($link, "
    SELECT * FROM friends 
    WHERE (user_id_1 = $user_id_1 AND user_id_2 = $user_id_2)
       OR (user_id_1 = $user_id_2 AND user_id_2 = $user_id_1)
");
if (mysqli_num_rows($check_friend) > 0) {
    $output["status"] = "error";
    $output["message"] = "Friendship already exists or is pending.";
    echo json_encode($output);
    exit;
}

// Insert pending friendship (only one direction; the other user must accept)
$insert = mysqli_query($link, "
    INSERT INTO friends (user_id_1, user_id_2, status) 
    VALUES ($user_id_1, $user_id_2, 'pending')
");

if ($insert) {
    $output["status"] = "success";
    $output["message"] = "Friend request sent.";
} else {
    $output["status"] = "error";
    $output["message"] = "Failed to send friend request.";
}

mysqli_close($link);
echo json_encode($output);
?>
