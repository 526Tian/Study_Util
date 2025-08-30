package com.cqust;

import java.util.*;

/**
 * @author Ltian
 * @date 2025/8/24 10:24
 * @description:
 */
public class LeetCodeTest {

    public static void main(String[] args) {
        setZeroes(new int[][]{{0,1,2,0},{3,4,5,2},{1,3,1,5}});
//        System.out.println(maximumSubarraySum(new int[]{1,5,4,2,9,9,9}, 3));
//        System.out.println(spiralOrder(new int[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12}}));
//        System.out.println(firstMissingPositive(new int[]{1, 2, 3}));
//        System.out.println(Arrays.toString(merge(new int [][]{{1,3}, {2, 6}})));
//        System.out.println(minWindow("abcabdebac", "cda"));
//        System.out.println(subarraySum(new int[]{1, 1, 1}, 2));
//        TreeMap<Integer, Integer> map = new TreeMap<>();
//        System.out.println(Arrays.toString(maxValue(new int[]{9,30,16,6,36,9 })));
//        System.out.println(maxWalls(new int[]{63,56,40,45,4,9,44,69,55,26,73,15,12,60,43,39,37,74,36,34,13,23,66,14,11,42,72,3,57,10,53,8,70,17,58,61,30,32}
//                , new int[]{8,7,4,8,9,5,2,4,5,2,6,9,5,9,5,3,7,6,9,2,8,7,4,3,5,1,7,5,1,3,5,3,5,4,8,7,6,4}, new int[]{6,22,50,52,20,9,23,75,26,21,60,58,41,28,30}));
    }

    public static void setZeroes(int[][] matrix) {
        // 如果原本就是0 那么也会影响当前列
        // 如果原本就是
        int rowLen = matrix.length;
        int columnLen = matrix[0].length;
        boolean rowZeroFlag = false, columnZeroFlag = false;
        for (int[] value : matrix) {
            if (value[0] == 0) {
                columnZeroFlag = true;
                break;
            }
        }

        for (int i = 0; i < columnLen; ++ i) {
            if (matrix[0][i] == 0) {
                rowZeroFlag = true;
                break;
            }
        }

        for (int i = 1; i < rowLen; ++ i) {
            for (int j = 1; j < columnLen; ++ j) {
                if (matrix[i][j] == 0) {
                    matrix[0][j] = 0;
                    matrix[i][0] = 0;
                }
            }
        }

        for (int i = 1; i < rowLen; ++ i) {
            if (matrix[i][0] == 0) {
                Arrays.fill(matrix[i], 0);
            }
        }

        for (int i = 1; i < columnLen; ++ i) {
            if (matrix[0][i] == 0) {
                for (int j = 0; j < rowLen; ++ j) {
                    matrix[j][i] = 0;
                }
            }
        }



        if (columnZeroFlag) {
            for (int i = 0; i < rowLen; ++ i) {
                matrix[i][0] = 0;
            }
        }

        if (rowZeroFlag) {
            for (int i = 0; i < columnLen; ++ i) {
                matrix[0][i] = 0;
            }
        }

    }

    public static long maximumSubarraySum(int[] nums, int k) {
        int len = nums.length;
        Set<Integer> numSet = new HashSet<>();
        long sum = 0;
        long ans = 0;
        for (int i = 0, j = 0; i < len; ++ i) {
            while (numSet.contains(nums[i])) {
                sum -= nums[j];
                numSet.remove(nums[j]);
                j ++;
            }
            numSet.add(nums[i]);
            sum += nums[i];
            if (i - j + 1 == k) {
                ans = Math.max(ans, sum);
                sum -= nums[j];
                numSet.remove(nums[j]);
                j ++;
            }
        }
        return ans;
    }

    public static List<Integer> spiralOrder(int[][] matrix) {
        int l = 0, r = matrix[0].length - 1, low = 0, hight = matrix.length - 1;
        List<Integer> ans = new ArrayList<>();
        while (true) {
            // 往右走
            if (l > r) {
                break;
            }
            for (int i = l; i <= r; ++ i) {
                ans.add(matrix[low][i]);
            }
            low ++;

            // 往下走
            if (low > hight) {
                break;
            }
            for (int i = low; i <= hight; ++ i) {
                ans.add(matrix[i][r]);
            }
            r --;

            // 往左走
            if (l > r) {
                break;
            }
            for (int i = r; i >= l; -- i) {
                ans.add(matrix[hight][i]);
            }
            hight --;

            // 往上走
            if (low > hight) {
                break;
            }
            for (int i = hight; i >= low; -- i) {
                ans.add(matrix[i][l]);
            }
            l ++;
        }
        return ans;
    }

//    public void setZeroes(int[][] matrix) {
//        // 行 x... 用常数来记录下来
//        // 列 y... 用常数来记录下来
//        int rowLen = matrix.length;
//        int columnLen = matrix[0].length;
//        Set<Integer> rowZeroSet = new HashSet<>(rowLen);
//        Set<Integer> columnZeroSet = new HashSet<>(columnLen);
//        for (int i = 0; i < rowLen; ++ i) {
//            for (int j = 0; j < columnLen; ++ j) {
//                if (matrix[i][j] == 0) {
//                    rowZeroSet.add(i);
//                    columnZeroSet.add(j);
//                }
//            }
//        }
//
//        for (Integer index: rowZeroSet) {
//            Arrays.fill(matrix[index], 0);
//        }
//
//        for (Integer index: columnZeroSet) {
//            for (int i = 0; i < rowLen; ++ i) {
//                matrix[i][index] = 0;
//            }
//        }
//    }

    public void rotate(int[] nums, int k) {
        // len = nums.length;
        // k = k % len
        // nums * 2
        // 移动右指针到2 * len - 1 - k位置
        // 再填充
        int len = nums.length;
        k = k % len;
        swapRange(nums, 0, len - 1);
        swapRange(nums, 0, k - 1);
        swapRange(nums, k, len - 1);
    }

    private void swapRange(int[] nums, int l, int r) {
        while(l < r) {
            int temp = nums[l];
            nums[l] = nums[r];
            nums[r] = temp;
            l ++;
            r --;
        }
    }

    public static int firstMissingPositive(int[] nums) {
        int len = nums.length;
        for (int i = 0; i < len; ++ i) {
            if (nums[i] <= 0 || nums[i] > len) {
                nums[i] = len + 1;
            }
        }
        for (int i = 0; i < len; ++ i) {
            int absNum = Math.abs(nums[i]);
            if (absNum <= len) {
                nums[absNum - 1] = -1 * Math.abs(nums[absNum - 1]);
            }
        }
        for (int i = 0; i < len; ++ i) {
            if (nums[i] > 0) {
                return i + 1;
            }
        }
        return len + 1;
    }

//    public int firstMissingPositive(int[] nums) {
//        int len = nums.length;
//        Set<Integer> numSet = new HashSet<>(len);
//        for (int i = 0; i < len; ++ i) {
//            numSet.add(nums[i]);
//        }
//        int ans = len + 1;
//        for (int i = 1; i <= len + 1; i ++) {
//            if (!numSet.contains(i)) {
//                ans = i;
//                break;
//            }
//        }
//        return  ans;
//    }

//    public int[] productExceptSelf(int[] nums) {
//        // 0的个数 个数 > 1 直接返回0数组 个数 == 0 乘积不算0 记录0下标 个数 == 0 直接算0下标
//        int len = nums.length;
//        int zeroCount = 0;
//        int zeroIndex = -1;
//        int allMultiplicationResult= 1;
//        for (int i = 0; i < len; ++ i) {
//            if (nums[i] == 0) {
//                zeroCount ++;
//                zeroIndex = i;
//            } else {
//                allMultiplicationResult *= nums[i];
//            }
//        }
//        int[] ans = new int[len];
//        if (zeroCount > 1) {
//            return ans;
//        }
//        if (zeroCount == 1) {
//            ans[zeroIndex] = allMultiplicationResult;
//            return ans;
//        }
//
//        for (int i = 0; i < len; ++ i) {
//            ans[i] = allMultiplicationResult / nums[i];
//        }
//
//        return ans;
//    }
//
//    public void rotate(int[] nums, int k) {
//        // len = nums.length;
//        // k = len % len
//        // nums * 2
//        // 移动左指针到k位置
//        // arrays.copy
//        int len = nums.length;
//        int[] moveArray = new int[len * 2];
//        for (int i = 0; i < len; ++ i) {
//            moveArray[i] = nums[i];
//            moveArray[i + len] = nums[i];
//        }
//
//        k = k % len;
//
//        int r = 2 * len - 1 - k;
//        int l = r + 1 - len;
//
//        for (int i = 0; i < len; ++ i) {
//            nums[i] = moveArray[l];
//            l ++;
//        }
//    }

    public static int[][] merge(int[][] intervals) {
        // (i, j) 元素 对i进行排序
        // 第i个元素与i+1元素，没有重叠区域，直接将i 移动到 i + 1
        //  第i个元素与i+1元素，有重叠区域，合并i, i + 1为同一个元素 记录
        // 怎么判断是否有重叠？ 维护连续区间的(l, r) 如果(i, j)这个元素 l <= i <= r 合并区间(l, max(r, j))
        List<Map.Entry<Integer, Integer>> intervalList = new ArrayList<>();
        for (int[] interval: intervals) {
            intervalList.add(Map.entry(interval[0], interval[1]));
        }
        intervalList.sort(Comparator.comparingInt(Map.Entry::getKey));
        int len = intervals.length;
        int[][] ans = new int[len][2];
        int index = 0;
        int preL = -1, preR = -1;
        for (int i = 0; i < len; ++ i) {
            Map.Entry<Integer, Integer> entry = intervalList.get(i);
            if (i == 0) {
                preL = entry.getKey();
                preR = entry.getValue();
                continue;
            }
            if (entry.getKey() >= preL && entry.getKey() <= preR) {
                preR = Math.max(preR, entry.getValue());
                continue;
            }
            ans[index][0] = preL;
            ans[index][1] = preR;
            index ++;
            preL = entry.getKey();
            preR = entry.getValue();
        }
        ans[index][0] = preL;
        ans[index][1] = preR;
        return Arrays.copyOf(ans, index);
    }

//    public static int[][] merge(int[][] intervals) {
//        // (i, j) 元素 对i进行排序
//        // 第i个元素与i+1元素，没有重叠区域，直接将i 移动到 i + 1
//        //  第i个元素与i+1元素，有重叠区域，合并i, i + 1为同一个元素 记录
//        // 怎么判断是否有重叠？ 维护连续区间的(l, r) 如果(i, j)这个元素 l <= i <= r 合并区间(l, max(r, j))
//        List<Map.Entry<Integer, Integer>> intervalList = new ArrayList<>();
//        for (int[] interval: intervals) {
//            intervalList.add(Map.entry(interval[0], interval[1]));
//        }
//        intervalList.sort(Comparator.comparingInt(Map.Entry::getKey));
//        int len = intervals.length;
//        List<Map.Entry<Integer, Integer>> ans = new ArrayList<>();
//        int preL = -1, preR = -1;
//        for (int i = 0; i < len; ++ i) {
//            Map.Entry<Integer, Integer> entry = intervalList.get(i);
//            if (i == 0) {
//                preL = entry.getKey();
//                preR = entry.getValue();
//                continue;
//            }
//            if (entry.getKey() >= preL && entry.getKey() <= preR) {
//                preR = Math.max(preR, entry.getValue());
//                continue;
//            }
//            ans.add(Map.entry(preL, preR));
//            preL = entry.getKey();
//            preR = entry.getValue();
//        }
//        ans.add(Map.entry(preL, preR));
//        return ans.stea;
//    }

    public int maxSubArray(int[] nums) {
        // sum[l, r] <= 0 那么可以将l往右移动直至sum[l, r] > 0
        // 因为 sum[l, y] <  sum[r + 1, y]
        // -5 + x < x && 0 + x <= x
        int sum = 0;
        int maxSum = Integer.MIN_VALUE;
        for (int i = 0, j = 0; i < nums.length; ++ i) {
            sum += nums[i];
            maxSum = Math.max(sum, maxSum);
            while(j <= i && sum <= 0) {
                sum -= nums[j];
                j ++;
                if (j <= i) {
                    maxSum = Math.max(sum, maxSum);
                }
            }
        }
        return maxSum;
    }

    public static String minWindow(String s, String t) {
        // 子串[l, r] l、r两个字符一定是t中的字符
        Map<Character, Integer> targetCharCountMap = new HashMap<>();
        int targetLen = t.length();
        for (int i = 0; i < targetLen; ++ i) {
            char c = t.charAt(i);
            targetCharCountMap.put(c, targetCharCountMap.getOrDefault(c, 0) + 1);
        }
        Map<Character, Integer> sourceCharCountMap = new HashMap<>();
        int maxLength = Integer.MAX_VALUE;
        String ans = "";
        int sourceLength = s.length();
        for (int i = 0, j = 0; i < sourceLength; ++ i) {
            char c = s.charAt(i);
            if (!targetCharCountMap.containsKey(c)) {
                continue;
            }
            sourceCharCountMap.put(c, sourceCharCountMap.getOrDefault(c, 0) + 1);
            if (check(targetCharCountMap, sourceCharCountMap)) {
                if (maxLength > (i - j + 1)) {
                    maxLength = i - j + 1;
                    ans = s.substring(j, i + 1);
                }
                if (sourceCharCountMap.containsKey(s.charAt(j))) {
                    sourceCharCountMap.put(s.charAt(j), sourceCharCountMap.get(s.charAt(j)) - 1);
                }
                j ++;
                // 移动j指针
                while (true) {
                    while (j < i && !targetCharCountMap.containsKey(s.charAt(j))) {
                        j++;
                    }
                    if (!check(targetCharCountMap, sourceCharCountMap)) {
                        break;
                    }
                    if (maxLength > (i - j + 1)) {
                        maxLength = i - j + 1;
                        ans = s.substring(j, i + 1);
                    }
                    sourceCharCountMap.put(s.charAt(j), sourceCharCountMap.get(s.charAt(j)) - 1);
                    j ++;
                }
            }
        }
        return ans;
    }

    public static boolean check(Map<Character, Integer> targetCharCountMap, Map<Character, Integer> sourceCharCountMap) {
        for (Map.Entry<Character, Integer> entry: targetCharCountMap.entrySet()) {
            if (entry.getValue() > sourceCharCountMap.getOrDefault(entry.getKey(), 0)) {
                return false;
            }
        }
        return true;
    }


    public int[] maxSlidingWindow(int[] nums, int k) {
        // [l, r] 哪些信息是无效的
        // 1. i < j && nums[i] <= nums[j] 循环移除掉这种i j需要入队
        // 2. i < j && nums[i] > nums[j]的 直接入队j
        // 那么最终队列的情况 就是 i < j nums[i] > nums[j]
        // 注意 往后移动 需要维护队列头元素下标不能超过了，不然出队
        // 最大值取队列第一个元素
        ArrayDeque<Map.Entry<Integer, Integer>> entryQueue = new ArrayDeque<>();
        int len = nums.length;
        int[] ans = new int[len - k + 1];
        int index = 0;
        for (int i = 0; i < len; ++ i) {
            // 入队
            while (!entryQueue.isEmpty() && entryQueue.peekLast().getValue() <= nums[i]) {
                entryQueue.pollLast();
            }
            entryQueue.addLast(Map.entry(i, nums[i]));

            // 出头结点
            while(!entryQueue.isEmpty() && entryQueue.peekFirst().getKey() <= i - k) {
                entryQueue.pollFirst();
            }

            // 记录答案
            if (!entryQueue.isEmpty() && i >= k - 1) {
                ans[index] = entryQueue.peekFirst().getValue();
                index ++;
            }
        }
        return ans;
    }

    public static int subarraySum(int[] nums, int k) {
        // 如果sum[l, r] = k 那么[l - 1, r] 或者 [l, r + 1] 还是可能等于 k 所以没有 滑动窗口的一个性质
        // 前缀和 + hash
        Map<Integer, Integer> preSumCountMap = new HashMap<>();
        preSumCountMap.put(0, 1);
        int sum = 0;
        int rangeCount = 0;
        for (int num: nums) {
            sum += num;
            int target = sum - k;
            rangeCount += preSumCountMap.getOrDefault(target, 0);
            preSumCountMap.put(sum, preSumCountMap.getOrDefault(sum, 0) + 1);
        }
        return rangeCount;
    }

    public List<Integer> findAnagrams(String s, String p) {
        // p字符串需要记录 字符 对应 个数
        // 双指针遍历s [l, r] 就能组成p字符串 记录其中出现字符的个数
        // 如果 r - l + 1 = p.length && 计数过程没问题 记录l
        // 什么是计数过程中有问题？ 出现p字符串中没有的字符 || 当前字符的计数 > p对应字符的计数 l 往后移
        int[] targetCount = new int[26];
        int[] sourceCount = new int[26];
        int targetLength = p.length();
        int sourceLength = s.length();
        for (int i = 0; i < targetLength; ++ i) {
            targetCount[p.charAt(i) - 'a'] ++;
        }
        List<Integer> result = new ArrayList<>();
        for (int i = 0, j = 0; i < sourceLength; ++ i) {
            int charIndex = s.charAt(i) - 'a';
            sourceCount[charIndex] ++;
            while (j <= i && sourceCount[charIndex] > targetCount[charIndex]) {
                sourceCount[s.charAt(j) - 'a'] --;
                j ++;
            }
            if (i - j + 1 == targetLength) {
                result.add(j);
            }
        }
        return result;
    }

    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> charCountMap = new HashMap<>();
        int len = s.length();
        int result = 0;
        for (int i = 0, j = 0; i < len; ++ i) {
            char c = s.charAt(i);
            while (charCountMap.getOrDefault(c, 0) > 0) {
                charCountMap.remove(s.charAt(j));
                j ++;
            }
            charCountMap.put(c, 1);
            result = Math.max(result, i - j + 1);
        }
        return result;
    }

    static class Pair implements Comparable<Pair>{

        private int robotIndex;

        private int distance;

        @Override
        public int compareTo(Pair pair) {
            return robotIndex - pair.robotIndex;
        }

        public Pair(int robotIndex, int distance) {
            this.robotIndex = robotIndex;
            this.distance = distance;
        }

    }

    public static int maxWalls(int[] robots, int[] distance, int[] walls) {
        int len = robots.length;
        List<Pair> pairs = new ArrayList<>(len);
        for (int i = 0; i < len; ++ i) {
            pairs.add(new Pair(robots[i], distance[i]));
        }
        Collections.sort(pairs);
        Arrays.sort(walls);
        int[][] f = new int[len + 1][2];

        for (int i = 1; i <= len; ++ i) {
            if (i == 1) {
                //
                f[i][1] = caclSum(0, pairs, pairs.get(0).distance, walls, false, true);
                f[i][0] = caclSum(0, pairs, pairs.get(0).distance, walls, true, true);
            } else {
                f[i][1] = Math.max(f[i - 1][0], f[i - 1][1]) + caclSum(i - 1, pairs, pairs.get(i - 1).distance, walls, false, true);
                Pair pair = pairs.get(i - 1);
                Pair prePair = pairs.get(i - 2);
                if (pair.distance + prePair.distance >= pair.robotIndex - prePair.robotIndex) {
                    f[i][0] = Math.max(f[i - 1][0] + caclSum(i - 1, pairs, pair.distance, walls, true, true)
                            , Math.max(f[i][0], Math.max(f[i - 2][0], f[i - 2][1]) + caclSum(i - 2, pairs, pair.distance + prePair.distance, walls, false, false)));
                } else {
                    f[i][0] = Math.max(f[i - 1][1], f[i - 1][0]) + caclSum(i - 1, pairs, pair.distance, walls, true, true);
                }
            }
        }

        return Math.max(f[len][0], f[len][1]);
    }

    /**
     * 计算能射击多少个墙
     * @param index 下标为index的机器人
     * @param pairs 排序后的机器人列表
     * @param distance 需要移动的距离
     * @param walls 排序后的墙列表
     * @param left true:往左 false:往右 射击
     * @param excludeNextIndex 射击过程中是否包含下一个机器人
     * @return 最多射击到多少个墙
     */
    public static int caclSum(int index, List<Pair> pairs, int distance, int[] walls, boolean left, boolean excludeNextIndex) {
        int l, r;
        if (left) {
            r = pairs.get(index).robotIndex;
            l = r - distance;
            if (index - 1 >= 0) {
                l = Math.max(l, pairs.get(index - 1).robotIndex + (excludeNextIndex ? 1 : 0));
            }
        } else {
            l = pairs.get(index).robotIndex;
            r = l + distance;
            if (index + 1 < pairs.size()) {
                r = Math.min(r, pairs.get(index + 1).robotIndex - (excludeNextIndex ? 1 : 0));
            }
        }

        int wallLIndex, wallRIndex;

        // 找到大于等于l的第一个下标
        int wallL = 0, wallR = walls.length - 1;
        while (wallL < wallR) {
            int mid = (wallL + wallR) >> 1;
            if (walls[mid] >= l) {
                wallR = mid;
            } else {
                wallL = mid + 1;
            }
        }

        wallLIndex = wallL;

        // 找到小于等于R的第一个下标
        wallL = 0;
        wallR = walls.length - 1;
        while (wallL < wallR) {
            int mid = (wallL + wallR + 1) >> 1;
            if (walls[mid] <= r) {
                wallL = mid;
            } else {
                wallR = mid - 1;
            }
        }

        wallRIndex = wallL;

        if (wallLIndex <= wallRIndex && walls[wallLIndex] >= l && walls[wallRIndex] <= r) {
            return wallRIndex - wallLIndex + 1;
        }

        return 0;
    }

    public static int[] maxValue(int[] nums) {
        // 左边最大值边走变维护
        // 右边最大值 找到所有nums[x] > nums[y] && y > x，再找到y左边的最大值 取max
        // 指针往左走，如果slefValue 小于前元素 且 leftMaxValue 大于 前元素 就删除前元素
        // 1-8 3-10 4-14 5-12 5-12直接不要 主要是一个/不插入、多个删除
        int len = nums.length;
        int[] leftMaxValues = new int[len];
        int maxValue = 0;
        for (int i = 0; i < len; ++ i) {
            maxValue = Math.max(maxValue, nums[i]);
            leftMaxValues[i] = maxValue;
        }
        int[] result = new int[len];
        TreeMap<Integer, Integer> map = new TreeMap<>();
        // 初始化
        result[len - 1] = leftMaxValues[len - 1];
        map.put(nums[len - 1], leftMaxValues[len - 1]);
        for (int i = len - 2; i >= 0; -- i) {
            // 获取右边最大值
            Map.Entry<Integer, Integer> entry = map.lowerEntry(leftMaxValues[i]);
            if (entry != null) {
                result[i] = Math.max(entry.getValue(), leftMaxValues[i]);
            } else {
                result[i] = leftMaxValues[i];
            }
            // 维护pairList
            Integer old = map.get(nums[i]);
            if (old == null || old < result[i]) {
                map.put(nums[i], result[i]);
                // 删除所有 "比nums[i]大，且 value <= res" 的节点
                while (true) {
                    Map.Entry<Integer, Integer> higher = map.higherEntry(nums[i]);
                    if (higher != null && higher.getValue() <= result[i]) {
                        map.remove(higher.getKey());
                    } else break;
                }
            }

        }
        return result;
    }
//
//    public static Pair buildPair(int self, int leftMaxValue) {
//        Pair pair = new Pair();
//        pair.selfValue = self;
//        pair.leftMaxValue = leftMaxValue;
//        return pair;
//    }

    public boolean partitionArray(int[] nums, int k) {
        int len = nums.length;
        if (len % k != 0) {
            return false;
        }
        Arrays.sort(nums);
        int countLimit = len / k;
        Map<Integer, Integer> numCountMap = new HashMap<>();
        for (int num: nums) {
            numCountMap.put(num, numCountMap.getOrDefault(num, 0) + 1);
        }
        return numCountMap.values().stream().noneMatch(numCount -> numCount > countLimit);
    }

    public int gcd(int a, int b) {
        return b != 0 ? gcd(b, a % b) : a;
    }

    public int gcdOfOddEvenSums(int n) {
        int sum1 = 0;
        int sum2 = 0;
        for (int i = 1; i <= n; ++ i) {
            if (i % 2 == 0) {
                sum2 += i;
            } else {
                sum1 += i;
            }
        }
        return gcd(sum1, sum2);
    }

}
