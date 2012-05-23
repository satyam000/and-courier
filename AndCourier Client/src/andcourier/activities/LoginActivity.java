package andcourier.activities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import client.communication.Client;
import client.event.ThreadLoginEvent;

public class LoginActivity extends Activity{
	
	private EditText login = null;
	private EditText password = null;
	private ProgressDialog loggingDial;
	private Resources res;
	private String FILENAME;
	
	private Handler handler;
	
	private Activity myInstance;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        FILENAME = this.getString(R.string.loginFile);
        login = (EditText)findViewById(R.id.loginText);
        password = (EditText)findViewById(R.id.passText);
        loggingDial= new ProgressDialog(this);
        loggingDial.setCancelable(false);
		loggingDial.setMessage(getResources().getString(R.string.loggingin));
        myInstance = this;
        handler = new Handler();
        res = getResources();
        
        try
        {
        	FileInputStream in = this.openFileInput(FILENAME);
        	byte [] arr = new byte[255];
        	in.read(arr);
        	String temp = new String(arr);
        	login.setText(temp.trim());
        }
        catch(Exception e)
        {}
        
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		((TextView)this.findViewById(R.id.hostAddressText)).setText(Client.getHostAddress());
	}
	
	@Override
	public void onPause()
	{
		loggingDial.dismiss();
		super.onPause();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case R.id.menu_changeAddress:
			Intent i = new Intent(this, AndCourierClientActivity.class);
			i.putExtra("loginCaller", true);
			startActivity(i);
			return true;
		default:
            return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.login_menu, menu);
		return true;
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
			
			new Thread(new Runnable(){
				
				public void run()
				{
					Client c = Client.getInstance();
					if (c == null)
					{
						handler.post(new Runnable(){
							
							public void run()
							{
								loggingDial.hide();
								Toast.makeText(LoginActivity.this, getString(R.string.unreachableHost), Toast.LENGTH_SHORT).show();
							}
						});
						return;
					}
					else
					{
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
								{
									try
									{
										FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
										fos.write(login.getText().toString().getBytes());
										fos.close();
									}
									catch(Exception e)
									{
										handler.post(new Runnable(){
											public void run() {
												Toast.makeText(LoginActivity.this, LoginActivity.this.getString(R.string.failedSaveLogin), Toast.LENGTH_SHORT).show();
											}});
									}
									handler.post(new Runnable(){
										public void run() {
											loggingDial.hide();
											startActivity(new Intent(LoginActivity.this, MainScreenActivity.class));
										}});
								}
								else
									handler.post(new Runnable(){
										public void run() {
											loggingDial.hide();
											Toast.makeText(myInstance, res.getString(R.string.incorrectCredentials), Toast.LENGTH_SHORT).show();
										}});
							}});
					}
				}
				
			}).start();
			
		}
	}

}
