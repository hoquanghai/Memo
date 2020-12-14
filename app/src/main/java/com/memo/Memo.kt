package com.memo

import java.util.*

class Memo(
        var id: Int?= null,
        var createPerson: String? = "",
        var userNumber: String? = "",
        var memoTitle: String? = "",
        var createdAt: String? = "",
        var updatedAt: String? = "",
        var personId: String? = "",
        var group: String? = "",
        var position: String? = "",
        var regular: String? = "",
        var avatar:String? = "",
        var attachments: ArrayList<Attachment>? = ArrayList(),
        var comments: ArrayList<Comment>? = ArrayList(),
    ) {
}