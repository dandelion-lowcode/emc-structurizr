package org.eclipse.epsilon.emc.structurizr.test;

import static org.junit.Assert.assertEquals;

import org.eclipse.epsilon.emc.structurizr.StructurizrModel;
import org.eclipse.epsilon.eol.EolEvaluator;
import org.eclipse.epsilon.eol.exceptions.EolEvaluatorException;
import org.junit.Before;
import org.junit.Test;

public class StructurizrModelGetterTests extends StructurizrTests {
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
	public void testCanGetName() {
		String name = (String) evaluator.evaluate("Workspace.all().first().name");
		assertEquals("Big Bank plc", name);
	}

	@Test
	public void testCanGetDescription() {
		String description = (String) evaluator.evaluate("Workspace.all().first().description");
		assertEquals("This is an example workspace to illustrate the key features of Structurizr, "
				+ "via the DSL, based around a fictional online banking system.", description);
	}

	@Test
	public void testCanGetTag() {
		String tag = (String) evaluator.evaluate("Style.all().first().tag");
		assertEquals("Person", tag);
	}

	@Test(expected = EolEvaluatorException.class)
	public void testThrowsIfPropertyNotFound() {
		evaluator.evaluate("Workspace.all().first().foo");
	}
}
