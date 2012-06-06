package tempest.app.neurons.base;

import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

public class Neuron implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int inputs  = 0;
	private Vector<Double> inputWeights;

	public Neuron(int inputs) {
		this.inputs = inputs;
		
		setInputWeights(new Vector<Double>(this.inputs));
		
		Random rnd = new Random();
		
		for (int i = 0; i < this.inputs+1; i++) {
			getInputWeights().add(-1 + rnd.nextDouble()*2);
		}
	}
	
	public Vector<Double> getInputWeights() {
		return this.inputWeights;
	}
	
	public void printWeights() {
		for (Double weight : getInputWeights()) {
			System.out.print(weight + " ");
		}
		System.out.println();
	}

	public int getInputs() {
		return inputs;
	}

	public Double getActivation() {
		return getInputWeights().lastElement();
	}

	public void setInputWeights(Vector<Double> inputWeights) {
		this.inputWeights = inputWeights;
	}
}