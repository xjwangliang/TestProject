http://mindtherobot.com/blog/534/android-ui-making-an-analog-rotary-knob/
Android UI: Making an Analog Rotary Knob
Jul 28th, 2010 at 8:02 pm

For some reason, I really enjoy recreating analog components on Android. Today I’d like to share my experience of re-creating a knob that looks like this:

As you can guess, this type of knob does not have min/max bounds and can be freely rotated as much as you want. This type of control is often used for browsing through a list of options that can be wrapped around. Also, an important property is that this knob does not rotate smoothly but switches from one nick to another. You can imagine that it also clicks when you rotate it – and our Android replica will also make that sound!

Here’s how the Android version looks:

I’ve included the source into this article. However, please be warned it is not completed and you will need to fix or finish few things before you can use this code in your own app.

And now, let’s get down to work!
Decomposition

If you have some experience making custom analog-looking controls, then you know where to start. We start with separating everything we draw into layers:

#1 is the outer circle, it has small dents on it and shadow that falls from the top left corner to the bottom right corner almost as a linear gradient. #2 is the inner circle. It contains the nicks and has an opposite shadow to the inner circle. That creates the look that the inner circle is embossed in the control. Now, #3 is a round piece that I call the center. It has a radial gradient that makes it look spherical, and a blurred light circle around it that separates it from the inner circle nicely.

It’s important to understand that all three layers will rotate when you rotate the knob, except for their shadows. That means we have to draw shadows separately.
Realism

First of all, I used BlurMaskFilter in various places to make the boundary lines, dents and shadows look really soft. In addition, I used the following texture for realism (click to see):

The problem with this kind of control is that, unless there is something that an eye can stick to, you won’t see any motion when you switch from one nick to another. It’s obvious since this control is structurally the same regardless of rotation. Thus I tried to overcome that problem using the following small tricks:

    The texture has some scratches that visibly rotate when you rotate the knob
    Nicks have a bit different length, very slight but that should enough for the brain to notice
    Rotation is not 100% precise, there is some random backlash

It seems to me that these tricks make the rotation visible and not confusing to the eye. Of course, we could just add a marker or make one of the nicks of a different color. However, that would make the result different from its original counterpart.

I’m using SoundPool to make the sound. You can read my article on WiseAndroid to learn more about SoundPool.
Optimization

I created a simple class called DrawLayer (perhaps I’ll rename it later) and cache all drawing into a set of draw-layers and only draw the bitmaps when it’s time to onDraw().

It’s important to rotate only the plastic layers and not to rotate the shadow layers.
Conclusion & Source

This is how I approached replicating an analog rotating knob. If you have any ideas how to improve it, you are welcome to suggest in comments.

Also, if you grab the source, please remember it’s just a prototype that needs to be improved and cleaned up.

Otherwise, I hope this was helpful or at least interesting. Have fun!

Attachment: the source

Tags: 2d, android, apps, custom, graphics, ui, view, widget

In General. You can leave a response, or trackback from your site.
