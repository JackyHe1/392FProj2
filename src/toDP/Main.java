
package toDP;

import MDELite.Marquee1In_1DirOut;
import PrologDB.DB;
import java.io.File;
import java.io.PrintStream;


public class Main extends MDELite.RunningBear {
    
    static DB db;
    
    public static void main(String... args) throws Exception {
        Marquee1In_1DirOut mark = new Marquee1In_1DirOut(Main.class, ".fsm.pl", "Java", args);
        String infile = mark.getInputFileName();
        String outdir = mark.outputDirectoryName();
        String AppName = mark.getAppName(infile);

        // Step 2: make sure that the outputDir is ok
        File od = new File(outdir);
        if (od.exists()) {
            if (!od.isDirectory()) {
                System.out.format("%s is a file not a directory - cannot proceed\n", outdir);
                System.exit(0);
            }
        } else {
            if (!od.mkdir()) {
                System.out.format("cannot create directory %s -- stop\n", outdir);
                System.exit(0);
            }
        }
        
        // Step 3: read database
        db = DB.readDB(infile);
        
        // Step 4: initialize RB output
        PrintStream o = new PrintStream(outdir+"/"+AppName+".java");
        openOut(o);
        
        // Step 5: this is where I stop and you continue
        //         write the M2T transformation here
        db.print(o);  // replace this
        
        closeOut();
    }
    
}
