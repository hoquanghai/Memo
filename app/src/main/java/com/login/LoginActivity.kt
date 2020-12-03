package com.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.MainActivity
import com.memo.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val inputUsername = findViewById<EditText>(R.id.username)
        val inputPassword = findViewById<EditText>(R.id.password)
        val btnLogin = findViewById<Button>(R.id.login)
        btnLogin.setOnClickListener {
            var username = inputUsername.text.toString().trim { it <= ' ' }
            var password = inputPassword.text.toString().trim { it <= ' ' }

            if (username.isNotEmpty()) {
                if (password.isEmpty()) {
                    password = ""
                }
                username = "$username@ondo-metal.co.jp"
                checkLogin(username, password)
                Log.v("Login", username)
                Log.v("Login", password)
            } else {
                Toast.makeText(applicationContext,
                    applicationContext.getText(R.string.error_empty_username), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }


    private fun checkLogin(email: String, password: String) {

        RetrofitLoginClient.instance.login(mapOf("email" to email, "password" to password)).enqueue(object:
            Callback<UserApi.LoginResponse> {
            override fun onResponse(
                call: Call<UserApi.LoginResponse>,
                response: Response<UserApi.LoginResponse>
            ) {
                val body = response.body()
                Log.v("LoginResponse",body.toString())
                if (body !== null && body.success) {
                    val token = body.result
                    var user: User = User()
                    user.id = body.message
                    TokenHelper(this@LoginActivity).set(token)
                    getUser(user,token)

                }
            }

            override fun onFailure(call: Call<UserApi.LoginResponse>, t: Throwable) {

                Toast.makeText(applicationContext,
                    applicationContext.getText(R.string.error_empty_password), Toast.LENGTH_LONG)
                    .show()
            }

        })
    }
    private fun getUser(user: User, token: String) {
        RetrofitLoginClient.instance.getUser(user.id, "Bearer "+token).enqueue(object:
            Callback<UserApi.UserResponse> {
            override fun onResponse(
                call: Call<UserApi.UserResponse>,
                response: Response<UserApi.UserResponse>
            ) {
                val body = response.body()
                if (body !== null && body.success) {
                    val user = body.result

                    UserHelper(this@LoginActivity).set(user)
                }
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onFailure(call: Call<UserApi.UserResponse>, t: Throwable) {
            }

        })

    }
}