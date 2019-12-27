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
 * The Class Type_Alphabetic.
 */
public class Type_Alphabetic extends FieldType {
	protected static String name = "Alphabetic";
	protected static String shortName = "A";
	protected static String description = 
		"The alphabetic characters (A) to (Z) and (a) to (z)." + 
		" There is no special consideration for upper or lower case. " + 
		" Therefore (A) and (a) should be considered identical.";
	
	public static final Type_Alphabetic INSTANCE = new Type_Alphabetic();
	
	/**
	 * Instantiates a new type_ alphabetic.
	 */
	public Type_Alphabetic() {
		super(name, shortName, description);
	}
	
	/* (non-Javadoc)
	 * @see org.cdgsoftware.icl.field.type.FieldType#isDataValid(java.lang.String)
	 */
	@Override
	public boolean isDataValid(String data) {
		String regex = "[^A-Za-z ]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(data);
		if (m.find()) {
			return false;
		}
		return true;
	}

}
