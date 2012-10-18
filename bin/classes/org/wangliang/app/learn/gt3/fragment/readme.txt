http://android.codeandmagic.org/2011/07/android-tabs-with-fragments/
http://neilgoodman.net/2012/03/12/working-with-fragments-on-android-part-2/
http://blog.csdn.net/xyz_lmn/article/details/6927390

Fragment{
	onInflate()
	onCreate();
	onCreateView();
	
	onCreateContextMenu();
	
	getActivity()
	getArguments()
	getFragmentManager()
	getLoaderManager()
	getView();
	getTargetFragment();
	getTargetRequestCode();
	getView();
	
	
	
	 
        
}



DialogFragment{
	Dialog onCreateDialog(Bundle savedInstanceState){}
	getTheme();
    getShowsDialog();
    
    
    show(transaction, tag)
    show(manager, tag)
}
FragmentActivity{
	getSupportFragmentManager()
}

FragmentManager{
	beginTransaction()
}

FragmentTransaction{
	replace(R.id.simple_fragment, newFragment);
	addToBackStack(null);
	commit();
}