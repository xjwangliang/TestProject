package org.wangliang.app.learn.sensor;

import org.wangliang.app.learn.R ;

import android.app.Activity ;
import android.os.Bundle ;
import android.view.ContextMenu ;
import android.view.Menu ;
import android.view.MenuItem ;
import android.view.View ;
import android.view.ContextMenu.ContextMenuInfo ;


//@Override public void onDraw(Canvas canvas) {
//    canvas.save(Canvas.MATRIX_SAVE_FLAG);
//    canvas.scale((getWidth() > getHieght()) ? getHeight() : getWidth());
//    ...
//    canvas.restore();
//}


//https://developer.android.com/guide/topics/graphics/hardware-accel.html硬件加速

//It works now, if you add a line to force the custom view to be software rendered.
//http://stackoverflow.com/questions/11460958/canvas-not-displaying-all-drawn-parts-in-custom-view
//So, the key clue in my mystery seemed to be that it worked on the emulator, but not on the hardware devices.
//Hardware Rendering
//
//I did peruse the hardware rendering page on the Android Developer's website, but apparently not closely enough.
//
//http://developer.android.com/guide/topics/graphics/hardware-accel.html
//
//While it does mention that the API's are available beginning version 11, it does not say that Hardware Rendering is turned on for all applications by default, starting with API Level 14 (ICS).
//
//What does this mean for us?
//
//Almost everything is faster; except for the few things that don't work.
//
//I managed to violate two of these, without realizing it:
//
//    Canvas.DrawTextOnPath()
//    Paint.setShadowLayer()
//
//It's not mentioned in the API reference (or anywhere else I can find, and certainly not checked by Lint), but using any of the listed operations can do weird things.
//
//In my case, Canvas.DrawTextOnPath() seemed to work just fine.
//
//But when Android notice that the paint that I used on the hand had shadow layer set, it silently ignored it.
//How do I know if my View is hardware accelerated?
//
//From the documentation link above:
//
//    There are two different ways to check whether the application is hardware accelerated:
//
//        View.isHardwareAccelerated() returns true if the View is attached to a hardware accelerated window.
//        Canvas.isHardwareAccelerated() returns true if the Canvas is hardware accelerated
//
//    If you must do this check in your drawing code, use Canvas.isHardwareAccelerated() instead >of View.isHardwareAccelerated() when possible. When a view is attached to a hardware >accelerated window, it can still be drawn using a non-hardware accelerated Canvas. This >happens, for instance, when drawing a view into a bitmap for caching purposes.
//
//In my case, the opposite appears to have occurred. The custom view logs that it is not Hardware-accelerated; however, the canvas reports that it is hardware-accelerated.
//Work Arounds and Fixings
//
//The simplest fix is forcing the custom view to do software rendering. Per the documentation this can be accomplished by:
//
//myView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//
//Alternatively, you could remove the offending operations, and keep hardware rendering turned on.
//
//Learn from my misfortune. Good luck, all.



public class ThermometerActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_test);
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case 1:
				 setContentView(R.layout.sensor_test2);
				break ;

			default:
				 setContentView(R.layout.sensor_test);
				break ;
		}
		return super.onOptionsItemSelected(item) ;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "A");
		menu.add(0, 1, 1, "B");
		return super.onCreateOptionsMenu(menu) ;
	}
}



//scratch
//AnalogClock
//vintage Coca-Cola thermometers,
//metallic rim金属轮辋
//skewed倾斜的
//nicks
//Fahrenheit华氏

/**
Here’s the list of nice features this custom view has:

    A gradient metallic rim and a textured face
    The logo changes its color depending on the temperature (as you can see in the screenshots)
    A circle-bent title below the logo
    A 3D hand with a shadow
    The hand moves in a physically realistic fashion
    And, of course, it shows the real temperature – based on the phone temperature sensor (if your phone has one)

Also, the thermometer view tries to be a good Android UI citizen:

    Scales well to any size
    Supports screen orientation changes and layout changes
    Optimizes its drawing procedures, doesn’t eat CPU when the hand is not moving
    Saves and restores its state gracefully when the activity is paused, re-created etc.
    Reusable – not bound to any external code
    
  If you’d like to know how to develop views of this kind, read on. It’s so easy and fun to do that you will be surprised.

Also, you can grab the code at the bottom of the article as usual. I suggest you do it right now since we will refer to it a lot in the following sections.

(In this article I won’t focus on code too much, because I want to highlight the sequence of steps you need to take to implement this custom view rather than code specifics. However, I will paste in the most interesting snippets.)

OK, time to start coding!



Step 1: Creating an Empty Custom View

I assume you are familiar with the built-in Android views such as TextView, ImageView and so on. We are going to develop our own custom view basically the same way that Android developers created the built-in components.

If you decide to build your own custom view from scratch, you unsurprisingly have to derive from a class called View. The first thing you need to do is to override the onMeasure() method. The onMeasure() takes two View.MeasureSpec instances, one for width and one for height, and is required to return the desired size of the view based on restrictions given in the MeasureSpec’s. It’s an interesting method and you should read the corresponding reference docs before you try to override it.

Back to our thermometer. What we want to achieve in onMeasure() is that we want to keep our custom view square, i.e. keep the width equal to the height — regardless of what the MeasureSpec restrictions are. You can read the code in Thermometer.onMeasure() to see how it is done.

Here’s an illustration of how the thermometer scales to different sizes:


After you override onMeasure() correctly, you have created the simplest view possible – a spacer that does not display anything, just takes some layout space. Now let’s draw something.

By the way, I called my view class Thermometer, without the word view in the name, inspired by the standard AnalogClock.

Step 2: Drawing the Parts

The most interesting callback in the View class is onDraw(). We get a Canvas instance as a parameter. The Canvas is the Android interface to 2D graphics, used to draw everything you see on the screen except stuff that is drawn using OpenGL (usually 3D games and stuff). There is a handful of auxiliary classes that help you define colors, brushes, gradients and so on in the same android.graphics package.

In today’s 3D world, I really love to do some old-school 2D graphics from time to time.
For this example, I was inspired by vintage Coca-Cola thermometers, such as this one.

I won’t describe all the code in details here so watch the steps and read the code to see how I draw the entire thermometer:

Scaling

We use the scale() method in Canvas to change the 2D coordinate space from width x height to 1.0 x 1.0 regardless of the current custom view size. This allows us to draw stuff based on float coordinates ranging from 0.0 to 1.0 (remember, width and height are kept equal by onMeasure() – that simplifies scaling a bit). Here’s a snippet from onDraw():
	
float scale = (float) getWidth();  
canvas.save(Canvas.MATRIX_SAVE_FLAG);
canvas.scale(scale, scale);

Apparently, if we want to scale from width to 1.0, scale should be set to width.
We use the Canvas.save() method together with the Canvas.restore() to restore the canvas to the initial state before we exit the onDraw() method (although we are not explicitly required to do so by the API).

The Rim

Method: drawRim(), see the initDrawingTools() method for tool definitions.

The first thing we draw is the metallic rim. We use a Paint with a LinearGradient shader to achieve the metallic look. Then we just draw a dark circle outline around the filled circle to make the entire drawing look better on different backgrounds.

Note how the gradient direction is not vertical but a bit skewed. I think the result looks more realistic like that. In general, you should play along with your 2D graphics for a while before the result looks good to you.


Method: drawFace(), see the initDrawingTools() method for tool definitions.

Now let’s draw the face. We will use a Bitmap based texture for it. Generally, textures can become a problem for scaled 2D graphics because they look bad both when scaled too large and when scaled too small. In this case, however, we would not be able to achieve the worn scratched look without a good texture. The only other Bitmap we will use will be the logo.

In order to fill a primitive with a bitmap texture, use the BitmapShader class. It works fine. Don’t forget to call the setFilterBitmap() method in your Paint instance with true to make the bitmap scale smoothly.

What we also draw here is the inner circular shadow that makes the rim look kind of 3D (closer to the camera than the face). We use a RadialGradient to do that. It’s cool that we can make a gradient with different alpha values for each color like in this case, so that we can make the beginning of the gradient more transparent than the end.


Method: drawScale(), see the initDrawingTools() method for tool definitions.

To draw the scale, we need to use more canvas transformations. Knowing how many scale nicks we have, we know how many degrees we have per nick. Then we use Canvas.rotate() to draw each nick and the number where necessary.

Note how we use Paint.setTextScaleX() to make the font a bit narrower. In my opinion, this makes the font look a bit more vintage.

In the end, we call Canvas.restore(), and the canvas is back to its un-rotated state.

(Sorry, non-US guys, the scale is in Fahrenheit. I’m not from the US either, so it was a good exercise for me when I tested the thermometer. :) )


Method: drawTitle(), see the initDrawingTools() method for tool definitions.

The title is the bent orange text “mindtherobot.com” at the bottom of the thermometer face.

In order to draw it, I used a very powerful method: Canvas.drawTextOnPath(). You can do many cool text effects with it since Path can be any shape, including arcs, polygons, circles and so on. In our case we use an arc.

Remember, you can use Paint.setTextAlign() to align the text in the middle of the path like in this example.


Method: drawLogo(), see the initDrawingTools() method for tool definitions.

The logo is just a bitmap I took from this blog’s header. However, I used a LightingColorFilter to re-color the bitmap depending on the temperature.

The logo is green at 40F, it turns red when the temperature is above 40F and blue when it is below. This looks especially cool when the hand is moving.


Method: drawHand(), see the initDrawingTools() method for tool definitions.

A hand is just a Path that we fill with a solid color and rotate using canvas transformations.

The interesting part is the shadow. We draw it using Paint.setShadowLayer() to draw it. It is called a “temporary API” in the docs, however for now it works so I will use it.

The problem is that the shadow rotates together with the path. In the real world, it should have the same offset regardless of the hand angle since the light source is not moving. We could fix it by anti-rotating the shadow using sine and cosine functions but I leave this as an exercise to the most pedantic of you. Since the arrow is not moving a lot and the shadow is pretty subtle, I don’t see much of a problem in that right now.

Also, please note that the hand is not drawn when we don’t know the temperature (perhaps the sensor did not give us an update yet).

So this is how we draw all parts of the thermometer. It is important to try to achieve the desired look first, and only then start optimizing the drawing procedures. By the way, optimization is our next step.

Step 3: Optimization

Once we have an idea of how our view will change depending on parameters, we can notice that some parts are static and some are moving. I suggest drawing the parts that do not move onto a Bitmap and draw them all together using a simple Canvas.drawBitmap() call in onDraw(). This way we trade some memory for performance.

In our case, the parts that do not move are the rim, the face, the scale and the title. The logo changes depending on the temperature, and so does the hand – so we need to redraw them every time.

Thus we add the following code to onSizeChanged():
// free the old bitmap
if (background != null) {
  background.recycle();
}
 
background = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
Canvas backgroundCanvas = new Canvas(background);
float scale = (float) getWidth();  
backgroundCanvas.scale(scale, scale);
 
drawRim(backgroundCanvas);
drawFace(backgroundCanvas);
drawScale(backgroundCanvas);
drawTitle(backgroundCanvas);
As you can see, we can use our drawXXX() method with any Canvas, and here we substitute the “real” Canvas provided to us in onDraw() with a Canvas that draws to our buffer Bitmap (background).

Then, in onDraw() we do the following:
	
canvas.drawBitmap(background, 0, 0, backgroundPaint);

And only after that we draw the moving parts:
	
drawLogo(canvas);
drawHand(canvas);
This is the simple optimization that should save some CPU cycles, especially when the hand is moving. Now let’s see what makes it move?


Step 4: Mechanics

Motion effects look most real when they are backed by real-world physical calculations. In our case, the hand has the following mechanical properties:

    handPosition – the current position of the hand (in degrees)
    handTarget – the target position of the hand (in degrees). If the position is not equal to the target, we need to move the hand.
    handVelocity – how many degrees the hand moves per second, can be positive or negative
    handAcceleration – how many degrees per second are added to the velocity per second. You might need to recall 7th grade physics if you don’t quite understand.
    lastHandMoveTime – the previous moment when we moved the hand. Needed for frame-rate independent animation. (Ask me in a comment if you don’t know what I mean here.)

And remember, you can’t use == to compare floats because of precision issues, unless you’re comparing to zero. Use Math.abs() of their difference (abs(a – b) < 0.001).
Step 5: State Saving & Getting the Temperature

Have a look at the onSaveInstanceState() and onRestoreInstanceState() methods. If you don’t do what is done there, you will lose all the state (in our case, the hand mechanics parameters) whenever the view is recreated. It can be recreated in various situations, for example when the screen orientation changes, so you should be ready for that. Also, please note how we save and restore the super state. It is required to do so.

I do not plan to describe how exactly to use the temperature sensor because I plan to write another post about sensors in general. Just wanted to add that on my Motorola Droid temperature updates arrive at a very slow rate, perhaps once or twice a minute, so sometimes you need to be patient when you wait for the hand to appear and to move.
Conclusion

We just created a cool looking, animated custom view from scratch. I hope you enjoyed this article. Feel free to ask questions, point to bugs and just show your feelings in the comments.

And one final note: don’t screw up the phone when testing the thermometer. Don’t put it into the fridge for too long


**/
