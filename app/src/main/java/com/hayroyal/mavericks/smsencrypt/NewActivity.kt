package com.hayroyal.mavericks.smsencrypt

import android.Manifest
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import com.hayroyal.mavericks.smsencrypt.Encryption.Blowfish
import com.hayroyal.mavericks.smsencrypt.Encryption.GenerateKey
import com.tbruyelle.rxpermissions2.RxPermissions

import kotlinx.android.synthetic.main.activity_new.*

class NewActivity : AppCompatActivity() {

    var address:String? = null
    var msg:String? = null
    var key:String? = null
    var encmsg:String? = null
    var newmsg:String? = null
    var TAG = "New Activity";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Compose New Message"

        send_btn.setOnClickListener({
            if(send_to.text.isEmpty()){
                send_to.error = "This Field Can\'t be Empty"
            }else{
                address = send_to.text.toString()
                if(message.text.isEmpty()){
                    message.error = "Field can\'t be Empty"
                }else{
                    msg = message.text.toString()
                    key = GenerateKey.generate()
                    encmsg = Blowfish.encrypt(msg!!.trim(),key!!.trim())
                    newmsg = key+Blowfish.sep+encmsg
                    Log.e(TAG,"Key" + key)
                    Log.e(TAG,"enc"+encmsg)
                    Log.e(TAG,newmsg)
                    sendSms(newmsg!!)
                }
            }


        })

    }

    fun sendSms(text:String){
        var smsManager = SmsManager.getDefault()
        var rxx = RxPermissions(this)
        rxx.request(Manifest.permission.SEND_SMS)
                .subscribe({ granted ->
                    if(granted){
                        smsManager.sendTextMessage(address,null,text,null,null)
                        Toast.makeText(this, "Sms Sent", Toast.LENGTH_LONG).show()
                        onBackPressed()
                    }else{
                        Snackbar.make(new_cood, "Message Could Not Be Sent At This Moment.", Snackbar.LENGTH_LONG).show()
                    }
                })
    }

}
