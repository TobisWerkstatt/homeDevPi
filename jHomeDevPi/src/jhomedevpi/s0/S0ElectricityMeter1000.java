/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jhomedevpi.s0;

/**
 *
 * @author Tobias
 */
public class S0ElectricityMeter1000 extends S0Device{
    public S0ElectricityMeter1000(int counts, int channel, String name){
        super("kWh", counts, channel, name);
    }
    
    public S0ElectricityMeter1000(int channel, String name){
        super("kWh", 0, channel, name);
    }

    @Override
    public double getPhysicalValue() {
        return getCounts()/1000.0;
    }
}
