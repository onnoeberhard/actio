package actio.actio.functions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import actio.actio.R;

@SuppressWarnings("deprecation")
public class ImageGallery extends Activity implements AdapterView.OnItemSelectedListener, ViewSwitcher.ViewFactory, SwipeInterface {
 
	String[] imgs;
	String[] titles;
	int actid;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        imgs = getIntent().getStringArrayExtra("imgs");
        titles = getIntent().getStringArrayExtra("titles");
        actid = getIntent().getIntExtra("actid", 0);
 
        if(actid == 0) {
        	Toast.makeText(this, R.string.error_sorry, Toast.LENGTH_SHORT).show();
        	finish();
        }
        
        setContentView(R.layout.gallery);
 
        mSwitcher = (ImageSwitcher) findViewById(R.id.switcher);
        mSwitcher.setFactory(this);
        mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in));
        mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out));
 
        gallery = (Gallery) findViewById(R.id.gallery);
        gallery.setAdapter(new ImageAdapter(this));
        gallery.setOnItemSelectedListener(this);

        TouchController tc = new TouchController(this);
        (findViewById(R.id.gallery_all)).setOnTouchListener(tc);
    }
 
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
    	(new DownloadImageTask2(mSwitcher, (ProgressBar) findViewById(R.id.loading))).execute(imgs[position]);
    	((TextView)findViewById(R.id.gtv)).setText(titles[position]);
    }
 
    public void onNothingSelected(AdapterView<?> parent) {
    }
 
    public View makeView() {
        ImageView i = new ImageView(this);
        i.setScaleType(ImageView.ScaleType.FIT_CENTER);
        i.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        return i;
    }
 
    private ImageSwitcher mSwitcher;
    private Gallery gallery;
 
    public class ImageAdapter extends BaseAdapter {
        public ImageAdapter(Context c) {
            mContext = c;
        }
 
        public int getCount() {
            return imgs.length;
        }
 
        public Object getItem(int position) {
            return position;
        }
 
        public long getItemId(int position) {
            return position;
        }
 
		public View getView(int position, View convertView, ViewGroup parent) {
            ImageView i = new ImageView(mContext);
            ProgressBar pb = new ProgressBar(mContext);
            (new Functions.DownloadImageTask(i, pb)).execute(imgs[position]);
            i.setAdjustViewBounds(true);
            i.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            pb.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            return i;
        }
 
        private Context mContext;
 
    }

	public static class DownloadImageTask2 extends
			AsyncTask<String, Void, Bitmap> {
		ImageSwitcher bmImage;
		ProgressBar progress;

		public DownloadImageTask2(ImageSwitcher bmImage, ProgressBar progress) {
			this.bmImage = bmImage;
			this.progress = progress;
			progress.setVisibility(View.VISIBLE);
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			progress.setVisibility(View.GONE);
			bmImage.setImageDrawable(new BitmapDrawable(result));
		}
	}

	@Override
	public void bottom2top(View v) {}
	@Override
	public void top2bottom(View v) {}

	@Override
	public void left2right(View v) {
		gallery.setSelection(gallery.getSelectedItemPosition()-1);
	}

	@Override
	public void right2left(View v) {
		if(gallery.getSelectedItemPosition()+1 < gallery.getCount())
			gallery.setSelection(gallery.getSelectedItemPosition()+1);
	}
	
	File file, dir;
	
	public void addImage(View v) {
		String[] items = {"Take Picture", "Select from Gallery"/*, "Link to Image"*/};
		(new AlertDialog.Builder(this)).setTitle("Upload Picture")
		.setItems(items, new OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
				if (item == 0) {
					Calendar cal = Calendar.getInstance();
					dir = new File(Environment.getExternalStorageDirectory() + "/Actio/");
					if(!dir.exists()) {
						dir.mkdirs();
					}
					file = new File(Environment.getExternalStorageDirectory() + "/Actio/", (cal.getTimeInMillis() + ".jpg"));
					if (!file.exists()) {
						try {
							file.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						file.delete();
						try {
							file.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					/*file.delete();
					dir.delete();*/
					captImageURI = Uri.fromFile(file);
					Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					i.putExtra(MediaStore.EXTRA_OUTPUT,	captImageURI);
					startActivityForResult(i, 2);
		        } else if(item == 1) {
		    		Intent intent = new Intent();
		    		intent.setType("image/*");
		    		intent.setAction(Intent.ACTION_GET_CONTENT);
		    		startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
		        }/* else if(item == 2) {
		        	//Link to Image
		        }*/
		    }
		}).create().show();
	}

	Uri currImageURI;
	Uri captImageURI;
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			final String imagePath = (requestCode == 1) ? getRealPathFromURI(data.getData()) : captImageURI.getPath();
			File f = new File(imagePath);
			if(f.length() < 2097152) {
				final HttpUploader uploader = new HttpUploader(this);
				final EditText et = new EditText(this);
				et.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				new AlertDialog.Builder(this).setView(et).setPositiveButton(android.R.string.ok, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						System.out.println(actid);
						System.out.println(et.getText().toString());
						uploader.execute(imagePath, Integer.toString(actid), et.getText().toString(), Integer.toString(imgs.length));
					}
				}).setNegativeButton(android.R.string.cancel, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).create().show();
			} else
				Toast.makeText(this, "The image is too large. Max size is 2mb.", Toast.LENGTH_SHORT).show();
		} else {
			if(file != null) {
				file.delete();
				dir.delete();
			}
		}
	}
	
	public String getRealPathFromURI(Uri contentUri) {
	    String [] proj = {MediaStore.Images.Media.DATA};
	    android.database.Cursor cursor = managedQuery(contentUri, proj, null, null, null);
	    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	    cursor.moveToFirst();
	    return cursor.getString(column_index);
	}

}