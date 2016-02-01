/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jhomedevpi.hardware;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tobias
 */
public class PCF8574Connector {
    public static final String PROP_STATE = "PROP_STATE";

    private int state;
    private final GpioController gpio = GpioFactory.getInstance();
    private final GpioPinDigitalInput interruptPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_21, PinPullResistance.PULL_UP);
    private I2CBus bus;
    private I2CDevice pcf8574;
    private final transient PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);

    public PCF8574Connector() {
        try {
            bus = I2CFactory.getInstance(I2CBus.BUS_1);
            pcf8574 = bus.getDevice(0x38);
        } catch (IOException ex) {
            Logger.getLogger(PCF8574Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
        interruptPin.addListener((GpioPinListenerDigital) (GpioPinDigitalStateChangeEvent event) -> {
            if (event.getState().isLow()) {
                try {
                    setState(pcf8574.read());
                } catch (IOException ex) {
                    Logger.getLogger(PCF8574Connector.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public int getState() {
        return state;
    }

    private void setState(int state) {
        int oldState = this.state;
        this.state = state;
        propertyChangeSupport.firePropertyChange(PROP_STATE, oldState, state);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener l, String property){
        propertyChangeSupport.addPropertyChangeListener(property, l);
    }
}
