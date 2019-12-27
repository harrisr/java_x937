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

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.cdgsoftware.icl.field.Field;
import org.cdgsoftware.icl.field.Position;
import org.cdgsoftware.icl.util.ICLException;
import org.jpos.util.Loggeable;

/**
 * The Class Record.
 */
public abstract class Record implements Loggeable {
	protected ArrayList<Field> fields;
	protected String recordName;
	protected String recordTypeNumber;


	/**
	 * Sets the field data.
	 * 
	 * @param fieldNumber
	 *            the field number FROM THE SPEC. Do not consider this a zero
	 *            based collection
	 * @param data
	 *            the data
	 * 
	 * @throws ICLException
	 *             the ICL exception
	 */
	public void setFieldData(int fieldNumber, String data) throws ICLException {
		if (fieldNumber > 0) {
			fieldNumber--;
		}
		fields.get(fieldNumber).setFieldData(data);
	}


	/**
	 * Gets the field name at position.
	 * 
	 * @param fieldNumber
	 *            the field number FROM THE SPEC. Do not consider this a zero
	 *            based collection
	 * 
	 * @return the field name at position
	 */
	public String getFieldNameAtPosition(int fieldNumber) {
		if (fieldNumber > 0) {
			fieldNumber--;
		}
		return fields.get(fieldNumber).getFieldName();
	}


	/**
	 * Gets the field data at position.
	 * 
	 * @param fieldNumber
	 *            the field number FROM THE SPEC. Do not consider this a zero
	 *            based collection
	 * 
	 * @return the field data at position
	 */
	public String getFieldDataAtPosition(int fieldNumber) {
		if (fieldNumber > 0) {
			fieldNumber--;
		}
		return fields.get(fieldNumber).getFieldData();
	}


	/**
	 * Gets the field at position.
	 * 
	 * @param fieldNumber
	 *            the field number FROM THE SPEC. Do not consider this a zero
	 *            based collection
	 * 
	 * @return the field data at position
	 */
	public Field getFieldAtPosition(int fieldNumber) {
		if (fieldNumber > 0) {
			fieldNumber--;
		}
		return fields.get(fieldNumber);
	}


	/**
	 * Gets the fields.
	 * 
	 * @return the fields
	 */
	public ArrayList<Field> getFields() {
		return fields;
	}


	/**
	 * Sets the fields.
	 * 
	 * @param fields
	 *            the new fields
	 */
	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}


	/**
	 * Gets the record name.
	 * 
	 * @return the record name
	 */
	public String getRecordName() {
		return recordName;
	}


	/**
	 * Sets the record name.
	 * 
	 * @param recordName
	 *            the new record name
	 */
	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jpos.util.Loggeable#dump(java.io.PrintStream, java.lang.String)
	 */
	public void dump(PrintStream p, String indent) {
		p.println(indent + getStructureXML());
	}


	/**
	 * Gets the structure xml.
	 * 
	 * @return the structure xml
	 */
	public String getStructureXML() {
		StringBuffer sb = new StringBuffer();
		String newline = System.getProperty("line.separator");
		Iterator<Field> it = fields.iterator();
		sb.append("<record name=\"" + recordName + "\" recordType=\"" + recordTypeNumber + "\">");
		while (it.hasNext()) {
			Field f = it.next();
			String fieldRepresentation = f.toString();
			sb.append(fieldRepresentation);
			sb.append(newline);
		}
		sb.append("</record>");
		return sb.toString();
	}


	/**
	 * Gets the bytes from the record
	 * 
	 * @return the bytes
	 */
	public byte[] getBytes() {
		return toString().getBytes();
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		Iterator<Field> it = fields.iterator();
		while (it.hasNext()) {
			sb.append(it.next().getFieldData());
		}
		return sb.toString();
	}


	/**
	 * Populate fields.
	 * 
	 * @param data
	 *            the data
	 * 
	 * @throws ICLException
	 *             the ICL exception
	 */
	public void populateFields(byte[] data) throws ICLException {
		int maxLength = getRecordLength();
		int dataLength = data.length;
		if (dataLength > maxLength) {
			throw new ICLException("Data passed is too long for " + recordName + " Max: " + maxLength + " Data Passed: " + dataLength);
		}
		int bytePosition = 0;
		Iterator<Field> it = fields.iterator();
		while (it.hasNext()) {
			int bytesRead = 0;
			Field f = it.next();
			Position pos = f.getPosition();
			int readLength = pos.getLength();
			StringBuffer buf = new StringBuffer(readLength);
			while (bytesRead < readLength && bytePosition < dataLength) {
				char c = (char) (data[bytePosition] & 0xFF);
				buf.append(c);
				bytePosition++;
				bytesRead++;
			}
			f.setFieldData(buf.toString());
		}
	}


	/**
	 * Gets the record length.
	 * 
	 * @return the record length
	 */
	public int getRecordLength() {
		Field f = fields.get(fields.size() - 1);
		return f.getPosition().getEnd();
	}


	public String getRecordTypeNumber() {
		return recordTypeNumber;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		result = prime * result + ((recordName == null) ? 0 : recordName.hashCode());
		result = prime * result + ((recordTypeNumber == null) ? 0 : recordTypeNumber.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Record)) return false;
		Record other = (Record) obj;
		if (fields == null) {
			if (other.fields != null) return false;
		} else if (!fields.equals(other.fields)) return false;
		if (recordName == null) {
			if (other.recordName != null) return false;
		} else if (!recordName.equals(other.recordName)) return false;
		if (recordTypeNumber == null) {
			if (other.recordTypeNumber != null) return false;
		} else if (!recordTypeNumber.equals(other.recordTypeNumber)) return false;
		return true;
	}

}
