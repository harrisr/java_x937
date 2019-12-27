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
import org.cdgsoftware.icl.field.validation.RecordTypeValidator;


/**
 * The Class RecordTypeFieldTest.
 */
public class RecordTypeFieldTest extends TestCase {
	private Field recordType;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() {
		recordType = new GenericNField(new Position(1,2));
		recordType.setFieldValidator(RecordTypeValidator.INSTANCE);
		recordType.setValidationCriteria(ValidationCriteria.REQUIRED);
	}
	
	/**
	 * Test valid data.
	 * 
	 * @throws Exception the exception
	 */
	public void testValidData() throws Exception {
		recordType.setFieldData("01");
		String fieldData = recordType.getFieldData();
		assertEquals("01", fieldData);
	}
	
	/**
	 * Test valid data short.
	 * 
	 * @throws Exception the exception
	 */
	public void testValidDataShort() throws Exception {
		recordType.setFieldData("1");
		String fieldData = recordType.getFieldData();
		assertEquals("01", fieldData);
	}
	
	/**
	 * Test valid invalid data empty.
	 * 
	 * @throws Exception the exception
	 */
	public void testValidInvalidDataEmpty() throws Exception {
		try {
			recordType.setFieldData("");
			fail("Adding a null record type should throw and exception");
		} catch (Exception asExpected) {
		}
	}
	
	/**
	 * Test valid invalid record type.
	 * 
	 * @throws Exception the exception
	 */
	public void testValidInvalidRecordType() throws Exception {
		try {
			recordType.setFieldData("02");
			fail("Adding a non UCD v1/x9.37 2003 compliant record type should throw and exception");
		} catch (Exception asExpected) {
		}
	}
	
	/**
	 * Test invalid data long.
	 * 
	 * @throws Exception the exception
	 */
	public void testInvalidDataLong() throws Exception {
		try {
			recordType.setFieldData("001");
			fail("Adding a record type longer than 2 numbers should fail");
		} catch (Exception asExpected) {
		}
	}

}
