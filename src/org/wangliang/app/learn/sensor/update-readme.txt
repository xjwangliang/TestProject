http://mindtherobot.com/blog/550/android-ui-vintage-thermometer-code-update/
Android UI: Vintage Thermometer – Code Update
Aug 4th, 2010 at 1:35 pm

After I had posted the original article about making a vintage Android thermometer, it became popular very quickly and I was really surprised how useful some people found it. The code I wrote for that article was meant as an example, a draft of what you could really do using the techniques described in the post.

Freddy Martens from ATS Tech Lab took that code, improved it and modified it for his needs while creating several industrial automation components based on the improved code of Vintage Thermometer. You can have a look at them here. I recommend reading other articles on their blog as well and keeping an eye on it.

However, what Freddy also did as a big favor for me and for you MTR readers was merging his improvements to the original Vintage Thermometer code without changing its overall functionality. Thanks to that, we now have the following features:

    Comments in the code – I myself am not very famous for writing a lot of them
    “Magic” numbers extracted as constants (and other things Freddy did to polish the code)
    Properties are now available so you can tweak thermometer settings in the layout XML!

I am really excited by what Freddy did, and in general how we can collaborate within the community and benefit each other. This kind of interaction is what brings life to the platform biosphere. This is what I’m doing MTR for.

Grab the code here: thermometer-freddy.zip

http://atstechlab.wordpress.com/2010/07/30/gauge-and-dial-widget-for-android/

Gauge and Dial widget for Android
Posted on July 30, 2010 by atstechlab

It wasn’t hard at all creating my own widget for Android. The Vintage Thermometer created by Ivan (the guy from MindTheRobot.com) served as a starting point for my widget.  First I removed the sensor specific part from the code and added getters and setters to be able to pass any value I like to the widget. The second step was adding a significant amount of comment to the code. The third and last thing I did was making the widget more configurable. The result is given below.

As said, the widget is configurable. Most of what you see in the image above can be configured in the layout file. The following properties are available:

    Widget type: gauge, dial or both.
    Enabling the extra colored ring indicating ranges scale ranges.
    Setting the 3 individual colors of the gauge.
    Setting the scale limits.
    Setting the min and max values for each range.
    Setting the increments for each large and small notch.

I will make the binary version available soon. Please check back later.

Because Ivan was very helpfull, I decided to update his code also. I’ve added properties and inline comments to his source code. Please check Mind the Robot for updates on the Vintage Thermometer. Also check that site for other Android related items.