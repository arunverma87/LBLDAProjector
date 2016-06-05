/**
 *
 */
package uas.facerecognition.lblda;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opencv.core.Mat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uas.facerecognition.lblda.lib.Sample;
import uas.facerecognition.lblda.lib.SampleContainer;
import uas.facerecognition.lblda.lib.local.LocalSubspace;
import uas.facerecognition.lblda.lib.local.LocalSubspaceProjector;

/**
 * @author arunv
 *
 */
public class LBLDATrainer {

	static Logger logger = LoggerFactory.getLogger(LBLDATrainer.class);

	private String subspacePath;
	private int imageWidth;
	private int imageHeight;
	private int outputDimension;

	private SampleContainer trainSamples, testSamples;
	private SampleContainer projectedTrainSamples, projectedTestSamples;

	private Map<String, Mat> trainImages;
	private Map<String, Mat> testImages;

	private LocalSubspace localSubspace = null;

	private static LBLDATrainer objTrainer = null;

	public static LBLDATrainer getInstance(Mat image, int imageWidth, int imageHeight, int outputDimension,
			String subspacePath) {
		if (objTrainer == null) {
			objTrainer = new LBLDATrainer(image, imageWidth, imageHeight, outputDimension, subspacePath);
		}
		return objTrainer;
	}

	public LBLDATrainer(Mat image, int imageWidth, int imageHeight, int outputDimension, String subspacePath) {

		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		this.outputDimension = outputDimension;
		this.subspacePath = subspacePath;

		trainImages = new HashMap<>();
		trainImages.put("NewClass", image);
		testImages = trainImages;

	}

	public SampleContainer getProjectedSampleList() {
		return projectedTestSamples;
	}

	public List<Double> getResultData(Sample sample){
		return sample.getData();
	}

	public boolean loadSamples() {

		trainSamples = new SampleContainer();
		trainSamples.load(trainImages, this.imageWidth, this.imageHeight);

		if (trainImages == testImages)
			testSamples = trainSamples;
		else
			testSamples = new SampleContainer();

		logger.debug("Total samples loaded in to system: " + String.valueOf(trainSamples.getSize()));
		return true;
	}

	public boolean projectSamples() {

		if (this.localSubspace == null) {
			if (loadData(this.subspacePath))
				return false;
		}

		// project samples into subspace

		LocalSubspaceProjector projector = new LocalSubspaceProjector(this.localSubspace);

		if (testSamples == trainSamples) {
			projectedTrainSamples = projector.projectSampleContainer(this.trainSamples, 0);
			projectedTestSamples = projectedTrainSamples;
		} else {
			projectedTrainSamples = projector.projectSampleContainer(this.trainSamples, 0);
			projectedTestSamples = projector.projectSampleContainer(this.testSamples, 0);
		}

		return true;
	}

	public boolean loadData(String path) {
		System.out.println("Starting deSerialization...");
		try {
			FileInputStream fileIn = new FileInputStream(path);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			try {
				this.localSubspace = (LocalSubspace) in.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println("Deserialized Data from " + path);
			in.close();
			fileIn.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}