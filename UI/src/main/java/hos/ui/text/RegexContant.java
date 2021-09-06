package hos.ui.text;

/**
 * <p>Title: RegexContant </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/4/9 23:05
 */
public interface RegexContant {
    /** 手机号（只能以 1 开头） */
    String REGEX_MOBILE = "[1]\\d{0,10}";
    /** 中文（普通的中文字符） */
    String REGEX_CHINESE = "[\\u4e00-\\u9fa5]*";
    /** 英文（大写和小写的英文） */
    String REGEX_ENGLISH = "[a-zA-Z]*";
    /** 计数（非 0 开头的数字） */
    String REGEX_COUNT = "[1-9]\\d*";
    /** 用户名（中文、英文、数字） */
    String REGEX_NAME = "[" + REGEX_CHINESE + "|" + REGEX_ENGLISH + "|" + "\\d*" + "]*";
    /** 非空格的字符（不能输入空格） */
    String REGEX_NONNULL = "\\S+";
    /** 英文和数字 */
    String REGEX_ENGLISH_NUMBER = "^[A-Za-z0-9]+$";
    /** 身份证号 */
    String REGEX_ID_CARD = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";

}
