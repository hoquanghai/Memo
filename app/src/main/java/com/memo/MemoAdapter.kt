package com.memo


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.login.TokenHelper
import com.login.UserHelper
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MemoAdapter(private val list: ArrayList<Memo>): RecyclerView.Adapter<MemoAdapter.MemoViewHolder>(){
    inner class MemoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val gson = Gson()
        private val isoFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        private val normalFormat = "yyyy-MM-dd hh:mm:ss.SSS"
        fun formatDate(utcInput: String?, outputFormat: String = "yyyy-MM-dd HH:mm"): String {
            var result = ""
            if (utcInput != null) {
                try {
                    val isoFormatter = SimpleDateFormat(isoFormat, Locale.JAPAN)
                    isoFormatter.timeZone = TimeZone.getTimeZone("UTC")
                    val outputFormatter = SimpleDateFormat(outputFormat, Locale.JAPAN)
                    val date = isoFormatter.parse(utcInput)
                    outputFormatter.timeZone = TimeZone.getDefault()
                    result = outputFormatter.format(date)
                } catch (error: Exception) {
                    try {
                        val inputFormatter = SimpleDateFormat(normalFormat, Locale.JAPAN)
                        val outputFormatter = SimpleDateFormat(outputFormat, Locale.JAPAN)
                        val date = inputFormatter.parse(utcInput)
                        result = outputFormatter.format(date)
                    } catch (error: Exception) {
                        Log.v("%s", error.toString())
                    }
                    if (result == "") Log.v("%s", error.toString())
                } finally {
                    return result
                }
            }
            return result
        }
        fun bind(memoResponse: Memo){
            with(itemView) {
                val createPerson = "${memoResponse.createPerson}"
                val createdAt = "${memoResponse.createdAt}"
                val memoTitle = "${memoResponse.memoTitle}"
                val url_avatar ="http://172.16.0.210/dr/dist/img/member/" +"${memoResponse.avatar}"
                val user_name = findViewById<TextView>(R.id.user_name)
                val create_time = findViewById<TextView>(R.id.create_time)
                val memo_title = findViewById<TextView>(R.id.memo_title)
                val user_avatar = findViewById<CircleImageView>(R.id.user_avatar)
                user_name.text = createPerson;
                memo_title.text = memoTitle;
                create_time.text = formatDate(createdAt)
                Picasso.get().load(url_avatar)
                        .into(user_avatar);
                // comment view
                val SERVER_FILE = "http://172.16.0.210"
                val FILE_PORT = "2998"
                val URL_AVATAR = "$SERVER_FILE:$FILE_PORT/api/v1/avatar"
                val user = UserHelper(this@MemoViewHolder).get()
                val token = TokenHelper(this).get()
                val avatar_url = "${URL_AVATAR}/${user?.picture?.medium}" +"?auth_token=" +"${token}"
                Picasso.get().load(avatar_url)
                        .into(memo_avatar);
                Picasso.get().load(avatar_url)
                        .into(avatar);


            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.iteam_memo,
            parent,
            false
        )
        return MemoViewHolder(view)
    }



    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}