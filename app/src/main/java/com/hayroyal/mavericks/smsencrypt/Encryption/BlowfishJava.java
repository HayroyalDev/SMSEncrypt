package com.hayroyal.mavericks.smsencrypt.Encryption;

import android.util.Base64;
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
            SecretKeySpec secretKeySpec = getSecretKeySpec(key);
            // Instantiate the cipher.
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encrypted = cipher.doFinal( data.getBytes() );
            retVal = new String(Base64.encode(encrypted, Base64.NO_WRAP));
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

    public String decrypt(String data, String key) {
       try{
           byte [] dec = (Base64.decode(data, Base64.NO_WRAP));
           SecretKeySpec secretKeySpec = getSecretKeySpec(key);
           Cipher cipher = Cipher.getInstance("Blowfish");
           cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
           byte[] hasil = cipher.doFinal(dec);
           Log.e(TAG, new String(hasil));
           return new String(hasil);
       }catch (Exception ex){
           System.out.println("Exception in CryptoUtil.encrypt():");
           ex.printStackTrace();
           return null;
       }
    }

    private SecretKeySpec getSecretKeySpec(String key) throws Exception
    {
        SecretKeySpec skeySpec = new SecretKeySpec( key.getBytes(), "ecc" );
        return skeySpec;
    }
}
