package com.zjf.toolkit.algorithm;

/**
 * 
* @ClassName: BinarySearchAlgorithm 
* @Description:  二分查找
* @author 张建峰 
* @date 2019年5月25日 下午5:41:47 
*
 */
public class BinarySearchAlgorithm {
	
	/**
	 * 二分查找，找到该值在数组中的下标，否则为-1
	 */
	static int binarySerach(int[] array, int key) {
	    int left = 0;
	    int right = array.length - 1;

	    // 这里必须是 <=
	    while (left <= right) {
	        int mid = (left + right) / 2;
	        if (array[mid] == key) {
	            return mid;
	        }
	        else if (array[mid] < key) {
	            left = mid + 1;
	        }
	        else {
	            right = mid - 1;
	        }
	    }

	    return -1;
	}
	
}
