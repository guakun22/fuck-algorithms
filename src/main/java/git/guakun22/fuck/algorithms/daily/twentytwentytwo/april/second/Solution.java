package git.guakun22.fuck.algorithms.daily.twentytwentytwo.april.second;

public class Solution {
    public int strongPasswordChecker(String password) {
        int misChar, misDigit, misUppercaseChar, len;
        misChar = misDigit = misUppercaseChar = 1;
        len = password.length();
        for (char character : password.toCharArray()) {
            if (Character.isLowerCase(character)) {
                misChar = 0;
            }
            if (Character.isUpperCase(character)) {
                misUppercaseChar = 0;
            }
            if (Character.isDigit(character)) {
                misDigit = 0;
            }
        }
        int missingCategoriesCount = misChar + misDigit + misUppercaseChar;
        if (len < 6) {
            return Math.max(6 - len, missingCategoriesCount);
        } else if (len <= 20) {
            int replacementRequiredCount, repeatCount;
            replacementRequiredCount = repeatCount = 0;
            char cursor = '@';

            for (int i = 0; i < len; i++) {
                char currentCharacter = password.charAt(i);
                if (currentCharacter == cursor) {
                    repeatCount++;
                } else {
                    replacementRequiredCount += repeatCount / 3;
                    repeatCount = 1;
                    cursor = currentCharacter;
                }
            }

            replacementRequiredCount += repeatCount / 3;

            System.out.println(Math.max(replacementRequiredCount, 3 - missingCategoriesCount));
            return Math.max(replacementRequiredCount, missingCategoriesCount);
        } else {
            int replacementRequiredCount, repeatCount, removeRequiredCount, nextRoundRemoveRequiredCount;
            replacementRequiredCount = repeatCount = nextRoundRemoveRequiredCount = 0;
            removeRequiredCount = len - 20;
            char cursor = '@';

            // 这一次实际上只处理了 len % 3 == 0 长度的连续字符串
            for (int i = 0; i < len; i++) {
                char currentCharacter = password.charAt(i);
                if (cursor == currentCharacter) {
                    repeatCount ++;
                } else {
                    if (removeRequiredCount > 0 && repeatCount >= 3) {
                        if (repeatCount % 3 == 0) {
                            removeRequiredCount--;
                            replacementRequiredCount--;
                        } else if (repeatCount % 3 == 1) {
                            nextRoundRemoveRequiredCount++;
                        }
                    }
                    replacementRequiredCount += repeatCount / 3;
                    repeatCount = 1;
                    cursor = currentCharacter;
                }
            }
            if (removeRequiredCount > 0 && repeatCount >= 3) {
                if (repeatCount % 3 == 0) {
                    removeRequiredCount--;
                    replacementRequiredCount--;
                } else if (repeatCount % 3 == 1) {
                    nextRoundRemoveRequiredCount++;
                }
            }
            replacementRequiredCount += repeatCount / 3;

            // 计算出了删除1个数据可以省出来的替换次数
            // 下面计算删除2个和3个是不是可以节省替换次数
            int use2 = Math.min(Math.min(replacementRequiredCount, nextRoundRemoveRequiredCount), removeRequiredCount / 2);
            replacementRequiredCount -= use2;
            removeRequiredCount -= use2 * 2;

            int use3 = Math.min(replacementRequiredCount, removeRequiredCount / 3);
            replacementRequiredCount -= use3;
            removeRequiredCount -= use3 * 2;

            return (len - 20) + Math.max(replacementRequiredCount, missingCategoriesCount);
        }
    }
}

class Runner {
    public static void main(String[] args) {
        Solution s = new Solution();
        int res1 = s.strongPasswordChecker("a");
        int res2 = s.strongPasswordChecker("a01");
        System.out.println("======================以下是结果==============================");
        System.out.println("a = " + res1);
        System.out.println("a01 = " + res2);
        System.out.println("1337C0d3 = " + s.strongPasswordChecker("1337C0d3"));
        System.out.println("333a3333a33333aAaAa333333 = " + s.strongPasswordChecker("333a3333a33333aAaAa333333"));
    }
}