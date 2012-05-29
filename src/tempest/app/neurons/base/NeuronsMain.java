package tempest.app.neurons.base;

import java.util.Random;
import java.util.Vector;

public class NeuronsMain {
	public static void main(String[] args) {
		NeuronNetwork net = new NeuronNetwork(3, 5, 2, 5);
		Vector<Double> weights = new Vector<Double>();
		weights = net.getWeights();
		System.out.println(weights.toString());
		
		Vector<Double> inputs = new Vector<Double>();
		inputs.add(0.5);
		inputs.add(-1.0);
		inputs.add(-0.36);
		
		// Pool is neural networks
		
		for (int i = 0; i<100; i++)
		{
			// Play the game for 30 seconds
			System.out.println(net.Update(inputs).toString());
			
			// AI`s crossover & mutate
			// GA ....
			//Vector<Double> newWeights = new Vector<Double>();
			Random rand = new Random();
			//net.updateWeights(newWeights);
			inputs.set(0, -1.0 + rand.nextDouble()*2);
		}
		
		System.out.println(net.getNumberOfWeights());
	}
}