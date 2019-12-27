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
 * The Class Type_NS.
 */
public class Type_NS extends FieldType {
	protected static String name = "Numeric/special";
	protected static String shortName = "NS";
	protected static String description = "Numeric and Special characters. "
			+ "Special characters are any ASCII characters > 1F or EBCDIC > 3F that are "
			+ "not numeric, alphabetic, nor blank. Hex 00 - 1F is not valid (ASCII).";
	
	public static final Type_NS INSTANCE = new Type_NS();

	/**
	 * Instantiates a new type_ ns.
	 */
	public Type_NS() {
		super(name, shortName, description);
	}

	/* (non-Javadoc)
	 * @see org.cdgsoftware.icl.field.type.FieldType#isDataValid(java.lang.String)
	 */
	@Override
	public boolean isDataValid(String data) {
		char[] chars = data.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c < 0x21) {
				// block space and non allowed lower value chars
				return false;
			} else if (c >= 0x41 && c <= 0x5A) {
				// block Upper Case A-Z
				return false;
			} else if (c >= 0x61 && c <= 0x7A) {
				// block Lower Case a-z
				return false;
			}
		}
		return true;
	}
}