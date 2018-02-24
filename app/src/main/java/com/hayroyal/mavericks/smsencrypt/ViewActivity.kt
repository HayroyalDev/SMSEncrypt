package com.hayroyal.mavericks.smsencrypt

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.hayroyal.mavericks.smsencrypt.Encryption.Blowfish
import com.hayroyal.mavericks.smsencrypt.Helper.Sms

import kotlinx.android.synthetic.main.activity_view.*

class ViewActivity : AppCompatActivity() {

    var sms : Sms? = null
    var TAG = "View Activity"
    var encr : Boolean = true
    var emsg : String? = null
    var key : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "View Message"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        sms = Sms()
        if (intent.extras != null) {
            sms!!.address = intent.getStringExtra("address")
            sms!!.message = intent.getStringExtra("body")
            sms!!.date = intent.getStringExtra("date")
            sms!!.type = intent.getStringExtra("type")
            //Log.e(TAG, sms.toString())
            if (sms!!.type == "sent") {
                supportActionBar!!.title = "Sent Message To ${sms!!.address}"
                to_from.text = "To: "
            } else {
                supportActionBar!!.title = "Inbox Message From ${sms!!.address}"
                to_from.text = "From: "
            }
            date.text = sms!!.date
            msg.text = sms!!.message
            address.text = sms!!.address
            enc.setOnClickListener({
                if(encr){
                    var split = sms!!.message!!.split(Blowfish.sep)
                    if(split.isEmpty()){
                        Toast.makeText(this, "Message is Not Encrypted", Toast.LENGTH_LONG).show()
                    }else{
                        key = split[0]
                        var smsg = split[1]
                        emsg = Blowfish.decrypt(smsg, key!!)
                        if(emsg == null){
                            Snackbar.make(cood, "Message is not Encrypted.", Snackbar.LENGTH_LONG).show()
                        }else{
                            msg.text = emsg
                            encr = false
                            enc.text = "Encrypt Message"
                        }
                    }

                }else{
                    val split = sms!!.message!!.split(Blowfish.sep)
                    if(split.isEmpty()){
                        Snackbar.make(cood, "Message can\' be encrypted.", Snackbar.LENGTH_LONG).show()
                    }else{
                        emsg = Blowfish.encrypt(msg.text.toString(), split[0])
                        msg.text = emsg
                        encr = true
                        enc.text = "Decrypt Message"
                    }
                }
            })
        } else {
            Toast.makeText(this,"No Data", Toast.LENGTH_SHORT).show()
            onBackPressed()
        }
    }

}
