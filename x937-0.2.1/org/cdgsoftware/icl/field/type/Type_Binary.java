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
package org.cdgsoftware.icl.field.type;

/**
 * The Class Type_Binary.
 */
public class Type_Binary extends FieldType {
	protected static String name = "Binary";
	protected static String shortName = "BINARY";
	protected static String description = 
		"Binary data is a sequence of bytes where each byte is 8-bit encoded";
	
	public static final Type_Binary INSTANCE = new Type_Binary();
	
	/**
	 * Instantiates a new type_ binary.
	 */
	public Type_Binary() {
		super(name, shortName, description);
	}
	
	/* (non-Javadoc)
	 * @see org.cdgsoftware.icl.field.type.FieldType#isDataValid(java.lang.String)
	 */
	@Override
	public boolean isDataValid(String data) {
		return true;
	}

}
