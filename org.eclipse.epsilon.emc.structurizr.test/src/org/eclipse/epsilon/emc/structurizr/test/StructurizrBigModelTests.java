package org.eclipse.epsilon.emc.structurizr.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.swing.text.ComponentView;
import javax.swing.text.View;
import javax.swing.text.html.ImageView;

import org.eclipse.epsilon.emc.structurizr.StructurizrModel;
import org.eclipse.epsilon.eol.EolEvaluator;
import org.eclipse.epsilon.eol.exceptions.EolEvaluatorException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.junit.Before;
import org.junit.Test;

import com.structurizr.Workspace;
import com.structurizr.documentation.Decision;
import com.structurizr.documentation.Documentation;
import com.structurizr.documentation.Section;
import com.structurizr.model.DeploymentNode;
import com.structurizr.model.Element;
import com.structurizr.model.Model;
import com.structurizr.model.Person;
import com.structurizr.model.Relationship;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.view.ContainerView;
import com.structurizr.view.CustomView;
import com.structurizr.view.DeploymentView;
import com.structurizr.view.DynamicView;
import com.structurizr.view.FilteredView;
import com.structurizr.view.SystemContextView;
import com.structurizr.view.SystemLandscapeView;

public class StructurizrBigModelTests extends StructurizrTests {
	protected StructurizrModel m;
	protected EolEvaluator evaluator;

	@Before
	public void setup() throws EolModelLoadingException, IOException {
		String resourcePath = getResourcePath("big-bank-plc.dsl");
		m = new StructurizrModel();
		m.setPath(resourcePath);
		m.setName("m");
		m.load();
		evaluator = new EolEvaluator(m);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetWorkspace() {
		List<Workspace> workspaces = (List<Workspace>) evaluator.evaluate("m!Workspace.all()");
		assertEquals(workspaces.size(), 1);
		Workspace workspace = workspaces.get(0);
		assertEquals("Big Bank plc", workspace.getName());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetModel() {
		List<Model> models = (List<Model>) evaluator.evaluate("m!Model.all()");
		assertEquals(models.size(), 1);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetViews() {
		Set<View> views = (Set<View>) evaluator.evaluate("m!View.all()");
		assertEquals(views.size(), 8);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetDocumentation() {
		List<Documentation> documentations = (List<Documentation>) evaluator.evaluate("m!Documentation.all()");
		assertEquals(documentations.size(), 1);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetRelationships() {
		Set<Relationship> relationships = (Set<Relationship>) evaluator.evaluate("m!Relationship.all()");
		assertEquals(42, relationships.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetPeople() {
		Set<Person> people = (Set<Person>) evaluator.evaluate("m!Person.all()");
		assertEquals(3, people.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetElements() {
		Set<Element> elements = (Set<Element>) evaluator.evaluate("m!Element.all()");
		assertEquals(51, elements.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetDeploymentNodes() {
		Set<DeploymentNode> deploymentNodes = (Set<DeploymentNode>) evaluator.evaluate("m!DeploymentNode.all()");
		assertEquals(5, deploymentNodes.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetCustomElements() {
		Set<Element> customElements = (Set<Element>) evaluator.evaluate("m!CustomElement.all()");
		assertEquals(0, customElements.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetSoftwareSystems() {
		Set<SoftwareSystem> softwareSystems = (Set<SoftwareSystem>) evaluator.evaluate("m!SoftwareSystem.all()");
		assertEquals(4, softwareSystems.size());
	}

	// View tests
	@SuppressWarnings("unchecked")
	@Test
	public void testGetComponentViews() {
		Set<ComponentView> componentViews = (Set<ComponentView>) evaluator.evaluate("m!ComponentView.all()");
		assertEquals(1, componentViews.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetContainerViews() {
		Set<ContainerView> containerViews = (Set<ContainerView>) evaluator.evaluate("m!ContainerView.all()");
		assertEquals(1, containerViews.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetCustomViews() {
		Set<CustomView> customViews = (Set<CustomView>) evaluator.evaluate("m!CustomView.all()");
		assertEquals(0, customViews.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetDeploymentViews() {
		Set<DeploymentView> deploymentViews = (Set<DeploymentView>) evaluator.evaluate("m!DeploymentView.all()");
		assertEquals(2, deploymentViews.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetDynamicViews() {
		Set<DynamicView> dynamicViews = (Set<DynamicView>) evaluator.evaluate("m!DynamicView.all()");
		assertEquals(1, dynamicViews.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetFilteredViews() {
		Set<FilteredView> filteredViews = (Set<FilteredView>) evaluator.evaluate("m!FilteredView.all()");
		assertEquals(0, filteredViews.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetImageViews() {
		Set<ImageView> imageViews = (Set<ImageView>) evaluator.evaluate("m!ImageView.all()");
		assertEquals(1, imageViews.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetSystemContextViews() {
		Set<SystemContextView> systemContextViews = (Set<SystemContextView>) evaluator
				.evaluate("m!SystemContextView.all()");
		assertEquals(1, systemContextViews.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetSystemLandscapeViews() {
		Set<SystemLandscapeView> systemLandscapeViews = (Set<SystemLandscapeView>) evaluator
				.evaluate("m!SystemLandscapeView.all()");
		assertEquals(1, systemLandscapeViews.size());
	}

	// Documentation tests
	@SuppressWarnings("unchecked")
	@Test
	public void testGetSections() {
		List<Section> sections = (List<Section>) evaluator.evaluate("m!Section.all()");
		assertEquals(0, sections.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetDecisions() {
		Set<Decision> decisions = (Set<Decision>) evaluator.evaluate("m!Decision.all()");
		assertEquals(0, decisions.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetAllOfTypeWorksAsGetAllOfKind() {
		Set<SoftwareSystem> softwareSystems = (Set<SoftwareSystem>) evaluator.evaluate("m!SoftwareSystem.all()");
		Set<SoftwareSystem> softwareSystems2 = (Set<SoftwareSystem>) evaluator
				.evaluate("m!SoftwareSystem.getAllOfType()");
		assertEquals(softwareSystems.size(), softwareSystems2.size());
	}

	// Unknown type
	@Test(expected = EolEvaluatorException.class)
	public void testUnknownType() {
		evaluator.evaluate("m!UnknownType.all()");
	}

	@Test
	public void testGetTypeNameOf() {
		assertEquals(null, m.getTypeNameOf(new Object()));
	}

	@Test(expected = EolNotInstantiableModelElementTypeException.class)
	public void testCreateInstance() throws EolRuntimeException {
		m.createInstance("type");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetElementById() {
		m.getElementById("id");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetElementId() {
		m.getElementId(new Object());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testSetElementId() {
		m.setElementId(new Object(), "newId");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testDeleteElement() throws EolRuntimeException {
		m.deleteElement(new Object());
	}

	@Test
	public void testIsInstantiable() {
		assertEquals(false, m.isInstantiable("type"));
	}

	@Test
	public void testStore() {
		assertEquals(false, m.store());
	}

	@Test(expected = EolEnumerationValueNotFoundException.class)
	public void testGetEnumerationValue() throws EolEnumerationValueNotFoundException {
		m.getEnumerationValue("enumeration", "label");
	}

	@Test
	public void testPropertyGetter() {
		String propertyValue = (String) evaluator.evaluate("m!SystemContextView.all().first().`structurizr.groups`");
		assertEquals("false", propertyValue);
	}

	@Test(expected = EolEvaluatorException.class)
	public void testPropertyGetterOverUnknownObject() {
		evaluator.evaluate("m!Workspace.all().first().property");
	}

	@Test
	public void testPropertySetter() {
		evaluator.execute("m!SystemContextView.all().first().`structurizr.groups` = 'true';");
		String propertyValue = (String) evaluator.evaluate("m!SystemContextView.all().first().`structurizr.groups`");
		assertEquals("true", propertyValue);
	}

	@Test(expected = EolEvaluatorException.class)
	public void testPropertySetterOfNonStringThrows() {
		evaluator.execute("m!SystemContextView.all().first().`structurizr.groups` = 1;");
	}

	@Test
	public void testAllContents() {
		assertEquals(115, m.allContents().size());
	}
}
