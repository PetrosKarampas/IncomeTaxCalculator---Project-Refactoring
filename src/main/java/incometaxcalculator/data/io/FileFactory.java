package incometaxcalculator.data.io;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class FileFactory {

    public FileFactory() {}

    public File createInfoFile(@NotNull String underscoreInfoDotExtension, int taxRegistrationNumber) {
        if(underscoreInfoDotExtension.equals("_INFO.xml"))
            return new File(taxRegistrationNumber + "_INFO.xml");
        else
            return new File(taxRegistrationNumber + "_INFO.txt");
    }
}
