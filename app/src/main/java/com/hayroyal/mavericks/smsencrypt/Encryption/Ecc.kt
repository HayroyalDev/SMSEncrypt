package com.hayroyal.mavericks.smsencrypt.Encryption

import android.util.Log
import java.security.KeyPairGenerator
import java.security.spec.ECGenParameterSpec

/**
 * Created by mavericks on 2/22/18.
 */
class Ecc{
    var key = "key";
    var TAG = "ECC"
    companion object {
        var TAG = "ECC"
        fun generate(){
            val kpg = KeyPairGenerator.getInstance("EC")
            val ecsp  = ECGenParameterSpec("secp192r1")
            kpg.initialize(ecsp)

            val kp = kpg.genKeyPair()
            val privKey = kp.getPrivate()
            val pubKey = kp.getPublic()
            Log.e(TAG, privKey.toString())
            Log.e(TAG, pubKey.toString())
        }
    }
}