 
package edu.nitrkl.graphics.components;

import java.util.ArrayList;

import javax.swing.JComponent;

public class SSVEPFlasher extends FlasherTemplate {

	public SSVEPFlasher(JComponent[] inputLables) {
		ArrayList<JComponent> temp = new ArrayList<JComponent>();
		for (JComponent alabel : inputLables)
			temp.add(alabel);
		this.groupList = temp;
	}

	public SSVEPFlasher(ArrayList<JComponent> inputLables) {
		this.groupList = inputLables;
	}

	public void halt() {
		flashing = false;
	}

	public void run() {
		flashing = true;
		while (true)	
			while (this.flashing) {
				setVisible(true);
				paintGroupImmediately();
				this.lock.lock();
				try {
					Thread.sleep((long) (timePeriod * dutyCycle));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.lock.unlock();
				setVisible(false);
				paintGroupImmediately();
				this.lock.lock();
				try {
					Thread.sleep((long) (timePeriod * (1 - dutyCycle)));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.lock.unlock();
			}
	}
}
