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
package org.cdgsoftware.icl.field;

import org.jpos.iso.LeftPadder;

import org.cdgsoftware.icl.field.type.Type_Numeric;
import org.cdgsoftware.icl.field.validation.Validator;
import org.cdgsoftware.icl.util.ICLException;


/**
 * The Class GenericNField.
 */
public class GenericNField extends Field {

    /**
     * Instantiates a new generic n field.
     * 
     * @param position the position
     */
    public GenericNField(Position position) {
        initialize();
        this.position = position;
    }

    /**
     * Instantiates a new generic n field.
     * 
     * @param position the position
     * @param fieldValidator the field validator
     * @param fieldData the field data
     * 
     * @throws ICLException the ICL exception
     */
    public GenericNField(Position position, Validator fieldValidator, String fieldData) throws ICLException {
        initialize();
        this.position = position;
        this.fieldValidator = fieldValidator;
        setFieldData(fieldData);
    }

    /**
     * Instantiates a new generic n field.
     * 
     * @param position the position
     * @param fieldName the field name
     */
    public GenericNField(Position position, String fieldName) {
        initialize();
        this.position = position;
        this.fieldName = fieldName;
    }

    /**
     * Instantiates a new generic n field.
     * 
     * @param position the position
     * @param fieldValidator the field validator
     * @param fieldData the field data
     * @param fieldName the field name
     * 
     * @throws ICLException the ICL exception
     */
    public GenericNField(Position position, Validator fieldValidator, String fieldData, String fieldName)
            throws ICLException {
        initialize();
        this.fieldName = fieldName;
        this.position = position;
        this.fieldValidator = fieldValidator;
        setFieldData(fieldData);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.cdgsoftware.icl.field.Field#initialize()
     */
    @Override
    protected void initialize() {
        this.fieldName = "Generic Numeric Field";
        this.fieldType = Type_Numeric.INSTANCE;
        this.usage = Usage.MANDATORY;
        this.validationCriteria = ValidationCriteria.NONE;
        this.padder = LeftPadder.ZERO_PADDER;
    }

}