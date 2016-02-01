/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jhomedevpi.hardware;

/**
 *
 * @author Tobias
 */
public class HardwareConnector {

    private PCF8574Connector p;

    private static HardwareConnector h = null;

    public static HardwareConnector getInstance() {
        if (h == null) {
            h = new HardwareConnector();
        }
        return h;
    }

    private HardwareConnector() {
        p = new PCF8574Connector();
    }

    public PCF8574Connector getPCF8574() {
        return p;
    }
}
