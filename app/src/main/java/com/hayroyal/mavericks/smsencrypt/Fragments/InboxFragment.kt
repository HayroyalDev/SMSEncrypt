package com.hayroyal.mavericks.smsencrypt.Fragments


import android.Manifest
import android.content.Intent
import android.database.DatabaseUtils
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.hayroyal.mavericks.smsencrypt.Adapters.InboxAdapter
import com.hayroyal.mavericks.smsencrypt.Adapters.SentAdapter
import com.hayroyal.mavericks.smsencrypt.Encryption.Blowfish
import com.hayroyal.mavericks.smsencrypt.Helper.Sms

import com.hayroyal.mavericks.smsencrypt.R
import com.hayroyal.mavericks.smsencrypt.ViewActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_view.*
import kotlinx.android.synthetic.main.fragment_inbox.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class InboxFragment : Fragment() {

    val TAG = "Inbox Activity"
    var adapter : InboxAdapter? = null
    var inboxList : ArrayList<Sms>? = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        var view : View = inflater!!.inflate(R.layout.fragment_inbox, container, false)
        var inboxlist = view.findViewById<ListView>(R.id.inboxlist)
        inboxList = ArrayList()
        adapter = InboxAdapter(activity, inboxList!!)
        inboxlist.adapter = adapter
        inboxlist.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val o = inboxlist.getItemAtPosition(position)
            val fullObject = o as Sms
            //Log.i("Hymn Details", fullObject.getTitle() + String.valueOf(fullObject.getID()));
            //Toast.makeText(getActivity(), "You have chosen: " + " " + fullObject.getID(), Toast.LENGTH_LONG).show();
            val intent = Intent(activity, ViewActivity::class.java).apply {
                putExtra("address", fullObject.address)
                        .putExtra("body", fullObject.message)
                        .putExtra("type", "inbox")
                        .putExtra("date", fullObject.date)

            }
            startActivity(intent)
        }
        getList()
        return view
    }
    //get Permission and list
    fun getList(){
        var rxx  = RxPermissions(activity)
        rxx
                .request(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS)
                .subscribe({ granted ->
                    if (granted) {
                        inboxList = getInboxSms()
                    }
                    else {
                        //Log.e(TAG, "Not Accepted")
                        Toast.makeText(activity,"Permission Denied!!!, Kindly restart app", Toast.LENGTH_SHORT).show()
                        System.exit(0)
                    }
                })
    }

    fun getInboxSms(): ArrayList<Sms>? {
        var list = ArrayList<Sms>()
        val contentResolver = activity.contentResolver
        val smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null)
        val indexBody = smsInboxCursor.getColumnIndex("body")
        val indexAddress = smsInboxCursor.getColumnIndex("address")
        val indexDate = smsInboxCursor.getColumnIndex("date")
        //Log.e(TAG, DatabaseUtils.dumpCursorToString(smsInboxCursor))
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) {
           // Log.e(TAG, "Empty")
        }else{
            do {
                var sms = Sms()
                sms.address = smsInboxCursor.getString(indexAddress)
                sms.message = smsInboxCursor.getString(indexBody)
                sms.date = convertTime(smsInboxCursor.getLong(indexDate))
                sms.type = "Inbox"
                var split = sms.message!!.split(Blowfish.sep)
                if(split.isEmpty() || split.size == 1){
                } else{
                    list.add(sms)
                }
                //Log.e(TAG, sms.toString())
                //Log.e(TAG, sms.date)
            } while (smsInboxCursor.moveToNext())
            //Log.e(TAG, "True")
        }

        return list
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

    override fun onResume() {
        super.onResume()
        getList()
        adapter!!.notifyDataSetChanged()
        adapter!!.swapItem(inboxList!!)
    }

}// Required empty public constructor
