/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jhomedevpi.s0;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.ranges.RangeException;

/**
 *
 * @author Tobias
 */
public class s0CounterTest {
    
    public s0CounterTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getBit method, of class s0Counter.
     */
    @Test
    public void testGetBit() {
        System.out.println("\ngetBit");
        testGetBit((byte)0xFF, (short)1, true);
        testGetBit((byte)0x00, (short)1, false);
        testGetBit((byte)0xFA, (short)5, true);
        testGetBit((byte)0xFA, (short)2, false);
        System.out.println("done");
    }
    
    private void testGetBit(byte theByte, short theBit, boolean expResult){
        System.out.print("Test Case: Byte = " + String.format("%02X", theByte) +
                " and Bit = " + String.valueOf(theBit) + " expected Result = " +
                String.valueOf(expResult) + ": ");
        
        s0Counter instance = new s0Counter();
        boolean result = instance.getBit(theByte, theBit);
        
        if(result == expResult){
            System.out.println("check");
        }else{
            System.out.println("failed");
        }
        
        assertEquals(expResult, result);  
    }
    
    @Test(expected = RangeException.class)
    public void testGetBitExcept1() {
        System.out.println("\ngetBit - Exception");
        testGetBitExcept((byte)0xFF, (short)-1);
    }
    
    @Test(expected = RangeException.class)
    public void testGetBitExcept2() {
        System.out.println("\ngetBit - Exception");
        testGetBitExcept((byte)0xFF, (short)8);
    }
    
    private void testGetBitExcept(byte theByte, short theBit){
        System.out.println("Test Case: Byte = " + String.format("%02X", theByte) +
                " and Bit = " + String.valueOf(theBit) + " expected Result = Error");
        
        s0Counter instance = new s0Counter();
        boolean result = instance.getBit(theByte, theBit); 
    }
    
}
