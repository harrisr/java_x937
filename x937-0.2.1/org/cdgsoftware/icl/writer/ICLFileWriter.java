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
package org.cdgsoftware.icl.writer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.cdgsoftware.icl.CashLetter;
import org.cdgsoftware.icl.ForwardPresentmentBundle;
import org.cdgsoftware.icl.ICLFile;
import org.cdgsoftware.icl.ICLItem;
import org.cdgsoftware.icl.ImageView;
import org.cdgsoftware.icl.reader.ICLReader;
import org.cdgsoftware.icl.util.FileEncoding;
import org.cdgsoftware.icl.util.RecordLength;
import org.jpos.iso.ISOUtil;

/**
 * The Class ICLFileWriter.
 */
public class ICLFileWriter {
	private ICLFile iclFile;
	private File fileOut;
	private FileOutputStream oStream;
	private byte[] len;
	private byte[] data;

	static Logger log = Logger.getLogger(ICLFileWriter.class);


	/**
	 * Instantiates a new iCL file writer.
	 */
	public ICLFileWriter() {
	}


	/**
	 * Instantiates a new iCL file writer.
	 * 
	 * @param iclFile
	 *            the icl file
	 * @param fileOut
	 *            the file out
	 */
	public ICLFileWriter(ICLFile iclFile, File fileOut) {
		this.iclFile = iclFile;
		this.fileOut = fileOut;
	}


	/**
	 * Write file.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void writeFile() throws IOException {
		oStream = new FileOutputStream(fileOut);

		if (iclFile == null || fileOut == null) {
			return;
		}

		/* write the file section by section */
		writeFileData();

		oStream.close();
	}


	/**
	 * Write file data.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void writeFileData() throws IOException {
		/* write the file header */
		len = RecordLength.intToByteArray(iclFile.getFileHeader().getRecordLength(), 4);
		data = iclFile.getFileHeader().getBytes();
		writeData();

		/* write all data in the cash letters */
		writeCashLetterData();

		/* write the file trailer */
		len = RecordLength.intToByteArray(iclFile.getFileControl().getRecordLength(), 4);
		data = iclFile.getFileControl().getBytes();
		writeData();
	}


	/**
	 * Write cash letter data.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void writeCashLetterData() throws IOException {
		Iterator<CashLetter> it = iclFile.getCashLetterList().iterator();
		while (it.hasNext()) {
			CashLetter letter = it.next();

			/* write the cash letter header */
			len = RecordLength.intToByteArray(letter.getCashLetterHeader().getRecordLength(), 4);
			data = letter.getCashLetterHeader().getBytes();
			writeData();

			/* write all of the data in the bundles */
			writeBundleData(letter);

			/* write the trailer */
			len = RecordLength.intToByteArray(letter.getCashLetterControl().getRecordLength(), 4);
			data = letter.getCashLetterControl().getBytes();
			writeData();
		}
	}


	/**
	 * Write bundle data.
	 * 
	 * @param letter
	 *            the letter
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void writeBundleData(CashLetter letter) throws IOException {
		Iterator<ForwardPresentmentBundle> it = letter.getForwardPresentmentBundleList().iterator();
		while (it.hasNext()) {
			ForwardPresentmentBundle bundle = it.next();

			/* write the bundle header */
			len = RecordLength.intToByteArray(bundle.getBundleHeader().getRecordLength(), 4);
			data = bundle.getBundleHeader().getBytes();
			writeData();

			/* write the item records */
			writeItemData(bundle);

			/* write the bundle control */
			len = RecordLength.intToByteArray(bundle.getBundleControl().getRecordLength(), 4);
			data = bundle.getBundleControl().getBytes();
			writeData();
		}

	}


	/**
	 * Write item data.
	 * 
	 * @param bundle
	 *            the bundle
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void writeItemData(ForwardPresentmentBundle bundle) throws IOException {
		Iterator<ICLItem> it = bundle.getItemList().iterator();
		while (it.hasNext()) {
			ICLItem item = it.next();

			/* write the check detail record */
			len = RecordLength.intToByteArray(item.getDetailRecord().getRecordLength(), 4);
			data = item.getDetailRecord().getBytes();
			writeData();

			/* write any addendum records */
			// TODO: implement writing of other addendum record types
			for (int i = 0; i < item.getCheckDetailAddendumAList().size(); i++) {
				len = RecordLength.intToByteArray(item.getCheckDetailAddendumAList().get(i).getRecordLength(), 4);
				data = item.getCheckDetailAddendumAList().get(i).getBytes();
				writeData();
			}

			for (int i = 0; i < item.getCheckDetailAddendumCList().size(); i++) {
				len = RecordLength.intToByteArray(item.getCheckDetailAddendumCList().get(i).getRecordLength(), 4);
				data = item.getCheckDetailAddendumCList().get(i).getBytes();
				writeData();
			}

			/* write the image view records */
			writeImageData(item);
		}
	}


	/**
	 * Write image data.
	 * 
	 * @param item
	 *            the item
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void writeImageData(ICLItem item) throws IOException {
		Iterator<ImageView> it = item.getImageViewList().iterator();
		while (it.hasNext()) {
			ImageView view = it.next();

			/* write the 50 record */
			len = RecordLength.intToByteArray(view.getImageViewDetail().getRecordLength(), 4);
			data = view.getImageViewDetail().getBytes();
			writeData();

			/* write the 52 record */
			len = RecordLength.intToByteArray(view.getImageViewData().getRecordLength(), 4);
			data = view.getImageViewData().getBytes();
			writeData();

			/* opt - write the 54 record */
			if (view.getImageViewAnalysis() != null) {
				len = RecordLength.intToByteArray(view.getImageViewAnalysis().getRecordLength(), 4);
				data = view.getImageViewAnalysis().getBytes();
				writeData();
			}
		}
	}


	/**
	 * Write data.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void writeData() throws IOException {
		if (oStream == null) {
			log.error("Output stream was null");
			throw new IOException("Output Stream is NULL");
		}
		oStream.write(len);
		/*
		 * change encoding of data if necessary. Currently the way this works is
		 * internally we always have it in ascii. So the only condition we need
		 * to account for is the EBCDIC encoding
		 */
		if (iclFile.getFileEncoding() == FileEncoding.EBCDIC) {
			data = ISOUtil.asciiToEbcdic(data);
		}
		oStream.write(data);
	}


	/**
	 * Gets the icl file.
	 * 
	 * @return the icl file
	 */
	public ICLFile getIclFile() {
		return iclFile;
	}


	/**
	 * Sets the icl file.
	 * 
	 * @param iclFile
	 *            the new icl file
	 */
	public void setIclFile(ICLFile iclFile) {
		this.iclFile = iclFile;
	}


	/**
	 * Gets the file out.
	 * 
	 * @return the file out
	 */
	public File getFileOut() {
		return fileOut;
	}


	/**
	 * Sets the file out.
	 * 
	 * @param fileOut
	 *            the new file out
	 */
	public void setFileOut(File fileOut) {
		this.fileOut = fileOut;
	}
}

class ICLFileWriterTestDrive {
	public static void main(String[] args) {
		try {
			ICLReader reader = new ICLReader(new FileInputStream("c:\\tmp\\file.icl"));
			ICLFile iclFile = reader.readICLFile();
			iclFile.saveAllImages("c:\\tmp");
			File fOut = new File("C:\\tmp\\outFile.icl");
			ICLFileWriter writer = new ICLFileWriter(iclFile, fOut);
			writer.writeFile();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}
}
