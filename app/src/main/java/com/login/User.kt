package com.login

class User(
    var id: String = "",
    var email: String? = "",
    var name: String? = "",
    var username: String? = "",
    var picture: UserPicture? = UserPicture(),
) {
}