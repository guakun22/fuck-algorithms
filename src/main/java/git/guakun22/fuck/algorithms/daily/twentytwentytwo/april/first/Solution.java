package git.guakun22.fuck.algorithms.daily.twentytwentytwo.april.first;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//https://leetcode-cn.com/problems/array-of-doubled-pairs/
public class Solution {
    public boolean canReorderDoubled(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int n : arr) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }

        if (map.getOrDefault(0, 0) % 2 != 0) {
            return false;
        }

        List<Integer> sorted = new ArrayList<Integer>(map.keySet());
        // [-2, 2, -4, 4]
        sorted.sort((a, b) -> Math.abs(a) - Math.abs(b));

        for(int n : sorted) {
            if (map.getOrDefault(2 * n, 0) < map.get(n)) {
                return false;
            }
            map.put(2 * n, map.getOrDefault(2 * n, 0) - map.get(n));
        }
        return true;
    }
}