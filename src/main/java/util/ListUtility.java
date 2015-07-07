package util;

import java.util.Collections;
import java.util.List;

/**
 * @author e1054909
 *
 */
public class ListUtility {
	
	/**
	 * This quicksort method takes x amount of lists to be used as mirrors. <br>
	 * Each swap done to the main compared list will be done to each of the mirror lists.
	 * @param list, list to be sorted
	 * @param lowIndex, lower index of sublist to swap
	 * @param highIndex, upper index of sublist to swap
	 * @param mirrors, each swap done to list will be done to these
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void quickSort(List<? extends Comparable> list, int lowIndex, int highIndex, List<?>... mirrors) {
		
		int i = lowIndex;
		int j = highIndex;
		Comparable pivot = list.get(lowIndex + ((highIndex - lowIndex) / 2));
		
		while (i <= j) {
			
			while (list.get(i).compareTo(pivot) == 1) {
				i++;
			}
			
			while (list.get(j).compareTo(pivot) == -1) {
				j--;
			}
			
			if (i <= j) {
				
				Collections.swap(list, i, j);
				
				for (List<?> mirror : mirrors) {
					Collections.swap(mirror, i, j);
				}
				
				i++;
				j--;
			}
		}
		
		if (lowIndex < j) {
			quickSort(list, lowIndex, j, mirrors);
		}
		
		if (i < highIndex) {
			quickSort(list, i, highIndex, mirrors);
		}
	}
}
