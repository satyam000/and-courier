package andcourier.activities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import client.communication.Client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AndCourierClientActivity extends Activity {
    
	private final String FILENAME = "hostAddress";
	private EditText address = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        address = (EditText)findViewById(R.id.textHostAddress);
        try
        {
        	FileInputStream in = this.openFileInput(FILENAME);
        	byte [] arr = new byte[255];
        	in.read(arr);
        	String temp = new String(arr);
        	address.setText(temp.trim());
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
        
        //startActivity(new Intent(this, LoginActivity.class));
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
    		Client.setHostAddress(address.getText().toString());
    		Client c = Client.getInstance();
    		if (c == null)
    		{
    			Toast.makeText(this, res.getString(R.string.incorrectAddress), Toast.LENGTH_SHORT).show();
    		}
    		else
    		{
		    	Toast.makeText(this, res.getString(R.string.savingHost), Toast.LENGTH_SHORT).show();
		    	try
		    	{
			    	FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
			    	fos.write(address.getText().toString().getBytes());
			    	fos.close();
			    	startActivity(new Intent(this, LoginActivity.class));
		    	}
		    	catch(Exception e){
		    		Toast.makeText(this, res.getString(R.string.failedSaveAddress), Toast.LENGTH_SHORT).show();
		    	}
    		}
    	}
    }
}