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

import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.cdgsoftware.icl.field.Field;
import org.cdgsoftware.icl.field.GenericANSField;
import org.cdgsoftware.icl.field.GenericBinaryField;
import org.cdgsoftware.icl.field.GenericNBField;
import org.cdgsoftware.icl.field.GenericNField;
import org.cdgsoftware.icl.field.Position;
import org.cdgsoftware.icl.field.Usage;
import org.cdgsoftware.icl.field.ValidationCriteria;
import org.cdgsoftware.icl.field.validation.RecordTypeValidator;
import org.cdgsoftware.icl.field.validation.RoutingNumberValidator;
import org.cdgsoftware.icl.util.ICLException;

/**
 * The Class ImageViewDataRecord.
 */
public class ImageViewDataRecord extends Record {
	public Field recordType;
	public Field eceInstitutionRoutingNumber;
	public Field bundleBusinessDate;
	public Field cycleNumber;
	public Field eceInstitutionItemSequenceNumber;
	public Field securityOriginatorName;
	public Field securityAuthenticatorName;
	public Field securityKeyName;
	public Field clippingOrigin;
	public Field clippingCoordinateH1;
	public Field clippingCoordinateH2;
	public Field clippingCoordinateV1;
	public Field clippingCoordinateV2;
	public Field lengthImageReferenceKey;
	public Field imageReferenceKey;
	public Field lengthDigitalSignature;
	public Field digitalSignature;
	public Field lengthImageData;
	public Field imageData;

	static Logger log = Logger.getLogger(ImageViewDataRecord.class);

	/**
	 * Instantiates a new image view data record. 
	 * 
	 * NOTE: you must specify positions 
	 * manually for fields 16 - 19 when building a new record to write to a file!!
	 */
	public ImageViewDataRecord() {
		fields = new ArrayList<Field>();
		recordName = "Image View Data Record";
		recordTypeNumber = "52";
		initFields();
	}

	/**
	 * Inits the fields.
	 */
	private void initFields() {
		try {
			/* record type */
			recordType = new GenericNField(new Position(1, 2), RecordTypeValidator.INSTANCE, "52",
					"Image View Data Record");
			recordType.setValidationCriteria(ValidationCriteria.REQUIRED);
			
			/* ece institution routing number */
			eceInstitutionRoutingNumber = new GenericNField(new Position(3, 11), "ECE Insitiution Routing Number");
			eceInstitutionRoutingNumber.setValidationCriteria(ValidationCriteria.REQUIRED);
			eceInstitutionRoutingNumber.setFieldValidator(RoutingNumberValidator.INSTANCE);
			
			/* bundle business date */
			bundleBusinessDate = new GenericNField(new Position(12, 19), "Bundle Business Date");
			
			/* cycle number */
			/* spec says numeric but some banks send a blank too */
			cycleNumber = new GenericNBField(new Position(20, 21), "Cycle Number");
			cycleNumber.setUsage(Usage.CONDITIONAL);
			
			/* ece institution item sequence number */
			eceInstitutionItemSequenceNumber = new GenericNBField(new Position(22, 36), "ECE Institution Item Sequence Number");
			
			/* security originator name */
			securityOriginatorName = new GenericANSField(new Position(37, 52), "Security Originator Name");
			securityOriginatorName.setUsage(Usage.CONDITIONAL);
			
			/* security authenticator name */
			securityAuthenticatorName = new GenericANSField(new Position(53, 68), "Security Authenticator Name");
			securityAuthenticatorName.setUsage(Usage.CONDITIONAL);
			
			/* security key name */
			securityKeyName = new GenericANSField(new Position(69, 84), "Security Key Name");
			securityKeyName.setUsage(Usage.CONDITIONAL);
			
			/* clipping origin */
			clippingOrigin = new GenericNBField(new Position(85, 85), "Clipping Origin");
			
			/* clipping coordinate h1 */
			clippingCoordinateH1 = new GenericNBField(new Position(86, 89), "Clipping Coordinate H1");
			clippingCoordinateH1.setUsage(Usage.CONDITIONAL);
			
			/* clipping coordinate h2 */
			clippingCoordinateH2 = new GenericNBField(new Position(90, 93), "Clipping Coordinate H2");
			clippingCoordinateH2.setUsage(Usage.CONDITIONAL);
			
			/* clipping coordinate v1 */
			clippingCoordinateV1 = new GenericNBField(new Position(94, 97), "Clipping Coordinate V1");
			clippingCoordinateV1.setUsage(Usage.CONDITIONAL);
			
			/* clipping coordinate v2 */
			clippingCoordinateV2 = new GenericNBField(new Position(98, 101), "Clipping Coordinate V2");
			clippingCoordinateV2.setUsage(Usage.CONDITIONAL);
			
			/* length image reference key */
			lengthImageReferenceKey = new GenericNBField(new Position(102, 105), "Length of Image Refernce Key");
			
			/* From here on positions are not actually "set" until reading data 
			 * It is important to be aware of this when writing a file as you will
			 * have to specify positions manually when building the record 
			 */
			
			/* image reference key */
			imageReferenceKey = new GenericANSField(new Position(106, 106), "Image Reference Key");
			
			/* length digital signature */
			lengthDigitalSignature = new GenericNBField(new Position(107, 107), "Length of Digital Signature");
			
			/* digital signature */
			digitalSignature = new GenericBinaryField(new Position(108, 108), "Digital Signature");
			
			/* length of image data */
			lengthImageData = new GenericNBField(new Position(109, 109), "Length of Image Data");
			
			/* image data */
			imageData = new GenericBinaryField(new Position(110, 110), "Image Data");

			fields.add(recordType);
			fields.add(eceInstitutionRoutingNumber);
			fields.add(bundleBusinessDate);
			fields.add(cycleNumber);
			fields.add(eceInstitutionItemSequenceNumber);
			fields.add(securityOriginatorName);
			fields.add(securityAuthenticatorName);
			fields.add(securityKeyName);
			fields.add(clippingOrigin);
			fields.add(clippingCoordinateH1);
			fields.add(clippingCoordinateH2);
			fields.add(clippingCoordinateV1);
			fields.add(clippingCoordinateV2);
			fields.add(lengthImageReferenceKey);
			fields.add(imageReferenceKey);
			fields.add(lengthDigitalSignature);
			fields.add(digitalSignature);
			fields.add(lengthImageData);
			fields.add(imageData);

		} catch (Exception e) {
			log.fatal(e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cdgsoftware.icl.record.Record#populateFields(byte[])
	 */
	@Override
	public void populateFields(byte[] data) throws ICLException {
		populateThroughField14(data);
		readImageReferenceKey(data);
		getLengthDigitalSignature(data);
		readDigitalSignature(data);
		getLengthImageData(data);
		readImageData(data);
	}
	
	private void populateThroughField14(byte[] data) throws ICLException {
		int maxLength = 105;
    	int bytePosition = 0;
    	for (int i = 0; i < 14; i++) {
    		int bytesRead = 0;
    		Field f = fields.get(i);
    		Position pos = f.getPosition();
    		int readLength = pos.getLength();
    		StringBuffer buf = new StringBuffer(readLength);
    		while (bytesRead < readLength && bytePosition < maxLength) {
    			char c = (char)(data[bytePosition] & 0xFF);
    			buf.append(c);
    			bytePosition++;
    			bytesRead++;
    		}
    		f.setFieldData(buf.toString());
    	}
	}
	
	private void readImageReferenceKey(byte[] data) throws ICLException {
		int keyStart = 106;
		int keyEnd = 105 + Integer.parseInt(lengthImageReferenceKey.getFieldData());
		int len = (keyEnd - keyStart) + 1;
		Position p = new Position(keyStart, keyEnd);
		imageReferenceKey.setPosition(p);
		
		log.debug("Image Key Start: " + keyStart);
		log.debug("Image Key End: " + keyEnd);
		log.debug("Image Key Length: " + len);
		
		if (len > 0) {
			byte[] b = new byte[len];
			for (int i = 0; i < len; i++) {
				b[i] = data[(keyStart - 1) + i];
			}
			imageReferenceKey.setFieldDataBytes(b);
		}
	}
	
	private void getLengthDigitalSignature(byte[] data) throws ICLException {
		int x = Integer.parseInt(lengthImageReferenceKey.getFieldData());
		int lenSigStart = 106 + x;
		int lenSigEnd =  110 + x;
		Position p = new Position(lenSigStart, lenSigEnd);
		
		StringBuffer buf = new StringBuffer(p.getLength());
		for (int i = 0; i < p.getLength(); i++) {
			char c = (char) (data[(lenSigStart - 1)+ i] & 0xFF);
			buf.append(c);
		}
		
		log.debug("Length Sig Start: " + lenSigStart);
		log.debug("Length Sig End: " + lenSigEnd);
		log.debug("Length Sig Data: " + buf.toString());
		
		lengthDigitalSignature.setPosition(p);
		lengthDigitalSignature.setFieldData(buf.toString());
	}
	
	private void readDigitalSignature(byte[] data) throws ICLException {
		int x = Integer.parseInt(lengthImageReferenceKey.getFieldData());
		int y = Integer.parseInt(lengthDigitalSignature.getFieldData());
		int sigStart = 111 + x;
		int sigEnd = 110 + x + y;
		int len = (sigEnd - sigStart) + 1;
		Position p = new Position(sigStart, sigEnd);
		digitalSignature.setPosition(p);
		
		log.debug("Digital Sig Start: " + sigStart);
		log.debug("Digital Sig End: " + sigEnd);
		log.debug("Digital Sig Length: " + len);
		
		if (len > 0) {
			byte[] b = new byte[len];
			for (int i = 0; i < len; i++) {
				b[i] = data[(sigStart - 1) + i];
			}
			digitalSignature.setFieldDataBytes(b);
		}
	}
	
	private void getLengthImageData(byte[] data) throws ICLException {
		int x = Integer.parseInt(lengthImageReferenceKey.getFieldData());
		int y = Integer.parseInt(lengthDigitalSignature.getFieldData());
		int lenDataStart = 111 + x + y;
		int lenDataEnd = 117 + x + y;
		Position p = new Position(lenDataStart, lenDataEnd);
		
		StringBuffer buf = new StringBuffer(p.getLength());
		for (int i = 0; i < p.getLength(); i++) {
			char c = (char) (data[(lenDataStart - 1) + i] & 0xFF);
			buf.append(c);
		}
		
		log.debug("Length Data Start: " + lenDataStart);
		log.debug("Length Data End: " + lenDataEnd);
		log.debug("Length Data: " + buf.toString());
		
		lengthImageData.setPosition(p);
		lengthImageData.setFieldData(buf.toString().trim());
	}
	
	private void readImageData(byte[] data) throws ICLException {
		int x = Integer.parseInt(lengthImageReferenceKey.getFieldData().trim());
		int y = Integer.parseInt(lengthDigitalSignature.getFieldData().trim());
		int z = Integer.parseInt(lengthImageData.getFieldData().trim());
		int imgStart = 118 + x + y;
		int imgEnd = 117 + x + y + z;
		Position p = new Position(imgStart, imgEnd);

		log.debug("Length Image Data: " + p.getLength());
		log.debug("Image Start: " + imgStart);
		log.debug("Image End: " + imgEnd);
		
		if (p.getLength() > 0) {
			/*
			 * add the raw bytes to the field since converting string to byte[] can
			 * introduce issues with the final image.
			 */
			byte[] b = new byte[p.getLength()];
			for (int i = 0; i < p.getLength(); i++) {
				try {
				b[i] = data[(imgStart - 1) + i];
				} catch (Exception ignored) {};
			}
			imageData.setFieldDataBytes(b);
			imageData.setPosition(p);
			//imageData.setFieldData(new String(data, imgStart, lenImg));
		} else {
			log.warn("Image data is mandatory in record 52 but was not present");
			throw new ICLException("Image data is mandatory in record 52 but was not present");
		}
	}

	@Override
	public byte[] getBytes() {
		ByteBuffer bb = ByteBuffer.allocate(imageData.getPosition().getEnd());
		bb.put(recordType.getFieldDataBytes());
		bb.put(eceInstitutionRoutingNumber.getFieldDataBytes());
		bb.put(bundleBusinessDate.getFieldDataBytes());
		bb.put(cycleNumber.getFieldDataBytes());
		bb.put(eceInstitutionItemSequenceNumber.getFieldDataBytes());
		bb.put(securityOriginatorName.getFieldDataBytes());
		bb.put(securityAuthenticatorName.getFieldDataBytes());
		bb.put(securityKeyName.getFieldDataBytes());
		bb.put(clippingOrigin.getFieldDataBytes());
		bb.put(clippingCoordinateH1.getFieldDataBytes());
		bb.put(clippingCoordinateH2.getFieldDataBytes());
		bb.put(clippingCoordinateV1.getFieldDataBytes());
		bb.put(clippingCoordinateV2.getFieldDataBytes());
		bb.put(lengthImageReferenceKey.getFieldDataBytes());
		if (imageReferenceKey.getPosition().getLength() > 0) {
			bb.put(imageReferenceKey.getFieldDataBytes());
		}
		bb.put(lengthDigitalSignature.getFieldDataBytes());
		if (digitalSignature.getPosition().getLength() > 0) {
			bb.put(digitalSignature.getFieldDataBytes());
		}
		bb.put(lengthImageData.getFieldDataBytes());
		if (imageData.getPosition().getLength() > 0) {
			bb.put(imageData.getFieldDataBytes());
		}
		
		return bb.array();
	}
}

class ImageViewDataRecordTestDrive {
	public static void main(String[] args) {
		ImageViewDataRecord f = new ImageViewDataRecord();
		System.out.println(f.toString());
		f.dump(System.out, "");
	}
}
