package tempest.app.neurons.base;

import java.util.Vector;

public class NeuronNetwork implements Cloneable {
	private int numberOfInputs;
	private int numberOfOutputs;
	private int numberHiddenLayers;
	private int neuronsPerHiddenLayer;

	private Vector<NeuronLayer> network;

	public NeuronNetwork(int inputs, int outputs, int hiddenLayers, int neuronsPerHiddenLayer) {
		this.numberOfInputs        = inputs;
		this.numberOfOutputs 	   = outputs;
		this.numberHiddenLayers    = hiddenLayers;
		this.neuronsPerHiddenLayer = neuronsPerHiddenLayer;

		network = new Vector<NeuronLayer>();
		network.add(new NeuronLayer(neuronsPerHiddenLayer, inputs, "Input"));
		for (int i = 0; i < hiddenLayers-1; i++) {
			network.add(new NeuronLayer(neuronsPerHiddenLayer, neuronsPerHiddenLayer, "Hidden " + i+1));
		}
		network.add(new NeuronLayer(outputs, neuronsPerHiddenLayer, "Output"));
	}

	// Chromosome
	public Vector<Double> getWeights() {
		Vector<Double> res = new Vector<Double>();

		for(int i=0;i<network.size();i++) {
			for (int n = 0; n < network.get(i).getLayerNeurons().size(); n++) {
				res.addAll(network.get(i).getLayerNeurons().get(n).getInputWeights());
			}
		}

		return res;
	}

	public int getNumberOfWeights() {
		int res = 0;

		for (int i = 0; i < network.size(); i++) {
			for (int n = 0; n < network.get(i).getLayerNeurons().size(); n++) {
				res++;
			}
		}

		return res;
	}

	public void updateWeights(Vector<Double> weights) {
		int currentWeight = 0;
		for (NeuronLayer layer : network) {
			for (Neuron neuron : layer.getLayerNeurons()) {
				Vector<Double> newWeights = new Vector<Double>();
				
				for (int i = currentWeight; i < neuron.getInputs(); i++, currentWeight++) {
					newWeights.add(weights.get(i));
				}
				
				neuron.setInputWeights(newWeights);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Vector<Integer> Update(Vector<Integer> inputs) {
		Vector<Integer> outputs = new Vector<Integer>();

		for (int i = 0; i < network.size(); i++) {
			NeuronLayer layer = network.get(i);
			
			if (i > 0) {
				inputs = (Vector<Integer>)outputs.clone();
			}
			
			outputs.clear();
			
			for (Neuron neuron : layer.getLayerNeurons()) {
				double inputResult = 0;
				
				for (int j = 0; j < neuron.getInputs(); j++) {
					inputResult += neuron.getInputWeights().get(j) * inputs.get(j);
				}
				
				outputs.add(inputResult > neuron.getActivation() ? 1 : 0);
			}			
		}
		
		return outputs;
	}

	public int getNumberOfInputs() {
		return numberOfInputs;
	}

	public int getNumberOfOutputs() {
		return numberOfOutputs;
	}

	public int getNumberHiddenLayers() {
		return numberHiddenLayers;
	}

	public int getNeuronsPerHiddenLayer() {
		return neuronsPerHiddenLayer;
	}

	public Vector<NeuronLayer> getNetwork() {
		return network;
	}

	public NeuronNetwork clone() {
		try {
			return (NeuronNetwork) super.clone();
		}
		catch( CloneNotSupportedException e )
		{
			return null;
		}
	} 
}
