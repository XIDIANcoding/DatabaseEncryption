package test;
import translatinPlug.ChineseToChar;
import translatinPlug.EncryptionAndDecrytion;
import translatinPlug.EncryptionAndDecrytion2;
import translatinPlug.TranslationPlug;

import java.util.HashMap;

import static translatinPlug.EncryptionAndDecrytion2.parseByte2HexStr;
import static translatinPlug.EncryptionAndDecrytion2.parseHexStr2Byte;

/**
 * @program: DatabaseEncryption
 * @description: test the project
 * @author: WYY
 * @create: 2018-12-25 13:42
 **/
public class Test {
    public static void main(String[]s){
        /**
         * test the aes
         */
        EncryptionAndDecrytion aes=new EncryptionAndDecrytion();
        String text=aes.encrypt("wyy");
        System.out.println(text);
        System.out.println(aes.decrypt(text));

        String content = "test";
        String password = "12345678";
        //加密
        EncryptionAndDecrytion2 aes2=new EncryptionAndDecrytion2();
        System.out.println("加密前：" + content);
        byte[] encryptResult = aes2.encrypt(content, password);
        String encryptResultStr = parseByte2HexStr(encryptResult);
        System.out.println("加密后：" + encryptResultStr);
        //解密
        byte[] decryptFrom = parseHexStr2Byte(encryptResultStr);
        byte[] decryptResult = aes2.decrypt(decryptFrom,password);
        System.out.println("解密后：" + new String(decryptResult));
        /**
         * test the chinesetoChar
         */
        ChineseToChar chineseToChar=new ChineseToChar();
        char chinese=chineseToChar.getFirstChar('阳');
        System.out.println("阳 "+chinese);

        /**
         * test the hsahmap
         */
        HashMap map=new HashMap();
        map.put('A',"1");
        System.out.println(map.get('A'));

        /**
         * test the indexDesign
         */
        TranslationPlug index=new TranslationPlug();
        System.out.println(index.getIndex("%王阳阳%"));
        System.out.println(index.getIndex("王阳阳%"));
        System.out.println(index.getIndex("%王阳阳"));
        System.out.println(index.getIndex("%wyy%"));
        System.out.println(index.getIndex("wyy%"));
        System.out.println(index.getIndex("%wyy"));

        /**
         * test select sql translation
         */
        String selectSql=index.translateLikeSearch_SQL("select * from user where name like '%wang'");
        System.out.println(selectSql);
        String  insertSql=index.translateLikeInsert_SQL("insert into students(\"M201873235\",\"王阳阳\",NULL)",1,2);
        System.out.println(insertSql);

        String id="M201873235";
        String decryption="wangyangyang";
        String index2="lll";
        String newSql="insert into students values(\""+id+"\""+",\""+decryption+"\""+",\""+index2+"\""+")";
        System.out.println(newSql);

        /**
         * test multi %
         */
        String old="%sss%aaa%sss%";
        String ss[]=old.split("%");
        for (String str:ss
             ) {
            System.out.println(str);

        }



    }

}
