package com.bian.viewapplication.algorithm;

public class SubStringAlgorithm {
    public static int getIndexUsKMP(String mainString, String pattern) {
        int[] nextArray = getNextArray(pattern);
        int mainBeginIndex = 0;
        int patternIndex = 0;
        while (mainBeginIndex < mainString.length()) {
            if (patternIndex == -1 || mainString.charAt(mainBeginIndex) == pattern.charAt(patternIndex)) {
                patternIndex++;
                mainBeginIndex++;
            } else {
                patternIndex = nextArray[patternIndex];
            }
            if (patternIndex == pattern.length()) {
                return mainBeginIndex - patternIndex;
            }
        }
        return -1;
    }
    /**
     * 生成next 数组
     *
     * @param pattern
     * @return
     */
    private static int[] getNextArray(String pattern) {
        int[] nextArray = new int[pattern.length()];
        int j = -1;
        int index = 0;
        nextArray[0] = -1;
        while (index < pattern.length() - 1) {
            if (j == -1 || pattern.charAt(index) == pattern.charAt(j)) {
                nextArray[++index] = ++j;
            } else {
                j = nextArray[j];
            }
        }
        return nextArray;
    }
}
