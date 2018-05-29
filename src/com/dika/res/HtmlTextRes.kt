package com.dika.res

object HtmlTextRes: TextResource("html/", "text/html") {
    val appAbout get() = load("about_apps.html")
    val companyAbout get() = load("about_company.html")
    val univAbout get() = load("about_univ.html")
}