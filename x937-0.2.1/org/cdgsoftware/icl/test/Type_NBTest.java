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

import org.cdgsoftware.icl.field.type.Type_NB;


/**
 * The Class Type_NBTest.
 */
public class Type_NBTest extends TestCase {
	private Type_NB fieldType;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() {
		fieldType = new Type_NB();
	}
	
	/**
	 * Test valid data.
	 * 
	 * @throws Exception the exception
	 */
	public void testValidData() throws Exception {
		assertEquals(true, fieldType.isDataValid("   2315648"));
	}
	
	/**
	 * Test invalid data symbol.
	 * 
	 * @throws Exception the exception
	 */
	public void testInvalidDataSymbol() throws Exception {
		assertEquals(false, fieldType.isDataValid("111*222"));
	}
	
	/**
	 * Test invalid data alpha.
	 * 
	 * @throws Exception the exception
	 */
	public void testInvalidDataAlpha() throws Exception {
		assertEquals(false, fieldType.isDataValid("Az"));
	}

	/**
	 * Test invalid data forbidden char.
	 * 
	 * @throws Exception the exception
	 */
	public void testInvalidDataForbiddenChar() throws Exception {
		char c = 0x02;
		String s = String.valueOf(c);
		assertEquals(false, fieldType.isDataValid(s));
	}

}