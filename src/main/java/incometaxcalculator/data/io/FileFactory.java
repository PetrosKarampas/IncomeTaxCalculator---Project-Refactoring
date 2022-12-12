package incometaxcalculator.data.io;

import java.io.File;

public class FileFactory {

    public FileFactory() {}

    public File createInfoFile(String infoDotFormat, int taxRegistrationNumber) {
        if(infoDotFormat.equals("_INFO.xml"))
            return new File(taxRegistrationNumber + "_INFO.xml");
        else
            return new File(taxRegistrationNumber + "_INFO.txt");
    }
}
