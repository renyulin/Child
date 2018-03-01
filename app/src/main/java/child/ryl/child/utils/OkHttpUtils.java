package child.ryl.child.utils;

import android.content.Context;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import child.ryl.child.R;
import child.ryl.child.update_photo.PromptHelper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * okhttp简单使用
 */
public class OkHttpUtils {
    public static void postForm(final Context context, String url,
                                Map<String, Object> params,
                                final PostCallback callback, boolean bShowDialog) {

//        final Dialog progress = PromptHelper.createLoadingDialog(context, "");
//        if (bShowDialog) {
//            progress.show();
//        }
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && params.size() != 0) {
            for (Map.Entry<String, Object> item : params.entrySet()) {
                if (item.getKey() != null && item.getValue() != null && !"".equals(item.getValue()))
                    builder.add(item.getKey(), item.getValue().toString());
            }
        }

        final Request request = new Request.Builder().post(builder.build()).url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
//                progress.cancel();
                PromptHelper.displayMessage(context, R.string.net_error_no_connection);
                callback.onFail(context.getString(R.string.net_error_no_connection));
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Looper.prepare();
//                progress.cancel();
                if (!response.isSuccessful()) {
                    PromptHelper.displayMessage(context, R.string.net_error_no_connection);
                    callback.onFail(context.getString(R.string.net_error_no_connection));
                    Looper.loop();
                    return;
                }
                Gson gson = new Gson();
                HashMap<String, Object> result = gson.fromJson(response.body().string(), new TypeToken<HashMap<String, Object>>() {
                }.getType());
                callback.onSuccess(result);
                Looper.loop();
            }
        });
    }

    public interface PostCallback {
        void onSuccess(Object content);

        void onFail(String msg);
    }
}
