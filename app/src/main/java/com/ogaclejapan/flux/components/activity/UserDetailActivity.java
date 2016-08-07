package com.ogaclejapan.flux.components.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.ogaclejapan.flux.R;
import com.ogaclejapan.flux.databinding.ActivityUserDetailBinding;

public class UserDetailActivity extends BaseActivity {

  private static final String EXTRA_USER_ID = "user_id";
  private static final String EXTRA_USER_IMAGE = "user_image";

  public static Intent newIntent(Context context, String userId, String userImage) {
    Intent intent = new Intent(context, UserDetailActivity.class);
    intent.putExtra(EXTRA_USER_ID, userId);
    intent.putExtra(EXTRA_USER_IMAGE, userImage);
    return intent;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getActivityComponent().inject(this);
    ActivityUserDetailBinding binding =
        DataBindingUtil.setContentView(this, R.layout.activity_user_detail);
    binding.setUserName(getIntent().getStringExtra(EXTRA_USER_ID));
    binding.setUserThumbnail(getIntent().getStringExtra(EXTRA_USER_IMAGE));
  }
}
