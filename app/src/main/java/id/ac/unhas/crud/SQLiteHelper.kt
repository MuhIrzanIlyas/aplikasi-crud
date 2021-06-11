package id.ac.unhas.crud

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception
import java.util.*

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAMA, null, DATABASE_VERSION) {

    companion object{

        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAMA = "ini.db"
        private const val TBL_INI = "tbl_ini"
        private const val ID = "id"
        private const val NAMA = "nama"
        private const val EMAIL = "email"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblIni = ("CREATE TABLE " + TBL_INI +"("
                + ID + " INTEGER PRIMARY KEY, " + NAMA + " TEXT,"
                + EMAIL + " TEXT" + ")")
        db?.execSQL(createTblIni)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_INI")
        onCreate(db)
    }

    fun insertIni(ini: ModelIni): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, ini.id)
        contentValues.put(NAMA, ini.nama)
        contentValues.put(EMAIL, ini.email)

        val success = db.insert(TBL_INI, null, contentValues)
        db.close()
        return success

    }

    fun getAllIni(): ArrayList<ModelIni> {
        val iniList: ArrayList<ModelIni> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_INI"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var nama: String
        var email: String

        if (cursor.moveToFirst()) {
            do {

                id = cursor.getInt(cursor.getColumnIndex("id"))
                nama = cursor.getString(cursor.getColumnIndex("nama"))
                email = cursor.getString(cursor.getColumnIndex("email"))

                val ini = ModelIni(id = id, nama = nama, email = email)
                iniList.add(ini)

            } while (cursor.moveToNext())
        }
        return iniList
    }
    
    fun updateIni(ini: ModelIni): Int {
        val db = this.writableDatabase
        
        val contentValues = ContentValues()
        contentValues.put(ID, ini.id)
        contentValues.put(NAMA, ini.nama)
        contentValues.put(EMAIL, ini.email)

        val success = db.update(TBL_INI, contentValues, "id= " + ini.id, null)
        db.close()
        return success
    }

    fun deleteIniById(id: Int): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, id)

        val success = db.delete(TBL_INI, "id= $id", null)
        db.close()
        return success
    }
}