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
import org.cdgsoftware.icl.field.GenericNBField;
import org.cdgsoftware.icl.field.GenericNField;
import org.cdgsoftware.icl.field.Position;
import org.cdgsoftware.icl.field.Usage;
import org.cdgsoftware.icl.field.ValidationCriteria;
import org.cdgsoftware.icl.field.validation.AuxOnUsValidator;
import org.cdgsoftware.icl.field.validation.BFDIndicatorValidator;
import org.cdgsoftware.icl.field.validation.CashLetterDocumentationTypeValidator;
import org.cdgsoftware.icl.field.validation.OnUsValidator;
import org.cdgsoftware.icl.field.validation.RecordTypeValidator;
import org.cdgsoftware.icl.util.RightJustifySpacePadder;

/**
 * The Class Deposit Ticket Record.
 */
public class DepositTicketRecord extends Record {
	public Field recordType;
	public Field auxiliaryOnUs;
	public Field externalProcessingCode;
	public Field payorBankRoutingNumber;
	public Field payourBankRoutingNumberCheckDigit;
	public Field onUs;
	public Field itemAmount;
	public Field eceInstitutionItemSequenceNumber;
	public Field documentationTypeIndicator;
	public Field returnAcceptanceIndicator;
	public Field micrValidIndicator;
	public Field bfdIndicator;
	public Field checkDetailRecordAddendumCount;
	public Field correctionIndicator;
	public Field archiveTypeIndicator;
	
	
	static Logger log = Logger.getLogger(DepositTicketRecord.class);

	/**
	 * Instantiates a new deposit ticket record.
	 */
	public DepositTicketRecord() {
		fields = new ArrayList<Field>();
		recordName = "Deposit Ticket Record";
		recordTypeNumber = "61";
		initFields();
	}
	
	/**
	 * Inits the fields.
	 */
	private void initFields() {
		try {
			/* check detail record */
			recordType = new GenericNField(new Position(1, 2), RecordTypeValidator.INSTANCE, "61",
					"Record Type");
            recordType.setValidationCriteria(ValidationCriteria.REQUIRED);
			
			/* aux on us */
			auxiliaryOnUs = new GenericANSField(new Position(3, 17), "Auxiliary On Us");
			auxiliaryOnUs.setPadder(RightJustifySpacePadder.SPACE_PADDER);
			auxiliaryOnUs.setUsage(Usage.CONDITIONAL);
			auxiliaryOnUs.setValidationCriteria(ValidationCriteria.REQUIRED_IF_PRESENT);
			auxiliaryOnUs.setFieldValidator(AuxOnUsValidator.INSTANCE);
			
			/* external processing code */
			externalProcessingCode = new GenericANSField(new Position(18, 18), "External Processing Code");
			externalProcessingCode.setPadder(RightJustifySpacePadder.SPACE_PADDER);
			externalProcessingCode.setUsage(Usage.CONDITIONAL);
			
			/* payor bank routing number */
			payorBankRoutingNumber = new GenericNField(new Position(19, 26), "Payor Bank Routing Number");
			
			/* payor bank routing number check digit */
			payourBankRoutingNumberCheckDigit = new GenericNField(new Position(27, 27), "Payor Bank Routing Number Check Digit");
			payourBankRoutingNumberCheckDigit.setUsage(Usage.CONDITIONAL);
			
			/* on us */
			onUs = new GenericANSField(new Position(28, 47), "On Us");
			onUs.setPadder(RightJustifySpacePadder.SPACE_PADDER);
			onUs.setUsage(Usage.CONDITIONAL);
			onUs.setValidationCriteria(ValidationCriteria.REQUIRED_IF_PRESENT);
			onUs.setFieldValidator(OnUsValidator.INSTANCE);
			
			/* item amount */
			itemAmount = new GenericNField(new Position(48, 57), "Item Amount");
			
			/* ece institution item sequence number */
			eceInstitutionItemSequenceNumber = new GenericNBField(new Position(58, 72), "ECE Institution Item Sequence Number");
			eceInstitutionItemSequenceNumber.setPadder(RightJustifySpacePadder.SPACE_PADDER);
			
			/* document type indicator */
			documentationTypeIndicator = new GenericANField(new Position(73, 73), "Documentation Type Indicator");
			documentationTypeIndicator.setFieldValidator(CashLetterDocumentationTypeValidator.INSTANCE);
			documentationTypeIndicator.setUsage(Usage.CONDITIONAL);
			documentationTypeIndicator.setValidationCriteria(ValidationCriteria.REQUIRED_IF_PRESENT);
			
			/* return acceptance indicator */
			returnAcceptanceIndicator = new GenericANField(new Position(74, 74), "Return Acceptance Indicator");
			returnAcceptanceIndicator.setUsage(Usage.CONDITIONAL);
			
			/* micr valid indicator */
			micrValidIndicator = new GenericNBField(new Position(75, 75), "MICR Valid Indicator");
			micrValidIndicator.setUsage(Usage.CONDITIONAL);
			
			/* bfd indicator */
			bfdIndicator = new GenericAField(new Position(76, 76), "BFD Indicator");
			bfdIndicator.setFieldValidator(BFDIndicatorValidator.INSTANCE);
			bfdIndicator.setValidationCriteria(ValidationCriteria.REQUIRED);
			bfdIndicator.setFieldData("N");
			
			/* check detail record addendum count */
			checkDetailRecordAddendumCount = new GenericANField(new Position(77, 78), "Check Detail Record Addendum Count");
			
			/* correction indicator */
			correctionIndicator = new GenericNField(new Position(79, 79), "Correction Indicator");
			correctionIndicator.setUsage(Usage.CONDITIONAL);
			
			/* archive type indicator */
			archiveTypeIndicator = new GenericANField(new Position(80, 80), "Archive Type Indicator");
			archiveTypeIndicator.setUsage(Usage.CONDITIONAL);
			
			
        	fields.add(recordType);
        	fields.add(auxiliaryOnUs);
        	fields.add(externalProcessingCode);
        	fields.add(payorBankRoutingNumber);
        	fields.add(payourBankRoutingNumberCheckDigit);
        	fields.add(onUs);
        	fields.add(itemAmount);
        	fields.add(eceInstitutionItemSequenceNumber);
        	fields.add(documentationTypeIndicator);
        	fields.add(returnAcceptanceIndicator);
        	fields.add(micrValidIndicator);
        	fields.add(bfdIndicator);
        	fields.add(checkDetailRecordAddendumCount);
        	fields.add(correctionIndicator);
        	fields.add(archiveTypeIndicator);
			
		} catch (Exception e) {
			log.fatal(e.getMessage(), e);
		}
	}

}
