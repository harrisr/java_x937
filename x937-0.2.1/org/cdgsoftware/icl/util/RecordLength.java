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

import org.apache.log4j.Logger;
import org.jpos.iso.ISOUtil;

/**
 * The Class RecordLength.
 */
public class RecordLength {
	static Logger log = Logger.getLogger(RecordLength.class);


	/**
	 * Convert the byte array to an int.
	 * 
	 * @param b
	 *            The byte array
	 * 
	 * @return The integer
	 */
	public static int byteArrayToInt(byte[] b) {
		return byteArrayToInt(b, 0);
	}


	// public static int byteArrayToInt(byte[] b) {
	// final ByteBuffer bb = ByteBuffer.wrap(b);
	// // bb.order(ByteOrder.LITTLE_ENDIAN);
	// return bb.getInt();
	// }
	//
	
	
	public static byte[] intToByteArray(int a) {
		byte[] ret = new byte[4];
		ret[3] = (byte) (a & 0xFF);
		ret[2] = (byte) ((a >> 8) & 0xFF);
		ret[1] = (byte) ((a >> 16) & 0xFF);
		ret[0] = (byte) ((a >> 24) & 0xFF);
		return ret;
	}


	/**
	 * Convert the byte array to an int starting from the given offset.
	 * 
	 * @param b
	 *            The byte array
	 * @param offset
	 *            The array offset
	 * 
	 * @return The integer
	 */
	public static int byteArrayToInt(byte[] b, int offset) {
		int value = 0;
		for (int i = 0; i < 4; i++) {
			int shift = (4 - 1 - i) * 8;
			value += (b[i + offset] & 0x000000FF) << shift;
		}
		return value;
	}


	/**
	 * Convert the integer value into a byte array of arrayLength bytes.
	 * 
	 * @param value
	 *            the value
	 * @param arrayLength
	 *            the array length
	 * 
	 * @return the byte array
	 */
	public static byte[] intToByteArray(int value, int arrayLength) {
		byte[] b = new byte[arrayLength];
		for (int i = 0; i < arrayLength; i++) {
			int offset = (b.length - 1 - i) * 8;
			b[i] = (byte) ((value >>> offset) & 0xFF);
		}
		return b;
	}
}

class RecordLengthTestDrive {
	public static void main(String[] args) {
		/* example null, null, null, P byte set for an 80 length field */
		byte[] b = new byte[] { (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x50 };
		System.out.println(RecordLength.byteArrayToInt(b));

		int length = 32000;
		byte[] ary = RecordLength.intToByteArray(length, 4);
		System.out.println("[" + ISOUtil.dumpString(ary) + "]");
		System.out.println(Integer.toBinaryString(125));

		length = 800;
		ary = RecordLength.intToByteArray(length, 4);
		System.out.println("[" + ISOUtil.dumpString(ary) + "]");

		length = 901;
		ary = RecordLength.intToByteArray(length, 4);
		System.out.println("[" + ISOUtil.dumpString(ary) + "]");
	}

}
