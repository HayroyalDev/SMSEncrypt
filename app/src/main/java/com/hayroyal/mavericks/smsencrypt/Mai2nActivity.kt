package com.hayroyal.mavericks.smsencrypt

import android.Manifest
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.tbruyelle.rxpermissions2.RxPermissions

import kotlinx.android.synthetic.main.activity_main.*
import android.text.method.TextKeyListener.clear
import android.content.ContentResolver
import android.database.DatabaseUtils
import android.net.Uri


//import rx.*;
class Mai2nActivity : AppCompatActivity() {

    var TAG = "Mai2nActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        var rxx  = RxPermissions(this)
        rxx
                .request(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS)
                .subscribe({ granted ->
                    if (granted) {
                        // Always true pre-M
                        getAllSMS()
                    } else {
                        Log.e(TAG, "Not Accepted")
                        // Oups permission denied
                    }
                })
    }

    private fun getAllSMS() {
        val contentResolver = contentResolver
        val smsInboxCursor = contentResolver.query(Uri.parse("content://sms"), null, null, null, null)
        val indexBody = smsInboxCursor.getColumnIndex("body")
        val indexAddress = smsInboxCursor.getColumnIndex("address")
        Log.e(TAG, DatabaseUtils.dumpCursorToString(smsInboxCursor))
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) {
            Log.e(TAG, "Empty")
        }else{
            do {
                val str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
                        "\n" + smsInboxCursor.getString(indexBody) + "\n"
                Log.e(TAG, str)
            } while (smsInboxCursor.moveToNext())
            Log.e(TAG, "True")
        }
        //arrayAdapter.clear()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
