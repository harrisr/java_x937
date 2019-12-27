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

import java.util.Arrays;

import org.jpos.iso.ISOUtil;

public enum FileEncoding {
	ASCII, EBCDIC, UNKNOWN;
	
	public static FileEncoding determineEncoding(byte[] fileHeaderRecordBytes) throws ICLException {
		byte[] EBCDIC_01 = {(byte)0xf0, (byte)0xf1};
		byte[] ASCII_01 = {(byte)0x30, (byte)0x31};
		byte[] recordType = ISOUtil.trim(fileHeaderRecordBytes, 2);
		if (Arrays.equals(recordType, EBCDIC_01)) {
			return FileEncoding.EBCDIC;
		} else if (Arrays.equals(recordType, ASCII_01)) {
			return FileEncoding.ASCII;
		} else {
			throw new ICLException("File Header Record Bytes do not appear to be either ASCII encoded or EBCDIC encoded");
		}
	}
}
