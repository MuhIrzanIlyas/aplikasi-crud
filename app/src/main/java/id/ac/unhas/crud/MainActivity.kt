package id.ac.unhas.crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var iniNama: EditText
    private lateinit var iniEmail: EditText
    private lateinit var tambahTombol : Button
    private lateinit var lihatTombol : Button
    private lateinit var updateTombol : Button

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: AdapterIni? = null
    private var ini: ModelIni? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)

        tambahTombol.setOnClickListener { tambahOrang() }
        lihatTombol.setOnClickListener { lihatOrang() }
        updateTombol.setOnClickListener { updateOrang() }

        adapter?.setOnClickItem {
            Toast.makeText(this, it.nama, Toast.LENGTH_SHORT).show()

            iniNama.setText(it.nama)
            iniEmail.setText(it.email)
            ini = it
        }
    }

    private fun tambahOrang() {
        val nama = iniNama.text.toString()
        val email = iniEmail.text.toString()

        if (nama.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Isi Kotaknya Dulu Gan!!", Toast.LENGTH_SHORT).show()
        } else {
            val ini = ModelIni(nama = nama, email = email)
            val status = sqLiteHelper.insertIni(ini)

            if(status > -1) {
                Toast.makeText(this, "Berhasil Ditambahkan Gan!!", Toast.LENGTH_SHORT).show()
                clearEditText()
                tambahOrang()
            } else {
                Toast.makeText(this, "Gagal Ditambahkan Gan!!", Toast.LENGTH_SHORT).show()
            }
        }

        adapter?.setOnClickDeleteItem {
            deleteIni(it.id)
        }

    }

    private fun updateOrang() {
        val nama = iniNama.text.toString()
        val email = iniEmail.text.toString()

        if(nama == ini?.nama && email == ini?.email) {
            Toast.makeText(this, "Data Berhasil Diupdate!!", Toast.LENGTH_SHORT).show()
            return
        }

        if(ini == null) return

        val ini = ModelIni(id = ini!!.id, nama = nama, email = email)
        val status = sqLiteHelper.updateIni(ini)
        if(status > -1) {
            clearEditText()
            lihatOrang()
        }
    }

    private fun lihatOrang() {
        val iniList = sqLiteHelper.getAllIni()
        Log.e("USER", "${iniList.size}")

        adapter?.addItem(iniList)
    }

    private fun deleteIni(id: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Pen Dihapus Nih Gan??")
        builder.setCancelable(true)
        builder.setPositiveButton("Yoi") { dialog, _ ->
            sqLiteHelper.deleteIniById(id)
            lihatOrang()
            dialog.dismiss()
        }
        builder.setNegativeButton("Gak!") { dialog, _ ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()

    }

    private fun clearEditText() {
        iniNama.setText("")
        iniEmail.setText("")
        iniNama.requestFocus()
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AdapterIni()
        recyclerView.adapter = adapter
    }

    private fun initView() {
        iniNama = findViewById(R.id.iniNama)
        iniEmail = findViewById(R.id.iniEmail)
        tambahTombol = findViewById(R.id.tambahTombol)
        lihatTombol = findViewById(R.id.lihatTombol)
        updateTombol = findViewById(R.id.updateTombol)
        recyclerView = findViewById(R.id.recyclerView)
    }
}