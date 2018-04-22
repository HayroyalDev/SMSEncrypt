package com.hayroyal.mavericks.smsencrypt

import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.hayroyal.mavericks.smsencrypt.Encryption.Blowfish
import com.hayroyal.mavericks.smsencrypt.Fragments.InboxFragment
import com.hayroyal.mavericks.smsencrypt.Fragments.SentFragment
import com.hayroyal.mavericks.smsencrypt.Helper.AppPreference

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.pword.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    //private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    val TAG = "MainAvtivity";
    var layout : LayoutInflater? = null
    var appPreference : AppPreference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "SMS Encrypt"
        var data = Blowfish.encrypt("Hello Sweet Heart, How are you doing? I miss you. This is a test message", "key")
        Log.e(TAG, data)
        appPreference = AppPreference(this)
        layout = LayoutInflater.from(this)
//        var dataa = GenerateKey.generate()
//        Log.e(TAG, dataa + "data")
        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
        fab.setOnClickListener { _ ->
            startActivity(Intent(this, NewActivity::class.java))
        }
        setUpTabs()
        if(appPreference!!.getAtFirstRun()){
            SetUpPassword()
            appPreference!!.setAtFirstRun(false)
        }else{
            bringUpPassword()
        }
    }

    private fun SetUpPassword() {
        val v : View = layout!!.inflate(R.layout.npword,null)
        val alert = AlertDialog.Builder(this)
                .setView(v)
                .setCancelable(false).show()
        val btn = v.findViewById<Button>(R.id.pBtn)
        val pwd = v.findViewById<TextView>(R.id.newpwd)
        val npwd = v.findViewById<TextView>(R.id.confirmPwd)

        btn.setOnClickListener{
            if(pwd.text.isNotEmpty() || npwd.text.isNotEmpty()){
                if(pwd.text.toString() == npwd.text.toString()){
                    appPreference!!.setPassword(pwd.text.toString())
                    alert.dismiss()
                }else{
                    pwd.error = "Password Mismatch"
                    npwd.error = "Password Mismatch"
                }
            }
            else{
                pwd.error = "Password Mismatch"
                npwd.error = "Password Mismatch"
            }
            Log.e(TAG,pwd.text.toString())
            Log.e(TAG,npwd.text.toString())
        }
    }

    private fun bringUpPassword() {
        val v : View = layout!!.inflate(R.layout.pword,null)
        val alert = AlertDialog.Builder(this)
                .setView(v)
                .setCancelable(false).show()
        val btn = v.findViewById<Button>(R.id.pBtn)
        val pwd = v.findViewById<TextView>(R.id.passwordTxt)
        Log.e(TAG, appPreference!!.getPassword())
        btn.setOnClickListener{
            if(appPreference!!.getPassword() == pwd.text.toString()){
                alert.dismiss()
            }else{
                pwd.error = "Incorrect Password";
            }
            Log.e(TAG,pwd.text.toString())
        }
    }

    private fun setUpTabs() {
        var viewPager = findViewById<ViewPager>(R.id.container)
        setupViewPager(viewPager)
        tabs.setupWithViewPager(viewPager)
    }

    private fun setupViewPager(viewPager: ViewPager){
        val adapter = Adapter(supportFragmentManager)
        adapter.addFragment(InboxFragment(), "Inbox")
        adapter.addFragment(SentFragment(), "Sent")
        viewPager.adapter = adapter
    }


//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//        val id = item.itemId
//
//        if (id == R.id.action_settings) {
//            return true
//        }
//
//        return super.onOptionsItemSelected(item)
//    }

    private class Adapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }

    }

}
