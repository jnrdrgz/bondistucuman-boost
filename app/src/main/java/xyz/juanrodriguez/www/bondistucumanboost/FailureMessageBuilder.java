package xyz.juanrodriguez.www.bondistucumanboost;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

public class FailureMessageBuilder {
    public static void build_failure_message(Context context, String method_name, int status_code, JSONObject response, String error){
        Log.d("rec_lineas", "request fail! status code:"+response);
        Log.d("rec_lineas", "Fail response: " + response);
        Log.e("rec_lineas", error);
        Toast.makeText(context, "Request Failed", Toast.LENGTH_SHORT).show();
    }
}
