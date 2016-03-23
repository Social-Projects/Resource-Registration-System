package org.registrator.community.dto;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.registrator.community.service.impl.PrintServiceImpl;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;


public class HeaderDTO {
	private Image image;
	private List <HeaderContent> headerText;

	
	public class HeaderContent{
		private String stringContent;
		private  int fontSize;
		private Font fontType;
		
		public HeaderContent(String stringContent, int fontSize, Font fontType) {
			this.stringContent = stringContent;
			this.fontSize = fontSize;
			this.fontType = fontType;
		}
		
		public String getStringContent() {
			return stringContent;
		}
		public void setStringContent(String stringContent) {
			this.stringContent = stringContent;
		}
		public int getFontSize() {
			return fontSize;
		}
		public void setFontSize(int fontSize) {
			this.fontSize = fontSize;
		}
		public Font getFontType() {
			return fontType;
		}
		public void setFontType(Font fontType) {
			this.fontType = fontType;
		}
		
		
		
	}
	
	
			public	HeaderDTO(){
					headerText=new ArrayList<>();
			}
	
	
			public Image getImage() {
				return image;
			}
		
			public void setImage(String imageURL, int widthOfImage, int heightOfImage) throws BadElementException, MalformedURLException, IOException{
				URL url   = PrintServiceImpl.class.getResource(imageURL);
				image = Image.getInstance(url);
				image.scaleAbsolute(widthOfImage, heightOfImage);
				image.setAlignment(Element.ALIGN_CENTER);
			}
			
			public void addSentenceToHeaderContent(String sentence, int fontSize, Font fontType){
				HeaderContent current=new HeaderContent(sentence, fontSize, fontType);
				headerText.add(current);			
			}


			public List<HeaderContent> getHeaderText() {
				return headerText;
			}


		

			
			
}
