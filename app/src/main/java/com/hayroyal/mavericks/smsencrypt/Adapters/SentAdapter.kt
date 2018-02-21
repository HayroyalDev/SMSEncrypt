package com.hayroyal.mavericks.smsencrypt.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.hayroyal.mavericks.smsencrypt.Helper.Sms
import com.hayroyal.mavericks.smsencrypt.R


/**
 * Created by mavericks on 2/21/18.
 */
class SentAdapter(context: Context, results: ArrayList<Sms>) : BaseAdapter() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private val TAG = "SentAdapter"
    private var smsChar : ArrayList<Sms> = results

    override fun getCount(): Int {
        return smsChar.size
    }

    override fun getItem(position: Int): Any {
        return smsChar[position]
    }

    override fun getItemId(position: Int): Long {
        return smsChar.size.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.sent_row, null)
            holder = ViewHolder()
            holder.address = convertView!!.findViewById(R.id.sent_to)
            holder.msg = convertView
                    .findViewById(R.id.msg)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.address!!.text = smsChar[position].address
        holder.msg!!.text = smsChar[position].message

        return convertView
    }


    internal class ViewHolder {
        var address: TextView? = null
        var msg : TextView? = null
    }

}
