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
import org.eclipse.epsilon.eol.execute.introspection.java.JavaPropertyGetter;

import com.structurizr.PropertyHolder;

public class StructurizrPropertyGetter extends JavaPropertyGetter {

	@Override
	public Object invoke(Object object, String property, IEolContext context) throws EolRuntimeException {
		try {
			return super.invoke(object, property, context);
		} catch (EolRuntimeException e) {
			if (!(object instanceof PropertyHolder)) {
				throw new EolRuntimeException(
						"Cannot get property " + property + " of non-PropertyHolder object " + object);
			}

			PropertyHolder propertyHolder = (PropertyHolder) object;
			if (!propertyHolder.getProperties().containsKey(property)) {
				throw new EolRuntimeException(
						"Cannot find property " + property + " in PropertyHolder " + propertyHolder);
			}
			return propertyHolder.getProperties().get(property);
		}

	}
}