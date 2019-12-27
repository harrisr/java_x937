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
package org.cdgsoftware.icl.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


/**
 * The Class PacificStandardTime.
 */
public class PacificStandardTime {
	
	/**
	 * Gets the current time in pst.
	 * 
	 * @return the current time in pst
	 */
	public static String getCurrentTimeInPST() {
		Date d = new Date();
		TimeZone estTime = TimeZone.getTimeZone("PST");
		DateFormat format = new SimpleDateFormat("HHmm");
		format.setTimeZone(estTime);
		return format.format(d);
	}
	
	/**
	 * Gets the current date in pst.
	 * 
	 * @return the current date in pst
	 */
	public static String getCurrentDateInPST() {
		Date d = new Date();
		TimeZone estTime = TimeZone.getTimeZone("PST");
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		format.setTimeZone(estTime);
		return format.format(d);
	}

}
