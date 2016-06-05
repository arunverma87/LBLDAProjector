/**
 *
 */
package uas.facerecognition.lblda.lib.local;

import java.util.Comparator;

/**
 * @author arunv
 *
 */
public class LocalFeatureComparator implements Comparator<LocalFeature> {


	@Override
	public int compare(LocalFeature o1, LocalFeature o2) {
		if(o1.getValue() > o2.getValue())
			return -1;
		if(o1.getValue() < o2.getValue())
			return 1;
		return 0;
	}

}
