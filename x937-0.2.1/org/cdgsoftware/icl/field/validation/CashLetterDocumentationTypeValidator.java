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
 * The Class CashLetterDocumentationTypeValidator.
 */
public class CashLetterDocumentationTypeValidator extends Validator {
    public static final CashLetterDocumentationTypeValidator INSTANCE = new CashLetterDocumentationTypeValidator();

    /* (non-Javadoc)
     * @see org.cdgsoftware.icl.field.validation.Validator#validateData(java.lang.String)
     */
    @Override
    public ValidationResult validateData(String data) throws ICLException {
        ValidationResult v = new ValidationResult();
        data = data.toUpperCase();
        /* ucd v1 and x9.37-2003 differ on this */
        if(data.equals("C") || data.equals("G") || data.equals("K") || data.equals("L") || data.equals("Z")) {
            v.setMessage("Cash Letter Documentation Indicator is Valid");
            v.setDataValid(true);
        } else {
            v.setMessage("This Cash Letter Documentation Type Indicator is UCD v1 NOT DSTU x9.37 compliant. "
                    + "The indicator value [" + data + "] is not a valid type.");
            v.setDataValid(false);
        }
        return v;
    }

}