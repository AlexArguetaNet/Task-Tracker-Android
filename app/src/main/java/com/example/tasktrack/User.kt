package com.example.tasktrack

import java.io.Serializable

class User(var username: String = "", var email: String = "", var id: String = ""): Serializable {

    override fun toString(): String {
        return "User(username='$username', email='$email', id='$id')"
    }
}