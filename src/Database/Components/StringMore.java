/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.Components;

/**
 *
 * @author Alim
 */
public class StringMore {

    public String joinStringArray(String arr[], String joiner) {
        String str = "";
        for (String s : arr) {
            if (str.equals("") == false) {
                str += joiner;
            }
            str += s;
        }

        return str;
    }

    public String joinCharArray(char arr[], String joiner) {
        String str = "";
        for (char s : arr) {
            if (str.equals("") == false) {
                str += joiner;
            }
            str += s;
        }
        return str;
    }

    public String EachWordUpperCase(String str) {
        if (str.equals("") || str == null) {
            return "";
        }
        String words[] = str.split(" ");
        String word;
        int count = words.length;
        for (int i = 0; i < count; i++) {
            word = words[i];
            char[] wordChars = word.toCharArray();
            wordChars[0] = Character.toUpperCase(wordChars[0]);
            words[i] = new String(wordChars);
        }
        word = joinStringArray(words, " ");
        return word;
    }

    public String chatAtUpperCase(String str, int index) {
        char words[] = str.toCharArray();
        String word;
        int count = words.length;
        words[index] = Character.toUpperCase(words[index]);
        word = joinCharArray(words, "");
        return word;
    }

    public String chatAtLowerCase(String str, int index) {
        char words[] = str.toCharArray();
        String word;
        int count = words.length;
        words[index] = Character.toLowerCase(words[index]);
        word = joinCharArray(words, "");
        return word;
    }
//
//    public static void main(String args[]) {
//        StringMore smore = new StringMore();
//        System.out.println(smore.EachWordUpperCase("hello world may name is we earl."));
//        System.out.println(smore.chatAtUpperCase("dw qfW d",3));
//    }
}
