package com.bingo.main;

import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.Properties;

import com.bingo.util.PropertiesConfigUtil;

public class ParamConstant {

    public static boolean isMessage = true;

    public static boolean isSerchImg = true;
    /**
     * 请求地址
     */
    public static String url = "http://localhost:8081/query5";
    /**
     * 正则匹配后缀
     */
    public static String reg = "^.+(.JPEG|.jpeg|.JPG|.jpg|.PNG|.png|.GIF|.gif|.BMP|.bmp)$";
    /**
     * 标题
     */
    public static String title = "不喜欢我不能关闭";
    /**
     * 左边按钮
     */
    public static String left_bt_desc = "喜欢我";
    /**
     * 右边按钮
     */
    public static String right_bt_desc = "不喜欢我";
    /**
     * 图片路径
     */
    public static String imgPath = "img";
    /**
     * 滑动次数
     */
    public static int s_count = 5;
    /**
     * 点击次数
     */
    public static int c_count = 10;
    /**
     * 按钮宽
     */
    public static int bt_width = 120;
    /**
     * 按钮高
     */
    public static int bt_htight = 50;
    /**
     * 对话框图片尺寸
     */
    public static int di_size = 50;

    static {
        init();
    }

    private static void init() {
        try {
            Properties properties = PropertiesConfigUtil.getProp();
            Enumeration<?> en = properties.propertyNames();
            Object obj = ParamConstant.class.newInstance();// 获取对象
            while (en.hasMoreElements()) {
                String key = (String) en.nextElement();
                Field field = ParamConstant.class.getField(key);
                if (field.getModifiers() == 9) {
                    if (field.getType().getName().contains("int")) {
                        field.set(obj, Integer.valueOf(properties.getProperty(key)));
                    } else if (field.getType().getName().contains("boolean")) {
                        field.set(obj, Boolean.valueOf(properties.getProperty(key)));
                    } else if (field.getType().getName().contains("String")) {
                        field.set(obj, properties.getProperty(key));
                    }
                }
            }
        } catch (NoSuchFieldException | SecurityException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
