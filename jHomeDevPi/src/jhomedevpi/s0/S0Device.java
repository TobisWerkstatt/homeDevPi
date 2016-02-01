/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jhomedevpi.s0;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import jhomedevpi.hardware.HardwareConnector;
import jhomedevpi.utils.GeneralUtils;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;

/**
 *
 * @author Tobias
 */
public abstract class S0Device{
    public static final String PROP_COUNTS = "PROP_COUNTS";
    
    private final String unit;
    private int counts;
    private int channel;
    private final String name;

    DecimalFormat df = new DecimalFormat("#0.0000");

    private PropertyChangeListener l = (PropertyChangeEvent evt) -> {
        if (!GeneralUtils.getBit((int)evt.getNewValue(), channel)) {
            detectCount();
            System.out.println("Detected on Device " + String.valueOf(channel));
        }
    };
    private final transient PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);

    public S0Device(String unit, int counts, int channel, String name) {
        this.unit = unit;
        this.counts = counts;
        this.channel = channel;
        this.name = name;
        HardwareConnector.getInstance().getPCF8574().addPropertyChangeListener(l, "PROP_STATE");
    }

    public void reset() {
        setCounts(0);
    }

    public abstract double getPhysicalValue();

    public void detectCount() {
        setCounts(getCounts() + 1);
        store();
    }

    public int getChannel() {
        return channel;
    }

    public void store() {
        String filename = getStorageFilename(new Date());
        File f = new File(filename);
        FileWriter writer = null;

        try {
            boolean writeHeader = !f.exists();
            writer = new FileWriter(f, true);
            if (writeHeader) {
                writer.append("Time\tEnergie");
                writer.append(System.getProperty("line.separator"));
                writer.append("ms\t" + unit);
                writer.append(System.getProperty("line.separator"));
            }
            // Zeit seit 0 
            writer.append(String.valueOf(GeneralUtils.getSecondsSinceMidnight()) + "\t" + df.format(getPhysicalValue()));
            writer.append(System.getProperty("line.separator"));
        } catch (IOException ex) {
            Logger.getLogger(S0Device.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(S0Device.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public String getUnit() {
        return unit;
    }

    private String getStorageFilename(Date theDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String sDate = formatter.format(theDate);
        return "s0_" + sDate + "_" + String.valueOf(channel + ".dat");
    }
    
    public TimeSeries getData(Date theDate){
        String filename = getStorageFilename(theDate);
        File f = new File(filename);
        TimeSeries s = null;
        BufferedReader reader = null;
        if(!f.exists()){
            return null;
        }
        
        try{
            reader = new BufferedReader(new FileReader(f));
            // Header überspringen
            reader.readLine();
            reader.readLine();
            s = new TimeSeries("S0_"+String.valueOf(channel)+"_"+getName());
            String line;
            while((line = reader.readLine()) != null){
                int pos = line.indexOf("\t"); 
                Date time = new Date(Integer.parseInt(line.substring(0, pos)));
                double value = Double.parseDouble(line.substring(pos+1).replace(',', '.'));
                s.add(new Millisecond(time), value);
            }
        }catch(IOException ex){
            Logger.getLogger(S0Device.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(S0Device.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return s;
    }

    public String getName() {
        return name;
    }

    public int getCounts() {
        return counts;
    }

    private void setCounts(int counts) {
        int oldCounts = this.counts;
        this.counts = counts;
        propertyChangeSupport.firePropertyChange(PROP_COUNTS, oldCounts, counts);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener l, String property){
        propertyChangeSupport.addPropertyChangeListener(property, l);
    }
}
