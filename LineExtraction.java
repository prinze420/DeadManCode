package com.rage.siapp.extraction.pdf.parse;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.rage.siapp.extraction.pdf.PDFCharacter;
import com.rage.siapp.extraction.pdf.PDFLine;
import com.rage.siapp.extraction.pdf.PDFWord;

public class LineExtraction {

	Map<Integer, List<PDFCharacter>> pageWiseCharacterMap;
	
	List<PDFLine> finalListLine ;
	
	public LineExtraction(Map<Integer, List<PDFCharacter>> pageWiseCharacterMap) {
		this.pageWiseCharacterMap=pageWiseCharacterMap;
		finalListLine = new ArrayList<PDFLine>();
	}

	public void createLines() throws Exception{
		
		PrintWriter writer = new PrintWriter("C:\\Users\\pg0e1373\\Desktop\\Temp.txt");
		
		List<PDFCharacter> listCharacters = new ArrayList<PDFCharacter>();
		for(Map.Entry<Integer, List<PDFCharacter>> entry : pageWiseCharacterMap.entrySet() )
		{
			for(PDFCharacter ch : entry.getValue())
			{
				listCharacters.add(ch);
			}
		}

		
		float prevX1 = 0f;
		float prevY1 = 0f;
		
		float prevX2 = 0f;
		float prevY2 = 0f;
		
		
		
		float curX1 = 0f;
		float curY1 = 0f;
		
		float curX2 = 0f;
		float curY2 = 0f;
		
		
		String line=listCharacters.get(0).getCharacter();;
		String finalLine="";
		List<PDFCharacter> listChars = new ArrayList<PDFCharacter>();
		listChars.add(listCharacters.get(0));
		
		for(int i=1;i<listCharacters.size();i++)
		{
			 
			curX1  = listCharacters.get(i).getX1();
			curY1  = listCharacters.get(i).getY1();
			
			prevX1 = listCharacters.get(i-1).getX1();
			prevX2 = listCharacters.get(i-1).getX2();
			prevY1 = listCharacters.get(i-1).getY1();
			prevY2 = listCharacters.get(i-1).getY2();
			
			
			
			
			
			
			
			
			
			//writer.println(listCharacters.get(i-1)+"  X1 "+prevX1+"  Y1 "+prevY1);
			//writer.println(listCharacters.get(i-1)+"  X2 "+prevX2+"  Y2 "+prevY2);
			//writer.println();
			
			//7 234.681
			//A 45.322468
			
			if( (Math.abs(curY1-prevY1) < 8) )
			{
				line+= listCharacters.get(i).getCharacter();
				listChars.add(listCharacters.get(i));
				
			}
			else
			{
				
				
				finalListLine.add(new PDFLine(line,listChars));
				listChars.clear();
				line="";
				line+= listCharacters.get(i).getCharacter();
				listChars.add(listCharacters.get(i));
			}
			
		}
		
		//for(int i=0;i<finalListLine.size();i++)
		{
			//writer.println(finalListLine.get(i));
		}
		
		
		extractWords(finalListLine);
		writer.close();
	}
	
	
	
	public void extractWords(List<PDFLine> finalListLine) throws Exception
	{
				
		
		
		List<PDFWord> finalWord= new ArrayList<PDFWord>();
		List<PDFCharacter> charInWords = new ArrayList<PDFCharacter>();
		
		PrintWriter writer = new PrintWriter("C:\\Users\\pg0e1373\\Desktop\\Temp.txt");
		for(int i=0;i<finalListLine.size();i++)
		{
			List<PDFCharacter> listChars = finalListLine.get(i).getListChars();
			
			String word="";
			//charInWords.add(listChars.get(0));
			
			
			
			for(int j=0;j<listChars.size();j++)
			{
				
				float curX2 =0f;
				float nextX1=0f;  
				
				
				curX2=listChars.get(j).getX2();
				
				if(j+1 < listChars.size())
				{
				
					nextX1=listChars.get(j+1).getX1();
					
					
					float xDiff = Math.abs( curX2-nextX1 );
					//System.out.println(xDiff);
					
					if( (xDiff < 2) && (listChars.get(j).getCharacter().trim().length()!=0) )
					{
						word+=listChars.get(j).getCharacter().trim();
						charInWords.add(listChars.get(j));
					}
					else
					{
						
						if(charInWords.size()>0)
						{
							word+=listChars.get(j).getCharacter().trim();
							if (listChars.get(j).getCharacter().trim().length()!=0)
							{
								charInWords.add(listChars.get(j));
							}
							finalWord.add( new PDFWord(word,charInWords,0f,0f,0f,0f));
							word="";
							charInWords.clear();
							
						}
						
							
					}
				}
				else
				{
						word+=listChars.get(j).getCharacter().trim();
						charInWords.add(listChars.get(j));
						finalWord.add( new PDFWord(word,charInWords,0f,0f,0f,0f));
						word="";
						charInWords.clear();
							
					
				}
				
				
			}
			
		}
				
		List<PDFWord> cleanFinalWord = new ArrayList<PDFWord>();
		
		 for (int i=0;i<finalWord.size();i++) 
		 {
		      if ( !(finalWord.get(i).getWord().trim().length()==0) ) 
		      { 
		    	  cleanFinalWord.add(finalWord.get(i));
		      }
		 }
		
		
		
		for(int w=0;w<cleanFinalWord.size();w++)
		{
			writer.print(w+"#"+cleanFinalWord.get(w));
			writer.println();
		}

		
		
		writer.close();
	}
	
	public void setBounds(List<PDFWord> cleanFinalWord)
	{
		List<PDFWord> boundFinalWord = new ArrayList<PDFWord>();

		
		
		
	}

}
