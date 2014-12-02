/*------------------------------------------------
 *------------------------------------------------
 * ||id     		:   112 0821 042
 * |name   		:   Alim Ul Karim
 * |email  		:   alim.karim.nsu@gmail.com
 * |course 		:   CSE 338 Networking
 * |blog   		:   http://bit.ly/auk-blog
 * |linkedin            :   http://linkd.in/alim-ul-karim
 *------------------------------------------------
 *------------------------------------------------
 */
package Database.Components;


import javax.swing.JComboBox;

/**
 *
 * @author Alim
 */
public class ArrayMore {

    public void readShow(Object arr[]) {
        for (Object s : arr) {
            System.out.println(s);
        }
    }

    public void readShow(int[] arr) {
        for (int s : arr) {
            System.out.println(s);
        }
    }

    public void arrayToCombo(JComboBox c, Object arr[]) {
        int count = arr.length;
        for (int i = 0; i < count; i++) {
            c.addItem(arr[i]);
        }
    }

    /**
     * 
     * @param arr
     * @param item
     * @return int: if not found return -1
     */
    public int search(Object arr[], Object item) {
        int i = 0;
        if(arr==null) return -1;
        if(item==null) return -1;
        for (Object o : arr) {
            if (o.equals(item)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * 
     * @param arr
     * @param item
     * @return int: if not found return -1
     */
    public int search(int arr[], int item) {
        int i = 0;
        for (int o : arr) {
            if (o == item) {
                return i;
            }
            i++;
        }
        return -1;
    }
    /**
     * 
     * @param arr
     * @param removeIndexes: remove those indexes from an array
     * @return 
     */
    public String[] remove(String arr[], int removeIndexes[]) {
        if (arr == null) {
            return new String[0];
        }
        if (removeIndexes.length > arr.length) {
            System.out.println("Remove len is greater than array len.");
            return new String[0];
        }
        String narr[] = new String[arr.length - removeIndexes.length]; // new array 
        int row = 0, row2 = 0;
        for (String s : arr) {
            if (search(removeIndexes, row) == -1) //not exist in the list
            {
                narr[row2++] = arr[row];
            }
            row++;
        }

        return narr;
    }

    public void comboSelectNextIndex(JComboBox c) {
        int count = c.getItemCount() - 1;
        if (count == -1) {
            return;
        }
        int sel = c.getSelectedIndex();
        if (sel + 1 <= count) {
            c.setSelectedIndex(sel + 1);
        } else {
            c.setSelectedIndex(0);
        }
    }

    public void comboSelectPreviousIndex(JComboBox c) {

        int count = c.getItemCount() - 1;
        if (count == -1) {
            return;
        }
        int sel = c.getSelectedIndex();
        if (sel - 1 >= 0) {
            c.setSelectedIndex(sel - 1);
        } else {
            c.setSelectedIndex(count);
        }
    }
//    public static void main(String args[]) {
//        ArrayMore arrtest = new ArrayMore();
//        String s[] = {"Dello", "mme", "jdwgh", "wdwq"};
//        int k[] = {0, 2};
//        s = arrtest.remove(s, k);
//        arrtest.readShow(s);
//    }
}
