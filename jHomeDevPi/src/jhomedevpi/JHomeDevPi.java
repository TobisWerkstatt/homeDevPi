/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jhomedevpi;

import java.util.Timer;
import jhomedevpi.hardware.HardwareConnector;
import jhomedevpi.io.ReportGenerator;
import jhomedevpi.io.ReporterTask;
import jhomedevpi.s0.S0Device;
import jhomedevpi.s0.S0ElectricityMeter2000;
import jhomedevpi.s0.S0Manager;

/**
 *
 * @author Tobias
 */
public class JHomeDevPi {

    public static void main(String[] args) throws InterruptedException {
        HardwareConnector h = HardwareConnector.getInstance();
        S0Manager m = new S0Manager();
        S0Device d0 = new S0ElectricityMeter2000(0, "Kühltrue");
        S0Device d1 = new S0ElectricityMeter2000(1, "Carport");
        m.addDevice(d0);
        m.addDevice(d1);
        ReportGenerator.getInstance().addS0Device(d0, d1);
        
        Timer t = new Timer();
        ReporterTask r = new ReporterTask();
        t.schedule(r, 0, 5*60*1000);
        
        while (true) {
            Thread.sleep(500);
        }
    }

}
