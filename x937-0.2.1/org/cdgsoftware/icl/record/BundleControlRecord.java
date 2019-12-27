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
 * The Class BundleControlRecord.
 */
public class BundleControlRecord extends Record {
	public Field recordType;
	public Field itemCount;
	public Field bundleAmount;
	public Field micrAmount;
	public Field imagesInBundleCount;
	public Field userField;
	public Field reserved;
	
	
	static Logger log = Logger.getLogger(BundleControlRecord.class);

	/**
	 * Instantiates a new bundle control record.
	 */
	public BundleControlRecord() {
		fields = new ArrayList<Field>();
		recordName = "Bundle Control Record";
		recordTypeNumber = "70";
		initFields();
	}
	
	/**
	 * Inits the fields.
	 */
	private void initFields() {
		try {
			/* check detail record */
			recordType = new GenericNField(new Position(1, 2), RecordTypeValidator.INSTANCE, "70",
					"Record Type");
            recordType.setValidationCriteria(ValidationCriteria.REQUIRED);
			
			/* item count */
			itemCount = new GenericNField(new Position(3, 6), "Items Within Bundle Count");
			
			/* bundle amount */
			bundleAmount = new GenericNField(new Position(7, 18), "Bundle Total Amount");
			
			/* micr amount */
			micrAmount = new GenericNField(new Position(19, 30), "MICR Valid Total Amount");
			micrAmount.setUsage(Usage.CONDITIONAL);
			
			/* images within bundle count */
			imagesInBundleCount = new GenericNField(new Position(31, 35), "Images Within Bundle Count");
			imagesInBundleCount.setUsage(Usage.CONDITIONAL);
			
            /* user field - NOT USED */
            userField = new GenericANSField(new Position(36, 55), "User Field");
            userField.setUsage(Usage.CONDITIONAL);
            
            /* Reserved - NOT USED */
            reserved = new GenericBlankField(new Position(56, 80), "Reserved Field");
            reserved.setFieldData("");
            
            fields.add(recordType);
            fields.add(itemCount);
            fields.add(bundleAmount);
            fields.add(micrAmount);
            fields.add(imagesInBundleCount);
            fields.add(userField);
            fields.add(reserved);
            
		} catch (Exception e) {
			log.fatal(e.getMessage(), e);
		}
	}

}

class BundleControlRecordTestDrive {
	public static void main(String[] args) {
		BundleControlRecord f = new BundleControlRecord();
        System.out.println(f.toString());
        f.dump(System.out, "");
	}
}
