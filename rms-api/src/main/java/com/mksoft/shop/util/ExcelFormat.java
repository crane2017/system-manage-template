package com.mksoft.shop.util;

public class ExcelFormat {

	/**
	 * 字号
	 */
	private short fontSize;
	/**
	 * 字体
	 */
	private String fontName;
	/**
	 * 字体是否加粗
	 */
	private boolean isBorder;
	/**
	 * 下边框
	 */
	private short bottomBorder;	
	/**
	 * 上边框
	 */
	private short topBorder;
	/**
	 * 左边框
	 */
	private short leftBorder;
	/**
	 * 有边框
	 */
	private short rightBorder;
	
	public short getFontSize() {
		return fontSize;
	}
	public void setFontSize(short fontSize) {
		this.fontSize = fontSize;
	}
	public String getFontName() {
		return fontName;
	}
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}
	public boolean isBorder() {
		return isBorder;
	}
	public void setBorder(boolean isBorder) {
		this.isBorder = isBorder;
	}
	public short getBottomBorder() {
		return bottomBorder;
	}
	public void setBottomBorder(short bottomBorder) {
		this.bottomBorder = bottomBorder;
	}
	public short getTopBorder() {
		return topBorder;
	}
	public void setTopBorder(short topBorder) {
		this.topBorder = topBorder;
	}
	public short getLeftBorder() {
		return leftBorder;
	}
	public void setLeftBorder(short leftBorder) {
		this.leftBorder = leftBorder;
	}
	public short getRightBorder() {
		return rightBorder;
	}
	public void setRightBorder(short rightBorder) {
		this.rightBorder = rightBorder;
	}
}
