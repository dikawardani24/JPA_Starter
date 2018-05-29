package com.dika.view.component.custom

import com.dika.res.SmallIconRes
import com.dika.view.component.Button
import javax.swing.Icon


open class CustomButton(text: String, icon: Icon): Button() {
    init {
        this.text = text
        this.icon = icon
    }
}

class AddButton: CustomButton("Tambah", SmallIconRes.addIcon)

class CalculatorButton: CustomButton("Hitung", SmallIconRes.calculatorIcon)

class CancelButton: CustomButton("Batal", SmallIconRes.cancelIcon)

class ClearButton: CustomButton("Bersihkan", SmallIconRes.clearIcon)

class ConfirmButton: CustomButton("Setuju", SmallIconRes.confirmIcon)

class FirstPageButton: CustomButton("Hal. Pertama", SmallIconRes.firstPageIcon)

class LastPageButton: CustomButton("Hal. Terakhir", SmallIconRes.lastPageIcon)

class LoginButton: CustomButton("Login", SmallIconRes.loginIcon)

class LogoutButton: CustomButton("Logout", SmallIconRes.logoutIcon)

class NextButton: CustomButton("Selanjutnya", SmallIconRes.nextIcon)

class PreviousButton: CustomButton("Sebelumnya", SmallIconRes.previousIcon)

class PrintButton: CustomButton("Cetak", SmallIconRes.printIcon)

class RefreshButton: CustomButton("Segarkan", SmallIconRes.refreshIcon)

class SaveButton: CustomButton("Simpan", SmallIconRes.saveIcon)

class SearchButton: CustomButton("Cari", SmallIconRes.searchIcon)

class SeeButton: CustomButton("Lihat", SmallIconRes.seeIcon)

class UnConfirmButton: CustomButton("Tidak Setuju", SmallIconRes.unconfirmIcon)

class UpdateButton: CustomButton("Perbaharui", SmallIconRes.updateIcon)