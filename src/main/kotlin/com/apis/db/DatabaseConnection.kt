package com.apis.db

import org.ktorm.database.Database

object DatabaseConnection {

    val dbuser = "root"
    val dbpassword = "root"
    val dburl = "jdbc:mysql://localhost:3306/ZeeNotes"
    val driver = "com.mysql.cj.jdbc.Driver"

    val database = Database.connect(
        url = dburl, driver, user = dbuser , password = dbpassword )

}