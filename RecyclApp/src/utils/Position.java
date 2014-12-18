/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author Vincent
 */
public class Position implements Serializable
{
    private double x;
    private double y;
    
    public Position(double _x, double _y)
    {
        x = (_x < 0) ? 0 : _x;
        y = (_y < 0) ? 0 : _y;
    }
    
    public Position(Position _copie)
    {
        x = _copie.x;
        y = _copie.y;
    }
    
    public double x()
    {
        return x;
    }
    
    public double y()
    {
        return y;
    }
    
    public void setX(double _x)
    {
        x = (_x < 0) ? 0 : _x;
    }
    
    public void setY(double _y)    
    {
        y = (_y < 0) ? 0 : _y;
    }
    
    public Point point(double _pixelsParMetre)
    {
        _pixelsParMetre = (_pixelsParMetre < 0) ? 0 
                                                  : _pixelsParMetre;
        
        return new Point(Math.round((float)(x * _pixelsParMetre)), Math.round((float)(y * _pixelsParMetre)));
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean estEgal = false;
        
        if (obj instanceof Position)
        {
            Position pos2 = (Position)obj;
            estEgal = (this.x == pos2.x) && (this.y == pos2.y);
        }
        
        return estEgal;
    }
    
    @Override
    public String toString()
    {
        return String.format("[%f, %f]", x, y);
    }
    
    public static Position fromPoint(Point _p, double _pixelsParMetre)
    {
        return new Position(_p.x / _pixelsParMetre, _p.y / _pixelsParMetre);
    }
}
