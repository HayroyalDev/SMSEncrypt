package com.hayroyal.mavericks.smsencrypt.Fragments

import android.Manifest
import android.content.Intent
import android.database.DatabaseUtils
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.hayroyal.mavericks.smsencrypt.Adapters.SentAdapter
import com.hayroyal.mavericks.smsencrypt.Encryption.Blowfish
import com.hayroyal.mavericks.smsencrypt.Helper.Sms

import com.hayroyal.mavericks.smsencrypt.R
import com.hayroyal.mavericks.smsencrypt.ViewActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SentFragment : Fragment() {

    var adapter : SentAdapter? = null
    var sentList : ArrayList<Sms>? = null
    val TAG = "Sent Adapter"
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view : View = inflater!!.inflate(R.layout.fragment_sent, container, false)
        sentList = ArrayList()
        var listView = view.findViewById<ListView>(R.id.sentlist)
        adapter = SentAdapter(activity, sentList!!)
        listView.adapter = adapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val o = listView.getItemAtPosition(position)
            val fullObject = o as Sms
            val intent = Intent(activity,ViewActivity::class.java).apply {
                putExtra("address", fullObject.address)
                        .putExtra("body", fullObject.message)
                        .putExtra("type", "sent")
                        .putExtra("date", fullObject.date)
            }
            getPermAndList()
            startActivity(intent)
        }
        return view
    }

    fun getPermAndList(){
        var rxx  = RxPermissions(activity)
        rxx
                .request(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS)
                .subscribe({ granted ->
                    if (granted) {
                        sentList = getSentSms()
                    }
                    else {
                        //Log.e(TAG, "Not Accepted")
                        Toast.makeText(activity,"Permission Denied!!!, Kindly restart app", Toast.LENGTH_SHORT).show()
                        System.exit(0)
                    }
                })

    }

    fun getSentSms(): ArrayList<Sms>? {
        var list = ArrayList<Sms>()
        val contentResolver = activity.contentResolver
        val smsInboxCursor = contentResolver.query(Uri.parse("content://sms/sent"), null, null, null, null)
        val indexBody = smsInboxCursor.getColumnIndex("body")
        val indexAddress = smsInboxCursor.getColumnIndex("address")
        val indexDate = smsInboxCursor.getColumnIndex("date")
        Log.e(TAG, DatabaseUtils.dumpCursorToString(smsInboxCursor))
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) {
            Log.e(TAG, "Empty")
        }else{
            do {
                var sms = Sms()
                sms.address = smsInboxCursor.getString(indexAddress)
                sms.message = smsInboxCursor.getString(indexBody)
                sms.date = convertTime(smsInboxCursor.getLong(indexDate))
                sms.type = "Sent"
                var split = sms.message!!.split(Blowfish.sep)
                if(split.isEmpty() || split.size == 1){
                } else{
                    list.add(sms)
                }
            } while (smsInboxCursor.moveToNext())
            //Log.e(TAG, "True")
        }

        return list
    }

    override fun onResume() {
        super.onResume()
        getPermAndList()
        Log.e(TAG, sentList.toString())
        adapter?.notifyDataSetChanged()

        adapter!!.swapData(sentList!!)

    }

    fun convertTime(time: Long) :String{
// convert seconds to milliseconds
        val date = Date(time)
// the format of your date
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
// give a timezone reference for formatting (see comment at the bottom)
        sdf.timeZone = TimeZone.getTimeZone("GMT+1")
        return sdf.format(date)
    }
}
