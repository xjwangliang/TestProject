Signature的生成方法
这里以windows平台的cygwin环境和命令行环境为例，且默认开发者已经安装了jdk，能够正常使用keytool

cygwin环境下
在cygwin环境下运行如下命令，输出结果即为signature
keytool -exportcert -alias [alias] -keypass [alias password] -keystore [keystore file path] -storepass [keystore password] | md5sum

例如：当前路径下包含用于对app签名的test.keystore文件，且keystore密码为123456，别名为openapi，别名密码为654321，则运行如下命令：
keytool -exportcert -alias openapi -keypass 654321 -keystore ./test.keystore -storepass 123456 | md5sum

输出结果为：
8f88de9693d22430ad7ce55047ec7946
85c6c2474dce00be11cf43af515d1807


sig[0] = 
308202eb30820254a00302010202044d36f7a4300d06092a864886f70d01010505003081b9310b300906035504061302383631123010060355040813094775616e67646f6e673111300f060355040713085368656e7a68656e31353033060355040a132c54656e63656e7420546563686e6f6c6f6779285368656e7a68656e2920436f6d70616e79204c696d69746564313a3038060355040b133154656e63656e74204775616e677a686f7520526573656172636820616e6420446576656c6f706d656e742043656e7465723110300e0603550403130754656e63656e74301e170d3131303131393134333933325a170d3431303131313134333933325a3081b9310b300906035504061302383631123010060355040813094775616e67646f6e673111300f060355040713085368656e7a68656e31353033060355040a132c54656e63656e7420546563686e6f6c6f6779285368656e7a68656e2920436f6d70616e79204c696d69746564313a3038060355040b133154656e63656e74204775616e677a686f7520526573656172636820616e6420446576656c6f706d656e742043656e7465723110300e0603550403130754656e63656e7430819f300d06092a864886f70d010101050003818d0030818902818100c05f34b231b083fb1323670bfbe7bdab40c0c0a6efc87ef2072a1ff0d60cc67c8edb0d0847f210bea6cbfaa241be70c86daf56be08b723c859e52428a064555d80db448cdcacc1aea2501eba06f8bad12a4fa49d85cacd7abeb68945a5cb5e061629b52e3254c373550ee4e40cb7c8ae6f7a8151ccd8df582d446f39ae0c5e930203010001300d06092a864886f70d0101050500038181009c8d9d7f2f908c42081b4c764c377109a8b2c70582422125ce545842d5f520aea69550b6bd8bfd94e987b75a3077eb04ad341f481aac266e89d3864456e69fba13df018acdc168b9a19dfd7ad9d9cc6f6ace57c746515f71234df3a053e33ba93ece5cd0fc15f3e389a3f365588a9fcb439e069d3629cd7732a13fff7b891499


命令行环境下
由于命令行下系统没有提供默认的md5sum，建议开发者自行选择合适的md5生成工具(HYPERLINK "http://sourceforge.net/directory/os:windows/freshness:recently-updated/?q=md5"http://sourceforge.net/directory/os:windows/freshness:recently-updated/?q=md5 )，
	生成方法跟cygwin环境下类似，可以先将keytool的输出重定向到文件，然后再用md5生成工具对该文件进行md5处理，获得signature，结果跟cygwin环境下相同。

keytool -exportcert -alias openapi -keypass 654321 -keystore ./test.keystore -storepass 123456 > out.txt

孟爽，我感觉微信有很多毛病，现在有一点眉目了，但是还是有问题。我做过两个测试，都是按照文档的要求，一个正常，一个不正常。而使用正是环境的app测试，之前一直调不出微信，现在能够调出微信，但是发不了消息，郁闷死了。我昨天给微信发了邮件，他回复检查 网站的内容和程序使用的要一致。今天又发了微博私信，现在还没回复。


keytool -exportcert -alias h_weixinsimple_alias -keypass hweixinmain -keystore /Users/wangliang/Desktop/keystore/h_weixinsimple.keystore -storepass hweixin  >/Users/wangliang/Desktop/keystore/out.txt
keytool -exportcert -alias h_weixinsimple_alias -keypass hweixinmain -keystore /Users/wangliang/Desktop/keystore/h_weixinsimple.keystore -storepass hweixin |md5(这时mac下的)
keytool -exportcert -alias wangliangalias -keypass wangliangalias -keystore /Users/wangliang/Desktop/keystore/weixin_sample_test.keystore -storepass wangliang |md5(我的测试)
keytool -exportcert -alias guoku -keypass guoku1@# -keystore /Users/wangliang/Desktop/keystore/guoku-keystore -storepass guoku1@# |md5
keytool -exportcert -alias guokualias -keypass higuokualias -keystore /Users/wangliang/Desktop/keystore/myguoku.keystore -storepass higuoku | md5


85c6c2474dce00be11cf43af515d1807

keytool -genkey -v -keystore /Users/wangliang/Desktop/keystore/h_weixinsimple.keystore -alias h_weixinsimple_alias -keyalg RSA -validity 20000  

输入keystore密码：   hweixin
再次输入新密码: hweixin
您的名字与姓氏是什么？
  [Unknown]：  wang
您的组织单位名称是什么？
  [Unknown]：  company
您的组织名称是什么？
  [Unknown]：  guoku
您所在的城市或区域名称是什么？
  [Unknown]：  beijing
您所在的州或省份名称是什么？
  [Unknown]：  beijing
该单位的两字母国家代码是什么
  [Unknown]：  cn
CN=wang, OU=company, O=guoku, L=beijing, ST=beijing, C=cn 正确吗？
  [否]：  y

正在为以下对象生成 1,024 位 RSA 密钥对和自签名证书 (SHA1withRSA)（有效期为 20,000 天）:
	 CN=wang, OU=company, O=guoku, L=beijing, ST=beijing, C=cn
输入<h_weixinsimple_alias>的主密码
	（如果和 keystore 密码相同，按回车）：  hweixinmain
再次输入新密码: hweixinmain
[正在存储 /Users/wangliang/Desktop/keystore/h_weixinsimple.keystore]

		
执行下面这句

jarsigner -verbose -keystore /Users/wangliang/Desktop/keystore/h_weixinsimple.keystore -signedjar /Users/wangliang/Desktop/keystore/weixin_sample_signed.apk  /Users/wangliang/Desktop/keystore/weixin_sample.apk  h_weixinsimple_alias
jarsigner -verbose -keystore /Users/wangliang/Desktop/keystore/guoku-keystore -signedjar /Users/wangliang/Desktop/keystore/guoku_sign.apk  /Users/wangliang/Desktop/keystore/guoku_un.apk  guoku
jarsigner -verbose -keystore /Users/wangliang/Desktop/keystore/myguoku.keystore -signedjar /Users/wangliang/Desktop/keystore/myguoku_sign.apk  /Users/wangliang/Desktop/keystore/myguoku_un.apk  /Users/wangliang/Desktop/keystore/guoku-keystore
jarsigner -verbose -keystore /Users/wangliang/Desktop/keystore/guoku-keystore -signedjar /Users/wangliang/Desktop/keystore/guoku_sign.apk  /Users/wangliang/Desktop/keystore/guoku_un.apk  guoku

jarsigner -verbose -keystore /Users/wangliang/Desktop/keystore/myguoku.keystore -signedjar /Users/wangliang/Desktop/keystore/myguoku_sign.apk  /Users/wangliang/Desktop/keystore/myguoku_un.apk  guokualias


(higuoku guokualias(alias) higuokualias)





1、使用android自带的debug.keystore进行签名
Android自带的证书文件的位置可以在Eclispe的 windows->preferences->android->Build中查看，是 在.android/debug.keystore 文件，测试程序和微博客户端都使用该证书签名后能够测试程序能够正常访问。

别名和密码

debug.keystore的别名为 androiddebugkey ，同时密码为 android 。

签名命令如下：
jarsigner -verbose -keystore debug.keystore -signedjar 签名后的 签名签的 debug.keystore
命令解释：
jarsigner是Java的签名工具，JDK自带
-verbose参数表示：显示出签名详细信息
-keystore表示使用当前目录中的android.keystore签名证书文件。
--signedjar weibo_signed.apk netease_microblog_android_unsigned.apk表示签名后生成的APK名称为 weibo_signed.apk，未签名的APK Android软件名称为netease_microblog_android_unsigned.apk
-androidauto.keystore表示签名文件的别名，生成证书的时候有书写




Q:应用没有审核通过能不能进行调试？
A:可以调试。iOS应用只要申请并获取到AppID就可进行调试，Android应用除了获取AppID外，还需要在网站上填写包名和签名两个字段，签名的具体生成方法见《Signature的生成方法文档》。

Q:为什么代码混淆之后，会导致无法弹出发送第三方消息的确认框？
A:需要在混淆配置文件proguard.cfg中，增加如下两行代码:
-keep class com.tencent.mm.sdk.openapi.WXMediaMessage { *;}

-keep class com.tencent.mm.SDK.openapi.** implements com.tencent.mm.sdk.openapi.WXMediaMessage$IMediaObject {*;}

Q:为什么用网上下载的SDK Demo工程直接运行到设备上，一开始可以正常调试，后面就不可以？
A:这里是由于身份校验失败造成的，要运行SDK Demo工程，可以参考文档《如何运行SDK Demo工程》，同时请下载更新最新版的SDK Sample。
Q:为什么日志里面有时候会出现setup profile from amm_manifest.xml failed错误？
A:这是由于SDK加载内部配置文件失败造成的，不影响SDK的正常使用，可以忽略这个错误日志。
更多问题，请邮件联系 weixinapp@qq.com 或关注官方微博 http://t.qq.com/weixin_app 的最新动态。


方法一：Android开发网提示这些问题主要是由于资源文件造成的，对于android开发来说应该检查res文件夹中的文件，逐个排查。这个问题可以通过升级系统的JDK和JRE版本来解决。 
方法二：这是因为默认给apk做了debug 签名，所以无法做新的签名这时就必须点工程右键->Android Tools ->Export Unsigned Application Package. 
或者从AndroidManifest.xml的 Exporting上也是一样的 
然后再基于这个导出的unsigned apk做签名，导出的时候最好将其目录选在你之前产生keystore的那个目录下，这样操作起来就方便了


openssl md5    /Users/wangliang/Desktop/keystore/out.tx

http://www.neilturner.me.uk/2006/09/10/verifying_md5_checksums_o.html
	
To verify the MD5SUM in OS X, simply open the Terminal and type “md5″ followed by one space, and then drag and drop the 
downloaded file from the finder into the terminal window. Hit return on your keyboard and wait a few moments (The larger 
the file, the longer it will take). The terminal will spit out the MD5 hash for the file, 
which you can then compare to the once provided by the download originator!


md5 /Users/wangliang/Desktop/keystore/weixin_sample_test.apk  结果和上面一样





/Users/wangliang/.android/debug.keystore默认的位置





