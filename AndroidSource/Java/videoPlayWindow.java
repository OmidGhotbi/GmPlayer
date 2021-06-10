package ${YYAndroidPackageName};

import ${YYAndroidPackageName}.RunnerActivity;
import com.yoyogames.runner.RunnerJNILib;
import ${YYAndroidPackageName}.R;

import java.io.IOException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

public class videoPlayWindow extends Service implements OnCompletionListener, OnPreparedListener, SurfaceHolder.Callback
	{
	WindowManager.LayoutParams mParams; WindowManager cinema; SurfaceView screen;
    SurfaceHolder holder; MediaPlayer mediaPlayer; AssetFileDescriptor afd;

	@Override public IBinder onBind( Intent intent ) { return null; }
	
	@Override
	public void onCreate()
		{
		super.onCreate();
		screen 			= new SurfaceView( this );
        cinema 			= ( WindowManager ) getApplicationContext( ).getSystemService( Context.WINDOW_SERVICE );
        mParams 		= new WindowManager.LayoutParams( );
        mParams.type 	= WindowManager.LayoutParams.TYPE_PHONE;
        mParams.format 	= PixelFormat.TRANSLUCENT;
        mParams.flags 	= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        if ( videoPlay.scale_height == 0 && videoPlay.scale_width == 0 )
        	{
        	//mParams.width 	= WindowManager.LayoutParams.WRAP_CONTENT;
        	mParams.height 	= WindowManager.LayoutParams.WRAP_CONTENT;
        	}
        else
        	{
        	Double d_w 		= videoPlay.scale_width;
        	Double d_h 		= videoPlay.scale_height;
        	int w 			= d_w.intValue();
        	int h 			= d_h.intValue();
        	mParams.width 	= w;
        	mParams.height 	= h;
        	}
        //mParams.gravity = Gravity.LEFT | Gravity.TOP;
		if ( videoPlay.pos_x == 0 && videoPlay.pos_y == 0 )
        	{
			mParams.gravity = Gravity.CENTER;
        	}
        else
        	{
        	Double d_x 		= videoPlay.pos_x;
        	Double d_y 		= videoPlay.pos_y;
        	int x 			= d_x.intValue();
        	int y 			= d_y.intValue();
        	mParams.gravity = Gravity.TOP | Gravity.LEFT;
        	mParams.x 		= x;
        	mParams.y 		= y;
        	}
	    cinema.addView( screen, mParams );
	    holder 				= screen.getHolder();
		holder.addCallback( this );
	    holder.setType( SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS );
	    mediaPlayer 		= new MediaPlayer();
		mediaPlayer.setOnCompletionListener( this );
		mediaPlayer.setOnPreparedListener( this );
		setVideoSource();
		}

	@Override
	public void surfaceCreated( SurfaceHolder holder )
		{
		mediaPlayer.setDisplay( holder );
		try
			{
			mediaPlayer.prepare();
			}
		catch ( Exception e )
			{
			Log.d( "VIDEOPLAY_WINDOW Error Prepping mediaPlayer", e.getMessage() );
			}
		}

	@Override
	public void onPrepared( MediaPlayer mp ) 
		{
		if ( videoPlay.loop == 1 )
			{
			mp.setLooping( true );
			}
		videoPlay.state = 1;
		mp.start( );
		screen.requestFocus();
		}
	
	@Override
	public void onCompletion( MediaPlayer mp )
	{
	int mt_value = (int) videoPlay.media_type;
	switch ( mt_value )
		{
	case 1:		if ( videoPlay.loop == 1 ) { mp.start(); break; } else { videoPlayWindow.this.stopSelf(); }
				break;
	case 2:		if ( videoPlay.loop == 1 ) { mp.start(); break; } else { videoPlayWindow.this.stopSelf(); }
				break;
	case 3:		if ( videoPlay.que_single ) { videoPlay.que_single = false; videoPlayWindow.this.stopSelf(); break; }
				if ( videoPlay.que_number <= videoPlay.que.size() )
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
					if ( videoPlay.loop == 1 )
						{
						videoPlay.que_number = 0;
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
						videoPlayWindow.this.stopSelf();
						}
					}
				break;
		default:
		}
	}	
	
	@Override
	public void onDestroy()
		{
		mediaPlayer.setDisplay( null );
		mediaPlayer.release();
		mediaPlayer = null;
		if ( this.screen != null )
			{
			this.cinema.removeView( this.screen );
			}
		if ( this.cinema != null ) this.cinema = null;
		videoPlay.setup 			= 0;
		videoPlay.videoplay_android_ini();
		super.onDestroy();
		}
	
	@Override
	public void surfaceChanged( SurfaceHolder holder, int format, int width, int height ){}
	
	@Override
	public void surfaceDestroyed( SurfaceHolder holder ){}
	
	
	public void setVideoSource()
		{
		int mt_value = (int) videoPlay.media_type;
		switch ( mt_value )
			{
			case 1:		try
					    	{
							if ( videoPlay.FILE_NAME.contains( "content:" ) )
								{
								mediaPlayer.setDataSource( this, Uri.parse(videoPlay.FILE_NAME) );
								}
							else
								{
								afd = getAssets().openFd( videoPlay.FILE_NAME );
								mediaPlayer.setDataSource( afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength() );
								}
							}
					    catch ( IOException e )
					    	{
					    	if ( videoPlay.DEBUGGING ) Toast.makeText( getApplicationContext(), "-CANNOT FIND VIDEO SOURCE-\n[media_type: 1]\nFilename: " +videoPlay.FILE_NAME +"\nLocation: Project Included Files\n\nNote:\nIf your video is in a folder under Included Files,\nyou must also include the folder name(s) in your\nfilename.\n\nExample:\nvideoplay_android( folder1/folder2/sample.mp4 )", Toast.LENGTH_SHORT ).show();
							e.printStackTrace();
					    	}
						break;
			case 2:		try
					    	{
							mediaPlayer.setDataSource( videoPlay.FILE_NAME );
					    	}
						catch ( IOException e )
				    		{
							if ( videoPlay.DEBUGGING ) Toast.makeText( getApplicationContext(), "-CANNOT FIND VIDEO SOURCE-\n[media_type: 2]\nURL: " +videoPlay.FILE_NAME +"\n\nMake sure your url is correct.", Toast.LENGTH_SHORT ).show();
							e.printStackTrace();
				    		}
						break;
			case 3:		String QUE_FILE_NAME = videoPlay.que.get( (int) videoPlay.que_number );
						if ( Patterns.WEB_URL.matcher( QUE_FILE_NAME ).matches() )
							{
							try
						    	{
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
								if ( videoPlay.FILE_NAME.contains( "content:" ) )
									{
									mediaPlayer.setDataSource( this, Uri.parse(videoPlay.FILE_NAME) );
									}
								else
									{
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
	
	}