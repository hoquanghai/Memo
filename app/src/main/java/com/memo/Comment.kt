package com.memo

import java.util.*

class Comment(
    val id: Int?= null,
    val timelineId: Int?= null,
    val commentId: Int?= null,
    val createPerson: String? = "",
    val createdAt: String? = "",
    val updatedAt:String? = "",
    val personId: String? = "",
    val group: String? = "",
    val position: String? = "",
    val regular: String? = "",
    val avatar:String? = "",
    val commentTitle:String? = "",
    val attachments:String? = "",
    val replies: ArrayList<Comment>? = ArrayList(),
) {

}