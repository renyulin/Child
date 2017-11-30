package child.ryl.child.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import child.ryl.child.R;
import child.ryl.child.utils.Utils;

/**
 * 网络请求
 */
public class NetFragment extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_net, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.fragment_net_btn1).setOnClickListener(this);
    }

    private void threadNet() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                requireNet();
            }
        }).start();
    }

    private void requireNet() {
        URL url = null;
        HttpURLConnection connection = null;
        InputStream stream;
        BufferedReader reader = null;
        try {
            url = new URL("http://www.baidu.com");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");//可以尝试 http://www.baidu.com  不一定每次都响应
//            connection.setRequestMethod("POST");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            //post传数据
//            DataOutputStream outputStream=new DataOutputStream(connection.getOutputStream());
//            outputStream.writeBytes("user=admin&password=123456");

            stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            showData(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void showData(final String string) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //ui操作string
                Utils.smallToast(getActivity(), string);
            }
        });
    }

    /*
    <apps>
        <e>
           <a>1a</a>
           <b>1b</b>
           <c>1c</c>
        </e>
         <e>
           <a>2a</a>
           <b>2b</b>
           <c>2c</c>
        </e>
         <e>
           <a>3a</a>
           <b>3b</b>
           <c>3c</c>
        </e>
    </apps>
     */
    private void xmlObject(String xmlString) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlString));
            int eventType = xmlPullParser.getEventType();
            String a = "";
            String b = "";
            String c = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG://开始解析
                        if ("ma".equals(nodeName)) {
                            a = nodeName;
                        } else if ("mb".equals(nodeName)) {
                            b = nodeName;
                        } else if ("mc".equals(nodeName)) {
                            c = nodeName;
                        }
                        break;
                    case XmlPullParser.END_TAG://完成解析
                        if ("e".equals(nodeName)) {
                            Log.e("a_b_c", a + "," + b + "," + c);
                        }
                        break;
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void JSONObject1(String jsonString) {
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void JSONObject2(byte[] response) {
        String jsonString = new String(response);
        if (TextUtils.isEmpty(jsonString)) {
            return;
        }
        //0
        Gson g = new Gson();
        Map<String, Object> jsonMapTmp;
        try {
            jsonMapTmp = g.fromJson(jsonString, new TypeToken<Map<String, Object>>() {
            }.getType());
        } catch (Exception e) {
            e.toString();
        }
        //1
//        Bean bean;
//        try {
//            bean = g.fromJson(jsonString, new TypeToken<Bean>() {
//            }.getType());
//        } catch (Exception e) {
//            e.toString();
//        }
        //2
//        List<Bean> beanList;
//        try {
//            beanList = g.fromJson(jsonString, new TypeToken<List<Bean>>() {
//            }.getType());
//        } catch (Exception e) {
//            e.toString();
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_net_btn1:
                threadNet();
                break;
        }
    }

    class Bean {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
