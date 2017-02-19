package com.pers.myc.zhihu;

/**
 * 软件的基本配置与设置
 */

public class Config {
    //屏幕大小
    private static int screenHeight;
    private static int screenWidth;
    //主题日报标题
    private static String[] ThemeTitle = {"今日热闻", "电影日报", "互联网安全", "开始游戏", "音乐日报", "动漫日报", "日常心理学", "用户推荐日报", "不许无聊", "设计日报", "大公司日报", "财经日报", "体育日报"};
    //夜间模式
    private static boolean nightMode;

    public static boolean isNightMode() {
        return nightMode;
    }
    public static void setNightMode(boolean nightMode) {
        Config.nightMode = nightMode;
    }
    public static String[] getThemeTitle() {
        return ThemeTitle;
    }
    public static void setThemeTitle(String[] themeTitle) {
        ThemeTitle = themeTitle;
    }
    public static int getScreenHeight() {
        return screenHeight;
    }
    public static void setScreenHeight(int screenHeight) {
        Config.screenHeight = screenHeight;
    }
    public static int getScreenWidth() {
        return screenWidth;
    }
    public static void setScreenWidth(int screenWidth) {
        Config.screenWidth = screenWidth;
    }



}
