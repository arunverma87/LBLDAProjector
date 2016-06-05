/**
 *
 */
package uas.facerecognition.lblda;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author arunv 0=ImagePath, 1=SubspacePath, 2=DataFile
 */
public class Main implements IConstant {

	static Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {

		if (args.length != 3) {
			logger.error("Three arguments should be supplied with application to run.");
			return;
		}

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		logger.info("OpenCV library loaded successfully");

		logger.info("Sample Projection for " + args[0]);
		long startTime = System.currentTimeMillis();


		Mat img = Imgcodecs.imread(args[0]);
		Mat resizeimage = new Mat();
		Size size = new Size(IMAGE_WIDTH, IMAGE_HEIGHT);
		Imgproc.resize(img, resizeimage, size);

		String subspacePath = args[1];

		if (!new File(subspacePath).exists()) {
			logger.error("Subspace path is not exist." + subspacePath);
			return;
		}
		LBLDATrainer trainer = LBLDATrainer.getInstance(resizeimage, IMAGE_WIDTH, IMAGE_HEIGHT, OUTPUT_DIMENSION, subspacePath);

		trainer.loadSamples();
		trainer.projectSamples();

		List<Double> resultData = trainer.getResultData(trainer.getProjectedSampleList().getSample(0));

		try {
			File file = new File(args[2]);
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < resultData.size(); i++)
				bw.write(Double.toString(resultData.get(i)) + "\t");

			bw.flush();
			bw.close();

		} catch (IOException exio) {
			logger.error(exio.getMessage());
		}

		long endTime = System.currentTimeMillis();
		double timeElapsed = ((endTime - startTime) / 1000.0);
		logger.info("Sample Projection finished in " + timeElapsed + " sec");
	}
}
