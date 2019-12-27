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
package org.cdgsoftware.icl.test;

import org.cdgsoftware.icl.record.FileHeaderRecord;

import junit.framework.TestCase;

/**
 * The Class FileHeaderRecordTest.
 */
public class FileHeaderRecordTest extends TestCase {
    private FileHeaderRecord r;

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        r = new FileHeaderRecord();
    }

    /**
     * Test valid fields.
     * 
     * @throws Exception the exception
     */
    public void testValidFields() throws Exception {
        r.standardLevel.setFieldData("03");
        r.immediateDestinationRoutingNumber.setFieldData("999999992");
        r.immediateOriginRoutingNumber.setFieldData("999999992");
        r.immediateDesitinationName.setFieldData("Jeffs Bank");
        r.immediateOriginName.setFieldData("Jeffs Computer");
        r.fileIDModifier.setFieldData("A");
        System.out.println(r.toString());
        assertEquals(80, r.toString().length());
    }

    /**
     * Test invalid level.
     * 
     * @throws Exception the exception
     */
    public void testInvalidLevel() throws Exception {
        try {
            r.standardLevel.setFieldData("04");
            fail("This should have throw an exception");
        } catch (Exception asExpected) {
            System.out.println(asExpected.getMessage());
        }
    }

    /**
     * Test invalid dest aba.
     * 
     * @throws Exception the exception
     */
    public void testInvalidDestABA() throws Exception {
        try {
            r.immediateDestinationRoutingNumber.setFieldData("999999999");
            fail("This should have throw an exception");
        } catch (Exception asExpected) {
            System.out.println(asExpected.getMessage());
        }
    }

    /**
     * Test invalid orig aba.
     * 
     * @throws Exception the exception
     */
    public void testInvalidOrigABA() throws Exception {
        try {
            r.immediateOriginRoutingNumber.setFieldData("999999999");
            fail("This should have throw an exception");
        } catch (Exception asExpected) {
            System.out.println(asExpected.getMessage());
        }
    }

    /**
     * Test invalid dest name.
     * 
     * @throws Exception the exception
     */
    public void testInvalidDestName() throws Exception {
        try {
            r.immediateDesitinationName.setFieldData("*");
            fail("This should have throw an exception");
        } catch (Exception asExpected) {
            System.out.println(asExpected.getMessage());
        }
    }

    /**
     * Test invalid orig name.
     * 
     * @throws Exception the exception
     */
    public void testInvalidOrigName() throws Exception {
        try {
            r.immediateOriginName.setFieldData("!");
            fail("This should have throw an exception");
        } catch (Exception asExpected) {
            System.out.println(asExpected.getMessage());
        }
    }
    
    /**
     * Test invalid modifier.
     * 
     * @throws Exception the exception
     */
    public void testInvalidModifier() throws Exception {
        try {
            r.fileIDModifier.setFieldData("a");
            fail("This should have throw an exception");
        } catch (Exception asExpected) {
            System.out.println(asExpected.getMessage());
        }
    }

}
