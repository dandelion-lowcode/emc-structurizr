/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Francisco Martinez-Lasaca - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.structurizr;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.execute.introspection.IPropertyGetter;
import org.eclipse.epsilon.eol.execute.introspection.IPropertySetter;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;

import com.structurizr.Workspace;
import com.structurizr.dsl.StructurizrDslParser;
import com.structurizr.util.WorkspaceUtils;

public class StructurizrModel extends org.eclipse.epsilon.eol.models.Model {
	static private final List<String> KNOWN_TOP_LEVEL_TYPES = List.of("Workspace", "Model", "View", "Documentation");
	static private final List<String> KNOWN_MODEL_TYPES = List.of("Relationship", "Person", "Element", "DeploymentNode",
			"CustomElement", "SoftwareSystem");
	static private final List<String> KNOWN_VIEW_TYPES = List.of("ComponentView", "ContainerView", "CustomView",
			"DeploymentView", "DynamicView", "FilteredView", "ImageView", "SystemContextView", "SystemLandscapeView");
	static private final List<String> KNOWN_STYLE_TYPES = List.of("Style", "ElementStyle");
	static private final List<String> KNOWN_DOCUMENTATION_TYPES = List.of("Section", "Decision");

	public static final String PROPERTY_FILE = "file";

	protected Workspace workspace;
	protected String path;
	protected File file;
	private StructurizrFileType fileType;

	protected StructurizrPropertyGetter propertyGetter;
	protected StructurizrPropertySetter propertySetter;

	public StructurizrModel() {
		this.propertyGetter = new StructurizrPropertyGetter();
		this.propertySetter = new StructurizrPropertySetter();
	}

	public void setWorkspace(Workspace workspace) {
		this.workspace = workspace;
	}

	public Workspace getWorkspace() {
		return workspace;
	}

	public void setPath(String path) {
		this.path = path;
		if (path.endsWith(".json")) {
			this.fileType = StructurizrFileType.JSON;
		} else if (path.endsWith(".dsl")) {
			this.fileType = StructurizrFileType.DSL;
		} else {
			this.fileType = null;
		}
	}

	public String getPath() {
		return path;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	private List<String> getKnownTypes() {
		List<String> knownTypes = new ArrayList<>();
		knownTypes.addAll(KNOWN_TOP_LEVEL_TYPES);
		knownTypes.addAll(KNOWN_MODEL_TYPES);
		knownTypes.addAll(KNOWN_VIEW_TYPES);
		knownTypes.addAll(KNOWN_STYLE_TYPES);
		knownTypes.addAll(KNOWN_DOCUMENTATION_TYPES);
		return knownTypes;
	}

	@Override
	public boolean hasType(String type) {
		return getKnownTypes().contains(type);
	}

	@Override
	public Collection<?> getAllOfType(String type) throws EolModelElementTypeNotFoundException {
		switch (type) {
		// Top level
		case "Workspace":
			return List.of(workspace);
		case "Model":
			return List.of(workspace.getModel());
		case "View":
			return workspace.getViews().getViews();
		case "Documentation":
			return List.of(workspace.getDocumentation());

		// Model
		case "Relationship":
			return workspace.getModel().getRelationships();
		case "Person":
			return workspace.getModel().getPeople();
		case "Element":
			return workspace.getModel().getElements();
		case "DeploymentNode":
			return workspace.getModel().getDeploymentNodes();
		case "CustomElement":
			return workspace.getModel().getCustomElements();
		case "SoftwareSystem":
			return workspace.getModel().getSoftwareSystems();

		// View
		case "ComponentView":
			return workspace.getViews().getComponentViews();
		case "ContainerView":
			return workspace.getViews().getContainerViews();
		case "CustomView":
			return workspace.getViews().getCustomViews();
		case "DeploymentView":
			return workspace.getViews().getDeploymentViews();
		case "DynamicView":
			return workspace.getViews().getDynamicViews();
		case "FilteredView":
			return workspace.getViews().getFilteredViews();
		case "ImageView":
			return workspace.getViews().getImageViews();
		case "SystemContextView":
			return workspace.getViews().getSystemContextViews();
		case "SystemLandscapeView":
			return workspace.getViews().getSystemLandscapeViews();

		// Styles
		case "Style":
		case "ElementStyle":
			return workspace.getViews().getConfiguration().getStyles().getElements();

		// Documentation
		case "Section":
			return workspace.getDocumentation().getSections();
		case "Decision":
			return workspace.getDocumentation().getDecisions();

		// Unknown type
		default:
			throw new EolModelElementTypeNotFoundException(this.name, type);
		}
	}

	@Override
	public Collection<?> getAllOfKind(String type) throws EolModelElementTypeNotFoundException {
		return this.getAllOfType(type);
	}

	@Override
	public boolean owns(Object instance) {
		return true;
	}

	@Override
	public IPropertyGetter getPropertyGetter() {
		return propertyGetter;
	}

	@Override
	public IPropertySetter getPropertySetter() {
		return propertySetter;
	}

	@Override
	public Object getEnumerationValue(String enumeration, String label) throws EolEnumerationValueNotFoundException {
		throw new EolEnumerationValueNotFoundException(enumeration, label, this.name);
	}

	@Override
	public Set<?> allContents() {
		// Using a set instead of a list to avoid duplicates
		Set<Object> res = new HashSet<>();
		for (String type : getKnownTypes()) {
			try {
				res.addAll(this.getAllOfType(type));
			} catch (EolModelElementTypeNotFoundException e) {
				// This should never happen
			}
		}
		return res;
	}

	@Override
	public String getTypeNameOf(Object instance) {
		return null;
	}

	@Override
	public Object createInstance(String type)
			throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		throw new EolNotInstantiableModelElementTypeException(this.name, type);
	}

	@Override
	public Object getElementById(String id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getElementId(Object instance) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setElementId(Object instance, String newId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteElement(Object instance) throws EolRuntimeException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isInstantiable(String type) {
		return false;
	}

	@Override
	public void load(StringProperties properties, IRelativePathResolver resolver) throws EolModelLoadingException {
		super.load(properties, resolver);

		String filePath = properties.getProperty(StructurizrModel.PROPERTY_FILE);
		setPath(resolver.resolve(filePath));
		file = new File(path);

		load();
	}

	@Override
	public void load() throws EolModelLoadingException {
		if (workspace != null) {
			return;
		}

		if (path == null || StringUtil.isEmpty(path)) {
			throw new EolModelLoadingException(new EolRuntimeException("The path is not specified"), this);
		}

		try {
			if (fileType == null) {
				throw new EolRuntimeException("Unknown file extension: " + path);
			}

			if (file == null) {
				file = new File(path);
			}

			switch (fileType) {
			case JSON:
				Workspace loadedWorkspace = WorkspaceUtils.loadWorkspaceFromJson(file);
				this.workspace = loadedWorkspace;
				break;
			case DSL:
				StructurizrDslParser parser = new StructurizrDslParser();
				parser.parse(file);
				this.workspace = parser.getWorkspace();
				break;
			}

		} catch (Exception e) {
			throw new EolModelLoadingException(e, this);
		}
	}

	@Override
	public boolean store(String location) {
		if (fileType != StructurizrFileType.JSON) {
			return false;
		}

		try {
			WorkspaceUtils.saveWorkspaceToJson(workspace, file);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean store() {
		return this.store(path);
	}
}
