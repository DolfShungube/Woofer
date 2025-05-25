<?php
// Show errors for debugging
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

// Database credentials
$host = "127.0.0.1";
$username = "s2709427";
$password = "s2709427";
$database = "d2709427";

// Connect to MySQL
$link = mysqli_connect($host, $username, $password, $database);
if (!$link) {
    die(json_encode([
        "status" => "error",
        "message" => "Database connection failed: " . mysqli_connect_error()
    ]));
}

// Get and sanitize inputs
$user_id_1 = isset($_REQUEST["user_id_1"]) ? intval($_REQUEST["user_id_1"]) : 0;
$user_id_2 = isset($_REQUEST["user_id_2"]) ? intval($_REQUEST["user_id_2"]) : 0;

$output = [];

// Prevent self-friendship
if ($user_id_1 === $user_id_2 || $user_id_1 <= 0 || $user_id_2 <= 0) {
    $output["status"] = "error";
    $output["message"] = "Invalid input or self-friendship is not allowed.";
    echo json_encode($output);
    exit;
}

// Check if both users exist
$stmt = mysqli_prepare($link, "SELECT COUNT(*) as total FROM users WHERE id IN (?, ?)");
mysqli_stmt_bind_param($stmt, "ii", $user_id_1, $user_id_2);
mysqli_stmt_execute($stmt);
$result = mysqli_stmt_get_result($stmt);
$row = mysqli_fetch_assoc($result);

if ($row["total"] < 2) {
    $output["status"] = "error";
    $output["message"] = "One or both users do not exist.";
    echo json_encode($output);
    exit;
}

// Check if friendship already exists
$stmt = mysqli_prepare($link, "
    SELECT * FROM friends 
    WHERE (user_id_1 = ? AND user_id_2 = ?) OR (user_id_1 = ? AND user_id_2 = ?)
");
mysqli_stmt_bind_param($stmt, "iiii", $user_id_1, $user_id_2, $user_id_2, $user_id_1);
mysqli_stmt_execute($stmt);
$result = mysqli_stmt_get_result($stmt);

if (mysqli_num_rows($result) > 0) {
    $output["status"] = "error";
    $output["message"] = "Friendship already exists or is pending.";
    echo json_encode($output);
    exit;
}

// Insert friend request with status 'pending'
$stmt = mysqli_prepare($link, "
    INSERT INTO friends (user_id_1, user_id_2, status) 
    VALUES (?, ?, 'pending')
");
mysqli_stmt_bind_param($stmt, "ii", $user_id_1, $user_id_2);
$success = mysqli_stmt_execute($stmt);

if ($success) {
    $output["status"] = "success";
    $output["message"] = "Friend request sent.";
} else {
    $output["status"] = "error";
    $output["message"] = "Failed to send friend request.";
}

mysqli_close($link);
echo json_encode($output);
?>
