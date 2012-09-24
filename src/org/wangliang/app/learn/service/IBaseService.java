package org.wangliang.app.learn.service ;

import android.os.Binder ;
import android.os.IBinder ;
import android.os.IInterface ;
import android.os.Parcel ;
import android.os.RemoteException ;

public abstract interface IBaseService extends IInterface {
	
	
	public abstract void sendToServiceMsg(String paramToServiceMsg)
			throws RemoteException ;

	public static abstract class Stub extends Binder implements IBaseService {
		private static final String DESCRIPTOR = "com.tencent.qphone.base.remote.IBaseService" ;
		static final int TRANSACTION_sendToServiceMsg = 1 ;

		public Stub() {
			attachInterface(this, "com.tencent.qphone.base.remote.IBaseService") ;
		}

		public static IBaseService asInterface(IBinder iBinder) {
			android.os.IInterface i ;
			if (iBinder != null) {
				i = iBinder.queryLocalInterface("com.tencent.qphone.base.remote.IBaseService") ;
				if ((i == null)|| (!(i instanceof IBaseService))){
					i = new Proxy(iBinder) ;
				}else{
					i = (IBaseService) i ;
				}
			} else {
				i = null ;
			}
			return (IBaseService) i ;
		}

		public IBinder asBinder() {
			return this ;
		}

		public boolean onTransact(int paramInt1, Parcel parcel,
				Parcel p1, int p2) throws RemoteException {
			int i ;
			switch (paramInt1) {
				default:
					boolean bool = super.onTransact(paramInt1, parcel,
							p1, p2) ;
					break ;
				case 1:
					parcel.enforceInterface("com.tencent.qphone.base.remote.IBaseService") ;
						//localToServiceMsg = (ToServiceMsg) ToServiceMsg.CREATOR.createFromParcel(paramParcel1) ;
					p1.writeNoException() ;
					i = 1 ;
					break ;
				case 1598968902:
					p1
							.writeString("com.tencent.qphone.base.remote.IBaseService") ;
					i = 1 ;
			}
			return false ;
		}

		private static class Proxy implements IBaseService {
			private IBinder mRemote ;

			Proxy(IBinder paramIBinder) {
				this.mRemote = paramIBinder ;
			}

			public IBinder asBinder() {
				return this.mRemote ;
			}

			public String getInterfaceDescriptor() {
				return "com.tencent.qphone.base.remote.IBaseService" ;
			}

			public void sendToServiceMsg(String paramToServiceMsg)
					throws RemoteException {
				Parcel localParcel2 = Parcel.obtain() ;
				Parcel localParcel1 = Parcel.obtain() ;
				try {
					localParcel2
							.writeInterfaceToken("com.tencent.qphone.base.remote.IBaseService") ;
					if (paramToServiceMsg != null) {
						localParcel2.writeInt(1) ;

					}
					this.mRemote.transact(1, localParcel2, localParcel1, 0) ;
					localParcel1.readException() ;
					return ;
//					/localParcel2.writeInt(0) ;
				} finally {
					localParcel1.recycle() ;
					localParcel2.recycle() ;
				}
			}
		}
	}
}