package com.vanka.devbook.Model

import android.icu.text.CaseMap
import android.service.controls.templates.ThumbnailTemplate

data class VideoModel(
    var author:String?=null,
    var title:String?=null,
    var thumbnail:String?=null,
    var authorImg:String?=null,
    var videoLink:String?=null

)
