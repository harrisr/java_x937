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
 * The Class AuxOnUsValidator.
 */
public class AuxOnUsValidator extends Validator {
	public static final AuxOnUsValidator INSTANCE = new AuxOnUsValidator();

	/**
	 * Checks if is data valid.
	 * 
	 * @param data the data
	 * 
	 * @return true, if is data valid
	 */
	private boolean isDataValid(String data) {
		char[] chars = data.toCharArray();
		for (int i = chars.length - 1; i >= 0; --i) {
			char c = chars[i];
			if (c == 0x2A || c == 0x20 || c == 0x2D || (c >= 0x30 && c <= 0x39)) {
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
            v.setMessage("Auxiliary On Us is Valid");
            v.setDataValid(true);
        } else {
            v.setMessage("Auxiliary On Us [" + data + "] did not pass validation check!");
            v.setDataValid(false);
        }
		return v;
	}

}
