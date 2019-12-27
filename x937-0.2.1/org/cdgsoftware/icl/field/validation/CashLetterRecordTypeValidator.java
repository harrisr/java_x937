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
 * The Class CashLetterRecordTypeValidator.
 */
public class CashLetterRecordTypeValidator extends Validator {
    public static final CashLetterRecordTypeValidator INSTANCE = new CashLetterRecordTypeValidator();

    /* (non-Javadoc)
     * @see org.cdgsoftware.icl.field.validation.Validator#validateData(java.lang.String)
     */
    @Override
    public ValidationResult validateData(String data) throws ICLException {
        ValidationResult v = new ValidationResult();
        data = data.toUpperCase();
        if(data.equals("I") || data.equals("N") || data.equals("E") || data.equals("F")) {
            v.setMessage("Cash Letter Record Indicator is Valid");
            v.setDataValid(true);
        } else {
            v.setMessage("This Cash Letter Record Type Indicator is UCD v1 and DSTU x9.37 compliant. "
                    + "The indicator value [" + data + "] is not a valid type.");
            v.setDataValid(false);
        }
        return v;
    }

}
