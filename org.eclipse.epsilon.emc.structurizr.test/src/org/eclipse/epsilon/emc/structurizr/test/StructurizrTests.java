package org.eclipse.epsilon.emc.structurizr.test;

import java.io.IOException;

import org.eclipse.epsilon.common.util.FileUtil;

abstract public class StructurizrTests {
	protected String getResourcePath(String resourceName) throws IOException {
		return FileUtil.getFileStandalone("resources/" + resourceName, StructurizrTests.class).getAbsolutePath();
	}
}
