package org.wangliang.app.learn.indexer;

import android.util.Log;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 字符串工具类
 * Created by Liuzhao
 * 2011-7-29
 */
public class MyStringUtility {

	/**
	 * 将汉字转换成拼音
	 * @param cnStr 要转换的中文字符串
	 * @return 转换完的英文字符串
	 *
	 * Created by Liuzhao
	 * 2011-7-29 上午12:27:11
	 */
	public static String Chinese2Pinyin(String cnStr) {
		
		//输出格式参数
		HanyuPinyinOutputFormat PINYIN_FORMAT = new HanyuPinyinOutputFormat();
		PINYIN_FORMAT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		PINYIN_FORMAT.setVCharType(HanyuPinyinVCharType.WITH_V);
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < cnStr.length(); i++) {
			char c = cnStr.charAt(i);
			if (c <= 255) {
				sb.append(c);
			} 
			else {
				String pinyin = null;
				try {
					String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, PINYIN_FORMAT);
					pinyin = pinyinArray[0];
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					Log.e("MyStringUtility", "Chinese2PinyinError");
				} catch (NullPointerException e) {
					// 如果是日文，可能抛出该异常
				}
				if (pinyin != null) {
					sb.append(pinyin);
				}
			}
		}
		return sb.toString();
	}
}
