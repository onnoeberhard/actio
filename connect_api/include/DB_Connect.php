<?php
class DB_Connect {

    // constructor
    function __construct() {
        
    }

    // destructor
    function __destruct() {
        // $this->close();
    }

    // Connecting to database
    public function connect() {
        //require_once 'include/config.php';
        // connecting to mysql
        $con = mysql_connect("localhost", "actio", "bplaceD22321");
        // selecting database
        mysql_select_db("actio");

        // return database handler
        return $con;
    }

    // Closing database connection
    public function close() {
        mysql_close();
    }

}

?>
