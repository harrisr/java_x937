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
import org.cdgsoftware.icl.field.GenericNField;
import org.cdgsoftware.icl.field.Position;
import org.cdgsoftware.icl.field.Usage;
import org.cdgsoftware.icl.field.ValidationCriteria;
import org.cdgsoftware.icl.field.validation.RecordTypeValidator;

/**
 * The Class ImageViewAnalysisRecord.
 */
public class ImageViewAnalysisRecord extends Record {
	public Field recordType;
	public Field globalImageQuality;
	public Field globalImageUsability;
	public Field imagingBankSpecificTest;
	public Field partialImage;
	public Field excessiveImageSkew;
	public Field piggybackImage;
	public Field tooLightOrTooDark;
	public Field streaksAndOrBlends;
	public Field belowMinimumImageSize;
	public Field exceedsMaximumImageSize;
	public Field reserved12;
	public Field reserved13;
	public Field reserved14;
	public Field reserved15;
	public Field reserved16;
	public Field reserved17;
	public Field reserved18;
	public Field reserved19;
	public Field reserved20;
	public Field reserved21;
	public Field reserved22;
	public Field reserved23;
	public Field reserved24;
	public Field imageEnabledPOD;
	public Field sourceDocumentBad;
	public Field dateUsability;
	public Field payeeUsability;
	public Field convenienceAmountUsability;
	public Field amountInWordsUsability;
	public Field signatureUsability;
	public Field payorNameAndAddressUsability;
	//TODO adjust this to use proper fields
	public Field allTheRest;
	
	static Logger log = Logger.getLogger(ImageViewAnalysisRecord.class);

	/**
	 * Instantiates a new image view analysis record.
	 */
	public ImageViewAnalysisRecord() {
        fields = new ArrayList<Field>();
        recordName = "Image View Analysis Record";
        recordTypeNumber = "54";
        initFields();
    }

    /**
     * Inits the fields.
     */
    private void initFields() {
        try {
            /* record type */
            recordType = new GenericNField(new Position(1, 2), RecordTypeValidator.INSTANCE, "54", "Image View Analysis Record");
            recordType.setValidationCriteria(ValidationCriteria.REQUIRED);
            
//            /* global image quality */
//            globalImageQuality = new GenericNField(new Position(3, 3), "Global Image Quality");
//            globalImageQuality.setUsage(Usage.MANDATORY);
//            
//            /* global image usability */
//            globalImageUsability = new GenericNField(new Position(4, 4), "Global Image Usability");
//            globalImageUsability.setUsage(Usage.MANDATORY);
//            
//            /* imaging bank specific test */
//            imagingBankSpecificTest = new GenericNField(new Position(5, 5), "Imaging Bank Specific Test");
//            imagingBankSpecificTest.setUsage(Usage.MANDATORY);
//            
//            /* partial image */
//            partialImage = new GenericNField(new Position(6, 6), "Partial Image");
//            partialImage.setUsage(Usage.CONDITIONAL);
//            
//            /* excessive image skew */
//            excessiveImageSkew = new GenericNField(new Position(7, 7), "Excessive Image Skew");
//            excessiveImageSkew.setUsage(Usage.CONDITIONAL);
//            
//            /* piggyback image */
//            piggybackImage = new GenericNField(new Position(8, 8), "Piggyback Image");
//            piggybackImage.setUsage(Usage.CONDITIONAL);
            
            /*  */
            
            //TODO correct this
            allTheRest = new GenericANSField(new Position(9, 80), "Everything Else Temp");
            allTheRest.setUsage(Usage.CONDITIONAL);
            
            fields.add(recordType);
            fields.add(allTheRest);
            
        } catch (Exception e) {
            log.fatal(e.getMessage(), e);
        }
    }

}
