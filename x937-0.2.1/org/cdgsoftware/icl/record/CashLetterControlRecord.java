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
import org.cdgsoftware.icl.field.GenericANSField;
import org.cdgsoftware.icl.field.GenericBlankField;
import org.cdgsoftware.icl.field.GenericNField;
import org.cdgsoftware.icl.field.Position;
import org.cdgsoftware.icl.field.Usage;
import org.cdgsoftware.icl.field.ValidationCriteria;
import org.cdgsoftware.icl.field.validation.RecordTypeValidator;

/**
 * The Class CashLetterControlRecord.
 */
public class CashLetterControlRecord extends Record {
	public Field recordType;
	public Field bundleCount;
	public Field itemCount;
	public Field cashLetterAmount;
	public Field imagesWithinCashLetterCount;
	public Field eceInstitutionName;
	public Field settlementDate;
	public Field reserved;
	
	
	static Logger log = Logger.getLogger(CashLetterControlRecord.class);

	/**
	 * Instantiates a new cash letter control record.
	 */
	public CashLetterControlRecord() {
		fields = new ArrayList<Field>();
		recordName = "Cash Letter Control Record";
		recordTypeNumber = "90";
		initFields();
	}
	
	/**
	 * Inits the fields.
	 */
	private void initFields() {
		try {
			/* check detail record */
			recordType = new GenericNField(new Position(1, 2), RecordTypeValidator.INSTANCE, "90",
					"Record Type");
            recordType.setValidationCriteria(ValidationCriteria.REQUIRED);
			
			/* bundle count */
			bundleCount = new GenericNField(new Position(3, 8), "Bundle Count");
			
			/* item count */
			itemCount = new GenericNField(new Position(9, 16), "Items Within Cash Letter Count");
			
			/* letter amount */
			cashLetterAmount = new GenericNField(new Position(17, 30), "Cash Letter Total Amount");
			
			/* images in cash letter count */
			imagesWithinCashLetterCount = new GenericANSField(new Position(31, 39), "Images Within Cash Ltter Count");
			imagesWithinCashLetterCount.setUsage(Usage.CONDITIONAL);
			
			/* ECE institution name */
			eceInstitutionName = new GenericAField(new Position(40, 57), "ECE Institution Name");
			eceInstitutionName.setUsage(Usage.CONDITIONAL);
			
			/* settlement date */
			settlementDate = new GenericNField(new Position(58, 65), "Settlement Date");
			settlementDate.setUsage(Usage.CONDITIONAL);
			
            /* Reserved */
            reserved = new GenericBlankField(new Position(66, 80), "Reserved Field");
            reserved.setFieldData("");
            
            fields.add(recordType);
            fields.add(bundleCount);
            fields.add(itemCount);
            fields.add(cashLetterAmount);
            fields.add(imagesWithinCashLetterCount);
            fields.add(eceInstitutionName);
            fields.add(settlementDate);
            fields.add(reserved);
            
		} catch (Exception e) {
			log.fatal(e.getMessage(), e);
		}
	}

}

class CashLetterControlRecordTestDrive {
	public static void main(String[] args) {
		CashLetterControlRecord f = new CashLetterControlRecord();
        System.out.println(f.toString());
        f.dump(System.out, "");
	}
}
