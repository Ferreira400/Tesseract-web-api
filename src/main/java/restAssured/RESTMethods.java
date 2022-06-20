package restAssured;

import io.restassured.config.DecoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.groovy.json.internal.LazyMap;
import java.io.File;
import java.util.*;

import static io.restassured.RestAssured.given;

public class RESTMethods {

    private static RequestSpecification rs;
    private static RequestSpecification rsAux;
    private static Response response;
    public static Response getResponse() {
        return response;
    }
    private static void setResponse(Response response) {
        RESTMethods.response = response;
    }
    public static void clearRs() {
        rs = null;
    }
    private static RequestSpecification buildBaseRequestSpecification() {
        clearRs();
        if (!(rsAux == null)) {
            rs.headers(((RequestSpecificationImpl) rsAux).getHeaders());
            rs.cookies(((RequestSpecificationImpl) rsAux).getCookies());
        }
        rs = given()
                .when()
                .accept(ContentType.JSON);
        return rs;
    }
    public static void addHeader(Header h) {
        if (h != null) {
            rsAux.header(h);
        }
    }
    public static void addCookies(Map<String, String> c) {
        if (c != null) {
            rsAux.cookies(c);
        }
    }
    public static Response executeGet(String endpoint, LazyMap headCustom, LazyMap camposParams) {
        response = buildBaseRequestSpecification()
                .headers(headCustom)
                .queryParams(camposParams)
                .get(endpoint)
                .then()
                .extract().response();
        printLogStart("GET", endpoint, "");
        printLog("GET", response);
        setResponse(response);
        return response;
    }
    public static Response executeGetpathParams(String endpoint, LazyMap headCustom, LazyMap camposParams) {
        response = buildBaseRequestSpecification()
                .headers(headCustom)
                .pathParams(camposParams)
                .get(endpoint)
                .then()
                .extract().response();
        printLogStart("GET", endpoint, "");
        printLog("GET", response);
        setResponse(response);
        return response;
    }
    public static Response executePost(String endpoint, Object json, LazyMap headCustom, LazyMap camposParams) {
        RequestSpecification request = buildBaseRequestSpecification()
                .headers(headCustom)
                .queryParams(camposParams);
        request = getRequestSpecification(json, headCustom, request);
        response = request
                .post(endpoint)
                .then()
                .extract().response();
        printLogStart("POST", endpoint, json.toString());
        printLog("POST", response);
        return response;
    }

    private static RequestSpecification getRequestSpecification(Object json, LazyMap headCustom, RequestSpecification request) {
        if (headCustom.get("Content-Type").equals("application/x-www-form-urlencoded")){
            request = request.params((Map) json);
        }else{
            request = request.body(json);
        }
        return request;
    }
    public static Response executePostPathParams(String endpoint, Object json, LazyMap headCustom, LazyMap camposParams) {
        response = buildBaseRequestSpecification()
                .headers(headCustom)
                .pathParams(camposParams)
                .body(json)
                .post(endpoint)
                .then()
                .extract().response();
        printLogStart("POST", endpoint, json.toString());
        printLog("POST", response);
        return response;
    }

    public static Response executePut(String endpoint, Object json, LazyMap headCustom, LazyMap camposParams, String path) {
        response = buildBaseRequestSpecification()
                .headers(headCustom)
                .queryParams(camposParams)
                .body(json)
                .put(endpoint + path)
                .then()
                .extract().response();
        printLogStart("PUT", endpoint, json.toString());
        printLog("PUT", response);
        return response;
    }
    public static Response executePutPathParams(String endpoint, Object json, LazyMap headCustom, LazyMap camposParams) {
        response = buildBaseRequestSpecification()
                .headers(headCustom)
                .pathParams(camposParams)
                .body(json)
                .put(endpoint)
                .then()
                .extract().response();
        printLogStart("PUT", endpoint, json.toString());
        printLog("PUT", response);
        return response;
    }
    public static Response executeDelete(String endpoint, LazyMap headCustom, LazyMap camposParams) {
        response = buildBaseRequestSpecification()
                .headers(headCustom)
                .queryParams(camposParams)
                .delete(endpoint)
                .then()
                .extract().response();
        printLogStart("DELETE", endpoint, "");
        printLog("DELETE", response);
        return response;
    }
    public static Response executeOptions(String endpoint, LazyMap headCustom, LazyMap camposParams) {
        response = buildBaseRequestSpecification()
                .headers(headCustom)
                .queryParams(camposParams)
                .options(endpoint)
                .then()
                .extract().response();
        printLogStart("OPTIONS", endpoint, "");
        printLog("OPTIONS", response);
        return response;
    }
    public static Response executePatch(String endpoint, Object json, LazyMap headCustom, LazyMap camposParams) {
        response = buildBaseRequestSpecification()
                .headers(headCustom)
                .queryParams(camposParams)
                .body(json)
                .patch(endpoint)
                .then()
                .extract().response();
        printLogStart("PATCH", endpoint, json.toString());
        printLog("PATCH", response);
        return response;
    }
    public static void executePostFile(String endpoint, LazyMap headCustom, LazyMap camposParams, String caminhoFile, String extensaoFile, String paramFile) {
        File file = new File(caminhoFile);
        response = buildBaseRequestSpecification()
                .headers(headCustom)
                .queryParams(camposParams)
                .multiPart(paramFile, file, extensaoFile)
                .post(endpoint)
                .then()
                .extract().response();
        printLogStart("POST", endpoint, "");
        printLog("POST", response);
        setResponse(response);
    }
    public static Response executeGetAuthBasic(String endpoint, LazyMap headCustom, LazyMap camposParams, String User, String Pass) {
        response =  buildBaseRequestSpecification()
                .headers(headCustom)
                .queryParams(camposParams)
                .auth().preemptive().basic(User, Pass)
                .get(endpoint)
                .then()
                .extract().response();
        printLogStart("GET", endpoint, "");
        printLog("GET", response);
        setResponse(response);
        return response;
    }
    public static Response executePostCertificateSSL(String endpoint, Object json, LazyMap headCustom, LazyMap camposParams) {
        response = buildBaseRequestSpecification()
                .headers(headCustom)
                .queryParams(camposParams)
                .body(json)
                .config(RestAssuredConfig.config()
                        .decoderConfig(DecoderConfig
                                .decoderConfig()
                                .defaultContentCharset("UTF-8"))
                        .sslConfig(new SSLConfig()
                                .relaxedHTTPSValidation()))
                .post(endpoint)
                .then()
                .extract().response();
        printLogStart("POST", endpoint, json.toString());
        printLog("POST", response);
        return response;
    }
    public static Response executeGetCertificateSSL(String endpoint, LazyMap headCustom) {
        response = buildBaseRequestSpecification()
                .headers(headCustom)
                .config(RestAssuredConfig.config()
                        .decoderConfig(DecoderConfig
                                .decoderConfig()
                                .defaultContentCharset("UTF-8"))
                        .sslConfig(new SSLConfig()
                                .relaxedHTTPSValidation()))
                .get(endpoint)
                .then()
                .extract()
                .response();
//        printLogStart("GET", endpoint, "");
//        printLog("GET", response);
        setResponse(response);
        return response;
    }
    private static void printLogStart(String method, String url, String json) {
        System.out.println("");
        System.out.println("====================================");
        System.out.println("");
        System.out.println("METHOD: [ " + method + " (Request) ]");
        System.out.println("Endpoint: [ " + url + " ]");
        System.out.println("Headers: [ " + ((RequestSpecificationImpl) rs).getHeaders().toString() + " ]");
        System.out.println("Body - Request: [ " + json + " ]");

        // Hooks.scenario.write("METHOD: [ "+ method + " (Request) ]");
        //   Hooks.scenario.write("Endpoint: [ "+ url + " ]");
        //  Hooks.scenario.write("Headers: [ "+ ((RequestSpecificationImpl) rs).getHeaders().toString() + " ]");
        // Hooks.scenario.write("Body - Request: [ " + json + " ]");
    }
    private static void printLog(String method, Response response) {
        System.out.println("");
        System.out.println("------------------------------------");
        System.out.println("");
        System.out.println("METHOD: [ " + method + " (Response) ]");
        System.out.println("Headers: [ " + response.getHeaders().toString() + " ]");
        System.out.println("Status Code: [ " + String.valueOf(response.statusCode()) + " ]");
        System.out.println("Response: [ " + response.getBody().asString() + " ]");
        System.out.println("");
        System.out.println("====================================");

        // Hooks.scenario.write("------------------------------------");
        //  Hooks.scenario.write("METHOD: [ "+ method + " (Response) ]");
        // Hooks.scenario.write("Status Code: [ "+ String.valueOf(response.statusCode()) + " ]");
        // Hooks.scenario.write("Response: [ "+ response.getBody().asString() + " ]");
        // Hooks.scenario.write("");
    }
    public static Integer getResponseCode() {
        return response.getStatusCode();
    }
    public static Object key(String field) {

        return response.getBody().jsonPath().get(field);
    }
    public static String validarHeader(String header){
        return response.header(header);

    }
}
