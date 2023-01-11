package com.serialgroup.serial;

import net.ailemon.asrt.sdk.BaseSpeechRecognizer;
import net.ailemon.asrt.sdk.Sdk;
import net.ailemon.asrt.sdk.models.AsrtApiResponse;
import net.ailemon.asrt.sdk.models.Wave;

import java.nio.file.Files;
import java.nio.file.Paths;

public class AsrtDemo {
//    private  static byte[] reSampling(byte[] data) throws IOException, UnsupportedAudioFileException {
//
//        WaveFileReader reader = new WaveFileReader();
//        AudioInputStream audioIn = reader.getAudioInputStream(new ByteArrayInputStream(data));
//
//        AudioFormat srcFormat = audioIn.getFormat();
//        int targetSampleRate = 16000;
//
//        AudioFormat dstFormat = new AudioFormat(srcFormat.getEncoding(),
//                targetSampleRate,
//                srcFormat.getSampleSizeInBits(),
//                srcFormat.getChannels(),
//                srcFormat.getFrameSize(),
//                srcFormat.getFrameRate(),
//                srcFormat.isBigEndian());
//
//        AudioInputStream convertedIn = AudioSystem.getAudioInputStream(dstFormat, audioIn);
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        int len = -1;
//        int totalLen = 4096;
//        byte[] buffer = new byte[totalLen];
//        while ((len = convertedIn.read(buffer, 0, totalLen)) != -1) {
//            byteArrayOutputStream.write(buffer, 0, len);
//        }
//        return byteArrayOutputStream.toByteArray();
//    }
    public static void main(String[] args) throws Exception {
        String host = "127.0.0.1";
        String port = "20001";
        String protocol = "http";
        BaseSpeechRecognizer sr = Sdk.GetSpeechRecognizer(host, port, protocol);
        String filename = "陌生人15.wav";
        if(args.length > 0){
            filename = args[0];
        }
        byte[] rawBytes = Files.readAllBytes(Paths.get(filename));
//        rawBytes = reSampling(rawBytes);
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
        // ============================================
        // 直接调用ASRT识别语音文件
//        AsrtApiResponse rsp = sr.RecogniteFile(filename);
//        System.out.println(rsp.statusCode);
//        System.out.println(rsp.statusMessage);
//        System.out.println(rsp.result);
//
//        // ============================================
//        // 调用ASRT识别语音序列
//        byte[] wavBytes = Common.readBinFile(filename);
//        Wave wav = new Wave();
//        wav.deserialize(wavBytes);
//        byte[] sampleBytes = wav.getRawSamples();
//        int sampleRate = wav.sampleRate;
//        int channels = wav.channels;
//        int byteWidth = wav.sampleWidth;
//        rsp = sr.Recognite(sampleBytes, sampleRate, channels, byteWidth);
//        System.out.println(rsp.statusCode);
//        System.out.println(rsp.statusMessage);
//        System.out.println(rsp.result);
//
//        // ============================================
//        // 调用ASRT声学模型识别语音序列
//        wavBytes = Common.readBinFile(filename);
//        wav = new Wave();
//        wav.deserialize(wavBytes);
//        sampleBytes = wav.getRawSamples();
//        sampleRate = wav.sampleRate;
//        channels = wav.channels;
//        byteWidth = wav.sampleWidth;
//        rsp = sr.RecogniteSpeech(sampleBytes, sampleRate, channels, byteWidth);
//        System.out.println(rsp.statusCode);
//        System.out.println(rsp.statusMessage);
//        System.out.println(rsp.result);
//
//        // ============================================
//        // 调用ASRT语言模型识别拼音序列1
//        String[] pinyins = ((String)rsp.result).split(", ");
//        rsp = sr.RecogniteLanguage(pinyins);
//        System.out.println(rsp.statusCode);
//        System.out.println(rsp.statusMessage);
//        System.out.println(rsp.result);
//
//        // ============================================
//        // 调用ASRT语言模型识别拼音序列2
//        pinyins = new String[]{"ni3", "hao3", "a1"};
//        rsp = sr.RecogniteLanguage(pinyins);
//        System.out.println(rsp.statusCode);
//        System.out.println(rsp.statusMessage);
//        System.out.println(rsp.result);
    }
}
