<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
$myid = $_REQUEST["myid"];


$myid = intval($myid);

$username = ""; // fill this
$password = "";// fill this
$database = "";// fill this


$link = mysqli_connect("127.0.0.1", $username, $password, $database);

if (!$link) {
    die("Connection failed: " . mysqli_connect_error());
}

$output = array();

$stmt = $link->prepare("SELECT * FROM friends WHERE user_1_id = ?");
$stmt->bind_param("i", $myid);
$stmt->execute();

$result = $stmt->get_result();

while ($row = $result->fetch_assoc()) {
    $output[] = $row;
}

?>
