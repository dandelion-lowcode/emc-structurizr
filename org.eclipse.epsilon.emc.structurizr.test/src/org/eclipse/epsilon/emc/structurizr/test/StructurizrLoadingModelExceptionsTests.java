package org.eclipse.epsilon.emc.structurizr.test;

import java.io.IOException;

import org.eclipse.epsilon.emc.structurizr.StructurizrModel;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.junit.Test;

public class StructurizrLoadingModelExceptionsTests extends StructurizrTests {
	@Test(expected = EolRuntimeException.class)
	public void testLoadingAnUnknownFileFormatThrows() throws EolModelLoadingException, IOException {
		String resourcePath = getResourcePath("hello.txt");
		StructurizrModel m = new StructurizrModel();
		m.setPath(resourcePath);
		m.load();
		m.close();
	}

	@Test(expected = EolRuntimeException.class)
	public void testLoadingANonParseableFileThrows() throws EolModelLoadingException, IOException {
		String resourcePath = getResourcePath("broken.dsl");
		StructurizrModel m = new StructurizrModel();
		m.setPath(resourcePath);
		m.load();
		m.close();
	}

	@Test
	public void testCanLoadJson() throws EolModelLoadingException, IOException {
		String resourcePath = getResourcePath("workspace.json");
		StructurizrModel m = new StructurizrModel();
		m.setPath(resourcePath);
		m.load();
		m.close();
	}

	@Test(expected = EolModelLoadingException.class)
	public void testThrowsIfThePathIsNotSpecified() throws EolRuntimeException {
		StructurizrModel m = new StructurizrModel();
		m.load();
		m.close();
	}
}
