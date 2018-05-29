package com.dika.res

object PlainTextRes: TextResource("plain/", "text/plain") {
    val banner get() = load("banner.txt")
    val endBanner get() = load("end_banner.txt")
}