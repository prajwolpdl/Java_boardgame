package assignment1;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;

public class Time {

	
	private Timer counter;
	private TimerTask task,taskCont;
	private int maxSec;
	private boolean limit;
	public Time() {
		this.maxSec = 0;
		this.limit = false;
		int count = this.maxSec;
		this.counter = new Timer();
	}
	public void Starttime() {
		int count = this.maxSec;
		String output = null;
		task = new TimerTask() {
			int secs = 0;
			int MaxSecs = count;
			    public void run() {
			    	if (secs  <= count ) {
						String output = Integer.toString(secs);
						View.setTimerText(output);
			            secs += 1;
			        } else {
			        	EndTime();
			        }
			    }
			};


		taskCont = new TimerTask() {
			int secs = 0;
			public void run() {
		        String output = Integer.toString(secs);
		        View.setTimerText(output);
		        secs += 1;
		        }
		};
		if (this.limit == false) {
			this.counter.schedule(taskCont, 0, 1000);
		}else {
			this.counter.schedule(task, 0, 1000);
		}
	}
	private void EndTime() {
		this.counter.cancel();	
	}
	private void SetLimit(int time) {
		this.maxSec = time;
		this.limit = true;
	}
	private boolean isTimeLeft() {
		if (this.task != null) {
			return false;
		}
		return true;
	}
}
