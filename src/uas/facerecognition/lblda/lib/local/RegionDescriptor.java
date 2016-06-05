/**
 *
 */
package uas.facerecognition.lblda.lib.local;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author arunv
 *
 */
public class RegionDescriptor implements Serializable {

	private static final transient long serialVersionUID = 1L;

	private List<Integer> indices;

	public RegionDescriptor() {
		indices = new ArrayList<>();
	}

	/**
	 * @return the indices
	 */
	public List<Integer> getIndices() {
		return indices;
	}

	public int getIndexFromList(int index) {
		return this.indices.get(index);
	}

	/**
	 * @param indices
	 *            the indices to set
	 */
	public void setIndices(List<Integer> indices) {
		this.indices = indices;
	}

	public int getsize() {
		if (this.indices != null)
			return this.indices.size();
		return 0;
	}

	public void initDescriptor(int originalWidth, int originalHeight, int localWidth, int posX, int posY) {
		int minx, miny, maxx, maxy;
		minx = posX;
		miny = posY;
		maxx = posX + localWidth;
		maxy = posY + localWidth;

		if (maxx > originalWidth)
			maxx = originalWidth;
		if (maxy > originalHeight)
			maxy = originalHeight;

		int x, y;
		for (y = miny; y < maxy; y++) {
			for (x = minx; x < maxx; x++) {
				indices.add(y * originalWidth + x);
			}
		}
	}
}
