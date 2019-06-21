/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.tagger.corpus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mnavas
 */
public class tempevalTab2plainKey {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here       

        List<List<String>> records = new ArrayList<>();
        List<List<String>> recordsExt = new ArrayList<>();
        List<List<String>> recordsAttr = new ArrayList<>();
        int flagInTimex = 0;
        String foutput = "C:\\Users\\mnavas\\CODE\\OLD_CODE\\data\\datasets\\timeEval\\tempeval2\\tempeval2plainKey\\";
//        try (BufferedReader br = new BufferedReader( new InputStreamReader(new FileInputStream("C:\\Users\\mnavas\\CODE\\OLD_CODE\\data\\datasets\\timeEval\\tempeval2\\tempeval2-test\\spanish\\entities\\base-segmentation.tab"), "UTF8"))) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\mnavas\\CODE\\OLD_CODE\\data\\datasets\\timeEval\\tempeval2\\key\\base-segmentation.tab"), "UTF8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\t");
                records.add(Arrays.asList(values));
            }
        } catch (IOException ex) {
            Logger.getLogger(tempevalTab2plainKey.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\mnavas\\CODE\\OLD_CODE\\data\\datasets\\timeEval\\tempeval2\\key\\timex-extents.tab"), "UTF8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\t");
                recordsExt.add(Arrays.asList(values));
            }
        } catch (IOException ex) {
            Logger.getLogger(tempevalTab2plainKey.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\mnavas\\CODE\\OLD_CODE\\data\\datasets\\timeEval\\tempeval2\\key\\timex-attributes.tab"), "UTF8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\t");
                recordsAttr.add(Arrays.asList(values));
            }

            String currentFile = records.get(0).get(0);
            String text = "";
            for (List<String> r : records) {
                if (!r.get(0).equalsIgnoreCase(currentFile)) {
                    text = text.replaceAll("</TIMEX3> <TIMEX3 tid=\"t\\d+\" type=\"\" value=\"\">([^<]*)", " $1");
                    text = "<?xml version=\"1.0\" ?>\n" +
"<TimeML xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"http://timeml.org/timeMLdocs/TimeML_1.2.1.xsd\">\n" +
"\n" +
"\n" +
"\n" +
"<TEXT>" + text + "</TEXT>\n" +
"\n" +
"\n" +
"</TimeML>";
                    FileOutputStream fos1 = new FileOutputStream(foutput + currentFile.replaceFirst("\\.txt", "\\.xml"));
                    OutputStreamWriter w = new OutputStreamWriter(fos1, "UTF-8");
                    BufferedWriter bw = new BufferedWriter(w);
                    bw.write(text);
                    bw.flush();
                    bw.close();
                    text = "";
                    currentFile = r.get(0);
                    flagInTimex = 0;
                }
                for (List<String> rec : recordsExt) {
                    if (rec.get(0).equalsIgnoreCase(r.get(0)) && rec.get(1).equalsIgnoreCase(r.get(1)) && rec.get(2).equalsIgnoreCase(r.get(2))) {
                        if (flagInTimex == 0) {
                            String val = "";
                            String type = "";

                            for (List<String> recAtt : recordsAttr) {
                                if (recAtt.get(0).equalsIgnoreCase(r.get(0)) && recAtt.get(1).equalsIgnoreCase(r.get(1)) && recAtt.get(2).equalsIgnoreCase(r.get(2)) && recAtt.get(4).equalsIgnoreCase(rec.get(4))) {
                                    if (recAtt.get(6).equalsIgnoreCase("val")) {
                                        val = recAtt.get(7);
                                    }
                                    else if (recAtt.get(6).equalsIgnoreCase("type")) {
                                        type = recAtt.get(7);
                                    }
                                }
                            }
                            text = text + "<TIMEX3 tid=\"" + rec.get(4) + "\" type=\"" + type + "\" value=\"" + val + "\">";
                            flagInTimex = 1;
                        }
                    }
                }
                 if (flagInTimex == 1) {
                        text = text + r.get(3) + "</TIMEX3> ";
                        flagInTimex = 0;
                    }
                 else{
                text = text + r.get(3) + " ";
                 }
            }
            text = text.replaceAll("</TIMEX3> <TIMEX3 tid=\"t\\d+\" type=\"\" value=\"\">([^<]*)", " $1");
                    text = "<?xml version=\"1.0\" ?>\n" +
"<TimeML xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"http://timeml.org/timeMLdocs/TimeML_1.2.1.xsd\">\n" +
"\n" +
"\n" +
"\n" +
"<TEXT>" + text + "</TEXT>\n" +
"\n" +
"\n" +
"</TimeML>";
                    FileOutputStream fos1 = new FileOutputStream(foutput + currentFile.replaceFirst("\\.txt", "\\.xml"));
                    OutputStreamWriter w = new OutputStreamWriter(fos1, "UTF-8");
                    BufferedWriter bw = new BufferedWriter(w);
                    bw.write(text);
                    bw.flush();
                    bw.close();
                    
                
            
        } catch (IOException ex) {
            Logger.getLogger(tempevalTab2plainKey.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
