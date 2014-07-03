package com.thanksandroid.example.dragdrop;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.Color;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnLongClickListener,
		View.OnDragListener {

	private final String IMAGE_TAG_ANDROID = "ANDROID";
	private final String IMAGE_TAG_ROBOT = "ROBOT";
	private ImageView dragViewOne;
	private ImageView dragViewTwo;
	private ImageView dropView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dragViewOne = (ImageView) findViewById(R.id.img_android);
		dragViewTwo = (ImageView) findViewById(R.id.img_android_robot);
		dropView = (ImageView) findViewById(R.id.img_drop);

		dragViewOne.setTag(IMAGE_TAG_ANDROID);
		dragViewTwo.setTag(IMAGE_TAG_ROBOT);
		dragViewOne.setOnLongClickListener(this);
		dragViewTwo.setOnLongClickListener(this);

		// set OnDragListener on the view where to drop the dragged item
		dropView.setOnDragListener(this);

	}

	@Override
	public boolean onLongClick(View v) {
		String tag = v.getTag().toString();
		ClipData dragData = ClipData.newPlainText("TAG", tag);

		// Instantiates the drag shadow builder.
		View.DragShadowBuilder myShadow;

		if (tag.equals(IMAGE_TAG_ANDROID)) {
			myShadow = new View.DragShadowBuilder(dragViewOne);
			dropView.setImageResource(R.drawable.android);
		} else {
			myShadow = new View.DragShadowBuilder(dragViewTwo);
			dropView.setImageResource(R.drawable.android_robot);
		}

		// Starts the drag
		v.startDrag(dragData, myShadow, null, 0);

		return false;
	}

	@Override
	public boolean onDrag(View v, DragEvent event) {

		// Defines a variable to store the action type for the incoming
		// event
		final int action = event.getAction();

		// Handles each of the expected events
		switch (action) {

		case DragEvent.ACTION_DRAG_STARTED:

			// Here you can determine if this View can accept the dragged
			// data
			// Return true if it can and false otherwise. If returned false,
			// this view won't accept any event until Drag ended

			// Apply blue tint to view to tell user that drag has started
			((ImageView) v).setColorFilter(Color.BLUE);

			// Invalidate the view to force a redraw in the new tint
			v.invalidate();

			// returns true to indicate that the View can accept the
			// dragged data.
			return true;

		case DragEvent.ACTION_DRAG_ENTERED:

			// Applies a green tint to the View. Return true; the return
			// value is ignored in this case.

			((ImageView) v).setColorFilter(Color.GREEN);

			// Invalidate the view to force a redraw in the new tint
			v.invalidate();

			return true;

		case DragEvent.ACTION_DRAG_LOCATION:

			// Ignore the event
			return true;

		case DragEvent.ACTION_DRAG_EXITED:

			// Re-sets the color tint to blue. Returns true; the return
			// value is ignored.
			((ImageView) v).setColorFilter(Color.BLUE);

			// Invalidate the view to force a redraw in the new tint
			v.invalidate();

			return true;

		case DragEvent.ACTION_DROP:
			// Gets the item containing the dragged data
			ClipData dragData = event.getClipData();

			// Gets the text data from the item.
			final String tag = dragData.getItemAt(0).getText().toString();

			// Displays a message containing the dragged data.
			Toast.makeText(MainActivity.this, "Dragged image is " + tag,
					Toast.LENGTH_LONG).show();

			// Turns off any color tints
			((ImageView) v).clearColorFilter();

			// Invalidates the view to force a redraw
			v.invalidate();

			// Returns true. DragEvent.getResult() will return true.
			return true;

		case DragEvent.ACTION_DRAG_ENDED:

			// Turns off any color tinting
			((ImageView) v).clearColorFilter();

			// Invalidates the view to force a redraw
			v.invalidate();

			// Does a getResult(), and displays what happened.
			if (event.getResult()) {
				Toast.makeText(MainActivity.this, "The drop was handled.",
						Toast.LENGTH_LONG).show();

			} else {
				Toast.makeText(MainActivity.this, "The drop didn't work.",
						Toast.LENGTH_LONG).show();
				dropView.setImageBitmap(null);
			}

			// returns true; the value is ignored.
			return true;

		default:
			break;
		}

		return false;
	}
}
