package edu.nitrkl.graphics.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.JComponent;

import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy;
import matlabcontrol.MatlabProxyFactory;

public class P300FlasherGroups extends FlasherTemplate {

	/*
	 * Original Codes
	 */

	public ArrayList<ArrayList<JComponent>> flashers = new ArrayList<ArrayList<JComponent>>();
	public ArrayList<Integer> index = new ArrayList<Integer>();
	protected boolean thisIs2d = false;
	static MatlabProxyFactory matlabProxyFactory = new MatlabProxyFactory();
	MatlabProxy matlabProxy = null;

	boolean matProxyPresent = false;
	boolean muteConsole = false;

	/*
	 * Code from p300Group
	 */

	public P300FlasherGroups(JComponent[][] inputLabels) {
		int i = 0;
		this.index.removeAll(index);
		for (JComponent[] lableGroup : inputLabels) {
			ArrayList<JComponent> labelList = new ArrayList<JComponent>();
			for (JComponent aLabel : lableGroup)
				labelList.add(aLabel);
			this.flashers.add(labelList);
			this.index.add(new Integer(i));
			i++;
		}
		try {
			matlabProxy = matlabProxyFactory.getProxy();
			matProxyPresent = true;
		} catch (MatlabConnectionException e) {
			System.out.println("Cannot get Proxy");
			e.printStackTrace();
		}
	}

	public P300FlasherGroups(ArrayList<ArrayList<JComponent>> inputLabels,
			boolean is2D) {
		this.flashers = inputLabels;
		int j = 0;
		this.index.removeAll(index);
		for (int i = 0; i < inputLabels.size(); i++, j++)
			this.index.add(new Integer(i));
		if (is2D) {
			thisIs2d = true;
			for (int i = 0; i < inputLabels.get(0).size(); i++) {
				this.index.add(new Integer(i + j));
				ArrayList<JComponent> labelList = new ArrayList<JComponent>();
				for (int k = 0; k < j; k++)
					labelList.add(this.flashers.get(k).get(i));
				this.flashers.add(labelList);
			}
		}
		try {
			matlabProxy = matlabProxyFactory.getProxy();
			matProxyPresent = true;
		} catch (MatlabConnectionException e) {
			System.out.println("Cannot get Proxy");
			e.printStackTrace();
		}
	}

	void convertTo2D() {
		if (!thisIs2d) {
			thisIs2d = true;
			int j = this.flashers.size();
			for (int i = 0; i < this.flashers.get(0).size(); i++) {
				ArrayList<JComponent> labelList = new ArrayList<JComponent>();
				this.index.add(new Integer(i + j));
				for (int k = 0; k < j; k++) {
					labelList.add(this.flashers.get(k).get(i));
				}
				this.flashers.add(labelList);
			}
		}
	}


	public void halt() {
		flashing = false;
	}

	public void run() {

		try {
			matlabProxy.eval("times=[];");
		} catch (MatlabInvocationException e2) {
			e2.printStackTrace();
		}

		this.flashing = true;
		long entryTime = (new Date()).getTime();
		while (true) {
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			entryTime = (new Date()).getTime();

			while (this.flashing) {
				Collections.shuffle(index);
				for (int i : index) {
					if (!muteConsole)
						System.out.println("" + i + " "
								+ ((new Date()).getTime() - entryTime));
					if (matProxyPresent) {
						try {
							matlabProxy.eval("times=[times,[" + i + "; "
									+ ((new Date()).getTime() - entryTime)
									+ "] ];");
						} catch (MatlabInvocationException e) {
							e.printStackTrace();
						}
					}
					
					//TODO: Verify Functionality
					setVisible(true);
					paintGroupImmediately();
					
					this.lock.lock();
					try {
						Thread.sleep((long) (timePeriod * (1 - dutyCycle)));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					this.lock.unlock();
					
					//TODO: Verify Functionality
					setVisible(true);
					paintGroupImmediately();
					
					this.lock.lock();
					try {
						Thread.sleep((long) (timePeriod * dutyCycle));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					this.lock.unlock();
				}
			}
		}
	}
}
