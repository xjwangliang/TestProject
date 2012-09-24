KeyguardManager localKeyguardManager = (KeyguardManager)getSystemService("keyguard");
KeyguardManager.KeyguardLock localKeyguardLock = localKeyguardManager.newKeyguardLock("ttpod_screen_locker");
localKeyguardLock.disableKeyguard();
localKeyguardManager.exitKeyguardSecurely(new c(this, localKeyguardLock));



PowerManager.WakeLock

PhoneStateListener

 this.t = ((PowerManager)getSystemService("power")).newWakeLock(1, getClass().getName());
    this.t.setReferenceCounted(false);
    this.t.acquire();
    ((TelephonyManager)getSystemService("phone")).listen(this.B, 32);
    
    
    
 protected final void s()
  {
    RemoteViews localRemoteViews = new RemoteViews(getPackageName(), d.a);
    localRemoteViews.setTextViewText(c.d, getText(e.a));
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(i.a(this, this.l.r));
    localStringBuilder.append(" - ");
    localStringBuilder.append(i.a(this, this.l.s));
    localRemoteViews.setTextViewText(c.c, localStringBuilder);
    Notification localNotification = new Notification();
    if (this.k)
    {
      localNotification.tickerText = localStringBuilder.toString();
      this.k = false;
    }
    localNotification.contentView = localRemoteViews;
    localNotification.flags = (0x2 | localNotification.flags);
    localNotification.icon = com.sds.android.ttpod.core.b.a;
    localNotification.when = System.currentTimeMillis();
    localNotification.contentIntent = PendingIntent.getActivity(this, 0, new Intent("com.sds.android.ttpod.ENTRY").addFlags(268435456), 0);
    a(hashCode(), localNotification);
  }
  
  
      removeCallbacksAndMessages(null);