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
import org.eclipse.epsilon.eol.execute.introspection.AbstractPropertySetter;

import com.structurizr.PropertyHolder;

public class StructurizrPropertySetter extends AbstractPropertySetter {

	@Override
	public void invoke(Object target, String property, Object value, IEolContext context) throws EolRuntimeException {
		if (!(value instanceof String)) {
			throw new EolRuntimeException("Cannot set property " + property + " to a non-String value " + value);
		}

		String stringValue = (String) value;

		if ("name".equals(property)) {
			invokeSetterOrThrow(target, "setName", stringValue, property);
			return;
		} else if ("description".equals(property)) {
			invokeSetterOrThrow(target, "setDescription", stringValue, property);
			return;
		} else if ("tag".equals(property)) {
			invokeSetterOrThrow(target, "setTag", stringValue, property);
			return;
		}

		if (!(target instanceof PropertyHolder)) {
			throw new EolRuntimeException(
					"Cannot set property " + property + " of non-PropertyHolder object " + target);
		}

		PropertyHolder targetPropertyHolder = (PropertyHolder) target;
		targetPropertyHolder.addProperty(property, stringValue);
	}

	private void invokeSetterOrThrow(Object target, String methodName, String value, String property)
			throws EolRuntimeException {
		try {
			Method method = target.getClass().getMethod(methodName, String.class);
			method.invoke(target, value);
		} catch (NoSuchMethodException e) {
			throw new EolRuntimeException(
					"The setter " + methodName + " does not exist on class " + target.getClass().getSimpleName());
		} catch (IllegalAccessException e) {
			throw new EolRuntimeException(
					"Cannot access the setter " + methodName + " on class " + target.getClass().getSimpleName());
		} catch (InvocationTargetException e) {
			throw new EolRuntimeException(
					"Cannot invoke the setter " + methodName + " on class " + target.getClass().getSimpleName());
		}
	}
}