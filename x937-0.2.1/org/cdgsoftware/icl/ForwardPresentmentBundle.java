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

import org.cdgsoftware.icl.record.BundleControlRecord;
import org.cdgsoftware.icl.record.BundleHeaderRecord;

/**
 * The Class ForwardPresentmentBundle.
 */
public class ForwardPresentmentBundle {
	private BundleHeaderRecord bundleHeader;
	private ArrayList<ICLItem> itemList;
	private BundleControlRecord bundleControl;
	// TODO implement additional records
	// private BoxSummary boxSummar;

	static Logger log = Logger.getLogger(ForwardPresentmentBundle.class);

	/**
	 * Instantiates a new forward presentment bundle.
	 */
	public ForwardPresentmentBundle() {
		itemList = new ArrayList<ICLItem>();
	}

	/**
	 * Instantiates a new forward presentment bundle.
	 * 
	 * @param bundleHeader the bundle header
	 * @param itemList the item list
	 * @param bundleControl the bundle control
	 */
	public ForwardPresentmentBundle(BundleHeaderRecord bundleHeader, ArrayList<ICLItem> itemList,
			BundleControlRecord bundleControl) {
		super();
		this.bundleHeader = bundleHeader;
		this.itemList = itemList;
		this.bundleControl = bundleControl;
	}

	/**
	 * Gets the bundle header.
	 * 
	 * @return the bundle header
	 */
	public BundleHeaderRecord getBundleHeader() {
		return bundleHeader;
	}

	/**
	 * Sets the bundle header.
	 * 
	 * @param bundleHeader the new bundle header
	 */
	public void setBundleHeader(BundleHeaderRecord bundleHeader) {
		this.bundleHeader = bundleHeader;
	}

	/**
	 * Gets the item list.
	 * 
	 * @return the item list
	 */
	public ArrayList<ICLItem> getItemList() {
		return itemList;
	}

	/**
	 * Sets the item list.
	 * 
	 * @param itemList the new item list
	 */
	public void setItemList(ArrayList<ICLItem> itemList) {
		this.itemList = itemList;
	}

	/**
	 * Gets the bundle control.
	 * 
	 * @return the bundle control
	 */
	public BundleControlRecord getBundleControl() {
		return bundleControl;
	}

	/**
	 * Sets the bundle control.
	 * 
	 * @param bundleControl the new bundle control
	 */
	public void setBundleControl(BundleControlRecord bundleControl) {
		this.bundleControl = bundleControl;
	}

}
