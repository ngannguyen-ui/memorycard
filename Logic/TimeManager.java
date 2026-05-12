package Logic;

public class TimeManager{
    private long startTime;
    private long endTime;
    private long totalTime;
    private boolean isRunning;
    public TimeManager(){
        this.startTime=0;
        this.totalTime=0;
        this.endTime=0;
        this.isRunning=false;
    }
    public void start(){
        if(!isRunning){
            this.startTime=System.currentTimeMillis();
            this.isRunning=true;
        }
    }
    public void stop(){
        if(isRunning){
            this.endTime=System.currentTimeMillis();
            this.isRunning=false;
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