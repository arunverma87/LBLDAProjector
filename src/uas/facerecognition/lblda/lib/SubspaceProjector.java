/**
 *
 */
package uas.facerecognition.lblda.lib;

/**
 * @author arunv
 *
 */
public class SubspaceProjector {

	private Subspace subspace;

	public SubspaceProjector(Subspace subspaceForProjection) {
		this.subspace = subspaceForProjection;
	}

	public Sample projectSample(Sample originalSample, int dim) {

		Sample projectedSample = new Sample();
		projectedSample.initAndSetDataSize(dim);

		int i, j;
		double sum;
		for (i = 0; i < dim; i++) {
			sum = 0;
			for (j = 0; j < originalSample.getDataSize(); j++) {
				sum += (originalSample.getDataOfIndex(j) - subspace.getCenterOffset().get(j))
						* subspace.getSubspaceAxes().get((i * subspace.getOriginalDim()) + j);
				// sum += ((*originalSample)[j] - subspace->centerOffset[j]) *
				// subspace->subspaceAxes[i*subspace->originalDim+j];
			}
			projectedSample.addDataOnIndex(sum, i);
		}
		projectedSample.setClassName(originalSample.getClassName());
		projectedSample.setFileName(originalSample.getFileName());
		return projectedSample;
	}

	public SampleContainer projectSampleContainer(SampleContainer originalSampleContainer, int dim) {

		if (dim > this.subspace.getSubspaceDim())
			dim = this.subspace.getSubspaceDim();

		SampleContainer projectedContainer = new SampleContainer();

		int totalSamples = originalSampleContainer.getSize();

		Sample projectedSample = null;

		for (int i = 0; i < totalSamples; i++) {
			projectedSample = projectSample(originalSampleContainer.getSample(i), dim);
			if (projectedSample != null)
				projectedContainer.addSample(projectedSample);
		}
		// Calculating Average
		projectedContainer.calculateAvgSample();

		return projectedContainer;
	}

}
