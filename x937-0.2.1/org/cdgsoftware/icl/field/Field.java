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

import java.io.PrintStream;

import org.jpos.iso.ISOException;
import org.jpos.iso.Padder;
import org.jpos.util.Loggeable;

import org.cdgsoftware.icl.field.type.FieldType;
import org.cdgsoftware.icl.field.validation.ValidationResult;
import org.cdgsoftware.icl.field.validation.Validator;
import org.cdgsoftware.icl.util.ICLException;

/**
 * The Class Field.
 */
public abstract class Field implements Loggeable {
	protected String fieldName;
	protected String validationCriteria;
	protected String usage;
	protected Position position;
	protected FieldType fieldType;
	protected String fieldData;
	protected byte[] fieldDataBytes;
	protected Padder padder;
	protected Validator fieldValidator;
	protected boolean validated = false;

	/**
	 * Initialize.
	 */
	protected abstract void initialize();

	/**
	 * Gets the field name.
	 * 
	 * @return the field name
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * Sets the field name.
	 * 
	 * @param fieldName
	 *            the new field name
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * Gets the validation criteria.
	 * 
	 * @return the validation criteria
	 */
	public String getValidationCriteria() {
		return validationCriteria;
	}

	/**
	 * Sets the validation criteria.
	 * 
	 * @param validationCriteria
	 *            the new validation criteria
	 */
	public void setValidationCriteria(String validationCriteria) {
		this.validationCriteria = validationCriteria;
	}

	/**
	 * Gets the usage.
	 * 
	 * @return the usage
	 */
	public String getUsage() {
		return usage;
	}

	/**
	 * Sets the usage.
	 * 
	 * @param usage
	 *            the new usage
	 */
	public void setUsage(String usage) {
		this.usage = usage;
	}

	/**
	 * Gets the position.
	 * 
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Sets the position.
	 * 
	 * @param position
	 *            the new position
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * Gets the field type.
	 * 
	 * @return the field type
	 */
	public FieldType getFieldType() {
		return fieldType;
	}

	/**
	 * Sets the field type.
	 * 
	 * @param fieldType
	 *            the new field type
	 */
	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}

	/**
	 * Gets the field data.
	 * 
	 * @return the field data
	 */
	public String getFieldData() {
		return fieldData;
	}

	/**
	 * Gets the padder.
	 * 
	 * @return the padder
	 */
	public Padder getPadder() {
		return padder;
	}

	/**
	 * Sets the padder.
	 * 
	 * @param padder
	 *            the new padder
	 */
	public void setPadder(Padder padder) {
		this.padder = padder;
	}

	/**
	 * Gets the field validator.
	 * 
	 * @return the field validator
	 */
	public Validator getFieldValidator() {
		return fieldValidator;
	}

	/**
	 * Sets the field validator.
	 * 
	 * @param fieldValidator
	 *            the new field validator
	 */
	public void setFieldValidator(Validator fieldValidator) {
		this.fieldValidator = fieldValidator;
	}

	/**
	 * Checks if is validated.
	 * 
	 * @return true, if is validated
	 */
	public boolean isValidated() {
		return validated;
	}

	/**
	 * Gets the field data bytes.
	 * 
	 * @return the field data bytes
	 */
	public byte[] getFieldDataBytes() {
		if (fieldDataBytes != null) {
			return fieldDataBytes;
		}
		return fieldData.getBytes();
	}

	/**
	 * Sets the field data bytes.
	 * 
	 * @param b
	 *            the new field data bytes
	 */
	public void setFieldDataBytes(byte[] b) {
		this.fieldDataBytes = b;
	}

	/**
	 * Sets the field data.
	 * 
	 * @param fieldData
	 *            the new field data
	 * 
	 * @return true, if sets the field data
	 * 
	 * @throws ICLException
	 *             the ICL exception
	 */
	public boolean setFieldData(String fieldData) throws ICLException {
		try {
			/* as a base measure make sure the data isn't longer than the field */
			if (validLength(fieldData)) {
				/*
				 * do we require validation separate from the generic type
				 * validation?
				 */
				if (validationCriteria.equals(ValidationCriteria.REQUIRED)
						|| (!fieldData.trim().equals("") && validationCriteria
								.equals(ValidationCriteria.REQUIRED_IF_PRESENT))) {
					if (validateData(fieldData)) {
						/* set the field */
						commitFieldData(fieldData);
					} else {
						throw new ICLException("Field Data for field: " + fieldName
								+ " was is not valid for type: " + fieldType.getName()
								+ " data passed: [" + fieldData + "]");
					}
				} else {
					/*
					 * no data validation needed for this field, just validate
					 * against type
					 */
					if (fieldData.trim().equals("")) {
						/* in the event the field is only spaces we set the 
						 * field to an empty string and allow the padder to 
						 * make it the appropriate length.  The purpose of this 
						 * is to allow fields that are conditional and present as
						 * spaces in the ICL file to bypass the field level 
						 * validation.  Think of a returnLocationRoutingNumber that is blank.  
						 * The field is numeric and therefore the blanks cannnot be committed 
						 * to the field.  
						 */
						commitFieldData("");
					} else {
						if (fieldType.isDataValid(fieldData)) {
							commitFieldData(fieldData);
						} else {
							throw new ICLException("Field data for field: " + fieldName
									+ " was not valid for the field type: " + fieldType.getName()
									+ " data passed: [" + fieldData + "]");
						}
					}
				}
			} else {
				throw new ICLException("Length of Field Data for field: " + fieldName
						+ " is too long for type: " + fieldType.getName() + " data passed: ["
						+ fieldData + "]");
			}
		} catch (Exception e) {
			String newLine = System.getProperty("line.separator");
			throw new ICLException("Error setting: " + fieldName + newLine + e.getMessage()
					+ " data passed: [" + fieldData + "]");
		}
		return true;
	}

	/**
	 * Commit field data.
	 * 
	 * @param fieldData
	 *            the field data
	 * 
	 * @throws ICLException
	 *             the ICL exception
	 */
	private void commitFieldData(String fieldData) throws ICLException {
		this.validated = true;
		try {
			this.fieldData = padder.pad(fieldData, position.getLength());
		} catch (ISOException e) {
			throw new ICLException("Exception Caught", e);
		}
	}

	/**
	 * Validate data.
	 * 
	 * @param fieldData
	 *            the field data
	 * 
	 * @return true, if successful
	 * 
	 * @throws ICLException
	 *             the ICL exception
	 */
	private boolean validateData(String fieldData) throws ICLException {
		if (fieldValidator != null) {
			ValidationResult v = fieldValidator.validateData(fieldData);
			if (!v.isDataValid()) {
				throw new ICLException("Field Validation Failed: " + v.getMessage());
			}
		}
		// finally make sure it meets the packager's requirements whether or not
		// the user set up a field validator
		return fieldType.isDataValid(fieldData);
	}

	/**
	 * Valid length.
	 * 
	 * @param fieldData
	 *            the field data
	 * 
	 * @return true, if successful
	 */
	private boolean validLength(String fieldData) {
		if (fieldData.length() > position.getLength()) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cdgsoftware.icl.util.Loggeable#dump(java.io.PrintStream,
	 * java.lang.String)
	 */
	public void dump(PrintStream p, String indent) {
		p.println("<field name=\"" + fieldName + "\" usage=\"" + usage + "\" position=\""
				+ position.toString() + "\"" + " type=\"" + fieldType.getShortName()
				+ "\" validationCriteria=\"" + validationCriteria + "\" />");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String fieldClass = getClass().getSimpleName();
		return "<field name=\"" + fieldName + "\" fieldClass=\"" + fieldClass + "\" usage=\""
				+ usage + "\" position=\"" + position.toString() + "\"" + " type=\""
				+ fieldType.getShortName() + "\" validationCriteria=\"" + validationCriteria
				+ "\" value=\"" + fieldData + "/>";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fieldData == null) ? 0 : fieldData.hashCode());
		result = prime * result + ((fieldName == null) ? 0 : fieldName.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((usage == null) ? 0 : usage.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Field)) return false;
		Field other = (Field) obj;
		if (fieldData == null) {
			if (other.fieldData != null) return false;
		} else if (!fieldData.equals(other.fieldData)) return false;
		if (fieldName == null) {
			if (other.fieldName != null) return false;
		} else if (!fieldName.equals(other.fieldName)) return false;
		if (position == null) {
			if (other.position != null) return false;
		} else if (!position.equals(other.position)) return false;
		if (usage == null) {
			if (other.usage != null) return false;
		} else if (!usage.equals(other.usage)) return false;
		return true;
	}

}
