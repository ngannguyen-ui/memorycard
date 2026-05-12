package Logic;

import javax.swing.Timer;

public class TimeManager{
    private long startTime;
    private long endTime;
    private long totalTime;
    private boolean isRunning;
    private Timer clockTimer;
    private Runnable onTick;
    public TimeManager(){
        this.startTime=0;
        this.totalTime=0;
        this.endTime=0;
        this.isRunning=false;
    }

    public void setOnTick(Runnable onTick){
        this.onTick = onTick;
    }

    public void start(){
        if(!isRunning){
            this.startTime=System.currentTimeMillis();
            this.isRunning=true;
            clockTimer = new Timer(1000, e -> {
                if (onTick != null) onTick.run();
            });
            clockTimer.start();
        }
    }
    public void stop(){
        if(isRunning){
            this.endTime=System.currentTimeMillis();
            this.isRunning=false;
            if (clockTimer != null) clockTimer.stop();
        }
    }
    
    public long getElapsedTime(){
        if(isRunning){
            return (System.currentTimeMillis()-startTime)/1000;
        }else{
            return (endTime-startTime)/1000;
        }
    }
}