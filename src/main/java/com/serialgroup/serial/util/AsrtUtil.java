package com.serialgroup.serial.util;

import com.serialgroup.serial.dto.AnonymityMatchDTO;
import net.ailemon.asrt.sdk.BaseSpeechRecognizer;
import net.ailemon.asrt.sdk.Sdk;
import net.ailemon.asrt.sdk.models.AsrtApiResponse;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.DicAnalysis;
import org.apache.catalina.manager.util.BaseSessionComparator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AsrtUtil {
    public static final AsrtUtil INST;

    private static final String asrtHost = "127.0.0.1";
    private static final String asrtProtocol = "protocol";
    private static final int asrtPort = 2001;

    private static final BaseSpeechRecognizer sr;

    private static final Map<String, Integer> chCodeMap = new HashMap<>();

    static {
        INST = new AsrtUtil();
        sr = Sdk.GetSpeechRecognizer(asrtHost, String.valueOf(asrtPort), asrtProtocol);
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
    public AsrtUtil asrt() {
        return INST;
    }

    public AnonymityMatchDTO recognizeAnonymity(byte[] voiceInput, int sampleRate, int channel, int byteWidth) {
        AsrtApiResponse rsp = sr.Recognite(voiceInput, sampleRate, channel, byteWidth);
        if (rsp.statusCode != 200) {
            throw new RuntimeException(String.format("asrt error, code:%d, msg:%s", rsp.statusCode, rsp.statusMessage));
        }
        String result = (String) rsp.result;
        if (result.contains("是")) {
            String[] strList = result.split("是");
            String name = strList[1];
            String prefix = strList[0];
            String numCh = prefix.replace("陌生人", "");
            int id = chCodeMap.getOrDefault(numCh, -1);
            if (id > 0) {
                AnonymityMatchDTO dto = new AnonymityMatchDTO();
                dto.setAnonymousId(id);
                dto.setName(name);
                return dto;
            }
        }
        return null;
    }



}
