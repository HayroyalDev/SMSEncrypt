package com.hayroyal.mavericks.smsencrypt.Encryption


/**
 * Created by mavericks on 2/23/18.
 */
class GenerateKey{
    companion object {
        val ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

        fun generate() : String{
            var count = 6;
            val builder = StringBuilder()
            while (count-- !== 0) {
                val character = (Math.random() * ALPHA_NUMERIC_STRING.length)
                builder.append(ALPHA_NUMERIC_STRING[character.toInt()])
            }
            return builder.toString()
        }
    }
}