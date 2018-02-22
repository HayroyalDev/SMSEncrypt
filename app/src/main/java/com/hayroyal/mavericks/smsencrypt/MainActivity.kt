package com.hayroyal.mavericks.smsencrypt

import android.Manifest
import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hayroyal.mavericks.smsencrypt.Encryption.Blowfish
import com.hayroyal.mavericks.smsencrypt.Encryption.BlowfishJava
import com.hayroyal.mavericks.smsencrypt.Encryption.Ecc
import com.hayroyal.mavericks.smsencrypt.Fragments.InboxFragment
import com.hayroyal.mavericks.smsencrypt.Fragments.SentFragment
import com.tbruyelle.rxpermissions2.RxPermissions

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = "SMS Encrypt"
        //Ecc.generate()
        var data = Blowfish.encrypt("Hello Sweet Heart, How are you doing? I miss you. This is a test message","key")
        //var data = BlowfishJava().encrypt("Data to be encrypted", "key")
        Toast.makeText(this, data, Toast.LENGTH_LONG).show()
        var ddata = Blowfish.decrypt(data,"key")
        //var data = BlowfishJava().encrypt("Data to be encrypted", "key")
        Toast.makeText(this, ddata, Toast.LENGTH_LONG).show()
        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        fab.setOnClickListener { view ->
            startActivity(Intent(this, NewActivity::class.java))
        }
        //setUpTabs()

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


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

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
