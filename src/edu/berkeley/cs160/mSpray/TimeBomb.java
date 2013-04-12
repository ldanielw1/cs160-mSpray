package edu.berkeley.cs160.mSpray;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;


public class TimeBomb {
	public void ignite() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					synchronized (this) {
						wait(3000);
						explode();

					}
				} catch (InterruptedException ex) {
				}

			}
		};

		thread.start();
	}

	public void explode() {
		// Override
	}
}