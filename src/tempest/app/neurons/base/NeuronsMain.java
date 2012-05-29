package tempest.app.neurons.base;

import java.util.Vector;

public class NeuronsMain {
	public static void main(String[] args) {
		NeuronNetwork net = new NeuronNetwork(3, 5, 2, 5);
		Vector<Double> weights = new Vector<Double>();
		weights = net.getWeights();
		System.out.println(weights.toString());
		
		Vector<Integer> inputs = new Vector<Integer>();
		inputs.add(1);
		inputs.add(-1);
		inputs.add(-2);
		inputs.add(2);
		
		// Pool is neural networks
		
		for (int i = 0; i<100; i++) {
			// Play the game for 30 seconds
			System.out.println(net.Update(inputs).toString());
			
			// AI`s crossover & mutate
			// GA ....
			//Vector<Double> newWeights = new Vector<Double>();
			//net.updateWeights(newWeights);
		}
		System.out.println(net.getNumberOfWeights());
	}
}