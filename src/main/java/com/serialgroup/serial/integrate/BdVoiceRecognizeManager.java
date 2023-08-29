package com.serialgroup.serial.integrate;

import com.baidu.aip.speech.AipSpeech;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BdVoiceRecognizeManager {
    public static final String APP_ID = "24528007";
    public static final String API_KEY = "GbvZeEys7ceKibPEeZkaHIPH";
    public static final String SECRET_KEY = "ovItDAULj9ubkSrM5vwi8LdBeKok4IV6";

    public static void main(String[] args) {
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        String path = "/Users/mac/workspace/serial/abcd.wav";

        // 调用接口
        JSONObject res = client.asr(path, "wav", 16000, null);
        System.out.println(res.getString("result"));

    }
}
