package resources;

import java.util.ArrayList;
import java.util.Random;

public class Perceptron extends SupervisedLearner {

	double [] weights;
	double learningRate = .5;
	Random rand;
	int epoch_threshold;
	double accuracy_threshold;
	double bias_input = 1.0;
	
	public Perceptron(Random rand){
		this.rand = rand;
		epoch_threshold = 15;
		accuracy_threshold = 0.1;
	}
	@Override
	public void train(Matrix features, Matrix labels) throws Exception {
		// TODO Auto-generated method stub
		weights = new double[features.cols()+1];
		initializeWeights(weights);
		//Loop until done
		boolean done = false;
		//Store outputs to compare to targets.
		double[] outputs = new double[features.rows()];
		//Number of epochs
		ArrayList<Double> accuracies = new ArrayList<Double>();
		accuracies.add(0.0);
		int epochs = 0;
		int good_epochs = 0;
		while(!done){
			for(int i = 0;i<features.rows();i++){
				double[] label = new double[1];
				this.predict(features.row(i), label);
				outputs[i] = label[0];
				if(outputs[i] != labels.get(i, 0)){
					adjustWeights(features.row(i), weights, labels.get(i, 0),outputs[i]);
				}
			}
			Matrix confusion = new Matrix();
			double acc = this.measureAccuracy(features, labels, confusion);
			if(Math.abs(accuracies.get(accuracies.size()-1) - acc) < accuracy_threshold){
				good_epochs++;
			}else{
				good_epochs = 0;
			}
			accuracies.add(acc);
			epochs ++;
			System.out.println("rsme="+acc);
			if(good_epochs > epoch_threshold || acc ==1){
				done = true;
			}
		}
		System.out.println(epochs);
	}

	@Override
	public void predict(double[] features, double[] labels) throws Exception {
		// TODO Auto-generated method stub
		double sum = 0;
		for(int i = 0;i< features.length;i++){
			sum += features[i] * weights[i];
		}
		sum += weights[weights.length-1];
		if(sum>=0){
			labels[0] = 1;
		}else{
			labels[0] = 0;
		}
	}

	private void initializeWeights(double[] weights){
		for(int i = 0;i<weights.length;i++){
			weights[i] = rand.nextDouble();
		}
	}
	private void adjustWeights(double[] values, double[] weights, double target, double output){
		for(int i = 0;i<values.length;i++){
			weights[i] += learningRate * (target - output) * values[i];
		}
		weights[weights.length-1] += learningRate * (target - output);
	}
}
