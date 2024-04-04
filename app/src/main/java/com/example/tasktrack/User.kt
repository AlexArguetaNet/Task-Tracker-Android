package com.example.tasktrack

class User(var username: String = "", var email: String = "", var id: String = "") {

    override fun toString(): String {
        return "User(username='$username', email='$email', id='$id')"
    }
}