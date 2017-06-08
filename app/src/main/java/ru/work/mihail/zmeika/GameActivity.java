package ru.work.mihail.zmeika;

import java.util.Timer;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import static java.lang.Math.abs;

public class GameActivity extends Activity implements View.OnClickListener {

	GameSurface surf;
	Timer t;
	int width, height;
	int x_begin, y_begin;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		surf = new GameSurface(this);
		this.setContentView(surf);
		t = new Timer();
		height = this.getWindowManager().getDefaultDisplay().getHeight();
		width = this.getWindowManager().getDefaultDisplay().getWidth();

		x_begin = width / 2;
		y_begin = height / 2;
	}

	@Override
	public void onStart() {
		super.onStart();
		super.onStart();
		t.scheduleAtFixedRate(new GraphUpdater(surf), 0, 100);
		t.scheduleAtFixedRate(new StepUpdater(this), 0, 500);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@Override
	public void onStop() {
		super.onStop();
		t.cancel();
		t.purge();
	}

	public boolean onTouchEvent(MotionEvent e)
	{
		int xpos=(int) e.getX();
		int ypos=(int) e.getY();

		switch (e.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				x_begin = xpos;
				y_begin = ypos;
			case MotionEvent.ACTION_MOVE:
				int dx = xpos - x_begin;
				int dy = ypos - y_begin;
				if (abs(dx) > abs(dy))
				{
					if (dx > 0)
						surf.mField.setDirection(SnakeGame.DIR_EAST);
					else
						surf.mField.setDirection(SnakeGame.DIR_WEST);
				}
				else
				{
					if (dy > 0)
						surf.mField.setDirection(SnakeGame.DIR_SOUTH);
					else
						surf.mField.setDirection(SnakeGame.DIR_NORTH);
				}
				break;
		}

		return true;
	}

	public void Step() {
		if (!surf.mField.nextMove()) {
			SimpleSnakeActivity.GAME_MODE=1;
			this.finish();
		}
		else{
			SimpleSnakeActivity.GAME_SCORE=this.surf.mField.mScore;
		}
	}

	public void onClick(View view) {
		// do nothing
	}
}
