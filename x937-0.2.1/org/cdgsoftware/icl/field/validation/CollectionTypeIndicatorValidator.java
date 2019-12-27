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
 * The Class CollectionTypeIndicatorValidator.
 */
public class CollectionTypeIndicatorValidator extends Validator {
    public static final CollectionTypeIndicatorValidator INSTANCE = new CollectionTypeIndicatorValidator();

    /* (non-Javadoc)
     * @see org.cdgsoftware.icl.field.validation.Validator#validateData(java.lang.String)
     */
    @Override
    public ValidationResult validateData(String data) throws ICLException {
        ValidationResult v = new ValidationResult();
        LeftPadder padder = LeftPadder.ZERO_PADDER;
        /* allowed values from x9.37-2003 */
        //String[] allowedValues = { "00", "01", "02", "03", "04", "05", "06", "10", "20", "99" };
        /* UCD v1 only allows '01' and '03' */
        
        /* if the user only sent int a single char pad it */
        if (data.length() < 2) {
            try {
                data = padder.pad(data, 2);
            } catch (ISOException e) {
                throw new ICLException("Exception Caught", e);
            }
        }
        if(data.equals("01") || data.equals("03")) {
            v.setMessage("Collection Type Indicator is Valid");
            v.setDataValid(true);
        } else {
            v.setMessage("This Collection Type Indicator is UCD v1 NOT DSTU x9.37 compliant. "
                    + "The collection type indicator value [" + data + "] is not a valid type.");
            v.setDataValid(false);
        }
        return v;
    }

}
