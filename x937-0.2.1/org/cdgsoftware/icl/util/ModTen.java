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


/**
 * The Class ModTen.
 */
public class ModTen {
	
	/**
	 * Instantiates a new mod ten.
	 */
	private ModTen() {
		super();
	}
	
	 /**
 	 * Check aba.
 	 * 
 	 * @param aba the aba
 	 * 
 	 * @return true, if successful
 	 */
    public static boolean checkAba(String aba) {
        String abaEight = aba.substring(0, 8);
        String checkDigit = aba.substring(aba.length() - 1);
        String calcDigit;
        String weights = "37137137";
        int sum = 0;
        String modulo;

        if (abaEight.length() != 8) {
            return false;
        }

        for (int i = 0; i < 8; i++) {
            int partOne = Integer.parseInt(abaEight.substring(i, i + 1));
            int partTwo = Integer.parseInt(weights.substring(i, i + 1));
            int multiple = partOne * partTwo;
            sum = sum + multiple;
        }

        modulo = Integer.toString(10 - (sum % 10));
        calcDigit = modulo.substring(modulo.length() - 1);

        if (calcDigit.equals(checkDigit)) {
            return true;
        }
        return false;

    }
}

class ModTenTestDrive {
    public static void main(String[] args) {
        String aba = "122000247";
        if (ModTen.checkAba(aba)) {
            System.out.println("ABA: " + aba + " passed!");
        } else {
            System.out.println("ABA: " + aba + " failed!");
        }
        // this one should fail
        aba = "123456789";
        if (ModTen.checkAba(aba)) {
            System.out.println("ABA: " + aba + " failed!");
        } else {
            System.out.println("ABA: " + aba + " passed!");
        }

    }
}


