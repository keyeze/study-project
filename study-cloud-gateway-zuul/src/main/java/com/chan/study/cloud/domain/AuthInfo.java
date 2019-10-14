package com.chan.study.cloud.domain;

import java.util.HashMap;
import java.util.Map;

public class AuthInfo {
}

class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]),i};
            }
        }
        return null;
    }
    public static void main(String[] args) {
        int[] nums = {0,3,-3,4,-1};
        int target = -1;
        new Solution().twoSum(nums, target);
    }

}
