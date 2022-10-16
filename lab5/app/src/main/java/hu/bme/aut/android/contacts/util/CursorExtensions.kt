package hu.bme.aut.android.contacts.util

import android.annotation.SuppressLint
import android.database.Cursor

@SuppressLint("Range")
fun Cursor.getStringByColumnName(colName: String) = this.getString(this.getColumnIndex(colName))