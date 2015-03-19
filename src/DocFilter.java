/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;

/**
 *
 * @author Николай
 */
public class DocFilter extends DocumentFilter {
  private int currentLength;
  private int maxLength;
  private String regularExpression;
  
  public DocFilter(int currentLength, int maxLength,String patern){
    this.currentLength = currentLength;
    this.maxLength = maxLength;
    regularExpression=patern;
  }

  @Override
  public void remove(FilterBypass fb, int offset, int length) throws BadLocationException{
    currentLength -= length;
    fb.remove(offset, length);
  }
  @Override
  public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException{
     int len=fb.getDocument().getLength();
     String oldStr=new String(fb.getDocument().getText(0, len));
      if((currentLength + string.length()) <= maxLength && testString(oldStr+string)){
            currentLength += string.length();
            fb.insertString(offset, string, attr);
        }
  }
  @Override
  public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr) throws BadLocationException{
    int len=fb.getDocument().getLength();
    String oldStr=new String(fb.getDocument().getText(0, len));
    if((currentLength - length + string.length()) <= maxLength && testString(oldStr+string)){
      currentLength += string.length() - length;
      fb.replace(offset, length, string, attr);
    }
  }
  
  private boolean testString(String str){
        Pattern p = Pattern.compile(regularExpression);  
        Matcher m = p.matcher(str);  
        return m.matches(); 
    }
}
