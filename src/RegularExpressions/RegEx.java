/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RegularExpressions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Alim
 */
public class RegEx {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    private Pattern pattern;
    private Matcher matcher;

    private String lastExecutedPattern;
    private boolean isPatternChange = true;
    //</editor-fold>

//    public RegEx() {
//
//    }
    
    /**
     * 
     * @param regularExpression : if null then returns false.
     * @param input
     * @return 
     */
    public boolean validate(String regularExpression, String input) {
        if (regularExpression != null) {
            isPatternChange = !regularExpression.equals(lastExecutedPattern);

            if (isPatternChange) {
                pattern = Pattern.compile(regularExpression);
                lastExecutedPattern = regularExpression;

            }
            matcher = pattern.matcher(input);
            return matcher.matches();
        }
        return false;

    }
}
