package org.eclipse.epsilon.emc.structurizr.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.epsilon.emc.structurizr.StructurizrModel;
import org.eclipse.epsilon.eol.EolEvaluator;
import org.junit.Before;
import org.junit.Test;

import com.structurizr.Workspace;
import com.structurizr.model.Model;
import com.structurizr.model.Person;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.model.Tags;
import com.structurizr.view.Shape;
import com.structurizr.view.Styles;
import com.structurizr.view.SystemContextView;
import com.structurizr.view.ViewSet;

public class APIWorkspaceTests {
    protected StructurizrModel m;
    protected EolEvaluator evaluator;

    @Before
    public void setup() throws Exception {
        Workspace workspace = new Workspace("Getting Started", "is a model of my software system.");
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

        m = new StructurizrModel();
        m.setName("m");
        m.setWorkspace(workspace);
        m.load();
        evaluator = new EolEvaluator(m);
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testGetWorkspace() {
        List<Workspace> workspaces = (List<Workspace>) evaluator.evaluate("m!Workspace.all()");
        assertEquals(workspaces.size(), 1);
        Workspace workspace = workspaces.get(0);
        assertEquals("Getting Started", workspace.getName());
    }
}
