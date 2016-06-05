/**
 *
 */
package uas.facerecognition.lblda.lib.local;

import uas.facerecognition.lblda.lib.Sample;
import uas.facerecognition.lblda.lib.SampleContainer;
import uas.facerecognition.lblda.lib.Subspace;

/**
 * @author arunv
 *
 */
public class LocalSubspaceProjector {

	private LocalSubspace localSubspace;

	/**
	 * @param localSubspace
	 */
	public LocalSubspaceProjector(LocalSubspace localSubspace) {
		this.localSubspace = localSubspace;
	}

	private Sample createLocalSample(Sample originalSample, RegionDescriptor regionDescriptor) {

		Sample localSample = new Sample();
		localSample.initAndSetDataSize(regionDescriptor.getsize());
		localSample.setFileName(originalSample.getFileName());
		localSample.setClassName(originalSample.getClassName());

		for (int i = 0; i < regionDescriptor.getsize(); i++) {
			localSample.addData(originalSample.getDataOfIndex(regionDescriptor.getIndexFromList(i)));
		}

		return localSample;
	}

	public Sample projectSample(Sample originalSample, int dim) {

		Sample projectedSample = new Sample();
		projectedSample.initAndSetDataSize(dim);

		int areaindex, subspaceindex, axisindex, originaldim;
		double sum;
		Subspace cursubspace;
		Sample cursample;

		SampleContainer localContainer = new SampleContainer();
		int numLocalDescriptors = localSubspace.getLocalDescriptorListSize();

		for (int i = 0; i < numLocalDescriptors; i++) {
			Sample sample = createLocalSample(originalSample, localSubspace.getLocalDescriptor(i));
			localContainer.addSample(sample);
		}

		for (int i = 0; i < dim; i++) {
			areaindex = localSubspace.getLocalFeature(i).getRegionDescriptorIndex();
			subspaceindex = localSubspace.getLocalFeature(i).getSubspaceindex();
			axisindex = localSubspace.getLocalFeature(i).getAxisindex();

			cursubspace = localSubspace.getLocalSubspace(subspaceindex);
			cursample = localContainer.getSample(areaindex);
			originaldim = cursubspace.getOriginalDim();
			sum = 0;
			for (int j = 0; j < originaldim; j++) {
				sum += (cursample.getDataOfIndex(j) - cursubspace.getCenterOffset().get(j))
						* cursubspace.getSubspaceAxes().get((axisindex * originaldim) + j);
				// sum += ((cursample)[j] - cursubspace->GetCenterOffset()[j]) *
				// cursubspace->GetSubspaceAxes()[axisindex*originaldim+j];
			}
			projectedSample.addData(sum);
		}

		projectedSample.setClassName(originalSample.getClassName());
		projectedSample.setFileName(originalSample.getFileName());

		return projectedSample;
	}

	public SampleContainer projectSampleContainer(SampleContainer originalSampleContainer, int dim) {

		if (dim == 0)
			dim = this.localSubspace.getLocalFeatureListSize();

		SampleContainer projectedSampleContainer = new SampleContainer();

		int totalSamples = originalSampleContainer.getSize();
		Sample projectedSample = null;

		for (int i = 0; i < totalSamples; i++) {
			projectedSample = projectSample(originalSampleContainer.getSample(i), dim);
			if (projectedSample != null)
				projectedSampleContainer.addSample(projectedSample);
		}
		return projectedSampleContainer;
	}
}
