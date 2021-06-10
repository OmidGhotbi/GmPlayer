package ${YYAndroidPackageName};

import ${YYAndroidPackageName}.RunnerActivity;
import com.yoyogames.runner.RunnerJNILib;
import ${YYAndroidPackageName}.R;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class videoPlayQueChooser extends Activity
	{
	@Override
	protected void onCreate( Bundle savedInstanceState )
		{
		super.onCreate( savedInstanceState );
		Intent mediaChooser = new Intent( Intent.ACTION_PICK );
		mediaChooser.setType( "video/*" );
		startActivityForResult( mediaChooser, 1 );
		}
	
	@Override
	protected void onActivityResult (int requestCode, int resultCode, Intent data ) 
		{
		super.onActivityResult(requestCode, resultCode, data);
		if ( resultCode == Activity.RESULT_OK )
			{
			Uri uri = data.getData();
			String item;
			item = String.valueOf( uri );
			if ( videoPlay.DEBUGGING ) Toast.makeText(getApplicationContext(), "External Source = " +item, Toast.LENGTH_LONG).show();
			if ( videoPlay.media_type == 3 )
				{
				videoPlay.que.add( item );
				}
			else
				{
				videoPlay.FILE_NAME = item;
				try
					{
					if ( videoPlay.win_mode != 1 ) 	{Intent runVideoPlay = new Intent( RunnerActivity.CurrentActivity, videoPlay.class ); RunnerActivity.CurrentActivity.startActivity( runVideoPlay );}
					else					{Intent runVideoPlay = new Intent( RunnerActivity.CurrentActivity, videoPlayWindow.class );RunnerActivity.CurrentActivity.startService( runVideoPlay );}
					}
				catch ( Exception e )
					{
					if ( videoPlay.DEBUGGING ) Toast.makeText(getApplicationContext(), "VideoPlay file chooser could not find run activity.", Toast.LENGTH_LONG).show();
					Log.d( "VIDEOPLAY_ANDROID", "EXCEPTION THROWN", e );
					}
				}
			
			}
		finish();
		}
	}
