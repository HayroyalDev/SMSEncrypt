package com.hayroyal.mavericks.smsencrypt;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.tbruyelle.rxpermissions2.RxPermissions;


/**
 * Created by mavericks on 2/18/18.
 */

public class MainClass {
    public Context context;
    RxPermissions rxPermissions;
    public MainClass(Context cont){
        context = cont;
    }
    public void check(){
        RxPermissions rx = new RxPermissions((Activity)context);
        //rx.requestEach(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS);
//        rxPermissions = new RxPermissions((Activity)context);
//        rxPermissions.request(android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_CONTACTS, Manifest.permission.CAMERA)
//                .subscribe(granted -> {
//                    if (granted) {
//
//                    } else {
//
//                    }
//                });
    }
}
