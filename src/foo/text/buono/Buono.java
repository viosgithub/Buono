package foo.text.buono;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

public class Buono extends Activity {
	/** Called when the activity is first created. */
	InputStream is;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://foodpic.net/post.php");
		MultipartEntity entity = new MultipartEntity();
		post.setEntity(entity);

		FileBody fileBody = new FileBody(new File(Environment
				.getExternalStorageDirectory().getAbsolutePath() + "/a.jpg"));
		entity.addPart("file", fileBody);

		try {
			client.execute(post);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (entity != null) {
			try {
				is = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				String line;
				while ((line = reader.readLine()) != null) {
					Log.d("debug", line);
				}
			} catch (UnsupportedOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					entity.consumeContent();
				} catch (UnsupportedOperationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}
}