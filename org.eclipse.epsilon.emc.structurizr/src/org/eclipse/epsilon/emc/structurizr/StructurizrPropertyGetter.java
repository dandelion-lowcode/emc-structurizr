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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.AbstractPropertyGetter;

import com.structurizr.PropertyHolder;

public class StructurizrPropertyGetter extends AbstractPropertyGetter {

	@Override
	public Object invoke(Object object, String property, IEolContext context) throws EolRuntimeException {
		if ("name".equals(property)) {
			return invokeGetterOrThrow(object, "getName", property);
		} else if ("description".equals(property)) {
			return invokeGetterOrThrow(object, "getDescription", property);
		} else if ("tag".equals(property)) {
			return invokeGetterOrThrow(object, "getTag", property);
		}

		if (!(object instanceof PropertyHolder)) {
			throw new EolRuntimeException(
					"Cannot get property " + property + " of non-PropertyHolder object " + object);
		}

		PropertyHolder propertyHolder = (PropertyHolder) object;
		if (!propertyHolder.getProperties().containsKey(property)) {
			throw new EolRuntimeException("Property " + property + " not found in PropertyHolder " + propertyHolder);
		}

		return propertyHolder.getProperties().get(property);
	}

	private Object invokeGetterOrThrow(Object target, String methodName, String property) throws EolRuntimeException {
		try {
			Method method = target.getClass().getMethod(methodName);
			return method.invoke(target);
		} catch (NoSuchMethodException e) {
			throw new EolRuntimeException(
					"The getter " + methodName + " does not exist on class " + target.getClass().getSimpleName());
		} catch (IllegalAccessException e) {
			throw new EolRuntimeException(
					"Cannot access the getter " + methodName + " on class " + target.getClass().getSimpleName());
		} catch (InvocationTargetException e) {
			throw new EolRuntimeException(
					"Cannot invoke the getter " + methodName + " on class " + target.getClass().getSimpleName());
		}
	}
}