package edu.nitrkl.graphics.test;

import java.io.IOException;

import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import edu.nitrkl.backend.components.MatlabInterface;

public class MtalabProxyTest {

	public static void main(String[] args) {
		try {
			MatlabInterface matInt = new MatlabInterface("./script/processor.m","index");
			for (double i : matInt.getIndex()) {
				int j = (int)(i);
				System.out.println("Index: " + j);
			}

		} catch (MatlabConnectionException | MatlabInvocationException
				| IOException e) {
			e.printStackTrace();
		}
	}
}
