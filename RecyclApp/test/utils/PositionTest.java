/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Point;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vincent
 */
public class PositionTest
{
    static float DELTA = 1e-5f;
    
    Position p;
    
    public PositionTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
        p = new Position(40.5, 30.25);
    }
    
    @After
    public void tearDown()
    {
    }

    /**
     * Test of x method, of class Position.
     */
    @Test
    public void testX()
    {
        assertEquals(40.5f, p.x(), DELTA);
    }

    /**
     * Test of y method, of class Position.
     */
    @Test
    public void testY()
    {
        assertEquals(30.25f, p.y(), DELTA);
    }

    /**
     * Test of setX method, of class Position.
     */
    @Test
    public void testSetX()
    {
        Position posTest = new Position(p);
        
        posTest.setX((15/3));
        assertEquals((15/3), posTest.x(), DELTA);
        
        posTest.setX(-15);
        assertEquals(0f, posTest.x(), DELTA);
    }

    /**
     * Test of setY method, of class Position.
     */
    @Test
    public void testSetY()
    {
        Position posTest = new Position(p);
        
        posTest.setY((15/3));
        assertEquals((15/3), posTest.y(), DELTA);
        
        posTest.setY(-15);
        assertEquals(0f, posTest.y(), DELTA);
    }

    /**
     * Test of point method, of class Position.
     */
    @Test
    public void testPoint()
    {
        assertEquals(new Point(162,121), p.point(4));
        assertEquals(new Point(0,0), p.point(0));
    }
    
}
