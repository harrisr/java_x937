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
import org.cdgsoftware.icl.field.GenericANSField;
import org.cdgsoftware.icl.field.Position;
import org.cdgsoftware.icl.field.ValidationCriteria;
import org.cdgsoftware.icl.field.validation.AuxOnUsValidator;
import org.cdgsoftware.icl.util.RightJustifySpacePadder;

/**
 * The Class AuxOnUsFieldTest.
 */
public class AuxOnUsFieldTest extends TestCase {
	private Field field;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() {
		field = new GenericANSField(new Position(1,15));
		field.setFieldValidator(AuxOnUsValidator.INSTANCE);
		field.setPadder(RightJustifySpacePadder.SPACE_PADDER);
		field.setValidationCriteria(ValidationCriteria.REQUIRED);
	}
	

	/**
	 * Test valid data.
	 * 
	 * @throws Exception the exception
	 */
	public void testValidData() throws Exception {
		field.setFieldData("0123456789");
		String fieldData = field.getFieldData();
		assertEquals("     0123456789", fieldData);
	}

	/**
	 * Test valid data2.
	 * 
	 * @throws Exception the exception
	 */
	public void testValidData2() throws Exception {
		field.setFieldData("     9998100466");
		String fieldData = field.getFieldData();
		assertEquals("     9998100466", fieldData);
	}
	

	/**
	 * Test valid data3.
	 * 
	 * @throws Exception the exception
	 */
	public void testValidData3() throws Exception {
		field.setFieldData(" *23456789");
		String fieldData = field.getFieldData();
		assertEquals("      *23456789", fieldData);
	}
	
	/**
	 * Test invlaid data.
	 * 
	 * @throws Exception the exception
	 */
	public void testInvlaidData() throws Exception {
		try {
			field.setFieldData("123/56789");
			fail("Adding an invalid value should generate an exception");
		} catch (Exception asExpected) {
		}
	}
}