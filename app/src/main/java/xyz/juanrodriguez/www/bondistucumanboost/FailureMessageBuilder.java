package xyz.juanrodriguez.www.bondistucumanboost;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

public class FailureMessageBuilder {
    public static void build(String method_name, int status_code, JSONObject response, String error){
        Log.d(method_name, "request fail! status code:" + status_code);
        Log.d(method_name, "Fail response: " + response);
        Log.e(method_name, error);
        //Toast.makeText(context, "Request Failed", Toast.LENGTH_SHORT).show();
    }
}
