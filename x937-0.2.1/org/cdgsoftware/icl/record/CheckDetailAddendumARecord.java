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
import org.cdgsoftware.icl.field.GenericAField;
import org.cdgsoftware.icl.field.GenericANField;
import org.cdgsoftware.icl.field.GenericANSField;
import org.cdgsoftware.icl.field.GenericBlankField;
import org.cdgsoftware.icl.field.GenericNBField;
import org.cdgsoftware.icl.field.GenericNField;
import org.cdgsoftware.icl.field.Position;
import org.cdgsoftware.icl.field.Usage;
import org.cdgsoftware.icl.field.ValidationCriteria;
import org.cdgsoftware.icl.field.validation.RecordTypeValidator;
import org.cdgsoftware.icl.field.validation.RoutingNumberValidator;

/**
 * The Class CheckDetailAddendumARecord.
 */
public class CheckDetailAddendumARecord extends Record {
	public Field recordType;
	public Field checkDetailAddendumARecordNumber;
	public Field bofdRoutingNumber;
	public Field bofdBusinessDate;
	public Field bofdItemSequenceNumber;
	public Field depositAccountNumber;
	public Field bofdDepositBranch;
	public Field payeeName;
	public Field truncationIndicator;
	public Field bofdConversionIndicator;
	public Field bofdCorrectionIndicator;
	public Field userField;
	public Field reserved;
	
	static Logger log = Logger.getLogger(CheckDetailAddendumARecord.class);

	/**
	 * Instantiates a new check detail addendum a record.
	 */
	public CheckDetailAddendumARecord() {
        fields = new ArrayList<Field>();
        recordName = "Check Detail Addendum A Record";
        recordTypeNumber = "26";
        initFields();
    }

    /**
     * Inits the fields.
     */
    private void initFields() {
        try {
            /* cash header record */
            recordType = new GenericNField(new Position(1, 2), RecordTypeValidator.INSTANCE, "26", "Record Type");
            recordType.setValidationCriteria(ValidationCriteria.REQUIRED);
        
            /* check detail addendum a record number */
            checkDetailAddendumARecordNumber = new GenericNField(new Position(3, 3), "Check Detail Addendum A Record Number");
            
            /* bofd routing number */
            bofdRoutingNumber = new GenericNField(new Position(4, 12), "BOFD Routing Number");
            bofdRoutingNumber.setValidationCriteria(ValidationCriteria.REQUIRED_IF_PRESENT);
            bofdRoutingNumber.setUsage(Usage.CONDITIONAL);
            bofdRoutingNumber.setFieldValidator(RoutingNumberValidator.INSTANCE);
            
            /* bofd business date */
            bofdBusinessDate = new GenericNField(new Position(13, 20), "BOFD Business Date");
            bofdBusinessDate.setUsage(Usage.CONDITIONAL);
            
            /* bofd item sequence number */
            bofdItemSequenceNumber = new GenericNBField(new Position(21, 35), "BOFD Item Sequence Number");
            bofdItemSequenceNumber.setUsage(Usage.CONDITIONAL);
            
            /* bofd deposit account number */
            depositAccountNumber = new GenericANSField(new Position(36, 53), "Deposit Account Number At BOFD");
            depositAccountNumber.setUsage(Usage.CONDITIONAL);
            
            /* bofd deposit branch */
            bofdDepositBranch = new GenericANSField(new Position(54, 58), "BOFD Deposit Branch");
            bofdDepositBranch.setUsage(Usage.CONDITIONAL);
            
            /* payee name */
            payeeName = new GenericANSField(new Position(59, 73), "Payee Name");
            payeeName.setUsage(Usage.CONDITIONAL);
            
            /* truncation indicator */
            truncationIndicator = new GenericAField(new Position(74, 74), "Truncation Indicator");
            truncationIndicator.setUsage(Usage.CONDITIONAL);
            
            /* bofd conversion indicator */
            bofdConversionIndicator = new GenericANField(new Position(75, 75), "BOFD Conversion Indicator");
            bofdConversionIndicator.setUsage(Usage.CONDITIONAL);
            
            /* bofd correction indicator */
            bofdCorrectionIndicator = new GenericNField(new Position(76, 76), "BOFD Correction Indicator");
            bofdCorrectionIndicator.setUsage(Usage.CONDITIONAL);
            
            /* user field */
            userField = new GenericANSField(new Position(77, 77), "User Field");
            userField.setUsage(Usage.CONDITIONAL);
            
            /* reserved */
            reserved = new GenericBlankField(new Position(78, 80), "Reserved");
            reserved.setFieldData("");
            
        	fields.add(recordType);
        	fields.add(checkDetailAddendumARecordNumber);
        	fields.add(bofdRoutingNumber);
        	fields.add(bofdBusinessDate);
        	fields.add(bofdItemSequenceNumber);
        	fields.add(depositAccountNumber);
        	fields.add(bofdDepositBranch);
        	fields.add(payeeName);
        	fields.add(truncationIndicator);
        	fields.add(bofdConversionIndicator);
        	fields.add(bofdCorrectionIndicator);
        	fields.add(userField);
        	fields.add(reserved);
        
        } catch (Exception e) {
            log.fatal(e.getMessage(), e);
        }
    }

}

class CheckDetailAddendumARecordTestDrive {
	public static void main(String[] args) {
		CheckDetailAddendumARecord f = new CheckDetailAddendumARecord();
        System.out.println(f.toString());
        f.dump(System.out, "");
	}
}
