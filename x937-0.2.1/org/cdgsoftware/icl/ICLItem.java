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

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.cdgsoftware.icl.record.CheckDetailAddendumARecord;
import org.cdgsoftware.icl.record.CheckDetailAddendumBRecord;
import org.cdgsoftware.icl.record.CheckDetailAddendumCRecord;
import org.cdgsoftware.icl.record.CheckDetailRecord;
import org.cdgsoftware.icl.record.Record;

/**
 * The Class ICLItem.
 */
public class ICLItem {
	private Record detailRecord;
	private ArrayList<CheckDetailAddendumARecord> checkDetailAddendumAList;
	private CheckDetailAddendumBRecord checkDetailAdendumB;
	private ArrayList<CheckDetailAddendumCRecord> checkDetailAddendumCList;
	private ArrayList<ImageView> imageViewList;

	static Logger log = Logger.getLogger(ICLItem.class);

	/**
	 * Instantiates a new iCL item.
	 */
	public ICLItem() {
		checkDetailAddendumAList = new ArrayList<CheckDetailAddendumARecord>();
	}

	/**
	 * Instantiates a new iCL item.
	 * 
	 * @param checkDetail
	 *            the check detail
	 * @param checkDetailAddendumAList
	 *            the check detail addendum a list
	 * @param imageViewList
	 *            the image view list
	 */
	public ICLItem(CheckDetailRecord checkDetail, ArrayList<CheckDetailAddendumARecord> checkDetailAddendumAList,
			ArrayList<ImageView> imageViewList) {
		super();
		this.detailRecord = checkDetail;
		this.checkDetailAddendumAList = checkDetailAddendumAList;
		this.imageViewList = imageViewList;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ICLItem))
			return false;
		ICLItem other = (ICLItem) obj;
		if (detailRecord == null) {
			if (other.detailRecord != null)
				return false;
		} else if (!detailRecord.equals(other.detailRecord))
			return false;
		return true;
	}

	/**
	 * Gets the check detail addendum a list.
	 * 
	 * @return the check detail addendum a list
	 */
	public ArrayList<CheckDetailAddendumARecord> getCheckDetailAddendumAList() {
		return checkDetailAddendumAList != null ? checkDetailAddendumAList : new ArrayList<CheckDetailAddendumARecord>();
	}

	public ArrayList<CheckDetailAddendumCRecord> getCheckDetailAddendumCList() {
			return checkDetailAddendumCList != null ? checkDetailAddendumCList : new ArrayList<CheckDetailAddendumCRecord>();
	}

	public CheckDetailAddendumBRecord getCheckDetailAdendumB() {
		return checkDetailAdendumB;
	}

	public Record getDetailRecord() {
		return detailRecord;
	}

	/**
	 * Gets the image view list.
	 * 
	 * @return the image view list
	 */
	public ArrayList<ImageView> getImageViewList() {
		return imageViewList != null ? imageViewList : new ArrayList<ImageView>();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((detailRecord == null) ? 0 : detailRecord.hashCode());
		return result;
	}

	/**
	 * Sets the check detail addendum a list.
	 * 
	 * @param checkDetailAddendumAList
	 *            the new check detail addendum a list
	 */
	public void setCheckDetailAddendumAList(ArrayList<CheckDetailAddendumARecord> checkDetailAddendumAList) {
		this.checkDetailAddendumAList = checkDetailAddendumAList;
	}

	public void setCheckDetailAddendumCList(ArrayList<CheckDetailAddendumCRecord> checkDetailAddendumCList) {
		this.checkDetailAddendumCList = checkDetailAddendumCList;
	}

	public void setCheckDetailAdendumB(CheckDetailAddendumBRecord checkDetailAdendumB) {
		this.checkDetailAdendumB = checkDetailAdendumB;
	}

	public void setDetailRecord(Record detailRecord) {
		this.detailRecord = detailRecord;
	}

	/**
	 * Sets the image view list.
	 * 
	 * @param imageViewList
	 *            the new image view list
	 */
	public void setImageViewList(ArrayList<ImageView> imageViewList) {
		this.imageViewList = imageViewList;
	}

}
