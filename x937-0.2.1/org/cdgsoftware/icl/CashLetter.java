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

import org.cdgsoftware.icl.record.CashLetterControlRecord;
import org.cdgsoftware.icl.record.CashLetterHeaderRecord;

/**
 * The Class CashLetter.
 */
public class CashLetter {
	private CashLetterHeaderRecord cashLetterHeader;
	private ArrayList<ForwardPresentmentBundle> forwardPresentmentBundleList;
	private ArrayList<ReturnBundle> returnBundleList;
	private CashLetterControlRecord cashLetterControl;
	// TODO: implement additional record
	// private ArrayList<RoutingNumberSummar> routingNumberSummaryList;

	static Logger log = Logger.getLogger(CashLetter.class);

	/**
	 * Instantiates a new cash letter.
	 */
	public CashLetter() {
		forwardPresentmentBundleList = new ArrayList<ForwardPresentmentBundle>();
		returnBundleList = new ArrayList<ReturnBundle>();
	}

	/**
	 * Instantiates a new cash letter.
	 * 
	 * @param cashLetterHeader the cash letter header
	 * @param forwardPresentmentBundleList the forward presentment bundle list
	 * @param returnBundleList the return bundle list
	 * @param cashLetterControl the cash letter control
	 */
	public CashLetter(CashLetterHeaderRecord cashLetterHeader,
			ArrayList<ForwardPresentmentBundle> forwardPresentmentBundleList,
			ArrayList<ReturnBundle> returnBundleList, CashLetterControlRecord cashLetterControl) {
		super();
		this.cashLetterHeader = cashLetterHeader;
		this.forwardPresentmentBundleList = forwardPresentmentBundleList;
		this.returnBundleList = returnBundleList;
		this.cashLetterControl = cashLetterControl;
	}

	/**
	 * Gets the cash letter header.
	 * 
	 * @return the cash letter header
	 */
	public CashLetterHeaderRecord getCashLetterHeader() {
		return cashLetterHeader;
	}

	/**
	 * Sets the cash letter header.
	 * 
	 * @param cashLetterHeader the new cash letter header
	 */
	public void setCashLetterHeader(CashLetterHeaderRecord cashLetterHeader) {
		this.cashLetterHeader = cashLetterHeader;
	}

	/**
	 * Gets the forward presentment bundle list.
	 * 
	 * @return the forward presentment bundle list
	 */
	public ArrayList<ForwardPresentmentBundle> getForwardPresentmentBundleList() {
		return forwardPresentmentBundleList;
	}

	/**
	 * Sets the forward presentment bundle list.
	 * 
	 * @param forwardPresentmentBundleList the new forward presentment bundle list
	 */
	public void setForwardPresentmentBundleList(
			ArrayList<ForwardPresentmentBundle> forwardPresentmentBundleList) {
		this.forwardPresentmentBundleList = forwardPresentmentBundleList;
	}

	/**
	 * Gets the return bundle list.
	 * 
	 * @return the return bundle list
	 */
	public ArrayList<ReturnBundle> getReturnBundleList() {
		return returnBundleList;
	}

	/**
	 * Sets the return bundle list.
	 * 
	 * @param returnBundleList the new return bundle list
	 */
	public void setReturnBundleList(ArrayList<ReturnBundle> returnBundleList) {
		this.returnBundleList = returnBundleList;
	}

	/**
	 * Gets the cash letter control.
	 * 
	 * @return the cash letter control
	 */
	public CashLetterControlRecord getCashLetterControl() {
		return cashLetterControl;
	}

	/**
	 * Sets the cash letter control.
	 * 
	 * @param cashLetterControl the new cash letter control
	 */
	public void setCashLetterControl(CashLetterControlRecord cashLetterControl) {
		this.cashLetterControl = cashLetterControl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cashLetterControl == null) ? 0 : cashLetterControl.hashCode());
		result = prime * result + ((cashLetterHeader == null) ? 0 : cashLetterHeader.hashCode());
		result = prime * result + ((forwardPresentmentBundleList == null) ? 0 : forwardPresentmentBundleList.hashCode());
		result = prime * result + ((returnBundleList == null) ? 0 : returnBundleList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof CashLetter)) return false;
		CashLetter other = (CashLetter) obj;
		if (cashLetterControl == null) {
			if (other.cashLetterControl != null) return false;
		} else if (!cashLetterControl.equals(other.cashLetterControl)) return false;
		if (cashLetterHeader == null) {
			if (other.cashLetterHeader != null) return false;
		} else if (!cashLetterHeader.equals(other.cashLetterHeader)) return false;
		if (forwardPresentmentBundleList == null) {
			if (other.forwardPresentmentBundleList != null) return false;
		} else if (!forwardPresentmentBundleList.equals(other.forwardPresentmentBundleList)) return false;
		if (returnBundleList == null) {
			if (other.returnBundleList != null) return false;
		} else if (!returnBundleList.equals(other.returnBundleList)) return false;
		return true;
	}

}
