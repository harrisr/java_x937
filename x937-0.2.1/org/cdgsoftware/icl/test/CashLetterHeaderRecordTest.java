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

import junit.framework.TestCase;

import org.cdgsoftware.icl.record.CashLetterHeaderRecord;

/**
 * The Class CashLetterHeaderRecordTest.
 */
public class CashLetterHeaderRecordTest extends TestCase {
    private CashLetterHeaderRecord r;

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() {
        r = new CashLetterHeaderRecord();
    }

    /**
     * Test valid values.
     * 
     * @throws Exception the exception
     */
    public void testValidValues() throws Exception {
        r.collectionTypeIndicator.setFieldData("01");
        r.destinationRoutingNumber.setFieldData("999999992");
        r.eceInstitutionRoutingNumber.setFieldData("999999992");
        r.cashLetterRecordTypeIndicator.setFieldData("I");
        r.cashLetterDocumentationTypeIndicator.setFieldData("G");
        r.cashLetterID.setFieldData("1");
        r.originatorContactName.setFieldData("Jeff Gordy");
        r.originatorContactPhoneNumber.setFieldData("9495551212");
        System.out.println(r.toString());
        assertEquals(80, r.toString().length());
    }

    /**
     * Test invalid collection type.
     * 
     * @throws Exception the exception
     */
    public void testInvalidCollectionType() throws Exception {
        try {
            r.collectionTypeIndicator.setFieldData("04");
            fail("This should have throw an exception");
        } catch (Exception asExpected) {
            System.out.println(asExpected.getMessage());
        }
    }

    /**
     * Test invalid aba.
     * 
     * @throws Exception the exception
     */
    public void testInvalidABA() throws Exception {
        try {
            r.destinationRoutingNumber.setFieldData("123456789");
            fail("This should have throw an exception");
        } catch (Exception asExpected) {
            System.out.println(asExpected.getMessage());
        }
    }

    /**
     * Test invalid record type.
     * 
     * @throws Exception the exception
     */
    public void testInvalidRecordType() throws Exception {
        try {
            r.cashLetterRecordTypeIndicator.setFieldData("X");
            fail("This should have throw an exception");
        } catch (Exception asExpected) {
            System.out.println(asExpected.getMessage());
        }
    }

    /**
     * Test invalid id.
     * 
     * @throws Exception the exception
     */
    public void testInvalidID() throws Exception {
        try {
            r.cashLetterID.setFieldData("321654987654");
            fail("This should have throw an exception");
        } catch (Exception asExpected) {
            System.out.println(asExpected.getMessage());
        }
    }

    /**
     * Test invalid phone.
     * 
     * @throws Exception the exception
     */
    public void testInvalidPhone() throws Exception {
        try {
            r.originatorContactPhoneNumber.setFieldData("19495551212");
            fail("This should have throw an exception");
        } catch (Exception asExpected) {
            System.out.println(asExpected.getMessage());
        }
    }
    
    /**
     * Test collection change.
     * 
     * @throws Exception the exception
     */
    public void testCollectionChange() throws Exception {
        /* verify that changes are propagated to member variables */
        r.setFieldData(11, "Gordy Jeff");
        assertEquals("Gordy Jeff", r.originatorContactName.getFieldData().trim());
    }

}
