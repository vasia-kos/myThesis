package lawaPackage;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.mortbay.util.ajax.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;

@WebServlet(urlPatterns = "/test2")
public class Servlet2 extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String schema_path="/home/vasia/IdeaProjects/lawa/data/SWDFschema.txt";
        String instance_path="/home/vasia/IdeaProjects/lawa/data/clean_swdf.nt";


        //CREATE FOLDERS IN HDFS-----------------------//
        String partition = request.getParameter("radioValue").toString();
        System.out.println(partition);
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://localhost:9000");
        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());

        //CREATE FOLDERS IN HDFS-----------------------//

        String SWDF = "hdfs://localhost:9000/SWDF";
        FileSystem fs = FileSystem.get(URI.create(SWDF), conf);
        fs.mkdirs(new Path(SWDF));
        String SCHEMA = "hdfs://localhost:9000/SWDF/schema";
        fs.mkdirs(new Path(SCHEMA));
        String INSTANCE = "hdfs://localhost:9000/SWDF/instance";
        fs.mkdirs(new Path(INSTANCE));
        fs.copyFromLocalFile(new Path(schema_path), new Path(SCHEMA));
        fs.copyFromLocalFile(new Path(instance_path), new Path(INSTANCE));


        String hdfs = conf.get("fs.defaultFS");
        String instance = conf.get("fs.defaultFS") + "/SWDF/instance";
        String schema = conf.get("fs.defaultFS") + "/SWDF/schema";

        runQueries p = new runQueries();

        String uri = "hdfs://localhost:9000/SWDF_data/node_index_"+partition;
        System.out.println(uri);
            Path path = new Path(uri);
                try {
                    JSONObject o = p.Partition(partition, path, fs, uri, conf, hdfs, instance, schema);
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json");
                    out.print(o);
                    out.flush();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }





