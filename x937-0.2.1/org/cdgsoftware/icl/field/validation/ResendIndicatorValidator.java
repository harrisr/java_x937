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
 * The Class ResendIndicatorValidator.
 */
public class ResendIndicatorValidator extends Validator {
    public static final ResendIndicatorValidator INSTANCE = new ResendIndicatorValidator();

    /* (non-Javadoc)
     * @see org.cdgsoftware.icl.field.validation.Validator#validateData(java.lang.String)
     */
    @Override
    public ValidationResult validateData(String data) throws ICLException {
        ValidationResult result = new ValidationResult();
        if (data.toUpperCase().equals("N")) {
            result.setMessage("Resend Indicator is valid");
            result.setDataValid(true);
        } else {
            result.setMessage("Resend Indicator [" + data
                    + "] did not pass validation check! Resend Indicator must be 'N' per UCD v1");
            result.setDataValid(false);
        }
        return result;
    }

}
