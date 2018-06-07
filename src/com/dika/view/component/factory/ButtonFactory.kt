package com.dika.view.component.factory

import com.dika.Factory
import com.dika.res.SmallIconRes
import com.dika.view.component.Button
import javax.swing.ImageIcon

object ButtonFactory: Factory<Button>() {
    override fun create(): Button {
        return Button()
    }

    private fun create(text: String, icon: ImageIcon): Button {
        return create().apply {
            this.text = text
            this.icon = icon
        }
    }

    val addButton get() = create("Tambah", SmallIconRes.addIcon)
    val calculatorButton get() = create("Hitung", SmallIconRes.calculatorIcon)
    val cancelButton get() = create("Batal", SmallIconRes.cancelIcon)
    val clearButton get() = create("Bersihkan", SmallIconRes.clearIcon)
    val confirmButton get() = create("Setuju", SmallIconRes.confirmIcon)
    val firstPageButton get() = create("", SmallIconRes.firstPageIcon)
    val lastPageButton get() = create("", SmallIconRes.lastPageIcon)
    val loginButton get() = create("Login", SmallIconRes.loginIcon)
    val logoutButton get() = create("Logout", SmallIconRes.logoutIcon)
    val nextButton get() = create("", SmallIconRes.nextIcon)
    val previousButton get() = create("", SmallIconRes.previousIcon)
    val printButton get() = create("Cetak", SmallIconRes.printIcon)
    val refreshButton get() = create("", SmallIconRes.refreshIcon)
    val saveButton get() = create("Simpan", SmallIconRes.saveIcon)
    val searchButton get() = create("Cari", SmallIconRes.searchIcon)
    val seeButton get() = create("Lihat", SmallIconRes.seeIcon)
    val unconfirmButton get() = create("Tidak Setuju", SmallIconRes.unconfirmIcon)
    val updateButton get() = create("Perbaharui", SmallIconRes.updateIcon)
}