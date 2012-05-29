package tempest.app.neurons.base;

import java.util.Vector;

public class NeuronNetwork {
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
		// Put new chromosome, Vector<Double>
	}
	
	
	@SuppressWarnings("unchecked")
	public Vector<Double> Update(Vector<Double> inputs) {
		Vector<Double> outputs = new Vector<Double>();

		double netInput = 0.0;
		
		if(inputs.size() != numberOfInputs) return outputs;
	
		for(int i=0;i<numberHiddenLayers+1;++i){
			
			if(i>0)
			{
				inputs = (Vector<Double>)outputs.clone();			
			}
			
			outputs.clear();
			
			NeuronLayer neuronLayer = network.get(i);
			Vector<Neuron> layerNeurons = neuronLayer.getLayerNeurons();
			for(int j=0; j < layerNeurons.size(); j++) {
				Neuron neuron = layerNeurons.get(j);
				int numInputs = neuron.getInputs();
				
				for (int w = 0; w < numInputs; w++) {
					netInput += neuron.getInputWeights().get(w) * 
							inputs.get(w);
				}
				
				outputs.add(netInput > neuron.getActivation() ? 1.0 : 0.0);
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
}
