package child.ryl.child.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

import child.ryl.child.R;

public class RequestHelper {

    public RequestHelper(Context context, String url, Map<String, Object> param) {
        excuteRequest(context, "post", url, null, param);
    }

    public RequestHelper(Context context, String method, String url, Map<String, String> header, Map<String, Object> param) {
        excuteRequest(context, method, url, header, param);
    }

    public void excuteRequest(Context context, String method, String url, Map<String, String> header, Map<String, Object> param) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        if (param != null && param.size() != 0) {
            Iterator<String> it = param.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                if (param.get(key) == null) {
                    continue;
                }
                params.add(key, param.get(key).toString());
            }
        }
        if (header != null && header.size() != 0) {
            Iterator<String> it = header.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                if (header.get(key) == null) {
                    continue;
                }
                client.addHeader(key, header.get(key));
            }
        }
        if ("post".equals(method)) {
            client.post(context, url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onFailure(int arg0, Header[] arg1, byte[] response, Throwable arg3) {
                    doFail(response);
                }

                @Override
                public void onSuccess(int arg0, Header[] arg1, byte[] response) {
                    doSuccess(response);
                }
            });
        } else if ("get".equals(method)) {
            client.get(context, url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onFailure(int arg0, Header[] arg1, byte[] response, Throwable arg3) {
                    doFail(response);
                }

                @Override
                public void onSuccess(int arg0, Header[] arg1, byte[] response) {
                    doSuccess(response);
                }
            });
        }
    }

    public void doSuccess(byte[] response) {

    }

    public void doFail(byte[] response) {

    }

    public static void readDataFromUrl(Context context, Map<String, Object> param, String url, RequestCallback callback) {
        readDataFromUrl(context, param, url, new int[]{0, 0}, callback, true);
    }

    public static void readDataFromUrl(Context context, Map<String, Object> param, String url, int[] resultStringId,
                                       RequestCallback callback) {
        readDataFromUrl(context, param, url, resultStringId, callback, true);
    }

    public static void readDataFromUrl(final Context context, Map<String, Object> param, String url,
                                       final int[] resultStringId, final RequestCallback callback, final boolean showDialog) {

        new RequestHelper(context, url, param) {
            @Override
            public void doSuccess(byte[] response) {
                if (showDialog) {
                }
                String jsonString = new String(response);
                if (TextUtils.isEmpty(jsonString)) {
                    callback.doFail(0);
                    return;
                }
                Gson g = new Gson();
                Map<String, Object> jsonMapTmp;
                try {
                    jsonMapTmp = g.fromJson(jsonString, new TypeToken<Map<String, Object>>() {
                    }.getType());
                } catch (IllegalStateException e) {
                    callback.doFail(0);
                    return;
                } catch (JsonSyntaxException e) {
                    callback.doFail(0);
                    return;
                }

                Integer status = (int) Math.floor((Double) jsonMapTmp.get("status"));
                if (status == 1) {
                    callback.doSuccess(jsonMapTmp);
                } else if (jsonMapTmp.get("msg") != null) {
                    Utils.smallToast(context, (String) jsonMapTmp.get("msg"));
                } else {
                    try {
                        Utils.smallToast(context, resultStringId[-status]);
                    } catch (IndexOutOfBoundsException e) {
                        Utils.smallToast(context, "net_error");
                    }
                    callback.doFail(status);
                }
            }

            @Override
            public void doFail(byte[] response) {
                if (showDialog) {
                }
                Utils.smallToast(context, "net_error");
                callback.doFail(0);
            }
        };
    }

    public interface RequestCallback {
        void doSuccess(Map<String, Object> jsonMap);

        void doFail(int result);
    }


    public static void readStringDataFromUrl(final Context context, Map<String, Object> param, String url, final RequestStringCallback callback, final boolean showDialog) {
        if (showDialog) {
        }
        new RequestHelper(context, url, param) {
            @Override
            public void doSuccess(byte[] response) {
                if (showDialog) {
                }
                String tokenString = new String(response);
                if (TextUtils.isEmpty(tokenString)) {
                    callback.doFail(0);
                    return;
                } else {
                    callback.doSuccess(tokenString);
                }
            }

            @Override
            public void doFail(byte[] response) {
                if (showDialog) {
                }
                Utils.smallToast(context, "net_error");
                callback.doFail(0);
            }
        };
    }


    public interface RequestStringCallback {
        public void doSuccess(String token);

        public void doFail(int result);
    }


    // 发送图片
//    public static void sendImages(final Context context, File[] fileArray, String picType, String sessionId,
//                                  final RequestCallback callback) {
//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//        if (fileArray != null && fileArray.length > 0) {
//            for (int i = 0; i < fileArray.length; i++) {
//                try {
//                    params.put("filename" + (i + 1), fileArray[i]);
//                    Log.e("image", "filename" + (i + 1) + ", " + fileArray[i].getName());
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        } else {
//            return;
//        }
//        params.put(Keys.SESSION_ID, sessionId);
//        params.put(Keys.PIC_TYPE, picType);
//        client.post(context, Constants.URL_UPLOAD_IMG, params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//                callback.doFail(0);
//                progress.dismiss();
//                Utils.smallToast(context, R.string.net_error_no_connection);
//            }
//
//            @Override
//            public void onSuccess(int arg0, Header[] arg1, byte[] response) {
//                progress.dismiss();
//                String jsonString = new String(response);
//                Gson g = new Gson();
//                Map<String, Object> jsonMapTmp = g.fromJson(jsonString, new TypeToken<Map<String, Object>>() {
//                }.getType());
//                Integer status = (int) Math.floor((Double) jsonMapTmp.get(Keys.STATUS));
//                if (status == 1) {
//                    callback.doSuccess(jsonMapTmp);
//                } else {
//                    try {
//                        Utils.smallToast(context, Keys.NORMAL_RESULT[-status]);
//                    } catch (IndexOutOfBoundsException e) {
//                        Utils.smallToast(context, R.string.net_error_unkonwn);
//                    }
//                    callback.doFail(status);
//                }
//            }
//        });
//    }

    public static void postImages(final Context context, final File[] fileArray, final String picType,
                                  final String sessionId, final RequestCallback callback) {

        final Handler handler = new Handler() {
            @SuppressWarnings("unchecked")
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    callback.doSuccess((Map<String, Object>) msg.obj);
                } else {
                    callback.doFail(msg.what);
                }
            }
        };
        new Thread() {
            public void run() {
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost("http://imgapi.fangxiaoer.com/API/Picuploadnew");
                MultipartEntity multipartEntity = new MultipartEntity();
                try {
                    multipartEntity.addPart("sessionId", new StringBody(sessionId, Charset.forName("UTF-8")));
                    multipartEntity.addPart("picType", new StringBody(picType, Charset.forName("UTF-8")));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(0);
                    return;
                }
                if (fileArray != null && fileArray.length > 0) {
                    for (int i = 0; i < fileArray.length; i++) {
                        multipartEntity.addPart("filename" + (i + 1), new FileBody(fileArray[i]));
                    }
                }
                post.setEntity(multipartEntity);
                try {
                    HttpResponse response = client.execute(post);
                    int statecode = response.getStatusLine().getStatusCode();

                    if (statecode == HttpStatus.SC_OK) {
                        HttpEntity responseEntity = response.getEntity();
                        if (responseEntity != null) {
                            InputStream is = responseEntity.getContent();
                            BufferedReader br = new BufferedReader(new InputStreamReader(is));
                            String tempStr;
                            StringBuilder sBuilder = new StringBuilder();
                            while ((tempStr = br.readLine()) != null) {
                                sBuilder.append(tempStr);
                            }
                            br.close();
                            is.close();
                            Gson g = new Gson();
                            Map<String, Object> jsonMapTmp = g.fromJson(sBuilder.toString(),
                                    new TypeToken<Map<String, Object>>() {
                                    }.getType());
                            Integer status = (int) Math.floor((Double) jsonMapTmp.get("status"));
                            if (status == 1) {
                                Message msg = new Message();
                                msg.what = status;
                                msg.obj = jsonMapTmp;
                                handler.sendMessage(msg);
                            } else {
                                handler.sendEmptyMessage(status);
                            }
                        }
                    }
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(0);
                } catch (IOException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(0);
                }
            }
        }.start();
    }
}
