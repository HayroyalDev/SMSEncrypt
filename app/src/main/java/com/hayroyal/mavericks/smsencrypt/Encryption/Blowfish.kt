package com.hayroyal.mavericks.smsencrypt.Encryption

import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec




/**
 * Created by mavericks on 2/22/18.
 */
class Blowfish{
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        val TAG = "Blowfish"
        fun encrypt(data : String, key : String) : String{
            var keyData = key.toByteArray()
            val secretKeySpec = SecretKeySpec(keyData, "Blowfish")
            val cipher = Cipher.getInstance("Blowfish")
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
            val hasil = cipher.doFinal(data.toByteArray())
            Log.e(TAG, hasil.toString())
            //var finalData = Base64.getEncoder().encodeToString(hasil)
            return hasil.toString()
        }

        fun decrypt(data : String, key : String) : String{
            val keyData = key.toByteArray()
            val secretKeySpec = SecretKeySpec(keyData, "Blowfish")
            val cipher = Cipher.getInstance("Blowfish")
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
            val hasil = cipher.doFinal(data.toByteArray())
            Log.e(TAG, hasil.toString())
            return hasil.toString()

        }
    }
}