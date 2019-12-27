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
 * The Class ReturnsIndicatorValidator.
 */
public class ReturnsIndicatorValidator extends Validator {
    public static final ReturnsIndicatorValidator INSTANCE = new ReturnsIndicatorValidator();

    /* (non-Javadoc)
     * @see org.cdgsoftware.icl.field.validation.Validator#validateData(java.lang.String)
     */
    @Override
    public ValidationResult validateData(String data) throws ICLException {
        ValidationResult v = new ValidationResult();
        data = data.toUpperCase();
        if(data.equals("E") || data.equals("R") || data.equals(" ")) {
            v.setMessage("Returns Indicator is Valid");
            v.setDataValid(true);
        } else {
            v.setMessage("This Returns Indicator is not UCD v1 or FRB 2003 compliant. "
                    + "The indicator value [" + data + "] is not a valid type.");
            v.setDataValid(false);
        }
        return v;
    }

}