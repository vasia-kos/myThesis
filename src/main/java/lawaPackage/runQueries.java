package lawaPackage;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.spark.launcher.SparkLauncher;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.mortbay.util.ajax.JSON;
import sun.misc.Launcher;

import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class runQueries {
    /*CHANG THE PATHS TO THE PATHS OF YOUR PC----------------------------------*/

    /*FILES IN IDEA PROJECTS -> LAWA PROJECT*/
    String appResource = "/home/vasia/IdeaProjects/lawa/LAWA-1.0-SNAPSHOT.jar";
    String query1 = "/home/vasia/IdeaProjects/lawa/testQueries/query";

    /* FILES IN TOMCAT DIRECTORY*/
    String path1 = "/home/vasia/Desktop/tomcat_9/bin/results/SWDF/4_bal/";
    String path2 = "/home/vasia/Desktop/tomcat_9/bin/NewQueries/translated_queries_4_bal/";
    String path3 = "/home/vasia/Desktop/tomcat_9/bin/NewQueries/translated_queries_2_bal/";
    String path4 = "/home/vasia/Desktop/tomcat_9/bin/NewQueries/translated_queries_8_bal/";
    String path5 = "/home/vasia/Desktop/tomcat_9/bin/results/SWDF/2_bal/";
    String path6 = "/home/vasia/Desktop/tomcat_9/bin/results/SWDF/8_bal/";
    String path7 = "/home/vasia/Desktop/tomcat_9/bin/results/SWDF/";
    String path8 = "/home/vasia/Desktop/tomcat_9/bin/results/";
    String newQueries = "/home/vasia/Desktop/tomcat_9/bin/NewQueries/";
    String translated_queries = "/home/vasia/Desktop/tomcat_9/bin/NewQueries/translated_queries_";
    String results = "/home/vasia/Desktop/tomcat_9/bin/results/SWDF/";
    String query2 = "/home/vasia/Desktop/tomcat_9/bin/NewQueries/query";

    /*PATHS TO ENVIROMENT VARIABLES*/
    String JAVA_HOME = "/usr/lib/jvm/java-8-openjdk-amd64/";
    String SPARK_HOME = "/home/vasia/Documents/dev/spark/spark-2.4.5-bin-hadoop2.7/";
    String HADOOP_DIR = "/home/vasia/Documents/dev/hadoop/hadoop-2.7.1/lib/native";
    String PATH = "/home/vasia/Documents/dev/maven/apache-maven-3.6.3//bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin:/usr/lib/jvm/java-8-openjdk-amd64//bin:/home/vasia/Documents/dev/scala/scala-2.11.8//bin:/home/vasia/Documents/dev/spark/spark-2.4.5-bin-hadoop2.7//bin:/home/vasia/Documents/dev/hadoop/hadoop-2.7.1//bin:/home/vasia/Documents/dev/hadoop/hadoop-2.7.1//sbin:/home/vasia/Documents/dev/hadoop/hadoop-2.7.1//bin:/home/vasia/Documents/dev/hadoop/hadoop-2.7.1/etc/hadoop/";

    // public variables
    String BLAP = "org.ics.isl.BLAP";
    String TRANS = "org.ics.isl.QueryTranslator";
    String PROS = "org.ics.isl.QueryProcessor";
    int execution_time1 = 0;
    int execution_time2 = 0;
    int execution_time3 = 0;
    int total_exe = 0;
    JSONObject obj = new JSONObject();



    public JSONObject runLawa(String partition, int numberQueries) throws IOException, InterruptedException, JSONException {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://localhost:9000");
        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());

        //CREATE FOLDERS IN HDFS-----------------------//

        String hdfs = conf.get("fs.defaultFS");
        String instance = conf.get("fs.defaultFS") + "/SWDF/instance";
        String schema = conf.get("fs.defaultFS") + "/SWDF/schema";


        // DELETE FOLDERS IN ECLIPSE------------------------------------------//

        deleteFolder(path1);
        deleteFolder(path2);
        deleteFolder(path3);
        deleteFolder(path4);
        deleteFolder(path5);
        deleteFolder(path6);
        deleteFolder(path7);
        deleteFolder(path8);



        return runQueries(partition, numberQueries, conf, hdfs);


    }


    public JSONObject Partition(String partition, Path path, FileSystem fs, String uri,
                                Configuration conf, String hdfs, String instance, String schema) throws IOException, InterruptedException, JSONException {

        String[] list1 = {partition, "SWDF", hdfs, instance, schema};
        if (!fs.exists(path)) {

            long startTime = System.nanoTime();
            Launcher(BLAP, list1);
            long endTime = System.nanoTime();

            // get difference of two nanoTime values
            long timeElapsed = endTime - startTime;
            long seconds = TimeUnit.SECONDS.convert(timeElapsed, TimeUnit.NANOSECONDS);
            System.out.println("Execution time in nanoseconds  : " + timeElapsed);
            System.out.println("Execution time in seconds  :" + seconds);

            obj.put("BLAPpartition", seconds);


            String clusterUri = conf.get("fs.defaultFS") + "/SWDF_data/cluster";
            int storage = Storage(conf, clusterUri);
            obj.put("BLAPstorage", storage);
        }


        return obj;

    }


    public JSONObject runQueries(String partition, int numberQueries,Configuration conf, String hdfs )
            throws IOException, InterruptedException, JSONException {

        System.out.println("Partition already made");
        NumberOfQueries(numberQueries);
        String[] list2 = {partition, "SWDF", "1", hdfs, newQueries};
        Launcher(TRANS, list2);
        String[] list3 = {partition, "SWDF", "1", hdfs, translated_queries + partition + "_bal/"};
        Launcher(PROS, list3);

        File bal1 = new File(results + partition + "_bal/results_1_bal.txt");

        if (bal1.exists()) {

            execution_time1 = ExecutionTime(results + partition + "_bal/results_1_bal.txt");

        }

        File bal2 = new File(results + partition + "_bal/results_2_bal.txt");
        if (bal2.exists()) {

            execution_time2 = ExecutionTime(results + partition + "_bal/results_2_bal.txt");

        }
        File bal3 = new File(results + partition + "_bal/results_5_bal.txt");
        if (bal3.exists()) {

            execution_time3 = ExecutionTime(results + partition + "_bal/results_5_bal.txt");

        }
        total_exe = (execution_time1 + execution_time2 + execution_time3);


        JSONObject obj = new JSONObject();
        obj.put("BLAPexeTime", total_exe);

        JSONArray list = new JSONArray();
        try (FileWriter file = new FileWriter("json.json")) {
            file.write(obj.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(obj);
        return obj;


    }


    public int ExecutionTime(String results)  {


         Scanner scan = null;
        File f = new File(results);
        try {
            scan = new Scanner(f);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(0);
        }

        int total = 0;
        boolean foundInts = false; // flag to see if there are any integers
        int i=1;
        int j=0;
        while (scan.hasNextLine()) { // Note change
            String currentLine = scan.nextLine();
            // split into words
            String words[] = currentLine.split(" ");

            // For each word in the line
            if(i==2 || i==j)
            for (String str : words ) {
                try {

                        int num = Integer.parseInt(str);
                                total += num;
                                j=i+4;

                        foundInts = true;
                    System.out.println("Found: " + num);


                } catch (NumberFormatException nfe) {
                }

            }
            i++;
        } // end while

        if (!foundInts)
            System.out.println("No numbers found.");
        else
            System.out.println("Total: " + total);

        // close the scanner
        scan.close();
        return total;

    }

    public void NumberOfQueries(int number) {
        File theNewDir = new File("NewQueries");
        File delDir = new File(newQueries);
        if (theNewDir.exists()) {
            String[] entries = delDir.list();
            for (String s : entries) {
                File currentFile = new File(delDir.getPath(), s);
                currentFile.delete();
            }
        }
        theNewDir.mkdir();
        BufferedReader reader;
        PrintWriter writer;
        String line;
        for (int i = 1; i <= number; i++) {
            if (i != 14 && i != 38 && i != 42 && i != 46 && i != 57 && i != 62 && i != 87 && i != 119 && i != 124
                    && i != 126 && i != 139 && i != 158 && i != 178 && i != 180 && i != 215 && i != 222 && i != 241
                    && i != 248 && i != 250 && i != 244 && i != 161) {
                try {
                    System.out.println(i);
                    File file = new File(query1 + i + ".txt");
                    File copyfile = new File(query2 + i + ".txt");

                    if (copyfile.createNewFile() || !copyfile.createNewFile()) {
                        reader = new BufferedReader(new FileReader(file));
                        writer = new PrintWriter(new FileWriter(copyfile));
                        while ((line = reader.readLine()) != null) {
                            writer.println(line);
                        }
                        reader.close();
                        writer.close();
                    }
                } catch (IOException ioex) {
                    System.err.println("error");
                }
            }
        }

    }

    public int Storage(Configuration conf, String clusterUri) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        Path filenamePath = new Path(clusterUri);
        int bytes = (int) fs.getContentSummary(filenamePath).getSpaceConsumed();
        int megaBytes = (int) ((bytes) * (0.000001));
        return megaBytes;
    }


    public void Launcher(String mainClass, String[] list) throws IOException, InterruptedException {
        Map<String, String> env = new HashMap<>();
        env.put("JAVA_HOME", JAVA_HOME);
        env.put("SPARK_HOME", SPARK_HOME);
        env.put("HADOOP_COMMON_LIB_NATIVE_DIR", HADOOP_DIR);
        env.put("PATH", PATH);
        SparkLauncher launcher = new SparkLauncher(env);

        launcher.setMainClass(mainClass).setMaster("local[8]").setAppResource(appResource)
                .addAppArgs(list);
        Process process = launcher.launch();

        InputStreamReaderRunnable inputStreamReaderRunnable = new InputStreamReaderRunnable(process.getInputStream(),
                "input");
        Thread inputThread = new Thread(inputStreamReaderRunnable, "LogStreamReader input");
        inputThread.start();

        InputStreamReaderRunnable errorStreamReaderRunnable = new InputStreamReaderRunnable(process.getErrorStream(),
                "error");
        Thread errorThread = new Thread(errorStreamReaderRunnable, "LogStreamReader error");
        errorThread.start();

        System.out.println("Waiting for finish...");
        int exitCode = process.waitFor();
        System.out.println("Finished! Exit code:" + exitCode);
    }

    public void deleteFolder(String path) {
        File delDir = new File(path);
        if (delDir.exists()) {
            String[] entries = delDir.list();
            for (String s : entries) {
                File currentFile = new File(delDir.getPath(), s);
                currentFile.delete();
            }
            delDir.delete();
        }
    }
}
