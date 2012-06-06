package tempest.app.neurons.base;

import java.io.Serializable;
import java.util.Random;
import java.util.Vector;


public class Genetic implements Serializable {
	private static final long serialVersionUID = 1L;
	private Vector<NeuronNetwork> pool;
	private int bestNetworkIndex;
	private Vector<Integer> networkScores;
	private int generation;
	private static Random rnd = new Random();
	
	private static double crossOverRate = Constants.crossRate;
	private static double mutationRate = Constants.mutateRate;

	public Genetic(Vector<NeuronNetwork> pool) {
		super();
		this.pool = pool;
		bestNetworkIndex = rnd.nextInt(pool.size());
		setNetworkScores(new Vector<Integer>(pool.size()));
	}
	
	public void evolve() {
		NeuronNetwork best = pool.get(bestNetworkIndex).clone();
		
		Vector<NeuronNetwork> newPool = new Vector<NeuronNetwork>(pool.size());
		
		for(int i = 0; i < pool.size() - 1; i++) {					
			NeuronNetwork net1 = getNetworkRoulette();
			NeuronNetwork net2 = getNetworkRoulette();
			
			crossOver(net1, net2);
			
			mutate(net1);
			mutate(net2);
			
			newPool.add(rnd.nextInt() % 2  > 0 ? net1 : net2);
		}
		
		newPool.add(best);
		
		pool.clear();
		pool = newPool;
		
		generation++;
	}
	
	private NeuronNetwork getNetworkRoulette() {
		double slice = rnd.nextDouble() * getTotalScore();
		
		double currentScore = 0;
		
		for(int i = 0; i< networkScores.size(); i++) {
			currentScore += networkScores.get(i);
			
			if (currentScore >= slice) {
				return pool.get(i);
			}
		}
		
		return pool.get(0); // Not reachable but for safety
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
	}

	private final void mutate(NeuronNetwork network) {
		Vector<Double> weights = network.getWeights();
		
		for(int i = 0; i < network.getWeights().size(); i++) {
			if (rnd.nextDouble() < mutationRate) {				
				weights.set(i, rnd.nextDouble());
			}
		}
	}
	
	public int getBestScore() {
		int bestScore = -99999;
		
		for (int score : networkScores) {
			if (score > bestScore) {
				bestScore = score;
				setBestPlayer(networkScores.indexOf(score));
			}
		}
		
		return bestScore;
	}

	public int getWorstScore() {
		int worstScore = 99999;
		
		for (int score : networkScores) {
			if (score < worstScore) {
				worstScore = score;
			}
		}
		
		return worstScore;
	}

	public int getTotalScore() {
		int totalScore = 0;
		
		for (int score : networkScores) {
			totalScore += score;
		}
		
		return totalScore;
	}

	public int getAvrScosre() {
		return getTotalScore() / networkScores.size();
	}


	public int getGeneration() {
		return generation;
	}

	public NeuronNetwork getBestNetwork() {
		return pool.get(bestNetworkIndex);
	}


	public void setBestPlayer(int bestNetwork) {
		this.bestNetworkIndex = bestNetwork;
	}
	
	public Vector<NeuronNetwork> getNetworks() {
		return pool;
	}

	public Vector<Integer> getNetworkScores() {
		return networkScores;
	}

	public void setNetworkScores(Vector<Integer> networkScores) {
		this.networkScores = networkScores;
	}
}
