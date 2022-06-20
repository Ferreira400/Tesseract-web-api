package restAssured.getSetObject;

import static org.apache.commons.io.IOUtils.toByteArray;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import io.restassured.response.Response;
import org.json.JSONObject;

public class ResolvedorDados {
    private GetValueObject getData;
    private SetValueObject setData;
    private JSONObject data;

    private ResolvedorDados(JSONObject data) {
        getData = new GetValueObject(data);

        setData = new SetValueObject(data);
        this.data = data;
    }

    public static ResolvedorDados obterDadosArquivo(String filename) {
        try {
            String content = new String(toByteArray(ResolvedorDados.class.getResourceAsStream(filename)));

            return carregarDados(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResolvedorDados carregarDados(String dados) {

        JSONObject data = new JSONObject(dados.trim());

        return new ResolvedorDados(data);
    }
    public static ResolvedorDados carregarDados(Response reposta){
        return carregarDados(reposta.getBody().asString());

    }
    public JSONObject get(){
        return data;
    }

    public String getString(String propriedade) {
        return get(propriedade);
    }

    public BigInteger getBigInteger(String propriedade) {
        return BigInteger.valueOf(get(propriedade));
    }

    public Long getLong(String propriedade) {
        return get(propriedade);
    }

    public Integer getInteger(String propriedade) {
        return get(propriedade);
    }

    public <T> T get(String propriedade) {
        return getData.get(propriedade);
    }

    public void set(String propriedade, Object value) {
        setData.set(propriedade, value);
    }
}

