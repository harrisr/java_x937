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
public class CheckDetailAddendumCRecord extends Record {
	public Field recordType;
	public Field checkDetailAddendumCRecordNumber;
	public Field endorsingBankRoutingNumber;
	public Field endorsingBusinessDate;
	public Field endorsingItemSequenceNumber;
	public Field truncationIndicator;
	public Field endorsingBankConversionIndicator;
	public Field endorsingBankCorrectionIndicator;
	public Field returnReason;
	public Field userField;
	public Field reserved;
	
	static Logger log = Logger.getLogger(CheckDetailAddendumCRecord.class);

	/**
	 * Instantiates a new check detail addendum a record.
	 */
	public CheckDetailAddendumCRecord() {
        fields = new ArrayList<Field>();
        recordName = "Check Detail Addendum C Record";
        recordTypeNumber = "28";
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
            checkDetailAddendumCRecordNumber = new GenericNField(new Position(3, 4), "Check Detail Addendum C Record Number");
            
            /* bofd routing number */
            endorsingBankRoutingNumber = new GenericNField(new Position(5, 13), "Endorsing Bank Routing Number");
            endorsingBankRoutingNumber.setValidationCriteria(ValidationCriteria.REQUIRED_IF_PRESENT);
            endorsingBankRoutingNumber.setUsage(Usage.CONDITIONAL);
            endorsingBankRoutingNumber.setFieldValidator(RoutingNumberValidator.INSTANCE);
            
            /* bofd business date */
            endorsingBusinessDate = new GenericNField(new Position(14, 21), "Endorsing Bank Business Date");
            endorsingBusinessDate.setUsage(Usage.CONDITIONAL);
            
            /* bofd item sequence number */
            endorsingItemSequenceNumber = new GenericNBField(new Position(22, 36), "Endorsing Bank Item Sequence Number");
            endorsingItemSequenceNumber.setUsage(Usage.CONDITIONAL);
            
            /* truncation indicator */
            truncationIndicator = new GenericAField(new Position(37, 37), "Truncation Indicator");
            truncationIndicator.setUsage(Usage.CONDITIONAL);
            
            /* bofd conversion indicator */
            endorsingBankConversionIndicator = new GenericANField(new Position(38, 38), "Endorsing Bank Conversion Indicator");
            endorsingBankConversionIndicator.setUsage(Usage.CONDITIONAL);
            
            /* bofd correction indicator */
            endorsingBankCorrectionIndicator = new GenericNField(new Position(39, 39), "Endorsing Bank Correction Indicator");
            endorsingBankCorrectionIndicator.setUsage(Usage.CONDITIONAL);
            
            /* return reason */
            returnReason = new GenericANField(new Position(40, 40), "Return Reason");
            returnReason.setUsage(Usage.CONDITIONAL);
            
            /* user field */
            userField = new GenericANSField(new Position(41, 55), "User Field");
            userField.setUsage(Usage.CONDITIONAL);
            
            /* reserved */
            reserved = new GenericBlankField(new Position(56, 80), "Reserved");
            reserved.setFieldData("");
            
        	fields.add(recordType);
        	fields.add(checkDetailAddendumCRecordNumber);
        	fields.add(endorsingBankRoutingNumber);
        	fields.add(endorsingBusinessDate);
        	fields.add(endorsingItemSequenceNumber);
        	fields.add(truncationIndicator);
        	fields.add(endorsingBankConversionIndicator);
        	fields.add(endorsingBankCorrectionIndicator);
        	fields.add(returnReason);
        	fields.add(userField);
        	fields.add(reserved);
        
        } catch (Exception e) {
            log.fatal(e.getMessage(), e);
        }
    }

}

class CheckDetailAddendumCRecordTestDrive {
	public static void main(String[] args) {
		CheckDetailAddendumCRecord f = new CheckDetailAddendumCRecord();
        System.out.println(f.toString());
        f.dump(System.out, "");
	}
}
