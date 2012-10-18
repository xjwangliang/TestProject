   当开始显示listview的时候，让每一项按顺序从屏幕右边滑到左边。
   
    AnimationSet set = new AnimationSet(true);

    Animation animation = new AlphaAnimation(0.0f, 1.0f);
    animation.setDuration(500);
    set.addAnimation(animation);

    animation = new TranslateAnimation(
    Animation.RELATIVE_TO_SELF, 50.0f,Animation.RELATIVE_TO_SELF, 0.0f,
    Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF, 0.0f
    );
    animation.setDuration(1000);
    set.addAnimation(animation);

    LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);

    group_list.setLayoutAnimation(controller);