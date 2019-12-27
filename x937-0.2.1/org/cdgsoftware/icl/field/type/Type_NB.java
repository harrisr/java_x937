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
 * The Class Type_NB.
 */
public class Type_NB extends FieldType {
	protected static String name = "Numericblank";
	protected static String shortName = "NB";
	protected static String description = "Numeric and blank characters only";
	
	public static final Type_NB INSTANCE = new Type_NB();
	
	/**
	 * Instantiates a new type_ nb.
	 */
	public Type_NB() {
		super(name, shortName, description);
	}
	
	/* (non-Javadoc)
	 * @see org.cdgsoftware.icl.field.type.FieldType#isDataValid(java.lang.String)
	 */
	@Override
	public boolean isDataValid(String data) {
		String regex = "[^0-9 ]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(data);
		if (m.find()) {
			return false;
		}
		return true;
	}

}
