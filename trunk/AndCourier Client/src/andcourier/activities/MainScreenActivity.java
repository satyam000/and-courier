package andcourier.activities;

import client.communication.Client;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainScreenActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreen);
	}
	
	public void showMyParcels(View v)
	{
		Intent in = new Intent(this, ParcelsActivity.class);
		in.putExtra("mode", 3);
		startActivity(in);
	}
	
	public void showAllParcels(View v)
	{
		Intent in = new Intent(this, ParcelsActivity.class);
		in.putExtra("mode", 2);
		startActivity(in);
	}
	
	public void showUnassigned(View v)
	{
		Intent in = new Intent(this, ParcelsActivity.class);
		in.putExtra("mode", 1);
		startActivity(in);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case R.id.menu_logOut:
			Client.disconnect();
			this.finish();
			return true;
		default:
            return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainscreen, menu);
		return true;
	}
}
