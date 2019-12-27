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
import org.cdgsoftware.icl.field.GenericNField;
import org.cdgsoftware.icl.field.Position;
import org.cdgsoftware.icl.field.Usage;
import org.cdgsoftware.icl.field.ValidationCriteria;
import org.cdgsoftware.icl.field.validation.RecordTypeValidator;

/**
 * The Class FileControlRecord.
 */
public class FileControlRecord extends Record {
	public Field recordType;
	public Field cashLetterCount;
	public Field totalRecordsCount;
	public Field totalItemsCount;
	public Field fileTotalAmount;
	public Field immediateOriginContactName;
	public Field immediateOriginContactPhone;
	public Field reserved;
	
	static Logger log = Logger.getLogger(FileControlRecord.class);

	/**
	 * Instantiates a new file control record.
	 */
	public FileControlRecord() {
		fields = new ArrayList<Field>();
		recordName = "File Control Record";
		recordTypeNumber = "99";
		initFields();
	}
	
	/**
	 * Inits the fields.
	 */
	private void initFields() {
		try {
			/* check detail record */
			recordType = new GenericNField(new Position(1, 2), RecordTypeValidator.INSTANCE, "99",
					"Record Type");
            recordType.setValidationCriteria(ValidationCriteria.REQUIRED);
			
			/* cash letter count */
			cashLetterCount = new GenericNField(new Position(3, 8), "Cash Letter Count");
			
			/* total records count */
			totalRecordsCount = new GenericNField(new Position(9, 16), "Total Records Count");
			
			/* total items count */
			totalItemsCount = new GenericNField(new Position(17, 24), "Total Items Count");
			
			/* file total amount */ 
			fileTotalAmount = new GenericNField(new Position(25, 40), "File Total Amount");
			
			/* immediate origin name */
			immediateOriginContactName = new GenericANField(new Position(41, 54), "Immediate Origin Contact Name");
			immediateOriginContactName.setUsage(Usage.CONDITIONAL);
			
			/* immediate origin phone */
			immediateOriginContactPhone = new GenericANField(new Position(55, 64), "Immediate Origin Contact Phone Number");
			immediateOriginContactPhone.setUsage(Usage.CONDITIONAL);
			
			/* reserved */
			reserved = new GenericBlankField(new Position(65, 80), "Reserved");
			reserved.setFieldData("");
			
			fields.add(recordType);
			fields.add(cashLetterCount);
			fields.add(totalRecordsCount);
			fields.add(totalItemsCount);
			fields.add(fileTotalAmount);
			fields.add(immediateOriginContactName);
			fields.add(immediateOriginContactPhone);
			fields.add(reserved);
			
		} catch (Exception e) {
			log.fatal(e.getMessage(), e);
		}
	}

}

class FileControlRecordTestDrive {
	public static void main(String[] args) {
		FileControlRecord f = new FileControlRecord();
        System.out.println(f.toString());
        f.dump(System.out, "");
	}
}
