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
package org.cdgsoftware.icl.field;

import java.io.PrintStream;

import org.jpos.util.Loggeable;

/**
 * The Class Position.
 */
public class Position implements Loggeable {

	/** The start. */
	private int start;

	/** The end. */
	private int end;

	/** The length. */
	private int length;


	/**
	 * Instantiates a new position.
	 */
	public Position() {
		this.start = 0;
		this.end = 0;
	}


	/**
	 * Instantiates a new position.
	 * 
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 */
	public Position(int start, int end) {
		this.start = start;
		this.end = end;
		calculateLength();
	}


	/**
	 * Gets the start.
	 * 
	 * @return the start
	 */
	public int getStart() {
		return start;
	}


	/**
	 * Sets the start.
	 * 
	 * @param start
	 *            the new start
	 */
	public void setStart(int start) {
		this.start = start;
		calculateLength();
	}


	/**
	 * Gets the end.
	 * 
	 * @return the end
	 */
	public int getEnd() {
		return end;
	}


	/**
	 * Sets the end.
	 * 
	 * @param end
	 *            the new end
	 */
	public void setEnd(int end) {
		this.end = end;
		calculateLength();
	}


	/**
	 * Gets the length.
	 * 
	 * @return the length
	 */
	public int getLength() {
		return length;
	}


	/**
	 * Calculate length.
	 */
	private void calculateLength() {
		this.length = (end - start) + 1;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cdgsoftware.icl.util.Loggeable#dump(java.io.PrintStream,
	 * java.lang.String)
	 */
	public void dump(PrintStream p, String indent) {
		p.println(indent + " startPosition=" + start + " endPosition=" + end);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return start + "-" + end;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + end;
		result = prime * result + length;
		result = prime * result + start;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Position)) return false;
		Position other = (Position) obj;
		if (end != other.end) return false;
		if (length != other.length) return false;
		if (start != other.start) return false;
		return true;
	}

}
