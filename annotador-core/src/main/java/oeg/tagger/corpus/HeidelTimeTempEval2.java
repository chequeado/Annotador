/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.tagger.corpus;

import oeg.tagger.core.tutorial.annotador.*;
import edu.stanford.nlp.pipeline.Annotation;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oeg.tagger.core.time.tictag.Annotador;
import oeg.tagger.core.time.tictag.TicTag;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author mnavas
 */
public class HeidelTimeTempEval2 {

    /**
     * Annotation of the files (in Spanish, just training) from TempEval3
     */
    public static void main(String[] args) { 
        FileOutputStream fos = null;
        try {
            File finput = new File("C:\\Users\\mnavas\\CODE\\OLD_CODE\\data\\datasets\\timeEval\\tempeval2\\tempeval2plain\\");
            String foutput = "C:\\Users\\mnavas\\CODE\\OLD_CODE\\data\\datasets\\timeEval\\tempeval2\\tempeval2Heidel.txt";
            String foutput2 = "C:\\Users\\mnavas\\CODE\\OLD_CODE\\data\\datasets\\timeEval\\tempeval2\\tempeval2Heidel\\";
//            String foutputHTML = "../annotador-core/src/main/resources/rules/output.html";
            File[] listF = finput.listFiles();
            String outp = "";
            Pattern pText = Pattern.compile("\\d+_(\\d{4})(\\d{2})(\\d{2})");
            for (File f : listF){
                try {
//                    System.out.println(txt + "\n");
                    Matcher mText = pText.matcher(f.getName());
                    String date = "2019-12-20";
                    if (mText.find()) {
                        date = mText.group(1) + "-" + mText.group(2) + "-" + mText.group(3);
                    }
                    outp = outp + "java -jar C:\\heideltime-standalone-2.2.1\\heideltime-standalone\\de.unihd.dbs.heideltime.standalone.jar -l spanish -t news " + f.getAbsolutePath() + " -dct " + date + " > " + foutput2 + f.getName() + "\n";
                    
                    
//                    outp = "<?xml version=\"1.0\" ?>\n" +
//"<TimeML xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"http://timeml.org/timeMLdocs/TimeML_1.2.1.xsd\">\n" +
//"\n" +
//"\n" +
//"\n" +
//"<TEXT>" + outp + "</TEXT>\n" +
//"\n" +
//"\n" +
//"</TimeML>";
                                        

                    
                } catch (Exception ex) {
                    Logger.getLogger(HeidelTimeTempEval2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }   
            
                    FileOutputStream fos1 = new FileOutputStream(foutput);
                    OutputStreamWriter w = new OutputStreamWriter(fos1, "UTF-8");
                    BufferedWriter bw = new BufferedWriter(w);
                    bw.write(outp);
                    bw.flush();
                    bw.close();
        } catch (Exception ex) {
            Logger.getLogger(HeidelTimeTempEval2.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    static public String createHighlights(String input2) {
//        input2 = input2.replaceFirst(Pattern.quote("<?xml version=\"1.0\"?>\n" + "<!DOCTYPE TimeML SYSTEM \"TimeML.dtd\">\n" + "<TimeML>"), "");
        input2 = input2.replaceFirst(Pattern.quote("</TimeML>"), "");
        input2 = input2.replaceAll("</TIMEX3>", "</span>");
        input2 = input2.replaceAll("\\r?\\n", "<br>");

        String pattern = "(<TIMEX3 ([^>]*)>)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(input2);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String color = "#7fa2ff";//"Orange";
//            String color = "rgba(255, 165, 0, 0.5)";//"Orange";
            String contetRegex = m.group(2);
            contetRegex = contetRegex.replaceAll("\"", "");
            contetRegex = contetRegex.replaceAll(" ", "\n");
            if (contetRegex.contains("SET")) {
                color = "#ccb3ff";//DodgerBlue";
//                color = "rgba(135, 206, 235, 0.5)";//DodgerBlue";
            } else if (contetRegex.contains("DURATION")) {
                color = "#99ffeb"; //Tomato
//                color = "hsla(9, 100%, 64%, 0.5)"; //Tomato
            } else if (contetRegex.contains("TIME")) {
                color = "#ffbb99";//"MediumSeaGreen";
//                color = "rgba(102, 205, 170, 0.5)";//"MediumSeaGreen";
            }

            String aux2 = m.group(0);
            aux2 = aux2.replace(">", "");

            m.appendReplacement(sb, aux2.replaceFirst(Pattern.quote(aux2), "<span style=\"background-color:"
                    + color + "\" title=\"" + contetRegex + "\">"));
        }
        m.appendTail(sb); // append the rest of the contents
        
        String saux = sb.toString();

        return saux;
    }

}