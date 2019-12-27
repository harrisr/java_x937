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
package org.cdgsoftware.icl.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

import org.apache.log4j.Logger;

import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.TIFFDecodeParam;
import com.sun.media.jai.codec.TIFFEncodeParam;
import com.sun.media.jai.codec.TIFFField;
import com.sun.media.jai.codecimpl.TIFFCodec;
import com.sun.media.jai.codecimpl.TIFFImageEncoder;

public class TiffTextWriter {
	static Logger log = Logger.getLogger(TiffTextWriter.class);
	private String imageFileName;
	private int fontHeight;
	private ArrayList<String> textToWrite;
	private BufferedImage inputImage;
	private int xOffset;
	private int yOffset;
	private boolean rotateText90Degrees;
	private Graphics2D g2;

	public TiffTextWriter() {
	}

	public TiffTextWriter(String imageFileName, ArrayList<String> textToWrite) {
		super();
		this.imageFileName = imageFileName;
		this.textToWrite = textToWrite;
		this.fontHeight = 18;
		this.xOffset = 300;
		this.yOffset = 200;
		this.rotateText90Degrees = false;
	}

	public TiffTextWriter(String imageFileName, int fontHeight, ArrayList<String> textToWrite) {
		super();
		this.imageFileName = imageFileName;
		this.fontHeight = fontHeight;
		this.textToWrite = textToWrite;
		this.xOffset = 300;
		this.yOffset = 200;
		this.rotateText90Degrees = false;
	}

	public TiffTextWriter(String imageFileName, int fontHeight, ArrayList<String> textToWrite,
			int xOffset, int yOffset, boolean rotateBeforeWrite) {
		super();
		this.imageFileName = imageFileName;
		this.fontHeight = fontHeight;
		this.textToWrite = textToWrite;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.rotateText90Degrees = rotateBeforeWrite;
	}

	public File getNewImage() throws IllegalArgumentException {
		if (imageFileName.equals("") || textToWrite == null) {
			throw new IllegalArgumentException("Output image and/or text not properly set");
		}
		if (!imageFileName.toLowerCase().endsWith("tiff")
				&& !imageFileName.toLowerCase().endsWith("tif")) {
			throw new IllegalArgumentException("Input file must be a tiff image");
		}

		inputImage = openImage();
		paint();

		try {
			FileOutputStream oStream = new FileOutputStream(imageFileName);

			int xres_tag = 282;
			int yres_tag = 283;
			long[] resolution = { 200, 1 };

			TIFFField xRes = new TIFFField(xres_tag, TIFFField.TIFF_RATIONAL, 1,
					new long[][] { resolution });
			TIFFField yRes = new TIFFField(yres_tag, TIFFField.TIFF_RATIONAL, 1,
					new long[][] { resolution });

			TIFFEncodeParam param = new TIFFEncodeParam();
			param.setCompression(TIFFEncodeParam.COMPRESSION_GROUP4);
			param.setLittleEndian(true);
			param.setExtraFields(new TIFFField[] { xRes, yRes });

			TIFFImageEncoder encoder = (TIFFImageEncoder) TIFFCodec.createImageEncoder("tiff",
					oStream, param);

			RenderedImage rImage = (RenderedImage) inputImage;
			encoder.encode(rImage);

			oStream.close();

		} catch (IOException e) {
			log.warn("Error writing new image file");
		}

		return new File(imageFileName);
	}

	private void paint() {
		g2 = getGraphicsFromImage();

		int lines = textToWrite.size();
		int xCoord = xOffset;
		int yCoord = yOffset;
		if (lines > 1) {
			yCoord = yOffset - ((fontHeight + 2) * (lines / 2));
		}

		if (rotateText90Degrees) {
			g2.rotate(90 * Math.PI / 180);
		}

		Iterator<String> it = textToWrite.iterator();
		while (it.hasNext()) {
			g2.drawString(it.next(), xCoord, yCoord);
			yCoord += (fontHeight + 2);
		}
	}

	private Graphics2D getGraphicsFromImage() {
		if (g2 == null) {
			g2 = inputImage.createGraphics();
			Font font = new Font("Serif", Font.BOLD, fontHeight);

			g2.setColor(Color.black);
			g2.setFont(font);
		}
		return g2;
	}

	private BufferedImage openImage() {
		FileSeekableStream stream;
		BufferedImage img = null;
		try {
			URL resource = getClass().getResource("checkTemplate.tif");
			stream = new FileSeekableStream(resource.getFile());
			TIFFDecodeParam decodeParam = new TIFFDecodeParam();
			decodeParam.setDecodePaletteAsShorts(true);

			ParameterBlock params = new ParameterBlock();
			params.add(stream);
			RenderedOp image1 = JAI.create("tiff", params);
			img = image1.getAsBufferedImage();
		} catch (IOException e) {
			log.warn("Error opening input tiff image", e);
		}
		return img;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public ArrayList<String> getTextToWrite() {
		return textToWrite;
	}

	public void setTextToWrite(ArrayList<String> textToWrite) {
		this.textToWrite = textToWrite;
	}

	public int getFontHeight() {
		return fontHeight;
	}

	public void setFontHeight(int fontHeight) {
		this.fontHeight = fontHeight;
	}

	public int getxOffset() {
		return xOffset;
	}

	public void setxOffset(int xOffset) {
		this.xOffset = xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}

	public void setyOffset(int yOffset) {
		this.yOffset = yOffset;
	}

	public boolean isRotateBeforeWrite() {
		return rotateText90Degrees;
	}

	public void setRotateBeforeWrite(boolean rotateBeforeWrite) {
		this.rotateText90Degrees = rotateBeforeWrite;
	}

	public Graphics2D getG2() {
		return g2;
	}

	public void setG2(Graphics2D g2) {
		this.g2 = g2;
	}

}

class TiffTextWriterTestDrive {
	public static void main(String[] args) {
		ArrayList<String> text = new ArrayList<String>();
		String text1 = "Merchant: Test Merchant";
		String text2 = "MerchantID: 12345678";
		String text3 = "Deposit Amount: $123.45";
		String text4 = "Item Count: 47";
		String text5 = "Deposit Date: 01/14/2010";
		String text6 = "";
		String text7 = "Deposit Account: 0123456789";
		text.add(text1);
		text.add(text2);
		text.add(text3);
		text.add(text4);
		text.add(text5);
		text.add(text6);
		text.add(text7);

		String fileName = "C:\\tmp\\testImage.tif";
		TiffTextWriter t = new TiffTextWriter(fileName, text);
		// t.setRotateBeforeWrite(true);
		// t.setxOffset(50);
		// t.setyOffset(-800);
		t.setRotateBeforeWrite(false);
		t.getNewImage();
	}
}
