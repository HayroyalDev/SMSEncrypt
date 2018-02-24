package com.hayroyal.mavericks.smsencrypt.Helper

/**
 * Created by mavericks on 2/21/18.
 */

class Sms {
    var address: String? = null
    var message: String? = null
    var type: String? = null
    var date: String? = null
    override fun toString(): String {
        return "Sms(address=$address, message=$message, type=$type, date=$date)"
    }


}
