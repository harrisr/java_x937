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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.cdgsoftware.icl.CashLetter;
import org.cdgsoftware.icl.ForwardPresentmentBundle;
import org.cdgsoftware.icl.ICLFile;
import org.cdgsoftware.icl.ICLItem;
import org.cdgsoftware.icl.ImageView;
import org.cdgsoftware.icl.ReturnBundle;
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
import org.cdgsoftware.icl.writer.ICLFileWriter;

/**
 * The Class ICLReader.
 */
public class ICLReader {
	private ICLFile iclFile;
	private InputStream is;
	private ArrayList<Record> records;
	private int recordPosition;

	static Logger log = Logger.getLogger(ICLReader.class);

	/**
	 * Instantiates a new iCL reader.
	 * 
	 * @param is
	 *            the is
	 */
	public ICLReader(InputStream is) {
		this.is = is;
	}

	/**
	 * Read icl file.
	 * 
	 * @return the iCL file
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ICLException
	 *             the ICL exception
	 */
	public ICLFile readICLFile() throws IOException, ICLException {
		/*
		 * read the raw records from the file and put store in an array list.
		 */
		ICLFileRecordReader recordReader = new ICLFileRecordReader(is);
		records = recordReader.read();

		/*
		 * go through the list of records and load them into the file
		 */
		iclFile = loadRecords();
		/*
		 * keep our file encoding in the event we want to transform this into an
		 * output file
		 */
		iclFile.setFileEncoding(recordReader.getFileEncoding());

		return iclFile;
	}

	/**
	 * Load records.
	 * 
	 * @return the iCL file
	 * 
	 * @throws ICLException
	 *             the ICL exception
	 */
	private ICLFile loadRecords() throws ICLException {
		ICLFile iclFile = new ICLFile();

		/*
		 * 01 load the file header. If the first record is not a file header
		 * record then throw an exception
		 */
		if (nextRecordType() == null) {
			/* empty file */
			return null;
		}

		FileHeaderRecord fileHeader = (FileHeaderRecord) getNextRecord();
		if (!fileHeader.recordType.getFieldData().equals("01")) {
			throwException(fileHeader);
		}
		iclFile.setFileHeader(fileHeader);

		/* loop to load cash letters */
		iclFile.setCashLetterList(loadCashLetters());

		/* 99 file control record */
		FileControlRecord fileControl = (FileControlRecord) getNextRecord();
		if (!fileControl.recordType.getFieldData().equals("99")) {
			throwException(fileControl);
		}
		iclFile.setFileControl(fileControl);

		return iclFile;
	}

	/**
	 * Load cash letters.
	 * 
	 * @return the array list< cash letter>
	 * 
	 * @throws ICLException
	 *             the ICL exception
	 */
	private ArrayList<CashLetter> loadCashLetters() throws ICLException {
		ArrayList<CashLetter> cashLetters = new ArrayList<CashLetter>();

		/* peek ahead and make sure we have a cash letter in the file */
		if (nextRecordType().equals("10") && nextRecordType() != null) {

			/* loop through cash letters until we hit the file control */
			while (!nextRecordType().equals("99") && nextRecordType() != null) {

				CashLetter cashLetter = new CashLetter();
				CashLetterHeaderRecord cashLetterHeader = (CashLetterHeaderRecord) getNextRecord();
				if (!cashLetterHeader.recordType.getFieldData().equals("10")) {
					throwException(cashLetterHeader);
				}
				/* 10 load the cash Letter Header Record. */
				cashLetter.setCashLetterHeader(cashLetterHeader);

				/* load the bundles */
				cashLetter.setForwardPresentmentBundleList(loadForwardBundles());
				cashLetter.setReturnBundleList(loadReturnBundles());

				/* 90 load the cash letter control */
				CashLetterControlRecord cashLetterControl = (CashLetterControlRecord) getNextRecord();
				if (!cashLetterControl.recordType.getFieldData().equals("90")) {
					throwException(cashLetterControl);
				}
				cashLetter.setCashLetterControl(cashLetterControl);

				/* add the letter to our list */
				cashLetters.add(cashLetter);
			}
		}
		return cashLetters;
	}

	/**
	 * Load forward bundles.
	 * 
	 * @return the array list< forward presentment bundle>
	 * 
	 * @throws ICLException
	 *             the ICL exception
	 */
	private ArrayList<ForwardPresentmentBundle> loadForwardBundles() throws ICLException {
		ArrayList<ForwardPresentmentBundle> bundles = new ArrayList<ForwardPresentmentBundle>();

		/* peek ahead and make sure we have a bundle in the file */
		if (nextRecordType().equals("20") && nextRecordType() != null) {

			/* loop through cash letters until we hit the cash letter control */
			while (!nextRecordType().equals("90") && nextRecordType() != null) {

				ForwardPresentmentBundle bundle = new ForwardPresentmentBundle();

				BundleHeaderRecord bundleHeader = (BundleHeaderRecord) getNextRecord();
				if (!bundleHeader.recordType.getFieldData().equals("20")) {
					throwException(bundleHeader);
				}
				/* add the header */
				bundle.setBundleHeader(bundleHeader);
				/* add the items */
				bundle.setItemList(loadItems());

				/* add the control */
				BundleControlRecord bundleControl = (BundleControlRecord) getNextRecord();
				if (!bundleControl.recordType.getFieldData().equals("70")) {
					throwException(bundleControl);
				}
				bundle.setBundleControl(bundleControl);

				/* add the bundle to our list */
				bundles.add(bundle);
			}
		}
		return bundles;
	}

	/**
	 * Load return bundles.
	 * 
	 * @return the array list< return bundle>
	 * 
	 * @throws ICLException
	 *             the ICL exception
	 */
	private ArrayList<ReturnBundle> loadReturnBundles() throws ICLException {
		// TODO: implement return bundle adding
		ArrayList<ReturnBundle> bundles = null;
		return bundles;
	}

	/**
	 * Load items.
	 * 
	 * @return the array list< icl item>
	 * 
	 * @throws ICLException
	 *             the ICL exception
	 */
	private ArrayList<ICLItem> loadItems() throws ICLException {
		ArrayList<ICLItem> items = new ArrayList<ICLItem>();
		/* peek ahead and make sure we have an item in the file */
		if ((nextRecordType().equals("25") || nextRecordType().equals("61")) && nextRecordType() != null) {

			/* loop through items until we hit the bundle control */
			while (!nextRecordType().equals("70") && nextRecordType() != null) {
				ICLItem iclItem = new ICLItem();

				if (nextRecordType().equals("25")) {
					CheckDetailRecord checkDetail = (CheckDetailRecord) getNextRecord();
					if (!checkDetail.recordType.getFieldData().equals("25")) {
						throwException(checkDetail);
					}
					iclItem.setDetailRecord(checkDetail);
				} else if (nextRecordType().equals("61")) {
					DepositTicketRecord depositTicket = (DepositTicketRecord) getNextRecord();
					if (!depositTicket.recordType.getFieldData().equals("61")) {
						throwException(depositTicket);
					}
					iclItem.setDetailRecord(depositTicket);
				}

				ArrayList<CheckDetailAddendumARecord> addendumARecords = new ArrayList<CheckDetailAddendumARecord>();
				while (nextRecordType().equals("26")) {
					/* add the Check Detail Addendum A Records */
					addendumARecords.add((CheckDetailAddendumARecord) getNextRecord());
				}
				iclItem.setCheckDetailAddendumAList(addendumARecords);

				if (nextRecordType().equals("27")) {
					/* add the check detail addendum b record */
					iclItem.setCheckDetailAdendumB((CheckDetailAddendumBRecord) getNextRecord());
				}

				ArrayList<CheckDetailAddendumCRecord> addendumCRecords = new ArrayList<CheckDetailAddendumCRecord>();
				while (nextRecordType().equals("28")) {
					/* add the Check Detail Addendum A Records */
					addendumCRecords.add((CheckDetailAddendumCRecord) getNextRecord());
				}
				iclItem.setCheckDetailAddendumCList(addendumCRecords);

				/* add image views */
				iclItem.setImageViewList(loadImageViews());

				/* add the item to our list */
				items.add(iclItem);
			}
		}
		return items;
	}

	/**
	 * Load image views.
	 * 
	 * @return the array list< image view>
	 * 
	 * @throws ICLException
	 *             the ICL exception
	 */
	private ArrayList<ImageView> loadImageViews() throws ICLException {
		ArrayList<ImageView> imageViews = new ArrayList<ImageView>();
		/* peek ahead and make sure we have an image in the file */
		if (nextRecordType().equals("50") && nextRecordType() != null) {

			/*
			 * loop through items until we hit the next check detail or bundle
			 * control
			 */
			while (!(nextRecordType().equals("25") || nextRecordType().equals("61")) && !nextRecordType().equals("70")
					&& nextRecordType() != null) {

				ImageView imageView = new ImageView();

				ImageViewDetailRecord imageDetail = (ImageViewDetailRecord) getNextRecord();
				if (!imageDetail.recordType.getFieldData().equals("50")) {
					throwException(imageDetail);
				}

				/* add the detail */
				imageView.setImageViewDetail(imageDetail);

				/*
				 * add the data - throw exception if it is not there because it
				 * is required to follow a image view detail record
				 */
				ImageViewDataRecord dataRecord = (ImageViewDataRecord) getNextRecord();
				if (!dataRecord.recordType.getFieldData().equals("52")) {
					throwException(dataRecord);
				}
				imageView.setImageViewData(dataRecord);

				/* optionally add analysis */
				if (nextRecordType().equals("54")) {
					imageView.setImageViewAnalysis((ImageViewAnalysisRecord) getNextRecord());
				}

				/* add the image view to the list */
				imageViews.add(imageView);
			}
		}

		return imageViews;
	}

	/**
	 * Next record type.
	 * 
	 * @return the string
	 */
	private String nextRecordType() {
		String recType = null;
		try {
			recType = records.get(recordPosition).getFieldDataAtPosition(1);
		} catch (ArrayIndexOutOfBoundsException e) {
			/* there is no next record, just return null */
			log.info("At end of record list");
		}
		return recType;
	}

	/**
	 * Gets the next record.
	 * 
	 * @return the next record
	 */
	private Record getNextRecord() {
		Record r = null;
		try {
			r = records.get(recordPosition);
			recordPosition++;
			log.debug(r.toString());
			log.debug(r.getStructureXML());
		} catch (ArrayIndexOutOfBoundsException e) {
			/* there is no next record */
			log.info("At end of record list");
		}
		return r;

	}

	/**
	 * Throw exception.
	 * 
	 * @param failureRecord
	 *            the failure record
	 * 
	 * @throws ICLException
	 *             the ICL exception
	 */
	private void throwException(Record failureRecord) throws ICLException {
		StringBuilder buf = new StringBuilder();
		buf.append("Input file does not appear to be the correct format");
		buf.append(System.getProperty("line.separator"));
		buf.append("Offending Record: ");
		buf.append(failureRecord.toString());
		buf.append(System.getProperty("line.separator"));
		buf.append("Record Structure: ");
		buf.append(System.getProperty("line.separator"));
		buf.append(failureRecord.getStructureXML());

		throw new ICLException(buf.toString());
	}

}

class ICLReaderTestDrive {
	public static void main(String[] args) {
		try {
			ICLReader reader = new ICLReader(new FileInputStream("/tmp/DemoICL20150722.120204.x9100"));
			ICLFile f = reader.readICLFile();
			System.out.println("Imported: " + f.getCashLetterList().get(0).getForwardPresentmentBundleList().size()
					+ " bundles");
			f.saveAllImages("/tmp/img/");
			ICLFileWriter writer = new ICLFileWriter(f, new File("/tmp/out.x9"));
			f.setFileEncoding(FileEncoding.ASCII);
			writer.writeFile();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
