package andcourier.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import client.communication.Client;
import client.event.ThreadLoginEvent;

public class LoginActivity extends Activity{
	
	private EditText login = null;
	private EditText password = null;
	private ProgressDialog loggingDial;
	private Resources res;
	
	private Handler handler;
	
	private Activity myInstance;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        login = (EditText)findViewById(R.id.loginText);
        password = (EditText)findViewById(R.id.passText);
        loggingDial= new ProgressDialog(this);
        loggingDial.setCancelable(false);
		loggingDial.setMessage(getResources().getString(R.string.loggingin));
        myInstance = this;
        handler = new Handler();
        res = getResources();
	}
	
	public void connectClicked(View v)
	{
		if (login.getText().length() == 0)
			Toast.makeText(myInstance, res.getString(R.string.loginFirst), Toast.LENGTH_SHORT).show();
		else if (password.getText().length() == 0)
			Toast.makeText(myInstance, res.getString(R.string.passwordFirst), Toast.LENGTH_SHORT).show();
		else
		{
			loggingDial.show();
			Client c = Client.getInstance();
			c.logIn(login.getText().toString(), password.getText().toString(), new ThreadLoginEvent(){
	
				public void errorOccured() {
					handler.post(new Runnable(){
	
						public void run() {
							loggingDial.hide();
							Toast.makeText(myInstance, res.getString(R.string.communicationFail), Toast.LENGTH_SHORT).show();
						}});
				}
	
				public void process() {
					
					if (this.success)
						handler.post(new Runnable(){
	
							public void run() {
								loggingDial.hide();
								//-----------------------------------------------------------tutaj wywo³aj g³owny ekran jak ju¿ go zrobisz
							}});
						
					else
						handler.post(new Runnable(){
	
							public void run() {
								loggingDial.hide();
								Toast.makeText(myInstance, res.getString(R.string.incorrectCredentials), Toast.LENGTH_SHORT).show();
							}});
				}});
		}
	}
}
