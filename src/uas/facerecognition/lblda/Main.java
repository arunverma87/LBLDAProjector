/**
 *
 */
package uas.facerecognition.lblda;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

	public static void main(String[] args) throws FileNotFoundException, IOException {

		if (args.length != 3 && args.length != 1) {
			logger.error("Three arguments or one argument should be supplied with application to run.");
			return;
		}
		String imagePath = "";
		String subspacePath = "";
		String dataFile = "";

		String fileConsistImages = "";

		if (args.length == 3) {
			imagePath = args[0];
			subspacePath = args[1];
			dataFile = args[2];
		} else {
			fileConsistImages = args[0];
		}

		long startTime = System.currentTimeMillis();

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		logger.info("OpenCV library loaded successfully");

		File imagesFile = new File(fileConsistImages);

		try (BufferedReader br = new BufferedReader(new FileReader(imagesFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] temp = line.split(" ");

				imagePath = temp[0];
				subspacePath = temp[1];
				dataFile = temp[2];

				logger.info("Sample Projection for " + imagePath);

				Mat img = Imgcodecs.imread(imagePath);
				Mat resizeimage = new Mat();
				Size size = new Size(IMAGE_WIDTH, IMAGE_HEIGHT);
				Imgproc.resize(img, resizeimage, size);

				if (!new File(subspacePath).exists()) {
					logger.error("Subspace path is not exist." + subspacePath);
					return;
				}
				LBLDATrainer trainer = LBLDATrainer.getInstance();

				trainer.setData(resizeimage, IMAGE_WIDTH, IMAGE_HEIGHT, OUTPUT_DIMENSION, subspacePath);
				trainer.loadSamples();
				try {
					trainer.projectSamples();

					List<Double> resultData = trainer.getResultData(trainer.getProjectedSampleList().getSample(0));

					try {
						File file = new File(dataFile);
						BufferedWriter bw = new BufferedWriter(new FileWriter(file));
						for (int i = 0; i < resultData.size(); i++)
							bw.write(Double.toString(resultData.get(i)) + "\t");

						bw.flush();
						bw.close();
						logger.info("Features data in decimal format written at :" + dataFile);
					} catch (IOException exio) {
						logger.error(exio.getMessage());
					}

				} catch (Exception ex) {
					// TODO:: Handle Exception..
				}
			}
		}

		long endTime = System.currentTimeMillis();
		double timeElapsed = ((endTime - startTime) / 1000.0);
		logger.info("Sample Projection finished in " + timeElapsed + " sec");

	}
}
