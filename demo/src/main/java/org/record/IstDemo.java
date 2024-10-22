package org.record;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class IstDemo extends WebSocketListener {
    private static final String hostUrl = "ws://ist-api-sg.xf-yun.com/v2/ist";
    private static final String apiKey = "b94daee9910020f917f0a6c5fa00697a";
    private static final String apiSecret = "9ed1789322f8943ee478a348f1f5b719";
    private static final String appid = "ga4d8fad";

    //    //中文
    private static final String file = "/Volumes/workspace/base_java/record/Demo/src/main/resources/tts/16k16bit.pcm";
//    //英语
//    private static final String file = "D:/iflytek/audio/tts/tts_en.pcm";
//    //阿拉伯语
//    private static final String file = "D:/iflytek/audio/tts/tts_arabic.pcm";
    //French
//    private static final String file = "D:\\static\\sdk\\fayu.pcm";
//    //印尼语
//    private static final String file = "D:/iflytek/audio/tts/tts_indonesian.pcm";
//    //泰语
//    private static final String file = "D:/iflytek/audio/tts/tts_thai.pcm";
//    //越南语
//    private static final String file = "D:/iflytek/audio/tts/tts_vietnamese.pcm";

    public static final int StatusFirstFrame = 0;
    public static final int StatusContinueFrame = 1;
    public static final int StatusLastFrame = 2;
    public static final Gson json = new Gson();


    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        System.out.println("open connection");
        // new Thread(()->{
        //连接成功，开始发送数据

        // }).start();
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        ResponseData resp = json.fromJson(text, ResponseData.class);
        System.out.println(text);
        if (resp != null) {
            if (resp.getCode() != 0) {
                System.out.println("error=>" + resp.getMessage() + " sid=" + resp.getSid());

                return;
            }
            if (resp.getData() != null) {
                if (resp.getData().getResult() != null) {
                    //  System.out.println(resp.getData().getResult().getText().text);

                }
                if (resp.getData().getStatus() == 2) {
                    // todo  resp.data.status ==2 说明数据全部返回完毕，可以关闭连接，释放资源
                    System.out.println("session end ");
                    webSocket.close(1000, "");
                    System.exit(0);
                } else {
                    // todo 根据返回的数据处理
                }
            }
        }
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        super.onFailure(webSocket, t, response);
        System.out.println(t.getMessage());
        try {
            System.out.println(response);
            if (response == null) {
                return;
            }
            System.out.println(response.code());
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main1(String[] args) throws Exception {
        // 构建鉴权url
        String authUrl = assembleRequestUrl(hostUrl, apiKey, apiSecret);

        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(authUrl).build();
        // System.out.println(client.newCall(request).execute());

        System.out.println("url===>" + authUrl);

        try {
            WebSocket webSocket = client.newWebSocket(request, new IstDemo());

            int frameSize = 1280; //每一帧音频的大小
            int intervel = 40;
            int status = 0;  // 音频的状态

            //FileInputStream fs = new FileInputStream("0.pcm");
            try (FileInputStream fs = new FileInputStream(file)) {
                byte[] buffer = new byte[frameSize];
                // 发送音频
                end:
                while (true) {
                    int len = fs.read(buffer);
                    if (len == -1) {
                        status = StatusLastFrame;  //文件读完，改变status 为 2
                    }

                    switch (status) {
                        case StatusFirstFrame:   // 第一帧音频status = 0
                            JsonObject frame = new JsonObject();
                            JsonObject business = new JsonObject();  //第一帧必须发送
                            JsonObject common = new JsonObject();  //第一帧必须发送
                            JsonObject data = new JsonObject();  //每一帧都要发送
                            // 填充common
                            common.addProperty("app_id", appid);
                            //填充business
                            business.addProperty("aue", "raw");

//                            //中文
                            business.addProperty("language", "zh_cn");
                            business.addProperty("accent", "mandarin");
                            business.addProperty("domain", "ist_ed_open");

//                            //英文
//                            business.addProperty("language", "en_us");
//                            business.addProperty("accent", "mandarin");
//                            business.addProperty("domain", "ist_open");

//                          //阿拉伯语
//                          business.addProperty("language", "ar_il");
//                          business.addProperty("accent", "mandarin");
//                          business.addProperty("domain", "ist_huanyu");

                            //法语
//                            business.addProperty("language", "fr_fr");
//                            business.addProperty("accent", "mandarin");
//                            business.addProperty("domain", "ist_open");

//                            //印度尼西亚语
//                            business.addProperty("language", "id_id");
//                            business.addProperty("accent", "mandarin");
//                            business.addProperty("domain", "ist_hy");

//                            //泰语
//                            business.addProperty("language", "th_TH");
//                            business.addProperty("accent", "mandarin");
//                            business.addProperty("domain", "ist_open");

//                            //越南语
//                            business.addProperty("language", "vi_vn");
//                            business.addProperty("accent", "mandarin");
//                            business.addProperty("domain", "ist_open");

                            business.addProperty("dwa", "wpgs");
                            business.addProperty("eos", 3000000);
                            business.addProperty("rate", "16000");
                            data.addProperty("status", StatusFirstFrame);

                            data.addProperty("audio", Base64.getEncoder().encodeToString(Arrays.copyOf(buffer, len)));
                            //填充frame
                            frame.add("common", common);
                            frame.add("business", business);
                            frame.add("data", data);

                            webSocket.send(frame.toString());
                            status = StatusContinueFrame;  // 发送完第一帧改变status 为 1
                            System.out.println("send first");
                            break;

                        case StatusContinueFrame:  //中间帧status = 1
                            JsonObject contineuFrame = new JsonObject();
                            JsonObject data1 = new JsonObject();
                            data1.addProperty("status", StatusContinueFrame);
                            data1.addProperty("audio", Base64.getEncoder().encodeToString(Arrays.copyOf(buffer, len)));
                            contineuFrame.add("data", data1);
                            webSocket.send(contineuFrame.toString());
                            //   System.out.println("send continue");
                            break;

                        case StatusLastFrame:    // 最后一帧音频status = 2 ，标志音频发送结束
                            JsonObject lastFrame = new JsonObject();
                            JsonObject data2 = new JsonObject();
                            data2.addProperty("status", StatusLastFrame);
                            data2.addProperty("encoding", "raw");
                            lastFrame.add("data", data2);
                            webSocket.send(lastFrame.toString());
                            System.out.println("sendlast");
                            break end;
                    }

                    Thread.sleep(intervel); //模拟音频采样延时
                }
                System.out.println("all data is send");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("end===");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 1; i++) {

            main1(null);
            System.out.println("fin==>:" + i);

        }
    }


    public static class ResponseData {
        private int code;
        private String message;
        private String sid;
        private Data data;

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return this.message;
        }

        public String getSid() {
            return sid;
        }

        public Data getData() {
            return data;
        }
    }

    public static class Data {
        private int status;
        private Object result;

        public int getStatus() {
            return status;
        }

        public Object getResult() {
            return result;
        }
    }


    /**
     * 生成用于鉴权的URL
     *
     * @param requestUrl
     * @param apiKey
     * @param apiSecret
     * @return
     */
    public static String assembleRequestUrl(String requestUrl, String apiKey, String apiSecret) {
        URL url = null;
        String httpRequestUrl = requestUrl.replace("ws://", "http://").replace("wss://", "https://");
        try {
            url = new URL(httpRequestUrl);
            SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            String date = format.format(new Date());
            String host = url.getHost();
            StringBuilder builder = new StringBuilder("host: ").append(host).append("\n").//
                    append("date: ").append(date).append("\n").//
                    append("GET ").append(url.getPath()).append(" HTTP/1.1");
            Charset charset = Charset.forName("UTF-8");
            Mac mac = Mac.getInstance("hmacsha256");
            System.out.println(builder.toString());
            SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(charset), "hmacsha256");
            mac.init(spec);
            byte[] hexDigits = mac.doFinal(builder.toString().getBytes(charset));
            String sha = Base64.getEncoder().encodeToString(hexDigits);
            String authorization =
                    String.format("hmac username=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey,
                            "hmac-sha256", "host date request-line", sha);
            String authBase = Base64.getEncoder().encodeToString(authorization.getBytes(charset));
            return String.format("%s?authorization=%s&host=%s&date=%s", requestUrl, URLEncoder.encode(authBase),
                    URLEncoder.encode(host), URLEncoder.encode(date));

        } catch (Exception e) {
            // throw new RuntimeException("assemble requestUrl error:"+e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
