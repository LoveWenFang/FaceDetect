package com.synjones.idcard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.synjones.bluetooth.DecodeWlt;
import com.synjones.idcard.DecodeAIDLService.Stub;



public class RemoteDecodeService extends Service {
		public static final String TAG = "PhotoDecodeService";
		
		private DecodeAIDLService.Stub mBinder = new Stub() {			
			@Override
			public byte[] decode(byte[] wlt) throws RemoteException {
				String bmpPath = getFileStreamPath("photo.bmp").getAbsolutePath();
				String wltPath = getFileStreamPath("photo.wlt").getAbsolutePath();
				File wltFile = new File(wltPath);

				
				try {
					FileOutputStream fos = new FileOutputStream(wltFile);
					fos.write(wlt);
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				////byte[] wlt_ok=FileOperate.readAllSdcardFile("/wlt.wlt");
				//log.error("wlt");
				//fos.write(wlt.img);
				
				DecodeWlt dw = new DecodeWlt();
				
				int result = dw.Wlt2Bmp(wltPath, bmpPath);
				byte [] buffer =null;  
				FileInputStream fin;
				try {
					File bmpFile = new File(bmpPath);
					fin = new FileInputStream(bmpFile);
					 int length = fin.available();   
					 buffer=new byte[length];
			         fin.read(buffer);       
			         fin.close(); 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
				return buffer;
			}
		};
		
		@Override
		public void onCreate() {
			super.onCreate();
			Log.d(TAG, "onCreate() executed");
		}
		
		@Override
		public int onStartCommand(Intent intent, int flags, int startId) {
			Log.d(TAG, "onStartCommand() executed");
			return super.onStartCommand(intent, flags, startId);
		}
		
		@Override
		public void onDestroy() {
			super.onDestroy();
			Log.d(TAG, "onDestroy() executed");
		}
		
		@Override
		public IBinder onBind(Intent intent) {
			return mBinder;
		}
		


}