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
import org.cdgsoftware.icl.util.EasternStandardTime;

/**
 * The Class DateFieldTest.
 */
public class DateFieldTest extends TestCase {
	private Field field;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() {
		try {
			field = new GenericNField(new Position(21,31));
			field.setFieldData(EasternStandardTime.getCurrentDateInEST());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test date loaded.
	 * 
	 * @throws Exception the exception
	 */
	public void testDateLoaded() throws Exception {
		String estDate = field.getFieldData();
		System.out.println(estDate);
		assertNotNull(estDate);
	}
}