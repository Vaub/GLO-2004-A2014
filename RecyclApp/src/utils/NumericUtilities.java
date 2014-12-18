/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Vincent
 */
public class NumericUtilities
{
    public static float roundFloat(float _value, int _numberOfDecimals)
    {
        BigDecimal bd = new BigDecimal(Float.toString(_value));
        bd = bd.setScale(_numberOfDecimals, BigDecimal.ROUND_HALF_UP);
        
        return bd.floatValue();
    }
    
    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
