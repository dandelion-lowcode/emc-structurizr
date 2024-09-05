package org.eclipse.epsilon.emc.structurizr.test;

import static org.junit.Assert.assertEquals;

import org.eclipse.epsilon.emc.structurizr.StructurizrModel;
import org.eclipse.epsilon.eol.EolEvaluator;
import org.junit.Before;
import org.junit.Test;

public class SetterTests {
    protected StructurizrModel m;
    protected EolEvaluator evaluator;

    @Before
    public void setup() throws Exception {
        String resourcePath = getResourcePath("big-bank-plc.dsl");
        m = new StructurizrModel();
        m.setPath(resourcePath);
        m.setName("m");
        m.load();
        evaluator = new EolEvaluator(m);
    }

    @Test
    public void testCanSetName() {
        evaluator.execute("Workspace.all().first().name = 'New Name';");
        String name = (String) evaluator.evaluate("Workspace.all().first().name");
        assertEquals("New Name", name);
    }

    @Test
    public void testCanSetDescription() {
        evaluator.execute("Workspace.all().first().description = 'New Description';");
        String description = (String) evaluator.evaluate("Workspace.all().first().description");
        assertEquals("New Description", description);
    }

    @Test
    public void testCanSetTag() {
        evaluator.execute("Style.all().first().tag = 'New Tag';");
        String tag = (String) evaluator.evaluate("Style.all().first().tag");
        assertEquals("New Tag", tag);
    }

    private String getResourcePath(String resourceName) {
        return getClass().getClassLoader().getResource(resourceName).getPath();
    }
}