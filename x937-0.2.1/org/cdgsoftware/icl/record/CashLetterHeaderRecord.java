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
import org.cdgsoftware.icl.field.GenericNField;
import org.cdgsoftware.icl.field.Position;
import org.cdgsoftware.icl.field.Usage;
import org.cdgsoftware.icl.field.ValidationCriteria;
import org.cdgsoftware.icl.field.validation.CashLetterDocumentationTypeValidator;
import org.cdgsoftware.icl.field.validation.CashLetterRecordTypeValidator;
import org.cdgsoftware.icl.field.validation.CollectionTypeIndicatorValidator;
import org.cdgsoftware.icl.field.validation.RecordTypeValidator;
import org.cdgsoftware.icl.field.validation.RoutingNumberValidator;
import org.cdgsoftware.icl.util.EasternStandardTime;

/**
 * The Class FileHeaderRecord.
 */
public class CashLetterHeaderRecord extends Record {
    public Field recordType;
    public Field collectionTypeIndicator;
    public Field destinationRoutingNumber;
    public Field eceInstitutionRoutingNumber;
    public Field cashLetterBusinessDate;
    public Field cashLetterCreationDate;
    public Field cashLetterCreationTime;
    public Field cashLetterRecordTypeIndicator;
    public Field cashLetterDocumentationTypeIndicator;
    public Field cashLetterID;
    public Field originatorContactName;
    public Field originatorContactPhoneNumber;
    public Field fedWorkType;
    public Field userField;
    public Field reserved;

    static Logger log = Logger.getLogger(CashLetterHeaderRecord.class);

    /**
     * Instantiates a new cash letter header record.
     */
    public CashLetterHeaderRecord() {
        fields = new ArrayList<Field>();
        recordName = "Cash Letter Header Record";
        recordTypeNumber = "10";
        initFields();
    }

    /**
     * Inits the fields.
     */
    private void initFields() {
        try {
            /* cash header record */
            recordType = new GenericNField(new Position(1, 2), RecordTypeValidator.INSTANCE, "10", "Record Type");
            recordType.setValidationCriteria(ValidationCriteria.REQUIRED);

            /* Collection Type Indicator */
            collectionTypeIndicator = new GenericNField(new Position(3, 4), "Collection Type Indicator");
            collectionTypeIndicator.setFieldValidator(CollectionTypeIndicatorValidator.INSTANCE);
            collectionTypeIndicator.setValidationCriteria(ValidationCriteria.REQUIRED);

            /* routing numbers */
            destinationRoutingNumber = new GenericNField(new Position(5, 13), "Destination Routing Number");
            destinationRoutingNumber.setFieldValidator(RoutingNumberValidator.INSTANCE);
            destinationRoutingNumber.setValidationCriteria(ValidationCriteria.REQUIRED_IF_PRESENT);

            eceInstitutionRoutingNumber = new GenericNField(new Position(14, 22), "ECE Institution Routing Number");
            eceInstitutionRoutingNumber.setFieldValidator(RoutingNumberValidator.INSTANCE);
            eceInstitutionRoutingNumber.setValidationCriteria(ValidationCriteria.REQUIRED);

            /* dates and times - default to now */
            cashLetterBusinessDate = new GenericNField(new Position(23, 30), "Cash Letter Business Date");
            cashLetterBusinessDate.setFieldData(EasternStandardTime.getCurrentDateInEST());

            cashLetterCreationDate = new GenericNField(new Position(31, 38), "Cash Letter Creation Date");
            cashLetterCreationDate.setFieldData(EasternStandardTime.getCurrentDateInEST());

            cashLetterCreationTime = new GenericNField(new Position(39, 42), "Cash Letter Creation Time");
            cashLetterCreationTime.setFieldData(EasternStandardTime.getCurrentTimeInEST());

            /* record type indicator */
            cashLetterRecordTypeIndicator = new GenericAField(new Position(43, 43), "Cash Letter Record Type Indicator");
            cashLetterRecordTypeIndicator.setFieldValidator(CashLetterRecordTypeValidator.INSTANCE);
            cashLetterRecordTypeIndicator.setValidationCriteria(ValidationCriteria.REQUIRED);

            /* documentation indicator */
            cashLetterDocumentationTypeIndicator = new GenericANField(new Position(44, 44),
                    "Cash Letter Documentation Type Indicator");
            cashLetterDocumentationTypeIndicator.setFieldValidator(CashLetterDocumentationTypeValidator.INSTANCE);
            cashLetterDocumentationTypeIndicator.setValidationCriteria(ValidationCriteria.REQUIRED_IF_PRESENT);
            cashLetterDocumentationTypeIndicator.setUsage(Usage.CONDITIONAL);

            /* letter ID */
            cashLetterID = new GenericANField(new Position(45, 52), "Cash Letter ID");
            cashLetterID.setUsage(Usage.CONDITIONAL);

            /* contact info */
            originatorContactName = new GenericANSField(new Position(53, 66), "Originator Contact Name");
            originatorContactName.setUsage(Usage.CONDITIONAL);
            originatorContactName.setFieldData("");

            originatorContactPhoneNumber = new GenericANSField(new Position(67, 76), "Originator Contact Phone Number");
            originatorContactPhoneNumber.setUsage(Usage.CONDITIONAL);
            originatorContactPhoneNumber.setFieldData("");

            /*
             * fed work type, only used under clearing arrangements, thus no
             * validator
             */
            fedWorkType = new GenericANField(new Position(77, 77), "Fed Work Type");
            fedWorkType.setUsage(Usage.CONDITIONAL);
            fedWorkType.setFieldData("");


            /* user field has no validator */
            userField = new GenericANSField(new Position(78, 79), "User Field");
            userField.setUsage(Usage.CONDITIONAL);
            userField.setFieldData("");

            /* reserved */
            reserved = new GenericBlankField(new Position(80, 80), null, " ", "Reserved");

            fields.add(recordType);
            fields.add(collectionTypeIndicator);
            fields.add(destinationRoutingNumber);
            fields.add(eceInstitutionRoutingNumber);
            fields.add(cashLetterBusinessDate);
            fields.add(cashLetterCreationDate);
            fields.add(cashLetterCreationTime);
            fields.add(cashLetterRecordTypeIndicator);
            fields.add(cashLetterDocumentationTypeIndicator);
            fields.add(cashLetterID);
            fields.add(originatorContactName);
            fields.add(originatorContactPhoneNumber);
            fields.add(fedWorkType);
            fields.add(userField);
            fields.add(reserved);

        } catch (Exception e) {
            log.fatal(e.getMessage(), e);
        }
    }
}

class CashLetterHeaderRecordTestDrive {
    public static void main(String[] args) {
        CashLetterHeaderRecord f = new CashLetterHeaderRecord();
        System.out.println(f.toString());
        f.dump(System.out, "");
    }
}
