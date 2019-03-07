package oeg.legalwhen.core.tutorial.annotador.Annotador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import oeg.annotador.core.servlets.Salida;
import oeg.annotador.core.time.tictag.Annotador;
import org.apache.commons.io.FileUtils;

/**
 * Tutorial of the Annotador tagger Tagging the content of a TEST.txt file with
 * detailed output in OUT-TEST.txt in the BRAT format
 *
 * @author mnavas
 */
public class TutorialESPfromTXTBRAT {

    public static void main(String[] args) throws IOException {
        PrintWriter out;
        File f = new File("../annotador-core/src/main/resources/rules/TEST.txt");
        File outputFile = new File("../annotador-core/src/main/resources/rules/OUT-TEST.txt");
        String input = FileUtils.readFileToString(f, "UTF-8");
        Annotador tt = new Annotador("ES");
        Salida outp = tt.annotateBRAT(input, null);

        FileOutputStream fos = new FileOutputStream(outputFile.getAbsolutePath());
        OutputStreamWriter w = new OutputStreamWriter(fos, "UTF-8");
        BufferedWriter bw = new BufferedWriter(w);
        bw.write(outp.txt);
        bw.flush();
        bw.close();

    }

}
