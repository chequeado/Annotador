package oeg.annotador.core.time.annotationHandler;

/**
 * Class that stores and outputs as String the information needed for a NIF
 * annotation
 *
 * @author mnavas
 */
public class NIFAnnotation {

    public String header = "";
    public String a = "";
    public String beginIndex = "";
    public String endIndex = "";
    public String isString = "";
    public String referenceContext = "";

    public String toString() {
        return header + a + beginIndex + endIndex + isString + referenceContext + "\n\n";
    }

}
