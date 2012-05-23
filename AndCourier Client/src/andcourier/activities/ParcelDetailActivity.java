package andcourier.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import client.communication.Client;
import client.event.ThreadDetailedParcelEvent;
import client.event.ThreadEventProcessor;

public class ParcelDetailActivity extends Activity {

	private TextView name, surname, city, street, postalCode, building, apartment, type, weight, sentOn, price;
	private ProgressDialog loading, assigning,delivering;
	private Handler hand;
	private int dbid = -1;
	private int mode;
	private Button button;
	@Override
	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		this.setContentView(R.layout.detailed_parcel);
		name = (TextView)this.findViewById(R.id.name);
		surname = (TextView)this.findViewById(R.id.surname);
		city = (TextView)this.findViewById(R.id.city);
		street = (TextView)this.findViewById(R.id.street);
		postalCode = (TextView)this.findViewById(R.id.postalCode);
		building = (TextView)this.findViewById(R.id.building);
		apartment = (TextView)this.findViewById(R.id.apartment);
		type = (TextView)this.findViewById(R.id.type);
		weight = (TextView)this.findViewById(R.id.weight);
		sentOn = (TextView)this.findViewById(R.id.sentOn);
		price = (TextView)this.findViewById(R.id.price);
		
		button = (Button)this.findViewById(R.id.button);
		
		loading = new ProgressDialog(this);
		loading.setMessage(getString(R.string.loadingData));
		assigning = new ProgressDialog(this);
		assigning.setMessage(getString(R.string.assigning));
		delivering = new ProgressDialog(this);
		delivering.setMessage(getString(R.string.delivering));
		
		hand = new Handler();
		
		this.dbid = this.getIntent().getExtras().getInt("dbid");
		this.mode = this.getIntent().getExtras().getInt("mode");
		
		switch (this.mode)
		{
		case 1:
			button.setText(getString(R.string.assignToMe));
			break;
		case 2:
			button.setVisibility(View.GONE);
			break;
		case 3:
			button.setText(getString(R.string.deliver));
			break;
		}
	}
	
	public void assignParcel()
	{
		assigning.show();
		Client.getInstance().AssignParcel(dbid, new ThreadEventProcessor(){

			public void process() {
				hand.post(new Runnable(){
					
					public void run()
					{
						assigning.hide();
						Toast.makeText(ParcelDetailActivity.this, getString(R.string.parcelAssigned), Toast.LENGTH_SHORT).show();
						button.setVisibility(View.GONE);
					}
					
				});
			}

			public void errorOccured() {
				hand.post(new Runnable(){
					public void run()
					{
						assigning.hide();
						Toast.makeText(ParcelDetailActivity.this, getString(R.string.failedToAssign), Toast.LENGTH_SHORT).show();
					}
				});
				
			}});
	}
	
	public void deliverParcel()
	{
		delivering.show();
		Client.getInstance().deliverParcel(dbid, new ThreadEventProcessor(){

			public void process() {
				hand.post(new Runnable(){
					
					public void run()
					{
						delivering.hide();
						Toast.makeText(ParcelDetailActivity.this, getString(R.string.parcelDelivered), Toast.LENGTH_SHORT).show();
						button.setVisibility(View.GONE);
					}
					
				});
			}

			public void errorOccured() {
				hand.post(new Runnable(){
					public void run()
					{
						delivering.hide();
						Toast.makeText(ParcelDetailActivity.this, getString(R.string.failedToDeliver), Toast.LENGTH_SHORT).show();
					}
				});
				
			}});
	}
	
	public void buttonClicked(View v)
	{
		switch(this.mode)
		{
		case 1:
			assignParcel();
			break;
		case 3:
			deliverParcel();
		}
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		loading.show();
		
		Client.getInstance().requestParcelDetails(dbid, new ThreadDetailedParcelEvent(){

			public void process() {
				ParcelDetailActivity.this.name.setText(this.name);
				ParcelDetailActivity.this.surname.setText(this.surname);
				ParcelDetailActivity.this.city.setText(this.city);
				ParcelDetailActivity.this.postalCode.setText(this.postalCode);
				ParcelDetailActivity.this.street.setText(this.street);
				ParcelDetailActivity.this.building.setText(this.building);
				ParcelDetailActivity.this.apartment.setText(this.apartment);
				ParcelDetailActivity.this.type.setText(this.type);
				ParcelDetailActivity.this.weight.setText(this.weight);
				ParcelDetailActivity.this.price.setText(this.price);
				ParcelDetailActivity.this.sentOn.setText(this.sentOn);
				loading.hide();
			}

			public void errorOccured() {
				hand.post(new Runnable(){
					
					public void run()
					{
						loading.hide();
						Toast.makeText(ParcelDetailActivity.this, getString(R.string.errorDataRetrival), Toast.LENGTH_SHORT).show();
					}
					
				});
				
			}});
	}
	
	@Override
	public void onPause()
	{
		loading.dismiss();
		assigning.dismiss();
		delivering.dismiss();
		super.onPause();
	}
}
