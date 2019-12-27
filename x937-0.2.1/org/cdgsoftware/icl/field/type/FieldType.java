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
package org.cdgsoftware.icl.field.type;

import java.io.PrintStream;

import org.jpos.util.Loggeable;

/**
 * The Class FieldType.
 */
public abstract class FieldType implements Loggeable {
	protected String name;
	protected String shortName;
	protected String description;


	/**
	 * Instantiates a new field type abstractly of course.
	 * 
	 * @param name the name
	 * @param shortName the short name
	 * @param description the description
	 */
	public FieldType(String name, String shortName, String description) {
		super();
		this.name = name;
		this.shortName = shortName;
		this.description = description;
	}
	

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 * 
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	

	/**
	 * Gets the short name.
	 * 
	 * @return the short name
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * Sets the short name.
	 * 
	 * @param shortName the new short name
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * Checks if is data valid.
	 * 
	 * @param data the data
	 * 
	 * @return true, if is data valid
	 */
	public abstract boolean isDataValid(String data);

	/* (non-Javadoc)
	 * @see org.cdgsoftware.icl.util.Loggeable#dump(java.io.PrintStream, java.lang.String)
	 */
	public void dump(PrintStream p, String indent) {
		p.println(indent + "name=\"" + name + "\" description=\"" + description
				+ "\"");
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof FieldType)) return false;
		FieldType other = (FieldType) obj;
		if (description == null) {
			if (other.description != null) return false;
		} else if (!description.equals(other.description)) return false;
		if (name == null) {
			if (other.name != null) return false;
		} else if (!name.equals(other.name)) return false;
		if (shortName == null) {
			if (other.shortName != null) return false;
		} else if (!shortName.equals(other.shortName)) return false;
		return true;
	}
}
