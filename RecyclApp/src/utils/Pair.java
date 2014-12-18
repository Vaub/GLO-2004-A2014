/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.Serializable;

/**
 *
 * @author Vincent
 */
public class Pair<X, Y> implements Serializable
{
    public X x;
    public Y y;
    
    public Pair(X x, Y y)
    {
        this.x = x;
        this.y = y;
    }
}
