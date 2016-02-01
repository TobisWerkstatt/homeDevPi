/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jhomedevpi.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import jhomedevpi.s0.S0Device;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/**
 *
 * @author Tobias
 */
public class ReportGenerator {
    private final List<S0Device> s0Devices = new ArrayList<>();
    private static final ReportGenerator singleton = new ReportGenerator();
    
    public static ReportGenerator getInstance(){
        return singleton;
    }
    
    public void createS0Report(){
        TimeSeriesCollection coll = new TimeSeriesCollection();
        Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.DATE, 0);
        for (S0Device device: s0Devices){
            TimeSeries s = device.getData(new Date(cal.getTimeInMillis()));
            if(s!=null){
                coll.addSeries(s);
            }
        }
        XYDotRenderer renderer = new XYDotRenderer();
        renderer.setDotHeight(5);
        renderer.setDotWidth(5);
        DateAxis xax = new DateAxis("x");
        NumberAxis yax = new NumberAxis("y");
         
        XYPlot plot = new XYPlot(coll, xax, yax, renderer);
        JFreeChart chart = new JFreeChart(plot);
        BufferedImage im = chart.createBufferedImage(800, 600);
        File f = new File("image.png");
        try {
            ImageIO.write(im, "png", f);
        } catch (IOException ex) {
            Logger.getLogger(ReportGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addS0Device(S0Device ...devices){
        s0Devices.addAll(Arrays.asList(devices));
    }
}
