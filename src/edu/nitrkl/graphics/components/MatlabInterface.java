package edu.nitrkl.graphics.components;

import java.io.File;
import java.io.IOException;

import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy;
import matlabcontrol.MatlabProxyFactory;

public class MatlabInterface {
	public MatlabProxy matProxy = null;
	public String matLabScript = new String();
	public String command = new String();
	public String resultVariable = "ans";

	public MatlabInterface(String matlabScript, String resultVar)
			throws MatlabConnectionException, MatlabInvocationException,
			IOException {

		matProxy = (new MatlabProxyFactory()).getProxy();
		File testFile = (new File(matlabScript));
		String workDir = testFile.getCanonicalPath().split(
				testFile.getName().split(".m")[0])[0];
		// System.out.println("Working Directory: " + workDir);
		matProxy.eval("cd '" + workDir + "'");
		// matProxy.eval(testFile.getName().split(".m")[0]);
		this.command = testFile.getName().split(".m")[0];
		this.resultVariable = resultVar;
		// System.out.println(testFile.getCanonicalPath().split(testFile.getName().split(".m")[0])[0]);
		// System.out.println(testFile.getName().split(".m")[0]);
	}

	public String eval(String expression) {
		try {
			return (String) (this.matProxy.returningEval(expression, 1)
					.toString());
		} catch (MatlabInvocationException e) {
			return "FAILURE";
		}
	}

	public double[] getIndex() throws ClassCastException {
		try {
			double[] i = null;
			matProxy.eval(resultVariable + "=" + command + ";");
			i = (double[]) (matProxy.getVariable(resultVariable));// eval(matLabScript);
			return i;
		} catch (MatlabInvocationException e) {
			return new double[0];
		}
	}
}
