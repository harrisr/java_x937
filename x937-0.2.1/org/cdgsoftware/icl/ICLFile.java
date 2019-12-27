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
package org.cdgsoftware.icl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.cdgsoftware.icl.record.FileControlRecord;
import org.cdgsoftware.icl.record.FileHeaderRecord;
import org.cdgsoftware.icl.util.FileEncoding;

/**
 * The Class ICLFile.
 */
public class ICLFile {
	private FileHeaderRecord fileHeader;
	private ArrayList<CashLetter> cashLetterList;
	private FileControlRecord fileControl;
	private FileEncoding fileEncoding;

	static Logger log = Logger.getLogger(ICLFile.class);

	/**
	 * Instantiates a new iCL file.
	 */
	public ICLFile() {
		cashLetterList = new ArrayList<CashLetter>();
	}

	/**
	 * Instantiates a new iCL file.
	 * 
	 * @param fileHeader
	 *            the file header
	 * @param cashLetterList
	 *            the cash letter list
	 * @param fileControl
	 *            the file control
	 */
	public ICLFile(FileHeaderRecord fileHeader, ArrayList<CashLetter> cashLetterList, FileControlRecord fileControl,
			FileEncoding fileEncoding) {
		super();
		this.fileHeader = fileHeader;
		this.cashLetterList = cashLetterList;
		this.fileControl = fileControl;
		this.fileEncoding = fileEncoding;
	}

	/**
	 * Save all images.
	 * 
	 * @param saveImagePath
	 *            the save path
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void saveAllImages(String saveImagePath) throws IOException {
		Iterator<CashLetter> cIterator = cashLetterList.iterator();
		while (cIterator.hasNext()) {
			/* get the cash letter */
			CashLetter cLetter = cIterator.next();

			/* get all of the forward bundles from the letter */
			Iterator<ForwardPresentmentBundle> bIterator = cLetter.getForwardPresentmentBundleList().iterator();
			while (bIterator.hasNext()) {
				ForwardPresentmentBundle bundle = bIterator.next();
				System.out.println(bundle.getBundleHeader().getFieldDataAtPosition(3));

				/* get all of the items from the bundle */
				Iterator<ICLItem> iIterator = bundle.getItemList().iterator();
				while (iIterator.hasNext()) {
					ICLItem item = iIterator.next();
					String namePrefix = null;

					if (item.getDetailRecord().getRecordTypeNumber().equals("61")) {
						namePrefix = "Deposit_Ticket_";
						item.getDetailRecord().dump(System.out, "Deposit Ticket:  ");
					}

					/* get all of the image views from the item */
					Iterator<ImageView> imgIterator = item.getImageViewList().iterator();

					/* finally loop through the images and save the files */
					while (imgIterator.hasNext()) {
						ImageView imgView = imgIterator.next();
						imgView.saveImage(namePrefix, saveImagePath);
					}
				}
			}
		}
	}

	/**
	 * Rebalance file.
	 * 
	 * @return true, if successful
	 */
	public boolean rebalanceFile() {
		// TODO: implement rebalancing
		return true;
	}

	/**
	 * Gets the file header.
	 * 
	 * @return the file header
	 */
	public FileHeaderRecord getFileHeader() {
		return fileHeader;
	}

	/**
	 * Sets the file header.
	 * 
	 * @param fileHeader
	 *            the new file header
	 */
	public void setFileHeader(FileHeaderRecord fileHeader) {
		this.fileHeader = fileHeader;
	}

	/**
	 * Gets the cash letter list.
	 * 
	 * @return the cash letter list
	 */
	public ArrayList<CashLetter> getCashLetterList() {
		return cashLetterList;
	}

	/**
	 * Sets the cash letter list.
	 * 
	 * @param cashLetterList
	 *            the new cash letter list
	 */
	public void setCashLetterList(ArrayList<CashLetter> cashLetterList) {
		this.cashLetterList = cashLetterList;
	}

	/**
	 * Gets the file control.
	 * 
	 * @return the file control
	 */
	public FileControlRecord getFileControl() {
		return fileControl;
	}

	/**
	 * Sets the file control.
	 * 
	 * @param fileControl
	 *            the new file control
	 */
	public void setFileControl(FileControlRecord fileControl) {
		this.fileControl = fileControl;
	}

	public FileEncoding getFileEncoding() {
		return fileEncoding;
	}

	public void setFileEncoding(FileEncoding fileEncoding) {
		this.fileEncoding = fileEncoding;
	}

}
