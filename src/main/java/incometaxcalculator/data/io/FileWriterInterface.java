package incometaxcalculator.data.io;

import java.io.IOException;

public interface FileWriterInterface {
    void generateFile(int taxRegistrationNumber) throws IOException;

}
