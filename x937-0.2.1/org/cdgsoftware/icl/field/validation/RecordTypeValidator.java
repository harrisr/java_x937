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

import org.jpos.iso.ISOException;
import org.jpos.iso.LeftPadder;

import org.cdgsoftware.icl.util.ICLException;

/**
 * The Class RecordTypeValidator.
 */
public class RecordTypeValidator extends Validator {
	public static final RecordTypeValidator INSTANCE = new RecordTypeValidator();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cdgsoftware.icl.field.validation.Validator#validateData(java.lang
	 * .String)
	 */
	@Override
	public ValidationResult validateData(String data) throws ICLException {
		ValidationResult result = new ValidationResult();
		LeftPadder padder = LeftPadder.ZERO_PADDER;
		String[] allowedValues = { "01", "10", "20", "25", "26", "27", "28", "31", "32", "33", "34", "35", "40", "41", "50",
				"52", "54", "61", "70", "75", "85", "90", "99" };
		if (data.length() < 2) {
			try {
				data = padder.pad(data, 2);
			} catch (ISOException e) {
				throw new ICLException("Error padding Standard Level Field", e);
			}
		}
		boolean matched = false;
		for (int i = 0; i < allowedValues.length; i++) {
			if (data.equals(allowedValues[i])) {
				matched = true;
				break;
			}
		}
		if (matched) {
			result.setMessage("Record Type is valid");
			result.setDataValid(true);
		} else {
			result.setMessage("This Record Type is UCD v1 and DSTU x9.37 compliant. " + "The record type value [" + data
					+ "] is not a valid type. You can subclass" + "Field yourself to allow your record type to be set.");
			result.setDataValid(false);
		}
		return result;
	}

}