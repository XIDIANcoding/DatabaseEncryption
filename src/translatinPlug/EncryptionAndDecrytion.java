package translatinPlug; /**
 * @program: DatabaseEncryption
 * @description: Define AES
 * @author: WYY
 * @create: 2018-12-25 13:30
 **/
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class EncryptionAndDecrytion {
    public static final String VIPARA = "aabbccddeeffgghh";   //AES 为16bytes. DES 为8bytes
    //编码方式
    public static final String bm = "UTF-8";

    //私钥
    private static final String ASE_KEY="aabbccddeeffgghh";   //AES固定格式为128/192/256 bits.即：16/24/32bytes。DES固定格式为128bits，即8bytes。

    /**
     * 加密
     *
     * @param cleartext
     * @return
     */
    public String encrypt(String cleartext) {
        //加密方式： AES128(CBC/PKCS5Padding) + Base64, 私钥：aabbccddeeffgghh
        try {
            //使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
            //两个参数，第一个为私钥字节数组， 第二个为加密方式 AES或者DES
            SecretKeySpec key = new SecretKeySpec(ASE_KEY.getBytes(), "AES");
            //实例化加密类，参数为加密方式，要写全
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //PKCS5Padding比PKCS7Padding效率高，PKCS7Padding可支持IOS加解密
            //初始化，此方法可以采用三种方式，按加密算法要求来添加。（1）无第三个参数（2）第三个参数为SecureRandom random = new SecureRandom();中random对象，随机数。(AES不可采用这种方法)（3）采用此代码中的IVParameterSpec
            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
            //加密操作,返回加密后的字节数组，然后需要编码。主要编解码方式有Base64, HEX, UUE,7bit等等。此处看服务器需要什么编码方式
            byte[] encryptedData = cipher.doFinal(cleartext.getBytes());

            return new BASE64Encoder().encode(encryptedData);
//            byte[]raw=ASE_KEY.getBytes();
//            SecretKeySpec key=new SecretKeySpec(raw,"AES");
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//            IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
//            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
//            byte[] encryptedData = cipher.doFinal(cleartext.getBytes());
//            return byte2hex(encryptedData).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param encrypted
     * @return
     */
    public String decrypt(String encrypted) {
        try {
            byte[] raw = ASE_KEY.getBytes("ASCII");
            IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
            SecretKeySpec key = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            //与加密时不同MODE:Cipher.DECRYPT_MODE
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            byte[] byteMi = new BASE64Decoder().decodeBuffer(encrypted);
            //byte[]byteMi=hex2byte(encrypted);
            try{
                byte[] original = cipher.doFinal(byteMi);
                String originalString = new String(original);
                return originalString;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
//            byte[] decryptedData = cipher.doFinal(byteMi);
//            return new String(decryptedData, bm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
//    public static byte[] hex2byte(String strhex) {
//        if (strhex == null) {
//            return null;
//        }
//        int l = strhex.length();
//        if (l % 2 == 1) {
//            return null;
//        }
//        byte[] b = new byte[l / 2];
//        for (int i = 0; i != l / 2; i++) {
//            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
//                    16);
//        }
//        return b;
//    }
//    public static String byte2hex(byte[] b) {
//        String hs = "";
//        String stmp = "";
//        for (int n = 0; n < b.length; n++) {
//            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
//            if (stmp.length() == 1) {
//                hs = hs + "0" + stmp;
//            } else {
//                hs = hs + stmp;
//            }
//        }
//        return hs.toUpperCase();
//    }


}
