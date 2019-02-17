package translatinPlug;


import java.util.HashMap;

import static translatinPlug.EncryptionAndDecrytion2.parseByte2HexStr;

/**
 * @program: DatabaseEncryption
 * @description: Define a plug to translate SQL to search in encryptedDatabase
 * @author: WYY
 * @create: 2018-12-25 13:21
 **/
public class TranslationPlug {
    private static HashMap map=new HashMap();
    static {
        map.put('A',"1");   map.put('a',"J");
        map.put('B',"1");   map.put('b',"J");
        map.put('C',"2");   map.put('c',"K");
        map.put('D',"2");   map.put('d',"K");
        map.put('E',"3");   map.put('e',"L");
        map.put('F',"3");   map.put('f',"L");
        map.put('G',"4");   map.put('g',"M");
        map.put('H',"4");   map.put('h',"M");
        map.put('I',"5");   map.put('i',"N");
        map.put('J',"5");   map.put('j',"N");
        map.put('K',"6");   map.put('k',"O");
        map.put('L',"6");   map.put('l',"O");
        map.put('M',"7");   map.put('m',"P");
        map.put('N',"7");   map.put('n',"P");
        map.put('O',"8");   map.put('o',"Q");
        map.put('P',"8");   map.put('p',"Q");
        map.put('Q',"9");   map.put('q',"R");
        map.put('R',"9");   map.put('r',"R");
        map.put('S',"A");   map.put('s',"S");
        map.put('T',"A");   map.put('t',"S");
        map.put('U',"B");   map.put('u',"T");
        map.put('V',"B");   map.put('v',"T");
        map.put('W',"C");   map.put('w',"U");
        map.put('X',"C");   map.put('x',"U");
        map.put('Y',"D");   map.put('y',"V");
        map.put('Z',"D");   map.put('z',"V");
        map.put('0',"E");
        map.put('1',"E");
        map.put('2',"F");
        map.put('3',"F");
        map.put('4',"G");
        map.put('5',"G");
        map.put('6',"H");
        map.put('7',"H");
        map.put('8',"I");
        map.put('9',"I");
        }
    public static boolean isChineseChar(char c) {
        return String.valueOf(c).matches("[\u4e00-\u9fa5]");
    }

    public String getIndex(String sql_v){
        ChineseToChar chinesetoChar=new ChineseToChar();
        StringBuffer indexBuffer=new StringBuffer();
        int i=(sql_v.charAt(0)=='%'?1:0);
        while(sql_v.charAt(i)!='%'){
            if(isChineseChar(sql_v.charAt(i))){
                indexBuffer.append(map.get(chinesetoChar.getFirstChar(sql_v.charAt(i))));
            }else{
                indexBuffer.append(map.get(sql_v.charAt(i)));
            }
            if(i==sql_v.length()-1)break;
            i++;
        }
        String res=indexBuffer.toString();
        indexBuffer.delete(0,indexBuffer.length()-1);
        return res;

    }

    public String translateLikeSearch_SQL(String sql) {
        TranslationPlug getindex=new TranslationPlug();
        int index = sql.indexOf("where");
        String where_phrase = sql.substring(index + 6);
        String [] ss = where_phrase.split(",");
        String new_where_phrase = "";
        for(String s:ss){
            s = s.trim();
            int index0 = s.indexOf("like");
            String plain_text = s.substring(index0 + 4).trim();
            String new_plain_text = "";
            if((plain_text.startsWith("\"")&&plain_text.endsWith("\""))||(plain_text.startsWith("\'")&&plain_text.endsWith("\'"))){
                plain_text = plain_text.substring(1,plain_text.length() - 1);
            }
            String [] pts = plain_text.split("%");
            for(String pt:pts){
                if(!pt.equals("")){
                    pt = getindex.getIndex(pt);
                    new_plain_text += pt;
                }
                new_plain_text += "%";
            }
            new_where_phrase += s.substring(0,index0 + 4).trim() + " \'"+new_plain_text.substring(0,new_plain_text.length()-(plain_text.endsWith("%")?0:1))+"\',";
        }
        new_where_phrase = new_where_phrase.substring(0,new_where_phrase.length() - 1);
        int likeindex=where_phrase.indexOf("like");
        String newPhrase=new_where_phrase.substring(likeindex);
        new_where_phrase="contentindex "+newPhrase;
        return sql.replace(where_phrase,new_where_phrase);
    }
    public String translateLikeInsert_SQL(String sql,int position,int indexPosition){
        EncryptionAndDecrytion aes=new EncryptionAndDecrytion();
        //EncryptionAndDecrytion2 aes=new EncryptionAndDecrytion2();
        TranslationPlug getindex=new TranslationPlug();
        int index0 = sql.indexOf('(');
        int index1 = sql.indexOf(')');
        String raw = sql.substring(index0+1,index1);
        String[] ss = raw.split(",");
        String new_raw = "";

        for(int i=0;i<ss.length;i++){
            if(i!=position&&i!=indexPosition){
                new_raw += ss[i];
                new_raw += ",";
            }else if(i==position||i==indexPosition){
                if(i==position){
                    ss[position] = ss[position].trim();
                    if((ss[position].startsWith("\"")&&ss[position].endsWith("\""))||(ss[position].startsWith("\'")&&ss[position].endsWith("\'"))){
                        ss[position] = ss[position].substring(1,ss[position].length() - 1);
                    }
                    new_raw += "\"" + aes.encrypt(ss[position]) + "\"";
                   // new_raw += "\"" + parseByte2HexStr(aes.encrypt(ss[position],"12345678")) + "\"";
                    new_raw += ",";
                }else if(i==indexPosition){
                    String index=getindex.getIndex(ss[position]);
                    new_raw += "\"" + index + "\"";
                    new_raw += ",";
                }
            }
        }
        new_raw = new_raw.substring(0,new_raw.length() - 1);
        return sql.replace(raw,new_raw);
    }

}
