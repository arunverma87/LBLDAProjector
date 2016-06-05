/**
 *
 */
package uas.facerecognition.lblda.lib.local;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uas.facerecognition.lblda.lib.Subspace;

/**
 * @author arunv
 *
 */
public class LocalSubspace implements Serializable {

	private static final transient long serialVersionUID = 1L;

	private List<RegionDescriptor> localDescriptor;

	private List<Subspace> localSubspaces;

	private List<LocalFeature> localFeatures;

	/**
	 *
	 */
	public LocalSubspace() {
		this.localDescriptor = new ArrayList<>();
		this.localSubspaces = new ArrayList<>();
		this.localFeatures = new ArrayList<>();
	}

	/**
	 * @return the localDescriptor
	 */
	public List<RegionDescriptor> getLocalDescriptorList() {
		return this.localDescriptor;
	}

	public RegionDescriptor getLocalDescriptor(int index) {
		if (this.localDescriptor != null) {
			return this.localDescriptor.get(index);
		}
		return null;
	}

	public int getLocalDescriptorListSize() {
		return this.localDescriptor.size();
	}

	public void setLocalDescriptorList(List<RegionDescriptor> localDescriptor) {
		this.localDescriptor = localDescriptor;
	}

	public void addDescriptor(RegionDescriptor descriptor) {
		this.localDescriptor.add(descriptor);
	}

	// ############################
	public List<Subspace> getLocalSubspaceList() {
		return this.localSubspaces;
	}

	public Subspace getLocalSubspace(int index) {
		if (this.localSubspaces != null) {
			return this.localSubspaces.get(index);
		}
		return null;
	}

	public int getLocalSubspaceListSize() {
		return this.localSubspaces.size();
	}

	public void setLocalSubspaceList(List<Subspace> localSubspaces) {
		this.localSubspaces = localSubspaces;
	}

	public void addSubspace(Subspace subspace) {
		this.localSubspaces.add(subspace);
	}

	// ############################
	public List<LocalFeature> getLocalFeatureList() {
		return this.localFeatures;
	}

	public LocalFeature getLocalFeature(int index) {
		if (this.localFeatures != null) {
			return this.localFeatures.get(index);
		}
		return null;
	}

	public int getLocalFeatureListSize() {
		return this.localFeatures.size();
	}

	public void setLocalFeatureList(List<LocalFeature> localFeature) {
		this.localFeatures = localFeature;
	}

	public void addLocalFeature(LocalFeature localFeature) {
		this.localFeatures.add(localFeature);
	}

}
