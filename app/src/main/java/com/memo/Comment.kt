package com.memo

import java.util.*

class Comment(
    val id: Int?,
    val timelineId: Int?,
    val commentId: Int?,
    val createdAt: Date?,
    val updatedAt:Date?,
    val personId: String?,
    val group: String?,
    val position: String?,
    val regular: String?,
    val avatar:String?,
    val commentTitle:String?,
    val attachments:String?,
    val replies: Comment?,
) {

}