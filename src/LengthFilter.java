/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;

/**
 *
 * @author Николай
 */
public class LengthFilter extends DocumentFilter {
  private int currentLength;
  private int maxLength;
  
  public LengthFilter(int currentLength, int maxLength){
    this.currentLength = currentLength;
    this.maxLength = maxLength;
  }

  @Override
  public void remove(FilterBypass fb, int offset, int length) throws BadLocationException{
    currentLength -= length;
    fb.remove(offset, length);
  }
  @Override
  public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException{
    if((currentLength + string.length()) <= maxLength){
      currentLength += string.length();
      fb.insertString(offset, string, attr);
    }
  }
  @Override
  public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr) throws BadLocationException{
    if((currentLength - length + string.length()) <= maxLength)
    {
      currentLength += string.length() - length;
      fb.replace(offset, length, string, attr);
    }
  }
}
