package com.kitri.cafe.board.model;

public class BbsDto extends BoardDto{

	private int bseq;
//	private int seq; BoardDto에 있음
	private String orignFile;
	private String saveFile;
	private String saveFolder;
	private long fileSize; // long인 이유는 
	
	
	public int getBseq() {
		return bseq;
	}
	public void setBseq(int bseq) {
		this.bseq = bseq;
	}
	public String getOrignFile() {
		return orignFile;
	}
	public void setOrignFile(String orignFile) {
		this.orignFile = orignFile;
	}
	public String getSaveFile() {
		return saveFile;
	}
	public void setSaveFile(String saveFile) {
		this.saveFile = saveFile;
	}
	public String getSaveFolder() {
		return saveFolder;
	}
	public void setSaveFolder(String saveFolder) {
		this.saveFolder = saveFolder;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	
	
	@Override
	public String toString() {
		return "BbsDto [bseq=" + bseq + ", orignFile=" + orignFile + ", saveFile=" + saveFile + ", saveFolder="
				+ saveFolder + ", fileSize=" + fileSize + "]";
	}
	
	
	
}
