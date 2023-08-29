package com.serialgroup.serial.util;

import com.baidu.aip.speech.AipSpeech;
import com.serialgroup.serial.dto.AnonymityMatchDTO;
import lombok.extern.slf4j.Slf4j;
import net.ailemon.asrt.sdk.BaseSpeechRecognizer;
import net.ailemon.asrt.sdk.Sdk;
import net.ailemon.asrt.sdk.models.AsrtApiResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class BdVoiceUtil {
    public static final BdVoiceUtil INST;
    public static final String APP_ID = "24528007";
    public static final String API_KEY = "GbvZeEys7ceKibPEeZkaHIPH";
    public static final String SECRET_KEY = "ovItDAULj9ubkSrM5vwi8LdBeKok4IV6";

    private static final AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

    private static final Map<String, Integer> chCodeMap = new HashMap<>();

    static {
        INST = new BdVoiceUtil();
        fillChCodeMap();

    }

    private static void fillChCodeMap() {
        chCodeMap.put("零", 0);
        chCodeMap.put("一", 1);
        chCodeMap.put("二", 2);
        chCodeMap.put("三", 3);
        chCodeMap.put("四", 4);
        chCodeMap.put("五", 5);
        chCodeMap.put("六", 6);
        chCodeMap.put("七", 7);
        chCodeMap.put("八", 8);
        chCodeMap.put("九", 9);
        chCodeMap.put("十", 10);
        chCodeMap.put("十一", 11);
        chCodeMap.put("十二", 12);
        chCodeMap.put("十三", 13);
        chCodeMap.put("十四", 14);
        chCodeMap.put("十五", 15);
        chCodeMap.put("十六", 16);
        chCodeMap.put("十七", 17);
        chCodeMap.put("十八", 18);
        chCodeMap.put("十九", 19);
        chCodeMap.put("二十", 20);
    }

    @Bean("asrt")
    public BdVoiceUtil asrt() {
        return INST;
    }

    public AnonymityMatchDTO recognizeAnonymity(byte[] voiceInput, int sampleRate, int channel, int byteWidth) {
//        String path = "/Users/mac/workspace/serial/abcd.wav";
        JSONObject res = client.asr(voiceInput, "wav", sampleRate, null);
//        System.out.println(res.toString(2));
        AnonymityMatchDTO dto = null;
        JSONArray jsonArray = res.getJSONArray("result");
        if (jsonArray.length() == 0) {
            return null;
        }
        String result = jsonArray.getString(0);
        if (result.contains("是")) {
            String[] strList = result.split("是");
            String name = strList[1];
            String prefix = strList[0];
            String numCh = prefix.replace("号陌生人", "");
            int id = chCodeMap.getOrDefault(numCh, -1);
            if (id > 0) {
                dto = new AnonymityMatchDTO();
                dto.setAnonymousId(id);
                dto.setName(name);
                return dto;
            }
            try {
                id = Integer.parseInt(numCh);
            }catch (NumberFormatException e) {
                log.error("parseInt error for {}", numCh, e);
            }
            if (id > 0) {
                dto = new AnonymityMatchDTO();
                dto.setAnonymousId(id);
                dto.setName(name);
                return dto;
            }
        }
        return null;
    }



}
