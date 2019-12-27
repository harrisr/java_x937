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
package org.cdgsoftware.icl.field.validation;


import org.cdgsoftware.icl.util.ICLException;

/**
 * The Class OnUsValidator.
 */
public class OnUsValidator extends Validator {
	public static final OnUsValidator INSTANCE = new OnUsValidator();

	/* This is basically the same as the AuxOnUs validator, but we 
	 * include support for forward slashes '/' which replace the 
	 * on-us symbols in the micr line
	 */
	/**
	 * Checks if is data valid.
	 * 
	 * @param data the data
	 * 
	 * @return true, if is data valid
	 */
	private boolean isDataValid(String data) {
		char[] chars = data.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c == 0x2A || c == 0x20 || c == 0x2F || c == 0x2D ||
					(c >= 0x30 && c <= 0x39)) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see org.cdgsoftware.icl.field.validation.Validator#validateData(java.lang.String)
	 */
	@Override
	public ValidationResult validateData(String data) throws ICLException {
        ValidationResult v = new ValidationResult();
        if (isDataValid(data)) {
            v.setMessage("On Us is Valid");
            v.setDataValid(true);
        } else {
            v.setMessage("On Us [" + data + "] did not pass validation check!");
            v.setDataValid(false);
        }
		return v;
	}

}
