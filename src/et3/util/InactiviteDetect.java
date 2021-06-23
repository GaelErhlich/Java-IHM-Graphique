package et3.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class InactiviteDetect {

    private Runnable runnable;
    private int inactiviteTemps;

    private ScheduledFuture schedule;


    public InactiviteDetect(Runnable runnable, int inactiviteTempsMs) {
        this.runnable = runnable;
        this.inactiviteTemps = inactiviteTempsMs;
    }


    public void ping() {
        if(schedule != null) {
            schedule.cancel(false);
        }

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        schedule = service.schedule(runnable, inactiviteTemps, TimeUnit.MILLISECONDS);
    }

    public void ping(Runnable runnable) {
        this.runnable = runnable;
        ping();
    }

}
