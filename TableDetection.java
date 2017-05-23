package com.rage.siapp.extraction.pdf.parse;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.rage.siapp.extraction.pdf.PDFLine;
import com.rage.siapp.extraction.pdf.PDFWord;

public class TableDetection {

	
	List<PDFLine> listLines;
	public TableDetection(List<PDFLine> lines) {
		this.listLines=lines;
	}

	
	
	
	
	public void markTables() throws Exception
	{
		
		PrintWriter writer = new PrintWriter("/home/dell/Desktop/table.txt", "UTF-8");
		for(int i=10;i<listLines.size()-1;i++)
		{
			PDFLine curLine= listLines.get(i);
			PDFLine nextLine= listLines.get(i+1);
			
			
		List<PDFLine> data1=	processCurLine(curLine);
		List<PDFLine> data2=	processNextLine(nextLine);
		
		for(int d=0;d<data2.size();d++)
		{
			process(data1.get(d),data2.get(d));
			
		}
		
			writer.println("data1 : "+data1);
			writer.println("data2 : "+data2);
			writer.println();
			
			
			
		}
		 writer.close();
		
	}
	
	private void process(PDFLine pdfLine, PDFLine pdfLine2) 
	{
		List<PDFWord> words1=pdfLine.getwords();
		List<PDFWord> words2=pdfLine2.getwords();
		
		//for(int i=0;i<words1.size();i++)
			
		
	}





	public List<PDFLine> processCurLine(PDFLine curLine)
	{
		List<PDFWord> words1 =curLine.getwords();
		
		
		List<PDFLine> finalLine = new ArrayList<PDFLine>();
		String line="";
		
		
		float X2CurWord=0f;
		float X1nextWord=0f;
		PDFWord curWord;
		PDFWord nextWord;
		List<PDFWord> wordsInLine = new ArrayList<PDFWord>();
		
		for(int w1=0;w1<words1.size();w1++)
		{	
			curWord = words1.get(w1);
			
			
			if(w1+1 < words1.size())
			{
				nextWord = words1.get(w1+1);
				
				
				X2CurWord =curWord.getCharacters().get(words1.get(w1).getCharacters().size()-1).getX2();
				X1nextWord =nextWord.getCharacters().get(0).getX1();
				
				
				if(X1nextWord <= (X2CurWord+5))
				{
					line+=curWord.getWord()+" ";
					wordsInLine.add(curWord);
				}
				else
				{
					
					line+=curWord.getWord()+"\n";
					wordsInLine.add(curWord);
					finalLine.add(new PDFLine(line,wordsInLine));
					wordsInLine.clear();
					
					line="";
					
				}
			
			}
			else
			{
				line+=curWord.getWord()+"\n";
				wordsInLine.add(curWord);
				finalLine.add(new PDFLine(line,wordsInLine));
				wordsInLine.clear();
				line="";
				
			}
		}
		return finalLine;

	}
	
	
	public List<PDFLine> processNextLine(PDFLine nextLine)
	{
		List<PDFWord> words1 =nextLine.getwords();
		
		
		List<PDFLine> finalLine = new ArrayList<PDFLine>();
		String line="";
		
		float X2CurWord=0f;
		float X1nextWord=0f;
		PDFWord curWord;
		PDFWord nextWord;
		List<PDFWord> wordsInLine = new ArrayList<PDFWord>();
		
		for(int w1=0;w1<words1.size();w1++)
		{	
			curWord = words1.get(w1);
			
			if(w1+1 < words1.size())
			{
				nextWord = words1.get(w1+1);
				
				X2CurWord =curWord.getCharacters().get(words1.get(w1).getCharacters().size()-1).getX2();
				X1nextWord =nextWord.getCharacters().get(0).getX1();
				
				
				if(X1nextWord <= (X2CurWord+5))
				{
					line+=curWord.getWord()+" ";
					wordsInLine.add(curWord);
				}
				else
				{
					
					line+=curWord.getWord()+"\n";
					wordsInLine.add(curWord);
					finalLine.add(new PDFLine(line,wordsInLine));
					wordsInLine.clear();
					line="";
					
				}
			
			}
			else
			{
				
				line+=curWord.getWord()+"\n";
				wordsInLine.add(curWord);
				finalLine.add(new PDFLine(line,wordsInLine));
				wordsInLine.clear();
				line="";
				
			}
		}

		return finalLine;
	}

}
