package eg.iti.shareit.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Mohamed on 2015/07/11.
 */
public class RestUtil {

    // Global Service properties
    private final String PROTOCOL = "http";
    private final String HOST;
    private final int PORT;
    private final String WS_URL;

    // Access Credentials
    private static final String AUTH_USERNAME = "username";
    private static final String AUTH_PASSWORD = "p@$$w0rd";

    // HttpClient
    private HttpClient httpClient;

    Logger log = Logger.getLogger(RestUtil.class.getName());

    private RestUtil(String host, int port, String contextPath) {

        if (contextPath!= null && !contextPath.endsWith("/")) {
            contextPath += "/";
        }

        if (contextPath!= null && !contextPath.startsWith("/")) {
            contextPath = "/"+contextPath;
        }

        HOST = host;
        PORT = port;
        WS_URL = PROTOCOL + "://" + HOST + ":" + PORT + contextPath;


        init();
    }

    /***
     * init httpClient with access credentials
     */
    protected void init() {
        httpClient = HttpClients.createDefault();

//        AuthScope authScope = new AuthScope(HOST, PORT);
//        CredentialsProvider credsProvider = new BasicCredentialsProvider();
//        credsProvider.setCredentials(authScope, new UsernamePasswordCredentials(AUTH_USERNAME, AUTH_PASSWORD));
//        Credentials credential = credsProvider.getCredentials(authScope);

//        httpClient.getCredentialsProvider().setCredentials(authScope, credentie();
    }


    /***
     * @param url         service Url
     * @return response object from web service
     * @throws IOException
     */
    public  String get(String url) throws IOException {
        return get(url, null);
    }

    /***
     * @param path         service path
     * @param params      send parameters
     * @return response object from web service
     * @throws IOException
     */
    public  String get(String path, List<NameValuePair> params) throws IOException {

        if (params != null) {
            path += getParamterAsString(params);
        }

        String url = WS_URL + path;
        HttpGet httpGet = new HttpGet(url);

        return processedRequest(httpGet);
    }

    /***
     * @param path         service path
     * @return response object from web service
     * @throws IOException
     */
    public String post(String path) throws IOException {
        String url = WS_URL + path;
        return post(url, null);
    }

    /***
     * @param path        service path
     * @param params      send parameters
     * @return response object from web service
     * @throws IOException
     */
    public String post(String path, List<NameValuePair> params) throws IOException {
        String url = WS_URL + path;
        HttpPost httpPost = new HttpPost(url);

        if (params != null) {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
        }

        return processedRequest(httpPost);
    }


    /***
     * @param path service path
     * @return response object from web service
     * @throws IOException
     */
    public String delete(String path) throws IOException {
        String url = WS_URL + path;
        return delete(url, null);
    }


    /***
     * @param path   service path
     * @param params send parameters
     * @return response object from web service
     * @throws IOException
     */
    public String delete(String path, List<NameValuePair> params) throws IOException {


        if (params != null) {
            path += getParamterAsString(params);
        }

        String url = WS_URL + path;
        HttpDelete httpDelete = new HttpDelete(url);

        return processedRequest(httpDelete);
    }

    /***
     * @param requestBase request method like HttpPost or HttpGet
     * @return response object from web service
     * @throws IOException
     */
    private String processedRequest(HttpRequestBase requestBase) throws IOException {

        log.log(Level.INFO,"RestUtil: Method " +requestBase.getMethod()+" - "+requestBase.getURI());

        HttpResponse response = httpClient.execute(requestBase);
        HttpEntity entity = response.getEntity();
        Reader reader = new InputStreamReader(entity.getContent());

        StringBuffer sb = new StringBuffer();
        int ch ;

        while( (ch = reader.read()) != -1) {
            sb.append((char) ch);
        }

        log.log(Level.INFO,"RestUtil: response - "+sb.toString() );

        return sb.toString();
    }


    private String getParamterAsString (List<NameValuePair> params) {
        String urlParams = "?";

        for(NameValuePair pair : params) {
            urlParams+=pair.getName()+"="+pair.getValue()+"&";
        }

        return urlParams;
    }
    private static RestUtil instance;

    public static RestUtil getInstance(String host, int port, String contextPath) {
        if (instance == null) {
            synchronized (RestUtil.class) {
                if (instance == null) {
                    instance = new RestUtil(host, port, contextPath);
                }
            }
        }

        return instance;
    }


}