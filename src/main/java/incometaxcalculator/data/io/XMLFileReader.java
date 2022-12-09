package incometaxcalculator.data.io;

import incometaxcalculator.exceptions.WrongFileFormatException;
import java.util.Arrays;

public class XMLFileReader extends FileReader
{
  @Override
  protected int getReceiptID(String[] values)
  {
    if (values[0].equals("<ReceiptID>"))
      return Integer.parseInt(values[1].trim());
    return -1;
  }
  @Override
  protected String getLastValueFromLine(String fieldsLine) throws WrongFileFormatException
  {
    try
    {
      String[] valueWithTail = fieldsLine.split(" ", 2);
      String[] valueReversed = new StringBuilder(valueWithTail[1]).reverse().toString().trim().split(" ", 2);
      return new StringBuilder(valueReversed[1]).reverse().toString();
    }
    catch (NullPointerException e)
    {
      System.out.println(Arrays.toString(e.getStackTrace()));
      throw new WrongFileFormatException();
    }
  }
}
