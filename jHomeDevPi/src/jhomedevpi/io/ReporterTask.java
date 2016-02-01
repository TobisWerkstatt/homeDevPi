/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jhomedevpi.io;

import java.util.TimerTask;

/**
 *
 * @author Tobias
 */
public class ReporterTask extends TimerTask{

    @Override
    public void run() {
        ReportGenerator.getInstance().createS0Report();
    }
    
}
