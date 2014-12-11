/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InputValidation;

import RegularExpressions.RegEx;

/**
 *
 * @author Alim
 */
public class Validate {
    //<editor-fold defaultstate="collapsed" desc="Fields">    

    static final RegEx _UsernameValidator = new RegEx(InputRegularExpressions.USERNAME);
    static final RegEx _EmailValidator = new RegEx(InputRegularExpressions.EMAIL);
    static final RegEx _NumberValidator = new RegEx(InputRegularExpressions.NUMBER);
    static final RegEx _CharactersValidator = new RegEx(InputRegularExpressions.CHARACTERS_ONLY);
//</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Min , Max Constrains Logic">

    /**
     *
     * @param input
     * @param min : -1 means no checking, returns true
     * @param max : -1 means no checking, returns true
     * @param nullAllow
     * @return true if current string matches min , max constrains
     */
    public static boolean minMaxCheck(String input, int min, int max, boolean nullAllow) {

        if (nullAllow == false && input != null) {

            int len = input.length();
            if (min == -1) {
                min = len;
            }

            if (max == -1) {
                max = len;
            }
            if (len >= min && len <= max) {
                return true;
            }
        }
        return false;
    }

// </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Validation Logics: Username, Email , number, characters only">
    /**
     *
     * @param input
     * @param min : -1 means no checking, returns true
     * @param max : -1 means no checking, returns true
     * @param nullAllow
     * @return
     */
    public static boolean userName(String input, int min, int max, boolean nullAllow) {

        return minMaxCheck(input, min, max, nullAllow) && _UsernameValidator.validate(input);
    }

    /**
     *
     * @param input
     * @param min : -1 means no checking, returns true
     * @param max : -1 means no checking, returns true
     * @param nullAllow
     * @return
     */
    public static boolean email(String input, int min, int max, boolean nullAllow) {
        return minMaxCheck(input, min, max, nullAllow) && _EmailValidator.validate(input);
    }

    /**
     *
     * @param input
     * @param min : -1 means no checking, returns true
     * @param max : -1 means no checking, returns true
     * @param nullAllow
     * @return
     */
    public static boolean number(String input, int min, int max, boolean nullAllow) {
        return minMaxCheck(input, min, max, nullAllow) && _NumberValidator.validate(input);
    }

    /**
     *
     * @param input
     * @param min : -1 means no checking, returns true
     * @param max : -1 means no checking, returns true
     * @param nullAllow
     * @return
     */
    public static boolean charactersOnly(String input, int min, int max, boolean nullAllow) {
        return minMaxCheck(input, min, max, nullAllow) && _CharactersValidator.validate(input);
    }
//</editor-fold>

}
