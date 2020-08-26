package com.example.amovie.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*


/**
 * Created on 10/4/16.
 */

class SQLHelper(context: Context) : ManagedSQLiteOpenHelper(context, "psm.db", null, 1) {

    companion object {
        private var instance: SQLHelper?=null
        @Synchronized
        fun getInstance(context: Context) : SQLHelper{

            if(instance == null ){
                instance = SQLHelper(context.applicationContext)
            }
            return instance as SQLHelper
        }

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(cUsuario.TABLE_USUARIO, true,
            cUsuario.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            cUsuario.correo to TEXT,
            cUsuario.username to TEXT,
            cUsuario.contra to TEXT,
            cUsuario.imagen to TEXT
            )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(cUsuario.TABLE_USUARIO , true)
    }
}

val Context.database : SQLHelper
get() = SQLHelper.getInstance(applicationContext)