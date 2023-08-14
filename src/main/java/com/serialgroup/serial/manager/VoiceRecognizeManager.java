package com.serialgroup.serial.manager;

import com.serialgroup.serial.vo.VoiceRecognizeVO;
import net.ailemon.asrt.sdk.BaseSpeechRecognizer;
import net.ailemon.asrt.sdk.Sdk;
import net.ailemon.asrt.sdk.models.AsrtApiResponse;
import net.ailemon.asrt.sdk.models.Wave;
import org.ansj.domain.Result;
import org.ansj.splitWord.analysis.DicAnalysis;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class VoiceRecognizeManager {

    private String host;
    private String port;
    private String protocol;



    @PostConstruct
    public void init() {
        host = "127.0.0.1";
        port = "20001";
        protocol = "http";

    }

    private String parse(String originStr) {
        Result result = DicAnalysis.parse(originStr);
        if (result.size() > 0) {
            return result.get(result.size() - 1).getRealName();
        }
        return null;
    }

    private VoiceRecognizeVO parseVO(String originStr) {
        Result result = DicAnalysis.parse(originStr);
//        if (result.size() > 0) {
//            return result.get(result.size() - 1).getRealName();
//        }
        return null;
    }
    public VoiceRecognizeVO recognizeVoice(byte[] bytes) {
        BaseSpeechRecognizer sr = Sdk.GetSpeechRecognizer(host, port, protocol);
        byte[] rawBytes = bytes;
        Wave wav = new Wave();
        wav.deserialize(rawBytes);
        byte[] sampleBytes = wav.getRawSamples();
        int sampleRate = wav.sampleRate;
        int channels = wav.channels;
        int byteWidth = wav.sampleWidth;
        AsrtApiResponse rsp;
        rsp = sr.Recognite(sampleBytes, sampleRate, channels, byteWidth);
        System.out.println(rsp.statusCode);
        System.out.println(rsp.statusMessage);
        System.out.println(rsp.result);
        return parseVO((String) rsp.result);
    }

//    TODO
    public String recognizeName(byte[] bytes) {
        BaseSpeechRecognizer sr = Sdk.GetSpeechRecognizer(host, port, protocol);
        byte[] rawBytes = bytes;
        Wave wav = new Wave();
        wav.deserialize(rawBytes);
        byte[] sampleBytes = wav.getRawSamples();
        int sampleRate = wav.sampleRate;
        int channels = wav.channels;
        int byteWidth = wav.sampleWidth;
        AsrtApiResponse rsp;
        rsp = sr.Recognite(sampleBytes, sampleRate, channels, byteWidth);
        System.out.println(rsp.statusCode);
        System.out.println(rsp.statusMessage);
        System.out.println(rsp.result);
        return parse((String) rsp.result);
    }

}
