package com.ogaclejapan.flux.components.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ogaclejapan.flux.R;

public class MainActivity extends BaseActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

}
