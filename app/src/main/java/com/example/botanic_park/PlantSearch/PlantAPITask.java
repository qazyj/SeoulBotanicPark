package com.example.botanic_park.PlantSearch;

/* Plant API에 대한 request/response를 다루는 클래스 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import android.widget.Toast;
import com.example.botanic_park.NetworkStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class PlantAPITask extends AsyncTask<Object, Void, ArrayList<ProbablePlant>> {
    private final String PLANT_API_ACCESS_KEY = "pcDKWtdEaPuDikRwZCNd4OzayYb25wVTP8ye75AZIn6s5gxiYh"; // 유료 키
    //private final String PLANT_API_ACCESS_KEY = "WdkH6FsQc3qKvYGpCBMko1AKvUuDOrmB3tBQD6mWBsvsdsIaYW";
    //private final String PLANT_API_ACCESS_KEY = "OGRsrYYylRyFCwJjYCxXIBZ56eYP0WFxevtOwUwDHzvzTj89Ma";
    //private final String PLANT_API_ACCESS_KEY = "QKTJfvdijU5NdNqRLxXm5Kavj0buGcgS98FRvLC8pJ89WaePLG";
    //private final String PLANT_API_ACCESS_KEY = "tMYVgRhGwcyOfxPqiYhRfZe5sjub9ru9mrgyVPEiE7n79MXYeT";
    //private final String PLANT_API_ACCESS_KEY = "wAUVieDY3jKeDzH48jVlnqvicYvz8cbN0fsIDMJEPRc29TxRmf";

    private String API_IDENTIFY_URL = "https://plant.id/api/identify";
    private String API_SUGGESION_URL = "https://plant.id/api/check_identifications";
    private String API_USAGE_URL = "https://api.plant.id/usage_info";

    private static final int MAX_READ_TIME = 20000;
    private static final int MAX_CONNECT_TIME = 20000;

    private String image;   // 64bit로 인코딩된 이미지 String
    private JSONArray imageArray;
    private ArrayList<ProbablePlant> probablePlants;
    private Boolean isOverUseage = false;

    Context context;
    ProgressDialog dialog;

    public PlantAPITask(Context context, String image) {
        this.image = image;
        this.context = context;

        dialog = new ProgressDialog(context);
        Log.d("테스트", (context instanceof CameraSearchActivity) + "");

        // API에서 요구하는 이미지 형식에 맞춤
        imageArray = new JSONArray();
        imageArray.put(image);
        imageArray.put("data:image/jpg;base64," + image);

        probablePlants = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (NetworkStatus.getConnectivityStatus(context)
                == NetworkStatus.TYPE_NOT_CONNECTED) {
            Toast.makeText(context, "인터넷이 연결되어 있지 않아 사용이 제한됩니다. " +
                    "인터넷을 연결한 후 사용해 주세요", Toast.LENGTH_LONG).show();
            cancel(true);
            return;
        }
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("식물을 찾고 있어요!!!");
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                cancel(true);
            }
        });

        dialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<ProbablePlant> o) {
        if (isCancelled())
            return;

        dialog.dismiss();
        super.onPostExecute(o);

        if (isOverUseage) {
            Toast.makeText(context, "식물 검색 API의 사용량을 초과하여 검색할 수 없습니다.", Toast.LENGTH_SHORT).show();
        } else if (o.size() > 0) {
            ArrayList<String> result = new ArrayList<>();
            for (ProbablePlant plant : o) {
                result.add(plant.name); // 이름만 빼내기
            }

            // 검색 결과 창 띄우기
            Intent intent = new Intent(context, SearchResultActivity.class);
            intent.putExtra(Fragment_Plant_Book.SEARCH_WORD_KEY, result);

            context.startActivity(intent);
        } else {
            Toast.makeText(context, "이미지를 인식할 수 없습니다.\n" +
                    "다시 촬영해 주세요.", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected ArrayList<ProbablePlant> doInBackground(Object... objects) {
        // API 사용량 체크
        String usageResponse = getUsageInfo();
        try {
            Object weeklyUsage = new JSONObject(usageResponse).get("used_week");
            int usageCount = Integer.parseInt(weeklyUsage.toString());
            if (usageCount >= 20) {
                isOverUseage = true;
                return null;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 첫번째 request
        if (isCancelled())
            return null;
        String firstResponse = sendForIdentification();
        JSONArray plantIDArray = new JSONArray();
        try {
            Object plantID = new JSONObject(firstResponse).get("id");

            // API에서 요구하는 형식에 맞춰줌
            plantIDArray.put(plantID);
            plantIDArray.put(67);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 두번째 request
        if (isCancelled())
            return null;
        String secondResponse = getSuggestions(plantIDArray);
        try {
            JSONArray responseArray = new JSONArray(secondResponse);
            for (int i = 0; i < responseArray.length(); i++) {
                JSONObject object = (JSONObject) responseArray.get(i);
                Object suggentions = object.get("suggestions");

                JSONArray suggestionArray = (JSONArray) suggentions;
                for (int j = 0; j < suggestionArray.length(); j++) {
                    if (isCancelled())
                        return null;

                    JSONObject object1 = (JSONObject) suggestionArray.get(j);

                    String name = (String) ((JSONObject) object1.get("plant")).get("name");
                    double probability = (double) object1.get("probability");

                    ProbablePlant plant = new ProbablePlant(name, probability);
                    probablePlants.add(plant);  // 결과 후보들을 리스트에 저장
                    Log.d("후보 리스트", probablePlants.toString());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return probablePlants;
    }
    private String sendForIdentification() {
        // 첫번째 request 실행
        // 검색 결과의 고유 id를 받아옴
        String response = new String();

        try {
            HttpsURLConnection conn = getHttpUrlConnection(new URL(API_IDENTIFY_URL));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("key", PLANT_API_ACCESS_KEY);
            jsonObject.put("images", imageArray);

            // Request Body에 Data를 담기 위해 OutputStream 객체 생성
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jsonObject.toString().getBytes());   // Request Body에 Data 세팅
            outputStream.flush();   // Data 입력
            outputStream.close();

            response = getResponse(conn);
            Log.d("첫번째 응답", response);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    private String getSuggestions(JSONArray requestID) {
        // 두번째 request 실행
        // sendForIdendtification 에서 얻은 requestID를 통해
        // 식물 이름 검색 결과를 얻음
        String response = null;
        try {
            HttpsURLConnection conn = (HttpsURLConnection) getHttpUrlConnection(new URL(API_SUGGESION_URL));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("key", PLANT_API_ACCESS_KEY);
            jsonObject.put("ids", requestID);

            // Request Body에 Data를 담기 위해 OutputStream 객체 생성
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jsonObject.toString().getBytes());   // Request Body에 Data 세팅
            outputStream.flush();   // Data 입력

            Thread.sleep(5000);
            response = getResponse(conn);
            Log.d("두번째 응답", response);

            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;
    }

    private String getUsageInfo() {
        // API 사용량 체크
        String response = new String();

        try {
            HttpsURLConnection conn = getHttpUrlConnection(new URL(API_USAGE_URL));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("key", PLANT_API_ACCESS_KEY);

            // Request Body에 Data를 담기 위해 OutputStream 객체 생성
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jsonObject.toString().getBytes());   // Request Body에 Data 세팅
            outputStream.flush();   // Data 입력
            outputStream.close();

            response = getResponse(conn);
            Log.d("api사용량", response);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    private HttpsURLConnection getHttpUrlConnection(URL url) {
        HttpsURLConnection conn = null;
        try {
            // URL 연결 (웹페이지 URL 연결)
            conn = (HttpsURLConnection) url.openConnection();

            // 요청 방식 선택 (GET, POST)
            conn.setRequestMethod("POST");

            // Request Header 값 세팅
            conn.setRequestProperty("Content-Type", "application/json");    // Request body 전달 형식
            conn.setRequestProperty("Accept", "application/json");  // 서버 Response Data 요청 형식

            // TimeOut 시간 (서버 접속 시 연결 시간)
            conn.setConnectTimeout(MAX_CONNECT_TIME);
            // TimeOut 시간 (Read 시 연결 시간)
            conn.setReadTimeout(MAX_READ_TIME);

            // OutputStream을 POST 데이터를 넘겨주겠다는 옵션
            conn.setDoOutput(true);
            // InputStream으로 서버로부터 응답을 받겠다는 옵션
            conn.setDoInput(true);

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return conn;
    }

    private String getResponse(HttpsURLConnection conn) {
        String response = new String();
        try {
            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStream is = conn.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                byte[] byteData = null;
                int nLength = 0;
                while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                byteData = baos.toByteArray();

                response = new String(byteData);
            } else {
                Log.i("통신 결과", conn.getResponseCode() + "에러");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}

// 가능성 있는 식물에 대한 정보 객체
class ProbablePlant {
    String name;
    double probability;

    public ProbablePlant(String name, double probability) {
        this.name = name;
        this.probability = probability;
    }

    @Override
    public String toString() {
        return "name: " + name + "\nprobability: " + probability;
    }
}

