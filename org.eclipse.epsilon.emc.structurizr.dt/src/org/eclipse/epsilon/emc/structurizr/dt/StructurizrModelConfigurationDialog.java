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
package org.eclipse.epsilon.emc.structurizr.dt;

import org.eclipse.epsilon.common.dt.launching.dialogs.AbstractModelConfigurationDialog;
import org.eclipse.epsilon.common.dt.util.DialogUtil;
import org.eclipse.epsilon.emc.structurizr.StructurizrModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class StructurizrModelConfigurationDialog extends AbstractModelConfigurationDialog {
	protected String getModelName() {
		return "Structurizr Model";
	}

	protected String getModelType() {
		return "Structurizr";
	}

	protected Label fileAdmonition;
	protected Label fileTextLabel;
	protected Text fileText;
	protected Button browseModelFile;

	protected void createGroups(Composite control) {
		super.createGroups(control);
		createFilesGroup(control);
		createLoadStoreOptionsGroup(control);
	}

	protected Composite createFilesGroup(Composite parent) {
		final Composite groupContent = DialogUtil.createGroupContainer(parent, "Files/URIs", 3);

		fileAdmonition = new Label(groupContent, SWT.NONE);
		GridData admonitionGridData = new GridData(GridData.FILL_HORIZONTAL);
		admonitionGridData.horizontalSpan = 3;
		fileAdmonition.setLayoutData(admonitionGridData);
		fileAdmonition.setText("Please note that only *.json files can be stored on disposal");

		GridData filebasedButtonGridData = new GridData(GridData.FILL_HORIZONTAL);
		filebasedButtonGridData.horizontalSpan = 3;

		fileTextLabel = new Label(groupContent, SWT.NONE);
		fileTextLabel.setText("File: ");

		fileText = new Text(groupContent, SWT.BORDER);
		fileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		browseModelFile = new Button(groupContent, SWT.NONE);
		browseModelFile.setText("Browse Workspace...");
		browseModelFile.addListener(SWT.Selection, new BrowseWorkspaceForModelsListener(fileText,
				"Structurizr models in the workspace", "Select a Structurizr model (DSL or JSON)"));

		groupContent.layout();
		groupContent.pack();
		return groupContent;
	}

	protected void loadProperties() {
		super.loadProperties();
		if (properties == null)
			return;
		fileText.setText(properties.getProperty(StructurizrModel.PROPERTY_FILE));
	}

	protected void storeProperties() {
		super.storeProperties();
		properties.put(StructurizrModel.PROPERTY_FILE, fileText.getText());
	}
}