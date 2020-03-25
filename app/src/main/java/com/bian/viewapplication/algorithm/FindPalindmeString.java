package com.bian.viewapplication.algorithm;

public class FindPalindmeString {
    //lg(m+n)
    public static double findMiddlePostion(int[] array1, int[] array2) {
        return 0;
    }

    public static String getLongestPalinder(String mesage) {
        String temp = parseString(mesage);
        int rightBound = 0, recentCenter = 0;
        int radiusArray[] = new int[temp.length()];
        for (int index = 1; index < temp.length(); index++) {
            int i_mirror = 2 * recentCenter - index;
            if (rightBound > index) {
                radiusArray[index] = Math.min(rightBound - recentCenter, radiusArray[i_mirror]);
            } else {
                radiusArray[index] = 0;
            }

            while (temp.charAt(index + radiusArray[index] + 1) == temp.charAt(index - radiusArray[index] - 1)) {
                radiusArray[index]++;
            }
            if (radiusArray[index] + index > rightBound) {
                rightBound = radiusArray[index] + index;
                recentCenter = index;
            }
        }
        // 找出 P 的最大值
        int maxLen = 0;
        int centerIndex = 0;
        for (int i = 1; i < temp.length() - 1; i++) {
            if (radiusArray[i] > maxLen) {
                maxLen = radiusArray[i];
                centerIndex = i;
            }
        }
        int start = (centerIndex - maxLen) / 2; //最开始讲的求原字符串下标

        return mesage.substring(start,start+maxLen);

    }

    private static String parseString(String mesage) {
        StringBuilder stringBuilder = new StringBuilder("^#");
        for (int index = 0; index < mesage.length(); index++) {
            stringBuilder.append(mesage.charAt(index));
            stringBuilder.append("#");
        }
        return stringBuilder.toString();
    }
}
