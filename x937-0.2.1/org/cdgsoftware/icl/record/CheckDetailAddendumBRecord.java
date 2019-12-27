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
public class CheckDetailAddendumBRecord extends Record {
	public Field recordType;
	public Field variableSizeRecordIndicator;
	public Field microfilmArchiveSecquenceNumber;
	public Field lengthOfImageArchiveSequenceNumber;
	public Field imageArchiveSequenceNumber;
	public Field description;
	public Field userField;
	public Field reserved;
	
	static Logger log = Logger.getLogger(CheckDetailAddendumBRecord.class);

	/**
	 * Instantiates a new check detail addendum a record.
	 */
	public CheckDetailAddendumBRecord() {
        fields = new ArrayList<Field>();
        recordName = "Check Detail Addendum B Record";
        recordTypeNumber = "27";
        initFields();
    }

    /**
     * Inits the fields.
     */
    private void initFields() {
        try {
            recordType = new GenericNField(new Position(1, 2), RecordTypeValidator.INSTANCE, "27", "Record Type");
            recordType.setValidationCriteria(ValidationCriteria.REQUIRED);
        
            variableSizeRecordIndicator = new GenericNField(new Position(3, 3), "Variable Size Record Indicator");  
            variableSizeRecordIndicator.setUsage(Usage.MANDATORY);

            microfilmArchiveSecquenceNumber = new GenericNBField(new Position(4, 18), "Microfilm Archive Sequence Number");
            microfilmArchiveSecquenceNumber.setUsage(Usage.CONDITIONAL);
            

            lengthOfImageArchiveSequenceNumber = new GenericNField(new Position(19, 22), "Length Of Image Archive Sequence Number");
            lengthOfImageArchiveSequenceNumber.setUsage(Usage.CONDITIONAL);
            
            imageArchiveSequenceNumber = new GenericNBField(new Position(23, 56), "Image Archive Sequence Number");
            imageArchiveSequenceNumber.setUsage(Usage.CONDITIONAL);
            
            description = new GenericANSField(new Position(57, 71), "Description");
            description.setUsage(Usage.CONDITIONAL);
            
            userField = new GenericANSField(new Position(72, 75), "User Field");
            userField.setUsage(Usage.CONDITIONAL);
            
            reserved = new GenericBlankField(new Position(76, 80), "Reserved");
            reserved.setFieldData("");
            
        	fields.add(recordType);
        	fields.add(variableSizeRecordIndicator);
        	fields.add(microfilmArchiveSecquenceNumber);
        	fields.add(lengthOfImageArchiveSequenceNumber);
        	fields.add(imageArchiveSequenceNumber);
        	fields.add(description);
        	fields.add(userField);
        	fields.add(reserved);
        
        } catch (Exception e) {
            log.fatal(e.getMessage(), e);
        }
    }

}
