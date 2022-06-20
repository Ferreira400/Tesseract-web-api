package restAssured;


import org.apache.commons.codec.binary.Base64;
import org.apache.groovy.json.internal.LazyMap;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.ToReadResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UtilsREST {

    private static LazyMap camposJson = new LazyMap();
    private static LazyMap headerCustom = new LazyMap();
    private static LazyMap valorArray = new LazyMap();
    private static JSONArray getJsonArrayValor = new JSONArray();
    static ToReadResponse kafkaResponseJSON = new ToReadResponse();
    private static List valorList = new ArrayList();

    public static JSONObject carregarJson(JSONObject obj, String parametro, String valorAntigo, String valorNovo, String format) throws Exception {
        // We need to know keys of Jsonobject
        JSONObject json = new JSONObject();
        Iterator iterator = obj.keys();
        String key = null;
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            // if object is just string we change value in key
            if ((obj.optJSONArray(key) == null) && (obj.optJSONObject(key) == null)) {
                if ((key.equals(parametro)) && (obj.get(key).toString().equals(valorAntigo))) {
                    switch (format) {
                        case "integer":
                            obj.put(key, Integer.valueOf(valorNovo));
                            break;
                        case "bolean":
                            obj.put(key, Boolean.valueOf(valorNovo));
                            break;
                        default:
                            obj.put(key, valorNovo);
                            break;
                    }
                    return obj;
                }
            }
            if (obj.optJSONObject(key) != null) {
                carregarJson(obj.getJSONObject(key), parametro, valorAntigo, valorNovo, format);
            }

            // if it's jsonarray
            if (obj.optJSONArray(key) != null) {
                JSONArray jArray = obj.getJSONArray(key);
                for (int i = 0; i < jArray.length(); i++) {
                    carregarJson(jArray.getJSONObject(i), parametro, valorAntigo, valorNovo, format);
                }
            }
        }
        return obj;
    }
    public static JSONObject carregarListaJson(JSONObject obj, String parametro, String valorAntigo, String[] valorNovo) throws Exception {
        JSONObject json = new JSONObject();
        Iterator iterator = obj.keys();
        String key = null;
        while (iterator.hasNext()) {
            key = (String) iterator.next();

            if ((obj.optJSONArray(key) == null) && (obj.optJSONObject(key) == null)) {
                if ((key.equals(parametro)) && (obj.get(key).toString().equals(valorAntigo))) {
                    obj.put(key, valorNovo);
                    return obj;
                }
            }
            if (obj.optJSONObject(key) != null) {
                carregarListaJson(obj.getJSONObject(key), parametro, valorAntigo, valorNovo);
            }

            // if it's jsonarray
            if (obj.optJSONArray(key) != null) {
                JSONArray jArray = obj.getJSONArray(key);
                for (int i = 0; i < jArray.length(); i++) {
                    carregarListaJson(jArray.getJSONObject(i), parametro, valorAntigo, valorNovo);
                }
            }
        }
        return obj;
    }
    public static JSONObject carregarJSONArray(JSONObject obj, String parametro, String valorAntigo, JSONArray valorNovo) throws Exception {
        // We need to know keys of Jsonobject
        JSONObject json = new JSONObject();
        Iterator iterator = obj.keys();
        String key = null;
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            // if object is just string we change value in key
            if ((obj.optJSONArray(key) == null) && (obj.optJSONObject(key) == null)) {
                if ((key.equals(parametro)) && (obj.get(key).toString().equals(valorAntigo))) {
                    obj.put(key, valorNovo);
                    return obj;
                }
            }
            if (obj.optJSONObject(key) != null) {
                carregarJSONArray(obj.getJSONObject(key), parametro, valorAntigo, valorNovo);
            }

            // if it's jsonarray
            if (obj.optJSONArray(key) != null) {
                JSONArray jArray = obj.getJSONArray(key);
                for (int i = 0; i < jArray.length(); i++) {
                    carregarJSONArray(jArray.getJSONObject(i), parametro, valorAntigo, valorNovo);
                }
            }
        }
        return obj;
    }
    public static void preencherValorArray(String campo, String valor) {
        valorArray.put(campo, valor);
    }
    public static void preencherLista(String valor){
        valorList.add(valor);
    }
    public static List getValorList() {
        return valorList;
    }
    public static LazyMap getValorArray() {
        return valorArray;
    }
    public static JSONArray getJsonArray(){
        getJsonArrayValor.put(getValorArray());
        return getJsonArrayValor;
    }
    public static void preencherCampo(String campo, String valor) {
        camposJson.put(campo, valor);
    }
    public static LazyMap getCampos() {
        return camposJson;
    }
    public static void preencherHeader(String campo, String valor) {
        headerCustom.put(campo, valor);
    }
    public static LazyMap getHeaderCustom() {
        return headerCustom;
    }
    public static JSONObject readJsonSimpleDemo(String filename) throws Exception {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        JSONObject obj;
        obj = new JSONObject(content.trim());
        return obj;

    }
    public static String encodeFileToBase64Binary(String file) {
        File f = new File(file);
        String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(f);
            byte[] bytes = new byte[(int) f.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return encodedfile;
    }
    public static void readJsonResponse(JSONObject jsonObject, String campo) {

        Iterator key = jsonObject.keys();
        System.out.println(interacaoJson(key, jsonObject, campo));
    }
    public static Object interacaoJson(Iterator iteratorKey, JSONObject jsonObject, String campo) {
        while (iteratorKey.hasNext()) {
            String key = iteratorKey.next().toString();
            //   System.out.println(key);
            Object value = jsonObject.get(key);
               if (value instanceof JSONObject) {
                JSONObject jsonSegundo = new JSONObject(value.toString());
                Iterator keySegundo = jsonSegundo.keys();
                interacaoJson(keySegundo, jsonSegundo, campo);
            } else if (value instanceof String) {
                if (key.equals(campo)) {
                    kafkaResponseJSON.setResponse(jsonObject.get(campo));
                    break;
                }
            } else if (value instanceof JSONArray) {
                JSONObject json = new JSONObject(((JSONArray) value).get(0).toString());
                System.out.println(json);
                Iterator keySegundo = json.keys();
                interacaoJson(keySegundo, json, campo);
            }
            else if (value instanceof Integer){
                if (key.equals(campo)) {
                    kafkaResponseJSON.setResponse(jsonObject.get(campo));
                    break;
                }
            }
            else if (value instanceof Boolean){
                if (key.equals(campo)) {
                    kafkaResponseJSON.setResponse(jsonObject.get(campo));
                    break;
                }
            }
        }
return  kafkaResponseJSON.getResponse();
    }

}

