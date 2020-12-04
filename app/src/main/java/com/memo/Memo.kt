package com.memo

import java.util.*

class Memo(
        val id: Int?= null,
        val createPerson: String?,
        val userNumber: String?,
        val memoTitle:String?,
        val createdAt: String?,
        val updatedAt: String?,
        val personId: String?,
        val group: String?,
        val position: String?,
        val regular: String?,
        val avatar:String?,
        var attachments: ArrayList<Attachment>? = ArrayList(),
        val comments: ArrayList<Comment>? = ArrayList(),
    ) {
}