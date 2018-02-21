package com.hayroyal.mavericks.smsencrypt.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.hayroyal.mavericks.smsencrypt.R


/**
 * A simple [Fragment] subclass.
 */
class InboxFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater!!.inflate(R.layout.fragment_inbox, container, false)
    }

}// Required empty public constructor
