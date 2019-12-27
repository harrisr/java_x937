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
import org.cdgsoftware.icl.field.validation.FileIDModifierValidator;
import org.cdgsoftware.icl.field.validation.RecordTypeValidator;
import org.cdgsoftware.icl.field.validation.ResendIndicatorValidator;
import org.cdgsoftware.icl.field.validation.RoutingNumberValidator;
import org.cdgsoftware.icl.field.validation.StandardLevelValidator;
import org.cdgsoftware.icl.field.validation.TestFileIndicatorValidator;
import org.cdgsoftware.icl.util.EasternStandardTime;

/**
 * The Class FileHeaderRecord.
 */
public class FileHeaderRecord extends Record {
    public Field recordType;
    public Field standardLevel;
    public Field testFileIndicator;
    public Field immediateDestinationRoutingNumber;
    public Field immediateOriginRoutingNumber;
    public Field fileCreationDate;
    public Field fileCreationTime;
    public Field resendIndicator;
    public Field immediateDesitinationName;
    public Field immediateOriginName;
    public Field fileIDModifier;
    public Field countryCode;
    public Field userField;
    public Field reserved;
    
    static Logger log = Logger.getLogger(FileHeaderRecord.class);

    /**
     * Instantiates a new file header record.
     */
    public FileHeaderRecord() {
        fields = new ArrayList<Field>();
        recordName = "File Header Record";
        recordTypeNumber = "01";
        initFields();
    }

    /**
     * Inits the fields.
     */
    private void initFields() {
        try {
            /* header record */
            recordType = new GenericNField(new Position(1, 2), RecordTypeValidator.INSTANCE, "01", "Record Type");

            /* default to DSTU X9.37-2003 */
            standardLevel = new GenericNField(new Position(3, 4), StandardLevelValidator.INSTANCE, "03",
                    "Standard Level");
            standardLevel.setValidationCriteria(ValidationCriteria.REQUIRED);

            /* default to a test file */
            testFileIndicator = new GenericAField(new Position(5, 5), TestFileIndicatorValidator.INSTANCE, "T",
                    "Test File Indicator");

            /* create the two routing number fields */
            immediateDestinationRoutingNumber = new GenericNField(new Position(6, 14),
                    "Immediate Destination Routing Number");
            immediateDestinationRoutingNumber.setFieldValidator(RoutingNumberValidator.INSTANCE);
            immediateDestinationRoutingNumber.setValidationCriteria(ValidationCriteria.REQUIRED_IF_PRESENT);
            
            immediateOriginRoutingNumber = new GenericNField(new Position(15, 23), "Immediate Origin Routing Number");
            immediateOriginRoutingNumber.setFieldValidator(RoutingNumberValidator.INSTANCE);
            immediateOriginRoutingNumber.setValidationCriteria(ValidationCriteria.REQUIRED);

            /* default date and time to now */
            fileCreationDate = new GenericNField(new Position(24, 31), null, EasternStandardTime.getCurrentDateInEST(),
                    "File Creation Date");
            fileCreationTime = new GenericNField(new Position(32, 35), null, EasternStandardTime.getCurrentTimeInEST(),
                    "File Creation Time");

            /* resend indicator is 'N' always */
            resendIndicator = new GenericAField(new Position(36, 36), ResendIndicatorValidator.INSTANCE, "N",
                    "Resend Indicator");

            /* destination name has different validation and usage per ucd v1 */
            immediateDesitinationName = new GenericANField(new Position(37, 54), "Immediate Destination Name");
            immediateDesitinationName.setUsage(Usage.CONDITIONAL);

            /* origin name has different validation and usage per ucd v1 */
            immediateOriginName = new GenericANField(new Position(55, 72), "Immediate Origin Name");
            immediateOriginName.setUsage(Usage.CONDITIONAL);

            /* file id modifier */
            fileIDModifier = new GenericANField(new Position(73, 73), "File ID Modifier");
            fileIDModifier.setFieldValidator(FileIDModifierValidator.INSTANCE);
            fileIDModifier.setValidationCriteria(ValidationCriteria.REQUIRED_IF_PRESENT);

            /* country code has no validator */
            countryCode = new GenericAField(new Position(74, 75), "Country Code");
            countryCode.setUsage(Usage.CONDITIONAL);
            countryCode.setFieldData("");

            /* user field has no validator */
            userField = new GenericANSField(new Position(76, 79), "User Field");
            userField.setUsage(Usage.CONDITIONAL);
            userField.setFieldData("");

            /* reserved - often the companion document */
            reserved = new GenericBlankField(new Position(80, 80), "Reserved");
            reserved.setFieldData("");
            
            fields.add(recordType);
            fields.add(standardLevel);
            fields.add(testFileIndicator);
            fields.add(immediateDestinationRoutingNumber);
            fields.add(immediateOriginRoutingNumber);
            fields.add(fileCreationDate);
            fields.add(fileCreationTime);
            fields.add(resendIndicator);
            fields.add(immediateDesitinationName);
            fields.add(immediateOriginName);
            fields.add(fileIDModifier);
            fields.add(countryCode);
            fields.add(userField);
            fields.add(reserved);
        } catch (Exception e) {
           log.fatal(e.getMessage(), e);
        }
    }
}

class FileHeaderRecordTestDrive {
    public static void main(String[] args) {
        FileHeaderRecord f = new FileHeaderRecord();
        System.out.println(f.toString());
        f.dump(System.out, "");
    }
}
