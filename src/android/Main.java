package com.hitmacreed.plugin.card;

   
//Native apis
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;


//BLINK CARD
import com.microblink.MicroblinkSDK;
import com.microblink.activity.BlinkCardActivity;
import com.microblink.entities.recognizers.Recognizer;
import com.microblink.entities.recognizers.RecognizerBundle;
import com.microblink.entities.recognizers.blinkcard.BlinkCardRecognizer;
import com.microblink.uisettings.ActivityRunner;
import com.microblink.uisettings.BlinkCardUISettings;


public class Main extends CordovaPlugin {

    Boolean Cvv = false;
    String androidKey = "mykey";

    private BlinkCardRecognizer mRecognizer;
    private RecognizerBundle mRecognizerBundle;
    public static final int MY_REQUEST_CODE = 1;
    public static final String TAG = "SCAN INFO";
    private CallbackContext mCallbackContext;

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }
   
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        mCallbackContext = callbackContext;
        Context context = cordova.getActivity().getApplicationContext();

        try {
            JSONObject options = args.getJSONObject(0);
            
            //GET VALUES
            Cvv = options.getBoolean("Cvv");
            androidKey = options.getString("androidKey");
         

        } catch (JSONException e) {
            callbackContext.error("Error encountered: " + e.getMessage());
            return false;
        }
        
        if(action.equals("read")) {
           
           this.readCard(context);
           
            return true;
        }
        return false;

    }

    private void readCard(Context context) {

        MicroblinkSDK.setLicenseKey(androidKey, context);
        mRecognizer = new BlinkCardRecognizer();
        mRecognizer.setExtractCvv(Cvv);
        mRecognizer.setExtractOwner(true);
        mRecognizer.setExtractValidThru(true);
        mRecognizer.getCombinedResult();
        mRecognizerBundle = new RecognizerBundle(mRecognizer);
        BlinkCardUISettings settings = new BlinkCardUISettings(mRecognizerBundle);
        Intent intent = new Intent(context, settings.getTargetActivity());
        settings.saveToIntent(intent);
        this.cordova.setActivityResultCallback(this);
        this.cordova.getActivity().startActivityForResult(intent, MY_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_REQUEST_CODE) {

            if (resultCode == BlinkCardActivity.RESULT_OK && data != null) {
               
                mRecognizerBundle.loadFromIntent(data);
                BlinkCardRecognizer.Result result = mRecognizer.getResult();

                if (result.getResultState() == Recognizer.Result.State.Valid) {
                  Toast.makeText(webView.getContext(),  result.getCardNumber() + "\n" +  result.getOwner() + "\n" + result.getValidThru(), Toast.LENGTH_LONG).show();
                  
                    JSONObject objResult = new JSONObject();
                    try {
                        objResult.put("cardNumber", result.getCardNumber());
                        objResult.put("cardOwner", result.getOwner());
                        objResult.put("cardExpDate", result.getValidThru());
                        objResult.put("cardCvv", result.getCvv());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                  mCallbackContext.success(objResult);
                }
            }
        }
    }
   
  
}