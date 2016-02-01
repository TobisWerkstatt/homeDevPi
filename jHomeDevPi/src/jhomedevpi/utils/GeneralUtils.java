/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jhomedevpi.utils;

import java.util.Date;
import java.util.TimeZone;
import org.w3c.dom.ranges.RangeException;

/**
 *
 * @author Tobias
 */
public class GeneralUtils {
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
    public static boolean getBit(int theByte, int theBit) throws RangeException{
        byte mask = (byte)(0x01 << theBit);
        return (theByte & mask) != 0;
    }
    
    public static long getSecondsSinceMidnight(){
        long time = 0;
        if(!TimeZone.getDefault().inDaylightTime(new Date())){
            time = 60*60*1000;
        }
        time = time+System.currentTimeMillis()%(24*60*60*1000);
        return time;
    }
}
