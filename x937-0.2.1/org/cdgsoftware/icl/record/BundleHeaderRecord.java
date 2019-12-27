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
import org.cdgsoftware.icl.field.GenericANField;
import org.cdgsoftware.icl.field.GenericBlankField;
import org.cdgsoftware.icl.field.GenericNBField;
import org.cdgsoftware.icl.field.GenericNField;
import org.cdgsoftware.icl.field.Position;
import org.cdgsoftware.icl.field.Usage;
import org.cdgsoftware.icl.field.ValidationCriteria;
import org.cdgsoftware.icl.field.validation.CollectionTypeIndicatorValidator;
import org.cdgsoftware.icl.field.validation.RecordTypeValidator;
import org.cdgsoftware.icl.field.validation.RoutingNumberValidator;
import org.cdgsoftware.icl.util.EasternStandardTime;
import org.cdgsoftware.icl.util.RightJustifySpacePadder;

/**
 * The Class BundleHeaderRecord.
 */
public class BundleHeaderRecord extends Record {
	public Field recordType;
	public Field collectionTypeIndicator;
	public Field destinationRoutingNumber;
	public Field eceInstitutionRoutingNumber;
	public Field bundleBusinessDate;
	public Field bundleCreationDate;
	public Field bundleID;
	public Field bundleSequenceNumber;
	public Field cycleNumber;
	public Field returnLocationRoutingNumber;
	public Field userField;
	public Field reserved;

	static Logger log = Logger.getLogger(BundleHeaderRecord.class);

	/**
	 * Instantiates a new bundle header record.
	 */
	public BundleHeaderRecord() {
		fields = new ArrayList<Field>();
		recordName = "Bundle Header Record";
		recordTypeNumber = "20";
		initFields();
	}

	/**
	 * Inits the fields.
	 */
	private void initFields() {
		try {
			/* bundle header record */
			recordType = new GenericNField(new Position(1, 2), RecordTypeValidator.INSTANCE, "20",
					"Record Type");
            recordType.setValidationCriteria(ValidationCriteria.REQUIRED);
			
			/* Collection Type Indicator */
			collectionTypeIndicator = new GenericNField(new Position(3, 4), "Collection Type Indicator");
			collectionTypeIndicator.setFieldValidator(CollectionTypeIndicatorValidator.INSTANCE);
			collectionTypeIndicator.setValidationCriteria(ValidationCriteria.REQUIRED);
			
            /* routing numbers */
            destinationRoutingNumber = new GenericNField(new Position(5, 13), "Destination Routing Number");
            //destinationRoutingNumber.setFieldValidator(RoutingNumberValidator.INSTANCE);
            destinationRoutingNumber.setValidationCriteria(ValidationCriteria.REQUIRED_IF_PRESENT);

            eceInstitutionRoutingNumber = new GenericNField(new Position(14, 22), "ECE Institution Routing Number");
            eceInstitutionRoutingNumber.setFieldValidator(RoutingNumberValidator.INSTANCE);
            eceInstitutionRoutingNumber.setValidationCriteria(ValidationCriteria.REQUIRED);
            
            /* dates - default to now */
            bundleBusinessDate = new GenericNField(new Position(23, 30), "Bundle Business Date");
            bundleBusinessDate.setFieldData(EasternStandardTime.getCurrentDateInEST());

            bundleCreationDate = new GenericNField(new Position(31, 38), "Bundle Creation Date");
            bundleCreationDate.setFieldData(EasternStandardTime.getCurrentDateInEST());
            
            /* bundle ID */ 
            bundleID = new GenericANField(new Position(39, 48), "Bundle ID");
            bundleID.setPadder(RightJustifySpacePadder.SPACE_PADDER);
            bundleID.setUsage(Usage.CONDITIONAL);
            bundleID.setValidationCriteria(ValidationCriteria.REQUIRED_IF_PRESENT);
            bundleID.setFieldData("");
            
            /* bundle sequence number */ 
            bundleSequenceNumber = new GenericNBField(new Position(49, 52), "Bundle Sequence Number");
            bundleSequenceNumber.setUsage(Usage.CONDITIONAL);
            
            /* cycle number */
            cycleNumber = new GenericANField(new Position(53, 54), "Cycle Number");
            cycleNumber.setUsage(Usage.CONDITIONAL);
            
            /* return location routing number */
            returnLocationRoutingNumber = new GenericNField(new Position(55, 63), "Return Location Routing Number");
            returnLocationRoutingNumber.setUsage(Usage.CONDITIONAL);
            returnLocationRoutingNumber.setFieldData("");
            
            /* user field - NOT USED */
            userField = new GenericANField(new Position(64, 68), "User Field");
            userField.setUsage(Usage.CONDITIONAL);
            userField.setFieldData("");
            
            /* Reserved - NOT USED */
            reserved = new GenericBlankField(new Position(69, 80), "Reserved Field");
            reserved.setUsage(Usage.CONDITIONAL);
            reserved.setFieldData("");
            
        	fields.add(recordType);
        	fields.add(collectionTypeIndicator);
        	fields.add(destinationRoutingNumber);
        	fields.add(eceInstitutionRoutingNumber);
        	fields.add(bundleBusinessDate);
        	fields.add(bundleCreationDate);
        	fields.add(bundleID);
        	fields.add(bundleSequenceNumber);
        	fields.add(cycleNumber);
        	fields.add(returnLocationRoutingNumber);
        	fields.add(userField);
        	fields.add(reserved);

		} catch (Exception e) {
			log.fatal(e.getMessage(), e);
		}
	}

}

class BundleHeaderRecordTestDrive {
    public static void main(String[] args) {
        BundleHeaderRecord f = new BundleHeaderRecord();
        System.out.println(f.toString());
        f.dump(System.out, "");
    }
}
