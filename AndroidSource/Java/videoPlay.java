package ${YYAndroidPackageName};

import ${YYAndroidPackageName}.RunnerActivity;
import com.yoyogames.runner.RunnerJNILib;
import ${YYAndroidPackageName}.R;

import com.unity3d.ads.android.UnityAds;
import com.unity3d.ads.android.IUnityAdsListener;
import android.util.Log;
//import android.R;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
//import android.content.pm.ActivityInfo;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.content.res.AssetFileDescriptor;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Patterns;
import android.view.Display;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.Toast;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.view.KeyEvent;

//@SuppressWarnings("deprecation")
public class videoPlay extends Activity implements IUnityAdsListener,OnCompletionListener, OnErrorListener, OnInfoListener, OnPreparedListener, OnSeekCompleteListener, OnVideoSizeChangedListener, SurfaceHolder.Callback, MediaController.MediaPlayerControl
	{
		int EVENT_OTHER_SOCIAL = 70;
		
	Display currentDisplay;
	FrameLayout target_layout;
	SurfaceView surfaceView;
	SurfaceHolder surfaceHolder;
	MediaPlayer mediaPlayer;
	MediaController controller;
	AssetFileDescriptor afd;
	int videoWidth = 0, videoHeight = 0;
	public final static String LOGTAG = "VIDEOPLAY ANDROID";
	
	//-----------------------------------------\\
	//////////  -VIDEOPLAY VARIABLES-  \\\\\\\\\\--------------------------------------------------------------------
	//-----------------------------------------\\
	
	public static String 			FILE_NAME;
	public static double 			return_result;
	public static double			setup 			= 0;
	public static double			state			= 0;
	public static double 			media_type		= 1;
	public static double			scale_width		= 0;
	public static double			scale_height	= 0;
	public static double			pos_x			= 0;
	public static double			pos_y			= 0;
	public static double			ctrl			= 0;
	public static String 			bg_colour		= "c_black";
	public static double			loop			= 0;
	public static List<String> 		que = new ArrayList<String>();
	public static double			que_number		= 0;
	public static double			win_mode		= 0;
	public static boolean  			que_single 		= false;
	public static boolean 			DEBUGGING 		= false;
	
	//-----------------------------------------\\
	//////////  -VIDEOPLAY EXECUTERS-  \\\\\\\\\\--------------------------------------------------------------------
	//-----------------------------------------\\
	// VIDEOPLAY_INI()
	public static double videoplay_android_ini()
		{
		return_result = 0;
		try
			{
			if ( setup != 1 )
				{
				setup 			= 1; 			media_type 		= 1; state 			= 0; 	scale_width 	= 0;
				scale_height 	= 0; 			pos_x 			= 0; pos_y 			= 0; 	ctrl 			= 0;
				bg_colour		= "c_black"; 	loop			= 0; que_number		= 0;	win_mode		= 0;
				que_single = false;
				}
			return_result = 1;
			}
		catch ( Exception e )
			{
			Log.d( "VIDEOPLAY_ANDROID_INI", "EXCEPTION THROWN", e );
			}
		return return_result;
		}
	// VIDEOPLAY_ANDROID( String: filename )
	public double videoplay_android( String fn )
		{
		return_result = 0;
		try
			{
			media_type = 1;
			FILE_NAME = fn;
			if ( win_mode != 1 ) 	{Intent runVideoPlay = new Intent( RunnerActivity.CurrentActivity, videoPlay.class ); RunnerActivity.CurrentActivity.startActivity( runVideoPlay );}
			else					{Intent runVideoPlay = new Intent( RunnerActivity.CurrentActivity, videoPlayWindow.class ); RunnerActivity.CurrentActivity.startService( runVideoPlay );}
			return_result = 1;
			}
		catch ( Exception e )
			{
			Toast.makeText( getApplicationContext(), "Toast!", Toast.LENGTH_SHORT ).show();
			Log.d( "VIDEOPLAY_ANDROID", "EXCEPTION THROWN", e );
			}
		return return_result;
		}
	// VIDEOPLAY_ANDROID_CHOOSE()
	public double videoplay_android_choose()
		{
		media_type = 1;
		try
			{
			Intent openChooser = new Intent( RunnerActivity.CurrentActivity, videoPlayQueChooser.class );
			RunnerActivity.CurrentActivity.startActivity( openChooser );
			return 1;
			}
		catch ( Exception e )
			{
			return 0;
			}
		}
	// VIDEOPLAY_ANDROID_STREAM( String: filename )
	public double videoplay_android_stream( String fn )
		{
		return_result = 0;
		try
			{
			media_type = 2;
			FILE_NAME = fn;
			if ( win_mode != 1 ) 	{Intent runVideoPlay = new Intent( RunnerActivity.CurrentActivity, videoPlay.class ); RunnerActivity.CurrentActivity.startActivity( runVideoPlay );}
			else					{Intent runVideoPlay = new Intent( RunnerActivity.CurrentActivity, videoPlayWindow.class ); RunnerActivity.CurrentActivity.startService( runVideoPlay );}
			return_result = 1;
			}
		catch ( Exception e )
			{
			Log.d( "VIDEOPLAY_ANDROID_STREAM", "EXCEPTION THROWN", e);
			}
		return return_result;
		}
	// VIDEOPLAY_ANDROID_WINDOW( double state )
	public double videoplay_android_set_window( double state ) 		{ win_mode = state; return 1; }
	// VIDEOPLAY_ANDROID_QUE
	public double videopaly_android_que_play()
		{
		return_result = 0;
		try
			{
			media_type = 3;
			if ( win_mode != 1 ) 	{Intent runVideoPlay = new Intent( RunnerActivity.CurrentActivity, videoPlay.class ); RunnerActivity.CurrentActivity.startActivity( runVideoPlay );}
			else					{Intent runVideoPlay = new Intent( RunnerActivity.CurrentActivity, videoPlayWindow.class ); RunnerActivity.CurrentActivity.startService( runVideoPlay );}
			return_result = 1;
			}
		catch ( Exception e )
			{
			Log.d( "VIDEOPLAY_ANDROID_QUE_PLAY", "EXCEPTION THROWN", e);
			}
		return return_result;
		}
	// VIDEOPLAY_ANDROID_QUE_PLAY_POS( double pos )
	public double videoplay_android_que_play_pos( double pos )
		{
		return_result = 0;
		try
			{
			media_type = 3;
			que_number = pos;
			que_single = true;
			if ( win_mode != 1 ) 	{Intent runVideoPlay = new Intent( RunnerActivity.CurrentActivity, videoPlay.class ); RunnerActivity.CurrentActivity.startActivity( runVideoPlay );}
			else					{Intent runVideoPlay = new Intent( RunnerActivity.CurrentActivity, videoPlayWindow.class ); RunnerActivity.CurrentActivity.startService( runVideoPlay );}
			return_result = 1;
			}
		catch ( Exception e )
			{
			Log.d( "VIDEOPLAY_ANDROID_QUE_PLAY", "EXCEPTION THROWN", e);
			}
		return return_result;
		}
	// VIDEOPLAY_ANDROID_QUE_PLAY_FROM( double pos )
		public double videoplay_android_que_play_from( double pos )
			{
			return_result = 0;
			try
				{
				media_type = 3;
				que_number = pos;
				if ( win_mode != 1 ) 	{Intent runVideoPlay = new Intent( RunnerActivity.CurrentActivity, videoPlay.class ); RunnerActivity.CurrentActivity.startActivity( runVideoPlay );}
				else					{Intent runVideoPlay = new Intent( RunnerActivity.CurrentActivity, videoPlayWindow.class ); RunnerActivity.CurrentActivity.startService( runVideoPlay );}
				return_result = 1;
				}
			catch ( Exception e )
				{
				Log.d( "VIDEOPLAY_ANDROID_QUE_PLAY", "EXCEPTION THROWN", e);
				}
			return return_result;
			}
	
	//--------------------------------------------------\\
	//////////     -VIDEOPLAY_SET COMMANDS-     \\\\\\\\\\--------------------------------------------------------------------
	//--------------------------------------------------\\
	public double videoplay_android_set_scale( double width, double height )  	{ scale_width 	= width; scale_height = height; return 1; }
	
	public double videoplay_android_set_position( double posx, double posy ) 	{ pos_x 		= posx; pos_y = posy; return 1; }
	
	public double videoplay_android_set_ctrl( double control_type ) 			{ ctrl 			= control_type; return 1; }
	
	public double videoplay_android_set_background( String bg ) 				{ bg_colour 	= bg; return 1; }
	
	public double videoplay_android_set_loop( double loop_state ) 				{ loop 			= loop_state; return 1; }
	
	public double videoplay_android_set_debugging( double state )				{ if (state == 1) DEBUGGING = true; else DEBUGGING = false; return 1;  }
	//--------------------------------------------------\\
	//////////     -VIDEOPLAY_GET COMMANDS-     \\\\\\\\\\--------------------------------------------------------------------
	//--------------------------------------------------\\
	
	public double videoplay_android_get_scaling_width() 	{ return scale_width; }
	
	public double videoplay_android_get_scaling_height() 	{ return scale_height; }
	
	public double videoplay_android_get_position_x() 		{ return pos_x; }
	
	public double videoplay_android_get_position_y() 		{ return pos_y; }
	
	public double videoplay_android_get_loop() 				{ return loop; }
	
	public double videoplay_android_get_isplaying()
		{
		try
			{
			if ( mediaPlayer.isPlaying() )
				{
				return 1;
				}
			else
				{
				return 0;
				}
			}
		catch ( Exception e )
			{
			Log.d( "VIDEOPLAY_ANDROID_GET_IS_PLAYING", "EXCEPTION THROWN", e);
			return 0;
			}
		}

	//--------------------------------------------------\\
	////////// -VIDEOPLAY_ANDROID_QUE COMMANDS- \\\\\\\\\\--------------------------------------------------------------------
	//--------------------------------------------------\\
	
	public double videopaly_android_que_add( String fn )
		{
		que.add( fn );
		return 1;
		}
	
	public double videopaly_android_que_remove( double position )
		{
		int que_pos = (int) position;
		try
			{
			que.remove( que_pos );
			return 1;
			}
		catch ( Exception e )
			{
			return 0;
			}
		}
	
	public double videoplay_android_que_choose()
		{
		try
			{
			Intent openChooser = new Intent( RunnerActivity.CurrentActivity, videoPlayQueChooser.class );
			RunnerActivity.CurrentActivity.startActivity( openChooser );
			return 1;
			}
		catch ( Exception e )
			{
			return 0;
			}
		}
	
	public double videoplay_android_que_clear()
		{
		try
			{
			que.clear();
			if ( que.isEmpty() )
				{
				return 1;
				}
			else
				{
				return 0;
				}
			}
		catch ( Exception e )
			{
			return 0;
			}
		}
	
	public String videoplay_android_que_currently_playing()
		{
		if ( que_number == 0 )
			{
			try
				{
				return que.get( (int) que_number );
				}
			catch ( Exception e )
				{
				return "QUE LIST EMPTY";
				}
			}
		else
			{
			try
				{
				return que.get( (int) que_number -1 );
				}
			catch ( Exception e )
				{
				return "Failed";
				}
			}
		}
	
	public double videoplay_android_que_size()
		{
		return (double) que.size();
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void onCreate( Bundle savedInstanceState )
		{
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		super.onCreate( savedInstanceState );
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		requestWindowFeature( Window.FEATURE_NO_TITLE );
        getWindow( ).setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView( R.layout.videoplay_display );
        FrameLayout target_layout = (FrameLayout) findViewById(R.layout.videoplay_display);
		surfaceView 	= ( SurfaceView ) this.findViewById( R.id.surfaceview );
		controller 		= new MediaController( this );
		currentDisplay 	= getWindowManager().getDefaultDisplay();
		surfaceHolder 	= surfaceView.getHolder();
		surfaceHolder.addCallback( this );
		surfaceHolder.setType( SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS );
		surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setOnPreparedListener( this );
		mediaPlayer.setOnCompletionListener( this );
		mediaPlayer.setOnSeekCompleteListener( this );
		mediaPlayer.setOnVideoSizeChangedListener( this );
		mediaPlayer.setOnInfoListener( this );
		mediaPlayer.setOnErrorListener( this );
		setVideoSource();
		}
	
	public void surfaceCreated( SurfaceHolder holder )
		{
		mediaPlayer.setDisplay( holder );
		try
			{
			mediaPlayer.prepare();
			}
		catch ( Exception e )
			{
			finish();
			}
		}
	
	public void onPrepared( MediaPlayer mp )
		{
		if ( videoPlay.scale_height == 0 && videoPlay.scale_width == 0 )
	    	{
				videoWidth 				= mp.getVideoWidth();
				videoHeight 			= mp.getVideoHeight();
				if ( videoWidth > currentDisplay.getWidth() || videoHeight > currentDisplay.getHeight() )
				{
					float heightRatio 	= ( float ) videoHeight / ( float ) currentDisplay.getHeight();
					float widthRatio 	= ( float ) videoWidth  / ( float ) currentDisplay.getWidth();
					if ( heightRatio > 1 || widthRatio > 1 )
					{
						if ( heightRatio > widthRatio )
						{
							videoHeight = ( int ) Math.ceil( ( float ) videoHeight / ( float ) heightRatio );
							videoWidth 	= ( int ) Math.ceil( ( float ) videoWidth  / ( float ) heightRatio );
						}
						else
						{
							videoHeight = ( int ) Math.ceil( ( float ) videoHeight / ( float ) widthRatio );
							videoWidth 	= ( int ) Math.ceil( ( float ) videoWidth  / ( float ) widthRatio );
						}
					}
				}
				else
					{
						float heightRatio 	= ( float ) videoHeight / ( float ) currentDisplay.getHeight();
						float widthRatio 	= ( float ) videoWidth  / ( float ) currentDisplay.getWidth();
						if ( heightRatio < 1 || widthRatio < 1 )
						{
							if ( heightRatio > widthRatio )
							{
								videoHeight = ( int ) Math.ceil( ( float ) videoHeight / ( float ) heightRatio );
								videoWidth 	= ( int ) Math.ceil( ( float ) videoWidth  / ( float ) heightRatio );
							}
							else
							{
								videoHeight = ( int ) Math.ceil( ( float ) videoHeight / ( float ) widthRatio );
								videoWidth 	= ( int ) Math.ceil( ( float ) videoWidth  / ( float ) widthRatio );
							}
						}
					}
	    	}
	    else
	    	{
	    	Double d_w 		= videoPlay.scale_width;
	    	Double d_h 		= videoPlay.scale_height;
	    	int w 			= d_w.intValue();
	    	int h 			= d_h.intValue();
	    	videoWidth 	 	= w;
	    	videoHeight		= h;
	    	}
		if ( videoPlay.pos_x == 0 && videoPlay.pos_y == 0 )
	    	{
			surfaceView.setLayoutParams( new FrameLayout.LayoutParams( videoWidth, videoHeight, Gravity.CENTER ) );
	    	}
	    else
	    	{
	    	Double d_x 		= videoPlay.pos_x;
	    	Double d_y 		= videoPlay.pos_y;
	    	int x 			= d_x.intValue();
	    	int y  			= d_y.intValue();
	    	surfaceView.setLayoutParams( new FrameLayout.LayoutParams( videoWidth, videoHeight, Gravity.TOP | Gravity.LEFT ) );
	    	surfaceView.setX(x);
	    	surfaceView.setY(y);
	    	}
		setBackgroundColour();
		controller.setMediaPlayer( this );
		controller.setAnchorView( this.findViewById( R.id.surfaceview ) );
		controller.setEnabled( true );
		if ( ctrl == 1 )	controller.show( 0 );
		mp.start();
		}
	
	public void surfaceChanged( SurfaceHolder holder, int format, int width, int height ) { }
	
	public void surfaceDestroyed( SurfaceHolder holder ) { }
	
	public void onCompletion( MediaPlayer mp )
		{
			if (UnityAds_IsReady() == 1)
			{
				UnityAds_Show();
			}
			
		int mt_value = (int) media_type;
		switch ( mt_value )
			{
		case 1:		if ( loop == 1 ) { mp.start(); break; } else { finish(); }
					break;
		case 2:		if ( loop == 1 ) { mp.start(); break; } else { finish(); }
					break;
		case 3:		if ( que_single ) { que_single = false; finish(); break; }
					if ( que_number <= que.size() )
						{
						setVideoSource();
						try
							{
							mp.prepare();
							}
						catch ( IllegalStateException e )
							{
							e.printStackTrace();
							}
						catch ( IOException e )
							{
							e.printStackTrace();
							}
						}
					else
						{
						if ( loop == 1 )
							{
							que_number = 0;
							setVideoSource();
							try
								{
								mp.prepare();
								}
							catch ( IllegalStateException e )
								{
								e.printStackTrace();
								}
							catch ( IOException e )
								{
								e.printStackTrace();
								}
							}
						else
							{
							finish();
							}
						}
					break;
			default:
			}
		}
	
	public void onSeekComplete( MediaPlayer mp ) { }

	public void onVideoSizeChanged( MediaPlayer mp, int width, int height ) { }

	public boolean canPause() { return true; }

	public boolean canSeekBackward() { return true; }

	public boolean canSeekForward() { return true; }

	public int getBufferPercentage() { return 0; }

	public int getCurrentPosition() { return mediaPlayer.getCurrentPosition(); }

	public int getDuration() { return mediaPlayer.getDuration(); }

	public boolean isPlaying() { return mediaPlayer.isPlaying(); }
	
	public int getAudioSessionId() { return 0; }

	public void pause()
		{
		if ( mediaPlayer.isPlaying() )
			{
			mediaPlayer.pause();
			}
		}

	public void seekTo( int pos ) 
	{
			//Toast.makeText(getApplicationContext(), String.valueOf(pos), Toast.LENGTH_LONG).show();
			mediaPlayer.seekTo( pos ); 
	}

	public void start() { mediaPlayer.start(); } 

	@Override
	public boolean onTouchEvent( MotionEvent ev )
		{
		if ( ctrl == 2 )
			{
				switch (ev.getAction()) 
				{
					//case MotionEvent.ACTION_DOWN:
					//case MotionEvent.ACTION_MOVE:
					case MotionEvent.ACTION_UP:
						if ( controller.isShowing() )
						{
							controller.hide();
						}
						else
						{
							controller.show();
						}
				}
			}
		return false;
		}

	@Override
	protected void onDestroy()
		{
		if(mediaPlayer!=null)
		{
			if ( controller.isShowing() )
			{
				controller.hide();
			}
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
			setup 	= 0;
			videoplay_android_ini();
		}
	super.onDestroy();
		}
	
	//-----------------------------------------\\
	//////////    -VIDEOPLAY TOOLS-    \\\\\\\\\\--------------------------------------------------------------------
	//-----------------------------------------\\
	
	public void setVideoSource()
		{
		int mt_value = (int) media_type;
		switch ( mt_value )
			{
			case 1:		try
					    	{
							if ( FILE_NAME.contains( "content:" ) )
								{
									//Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_LONG).show();
								//mediaPlayer.setDataSource( this, getFileUri(FILE_NAME) ); //Uri.parse(FILE_NAME) );
								mediaPlayer.setDataSource( this, Uri.parse(FILE_NAME) );
								}
							else
								{
									 if ( FILE_NAME.contains( "working_directory" ) )
									 {
										 FILE_NAME=FILE_NAME.replaceAll("working_directory","");
										 //Toast.makeText(getApplicationContext(), FILE_NAME, Toast.LENGTH_LONG).show();
										 mediaPlayer.setDataSource( this, getFileUri(FILE_NAME) );
									 }
									 else
									 {
										afd = getAssets().openFd( FILE_NAME );
										mediaPlayer.setDataSource( afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength() );
									 }
								//afd = getAssets().openFd( FILE_NAME );
								//mediaPlayer.setDataSource( afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength() );
								//mediaPlayer.setDataSource( this, getFileUri(FILE_NAME) );
								}
							}
					    catch ( IOException e )
					    	{
					    	if ( videoPlay.DEBUGGING ) Toast.makeText( getApplicationContext(), "-CANNOT FIND VIDEO SOURCE-\n[media_type: 1]\nFilename: " +FILE_NAME +"\nLocation: Project Included Files\n\nNote:\nIf your video is in a folder under Included Files,\nyou must also include the folder name(s) in your\nfilename.\n\nExample:\nvideoplay_android( folder1/folder2/sample.mp4 )", Toast.LENGTH_SHORT ).show();
							e.printStackTrace();
					    	}
						break;
			case 2:		try
					    	{
								//Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_LONG).show();
							mediaPlayer.setDataSource( FILE_NAME );
					    	}
						catch ( IOException e )
				    		{
							if ( videoPlay.DEBUGGING ) Toast.makeText( getApplicationContext(), "-CANNOT FIND VIDEO SOURCE-\n[media_type: 2]\nURL: " +FILE_NAME +"\n\nMake sure your url is correct.", Toast.LENGTH_SHORT ).show();
							e.printStackTrace();
				    		}
						break;
			case 3:		String QUE_FILE_NAME = que.get( (int) que_number );
						que_number++;
						if ( Patterns.WEB_URL.matcher( QUE_FILE_NAME ).matches() )
							{
							try
						    	{
									//Toast.makeText(getApplicationContext(), "4", Toast.LENGTH_LONG).show();
								mediaPlayer.setDataSource( QUE_FILE_NAME );
						    	}
							catch ( IOException e )
					    		{
								if ( videoPlay.DEBUGGING ) Toast.makeText( getApplicationContext(), "-CANNOT FIND VIDEO SOURCE-\n[media_type: 3]\nURL: " +QUE_FILE_NAME +"\n\nMake sure your url is correct.", Toast.LENGTH_SHORT ).show();
								e.printStackTrace();
					    		}
							}
						else
							{
							try
						    	{
								if ( FILE_NAME.contains( "content:" ) )
									{
										//Toast.makeText(getApplicationContext(), "5", Toast.LENGTH_LONG).show();
									mediaPlayer.setDataSource( this, Uri.parse(FILE_NAME) );
									}
								else
									{
										//Toast.makeText(getApplicationContext(), "6", Toast.LENGTH_LONG).show();
									afd = getAssets().openFd( QUE_FILE_NAME );
									mediaPlayer.setDataSource( afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength() );
									}
								}
						    catch ( IOException e )
						    	{
						    	if ( videoPlay.DEBUGGING ) Toast.makeText( getApplicationContext(), "-CANNOT FIND VIDEO SOURCE-\n[media_type: 3]\nFilename: " +QUE_FILE_NAME +"\nLocation: Project Included Files\n\nNote:\nIf your video is in a folder under Included Files,\nyou must also include the folder name(s) in your\nfilename.\n\nExample:\nvideoplay_android( folder1/folder2/sample.mp4 )", Toast.LENGTH_SHORT ).show();
								e.printStackTrace();
						    	}
							}
						break;
			default:
			}
		}
	public void setBackgroundColour()
		{
		try
		{
		if ( bg_colour == "c_aqua" ) 	target_layout.setBackgroundColor(getResources().getColor(R.color.c_aqua));
		if ( bg_colour == "c_black" ) 	target_layout.setBackgroundColor(getResources().getColor(R.color.c_black));
		if ( bg_colour == "c_blue" ) 	target_layout.setBackgroundColor(getResources().getColor(R.color.c_blue));
		if ( bg_colour == "c_dkgray" ) 	target_layout.setBackgroundColor(getResources().getColor(R.color.c_dkgray));
		if ( bg_colour == "c_fuchsia" )	target_layout.setBackgroundColor(getResources().getColor(R.color.c_fuchsia));
		if ( bg_colour == "c_gray" ) 	target_layout.setBackgroundColor(getResources().getColor(R.color.c_gray));
		if ( bg_colour == "c_green" ) 	target_layout.setBackgroundColor(getResources().getColor(R.color.c_green));
		if ( bg_colour == "c_lime" ) 	target_layout.setBackgroundColor(getResources().getColor(R.color.c_lime));
		if ( bg_colour == "c_ltgray" ) 	target_layout.setBackgroundColor(getResources().getColor(R.color.c_ltgray));
		if ( bg_colour == "c_maroon" ) 	target_layout.setBackgroundColor(getResources().getColor(R.color.c_maroon));
		if ( bg_colour == "c_navy" ) 	target_layout.setBackgroundColor(getResources().getColor(R.color.c_navy));
		if ( bg_colour == "c_olive" ) 	target_layout.setBackgroundColor(getResources().getColor(R.color.c_olive));
		if ( bg_colour == "c_orange" ) 	target_layout.setBackgroundColor(getResources().getColor(R.color.c_orange));
		if ( bg_colour == "c_purple" )	target_layout.setBackgroundColor(getResources().getColor(R.color.c_purple));
		if ( bg_colour == "c_red" )		target_layout.setBackgroundColor(getResources().getColor(R.color.c_red));
		if ( bg_colour == "c_silver" )	target_layout.setBackgroundColor(getResources().getColor(R.color.c_silver));
		if ( bg_colour == "c_teal" )	target_layout.setBackgroundColor(getResources().getColor(R.color.c_teal));
		if ( bg_colour == "c_white" ) 	target_layout.setBackgroundColor(getResources().getColor(R.color.c_white));
		if ( bg_colour == "c_yellow" )	target_layout.setBackgroundColor(getResources().getColor(R.color.c_yellow));
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		}
	
	//  ON EVENT LOGGING
	public boolean onError( MediaPlayer mp, int whatError, int extra )
		{
		if ( whatError == MediaPlayer.MEDIA_ERROR_SERVER_DIED )
			{
			Log.v( LOGTAG, "Media Error, Server Died " + extra );
			}
		else if ( whatError == MediaPlayer.MEDIA_ERROR_UNKNOWN )
			{
			Log.v( LOGTAG, "Media Error, Error Unknown " + extra );
			}
		return false;
		}
	public boolean onInfo( MediaPlayer mp, int whatInfo, int extra )
		{
		if 		( whatInfo == MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING ) 	Log.v( LOGTAG, "Media Info, Media Info Bad Interleaving " + extra );
		else if ( whatInfo == MediaPlayer.MEDIA_INFO_NOT_SEEKABLE ) 		Log.v( LOGTAG, "Media Info, Media Info Not Seekable " + extra );
		else if ( whatInfo == MediaPlayer.MEDIA_INFO_UNKNOWN ) 				Log.v( LOGTAG, "Media Info, Media Info Unknown " + extra );
		else if ( whatInfo == MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING ) 	Log.v( LOGTAG, "MediaInfo,  Media Info Video Track Lagging " + extra );
		else if ( whatInfo == MediaPlayer.MEDIA_INFO_METADATA_UPDATE ) 		Log.v( LOGTAG, "MediaInfo,  Media Info Metadata Update " + extra );
		return false;
		}
		
		public Uri getFileUri(String FILE_NAME) 
		{
			//String path = activity.getFilesDir().getAbsolutePath() + "/" + filename;
			//String path = getFilesDir().getAbsolutePath()+ "/" + FILE_NAME;
			String path = getFilesDir().getAbsolutePath()+ FILE_NAME;
			File file = new File(path);
			file.setReadable(true, false);
			//Toast.makeText(getApplicationContext(), path, Toast.LENGTH_LONG).show();
			//return Uri.parse("file:///" + path);
			return Uri.parse(path);
		}
		
		
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) 
        { //Back key pressed
           //Things to Do
		   if (UnityAds_IsReady() == 1)
			{
				UnityAds_Show();
			}
			
            if(mediaPlayer!= null)
            {
                mediaPlayer.stop();
                mediaPlayer=null;
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
		
		////////////////////////////////////////////////////// Unity Ads ///////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////// Unity Ads ///////////////////////////////////////////////////////////////////////////
		public void UnityAds_Init(String _app_id)
	{
		final String app_id = _app_id;
		
		Log.i("yoyo","Initialising UnityAds with activity "+RunnerActivity.CurrentActivity);
		RunnerActivity.ViewHandler.post( new Runnable() {
			public void run() {
				UnityAds.init(RunnerActivity.CurrentActivity, app_id, (IUnityAdsListener)videoPlay.this);
			}
		 });
	}
	
	public void UnityAds_TestMode(double _testMode) {
	
		final boolean testMode = (_testMode == 1) ? true : false;
		RunnerActivity.ViewHandler.post( new Runnable() {
			public void run() {
				UnityAds.setTestMode(testMode);
			}
		 });
		 
	}
	
	public double UnityAds_IsReady() {
		
		boolean showRes = (UnityAds.canShow() && UnityAds.canShowAds());
		double ready = (showRes) ? 1 : 0;
		
		return ready;
	}
	
	public void UnityAds_Show() {
		
		Log.i("yoyo","UnityAds_Show Ad");
		RunnerActivity.ViewHandler.post( new Runnable() {
			public void run() {
				if(UnityAds.canShow() && UnityAds.canShowAds()) {
					//UnityAds.setTestMode(false);
					UnityAds.setZone("rewardedVideo");
					UnityAds.show();
				}	
			}
		 });
	}
	
	 @Override
    public void onHide() {
	
       int dsMapIndex = RunnerJNILib.jCreateDsMap(null, null, null);
	   RunnerJNILib.DsMapAddString( dsMapIndex, "type", "hide_unity_ads" );
	   RunnerJNILib.CreateAsynEventWithDSMap(dsMapIndex,EVENT_OTHER_SOCIAL);
	   Log.i("yoyo","UnityAds_onHide ");
		
    }

    @Override
    public void onShow() {
	
       int dsMapIndex = RunnerJNILib.jCreateDsMap(null, null, null);
	   RunnerJNILib.DsMapAddString( dsMapIndex, "type", "show_unity_ads" );
	   RunnerJNILib.CreateAsynEventWithDSMap(dsMapIndex,EVENT_OTHER_SOCIAL);
	   Log.i("yoyo","UnityAds_onShow ");
	   
    }

    @Override
    public void onVideoStarted() {
	
       int dsMapIndex = RunnerJNILib.jCreateDsMap(null, null, null);
	   RunnerJNILib.DsMapAddString( dsMapIndex, "type", "video_started_unity_ads" );
	   RunnerJNILib.CreateAsynEventWithDSMap(dsMapIndex,EVENT_OTHER_SOCIAL);
	   Log.i("yoyo","UnityAds_onVideoStarted ");
    }

    @Override
    public void onVideoCompleted(String item, boolean skipped) {
      
	    int dsMapIndex = RunnerJNILib.jCreateDsMap(null, null, null);
		RunnerJNILib.DsMapAddString( dsMapIndex, "type", "video_completed_unity_ads" );
		double shown = (skipped) ? 1 : 0;
		RunnerJNILib.DsMapAddString( dsMapIndex, "item", item );
		RunnerJNILib.DsMapAddDouble( dsMapIndex,"skipped",shown);
		RunnerJNILib.CreateAsynEventWithDSMap(dsMapIndex,EVENT_OTHER_SOCIAL);
		Log.i("yoyo","UnityAds_onVideoCompleted ");
	  
    }

    @Override
    public void onFetchCompleted() {
        
	   int dsMapIndex = RunnerJNILib.jCreateDsMap(null, null, null);
	   RunnerJNILib.DsMapAddString( dsMapIndex, "type", "fetch_completed_unity_ads" );
	   RunnerJNILib.CreateAsynEventWithDSMap(dsMapIndex,EVENT_OTHER_SOCIAL);
	   Log.i("yoyo","UnityAds_onFetchCompleted ");
		
    }

    @Override
    public void onFetchFailed() {
	
       int dsMapIndex = RunnerJNILib.jCreateDsMap(null, null, null);
	   RunnerJNILib.DsMapAddString( dsMapIndex, "type", "fetch_failed_unity_ads" );
	   RunnerJNILib.CreateAsynEventWithDSMap(dsMapIndex,EVENT_OTHER_SOCIAL);
	   Log.i("yoyo","UnityAds_onFetchFailed ");
		
    }
	}