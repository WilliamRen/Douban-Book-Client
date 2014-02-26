package com.bluesunshine.doubanbook.view;

import java.util.Hashtable;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluesunshine.doubanbook.R;
import com.bluesunshine.doubanbook.R.id;
import com.bluesunshine.doubanbook.R.layout;
import com.bluesunshine.doubanbook.R.menu;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRcodeActivity extends Activity {
	private Button openCamera;
	private TextView scanResult;
	private EditText inputContent;
	private Button genCode;
	private ImageView codeImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qrcode);
		
		openCamera=(Button)findViewById(R.id.openCamera);
		scanResult=(TextView)findViewById(R.id.scanResult);
		inputContent=(EditText)findViewById(R.id.inputContent);
		genCode=(Button)findViewById(R.id.genCode);
		codeImg=(ImageView)findViewById(R.id.codeImg);
		
		
		//打开摄像头，扫描二维码
		openCamera.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//打开扫描界面扫描条形码或二维码  
                Intent openCameraIntent = new Intent(QRcodeActivity.this,CaptureActivity.class);  
                startActivityForResult(openCameraIntent, 0); 
			}			
		});
		
		//生成二维码
		genCode.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String text=inputContent.getText().toString();
				if(null!=text && !text.equals("")){
					try {
						// 实例化二维码对象  
			            QRCodeWriter writer = new QRCodeWriter();  
			            // 用一个map保存编码类型  
			            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();  
			            // 保持字符集为“utf－8”  
			            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");  
			            /*  
			             * 第一个参数：输入的文本 
			             * 第二个参数：条形码样式－》二维码 
			             * 第三个参数：宽度 
			             * 第四个参数：高度 
			             * 第五个参数：map保存编码类型 
			             */			            
						BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE,  
						        300, 300, hints);
						// 将像素保存在数组里  
			            int[] pixels = new int[300 * 300];  
			            for (int y = 0; y < 300; y++) {  
			                for (int x = 0; x < 300; x++) {  
			                    if (bitMatrix.get(x, y)) {// 二维码黑点  
			                        pixels[y * 300 + x] = 0xff000000;  
			                    } else {// 二维码背景白色  
			                        pixels[y * 300 + x] = 0xffffffff;  
			                    }  
			  
			                }  
			            } 
			            // 生成位图  
			            Bitmap bitmap = Bitmap.createBitmap(300, 300,  
			                    Bitmap.Config.ARGB_8888); 
			            /*  
			             * 第一个参数：填充位图的像素数组 
			             * 第二个参数：第一个颜色跳过几个像素读取 
			             * 第三个参数：像素的幅度 
			             * 第四个参数：起点x坐标 
			             * 第五个参数：起点y坐标 
			             * 第六个参数：宽 
			             * 第七个参数：高 
			             */  
			            bitmap.setPixels(pixels, 0, 300, 0, 0, 300, 300);
			            
			            //设置图像
			            codeImg.setImageBitmap(bitmap);
			            
					} catch (WriterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
			}			
		});
	}
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        super.onActivityResult(requestCode, resultCode, data);  
        //处理扫描结果（在界面上显示）  
        if (resultCode == RESULT_OK) {  
            Bundle bundle = data.getExtras();  
            String result = bundle.getString("result");  
            scanResult.setText(result);  
        }  
    } 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
