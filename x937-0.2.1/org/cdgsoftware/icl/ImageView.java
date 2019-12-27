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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

import org.cdgsoftware.icl.record.ImageViewAnalysisRecord;
import org.cdgsoftware.icl.record.ImageViewDataRecord;
import org.cdgsoftware.icl.record.ImageViewDetailRecord;

/**
 * The Class ImageView.
 */
public class ImageView {
	private ImageViewDetailRecord imageViewDetail;
	private ImageViewDataRecord imageViewData;
	private ImageViewAnalysisRecord imageViewAnalysis;

	static Logger log = Logger.getLogger(ImageView.class);

	/**
	 * Instantiates a new image view.
	 */
	public ImageView() {
	}

	/**
	 * Instantiates a new image view.
	 * 
	 * @param imageViewDetail the image view detail
	 * @param imageViewData the image view data
	 * @param imageViewAnalysis the image view analysis
	 */
	public ImageView(ImageViewDetailRecord imageViewDetail, ImageViewDataRecord imageViewData,
			ImageViewAnalysisRecord imageViewAnalysis) {
		super();
		this.imageViewDetail = imageViewDetail;
		this.imageViewData = imageViewData;
		this.imageViewAnalysis = imageViewAnalysis;
	}
	
	/**
	 * Save image.
	 * 
	 * @param f the f
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public File saveImage(String namePrefix, String path) throws IOException {
		File outputFile = null;
		if (namePrefix == null) {
			namePrefix = "";
		}
		try {
			String frontRear = "";
			String fileSeparator = System.getProperty("file.separator");
			
			/* prepend and F or R for front or rear view */
			if (imageViewDetail.viewSideIndicator.getFieldData().equals("0")) {
				frontRear = "F";
			} else {
				frontRear = "R";
			}
			
			/* build the filename using the sequence number as identifier */
			String fileName =  path + fileSeparator + namePrefix + frontRear + 
				imageViewData.eceInstitutionItemSequenceNumber.getFieldData().trim() 
				+ ".tiff";
			outputFile = new File(fileName);
			
			if (outputFile.exists()) {
				return outputFile;
			}
			
			FileOutputStream fOut = new FileOutputStream(outputFile);
			fOut.write(imageViewData.imageData.getFieldDataBytes());
			fOut.close();
			log.debug("Wrote file: " + outputFile.getName());
		} catch (IOException e) {
			log.error("Error writing to file", e);
			throw e;
		}
		return outputFile;
	}

	/**
	 * Gets the image view detail.
	 * 
	 * @return the image view detail
	 */
	public ImageViewDetailRecord getImageViewDetail() {
		return imageViewDetail;
	}

	/**
	 * Sets the image view detail.
	 * 
	 * @param imageViewDetail the new image view detail
	 */
	public void setImageViewDetail(ImageViewDetailRecord imageViewDetail) {
		this.imageViewDetail = imageViewDetail;
	}

	/**
	 * Gets the image view data.
	 * 
	 * @return the image view data
	 */
	public ImageViewDataRecord getImageViewData() {
		return imageViewData;
	}

	/**
	 * Sets the image view data.
	 * 
	 * @param imageViewData the new image view data
	 */
	public void setImageViewData(ImageViewDataRecord imageViewData) {
		this.imageViewData = imageViewData;
	}

	/**
	 * Gets the image view analysis.
	 * 
	 * @return the image view analysis
	 */
	public ImageViewAnalysisRecord getImageViewAnalysis() {
		return imageViewAnalysis;
	}

	/**
	 * Sets the image view analysis.
	 * 
	 * @param imageViewAnalysis the new image view analysis
	 */
	public void setImageViewAnalysis(ImageViewAnalysisRecord imageViewAnalysis) {
		this.imageViewAnalysis = imageViewAnalysis;
	}

}
