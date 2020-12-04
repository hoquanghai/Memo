package com.memo

class Attachment(
    val id: Int?= null,
    val parentId: Int?= null,
    val destination: String? = "",
    val filename: String? = "",
    val mimetype: String? = "",
    val originalname: String? = "",
    val path: String? = "",
    val size: Int?= null,
) {
}