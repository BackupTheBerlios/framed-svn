package net.sf.tacos.demo.partial;

import net.sf.tacos.ajax.AjaxDirectService;
import net.sf.tacos.ajax.components.ProgressBar;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry.IDirect;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.html.BasePage;

/**
 * Demonstrates use of {@link ProgressBar} component to render
 * progress of threaded work.
 * 
 * @author Jesse Kuhnert
 */
public abstract class ProgressCounter extends BasePage implements IDirect {
    
    /** Logger */
    private static final Log log = LogFactory.getLog(ProgressCounter.class);
    
    /** Injected ajax engine */
    public abstract AjaxDirectService getAjaxEngineService();
    /** Worker doing import */
    public abstract ProgressWorkThread getProgressWorker();
    /** sets worker doing import */
    public abstract void setProgressWorker(ProgressWorkThread worker);
    /** Set time - in milliseconds - that worker started */
    public abstract void setStartTime(long time);
    /** Gets start time */
    public abstract long getStartTime();
    
    /**
     * 
     * @return True if currently importing a casebase file.
     */
    public boolean isImporting()
    {
        return getProgressWorker() != null && !getProgressWorker().isComplete();
    }
    
    /**
     * Calculates amount of time left, in minutes, for task.
     * @return
     */
    public String getEstimatedTimeLeft()
    {
        if (getProgressWorker() == null)
            return "0 min";
        
        //Get values so they don't change on us
        double currentProgress = getProgressWorker().getCurrentProgress();
        double totalProgress = getProgressWorker().getTotalProgress();
        
        double avgDuration = 
            (System.currentTimeMillis() - getStartTime()) / currentProgress;
        double remainingDuration = 
            (totalProgress - getProgressWorker().getCurrentProgress()) * avgDuration;
        return DurationFormatUtils
        .formatDurationHMS(Math.round(remainingDuration));
    }
    
    /**
     * {@inheritDoc}
     */
    public void trigger(IRequestCycle cycle)
    {
        
    }
    
    /**
     * Starts the progress task.
     * @param cycle
     * @throws Exception
     */
    public void startTask(IRequestCycle cycle) 
    throws Exception
    {
        if (isImporting())
            return;
        log.debug("startTask");
        
        setProgressWorker(null);
        
        //Start task
        ProgressWorkThread worker = new ProgressWorkThread();
        setProgressWorker(worker);
        worker.start();
        setStartTime(System.currentTimeMillis());
    }
}
