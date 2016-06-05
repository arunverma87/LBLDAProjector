/**
 *
 */
package uas.facerecognition.lblda.lib.local;

import java.io.Serializable;

/**
 * @author arunv
 *
 */
public class LocalFeature implements Serializable {

	private static final transient long serialVersionUID = 1L;

	private int regionDescriptorIndex; //index of image region
	private int subspaceindex; //subspace index corresponding to the image region
	private int axisindex; //index of the basis vector inside the subspace
	private double value; //value  giving indication about the goodness of the feature

	/**
	 *
	 */
	public LocalFeature() {

	}

	public LocalFeature(int regionDescriptorIndex, int subspaceindex, int axisindex, double value) {
		this.regionDescriptorIndex = regionDescriptorIndex;
		this.subspaceindex = subspaceindex;
		this.axisindex = axisindex;
		this.value = value;
	}

	public int getRegionDescriptorIndex() {
		return regionDescriptorIndex;
	}

	public void setRegionDescriptorIndex(int regionDescriptorIndex) {
		this.regionDescriptorIndex = regionDescriptorIndex;
	}

	public int getSubspaceindex() {
		return subspaceindex;
	}

	public void setSubspaceindex(int subspaceindex) {
		this.subspaceindex = subspaceindex;
	}

	public int getAxisindex() {
		return axisindex;
	}

	public void setAxisindex(int axisindex) {
		this.axisindex = axisindex;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

}
