package andcourier.activities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import client.communication.Client;

public class AndCourierClientActivity extends Activity {
    
	private String FILENAME;
	private EditText address = null;
	private boolean loginCaller = false;
	private Handler handler;
	private ProgressDialog savingDial;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        FILENAME = this.getString(R.string.hostAddressFile);
        loginCaller = this.getIntent().getBooleanExtra("loginCaller", false);
        if (loginCaller)
        	((TextView)findViewById(R.id.firstRunText)).setVisibility(View.INVISIBLE);
        address = (EditText)findViewById(R.id.textHostAddress);
        handler = new Handler();
        savingDial = new ProgressDialog(this);
		savingDial.setCancelable(false);
		savingDial.setMessage(getString(R.string.savingHost));
        try
        {
        	FileInputStream in = this.openFileInput(FILENAME);
        	byte [] arr = new byte[255];
        	in.read(arr);
        	String temp = new String(arr);
        	address.setText(temp.trim());
        	Client.setHostAddress(address.getText().toString());
        }
        catch (FileNotFoundException e)
        {
        	return;
        }
        catch(Exception e)
        {
        	Toast.makeText(this, getResources().getString(R.string.failedReadAddress), Toast.LENGTH_SHORT).show();
        	return;
        }
        if (!loginCaller)
        	startActivity(new Intent(this, LoginActivity.class));
    }
    
    @Override
    public void onPause()
    {
    	savingDial.dismiss();
    	super.onPause();
    }
    
    public void saveClicked(View v)
    {
    	Resources res = this.getResources();
    	if (address.getText().length() == 0)
    	{
    		Toast.makeText(this, res.getString(R.string.addressFirst), Toast.LENGTH_SHORT).show();
    	}
    	else
    	{
			
			savingDial.show();
			new Thread(new Runnable()
			{
				public void run() {
					if (!Client.testAddress(address.getText().toString()))
					{
						handler.post(new Runnable(){
							public void run() {
								savingDial.hide();
								Toast.makeText(AndCourierClientActivity.this, AndCourierClientActivity.this.getResources().getString(R.string.incorrectAddress), Toast.LENGTH_SHORT).show();
							}});
					}
					else
					{
						Client.setHostAddress(address.getText().toString());
						try
				    	{
					    	FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
					    	fos.write(address.getText().toString().getBytes());
					    	fos.close();
					    	handler.post(new Runnable(){
								public void run() {
									savingDial.hide();
									if (loginCaller)
							    		AndCourierClientActivity.this.finish();
							    	else
							    		AndCourierClientActivity.this.startActivity(new Intent(AndCourierClientActivity.this, LoginActivity.class));
								}});
					    	
				    	}
				    	catch(Exception e){
				    		handler.post(new Runnable(){
								public void run() {
									Toast.makeText(AndCourierClientActivity.this, AndCourierClientActivity.this.getString(R.string.failedSaveAddress), Toast.LENGTH_SHORT).show();
								}});
				    	}
					}
				}}).start();
    	}
    }
}