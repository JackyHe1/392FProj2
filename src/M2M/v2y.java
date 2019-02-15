package M2M;

import MDELite.Marquee1In_1Out;
import MDELite.Utils;
import PrologDB.DB;
import PrologDB.DBSchema;
import PrologDB.Table;
import PrologDB.Tuple;


public class v2y {
    static DB indb, outdb;
    static Table vBox, vAssociation, yumlBox, yumlAssociation;
    
    public static void main(String... args)  {
        Marquee1In_1Out mark = new Marquee1In_1Out(v2y.class, ".vpl.pl", ".ypl.pl", args);
        String inputFileName = mark.getInputFileName();
        String outputFileName = mark.getOutputFileName();
        String appName = mark.getAppName(outputFileName);
        
        // Step 1: read input database, output schema, and create (empty) output db
        indb = DB.readDB(inputFileName);
        DBSchema dbs = DBSchema.readDBSchema(Utils.MDELiteHome()+"libpl/ypl.schema.pl");
        outdb = new DB(appName, dbs);
        
        // Step 2: now perform M2M transformation
        //         convert X.vpl.pl database into X.ypl.pl
        //         I'll get you started
        yumlBox = outdb.getTableEH("yumlBox");
        for (Tuple b : indb.getTableEH("vBox").tuples()) {
            Tuple y = new Tuple(outdb.getTableEH("yumlBox"));
            String fields = b.get("fields").replace("%",";");     //<-- important translation!
            String methods = b.get("methods").replace("%", ";");  //<-- important translation!
            y.setValues(b.get("id"),b.get("type"),b.get("name"),fields,methods,b.get("abst"));
            yumlBox.add(y);
        }
        // you do the rest
        
        // Step 3: output computed ypl.pl database
        outdb.print(outputFileName);
    }
    
}
