package com.memo

import java.util.*

class Memo(
    val id: Int?,
    val personId: String?,
    val createPerson: String?,
    val userNumber: String?,
    val group: String?,
    val position: String?,
    val regular: String?,
    val avatar:String?,
    val memoTitle:String?,
    var attachments: Attachment?,
    val createdAt: Date?,
    val updatedAt: Date?,
    val comments: Comment?,

    ) {
}