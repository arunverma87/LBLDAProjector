/**
 *
 */
package uas.facerecognition.lblda.lib.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author arunv
 *
 */
public class ImageIO {

	static Logger logger = LoggerFactory.getLogger(ImageIO.class);

	private String filePath;
	private Mat image;
	private int width;
	private int height;

	public ImageIO(String filePath, int width, int height) {
		this.filePath = filePath;
		this.width = width;
		this.height = height;
	}

	public ImageIO(Mat image, int width, int height) {
		this.filePath = "";
		this.image = image;
		this.width = width;
		this.height = height;
	}

	public List<Double> getGreyImageData() {

		List<Double> data = new ArrayList<>();
		if (!this.filePath.equals("")) {

			if (new File(this.filePath).exists()) {
				Mat img = Imgcodecs.imread(this.filePath);
				// Mat grayImg = new Mat(height, width, CvType.CV_8U);
				// Imgproc.cvtColor(img, grayImg, Imgproc.COLOR_BGR2GRAY);

				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						// double[] temp = img.get(i, j)[0];
						data.add(img.get(i, j)[0]);
					}
				}

			} else {
				logger.debug(this.filePath + " : is not exist ");
			}

		} else {

			 Mat grayImg = new Mat(height, width, CvType.CV_8U);
			 Imgproc.cvtColor(this.image, grayImg, Imgproc.COLOR_BGR2GRAY);

			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					// double[] temp = img.get(i, j)[0];
					data.add(grayImg.get(i, j)[0]);
				}
			}


		}
		return data;

	}
}