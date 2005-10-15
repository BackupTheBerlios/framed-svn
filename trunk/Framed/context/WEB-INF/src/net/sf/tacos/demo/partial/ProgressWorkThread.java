package net.sf.tacos.demo.partial;

import java.io.Serializable;

import net.sf.tacos.ajax.components.ProgressWorker;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Demonstration of {@link ProgressWorker}.
 * 
 * @author jkuhnert
 */
public class ProgressWorkThread extends Thread implements ProgressWorker, Serializable {
    
    /** serialuid */
    private static final long serialVersionUID = 4915291816082490796L;
    /** logger */
    private static final Log log = LogFactory.getLog(ProgressWorkThread.class);
    
    /** Total progress we are working on */
    private static final double TOTAL = 100;
    
    /** Current progress */
    private double progress = 0;
    
    /* Flag for if we are running or not */
    private boolean running = true;
    
    /** Default constructor */
    public ProgressWorkThread()
    {
    }
    
    /**
     * {@inheritDoc}
     */
    public double getTotalProgress() {
        return TOTAL;
    }
    
    /**
     * {@inheritDoc}
     */
    public double getCurrentProgress() {
        return progress;
    }

    /**
     * {@inheritDoc}
     */
    public String getCurrentStatus() {
        return "Processing count of " + progress;
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean isComplete() {
        return !running;
    }
    
    /**
     * {@inheritDoc}
     */
    public void cancelTask() {
        if (!running)
            return;
        
        progress = TOTAL;
        running = false;
        try { this.interrupt(); } catch (Exception et) { }
    }
    
    /**
     * {@inheritDoc}
     */
    public void run() {
        while (progress < TOTAL) {
            log.debug("progressWorker() " + progress);
            progress += 1;
            try { sleep(100); } catch (Exception e) { }
        }
        running = false;
    }
}
