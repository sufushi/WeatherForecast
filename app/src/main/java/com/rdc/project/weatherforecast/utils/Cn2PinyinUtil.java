package com.rdc.project.weatherforecast.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Cn2PinyinUtil {

    public static StringBuilder sStringBuilder = new StringBuilder();

    public static String getPinyinHeaderChar(String chines) {
        sStringBuilder.setLength(0);
        char[] chars = chines.toCharArray();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char aChar : chars) {
            if (aChar > 128) {
                try {
                    sStringBuilder.append(PinyinHelper.toHanyuPinyinStringArray(aChar, format)[0].charAt(0));
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
            } else {
                sStringBuilder.append(aChar);
            }
        }
        return sStringBuilder.toString();
    }

    public static String getPinyinFirstLetter(String chines) {
        sStringBuilder.setLength(0);
        char c = chines.charAt(0);
        String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c);
        if (pinyinArray != null) {
            sStringBuilder.append(pinyinArray[0].charAt(0));
        } else {
            sStringBuilder.append(c);
        }
        return sStringBuilder.toString();
    }

    public static String getPinyin(String chines) {
        sStringBuilder.setLength(0);
        char[] chars = chines.toCharArray();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char aChar : chars) {
            if (aChar > 128) {
                try {
                    sStringBuilder.append(PinyinHelper.toHanyuPinyinStringArray(aChar, format)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
            } else {
                sStringBuilder.append(aChar);
            }
        }
        return sStringBuilder.toString();
    }

}
