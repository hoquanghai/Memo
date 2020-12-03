package com.memo

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
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