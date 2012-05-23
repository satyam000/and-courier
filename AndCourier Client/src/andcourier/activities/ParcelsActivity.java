package andcourier.activities;

import java.util.LinkedList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import client.communication.Client;
import client.event.ThreadListResponseEvent;

public class ParcelsActivity extends Activity{

	private ProgressDialog loading;
	private Handler hand;
	private TableLayout table;
	private LinkedList<String[]> itemlist;
	private int mode;
	
	@Override
	public void onCreate(Bundle bsaved)
	{
		super.onCreate(bsaved);
		this.setContentView(R.layout.list_info);
		loading = new ProgressDialog(this);
		loading.setMessage(getString(R.string.loadingData));
		hand = new Handler();
		table = (TableLayout)findViewById(R.id.table);
		
		this.mode = this.getIntent().getExtras().getInt("mode");
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		refresh();
	}
	
	private void refresh()
	{
		table.removeAllViews();
		listHeader();
		loadList();
	}
	
	private void listHeader()
	{
		TableRow row = new TableRow(this);
		row.setBackgroundColor(Color.GRAY);
		TextView text = new TextView(this);
		
		text.setText(R.string.type);
		row.addView(text);
		
		text = new TextView(this);
		text.setText(R.string.weight);
		row.addView(text);
		
		text = new TextView(this);
		text.setText(R.string.city);
		row.addView(text);
		
		table.addView(row);
	}
	
	private void onProcess(LinkedList<String[]>list)
	{
		itemlist = list;
		
		hand.post(new Runnable(){
			
			public void run()
			{
				TableRow row;
				TextView text;
				for (String [] ss : itemlist)
				{
					row = new TableRow(ParcelsActivity.this);
					
					text = new TextView(ParcelsActivity.this);
					text.setText(ss[4]);
					row.addView(text);
					
					text = new TextView(ParcelsActivity.this);
					text.setText(ss[1]);
					row.addView(text);
					
					text = new TextView(ParcelsActivity.this);
					text.setText(ss[3]);
					row.addView(text);
					
					row.setOnClickListener(new ItemClick());
					table.addView(row);
				}
				loading.hide();
			}
		});
	}
	
	private void onErrorOccured()
	{
		hand.post(new Runnable(){
			
			public void run()
			{
				loading.hide();
				Toast.makeText(ParcelsActivity.this, getString(R.string.loadingDataFailed), Toast.LENGTH_SHORT).show();
			}
			
		});
	}
	
	private void loadList()
	{
		loading.show();
		if (mode == 1)
		{
			Client.getInstance().requestUnassignedParcels(new ThreadListResponseEvent(){
	
				public void process() {
					onProcess(list);
				}
	
				public void errorOccured() {
					onErrorOccured();
					
				}}, 0);
		}
		
		else if (mode == 2)
		{
			Client.getInstance().requestAssignedToMeParcels(new ThreadListResponseEvent(){
	
				public void process() {
					onProcess(list);
				}
	
				public void errorOccured() {
					onErrorOccured();
					
				}});
		}
		
		else if (mode == 3)
		{
			Client.getInstance().requestAssignedUndeliveredParcels(new ThreadListResponseEvent(){
	
				public void process() {
					onProcess(list);
				}
	
				public void errorOccured() {
					onErrorOccured();
					
				}});
		}
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		loading.dismiss();
	}
	
	private class ItemClick implements View.OnClickListener
	{

		public void onClick(View v) {
			
			//Toast.makeText(UnassignedParcelsActivity.this, Integer.toString(table.indexOfChild(v)), Toast.LENGTH_SHORT).show();
			int tabid = table.indexOfChild(v);
			int id = 0, i=1;
			for (String [] ss : itemlist)
			{
				if (i == tabid)
				{
					id = Integer.parseInt(ss[0]);
					//Toast.makeText(UnassignedParcelsActivity.this, ss[4], Toast.LENGTH_SHORT).show();
					break;
				}
				i++;
			}
			Intent in= new Intent (ParcelsActivity.this, ParcelDetailActivity.class);
			in.putExtra("dbid", id);
			in.putExtra("mode", mode);
			startActivity(in);
		}
	}
}
