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
import org.cdgsoftware.icl.util.ModTen;

/**
 * The Class RoutingNumberValidator.
 */
public class RoutingNumberValidator extends Validator {
    public static final RoutingNumberValidator INSTANCE = new RoutingNumberValidator();

    /* (non-Javadoc)
     * @see org.cdgsoftware.icl.field.validation.Validator#validateData(java.lang.String)
     */
    @Override
    public ValidationResult validateData(String aba) throws ICLException {
        ValidationResult result = new ValidationResult();
        if (ModTen.checkAba(aba)) {
            result.setMessage("Routing Number Passed Mod10 Check");
            result.setDataValid(true);
        } else if (aba.equals("111111111") || aba.equals("222222222")) {
        	//approve test routing numbers
        	result.setMessage("Routing Number Passed Test 1's and 2's Check");
        	result.setDataValid(true);
        } else {
            result.setMessage("Routing Number [" + aba + "] did not pass mod ten check!");
            result.setDataValid(false);
        }
        return result;
    }

}
