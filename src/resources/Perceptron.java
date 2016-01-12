package resources;

public class Perceptron extends SupervisedLearner {

	double [] weights;
	@Override
	public void train(Matrix features, Matrix labels) throws Exception {
		// TODO Auto-generated method stub
		weights = new double[features.cols()+1];
		initializeWeights(weights);
		int errors = 0;
		int correct = 0;
		double[] outputs = new double[features.rows()];
		for(int i = 0;i<features.rows();i++){
			int sum = 0;
			for(int j = 0;j<features.row(i).length; j++){
				sum+=features.get(i, j) * weights[j];
			}
			sum+=weights[weights.length-1];
			if(sum>=0){
				outputs[i] = 1;
			}else{
				outputs[i] = 0;
			}
			if(outputs[i]!=labels.get(i, 0)){
				adjustWeights(features.row(i),weights,labels.get(i, 0), outputs[i]);
				errors++;
			}else{
				correct++;
			}
		}
		double classification_accuracy = correct/(correct + errors);
		double classification_error = 1-classification_accuracy;
	}

	@Override
	public void predict(double[] features, double[] labels) throws Exception {
		// TODO Auto-generated method stub

	}

	private void initializeWeights(double[] weights){
		for(double weight: weights){
			weight = Math.random();
		}
	}
	private void adjustWeights(double[] values, double[] weights, double target, double output){
		double learningRate = .1;
		for(int i = 0;i<weights.length;i++){
			weights[i] += learningRate * (target - output) * values[i];
		}
	}
}
