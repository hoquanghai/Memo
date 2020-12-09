package com.memo


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.MainActivity
import com.google.gson.Gson
import com.login.UserHelper
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MemoAdapter(private val list: ArrayList<Memo>): RecyclerView.Adapter<MemoAdapter.MemoViewHolder>(){


    val gson = Gson()
    var lisImage = ArrayList<Attachment>();
    var lisComment = ArrayList<Comment>();
    private val viewPool = RecyclerView.RecycledViewPool()
    private val viewPool2 = RecyclerView.RecycledViewPool()
    init {
        this.lisImage = lisImage
        this.lisComment = lisComment
    }
    inner class MemoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var user_name: TextView = itemView.findViewById(R.id.user_name)
        var create_time: TextView = itemView.findViewById(R.id.create_time)
        var memo_title: TextView = itemView.findViewById(R.id.memo_title)
        var user_avatar: CircleImageView = itemView.findViewById(R.id.user_avatar)
        var list_image: RecyclerView = itemView.findViewById(R.id.list_image)
        var list_comment: RecyclerView = itemView.findViewById(R.id.list_comment)

        fun bind(memoResponse: Memo){
            with(itemView) {
                val createPerson = "${memoResponse.createPerson}"
                val createdAt = "${memoResponse.createdAt}"
                val memoTitle = "${memoResponse.memoTitle}"
                val attachments = memoResponse.attachments
                val url_avatar ="http://172.16.0.210/dr/dist/img/member/" +"${memoResponse.avatar}"

                user_name.text = createPerson;
                memo_title.text = memoTitle;
                create_time.text = formatDate(createdAt)
                Picasso.get().load(url_avatar)
                        .into(user_avatar);

                val childLayoutManager = LinearLayoutManager(MemoViewHolder.list_image.context, LinearLayout.HORIZONTAL, false)
                holder.list_image.apply {
                layoutManager = childLayoutManager
                adapter = movie.attachments?.let { ImageAdapter(it) }
                setRecycledViewPool(viewPool)
        };



                // comment view
                val intent = (context as Activity).intent
                val user = UserHelper(context).get()

                Log.v("newMemo", gson.toJson(user))
                val avatar_url = intent.getStringExtra("avatar_url")
                if (avatar_url != null) {
                    val comment_avatar = findViewById<CircleImageView>(R.id.comment_avatar)
                    Picasso.get().load(avatar_url)
                        .into(comment_avatar);
                }

                val comment_put = findViewById<ImageButton>(R.id.comment_put)
                val editText1 = findViewById<EditText>(R.id.editText1)
                comment_put.setOnClickListener{
                     val newMemo = Memo(
                             avatar = user?.picture?.thumbnail,
                             createdAt = Date().toString(),
                             createPerson = user?.name,
                             group = "abc",
                             userNumber = "njfng",
                             memoTitle = editText1.getText().toString(),
                             personId = "nnj",
                             regular = "fhfh",
                             position = "fgfg",
                             updatedAt = "fgfg",

                     )
                    Log.v("newMemo", gson.toJson(newMemo))


                }
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



    @SuppressLint("WrongConstant")
    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.bind(list[position])
//        val movie = list[position]
//        holder.create_time.text = formatDate(movie.createdAt)
//        holder.memo_title.text = movie.memoTitle
//        holder.user_name.text = movie.createPerson
//        val url_avatar ="http://172.16.0.210/dr/dist/img/member/" +"${movie.avatar}"
//        Picasso.get().load(url_avatar)
//          .into(holder.user_avatar);
//
//        val commentLayoutManaget = LinearLayoutManager(holder.list_comment.context)
//        holder.list_comment.apply {
//            layoutManager = commentLayoutManaget
//            adapter = movie.comments?.let { CommentAdapter(it) }
//            setRecycledViewPool(viewPool2)
//        }
//
//        val childLayoutManager = LinearLayoutManager(holder.list_image.context, LinearLayout.HORIZONTAL, false)
//        holder.list_image.apply {
//            layoutManager = childLayoutManager
//            adapter = movie.attachments?.let { ImageAdapter(it) }
//            setRecycledViewPool(viewPool)
//        };


    }

    override fun getItemCount(): Int = list.size

    fun formatDate(utcInput: String?, outputFormat: String = "yyyy-MM-dd HH:mm"): String {
        val isoFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        val normalFormat = "yyyy-MM-dd hh:mm:ss.SSS"
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
}


