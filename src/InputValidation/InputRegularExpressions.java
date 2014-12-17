/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InputValidation;

/**
 *
 * @author Alim
 */
public final class InputRegularExpressions {
    
    public static final String USERNAME = "\\w+";
    
    //<editor-fold defaultstate="collapsed" desc="Email regular expression by RFC 5322">
    //http://stackoverflow.com/questions/16295329/email-address-validation-fails-when-domain-name-has-a-hyphen
    
    public static final String EMAIL = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    //</editor-fold>
    
    public static final String NUMBER = "\\d+";
    
    public static final String CHARACTERS_ONLY = "[aA-zZ]";
    
    public static final String SQL_SEARCH_VALID = "[^'`\\[\\];\\\"\\\\|&?$\\^\\(\\)%]+";
    
    
}
