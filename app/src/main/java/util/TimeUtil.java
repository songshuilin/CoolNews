package util;

/**
 * Created by Administrator on 2016/12/15.
 */

public class TimeUtil {
    /**
     * 毫秒数转化时间
     *
     * @param mss
     * @return
     */
    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;

        return checkZero(days, hours, minutes, seconds);
    }

    /**
     * 检查是否有哪个为零
     *
     * @param days
     * @param hours
     * @param minutes
     * @param seconds
     */
    private static String checkZero(long days, long hours, long minutes, long seconds) {
        StringBuffer data = new StringBuffer();
        if (days != 0L) {
            data.append(days + "日");
        }

        if (hours != 0L) {
            data.append(hours + "时");
        }
        if (minutes != 0L) {
            data.append(minutes + "分");
        }
        if (seconds != 0L) {
            data.append(seconds + "秒");
        }


        return data.toString();
    }


}
