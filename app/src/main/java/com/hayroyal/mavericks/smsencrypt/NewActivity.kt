package com.hayroyal.mavericks.smsencrypt

import android.Manifest
import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.telephony.SmsManager
import android.telephony.SubscriptionInfo
import android.telephony.SubscriptionManager
import android.util.Log
import android.widget.Button
import android.widget.TextView
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
                    selectSim()
                }
            }


        })

    }

    fun selectSim() {
        val rxx = RxPermissions(this)
        rxx.request(Manifest.permission.READ_PHONE_STATE)
                .subscribe({ granted ->
                    if(granted){

                        val sub : SubscriptionManager  = SubscriptionManager.from(applicationContext)
                        val subList : List<SubscriptionInfo> = sub.activeSubscriptionInfoList
                        for(list in subList){
                            Log.e(TAG,list.subscriptionId.toString())
                            Log.e(TAG,list.carrierName.toString())
                        }
                        if(subList.size > 1){
                            AlertDialog.Builder(this)
                                    .setTitle("Select Sim")
                                    .setMessage("Kindly select a sim which the message will be sent from")
                                    .setPositiveButton("${subList[0].carrierName} - Sim 1", { dialog, which ->
                                        Log.e(TAG, "${subList[0].carrierName} - Sim 1}")
                                        sendSms(newmsg!!,subList[0].subscriptionId)

                                    })
                                    .setNegativeButton("${subList[1].carrierName} - Sim 2", {dialog, which ->
                                        Log.e(TAG, "${subList[1].carrierName} - Sim 2}")
                                        sendSms(newmsg!!,subList[1].subscriptionId)
                                    }).show()
                        }else if(subList.size == 1){
                            sendSms(newmsg!!,subList[0].subscriptionId)
                        }else{
                            Snackbar.make(new_cood, "Sim Not Present in Device", Snackbar.LENGTH_LONG).show()
                        }
                    }else{
                        Snackbar.make(new_cood, "Message Could Not Be Sent At This Moment. Kindly Accept Permission", Snackbar.LENGTH_LONG).show()
                    }
                })

    }

    fun sendSms(text:String, subId : Int){
        //Getting intent and PendingIntent instance

        val smsManager = SmsManager.getDefault()
        val rxx = RxPermissions(this)
        rxx.request(Manifest.permission.SEND_SMS)
                .subscribe({ granted ->
                    if(granted){
                       val list : ArrayList<String> = SmsManager.getDefault().divideMessage(text)
                        if(list.size == 1){
                            val intent =  Intent(applicationContext,NewActivity::class.java)
                            val pi : PendingIntent = PendingIntent.getActivity(applicationContext, 0, intent,0)
                            SmsManager.getSmsManagerForSubscriptionId(subId).sendTextMessage(address,null,text,null,pi)
                            Toast.makeText(this, "Sending Message...", Toast.LENGTH_LONG).show()
                        }else{
                            Snackbar.make(new_cood, "Encrypted Message length is more that 160.", Snackbar.LENGTH_LONG).show()
                            //SmsManager.getSmsManagerForSubscriptionId(subId).sendMultipartTextMessage(address,null,list,)
                        }
                        //onBackPressed()
                    }else{
                        Snackbar.make(new_cood, "Message Could Not Be Sent At This Moment.", Snackbar.LENGTH_LONG).show()
                    }
                })
    }

}
