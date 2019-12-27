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

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

/**
 * The Class FileUtils.
 */
public class FileUtils {
	static Logger log = Logger.getLogger(FileUtils.class);

	/**
	 * BufferedReaders are really useful for reading files (or other streams for
	 * that matter) line by line. This is a utility to get a reader from a file
	 * without having to muck up your code with all the new blah blah
	 * statements.
	 * 
	 * @param src the src
	 * 
	 * @return BufferedReader
	 */
	public static BufferedReader getBufferedReaderFromFile(File src) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(
					src))));
		} catch (FileNotFoundException e) {
			log.error("src file: " + src.getName() + "was not found.", e);
			return null;
		}
		return br;
	}

	/**
	 * BufferedReaders are really useful for reading files (or other streams for
	 * that matter) line by line. This is a utility to get a reader from a file
	 * without having to muck up your code with all the new blah blah
	 * statements.
	 * 
	 * @param src the src
	 * 
	 * @return BufferedReader
	 */
	public static BufferedReader getBufferedReaderFromInputStream(InputStream src) {
		BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(src)));
		return br;
	}

}
