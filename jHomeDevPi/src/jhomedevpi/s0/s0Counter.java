/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jhomedevpi.s0;

import org.w3c.dom.ranges.RangeException;

/**
 *
 * @author Tobias
 */
public class s0Counter {
    public s0Counter(){
        
    }
    
    /**
     * Gibt den Wert eines bestimmten Bits in einem Byte zurück. <br>Z.B.:<br>
     * Bit 5 in 0xFA:   0xFA = 11<u>1</u>11010<br>
     * Ergebnis wäre true
     * 
     * @param theByte
     * @param theBit
     * @return 
     * @throws RangeException wenn Bit nicht zwischen 0 und 7
     */
    public boolean getBit(byte theByte, short theBit) throws RangeException{
        if(theBit<0){
            throw new RangeException(theBit, "Bit nicht zulässig");
        }
        if(theBit>7){
            throw new RangeException(theBit, "Bit nicht zulässig");
        }
        byte mask = (byte)(0x01 << theBit);
        return (theByte & mask) != 0;
    }
}
