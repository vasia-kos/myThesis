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

@WebServlet(urlPatterns = "/test")
public class Servlet1 extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        //CREATE FOLDERS IN HDFS-----------------------//
        String partition = request.getParameter("radioValue").toString();
        int number = Integer.parseInt(request.getParameter("numberQueries"));


            runQueries p = new runQueries();

            try {
                JSONObject o = p.runLawa(partition, number);
                System.out.println(o);
                //JSONObject obj = p.runLawa(partition,number);
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
