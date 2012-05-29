package tempest.app.neurons.base;

import java.util.Vector;

public class NeuronLayer {
	private int numberOfNeurons = 0;
	private String label = "";
	private Vector<Neuron> layer;
	
	public NeuronLayer(int neurons, int neuronsInput, String label) {
		this.numberOfNeurons = neurons;
		this.label = label;
		
		layer = new Vector<Neuron>(numberOfNeurons);
		for (int i = 0; i < this.numberOfNeurons; i++) {
			layer.add(new Neuron(neuronsInput));
		}
	}
	
	public Vector<Neuron> getLayerNeurons() {
		return this.layer;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public void changeLabel(String newLabel) {
		this.label = newLabel;
	}
}