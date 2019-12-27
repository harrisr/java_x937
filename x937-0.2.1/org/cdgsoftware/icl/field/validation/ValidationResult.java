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

/**
 * The Class ValidationResult.
 */
public class ValidationResult {
    private String message;
    private boolean dataValid;

    /**
     * Instantiates a new validation result.
     */
    public ValidationResult() {
        this.message = "I have not been set by the validator yet.";
        this.dataValid = false;
    }

    /**
     * Instantiates a new validation result.
     * 
     * @param message the message
     * @param dataValid the data valid
     */
    public ValidationResult(String message, boolean dataValid) {
        super();
        this.message = message;
        this.dataValid = dataValid;
    }

    /**
     * Gets the message.
     * 
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     * 
     * @param message the new message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Checks if is data valid.
     * 
     * @return true, if is data valid
     */
    public boolean isDataValid() {
        return dataValid;
    }

    /**
     * Sets the data valid.
     * 
     * @param dataValid the new data valid
     */
    public void setDataValid(boolean dataValid) {
        this.dataValid = dataValid;
    }


}
