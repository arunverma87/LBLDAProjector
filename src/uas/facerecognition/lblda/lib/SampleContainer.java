/**
 *
 */
package uas.facerecognition.lblda.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.opencv.core.Mat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author arunv
 *
 */
public class SampleContainer {

	//static Logger logger = LoggerFactory.getLogger(SampleContainer.class);

	private List<Sample> samples;
	private Map<String, Integer> diffClasses;
	private Sample avgSample;
	ExecutorService executorService = Executors.newFixedThreadPool(50);

	public SampleContainer() {
		samples = new ArrayList<>();
		diffClasses = new HashMap<String, Integer>();
	}

	public void clear() {
		samples.clear();
	}

	public int getSize() {
		if (samples != null)
			return samples.size();
		else
			return 0;
	}

	public List<Sample> getAllSamples() {
		return samples;
	}

	public Sample getSample(int index) {
		return samples.get(index);
	}

	public void addSample(Sample sample) {
		this.samples.add(sample);
		if (diffClasses.containsKey(sample.getClassName()))
			diffClasses.put(sample.getClassName(), diffClasses.get(sample.getClassName()) + 1);
		else
			diffClasses.put(sample.getClassName(), 1);
	}

	public int getNumberOfClasses() {
		return this.diffClasses.size();
	}

	public Sample getAvgSample() {
		if (avgSample == null)
			calculateAvgSample();
		return avgSample;
	}

	public void calculateAvgSample() {
		int i, j;
		avgSample = new Sample();
		int dataSize = samples.get(0).getDataSize();
		avgSample.initAndSetDataSize(dataSize);

		double[] tempData = new double[dataSize];

		// Arrays.fill(tempData, 0);

		for (i = 0; i < getSize(); i++) {
			for (j = 0; j < dataSize; j++) {
				tempData[j] += samples.get(i).getDataOfIndex(j);
			}
		}
		for (j = 0; j < dataSize; j++) {
			avgSample.addData(tempData[j] / samples.size());
		}

		tempData = null;
	}

	public Sample getAvgSampleOfClass(String className) {
		int i, j, k = 0;
		Sample avg = new Sample(className);

		int dataSize = samples.get(0).getDataSize();
		avg.initAndSetDataSize(dataSize);

		double[] tempData = new double[dataSize];

		// Arrays.fill(tempData, 0);

		for (i = 0; i < getSize(); i++) {
			if (!samples.get(i).getClassName().equals(className))
				continue;
			for (j = 0; j < dataSize; j++) {
				tempData[j] += samples.get(i).getDataOfIndex(j);
			}
			k++;
		}
		for (j = 0; j < dataSize; j++) {
			avg.addData(tempData[j] / k);
			// avg.setDataOnIndex(tempData[j] / k, j);
		}
		tempData = null;
		return avg;
	}

	public void load(Map<String, Mat> images, int width, int height) {
		Sample sample;
		for (String className : images.keySet()) {
			sample = new Sample();
			if (sample.load(images.get(className), className, width, height)) {
				samples.add(sample);
			}
		}

	}
}
