/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jhomedevpi.s0;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import jhomedevpi.utils.GeneralUtils;

/**
 *
 * @author Tobias
 */
public class S0Manager {
    private final List<S0Device> deviceList = new ArrayList<>();
    private boolean changed = false;
    
    private final PropertyChangeListener l = (PropertyChangeEvent evt) -> {
        if(evt.getPropertyName().equals("PROP_COUNTS")){
            changed = true;
        }
    };
    
    public void addDevice(S0Device device){
        deviceList.add(device);
    }
    
    public void detectChange(byte state){
        for(int i = 0; i < 8; i++){
            if(GeneralUtils.getBit(state, i)){
                for(S0Device device: deviceList){
                    if(device.getChannel() == i){
                        device.detectCount();
                    }
                }
            }
        }
    }

    public boolean hasChanged() {
        return changed;
    }
}
