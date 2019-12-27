/*
 * CDG Software
 * Copyright (C) 2010 Jeff Gordy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.cdgsoftware.icl.test;

import junit.framework.TestCase;

import org.cdgsoftware.icl.field.Field;
import org.cdgsoftware.icl.field.GenericNField;
import org.cdgsoftware.icl.field.Position;
import org.cdgsoftware.icl.field.ValidationCriteria;
import org.cdgsoftware.icl.field.validation.RoutingNumberValidator;


/**
 * The Class RoutingNumberFieldTest.
 */
public class RoutingNumberFieldTest extends TestCase {
	private Field field;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() {
		field = new GenericNField(new Position(6,14));
		field.setFieldValidator(RoutingNumberValidator.INSTANCE);
		field.setValidationCriteria(ValidationCriteria.REQUIRED);
	}
	
	
	/**
	 * Test valid data.
	 * 
	 * @throws Exception the exception
	 */
	public void testValidData() throws Exception {
		field.setFieldData("122000247");
		String fieldData = field.getFieldData();
		assertEquals("122000247", fieldData);
	}
	
	/**
	 * Test arg constructor.
	 * 
	 * @throws Exception the exception
	 */
	public void testArgConstructor() throws Exception {
		field = new GenericNField(new Position(6,14), RoutingNumberValidator.INSTANCE, "999999992", "DEST");
		String fieldData = field.getFieldData();
		assertEquals("999999992", fieldData);
	}
	
	
	/**
	 * Test invlaid data.
	 * 
	 * @throws Exception the exception
	 */
	public void testInvlaidData() throws Exception {
		try {
			field.setFieldData("123456789");
			fail("Adding an invalid value should generate an exception");
		} catch (Exception asExpected) {
		}
	}
	
	/**
	 * Test blank data.
	 * 
	 * @throws Exception the exception
	 */
	public void testBlankData() throws Exception {
		try {
			field.setFieldData("");
			fail("Adding an empty string should generate an exception");
		} catch (Exception asExpected) {
		}
	}

}