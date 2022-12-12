package incometaxcalculator.data.io;

import incometaxcalculator.exceptions.WrongFileFormatException;
import java.util.Arrays;

public class TXTFileReader extends FileReader {

  @Override
  protected int getReceiptID(String[] values) {
    if (values[0].equals("Receipt")) {
      if (values[1].equals("ID:"))
        return Integer.parseInt(values[2].trim());
    }
    return -1;
  }

  @Override
  protected String getLastValueFromLine(String fieldsLine) throws WrongFileFormatException {
    try {
      String[] values = fieldsLine.split(" ", 2);
      values[1] = values[1].trim();
      return values[1];
    }
    catch (NullPointerException e) {
        System.out.println(Arrays.toString(e.getStackTrace()));
        throw new WrongFileFormatException();
    }
  }
}