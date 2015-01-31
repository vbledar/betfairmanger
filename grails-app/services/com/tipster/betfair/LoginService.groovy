package com.tipster.betfair

import grails.transaction.Transactional
import net.sf.json.JSON
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.conn.ClientConnectionManager
import org.apache.http.conn.scheme.Scheme
import org.apache.http.conn.ssl.SSLSocketFactory
import org.apache.http.conn.ssl.StrictHostnameVerifier
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import org.codehaus.groovy.grails.web.json.JSONElement
import org.codehaus.groovy.grails.web.json.parser.JSONParser

import javax.net.ssl.KeyManager
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import java.security.KeyStore
import java.security.SecureRandom

@Transactional
class LoginService extends BaseService {

    def grailsApplication

    def retrieveSessionToken() {

        String sessionToken = "L4I5rnYoOBdGEIngy0bwhmzAJ5F22w+2mg2rk17QkIY="

//        String username = grailsApplication.config.betfair.username
//        String password = grailsApplication.config.betfair.password
//        String certificatePassword = grailsApplication.config.betfair.certificatePassword
//        File certificate = new File(grailsApplication.config.betfair.certificateFilename)
//        String applicationKey = grailsApplication.config.betfair.applicationKey
//
//        DefaultHttpClient httpClient = new DefaultHttpClient()
//        SSLContext ctx = SSLContext.getInstance("TLS");
//        KeyManager[] keyManagers = getKeyManagers("pkcs12", new FileInputStream(certificate), certificatePassword);
//        ctx.init(keyManagers, null, new SecureRandom());
//        SSLSocketFactory factory = new SSLSocketFactory(ctx, new StrictHostnameVerifier());
//
//        ClientConnectionManager manager = httpClient.getConnectionManager();
//        manager.getSchemeRegistry().register(new Scheme("https", 443, factory));
//        HttpPost httpPost = new HttpPost("https://identitysso.betfair.com/api/certlogin");
//        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//        nvps.add(new BasicNameValuePair("username", username));
//        nvps.add(new BasicNameValuePair("password", password));
//
//        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
//
//
//        httpPost.setHeader("X-Application", applicationKey);
//
//
//        System.out.println("executing request" + httpPost.getRequestLine());
//
//        HttpResponse response = httpClient.execute(httpPost);
//        HttpEntity entity = response.getEntity();
//
//        if (entity != null) {
//            String responseString = EntityUtils.toString(entity);
//            JSONElement responseJson = grails.converters.JSON.parse(responseString)
//            if (responseJson.loginStatus && responseJson.loginStatus == 'SUCCESS')
//                sessionToken = responseJson.sessionToken
//            else {
//                log.error "Failed to retrieve session token because:"
//                log.error responseJson.loginStatus
//                throw new Exception("Failed to retrieve session token.")
//            }
//        } else {
//            log.error "Failed to retrieve session token because:"
//            log.error "HTTP response is not available"
//            throw new Exception("Failed to retrieve session token.")
//        }

        return sessionToken
    }

    private static KeyManager[] getKeyManagers(String keyStoreType, InputStream keyStoreFile, String keyStorePassword) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(keyStoreFile, keyStorePassword.toCharArray());
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, keyStorePassword.toCharArray());
        return kmf.getKeyManagers();
    }


}
