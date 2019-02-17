package translatinPlug;

/**
 * @program: DatabaseEncryption
 * @description: define a func to translate Chinese to string and get the first char
 * @author: WYY
 * @create: 2018-12-25 13:47
 **/

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
public class ChineseToChar{
    /**
     * chiese to firstchar
     * @param chinese
     * @return
     */

    public  char getFirstChar(char chinese) {
        StringBuffer pybf = new StringBuffer();

        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        try {
            String[] temp = PinyinHelper.toHanyuPinyinStringArray(chinese, defaultFormat);
            if (temp != null) {
                pybf.append(temp[0].charAt(0));
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        StringBuffer sb = new StringBuffer(pybf.toString().replaceAll("\\W", "").trim());
        String ss = sb.delete(1, sb.length()).toString();
        char res=Character.toUpperCase(ss.toCharArray()[0]);

        return res;

    }
}

