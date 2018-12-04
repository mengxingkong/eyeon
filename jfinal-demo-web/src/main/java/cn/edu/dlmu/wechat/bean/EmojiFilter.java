package cn.edu.dlmu.wechat.bean;


public class EmojiFilter {

    public boolean containsEmoji(String source) {
        if (null==source || "".equals(source)) {
            return false;
        }
        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

                if (isEmojiCharacter(codePoint)) {
                //do nothing，判断到了这里表明，确认有表情字符
                return true;
            }
        }

        return false;
    }

    /// <summary>
    /// 去掉表情符号
    /// </summary>
    /// <param name="codePoint"></param>
    /// <returns></returns>
    private boolean isEmojiCharacter(char codePoint)
    {
        return (codePoint >= 0x2600 && codePoint <= 0x27BF) // 杂项符号与符号字体
                || codePoint == 0x303D
                || codePoint == 0x2049
                || codePoint == 0x203C
                || (codePoint >= 0x2000 && codePoint <= 0x200F) //
                || (codePoint >= 0x2028 && codePoint <= 0x202F) //
                || codePoint == 0x205F //
                || (codePoint >= 0x2065 && codePoint <= 0x206F) //
                                                                   /* 标点符号占用区域 */
                || (codePoint >= 0x2100 && codePoint <= 0x214F) // 字母符号
                || (codePoint >= 0x2300 && codePoint <= 0x23FF) // 各种技术符号
                || (codePoint >= 0x2B00 && codePoint <= 0x2BFF) // 箭头A
                || (codePoint >= 0x2900 && codePoint <= 0x297F) // 箭头B
                || (codePoint >= 0x3200 && codePoint <= 0x32FF) // 中文符号
                || (codePoint >= 0xD800 && codePoint <= 0xDFFF) // 高低位替代符保留区域
                || (codePoint >= 0xE000 && codePoint <= 0xF8FF) // 私有保留区域
                || (codePoint >= 0xFE00 && codePoint <= 0xFE0F) // 变异选择器
                //   || (codePoint >= U + 2600 && codePoint <= 0xFE0F)
                || codePoint >= 0x10000; // Plane在第二平面以上的，char都不可以存，全部都转
    }


    /**
     * 过滤emoji 或者 其他非文字类型的字符
     * @param source
     * @return
     */
    public  String filterEmoji(String source) {

        if (!containsEmoji(source)) {
            return source;//如果不包含，直接返回
        }
        //到这里铁定包含
        StringBuilder buf = null;

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }

                buf.append(codePoint);
            } else {
            }
        }

        if (buf == null) {
            return source;//如果没有找到 emoji表情，则返回源字符串
        } else {
            if (buf.length() == len) {//这里的意义在于尽可能少的toString，因为会重新生成字符串
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }

    }
}
