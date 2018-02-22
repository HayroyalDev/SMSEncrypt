package com.hayroyal.mavericks.smsencrypt.Encryption;

import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by mavericks on 2/22/18.
 */

public class BlowfishJava {
    String algorithm = "Blowfish";
    String TAG = "Algo";
    public String encrypt(String data, String key){
        String retVal = null;
        try
        {
            SecretKeySpec skeySpec = getSecretKeySpec(key);
            Log.e(TAG, "encrypt: " + skeySpec.toString());

            // Instantiate the cipher.
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

            //byte[] encrypted = cipher.doFinal( URLEncoder.encode(data).getBytes() );
            byte[] encrypted = cipher.doFinal( data.getBytes() );
            retVal = new String(encrypted);
            Log.e(TAG, "encrypt: " + retVal);
        }
        catch (Exception ex)
        {
            System.out.println("Exception in CryptoUtil.encrypt():");
            ex.printStackTrace();
            retVal = null;
        }
        finally
        {
            return retVal;
        }
    }

    void decrypt(String data, String key){

    }

    private SecretKeySpec getSecretKeySpec(String key) throws Exception
    {
        SecretKeySpec skeySpec = new SecretKeySpec( key.getBytes(), "EC" );
        return skeySpec;
    }
}
