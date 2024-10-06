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

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.java.JavaPropertySetter;

import com.structurizr.PropertyHolder;

public class StructurizrPropertySetter extends JavaPropertySetter {

	@Override
	public void invoke(Object target, String property, Object value, IEolContext context) throws EolRuntimeException {
		try {
			super.invoke(target, property, value, context);
		} catch (EolRuntimeException e) {
			if (!(target instanceof PropertyHolder)) {
				throw new EolRuntimeException(
						"Cannot set property " + property + " of non-PropertyHolder object " + target);
			}

			if (!(value instanceof String)) {
				throw new EolRuntimeException("Cannot set property " + property + " to a non-String value");
			}

			String stringValue = (String) value;
			PropertyHolder targetPropertyHolder = (PropertyHolder) target;
			targetPropertyHolder.addProperty(property, stringValue);
		}
	}
}