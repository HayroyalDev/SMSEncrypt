package com.hayroyal.mavericks.smsencrypt.Encryption

import android.util.Base64
import android.util.Log
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec




/**
 * Created by mavericks on 2/22/18.
 */
class Blowfish{
    companion object {
        var sep = "VXcBW9"
        internal var algorithm = "Blowfish"
        internal var TAG = "Algo"
        fun encrypt(data: String, key: String): String? {
            var retVal: String? = null
            try {
                val secretKeySpec = getSecretKeySpec(key)
                // Instantiate the cipher.
                val cipher = Cipher.getInstance(algorithm)
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
                val encrypted = cipher.doFinal(data.toByteArray())
                retVal = String(Base64.encode(encrypted, Base64.NO_WRAP))
            } catch (ex: Exception) {
                ex.printStackTrace()
                retVal = null
            } finally {
                return retVal
            }
        }

        fun decrypt(data: String, key: String): String? {
            try {
                val dec = Base64.decode(data, Base64.NO_WRAP)
                val secretKeySpec = getSecretKeySpec(key)
                val cipher = Cipher.getInstance("Blowfish")
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
                val hasil = cipher.doFinal(dec)
                Log.e(TAG, String(hasil))
                return String(hasil)
            } catch (ex: Exception) {
                println("Exception in CryptoUtil.encrypt():")
                ex.printStackTrace()
                return null
            }

        }

        @Throws(Exception::class)
        private fun getSecretKeySpec(key: String): SecretKeySpec {
            return SecretKeySpec(key.toByteArray(), "ecc")
        }
    }
}