package andcourier.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
}
