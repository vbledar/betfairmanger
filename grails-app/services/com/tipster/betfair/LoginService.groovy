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
        String sessionToken

        BetfairSession sessionRecord = BetfairSession.find("FROM BetfairSession ORDER BY dateCreated DESC")
        if (sessionRecord) {
            use(groovy.time.TimeCategory) {
                def difference = Calendar.getInstance().getTime() - sessionRecord.dateCreated
                if (difference.hours < 2) return sessionRecord.session
            }
        }

        String filename = grailsApplication.config.betfair.certificateFilename

        // search in user home directory
        String homeDirectoryPath = System.getProperty("user.home")
        if (!homeDirectoryPath.endsWith("/") || !homeDirectoryPath.endsWith("\\"))
            homeDirectoryPath = homeDirectoryPath + "/"
        File certificate = new File(homeDirectoryPath + filename)

        String username = grailsApplication.config.betfair.username
        String password = grailsApplication.config.betfair.password
        String certificatePassword = grailsApplication.config.betfair.certificatePassword

        String applicationKey = grailsApplication.config.betfair.applicationKey

        DefaultHttpClient httpClient = new DefaultHttpClient()
        SSLContext ctx = SSLContext.getInstance("TLS");
        KeyManager[] keyManagers = getKeyManagers("pkcs12", new FileInputStream(certificate), certificatePassword);
        ctx.init(keyManagers, null, new SecureRandom());
        SSLSocketFactory factory = new SSLSocketFactory(ctx, new StrictHostnameVerifier());

        ClientConnectionManager manager = httpClient.getConnectionManager();
        manager.getSchemeRegistry().register(new Scheme("https", 443, factory));
        HttpPost httpPost = new HttpPost("https://identitysso.betfair.com/api/certlogin");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("username", username));
        nvps.add(new BasicNameValuePair("password", password));

        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        httpPost.setHeader("X-Application", applicationKey);

        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            String responseString = EntityUtils.toString(entity);
            JSONElement responseJson = grails.converters.JSON.parse(responseString)
            if (responseJson.loginStatus && responseJson.loginStatus == 'SUCCESS') {
                sessionToken = responseJson.sessionToken

                sessionRecord = new BetfairSession(session: sessionToken)
                if (!sessionRecord.save()) {
                    log.error "Failed to save newly created betfair session instance."
                    sessionRecord.errors.each {
                        log.error it
                    }
                }
			}
            else {
                log.error "Failed to retrieve session token because:"
                log.error responseJson.loginStatus
                throw new Exception("Failed to retrieve session token.")
            }
        } else {
            log.error "Failed to retrieve session token because:"
            log.error "HTTP response is not available"
            throw new Exception("Failed to retrieve session token.")
        }

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
