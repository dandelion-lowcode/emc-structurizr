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

import org.eclipse.epsilon.eol.EolModule;

import com.structurizr.Workspace;
import com.structurizr.model.Model;
import com.structurizr.model.Person;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.model.Tags;
import com.structurizr.view.Shape;
import com.structurizr.view.Styles;
import com.structurizr.view.SystemContextView;
import com.structurizr.view.ViewSet;

public class App {
	public static void main(String[] args) throws Exception {
		// Example from https://docs.structurizr.com/java/getting-started
		Workspace workspace = new Workspace("Getting Started", "This is a model of my software system.");

		Model model = workspace.getModel();
		Person user = model.addPerson("User", "A user of my software system.");
		SoftwareSystem softwareSystem = model.addSoftwareSystem("Software System", "My software system.");
		user.uses(softwareSystem, "Uses");

		ViewSet views = workspace.getViews();
		SystemContextView contextView = views.createSystemContextView(softwareSystem, "SystemContext",
				"An example of a System Context diagram.");
		contextView.addAllSoftwareSystems();
		contextView.addAllPeople();

		Styles styles = views.getConfiguration().getStyles();
		styles.addElementStyle(Tags.SOFTWARE_SYSTEM).background("#1168bd").color("#ffffff");
		styles.addElementStyle(Tags.PERSON).background("#08427b").color("#ffffff").shape(Shape.Person);

		// EOL usage example
		StructurizrModel m = new StructurizrModel();
		m.setName("m");
		m.setWorkspace(workspace);

		EolModule module = new EolModule();
		module.getContext().getModelRepository().addModel(m);
		module.parse("var workspace = Workspace.all().first();\n" 
				+ "var views = View.all();\n"
				+ "var styles = Style.all();\n"
				+ "\n"
				+ "('The workspace is called ' + workspace.name).println();\n"
				+ "workspace.name = 'Draft Workspace';\n"
				+ "('After changing its name, it is now called ' + workspace.name).println();\n"
				+ "''.println();"
				+ "\n"
				+ "('There are ' + views.size() + ' views: ' + views.name.concat(', ')).println();\n"
				+ "('There are ' + styles.size() + ' styles defined over: ' + styles.tag.concat(', ')).println();"
		);
		module.execute();
		
		// You can also configure the EMC driver
		// by directly importing a model in a *.dsl or *.json file:
		// 
		// StructurizrModel m = new StructurizrModel();
		// m.setName("m");
		// m.setFile(new File("..."));
	}
}