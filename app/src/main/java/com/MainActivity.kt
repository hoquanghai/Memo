package com

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.auth0.android.jwt.JWT
import com.google.gson.Gson
import com.login.LoginActivity
import com.login.TokenHelper
import com.login.UserHelper
import com.memo.*
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {


    private val RECORD_REQUEST_CODE = 1
    companion object {
        lateinit var instance: MainActivity
        const val PERMISSION_REQUEST_STORAGE = 0
    }

    init {
        instance = this
    }
    val list = ArrayList<Memo>();
    lateinit var avatar: CircleImageView
    lateinit var memo_avatar: CircleImageView
    lateinit var section: RecyclerView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        avatar =findViewById(R.id.avatar)
        memo_avatar =findViewById(R.id.memo_avatar)
        section =findViewById(R.id.section)
        swipeRefreshLayout = findViewById(R.id.swipeToRefresh)

        if (checkUserExist() == true && checkTokenValueDate() == true) {
            checkTokenValueDate()
            checkUserExist()
            checkPermission()

            val SERVER_FILE = "http://172.16.0.210"
            val FILE_PORT = "2998"
            val URL_AVATAR = "$SERVER_FILE:$FILE_PORT/api/v1/avatar"
            val user = UserHelper(this).get()
            val token = TokenHelper(this).get()
            val avatar_url = "${URL_AVATAR}/${user?.picture?.medium}" +"?auth_token=" +"${token}"
            Picasso.get().load(avatar_url)
                .into(memo_avatar);
            Picasso.get().load(avatar_url)
                .into(avatar);
            avatar.setOnClickListener {
                showLogoutDialog()
            }
            section.setHasFixedSize(true)
            section.layoutManager = LinearLayoutManager(this)
            swipeRefreshLayout.setOnRefreshListener {
                list.clear()
                getMemos()
                swipeRefreshLayout.isRefreshing = false
                Toast.makeText(this, "ページが更新されました!", Toast.LENGTH_SHORT).show()
            }
            getMemos()
        } else {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun checkUserExist(): Boolean {
        val user = UserHelper(this).get()
        if( user != null){
            return true
        } else {
            return false
        }
    }

    fun checkTokenValueDate(): Boolean? {
        val token = TokenHelper(this).get()
        val jwt = token?.let { JWT(it) }
        val expiresAt = jwt?.getExpiresAt();
        val expiresTime =  expiresAt?.time
        val todayTime = (Math.floor(Date().time / 1000.toDouble()) * 1000).toLong()
        Log.v("token1", todayTime.toString())
        Log.v("token2", expiresTime.toString())
        if(todayTime <= expiresTime!!) {
            return true
        }else {
            return false
        }
    }

    fun checkPermission() {
        val WRITE_EXTERNAL_PERMISSION =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (WRITE_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED) {
            Log.v("TAG", "Permission to record denied")
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                makeRequest()

            } else {
                makeRequest()

            }
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            RECORD_REQUEST_CODE)
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("ログアウト")
            .setMessage("ログアウトしてもよろしいですか？")
            .setPositiveButton(android.R.string.yes) { _, _ ->
                launchLoginActivity()
            }
            .setNegativeButton(android.R.string.no) { _, _ -> }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }
    private fun launchLoginActivity() {
        TokenHelper(this).reset()
        UserHelper(this).reset()
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun getMemos(){
        val gson = Gson()
        swipeRefreshLayout.isRefreshing = true
        swipeRefreshLayout.isEnabled = true

        RetrofitMemo.instance.getSection().enqueue(object :
        Callback<ArrayList<Memo>>{
            override fun onResponse(call: Call<ArrayList<Memo>>, response: Response<ArrayList<Memo>>) {
                swipeRefreshLayout.isRefreshing = false
                swipeRefreshLayout.isEnabled = true
                response.body()?.let {
                    list.addAll(it)
                }
                val adapter = MemoAdapter(list)
                section.adapter = adapter
            }

            override fun onFailure(call: Call<ArrayList<Memo>>, t: Throwable) {
                swipeRefreshLayout.isRefreshing = false
                swipeRefreshLayout.isEnabled = true
                Log.v("ABC1", "Get False")

            }

        })
    }

}