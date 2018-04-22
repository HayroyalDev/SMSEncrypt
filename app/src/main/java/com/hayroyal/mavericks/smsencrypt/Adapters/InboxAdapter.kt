package com.hayroyal.mavericks.smsencrypt.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.hayroyal.mavericks.smsencrypt.Helper.Sms
import com.hayroyal.mavericks.smsencrypt.R

class InboxAdapter(context: Context, results: ArrayList<Sms>) : BaseAdapter() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private val TAG = "InboxAdapter"
    private var smsChar : ArrayList<Sms> = results

    override fun getCount(): Int {
        return smsChar.size
    }

    fun swapItem(list : ArrayList<Sms>){
        smsChar = list
        notifyDataSetChanged()
    }


    override fun getItem(position: Int): Any {
        return smsChar[position]
    }

    override fun getItemId(position: Int): Long {
        return smsChar.size.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var cView = convertView
        val holder: ViewHolder
        if (cView == null) {
            cView = mInflater.inflate(R.layout.inbox_row, null)
            holder = ViewHolder()
            holder.address = cView!!.findViewById(R.id.sent_to)
            holder.msg = cView
                    .findViewById(R.id.msg)
            cView.tag = holder
        } else {
            holder = cView.tag as ViewHolder
        }
        holder.address!!.text = smsChar[position].address
        holder.msg!!.text = smsChar[position].message

        return cView
    }


    internal class ViewHolder {
        var address: TextView? = null
        var msg : TextView? = null
    }

}
