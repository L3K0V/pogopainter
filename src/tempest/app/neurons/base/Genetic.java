package tempest.app.neurons.base;

import java.util.Random;
import java.util.Vector;


public class Genetic {
	private Vector<NeuronNetwork> pool;
	private int bestScore = 0;
	private int worstScore = 0;
	private int totalScore = 0;
	private int avrScore = 0;
	private NeuronNetwork bestPlayer;
	private int generation = 0;
	private static Random rnd = new Random();
	
	private static double crossOverRate = 0.7;
	private static double mutationRate = 0.08;

	public Genetic(Vector<NeuronNetwork> pool) {
		super();
		this.pool = pool;
		bestPlayer = pool.get(rnd.nextInt(pool.size()));
	}
	
	public void evolve() {
		NeuronNetwork best = bestPlayer.clone();
		
		for(int i = 0; i < pool.size(); i++) {
			NeuronNetwork current = pool.get(i);
			if (current == bestPlayer)
				continue;
			
			crossOver(best, pool.get(i));
			mutate(i);
			
			System.out.println("Weights: "+current.getWeights().toString());
		}
		
		generation++;
	}
	
	
	private final void crossOver(NeuronNetwork net1, NeuronNetwork net2) {

		// Should we cross over?
		if (rnd.nextDouble() > crossOverRate) return;

		// Generate a random position
		int pos = rnd.nextInt(pool.get(0).getNumberOfWeights()+1);
		Vector<Double> networkOne = net1.getWeights();
		Vector<Double> networkTwo = net2.getWeights();
				
		// Swap all chars after that position
		for (int x=pos; x<networkOne.size(); x++) {
			// Get our character
			double tmp = networkOne.elementAt(x);
			
			networkOne.set(x, networkTwo.elementAt(x));
			networkTwo.set(x, tmp);
		}
		
		net1.updateWeights(networkOne);
		net2.updateWeights(networkTwo);
	}

	private final void mutate(int net) {
		for (int x=0;x<pool.get(net).getNumberOfWeights()+1;x++) {
			if (rnd.nextDouble()<= mutationRate) {
				Vector<Double> weights = pool.get(net).getWeights();
				weights.set(x, rnd.nextDouble());
				pool.get(net).updateWeights(weights);
			}
		}
	}
	
	public int getBestScore() {
		return bestScore;
	}


	public void setBestScore(int bestScore) {
		this.bestScore = bestScore;
	}


	public int getWorstScore() {
		return worstScore;
	}


	public void setWorstScore(int worstScore) {
		this.worstScore = worstScore;
	}


	public int getTotalScore() {
		return totalScore;
	}


	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}


	public int getAvrScore() {
		return avrScore;
	}


	public void setAvrScore(int avrScore) {
		this.avrScore = avrScore;
	}


	public NeuronNetwork getBestPlayer() {
		return bestPlayer;
	}


	public void setBestPlayer(NeuronNetwork bestPlayer) {
		this.bestPlayer = bestPlayer;
	}
	
	public Vector<NeuronNetwork> getNetworks() {
		return pool;
	}
}
