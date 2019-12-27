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
 * The Class StandardLevelValidator.
 */
public class StandardLevelValidator extends Validator {
    public static final StandardLevelValidator INSTANCE = new StandardLevelValidator();

    /* (non-Javadoc)
     * @see org.cdgsoftware.icl.field.validation.Validator#validateData(java.lang.String)
     */
    @Override
    public ValidationResult validateData(String data) throws ICLException {
        ValidationResult result = new ValidationResult();
        LeftPadder padder = LeftPadder.ZERO_PADDER;
        if (data.length() < 2) {
            try {
               data = padder.pad(data, 2);
            } catch (ISOException e) {
                throw new ICLException("Error padding Standard Level Field", e);
            }
        } 
        if (data.equals("01") || data.equals("02") || data.equals("03")) {
            result.setMessage("Standard Level is valid");
            result.setDataValid(true);
        } else {
            result.setMessage("Standard Level [" + data + "] did not pass validation check!");
            result.setDataValid(false);
        }
        return result;
    }

}
