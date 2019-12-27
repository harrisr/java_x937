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
package org.cdgsoftware.icl.record;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.cdgsoftware.icl.field.Field;
import org.cdgsoftware.icl.field.GenericANSField;
import org.cdgsoftware.icl.field.GenericBlankField;
import org.cdgsoftware.icl.field.GenericNField;
import org.cdgsoftware.icl.field.Position;
import org.cdgsoftware.icl.field.Usage;
import org.cdgsoftware.icl.field.ValidationCriteria;
import org.cdgsoftware.icl.field.validation.RecordTypeValidator;

/**
 * The Class CheckDetailRecord.
 */
public class AccountTotalsDetailRecord extends Record {
	public Field recordType;
	public Field destinationRoutingNumber;
	public Field keyAccount1;
	public Field keyAccount2;
	public Field totalItemCount;
	public Field totalItemAmount;
	public Field userField;
	public Field reserved;

	static Logger log = Logger.getLogger(AccountTotalsDetailRecord.class);


	/**
	 * Instantiates a new check detail record.
	 */
	public AccountTotalsDetailRecord() {
		fields = new ArrayList<Field>();
		recordName = "Account Totals Detail Record";
		recordTypeNumber = "40";
		initFields();
	}


	/**
	 * Inits the fields.
	 */
	private void initFields() {
		try {
			/* check detail record */
			recordType = new GenericNField(new Position(1, 2), RecordTypeValidator.INSTANCE, "40",
					"Record Type");
            recordType.setValidationCriteria(ValidationCriteria.REQUIRED);
			
            destinationRoutingNumber = new GenericNField(new Position(3, 11), "Destination Routing Number");
            
        	keyAccount1 = new GenericNField(new Position(12, 29), "Key Account");
        	keyAccount2 = new GenericNField(new Position(30, 47), "Key Account");
			totalItemCount = new GenericNField(new Position(48, 59), "Total Item Count");
			totalItemAmount = new GenericNField(new Position(60, 73), "Total Item Amount");
			

            /* user field has no validator */
            userField = new GenericANSField(new Position(74, 77), "User Field");
            userField.setUsage(Usage.CONDITIONAL);
            userField.setFieldData("");

            /* reserved */
            reserved = new GenericBlankField(new Position(78, 80), null, " ", "Reserved");
			
			
        	fields.add(recordType);
        	fields.add(destinationRoutingNumber);
        	fields.add(keyAccount1);
        	fields.add(keyAccount2);
        	fields.add(totalItemCount);
        	fields.add(totalItemAmount);
        	fields.add(userField);
        	fields.add(reserved);
			
		} catch (Exception e) {
			log.fatal(e.getMessage(), e);
		}
	}
}
