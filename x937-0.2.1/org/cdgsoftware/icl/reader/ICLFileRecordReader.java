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
package org.cdgsoftware.icl.reader;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.cdgsoftware.icl.record.AccountTotalsDetailRecord;
import org.cdgsoftware.icl.record.BundleControlRecord;
import org.cdgsoftware.icl.record.BundleHeaderRecord;
import org.cdgsoftware.icl.record.CashLetterControlRecord;
import org.cdgsoftware.icl.record.CashLetterHeaderRecord;
import org.cdgsoftware.icl.record.CheckDetailAddendumARecord;
import org.cdgsoftware.icl.record.CheckDetailAddendumBRecord;
import org.cdgsoftware.icl.record.CheckDetailAddendumCRecord;
import org.cdgsoftware.icl.record.CheckDetailRecord;
import org.cdgsoftware.icl.record.DepositTicketRecord;
import org.cdgsoftware.icl.record.FileControlRecord;
import org.cdgsoftware.icl.record.FileHeaderRecord;
import org.cdgsoftware.icl.record.ImageViewAnalysisRecord;
import org.cdgsoftware.icl.record.ImageViewDataRecord;
import org.cdgsoftware.icl.record.ImageViewDetailRecord;
import org.cdgsoftware.icl.record.Record;
import org.cdgsoftware.icl.util.FileEncoding;
import org.cdgsoftware.icl.util.ICLException;
import org.cdgsoftware.icl.util.RecordLength;
import org.jpos.iso.ISOUtil;

/**
 * The Class ICLFileRecordReader.
 */
public class ICLFileRecordReader {
	private InputStream inputStream;
	private BufferedInputStream is;
	private ArrayList<Record> records;
	private byte[] fieldLength;
	private FileEncoding fileEncoding;

	static Logger log = Logger.getLogger(ICLFileRecordReader.class);

	/**
	 * Instantiates a new iCL file record reader.
	 */
	public ICLFileRecordReader() {
		this.records = new ArrayList<Record>();
		this.fieldLength = new byte[4];
		this.fileEncoding = FileEncoding.UNKNOWN;
	}

	/**
	 * Instantiates a new iCL file record reader.
	 * 
	 * @param is
	 *            the is
	 */
	public ICLFileRecordReader(InputStream is) {
		this.inputStream = is;
		this.records = new ArrayList<Record>();
		this.fieldLength = new byte[4];
		this.fileEncoding = FileEncoding.UNKNOWN;
	}

	/**
	 * Read.
	 * 
	 * @return the array list< record>
	 */
	public ArrayList<Record> read() throws ICLException {
		try {
			is = new BufferedInputStream(inputStream);

			int length;
			while ((length = getNextFieldLength()) > 0) {
				Record r = getNextRecord(length);
				log.debug(r.toString());
				records.add(r);
			}

			int recordCountFromFile = Integer.parseInt(records.get(records.size() - 1)
					.getFieldDataAtPosition(3));
			log.info("Imported: " + records.size() + " records from ICL file");
			log.info("Record count from file: " + recordCountFromFile);
			if (records.size() != recordCountFromFile) {
				log.error("Record counts do not match.  Please check error log");
				return null;
			}

			is.close();
			inputStream.close();
		} catch (IOException e) {
			log.error("Error reading input source file", e);
		}
		return records;
	}

	/**
	 * Gets the next record.
	 * 
	 * @param readSize
	 *            the read size
	 * 
	 * @return the next record
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private Record getNextRecord(int readSize) throws IOException, ICLException {
		byte[] recordBytes = new byte[readSize];
		Record rec = null;

		/* read from the file */
		is.read(recordBytes);
		if (fileEncoding == FileEncoding.UNKNOWN) {
			/*
			 * at this point we have not determined the file encoding and we
			 * should be looking at a "01" File Header Record. If we are not
			 * then we need to throw an error that this is not a valid X9.37
			 * file encoding
			 */
			fileEncoding = FileEncoding.determineEncoding(recordBytes);
		}
		if (fileEncoding == FileEncoding.EBCDIC) {
			recordBytes = ISOUtil.ebcdicToAsciiBytes(recordBytes);
		}
		/*
		 * this is a bit expensive and should probably just be done with the
		 * bytes TODO: speed this up
		 */
		String s = new String(recordBytes, 0, 2);
		log.debug("Record type : " + s);
		if (s.matches("^01.*")) {
			rec = new FileHeaderRecord();
		} else if (s.matches("^10.*")) {
			rec = new CashLetterHeaderRecord();
		} else if (s.matches("^20.*")) {
			rec = new BundleHeaderRecord();
		} else if (s.matches("^25.*")) {
			rec = new CheckDetailRecord();
		} else if (s.matches("^61.*")) {
			rec = new DepositTicketRecord();
		} else if (s.matches("^26.*")) {
			rec = new CheckDetailAddendumARecord();
		} else if (s.matches("^27.*")) {
			rec = new CheckDetailAddendumBRecord();
		} else if (s.matches("^28.*")) {
			rec = new CheckDetailAddendumCRecord();
		} else if (s.matches("^40.*")) {
			rec = new AccountTotalsDetailRecord();
		} else if (s.matches("^50.*")) {
			rec = new ImageViewDetailRecord();
		} else if (s.matches("^52.*")) {
			rec = new ImageViewDataRecord();
			if (fileEncoding == FileEncoding.EBCDIC) {
				/*
				 * we need to revert our conversion of the actual image data
				 * since the image itself is not encoded in EBCDIC and therefore
				 * should not be adjusted. This too is an expensive process as
				 * to find the start point of the image data we actually need to
				 * load the image first into the record to calculate all of the
				 * dynamic sizes.
				 */
				try {
					rec.populateFields(recordBytes);
				} catch (ICLException e) {
					log.warn("Error populating fields from byte array", e);
					throw (e);
				}
				int imageDataStart = rec.getFieldAtPosition(19).getPosition().getStart() - 1;
				recordBytes = ISOUtil.concat(recordBytes, 0, imageDataStart, ISOUtil
						.asciiToEbcdic(recordBytes), imageDataStart, recordBytes.length
						- imageDataStart);
			}
		} else if (s.matches("^54.*")) {
			rec = new ImageViewAnalysisRecord();
		} else if (s.matches("^70.*")) {
			rec = new BundleControlRecord();
		} else if (s.matches("^90.*")) {
			rec = new CashLetterControlRecord();
		} else if (s.matches("^99.*")) {
			rec = new FileControlRecord();
		} else {
			log.error("Unknown record type: [" + s + "]");
			log
					.error("If this is a valid record type then you will need to implement it as a new record.  And of course send the implementation back to the community!");
			log.error("Full record: [" + new String(recordBytes, 0, readSize) + "]");
			return null;
		}

		try {
			rec.populateFields(recordBytes);
		} catch (ICLException e) {
			log.warn("Error populating fields from byte array", e);
		}

		return rec;
	}

	/**
	 * Gets the next field length.
	 * 
	 * @return the next field length
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private int getNextFieldLength() throws IOException {
		int numRead = is.read(fieldLength);
		if (numRead == -1) {
			log.debug("EOF detected");
			return 0;
		} else if (numRead != 4) {
			log.error("Error with ICL file.  Four bytes were expected for length but we read: "
					+ numRead + "bytes.");
		}
		int bytesToRead = RecordLength.byteArrayToInt(fieldLength);
		log.debug("Next field is : " + bytesToRead + " bytes long.");
		return bytesToRead;
	}

	/**
	 * Gets the input stream.
	 * 
	 * @return the input stream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * Sets the input stream.
	 * 
	 * @param inputStream
	 *            the new input stream
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * Gets the records.
	 * 
	 * @return the records
	 */
	public ArrayList<Record> getRecords() {
		return records;
	}

	public BufferedInputStream getIs() {
		return is;
	}

	public void setIs(BufferedInputStream is) {
		this.is = is;
	}

	public byte[] getFieldLength() {
		return fieldLength;
	}

	public void setFieldLength(byte[] fieldLength) {
		this.fieldLength = fieldLength;
	}

	public FileEncoding getFileEncoding() {
		return fileEncoding;
	}

	public void setFileEncoding(FileEncoding fileEncoding) {
		this.fileEncoding = fileEncoding;
	}
}

class ICLFileRecordReaderTestDrive {
	public static void main(String[] args) {
		InputStream is;
		try {
			is = new FileInputStream("c:\\tmp\\file.icl");
			ICLFileRecordReader reader = new ICLFileRecordReader(is);
			reader.read();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (ICLException e1) {
			System.out.println(e1.getMessage());
		}
	}
}
