/**
 *
 */
package uas.facerecognition.lblda.lib;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uas.facerecognition.lblda.lib.util.ImageIO;

/**
 * @author arunv
 *
 */
public class Sample {

	static Logger logger = LoggerFactory.getLogger(Sample.class);

	private List<Double> data;

	private String fileName;
	private String className;
	private int width;
	private int height;

	public Sample() {

	}

	public Sample(String className) {
		this.className = className;
	}

	/**
	 * @return the data
	 */
	public List<Double> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(List<Double> data) {
		this.data = data;
	}

	public int getDataSize() {
		return this.data.size();
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className
	 *            the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	public void initAndSetDataSize(int size) {
		this.data = new ArrayList<>(size);
	}

	public double getDataOfIndex(int index) {
		return this.data.get(index);
	}

	public void addData(double value) {
		this.data.add(value);
	}

	public void addDataOnIndex(double value, int index) {
		this.data.add(index, value);
	}

	public boolean load(String fileName, String className, int width, int height) {

		this.fileName = fileName;
		this.className = className;
		this.width = width;
		this.height = height;
		this.data = new ImageIO(fileName, this.width, this.height).getGreyImageData();

		if (this.data == null) {
			logger.debug(fileName + ": not able to get GreyImageData");
			return false;
		}

		return true;
	}

	public boolean load(Mat image, String className, int width, int height) {

		this.fileName = "";
		this.className = className;
		this.width = width;
		this.height = height;
		this.data = new ImageIO(image, this.width, this.height).getGreyImageData();

		if (this.data == null) {
			logger.debug(fileName + ": not able to get GreyImageData");
			return false;
		}
		return true;
	}


}
