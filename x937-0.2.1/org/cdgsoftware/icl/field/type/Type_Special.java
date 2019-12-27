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

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * The Class Type_Special.
 */
public class Type_Special extends FieldType {
	protected static String name = "Special Characters";
	protected static String shortName = "S";
	protected static String description = 
		"Special characters are any ASCII characters > 1F or EBCDIC > 3F that are " + 
                "not numeric, alphabetic, nor blank. Hex 00 - 1F is not valid (ASCII).";
	
	public static final Type_Special INSTANCE = new Type_Special();
	
	/**
	 * Instantiates a new type_ special.
	 */
	public Type_Special() {
		super(name, shortName, description);
	}
	
	/* (non-Javadoc)
	 * @see org.cdgsoftware.icl.field.type.FieldType#isDataValid(java.lang.String)
	 */
	@Override
	public boolean isDataValid(String data) {
		char[] chars = data.toCharArray();
		for (int i=0; i<chars.length; i++) {
			char c = chars[i];
			if (c <= 0x1F) {
				return false;
			}
		}
		//check for the non... rules
		String regex = "[0-9A-Za-z ]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(data);
		if (m.find()) {
			return false;
		}
		return true;
	}

}
