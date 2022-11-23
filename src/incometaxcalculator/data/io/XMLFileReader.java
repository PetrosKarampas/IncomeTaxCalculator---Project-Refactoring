package incometaxcalculator.data.io;

import incometaxcalculator.exceptions.WrongFileFormatException;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;

public class XMLFileReader extends FileReader {
  protected int getReceiptID(@NotNull String @NotNull [] values){
    if(values[0].equals("<ReceiptID>"))
      return Integer.parseInt(values[1].trim());

    return -1;
  }
  protected String getLastValueFromLine(String fieldsLine) throws WrongFileFormatException
  {
    try {
      String[] valueWithTail = fieldsLine.split(" ", 2);
      String[] valueReversed = new StringBuilder(valueWithTail[1]).reverse().toString().trim().split(" ", 2);
      return new StringBuilder(valueReversed[1]).reverse().toString();
    } catch (NullPointerException e) {
      System.out.println(Arrays.toString(e.getStackTrace()));
      throw new WrongFileFormatException();
    }
  }
}
