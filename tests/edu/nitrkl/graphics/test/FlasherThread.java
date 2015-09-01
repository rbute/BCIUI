package edu.nitrkl.graphics.test;

import java.awt.Color;
import java.awt.Component;
import java.util.concurrent.locks.ReentrantLock;

public class FlasherThread extends Thread {
	boolean run=true;
	
	Component component = null;
	
	public FlasherThread(Component aComponent) {
		component=aComponent;
	}
	
	public void halt() {
		run=false;
	}
	public void run(){
		
		ReentrantLock lock =new ReentrantLock();
		lock.lock();
		while(this.run){
			this.component.setBackground(Color.BLACK);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.component.setBackground(Color.WHITE);
			try {
				Thread.sleep(100);;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		lock.unlock();
	}
}
