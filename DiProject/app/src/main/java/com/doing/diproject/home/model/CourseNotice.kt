package com.doing.diproject.home.model



data class CourseNotice(val total: Int, val list: List<Notice>?) {

    data class Notice(
        var id: String,
        var sticky: Int,
        var type: String,
        var title: String,
        var subtitle: String,
        var url: String,
        var cover: String,
        var createTime: String
    )
}