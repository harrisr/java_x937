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
import org.cdgsoftware.icl.field.GenericNBField;
import org.cdgsoftware.icl.field.GenericNField;
import org.cdgsoftware.icl.field.Position;
import org.cdgsoftware.icl.field.Usage;
import org.cdgsoftware.icl.field.ValidationCriteria;
import org.cdgsoftware.icl.field.validation.RecordTypeValidator;
import org.cdgsoftware.icl.field.validation.RoutingNumberValidator;

/**
 * The Class ImageViewDetailRecord.
 */
public class ImageViewDetailRecord extends Record {
	public Field recordType;
	public Field imageIndicator;
	public Field imageCreatorRoutingNumber;
	public Field imageCreatorDate;
	public Field imageViewFormatIndicator;
	public Field imageViewCompressionAlgorithmIdentifier;
	public Field imageViewDataSize;
	public Field viewSideIndicator;
	public Field viewDescriptor;
	public Field digitialSignatureIndicator;
	public Field digitalSignatureMethod;
	public Field securityKeySize;
	public Field startProtectedData;
	public Field lengthProtectedData;
	public Field imageRecreateIndicator;
	public Field userField;
	public Field reserved;
	
	static Logger log = Logger.getLogger(ImageViewDetailRecord.class);

	/**
	 * Instantiates a new image view detail record.
	 */
	public ImageViewDetailRecord() {
        fields = new ArrayList<Field>();
        recordName = "Image View Detail Record";
        recordTypeNumber = "50";
        initFields();
    }

    /**
     * Inits the fields.
     */
    private void initFields() {
        try {
            /* record type */
            recordType = new GenericNField(new Position(1, 2), RecordTypeValidator.INSTANCE, "50", "Image View Detail Record");
            recordType.setValidationCriteria(ValidationCriteria.REQUIRED);
            
            /* image indicator */
            imageIndicator = new GenericNField(new Position(3, 3), "Image Indicator");
            
            /* image creator routing number */
            imageCreatorRoutingNumber = new GenericNField(new Position(4, 12), "Image Creator Routing Number");
            imageCreatorRoutingNumber.setValidationCriteria(ValidationCriteria.REQUIRED);
            imageCreatorRoutingNumber.setFieldValidator(RoutingNumberValidator.INSTANCE);
            
            /* image creator date */
            imageCreatorDate = new GenericNField(new Position(13, 20), "Image Creator Date");
            
            /* image view format indicator */
            imageViewFormatIndicator = new GenericNField(new Position(21, 22), "Image View Format Indicator");
            
            /* image view compression algorithm identifier */
            imageViewCompressionAlgorithmIdentifier = new GenericNBField(new Position(23, 24), "Image View Compression Algorithm Identifier");
            
            /* image view data size */
            imageViewDataSize = new GenericNField(new Position(25, 31), "Image View Data Size");
            imageViewDataSize.setUsage(Usage.CONDITIONAL);
            
            /* view side indicator */
            viewSideIndicator = new GenericNField(new Position(32, 32), "View Side Indicator");
            
            /* view descriptor */
            viewDescriptor = new GenericNField(new Position(33, 34), "View Descriptor");
            
            /* digital signature indicator */
            digitialSignatureIndicator = new GenericNBField(new Position(35, 35), "Digital Signature Indicator");
            
            /* for the following fields the spec is numeric, but some banks send blanks */
            
            /* digital signature method */
            digitalSignatureMethod = new GenericNBField(new Position(36, 37), "Digital Signature Method");
            digitalSignatureMethod.setUsage(Usage.CONDITIONAL);
            
            /* security key size */
            securityKeySize = new GenericNBField(new Position(38, 42), "Security Key Size");
            securityKeySize.setUsage(Usage.CONDITIONAL);
            
            /* start of protected data */
            startProtectedData = new GenericNBField(new Position(43, 49), "Start Of Protected Data");
            startProtectedData.setUsage(Usage.CONDITIONAL);
            
            /* length of protected data */
            lengthProtectedData = new GenericNBField(new Position(50, 56), "Length Of Protected Data");
            lengthProtectedData.setUsage(Usage.CONDITIONAL);
            
            /* image recreate indicator */
            imageRecreateIndicator = new GenericNBField(new Position(57, 57), "Image Recreate Indicator");
            imageRecreateIndicator.setUsage(Usage.CONDITIONAL);
            
            /* user field */
            userField = new GenericANSField(new Position(58, 65), "User Field");
            userField.setUsage(Usage.CONDITIONAL);
            
            /* reserved */
            reserved = new GenericBlankField(new Position(66, 80), "Reserved");
            reserved.setFieldData("");
            
        	fields.add(recordType);
        	fields.add(imageIndicator);
        	fields.add(imageCreatorRoutingNumber);
        	fields.add(imageCreatorDate);
        	fields.add(imageViewFormatIndicator);
        	fields.add(imageViewCompressionAlgorithmIdentifier);
        	fields.add(imageViewDataSize);
        	fields.add(viewSideIndicator);
        	fields.add(viewDescriptor);
        	fields.add(digitialSignatureIndicator);
        	fields.add(digitalSignatureMethod);
        	fields.add(securityKeySize);
        	fields.add(startProtectedData);
        	fields.add(lengthProtectedData);
        	fields.add(imageRecreateIndicator);
        	fields.add(userField);
        	fields.add(reserved);
            
        } catch (Exception e) {
            log.fatal(e.getMessage(), e);
        }
    }

}

class ImageViewDetailRecordTestDrive {
	public static void main(String[] args) {
		ImageViewDetailRecord f = new ImageViewDetailRecord();
        System.out.println(f.toString());
        f.dump(System.out, "");
	}
}
